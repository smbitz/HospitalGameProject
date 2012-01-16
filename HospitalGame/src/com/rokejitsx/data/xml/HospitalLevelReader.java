package com.rokejitsx.data.xml;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParserException;

import android.graphics.PointF;
import android.util.Log;

import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.route.RouteManager;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.building.Chair;
import com.rokejitsx.ui.building.GlassDoor;
import com.rokejitsx.ui.building.Laundry;
import com.rokejitsx.ui.building.others.Food;
import com.rokejitsx.ui.building.others.Plant;
import com.rokejitsx.ui.building.others.Television;
import com.rokejitsx.ui.building.others.Water;
import com.rokejitsx.ui.building.transport.Ambulance;
import com.rokejitsx.ui.building.waitingqueue.Outside;
import com.rokejitsx.ui.building.ward.BabyScan;
import com.rokejitsx.ui.building.ward.Bed;
import com.rokejitsx.ui.building.ward.Dentist;
import com.rokejitsx.ui.building.ward.QuickTreat;
import com.rokejitsx.ui.building.ward.TAC;
import com.rokejitsx.ui.building.ward.Triage;
import com.rokejitsx.ui.building.ward.Xray;
import com.rokejitsx.ui.building.ward.pharmacy.FirstPharmacy;
import com.rokejitsx.util.StringUtil;

public class HospitalLevelReader extends XmlReader{
  
	
  private Hashtable<String, Vector<String>> levelInfo;
  private Hashtable<String, BuildingInfo> fixBuildingList, dropAreaBuildingList, equipmentList;
  private RouteManager routeManager;
  private int floor;
  public HospitalLevelReader(int id, int floor){
    super(id);	  
    levelInfo = new Hashtable<String, Vector<String>>();
    this.floor = floor;
  }
  
  
  public void getFixedBuilding(Vector<BuildingInfo> builingList){
    getBuildingList(fixBuildingList, builingList);   
  }
  
  public void getDropBuilding(Vector<BuildingInfo> builingList){
    getBuildingList(dropAreaBuildingList, builingList);   
  }
  
  public void getEquipmentBuilding(Vector<BuildingInfo> builingList){
    getBuildingList(equipmentList, builingList);	  
  }
  
  private void getBuildingList(Hashtable<String, BuildingInfo> list, Vector<BuildingInfo> result){
    Enumeration<BuildingInfo> e = list.elements();
    while(e.hasMoreElements()){
      BuildingInfo info = e.nextElement();
      if(info != null){
    	if(!result.contains(info))
          result.add(info);
      }
    }	  
  }
  
  /*public GameObject[] getBuilding(){
    GameObject[] result = new GameObject[buildingListInfo.size()];
    Enumeration<GameObject> e = buildingListInfo.elements();
    int i = 0;
    while(e.hasMoreElements()){
      result[i] = e.nextElement();
      i++;
    }
    
    return result;
  }
  */
  public RouteManager getRoute(){
    return routeManager;	  
  }

  @Override
  protected boolean parse(String tagName) {	
	Log.d("Rokejits", "HospitalLevel parse tagName = "+tagName);
	if(tagName.equals("WorldInfo"))
	  return true;
    Vector datas = levelInfo.get(tagName);
    Log.d("Rokejits", "datas = "+datas);
    if(datas == null){
      levelInfo.put(tagName, datas = new Vector<String>());	
    }
    Log.d("Rokejits", "datas2 = "+datas);
    try {
      String value = getStringValue();
      Log.d("Rokejits", "value = "+value);
	  datas.addElement(value);
	} catch (XmlPullParserException e) {
	  Log.d("Rokejits", "XmlPullParserException = "+e.toString());
	  e.printStackTrace();
	} catch (IOException e) {
      Log.d("Rokejits", "IOException = "+e.toString());
	  e.printStackTrace();
	}
    return true;
  }	
  
  public void initialBuilding(/*int hospitalLevel*/){
	fixBuildingList = new Hashtable<String, BuildingInfo>();
	dropAreaBuildingList = new Hashtable<String, BuildingInfo>();
	equipmentList = new Hashtable<String, BuildingInfo>();
	
    Vector<String> fixedIdList 				= levelInfo.get("actionFixedID");
    Vector<String> dropAreaList 			= levelInfo.get("actionDropArea");
    Vector<String> equipment				= levelInfo.get("actionEquipmentID");
    Vector<String> posList 					= levelInfo.get("actionPos");
    Vector<String> linkCountList 			= levelInfo.get("actionLinkCount");
    Vector<String> linkPointList 			= levelInfo.get("actionLinkPoint");
    Vector<String> actionPatientNodeList	= levelInfo.get("actionPatientNode");
    Vector<String> actionNodeList 			= levelInfo.get("actionNode");
    Vector<String> nodePosList 				= levelInfo.get("nodePos");
    Vector<String> connectionList 			= levelInfo.get("connectsTo");
    /*Vector actionEnabledList = levelInfo.get("ActionEnabled");
    Vector*/ 
    
    
    initBuilding(fixedIdList, fixBuildingList);
    initBuilding(dropAreaList, dropAreaBuildingList);
    initBuilding(equipment, equipmentList);
    
    setBuildingPosition(posList, fixBuildingList);   
    setBuildingPosition(posList, dropAreaBuildingList);
    setBuildingPosition(posList, equipmentList);
    /*setBuildingLinkCount(linkCountList);
    setBuildingLinkPoint(linkPointList);*/
    setBuildingActionPatientNode(actionPatientNodeList, fixBuildingList);
    setBuildingActionPatientNode(actionPatientNodeList, dropAreaBuildingList);
    setBuildingActionPatientNode(actionPatientNodeList, equipmentList);
    
    setBuildingActionNode(actionNodeList, fixBuildingList);
    setBuildingActionNode(actionNodeList, dropAreaBuildingList);
    setBuildingActionNode(actionNodeList, equipmentList);
    
    initRoute(nodePosList);
    setNodeConnection(connectionList);
  }
  
  private void setNodeConnection(Vector<String> connectionList){
    Enumeration<String> e = connectionList.elements();
    while(e.hasMoreElements()){
      String[] info = StringUtil.stringToStringArray(e.nextElement());
      int nodeId = Integer.parseInt(info[0]);
      int patientId = Integer.parseInt(info[1]);
      routeManager.addConnection(nodeId, patientId);
    }
  }
  
  private void initRoute(Vector<String> nodePosList){
	Hashtable<String, PointF> nodeList = new Hashtable<String, PointF>();
    Enumeration<String> e = nodePosList.elements();
    while(e.hasMoreElements()){
      String[] info = StringUtil.stringToStringArray(e.nextElement());
      String nodeId = info[0];
      float x = Float.parseFloat(info[1]);
      float y = Float.parseFloat(info[2]);
      nodeList.put(nodeId, new PointF(x, y));
    }
    routeManager = new RouteManager();
    for(int i = 0;i < nodeList.size();i++){
      PointF point = nodeList.get(""+i);
      routeManager.addRoute(point.x, point.y);
    }
  }
  
  private void setBuildingActionNode(Vector<String> actionNode, Hashtable<String, BuildingInfo> buildingListInfo){
    Enumeration<String> e = actionNode.elements();
    while(e.hasMoreElements()){
      String[] info = StringUtil.stringToStringArray(e.nextElement());
      String actionId = info[0];
      int nodeId = Integer.parseInt(info[1]);
      BuildingInfo building = (BuildingInfo) buildingListInfo.get(actionId);
      if(building != null)
        building.setActionNode(nodeId);
    }
  }
  
  private void setBuildingActionPatientNode(Vector<String> actionPatientNode, Hashtable<String, BuildingInfo> buildingListInfo){
    Enumeration<String> e = actionPatientNode.elements();
    while(e.hasMoreElements()){
      String[] info = StringUtil.stringToStringArray(e.nextElement());
      String actionId = info[0];
      int nodeId = Integer.parseInt(info[1]);
      BuildingInfo building = (BuildingInfo) buildingListInfo.get(actionId);
      if(building != null)
        building.setPatientActionNoed(nodeId);
    }
  }
  
/*  private void setBuildingLinkPoint(Vector<String> linkPointList){
    Enumeration<String> e = linkPointList.elements();
    while(e.hasMoreElements()){
      String[] info = StringUtil.stringToStringArray(e.nextElement());
      String actionId = info[0];     
      Building building = (Building) buildingListInfo.get(actionId);
      if(building != null)
        building.readLinkPoint(info);
    }
  }
  
  
  private void setBuildingLinkCount(Vector<String> linkCountList){
    Enumeration<String> e = linkCountList.elements();
    while(e.hasMoreElements()){
      String[] info = StringUtil.stringToStringArray(e.nextElement());
      String actionId = info[0];      
      Building building = (Building) buildingListInfo.get(actionId);
      Log.d("Rokejits",actionId+"/"+building);
      if(building != null)
        building.readLinkPointCount(info);
      
    }	  
  }*/
  
  private void setBuildingPosition(Vector<String> posList, Hashtable<String, BuildingInfo> buildingListInfo){
    Enumeration<String> e = posList.elements();
    while(e.hasMoreElements()){
      String[] info = StringUtil.stringToStringArray(e.nextElement());
      float posX = Float.parseFloat(info[1]);
      float posY = Float.parseFloat(info[2]);
      Enumeration<String> e2 = buildingListInfo.keys();     
      BuildingInfo buildingInfo = buildingListInfo.get(info[0]); 
      if(buildingInfo != null)    
        buildingInfo.setPosition(posX, posY);
    }	  
  }
  
  private void initBuilding(Vector<String> infos, Hashtable<String, BuildingInfo> buildingListInfo){
	if(infos == null)
	  return;
    Enumeration<String> e = infos.elements();
    while(e.hasMoreElements()){
      initBuilding(e.nextElement(), buildingListInfo);	
    }	  
  }
  
  private void initBuilding(String infos, Hashtable<String, BuildingInfo> buildingListInfo){
	//Log.d("Rokejits", "infos = "+infos);
    String[] info = StringUtil.stringToStringArray(infos);    
    int buildingType = Integer.parseInt(info[1]);
    BuildingInfo building = buildingListInfo.get(info[0]);
    if(building == null){    	
      building = new BuildingInfo(buildingType);
      if(building != null){
    	building.setFloor(floor);
        buildingListInfo.put(info[0], building);
      }
    }    
  }
  
  /*private GameObject createBuildingObject(int type, int hospitalLevel){
    switch(type){
      case Building.GLASSDOOR:
        return new GlassDoor();  	
      case Building.LAUNDRY:
        return new Laundry();
      case Building.CHAIR:
        return new Chair();
      case Building.TRIAGE:
        return new Triage(hospitalLevel);
      case Building.WATER:
        return new Water();
      case Building.FOOD:
        return new Food();
      case Building.PLANT:
        return new Plant();
      case Building.TELEVISION:
        return new Television();
      case Building.PHARMACY:
        return new FirstPharmacy(hospitalLevel);
      case Building.QUICKTREAT:
        return new QuickTreat();
      case Building.AMBULANCE:
        return new Ambulance();
      case Building.OUTSIDE:
        return new Outside();
      case Building.BED:
        return new Bed();
      case Building.XRAY:
        return new Xray();
      case Building.TAC:
    	return new TAC();
      case Building.DENTIST:
      	return new Dentist();
      case Building.BABY_SCAN:
        return new BabyScan();
    	
    }
    return null;
	
	
  }*/
  
  public class BuildingInfo{
    private int buildingId, floor, actionNode, patientActionNode;
    private float posX,posY;
    private boolean enable;
    
    public BuildingInfo(int id){
      this.buildingId = id;	
    }    
    
    public void setActionNode(int node){
      actionNode = node;	
    }
    
    public void setPatientActionNoed(int node){
      patientActionNode = node;	
    }
    
    public int getActionNode(){
      return actionNode;	
    }
    
    public int getPatientActionNode(){
      return patientActionNode; 	
    }
    
    public int getBuildingId(){
      return buildingId; 	
    }
    
    public void setFloor(int floor){
      this.floor = floor;	
    }
    
    public int getFloor(){
      return floor; 	
    }
    
    public void setPosition(float x, float y){
      posX = x;
      posY = y;
    }
    
    public float getX(){
      return posX;	
    }
    
    public float getY(){
      return posY;	
    }
    
    public void setEnable(boolean enable){
      this.enable = enable;  	
    }
    
    public boolean isEnable(){
      return enable;	
    }   
    
    
    
  }
  
}
