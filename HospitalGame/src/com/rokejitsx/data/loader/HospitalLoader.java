package com.rokejitsx.data.loader;

import java.io.IOException;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.route.RouteManager;
import com.rokejitsx.data.xml.BuildingInfo;
import com.rokejitsx.data.xml.HospitalLevelReader;
import com.rokejitsx.save.GameStatManager;
import com.rokejitsx.ui.building.Building;

public class HospitalLoader extends Loader{
  private int hospitalId;
  private BuildingInfo[] buildingInfoList;
  private RouteManager[] routeManagerList;
  private int maxFloor;
  //private boolean needMapScreen;
  public HospitalLoader(int hospitalId, LoaderListener listener){
    super(listener, 100);	 
    this.hospitalId = hospitalId;
  }

  @Override
  protected void onLoad() {
    ResourceManager.getInstance().loadLevel(hospitalId);
    Vector<BuildingInfo> infoList = new Vector<BuildingInfo>();        
    int[] levelInfo = ResourceManager.getInstance().getHospitalXmlIds(hospitalId);   
    maxFloor = levelInfo.length; 
    routeManagerList = new RouteManager[maxFloor];
    for(int i = 0;i < maxFloor;i++){
      HospitalLevelReader hospitalLevelReader = new HospitalLevelReader(levelInfo[i], i);
      try {
        hospitalLevelReader.startParse();
        hospitalLevelReader.initialBuilding();
	  } catch (XmlPullParserException e) {		
		e.printStackTrace();
	  } catch (IOException e) {		
     	e.printStackTrace();
	  }      
	  hospitalLevelReader.getFixedBuilding(infoList);
	  hospitalLevelReader.getDropBuilding(infoList);
	  hospitalLevelReader.getEquipmentBuilding(infoList);
	  routeManagerList[i] = hospitalLevelReader.getRoute();
	}
    /*buildingInfoList = new BuildingInfo[infoList.size()];
    infoList.copyInto(buildingInfoList);
    Log.d("Rokejits", "hasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
    for(int i = 0;i <buildingInfoList.length;i++){
      Log.d("Rokejits", "load = "+buildingInfoList[i].getActId() + " / "+buildingInfoList[i].getBuildingId());	
    }
    Log.d("Rokejits", "hasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss2");*/
    buildingInfoList = GameStatManager.getInstance().loadBuilding();
    if(buildingInfoList == null){
      
      buildingInfoList = new BuildingInfo[infoList.size()];
      infoList.copyInto(buildingInfoList);
      GameStatManager.getInstance().saveBuilding(buildingInfoList);
    }else{
         	
    }
	
  }
  
  /*public boolean isNeedMapScreen(){
    return needMapScreen;	  
  }*/
  
  public BuildingInfo[] getBuildingInfoList(){
    return buildingInfoList;	  
  }
  
  public RouteManager[] getRouteManagerList(){
    return routeManagerList;	  
  }
  
  public int getHospitalId(){
    return hospitalId;	  
  }
  
  public int getMaxFloor(){
    return maxFloor;	  
  }
  
  
}
