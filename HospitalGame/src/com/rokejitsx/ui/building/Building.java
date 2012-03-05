package com.rokejitsx.ui.building;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import android.util.Log;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameCharactorListener;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.AnimationInfo;
import com.rokejitsx.data.xml.DataHolder;
import com.rokejitsx.data.xml.ObjectInfosReader.ObjectInfo;
import com.rokejitsx.data.xml.global.GlobalsXmlReader;
import com.rokejitsx.data.xml.global.giactionpoint.GiActionPointBuilding;
import com.rokejitsx.ui.building.elevator.Elevator;
import com.rokejitsx.ui.building.others.Food;
import com.rokejitsx.ui.building.others.Plant;
import com.rokejitsx.ui.building.others.Television;
import com.rokejitsx.ui.building.others.Water;
import com.rokejitsx.ui.building.transport.Ambulance;
import com.rokejitsx.ui.building.transport.Helicopter;
import com.rokejitsx.ui.building.waitingqueue.Outside;
import com.rokejitsx.ui.building.waitingqueue.OutsideElevator;
import com.rokejitsx.ui.building.ward.BabyScan;
import com.rokejitsx.ui.building.ward.Bed;
import com.rokejitsx.ui.building.ward.Cardiology;
import com.rokejitsx.ui.building.ward.Chemotherapy;
import com.rokejitsx.ui.building.ward.Dentist;
import com.rokejitsx.ui.building.ward.Operation;
import com.rokejitsx.ui.building.ward.Ophthalmology;
import com.rokejitsx.ui.building.ward.Physiotherapy;
import com.rokejitsx.ui.building.ward.Psychiatry;
import com.rokejitsx.ui.building.ward.QuickTreat;
import com.rokejitsx.ui.building.ward.TAC;
import com.rokejitsx.ui.building.ward.Triage;
import com.rokejitsx.ui.building.ward.UltraScan;
import com.rokejitsx.ui.building.ward.Xray;
import com.rokejitsx.ui.building.ward.pharmacy.FirstPharmacy;
import com.rokejitsx.ui.building.ward.pharmacy.UpperPhamacy;
import com.rokejitsx.ui.patient.NumberLineField;
import com.rokejitsx.ui.patient.Patient;

public abstract class Building extends GameObject implements GameCharactorListener{
   
  public final static int NONE				= 0;
  public final static int BED				= 1;
  public final static int CHAIR				= 2; 
  public final static int XRAY				= 3;
  public final static int QUICKTREAT		= 4;
  public final static int GLASSDOOR			= 5;
  public final static int OUTSIDE			= 6;
  public final static int TRIAGE			= 7;
  public final static int ELEVATOR 			= 8;
  public final static int OUTSIDE_ELEVATOR	= 9;
  public final static int LAUNDRY			= 10;
  public final static int PHARMACY			= 11;
  public final static int AMBULANCE			= 12;
  public final static int PLANT				= 13;
  public final static int CLOSET			= 14;
  public final static int WATER				= 15;
  public final static int FOOD				= 16;	
  public final static int TAC				= 17;
  public final static int GARBAGE			= 18;
  public final static int PHYSIOTHERAPY		= 19;
  public final static int OPHTHALMOLOGY		= 20;
  public final static int PSYCHIATRY		= 21;
  public final static int CHEMOTHERAPY		= 22;
  public final static int BABY_SCAN			= 23;
  public final static int DENTIST			= 24;
  public final static int CARDIOLOGY		= 25;
  public final static int OPERATION			= 26;
  public final static int CLOSET1			= 27;
  public final static int CLOSET2			= 28;
  public final static int CLOSET3			= 29;
  public final static int CLOSET4			= 30;
  public final static int ULTRASCAN			= 31;
  public final static int UPPER_PHARMACY	= 32;
  public final static int STRETCHER			= 33;
  public final static int TELEVISION		= 34;
  public final static int HELICOPTER		= 35;
  public final static int MAX_BUILDING		= 36;	
  
  private final static String[] buildingImgNameList = {    
    null,						// NONE
    MONTAGE_CAMAS,				// BED
    MONTAGE_CHAIR,				// CHAIR
    MONTAGE_XRAY,   			// XRAY
    MONTAGE_QUICKTREAT,			// QUICKTREAT
    MONTAGE_GLASSDOOR,			// GLASSDOOR
    null,						// OUTSIDE
    MONTAGE_MEDICOTRIAGEM,		// TRIAGE
    MONTAGE_PORTAS_ELEVADORES,	// ELEVATOR
    null,						// OUTSIDE_ELEVATOR;
    MONTAGE_ROUPA,				// LAUNDRY
    MONTAGE_FARMACIA_MEDICO,	// PHARMACY
    MONTAGE_AMBULANCE,			// AMBULANCE
    MONTAGE_PLANT,				// PLANT
    null,						// CLOSET
    MONTAGE_WATER,				// WATER
    MONTAGE_FOOD,				// FOOD	
    MONTAGE_TAC,				// TAC
    null,						// GARBAGE
    MONTAGE_PHYSIOTHERAPY,		// PHYSIOTHERAPY
    MONTAGE_OPHTHALMOLOGY,		// OPHTHALMOLOGY
    MONTAGE_PSYCHIATRY,			// PSYCHIATRY
    MONTAGE_CHEMOTHERAPY,		// CHEMOTHERAPY
    MONTAGE_BABYSCAN,			// BABY_SCAN
    MONTAGE_DENTIST,			// DENTIST
    MONTAGE_CARDIO,				// CARDIOLOGY
    MONTAGE_OPERATION,			// OPERATION
    null,						// CLOSET1
    null,						// CLOSET2
    null,						// CLOSET3
    null,						// CLOSET4
    MONTAGE_ULTRASCAN,			// ULTRASCAN
    null,						// UPPER_PHARMACY
    null,						// STRETCHER
    null,						// TELEVISION
    MONTAGE_HELICOPTER,			// HELICOPTER
  };
 
  protected GameObject[] visitors;
 // protected int availableLinkPoint;
  //private PointF[] linkPoint;
  private BuildingListener listener; 
  private int actionPatientNode,actionNode;
  
  private int buildingType;
  private ObjectInfo objInfo;
  private float floorX, floorY;
  
  public static final int STATE_IDLE    = 0;
  public static final int STATE_DO_WORK = 1;
  public static final int STATE_DIRTY	= 2;
  public static final int STATE_BROKED	= 3;
  
  
  private boolean isCanDirty,isCanBroke;
  private int buildingState;
  private int[] animationIds = new int[4];
  
  private int focusTileIndex;
  private Vector<float[]> gameCharactorOnReceivedPositionList;
  //protected NumberLineField numField;
  private Checker checker;
  
  
  
  public Building(int type, int visitorCount){
	super(buildingImgNameList[type]);
    this.buildingType = type;
    objInfo = ResourceManager.getInstance().getObjectInfo(getBuildingType());
    if(visitorCount > 0)
      visitors = new GameObject[visitorCount];    
    
    /*numField = new NumberLineField(2);
    numField.setPosition(0, 0);
    numField.setNumber(1);
    attachChild(numField); */   
  } 
  
  protected void setCheckPosition(float x, float y){
	if(checker == null){
      checker = new Checker(5);
      attachChild(checker);
	}	
	checker.setPosition(x, y);
  }  
  
  public boolean isCanCheck(){
    if(checker != null)
      return checker.isCanCheck();
    return false;	  
  }
  
  public boolean checked(){
    if(checker != null)
      return checker.checked();
    return false;
  }
  
  public void unChecked(){
    if(checker != null)
      checker.unChecked();
  }
  
  
  /*public void setNum(int num){
    numField.setNumber(num);	   
  }*/
  
  protected void addGameCharactorOnReceivedPosition(float x, float y){
    if(gameCharactorOnReceivedPositionList == null)
      gameCharactorOnReceivedPositionList = new Vector<float[]>();
    gameCharactorOnReceivedPositionList.add(new float[]{x , y});
  }
  
  public void setBuildingCanBroke(){
    isCanBroke = true;  	  
  }
  
  public boolean isBuildingCanBroke(){
    return isCanBroke;	  
  }
  
  public boolean isBroked(){
    return buildingState == STATE_BROKED; 	  
  }
  
  public void setBuildingCanDirty(){
    isCanDirty = true;	  
  }
  
  public boolean isBuildingCanDirty(){
    return isCanDirty;	  
  }
  
  
  
  public boolean isDirty(){
    return buildingState == STATE_DIRTY;	  
  }
  
  protected void setFocusTileIndex(int index){
    focusTileIndex = index;	  
  } 
  
  protected void setIdleAnimationId(int id){
    animationIds[STATE_IDLE] = id;	  
  }
	  
  protected void setDoWorkAnimationId(int id){
	animationIds[STATE_DO_WORK] = id;	  
  }
  
  protected void setBrokedAnimationId(int id){
    animationIds[STATE_BROKED] = id;	  
  }
  
  protected void setDirtyAnimationId(int id){
    animationIds[STATE_DIRTY] = id;	  
  }
  
  
  protected AnimationInfo onSetAnimInfoBefore(int state, AnimationInfo animInfo){
    return animInfo;	  
  }
  
  public void setState(int state){	
    this.buildingState = state;	  
    AnimationInfo animationInfo = ResourceManager.getInstance().getAnimationInfo(animationIds[state]);   
    animationInfo = onSetAnimInfoBefore(state, animationInfo);
    setAnimation(mainSprite, animationInfo);   
    onSetState(state);
  }
  
  protected void onSetState(int state){}
  
  public int getState(){
    return buildingState;	  
  }
  
  public boolean isCanReceiveVisitor(){
     return getAvailableLinkPoint() != -1;	  
  }  
  
  public void onFocus(){
    mainSprite.stopAnimation(focusTileIndex);	  
  }
  
  public void onUnFocus(){
    setState(STATE_IDLE);	  
  }
  
  
  public void setFloorPosition(float x, float y){
    floorX = x;
    floorY = y;
  }
  
  public float getFloorX(){
    return floorX;	  
  }
  
  public float getFloorY(){
    return floorY;	  
  }
  
  protected ObjectInfo getObjectInfo(){
    return objInfo;	  
  }
  
  public int getBuildingType(){
    return buildingType;	  
  }
  
  
  
  /*protected void initLinkedObjectPosition(){    
	if(objInfo == null){
	  return;	
	}
	Vector<LinkedObject> linkList = objInfo.getLinkedObjectList();
	if(linkList == null)
	  return;
    Enumeration<LinkedObject> e = linkList.elements();
    while(e.hasMoreElements()){
      LinkedObject linkObject = e.nextElement();
      String imgName = linkObject.getImageName();
      float x = linkObject.getX();
      float y = linkObject.getY();
      //int[] frameSize = IMAGE_FRAME_LIST[StringUtil.stringIndexInStringArray(imgName, IMAGE_LIST)];
      Log.d("Rokejitsx", "linkedObject = "+imgName);
      Log.d("Rokejitsx", "floorX = "+floorX);
      Log.d("Rokejitsx", "floorY = "+floorY);
      Log.d("Rokejitsx", "x = "+x);
      Log.d("Rokejitsx", "y = "+y);
      AnimatedSprite sprite = getLinkedObjectSprite(imgName);      
      x = floorX + x - sprite.getWidth();
      y = floorY + y - sprite.getHeight();
      if(sprite != null)
        setSpritePosition(sprite, x, y);
        //sprite.setPosition(x, y);        
             
    }
  }*/
  
 /* protected AnimatedSprite getLinkedObjectSprite(String imgName){
    return null;	  
  }*/
  
  /**
   * 
   * @param maxVisitor Number of available visitor
   */
  /*public Building(int maxVisitor){	
    this.maxVisitor = maxVisitor;
    this.availableLinkPoint = maxVisitor;
    visitors = new GameCharactor[maxVisitor];    
  }*/ 
  
  /*public void readLinkPointCount(String[] info){    
    int linkCount = Integer.parseInt(info[1]);   
    setLinkPointCount(linkCount);	  
  }
  
  public void readLinkPoint(String[] info){
	
    int linkPointId = Integer.parseInt(info[1]);
    float x = Float.parseFloat(info[2]);
    float y = Float.parseFloat(info[3]);   
    setLinkPoint(linkPointId, x, y); 
  }*/
  
  /*protected void setLinkPointCount(int count){
	availableLinkPoint = count;
	if(availableLinkPoint == 0)
	  return;
    linkPoint = new PointF[count];	
    visitors = new GameObject[count];
  }*/
  
  /*private void setLinkPoint(int id, float x, float y){
    linkPoint[id] = new PointF(x, y);	  
  }
  */
  public void setBuildingListener(BuildingListener listener){
    this.listener = listener;	  
  }
  
  public BuildingListener getBuildingListener(){
    return listener;	  
  }
  
  public void setActionPatientNode(int index){
    this.actionPatientNode = index;	  
  }
	  
  public int getActionPatientnode(){
    return actionPatientNode;	  
  }
  
  public void setActionNode(int index){
    this.actionNode = index;	  
  }
  
  public int getActionnode() {
    return actionNode;	
  }
  
  public GameObject isBuildingContain(float x, float y){
    if(contains(x, y))
      return this;
    return null;
  } 
  
  protected int getAvailableLinkPoint(){
	if(visitors != null){
      for(int i = 0;i < visitors.length;i++){
        if(visitors[i] == null)
          return i;
      }
	}
    return -1;
  }
  
  protected int getGameObjectInVisitorQueueIndex(GameObject gameObject){
    if(visitors != null){
      for(int i = 0;i < visitors.length;i++){
        if(gameObject.equals(visitors[i]))
          return i;
      }
	}
    return -1;	  
  }
  
  public boolean receiveCharator(GameCharactor gameChar){
	if(isDirty() || isBroked())
	  return false;
    /*if(availableLinkPoint == 0)
	  return false;
    availableLinkPoint--;*/
	int index = getAvailableLinkPoint();
	Log.d("rokejitsX", "index"+index);
	if(index == -1)
	  return false;
    if(gameChar.getCurrentBuilding() != null)
      gameChar.getCurrentBuilding().removeCharactor(gameChar);
    
    //Log.d("RokejitsX", "getAvailableLinkPoint() = "+getAvailableLinkPoint());    
	visitors[index] = gameChar;	
	gameChar.setCurrentFloor(this.getCurrentFloor());
	gameChar.setCurrentBuilding(this);	
	if(onReceive(gameChar)){
	  onGameCharactorCallback(gameChar);     
	}	
	if(listener != null)
	  listener.onReceive(this, gameChar);
    return true;		  
  }
  
  public void onGameCharactorCallback(GameCharactor gameChar){
	int index = getGameObjectInVisitorQueueIndex(gameChar);    
    setGameCharactorPositionOnReceived(gameChar,index);	  
    setGameChatactorOnReceived(gameChar);
  }
  
  protected abstract void setGameChatactorOnReceived(GameCharactor gameChar);
  
  private void setGameCharactorPositionOnReceived(GameCharactor gameChar,int index){
    float[] position = gameCharactorOnReceivedPositionList.elementAt(index);
    gameChar.setPositionOnBuildingReceived(position[0], position[1]);	  
  }
  
  
  
  protected int getMaxVisitorCount(){
    return visitors.length;	  
  }
  
  protected int getVisitorCount(){
	if(visitors == null)
	  return 0;
	int count = 0;
	for(int i = 0;i < visitors.length;i++){
	  if(visitors[i] != null)
	    count++;
	}
    return count;	  
  }
  
  public GameObject getCharactor(int index){
	if(index >= visitors.length)
	  return null;
    return visitors[index];	  
  }
  
  public void removeCharactor(GameCharactor gameChar){
	for(int i = 0;i < visitors.length;i++){
	  if(gameChar.equals(visitors[i])){
	    visitors[i] = null;
	    gameChar.setCurrentBuilding(null);
	    onRemove(gameChar);
	    if(listener != null)
	  	  listener.onRemove(this, gameChar);
	    //availableLinkPoint++;
	    break;
	  }		  
	}    
    
  }
  
  protected boolean onReceive(GameCharactor gameChar){
    return false;
  }  
  protected void onRemove(GameCharactor gameChar){}
  
  
  
  
  

  @Override
  protected void onDrawChildren(GL10 pGL, Camera pCamera) {
	super.onDrawChildren(pGL, pCamera);
    //if(this.mChildren != null && this.mChildrenVisible) { 	   	  
      if(visitors == null)
        return;
 	  for(int i = 0;i < visitors.length;i++){
 	    Patient patient = (Patient) getCharactor(i);
 	    if(patient != null && !patient.isOnPick())
 	      patient.onDraw(pGL, pCamera); 	    
 	  }
 	  
 	  drawChecker(pGL, pCamera);
 	  
 	//}
  }
  
  protected void drawChecker(GL10 pGL, Camera pCamera){
    if(checker != null)
      checker.onDraw(pGL, pCamera);
  }
  
  
  
  public void myOnApplyTransformations(GL10 pGL){
    onApplyTransformations(pGL);	  
  }
  
  
  
  public static GameObject createBuildingObject(int type, int hospitalLevel){
    switch(type){    
      case Building.BED:					//1
        return new Bed();
      case Building.CHAIR:					//2    	  
        return new Chair();
      case Building.XRAY:					//3
        return new Xray();
      case Building.QUICKTREAT:				//4
        return new QuickTreat();
      case Building.GLASSDOOR:				//5
        return new GlassDoor();
      case Building.OUTSIDE:				//6
        return new Outside();
      case Building.TRIAGE:					//7
        return new Triage(hospitalLevel);
      case Building.ELEVATOR:				//8  
        return new Elevator();
      case Building.OUTSIDE_ELEVATOR:		//9
        return new OutsideElevator();
      case Building.LAUNDRY:				//10
        return new Laundry();
      case Building.PHARMACY:				//11
        return new FirstPharmacy(hospitalLevel);
      case Building.AMBULANCE:				//12
        return new Ambulance();
      case Building.PLANT:					//13
        return new Plant();
      case Building.WATER:					//15
        return new Water();
      case Building.FOOD:					//16
        return new Food();
      case Building.TAC:					//17
      	return new TAC();
      	
      case Building.PHYSIOTHERAPY:			//19
        return new Physiotherapy();
      case Building.OPHTHALMOLOGY:			//20
        return new Ophthalmology();
      case Building.PSYCHIATRY:				//21
        return new Psychiatry();
      case Building.CHEMOTHERAPY:			//22
      return new Chemotherapy();     
      case Building.BABY_SCAN:				//23
        return new BabyScan();
      case Building.DENTIST:				//24
        return new Dentist();
      case Building.CARDIOLOGY:				//25
        return new Cardiology();
      case Building.OPERATION:				//26
        return new Operation();       
      case Building.ULTRASCAN:			    //31
        return new UltraScan();
      case Building.UPPER_PHARMACY:			//32
        return new UpperPhamacy(hospitalLevel);
      case Building.TELEVISION:				//34
        return new Television();
      case Building.HELICOPTER:				//35
        return new Helicopter();  
      
      
      
      
      
      
      
      
    	
    }
    return null;
	
	
  }
  
  public static AnimatedSprite getMachineThumbnail(int buildingType){
    AnimatedSprite machineField = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MACHINES));
    int frame = 0;
    switch(buildingType){
      case Building.PHYSIOTHERAPY:
        frame = 1;
      break;
      case Building.OPHTHALMOLOGY:
        frame = 3;
      break;
      case Building.OPERATION:
        frame = 5;
      break;
      case Building.CHEMOTHERAPY:
        frame = 7;
      break;
      case Building.TAC:
        frame = 9;
      break;
      case Building.PSYCHIATRY:
        frame = 11;
      break;
      case Building.ULTRASCAN:
        frame = 13;
      break;
      case Building.BABY_SCAN:
        frame = 15;
      break;
      case Building.CARDIOLOGY:
        frame = 17;
      break;
      case Building.DENTIST:
        frame = 19;
      break;
      case Building.XRAY:
        frame = 21;
      break;
      case Building.BED:
        frame = 23;
      break;
      case Building.QUICKTREAT:
        frame = 31;
      break;
      case Building.TRIAGE:
        frame = 32;
      break;      
    }
    machineField.setCurrentTileIndex(frame);
    return machineField;
  }
  
  public static int getMachineCost(int buildingType){
	Log.d("RokejitsX", "getMachineCost = "+buildingType);
	String tagName = null;
    switch(buildingType){
      case BED:
        tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_BED;
      break;
      case XRAY:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_XRAY;
      break;
      case QUICKTREAT:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_QUICKTREAT;
      break;
      case PLANT:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_PLANT;
      break;
      case WATER:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_WATER;
      break;
      case FOOD:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_FOOD;
      break;
      case TAC:
        tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_CAT;
      break;
      case PHYSIOTHERAPY:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_PHYSIOTHERAPY;
      break;
      case OPHTHALMOLOGY:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_OPHTHALMOLOGY;
      break;
      case PSYCHIATRY:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_PSYCHIATRY;
      break;
      case CHEMOTHERAPY:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_CHEMOTHERAPY;
      break;
      case BABY_SCAN:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_BABYSCAN;
      break;
      case DENTIST:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_DENTIST;
      break;
      case CARDIOLOGY:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_CARDIOLOGY;
      break;
      case OPERATION:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_OPERATION;
      break;
      case ULTRASCAN:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_ULTRA_SCAN;
      break;
      case TELEVISION:
    	tagName = GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_TELEVISION;
      break;
    }    
    DataHolder dHolder = ResourceManager.getInstance().getGlobalData(tagName);
    return (int) dHolder.getDouble(GiActionPointBuilding.PRICE);	  
  }

  
  public static String getMachineDescription(int buildingType){
    switch(buildingType){
      case BED:
        return "This new and improved hospital bed has 25% less bed bugs than last year's model!";
      case XRAY:
        return "It's 10 times more X-treme than the Y-ray!";
      case QUICKTREAT:
          return "Gets patients in and out before you can say 'malpractice'.";
      case PLANT:
          return "Scientifically engineered to stay green and leafy under artificial hospital lights.";
      case WATER:
          return "The finest in imported tap water; it keeps patients content longer. Now with more fish.";
      case FOOD:
          return "These prepackaged meals taste almost like real food.";
      case TAC:
          return "Contains three settings: regular, delicate, and permanent press.";
      case PHYSIOTHERAPY:
          return "Gives patients a run for their money.";
      case OPHTHALMOLOGY:
          return "Make sure patients sign the fine print before treatment.";
      case PSYCHIATRY:
          return "Dr. Birdbrain has a flighty but effective method for uplifting patient spirits.";
      case CHEMOTHERAPY:
          return "A dermatologic treatment that smoothes wrinkles, clears pores, and smells like pine!";
      case BABY_SCAN:
          return "Send patients to Dr. Juan and they will always leave pregnant!";
      case DENTIST:
          return "We never hear patients complain about Dr. Molar, but they sure do mumble a lot.";
      case CARDIOLOGY:
          return "If a patient complains of chest pains and they haven't even seen the bill yet, send them here.";
      case OPERATION:
          return "He slices; he dices; patients won't know what hit them!";
      case ULTRASCAN:
          return "No one really knows how to use it. But it's so high tech, that we trust its diagnosis.";
      case TELEVISION:
          return "It's the ultimate plug-in drug! It keeps patients pacified for longer periods.";
    }	  
    return null;
  }
  
  public static String getDoctorName(int buildingType){
    switch(buildingType){
      case QUICKTREAT:
        return "Dr. Swift";
      case PHYSIOTHERAPY:
          return "Dr. Hale";
      case OPHTHALMOLOGY:
          return "Dr. Buenavista";
      case PSYCHIATRY:
          return "Dr. Birdbrain";
      case BABY_SCAN:
          return "Dr. Don Juan";
      case DENTIST:
          return "Dr. Molar";
      case CARDIOLOGY:
          return "Dr. Lionhart";
      case OPERATION:
          return "Dr. Hackenslash";      
    }  	   
    return null;
  }
  
  public static String getBuildingName(int buildingType){	  
    switch(buildingType){
      case Building.PHYSIOTHERAPY:
        return "PHYSIOTHERAPY";     
      case Building.OPHTHALMOLOGY:
    	return "OPHTHALMOLOGY";
      case Building.OPERATION:
    	return "OPERATION";
      case Building.CHEMOTHERAPY:
        return "CHEMOTHERAPY";
      case Building.TAC:
    	return "TAC";
      case Building.PSYCHIATRY:
    	return "PSYCHIATRY";
      case Building.ULTRASCAN:
    	return "ULTRASCAN";
      case Building.BABY_SCAN:
    	return "BABY_SCAN";
      case Building.CARDIOLOGY:
    	return "CARDIOLOGY";
      case Building.DENTIST:
        return "DENTIST";
      case Building.XRAY:
    	return "XRAY";
      case Building.BED:
    	return "BED";
      case Building.QUICKTREAT:
    	return "QUICKTREAT";
      case Building.PLANT:
      	return "PLANT";
      case Building.FOOD:
      	return "FOOD";
      case Building.WATER:
      	return "WATER";
      case Building.TELEVISION:
      	return "TELEVISION";
            
    }
	  
    return "";
  }
  
  
  
}
