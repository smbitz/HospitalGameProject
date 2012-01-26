package com.rokejitsx.ui.hospital;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.util.FloatMath;
import android.util.Log;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameCharactorListener;
import com.rokejitsx.data.GameCharatorFloorChangedListener;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.route.RouteManager;
import com.rokejitsx.data.xml.AnimationInfo;
import com.rokejitsx.data.xml.HospitalLevelReader.BuildingInfo;
import com.rokejitsx.data.xml.ObjectInfosReader.ObjectInfo;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.building.BuildingListener;
import com.rokejitsx.ui.building.Chair;
import com.rokejitsx.ui.building.elevator.Elevator;
import com.rokejitsx.ui.building.elevator.ElevatorFloorSelector.ElevatorSelectorListener;
import com.rokejitsx.ui.building.transport.Ambulance;
import com.rokejitsx.ui.building.transport.Helicopter;
import com.rokejitsx.ui.building.transport.Transporter;
import com.rokejitsx.ui.building.waitingqueue.Outside;
import com.rokejitsx.ui.building.waitingqueue.OutsideElevator;
import com.rokejitsx.ui.building.ward.Triage;
import com.rokejitsx.ui.building.ward.Ward;
import com.rokejitsx.ui.building.ward.WardListener;
import com.rokejitsx.ui.building.ward.pharmacy.FirstPharmacy;
import com.rokejitsx.ui.building.ward.pharmacy.FirstPharmacyListener;
import com.rokejitsx.ui.building.ward.pharmacy.Pharmacy;
import com.rokejitsx.ui.building.ward.pharmacy.PharmacyListener;
import com.rokejitsx.ui.item.Dust;
import com.rokejitsx.ui.item.InfoPlate;
import com.rokejitsx.ui.item.Item;
import com.rokejitsx.ui.nurse.Nurse;
import com.rokejitsx.ui.nurse.NurseListener;
import com.rokejitsx.ui.patient.Patient;
import com.rokejitsx.ui.patient.PatientListener;

public class Hospital extends Entity implements WardListener, PatientListener, GameCharactorListener, BuildingListener, ElevatorSelectorListener, GameCharatorFloorChangedListener, FirstPharmacyListener, PharmacyListener, NurseListener, HospitalUIListener {
  private RouteManager[] routeManagerList;  
  private Triage analizeWard;    
  private Chair waitingChair;
  private Outside frontWaitingQueue;
  private FirstPharmacy firstInfoWard;
  private Pharmacy[] infowardList;
  /*private AmbulanceCharactor ambCharator;
  private AmbulanceBuilding ambBuilding;*/
  private Elevator[] elevatorList;
  private OutsideElevator[] elevatorWaitingQueueList;
  private int patientCount;  
  //private long ambulanceTime;
  private Vector<Patient> patientList,healedPatientList;
  private Vector<GameObject> buildingList;
  
  private Nurse nurse;
  private Vector<GameObject> nurseQueue;
  private Patient pickedPatient;
  private Building collideBuilding;
  
  private int patientQueueNumber = 1;
  
  private int currentFloor,maxFloor;
  
  private Vector<FloorChangeListener> floorListener;
  private HospitalListener hospitalListener;  
  private Elevator selectedElevator;
  
  private Item[] itemOnDesk;
  
  
  private Ambulance ambulance;
  private Helicopter helicopter;
  
  
  private Vector<Patient> patientAlmostDeadList;
  
  
  
  public Hospital(int maxFloor){
	this.maxFloor = maxFloor;
	itemOnDesk = new Item[5];
	patientList = new Vector<Patient>();
	healedPatientList = new Vector<Patient>();
	patientAlmostDeadList = new Vector<Patient>();
	buildingList = new Vector<GameObject>();
	routeManagerList = new RouteManager[maxFloor];
	infowardList = new Pharmacy[maxFloor];
	elevatorList = new Elevator[maxFloor];
	elevatorWaitingQueueList = new OutsideElevator[maxFloor];
    //loadRoute();
   // loadWard();
    //initialNurse();
    //initAmbulance();
    setPosition(0, 0);    
    //this.setFloor(0);
    /*for(int i = 0;i < routeManager.getConnectionCount();i++){
      int[] routeIndex = routeManager.getConnection(i);
      Line line = new Line(routeManager.getRouteX(routeIndex[0]), routeManager.getRouteY(routeIndex[0]),
    		               routeManager.getRouteX(routeIndex[1]), routeManager.getRouteY(routeIndex[1]));
      line.setColor(0, 1, 0);
      attachChild(line);
    }*/
    //routeManager.findPath(11, 12, ambCharator);
   /* Patient patient = new Patient(0);
    patient.setPosition(routeManager.getRouteX(0), routeManager.getRouteY(0));    
    attachChild(patient);
    routeManager.findPath(0, 5, patient);*/
    //patientComein();
  } 
  
  public void startPlay(){
    if(ambulance != null)
      ambulance.comeIn();
    if(helicopter != null)
      helicopter.comeIn();
  }
  
  private void showElevatorFloorSelector(Elevator selectedElevator){
	this.selectedElevator = selectedElevator;
	if(maxFloor == 2){
	  if(selectedElevator.getCurrentFloor() == 0)
	    onElevatorFloorSelected(1);
	  else
		onElevatorFloorSelected(2);
	  
	}else{
	  hospitalListener.onShowElevetorSelector(selectedElevator.getX() + selectedElevator.getWidth()/2, selectedElevator.getY());
	}
    //this.elevatorFloorSelector.setVisible(true);	  
  }
  
  private void hideElevatorFloorSelector(){
    //this.elevatorFloorSelector.setVisible(false);
    hospitalListener.onHideElevetorSelector();
  }
  
  
  public void setFloor(int floor){
    currentFloor = floor;
    if(bg != null)
    for(int i = 0;i < bg.length;i++){
      if(i == currentFloor)
        bg[i].setVisible(true);
      else
    	bg[i].setVisible(false);
    }
    updateFloorChanged(floor);
    //Enumeration<Item> e = itemOnDesk.elements();
    
    for(int i = 0; i < itemOnDesk.length;i++){
      Item item = itemOnDesk[i];   
      if(item == null)
        continue;
      if(!nurseQueue.contains(item) && !item.equals(nurse.getItemToPick())){
    	Pharmacy infoWard = (Pharmacy) item.getOwner();
        if(infoWard.getCurrentFloor() != currentFloor){
          Pharmacy infoWard2 = infowardList[currentFloor];
          if(infoWard2 != null){
            infoWard.removeItemFromQueue(item);
            infoWard2.receiveItemToQueue(item);
          }
        }  	  
      }
    }
  }
  
  public int getFloor(){
    return currentFloor;	  
  }
  
  public void setHospitalListener(HospitalListener listener){
    this.hospitalListener = listener;	  
  }
  
  public void addFloorChangeListener(FloorChangeListener listener){
    if(floorListener == null)
      floorListener = new Vector<FloorChangeListener>();
    floorListener.add(listener);
  }
  
  public void removeFloorChangeListener(FloorChangeListener listener){
    if(floorListener == null)
      return;
    floorListener.remove(listener);
  }
  
  private void updateFloorChanged(int floor){
    if(floorListener == null)
      return;
    Enumeration<FloorChangeListener> e = floorListener.elements();
    while(e.hasMoreElements()){
      FloorChangeListener listener = e.nextElement();
      listener.onFloorChanged(floor);
    }
  }
  
  
  
  private int[][] wardCourseInfo = {
    { Building.QUICKTREAT /*Building.ULTRASCAN*//*Building.CARDIOLOGY*//*Building.BABY_SCAN*/},
    null,
    {Building.PSYCHIATRY, Building.PHYSIOTHERAPY},
    {Building.QUICKTREAT, Building.XRAY, Building.OPERATION, Building.DENTIST},
    {Building.PSYCHIATRY, Building.OPERATION, Building.TAC},
    {Building.DENTIST, Building.QUICKTREAT, Building.CHEMOTHERAPY, Building.OPHTHALMOLOGY},
    {Building.QUICKTREAT, Building.XRAY, Building.TAC, Building.CARDIOLOGY, Building.OPERATION}
  };
  
  
  private int[] patinetIdList = {0, 1, 2, 3, 4, 8, 9,10};
  private Random random = new Random();
  public void patientComein(){
	int pId = patinetIdList[Math.abs(random.nextInt()) % patinetIdList.length];
    Patient patient = new Patient(pId);	  
    if(getFloor() != 0)
      patient.setVisible(false);    
    patient.setGameCharactorPosition(routeManagerList[0].getRouteX(0), routeManagerList[0].getRouteY(0));
    /*patient.onSetFace(GameCharactor.FACE_UP_R);
    attachChild(patient);
    float x = 500;
    float y = 200;
    patient = new Patient(pId, hId);
    patient.setGameCharactorPosition(x, y);
    patient.onSetFace(GameCharactor.FACE_UP_L);
    attachChild(patient);
    
    y +=100;
    patient = new Patient(pId, hId);
    patient.setGameCharactorPosition(x,y);
    patient.onSetFace(GameCharactor.FACE_DOWN_R);
    attachChild(patient);
    y +=100;
    patient = new Patient(pId, hId);
    patient.setGameCharactorPosition(x, y);
    patient.onSetFace(GameCharactor.FACE_DOWN_L);
    attachChild(patient);
    
    
    y +=100;
    patient = new Patient(pId, hId);
    patient.setGameCharactorPosition(x, y);
    //patient.onSetFace(GameCharactor.FACE_DOWN_L);
    patient.setPickable(true);
    attachChild(patient);*/
    
    
    
    patient.setHealthLevel(25);    
    
    patient.setGameCharactorFloorChangeListener(this);
    patient.addGameCharactorListener(this);
    patient.addWardHealingRoute(Building.TRIAGE, 0);
    InfoPlate infoPlate = new InfoPlate(patientQueueNumber);    
    infoPlate.setOwner(patient);
    
    
    //infoPlate.setGameCharactorFloorChangeListener(this);
    //addFloorChangeListener(infoPlate);
    
    int[] random = wardCourseInfo[hospitalId];
    if(random == null){
      patient.addWardHealingRoute(Building.BED, getWardFloor(Building.BED)).addItem(infoPlate);	
    }else{
      Random r = new Random();
      int id = Math.abs(r.nextInt()) % random.length;
      patient.addWardHealingRoute(random[id], getWardFloor(random[id])).addItem(infoPlate);
      patient.addWardHealingRoute(Building.BED, getWardFloor(Building.BED));
    }
    
    /*patient.addWardHealingRoute(Building.QUICKTREAT, 0).addItem(infoPlate);
    patient.addWardHealingRoute(Building.BED, 0).addItem(infoPlate);*/
    	
    patient.setShowFloorNumberInBubbleBox(true);
    
    
    
    patient.setPatientListener(this);
    patient.setQueue(patientQueueNumber++);
    if(patientQueueNumber > 15)
      patientQueueNumber = 1;
    patientList.add(patient);
    attachChild(patient);   
    
    addFloorChangeListener(patient);
    routeManagerList[0].findPath(0, 2, patient);
    
    
    
  }
  
  public int getWardFloor(int buildingType){
       
    for(int i = 0;i < buildingList.size();i++){
      GameObject obj = buildingList.elementAt(i);
      if(!(obj instanceof Ward))
        continue;
      Ward ward = (Ward) obj;
      if(ward.getBuildingType() == buildingType)
        return ward.getCurrentFloor();
    }
    return -1;	
  }
 
  
  private void addNurseQueue(GameObject obj){	
    nurseQueue.add(obj);    
    startNurseQueue();
  }
  
  private void startNurseQueue(){
    if(nurse.getGameCharactorState() != Nurse.STATE_IDLE)
      return;
    nurse.setGameCharactorState(nurse.STATE_PREPARE);
    nextQueue();
  }
  
  private void nextQueue(){
	Log.d("nextQueue", ""+nurseQueue.size());
    if(nurseQueue.size() == 0 || nurse.isCleaning())
      return;    
    GameObject toTarget = nurseQueue.elementAt(0);
    Log.d("Rokejits", "toTarget "+toTarget.getClass());
    if(toTarget instanceof Item){
      Item item = (Item) toTarget;	
      Log.d("InfomationWard", "ownerrrrrr1 "+item.getOwner().getClass());
      Log.d("InfomationWard", "ownerrrrrr2 "+item.getPatientNumber());
      Pharmacy infoWard = (Pharmacy) item.getOwner();
      Log.d("InfomationWard", "ownerrrrrr "+infoWard.getCurrentFloor());
      if(infoWard.getCurrentFloor() != nurse.getCurrentFloor()){
        toTarget = elevatorList[nurse.getCurrentFloor()];	
        Log.d("InfomationWard", "ownerrrrrr3 ");
      }else{
    	Log.d("InfomationWard", "ownerrrrrr4 ");  
        nurseQueue.remove(0);
      }
      Log.d("InfomationWard", "ownerrrrrr5 "+nurseQueue.size());  
      
    }else{
      if(toTarget.getCurrentFloor() != nurse.getCurrentFloor()){
        toTarget = elevatorList[nurse.getCurrentFloor()];	
      }else{
        nurseQueue.remove(0);
      }
    }
    int toRouteIndex = -1;
    int fromRouteIndex = nurse.getCurrentRouteIndex();
    if(toTarget instanceof Building){
      Building building = (Building) toTarget;      
      nurse.setCurrentBuilding(building);
      nurse.addGameCharactorListener(building);
      toRouteIndex = ((Building)toTarget).getActionnode();
      //nurse.setCurrentFloor(toTarget.getCurrentFloor());
    }else if(toTarget instanceof Item){
      Item item = (Item) toTarget;
      Pharmacy infoWard = (Pharmacy) item.getOwner();
      nurse.setItemToPick(item);
      nurse.addGameCharactorListener(infoWard);
      nurse.setCurrentBuilding(infoWard);
      toRouteIndex = infoWard.getActionnode();
    }
    
    nurse.addGameCharactorListener(this);    
    Log.d("move", fromRouteIndex +"/" + toRouteIndex + "/" + nurse.getCurrentFloor()+ "/"+ routeManagerList.length);
    if(toRouteIndex != -1)
      routeManagerList[nurse.getCurrentFloor()].findPath(fromRouteIndex, toRouteIndex, nurse);
  }
  
  public void initialNurse(){
    nurse = new Nurse();    
    nurse.setGameCharactorPosition(routeManagerList[0].getRouteX(firstInfoWard.getActionnode()), 
    		                       routeManagerList[0].getRouteY(firstInfoWard.getActionnode()));
    nurse.setGameCharactorFloorChangeListener(this);
    nurse.setCurrentBuilding(firstInfoWard);
    nurse.setNurseListener(this);
    addFloorChangeListener(nurse);
    attachChild(nurse);
    nurseQueue = new Vector<GameObject>();
  }
  
  
  /*private void initAmbulance(){
    ambBuilding = new AmbulanceBuilding();	
    ambBuilding.setPosition(routeManager.getRouteX(12) - ambBuilding.getWidth()/2, routeManager.getRouteY(12) - ambBuilding.getHeight());
    ambBuilding.setBuildingListener(this);
    ambBuilding.setVisible(false);
    ambBuilding.setShow(false);
    
    ambCharator = new AmbulanceCharactor();
    ambCharator.setPosition(routeManager.getRouteX(11) - ambBuilding.getWidth()/2, routeManager.getRouteY(11) - ambBuilding.getHeight());
    ambCharator.addGameCharactorListener(this);
    ambCharator.setShow(true);
    buildingList.add(ambBuilding);
    
    
    this.addFloorChangeListener(ambBuilding);
    this.addFloorChangeListener(ambCharator);
    
    attachChild(ambBuilding);
    attachChild(ambCharator);
  }*/
  
  
  /*private void loadWard(){
    waitingChair = new Chair();
    waitingChair.setPosition(350, 300);
    waitingChair.setEnterRoute(2);
    waitingChair.setExitRoute(2);
    
    frontWaitingQueue = new Outside();
    frontWaitingQueue.setPosition(410, 470);    
    frontWaitingQueue.setExitRoute(1);
    
    
    analizeWard = new Triage();
    analizeWard.setPosition(580, 380);
    analizeWard.setWardListener(this);
    analizeWard.setExitRoute(10);
    
    infowardList = new Pharmacy[maxFloor];
    
    firstInfoWard = new FirstPharmacy();
    firstInfoWard.setFirstInfomationListener(this);
    firstInfoWard.setEnterRoute(4);
    //infoWard.setExitRoute(5);
    firstInfoWard.setPosition(50, 270);
    firstInfoWard.setFirstInfomationListener(this);
    infowardList[0] = firstInfoWard;
    for(int i = 1;i < maxFloor;i++){
      Pharmacy infoWard = new Pharmacy();
      infoWard.setPharmacyListener(this);
      infoWard.setCurrentFloor(i);
      infoWard.setEnterRoute(4);      
      infoWard.setPosition(50, 270);
      buildingList.add(infoWard);
      infowardList[i] = infoWard;
      this.addFloorChangeListener(infoWard);
      attachChild(infoWard);
    }
    
    this.addFloorChangeListener(waitingChair);
    this.addFloorChangeListener(frontWaitingQueue);
    this.addFloorChangeListener(analizeWard);
    this.addFloorChangeListener(firstInfoWard);
    
    buildingList.add(waitingChair);
    buildingList.add(frontWaitingQueue);
    buildingList.add(analizeWard);
    buildingList.add(firstInfoWard);   
    
    
    
    attachChild(waitingChair);
    attachChild(frontWaitingQueue);
    attachChild(analizeWard);
    attachChild(firstInfoWard);
    
    for(int i = 0;i < maxFloor;i++){
      QuickTreat healingWard = new QuickTreat();
      healingWard.setCurrentFloor(i);
      healingWard.setPosition(200, 110);
      healingWard.setWardListener(this);
      healingWard.setEnterRoute(5);
      healingWard.setExitRoute(5);
        
      QuickTreat healingWard_1 = new QuickTreat();
      healingWard_1.setCurrentFloor(i);
      healingWard_1.setPosition(600, 110);
      healingWard_1.setWardListener(this);
      healingWard_1.setEnterRoute(9);
      healingWard_1.setExitRoute(9);
       
      HealingWard2 healingWard2 = new HealingWard2();
      healingWard2.setCurrentFloor(i);
      healingWard2.setPosition(400, 110);
      healingWard2.setWardListener(this);
      healingWard2.setEnterRoute(7);
      healingWard2.setExitRoute(7);	
      
      this.addFloorChangeListener(healingWard);
      this.addFloorChangeListener(healingWard_1);
      this.addFloorChangeListener(healingWard2);
      
      buildingList.add(healingWard);
      buildingList.add(healingWard_1);
      buildingList.add(healingWard2);
      
      attachChild(healingWard);
      attachChild(healingWard_1);
      attachChild(healingWard2);
    }
    
    
    
    elevatorList = new Elevator[maxFloor];
    elevatorWaitingQueueList = new ElevatorWaitingQueue[maxFloor];
    for(int i = 0;i < maxFloor; i++){
      Elevator elevator = new Elevator();
      
      ElevatorWaitingQueue elevatorWaitingQueue = new ElevatorWaitingQueue();
      
      elevator.setCurrentFloor(i);
      elevatorWaitingQueue.setCurrentFloor(i);
      
      elevator.setEnterRoute(14);
      elevator.setExitRoute(14);	
      
      elevatorWaitingQueue.setEnterRoute(14);
      elevatorWaitingQueue.setExitRoute(14);
      
      elevator.setBuildingListener(this);
      elevator.setPosition(70, 170);
      elevatorWaitingQueue.setPosition(150, 170);      
      
      addFloorChangeListener(elevator);
      addFloorChangeListener(elevatorWaitingQueue);
      
      buildingList.add(elevator);
      buildingList.add(elevatorWaitingQueue);
      
      attachChild(elevator);
      attachChild(elevatorWaitingQueue);
      
      elevatorList[i] = elevator;
      elevatorWaitingQueueList[i] = elevatorWaitingQueue;
    }
    
    Enumeration<Building> e = buildingList.elements();
    while(e.hasMoreElements()){
      Building b = e.nextElement();
      if(b.getCurrentFloor() != getFloor())
        b.setVisible(false);
    }
    
    
    
  }*/
  
  
  public void initRouteManager(RouteManager[] list){
    routeManagerList = list;	  
  }
  
  private BitmapTextureAtlas bgTextureAtlas;
  private TextureRegion[] bgTextureRegion;
  private Sprite[] bg;
  private float[][] bgOffset = {
    {-80, 0},
    {-80, 165},		  
    {-80, 340},
    {-80, 505}
  };
  
  public void initialBg(int hospital, int maxFloor){
	bgTextureRegion = new TextureRegion[maxFloor];
	bg = new Sprite[maxFloor];
	bgTextureAtlas = new BitmapTextureAtlas(2048, 4096, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/hospitals/");
	String imgName = "hospital%h_piso%l_0.png";
	int x = 0;
	int y = 0;
	imgName = imgName.replaceAll("%h", ""+(hospital + 1));
	for(int i = 0;i < maxFloor;i++){
	  String img = 	imgName.replaceAll("%l", ""+ i);
	  bgTextureRegion[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bgTextureAtlas, HospitalGameActivity.getGameActivity(), img, x, y);
	  x += bgTextureRegion[i].getWidth();
	  if(i % 2 == 1){
	    x = 0;
	    y += bgTextureRegion[i].getHeight(); 
	  }
	}
	//this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas);  	
	HospitalGameActivity.getGameActivity().getEngine().getTextureManager().loadTexture(bgTextureAtlas);
	
	for(int i = 0;i < bg.length;i++){
	  
	  float[] offset = bgOffset[i];
	  bg[i] = new Sprite(0 + offset[0], -600 + offset[1], bgTextureRegion[i]);
	  attachChild(bg[i], 0);
	}
	
  }
  
  
  private int hospitalId;
  public void loadBuilding(int hospital, BuildingInfo[] buildingInfoList){    	  
	  hospitalId = hospital;
	
	  
	  for(int i= 0;i < buildingInfoList.length;i++){
		BuildingInfo buildingInfo = buildingInfoList[i];
		
		GameObject building = Building.createBuildingObject(buildingInfo.getBuildingId(), hospital);
		
		
		//Log.d("RokejitsX", "create building1 = "+buildingInfo.getBuildingId()+"************************************************");
	    if(building == null)
	      continue;
	    /*ObjectInfo objectInfo = ResourceManager.getInstance().getObjectInfo(buildingInfo.getBuildingId());
	    Log.d("RokejitsX", "create building2 = "+building.getClass()+"************************************************");
	    Log.d("RokejitsX", "create building3 = "+objectInfo+"************************************************");*/
	    addFloorChangeListener(building);
		//GameObject building = buildingList[i];
		if(building != null){
		  buildingList.addElement(building);
		  building.setCurrentFloor(buildingInfo.getFloor());
		  building.setGameObjectPositionAsCenter(buildingInfo.getX(), buildingInfo.getY());
		  /*if(objectInfo != null){
		    float x = buildingInfo.getX() - objectInfo.getFloorX() - building.getWidth()/2;
		    float y = buildingInfo.getY() - objectInfo.getFloorY() - building.getHeight();
		    building.setPosition(x, y);
		  }else{
	        building.setGameObjectPositionAsCenter(buildingInfo.getX(), buildingInfo.getY());  
		  }*/
		  
	      attachChild(building);
	      
	      if(building instanceof Building){
	        Building b = (Building) building;
	        b.setActionNode(buildingInfo.getActionNode());
	        b.setActionPatientNode(buildingInfo.getPatientActionNode());
	        //b.setBuildingListener(this);
	      }
	      
	      if(building instanceof Ward){
	        Ward ward = (Ward) building;
	        ward.setWardListener(this);
	      }
	      
	      if(building instanceof Triage){
	        analizeWard = (Triage) building;
	        analizeWard.setWardListener(this);
	      }else if(building instanceof Chair){
	    	waitingChair = (Chair) building;  
	      }else if(building instanceof Outside){
	    	frontWaitingQueue = (Outside) building;
	      }else if(building instanceof FirstPharmacy){
	    	firstInfoWard = (FirstPharmacy) building; 
	    	firstInfoWard.setFirstInfomationListener(this);
	    	firstInfoWard.setPharmacyListener(this);	    	
	    	infowardList[0] = firstInfoWard;
	      }else if(building instanceof Pharmacy){	    	
		  	Pharmacy phamacy = (Pharmacy) building;	    	
		  	phamacy.setPharmacyListener(this);	    	
		    infowardList[phamacy.getCurrentFloor()] = phamacy;
		  }else if(building instanceof Ambulance){
	        ambulance = (Ambulance) building;
	        ambulance.setBuildingListener(this);
	       // amb.initPosition();
	       // building.setVisible(false);   	  
	      }else if(building instanceof Helicopter){
	        helicopter = (Helicopter) building;
	        helicopter.setBuildingListener(this);
	      }else if(building instanceof Elevator){
	    	((Elevator) building).setBuildingListener(this);
	        elevatorList[building.getCurrentFloor()] = (Elevator) building;	  
	      }else if(building instanceof OutsideElevator){
	    	elevatorWaitingQueueList[building.getCurrentFloor()] = (OutsideElevator) building;	  
	      }	     
		}
	  }
	  //routeManager = reader.getRoute();	
  }
  
  /*public void loadRoute(){  
    routeManager = new RouteManager();
    routeManager.addRoute(800, 530);//0
    routeManager.addRoute(400, 530);//1
    routeManager.addRoute(400, 370);//2 waiting chair
    routeManager.addRoute(200, 370);//3
    routeManager.addRoute(200, 270);//4 info ward
    routeManager.addRoute(200, 170);//5 healing ward
    routeManager.addRoute(400, 270);//6
    routeManager.addRoute(400, 170);//7 healing ward2
    routeManager.addRoute(600, 270);//8
    routeManager.addRoute(600, 170);//9 healing ward
    routeManager.addRoute(600, 370);//10 analize ward   
    
    
    routeManager.addRoute(800, 600);//11
    routeManager.addRoute(400, 600);//12
    routeManager.addRoute(0, 600);//13
    routeManager.addRoute(150, 170);//14
    
    
    routeManager.addConnection(0, 1);
    routeManager.addConnection(1, 2);
    routeManager.addConnection(2, 3);
    routeManager.addConnection(3, 4);
    routeManager.addConnection(4, 5);
    routeManager.addConnection(4, 6);
    routeManager.addConnection(5, 14);
    routeManager.addConnection(6, 7);
    routeManager.addConnection(6, 8);
    routeManager.addConnection(8, 9);
    routeManager.addConnection(8, 10);    
    
    routeManager.addConnection(11, 12);
    routeManager.addConnection(12, 13);
    
    
  }*/

  
  private float patientComeInTime = 0;
  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {
	/*if(ambulanceTime > 0){	  
	  if(ambulanceTime + 8000 <= System.currentTimeMillis()){
	    ambulanceTime = 0;
	    ambCharator.setPosition(routeManager.getRouteX(11) - ambBuilding.getWidth()/2, routeManager.getRouteY(11) - ambBuilding.getHeight());
	    if(getFloor() == 0)
	      ambCharator.setVisible(true);
	    routeManager.findPath(11, 12, ambCharator);
	  }
	}*/
	if(patientComeInTime <= 0){
      if(patientCount < 1){				
	    patientComein();
	    patientCount++;
	  }
      patientComeInTime = 5;
	}else{
	  patientComeInTime -= pSecondsElapsed;	
	}
	super.onManagedUpdate(pSecondsElapsed);
	
	Enumeration<Patient> e = patientList.elements();
	while(e.hasMoreElements()){
      Patient patient = e.nextElement();
      //Log.d("RokejitsX", "patient.getHealthLevel() = "+patient.getHealthLevel());
      if(patient.getHealthLevel() <= 20 && !patientAlmostDeadList.contains(patient)){
        patientAlmostDeadList.add(patient);
        //add doughnut to hospital UI
        hospitalListener.addPatientDoughnut(patient);
      }else if(patient.getHealthLevel() > 20 && patientAlmostDeadList.contains(patient)){
        patientAlmostDeadList.remove(patient);
        //remove doughnut from hospital UI
        hospitalListener.removePatientDoughnut(patient);
      }
	}
	
  }
  
  
  Comparator<? super GameObject> objComparator = new Comparator<GameObject>(){	 
     @Override
	 public int compare(GameObject lhs, GameObject rhs) {
    	float y1 = lhs.getY(); 
    	float y2 = rhs.getY();    	
    	if(y1 == y2)
    	  return 0;
    	if(y1 < y2)
    	  return -1;
		return 1;
	 } 
  };
  
  
  
 /* @Override
  protected void doDraw(GL10 pGL, Camera pCamera) {
	GameObject[] list = new GameObject[getChildCount()];
	for(int i = 0;i < getChildCount();i++){
	  list[i] = ((GameObject) getChild(i));	
	}
	Arrays.sort(list, objComparator);
	for(int i = 0;i < list.length;i++){
	  (list[i]).onDraw(pGL, pCamera);	
	}
	//super.doDraw(pGL, pCamera);
  }*/

  @Override
  protected void onDrawChildren(GL10 pGL, Camera pCamera) {
  // TODO Auto-generated method stub
    sortChildren();
    if(this.mChildren != null && this.mChildrenVisible) {
	  //this.onManagedDrawChildren(pGL, pCamera);
      final ArrayList<IEntity> children = this.mChildren;
	  final int childCount = children.size();
	  for(int i = 0; i < childCount; i++) {
	    IEntity child = (IEntity) children.get(i); 
	    if(!(child instanceof Patient)){
	      child.onDraw(pGL, pCamera);
	    }else{
	      Patient patient = (Patient) child;
	      if(patient.getCurrentBuilding() == null || patient.isOnPick())
	        patient.onDraw(pGL, pCamera);
	    }
	  }
	
	  for(int i = 0; i < childCount; i++) {
        IEntity child = (IEntity) children.get(i); 
	    if(child instanceof Patient){
		  Patient patient = (Patient) child;
		  if(patient.isVisible() && patient.getCurrentFloor() == getFloor())
		    patient.drawInterface(pGL, pCamera);
	    }	
	  }
	  
    }
  //super.onDrawChildren(pGL, pCamera);
/*	  detachSelf();
  int count = getChildCount();
  if(pickedPatient != null)
    count -= 1;
  GameObject[] list = new GameObject[count];
  for(int i = 0;i < count;i++){
	if(pickedPatient != null && pickedPatient.equals(getChild(i)))
	  continue;		  
    list[i] = ((GameObject) getChild(i));	
  }
	
  Arrays.sort(list, objComparator);
  for(int i = 0;i < list.length;i++){
    (list[i]).onDraw(pGL, pCamera);	
  }
  
  if(pickedPatient != null)
    pickedPatient.onDraw(pGL, pCamera);*/
	  
}

public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) { 	
	
 	int pointerID = pSceneTouchEvent.getPointerID();
 	Log.d("test", ""+pointerID);
 	if(pointerID == 0){
 	  int action = pSceneTouchEvent.getAction();
 	  float touchX = pSceneTouchEvent.getX();
 	  float touchY = pSceneTouchEvent.getY();
 	  switch(action){
 	    case TouchEvent.ACTION_DOWN:
 	      Log.d("test", "action down");	
 	      Enumeration<Patient> e = patientList.elements();
 	      while(e.hasMoreElements()){
 	        Patient patient = e.nextElement();	 
 	        if(patient.isVisible() && patient.canPick() && patient.isContain(touchX, touchY)){ 	         
 	          pickedPatient = patient;	
 	          pickedPatient.onPicked(touchX, touchY); 	           
 	          break;
 	        }
 	      }
 	     Log.d("test", "pickedPatient = "+pickedPatient);
 	      if(pickedPatient == null){
 	        Enumeration<GameObject> buildingElements = buildingList.elements(); 	      
 	        while(buildingElements.hasMoreElements()){
 	          GameObject obj = buildingElements.nextElement();
 		      if(!(obj instanceof Building) || obj instanceof Chair || obj instanceof Outside || obj instanceof OutsideElevator || obj instanceof Triage)
 		        continue;
 		      Building building = (Building) obj;
 	          //Building building = (Building) buildingElements.nextElement();
 	          if(!building.isVisible() || building instanceof Elevator)
 	            continue;
 	          //if(building instanceof Ward){
 	          obj = building.isBuildingContain(touchX, touchY); 	          
 	          if(obj != null){ 	            
 	            addNurseQueue(obj);
 	            return true;
 	          }
 	          //}
 	        }
 	        
 	      }else{
 	        return true;	  
 	      }
 	    break;	    
 	    case TouchEvent.ACTION_MOVE:
 	      if(pickedPatient != null){
 	        pickedPatient.onDraged(touchX, touchY);
 	       float patientX = pickedPatient.getX() + pickedPatient.getWidth()/2;
     	   float patientY = pickedPatient.getY() + pickedPatient.getHeight()/2;
 	        Enumeration<GameObject> buildingElements = buildingList.elements(); 
 	        Building currentCollide = null;
	        while(buildingElements.hasMoreElements()){
	          GameObject obj = buildingElements.nextElement();
	          if(!(obj instanceof Building))
	            continue;
	          if(!(obj instanceof Ward) &&
	        	 !(obj instanceof Elevator) &&
	        	 !(obj instanceof Transporter) &&
	        	 !(obj instanceof Chair))
	            continue;
	          
	          Building building = (Building) obj;
	         /* if(building instanceof Building){
	            if(building.isDirty() || building.getState() != building.STATE_IDLE)
	              continue;
	          }else if(building instanceof Transporter){
	            Transporter tranSporter = (Transporter) building;
	            if(tranSporter.getTransportState() != Transporter.STATE_IDLE)
	              continue;
	          }*/
	          if(building.isDirty() || building.isBroked())
	            continue;
	          if(building instanceof Ward){
	            Ward ward = (Ward) building;
	            if(ward.getBuildingType() != pickedPatient.getNextWardType() || ward.getState() != Ward.STATE_IDLE)
	              continue;
	          }
	          if(building.isBuildingContain(patientX, patientY) != null && !building.equals(pickedPatient.getCurrentBuilding()) && building.isVisible()){	        	
	        	if(currentCollide != null){
	        	  if(currentCollide.equals(building))
	  	            continue;	
	        	  
	        	  float collideX = currentCollide.getX() + currentCollide.getWidth()/2;
	        	  float collideY = currentCollide.getY() + currentCollide.getHeight()/2;
	        	  float buildingX = building.getX() + building.getWidth()/2;
	        	  float buildingY = building.getY() + building.getHeight()/2;
	        	  
	        	  float x1 = patientX - collideX;
	        	  float y1 = patientY - collideY;
	        	  float x2 = patientX - buildingX;
	        	  float y2 = patientY - buildingY;
	        	  
	        	  float distance1 = FloatMath.sqrt((x1 * x1) + (y1 * y1));  	
	        	  float distance2 = FloatMath.sqrt((x2 * x2) + (y2 * y2));
	        	  
	        	  if(distance2 < distance1){
	        	    currentCollide = building;	  
	        	  }
	        		  
	        	  
	        	}else{	        	
	        	  currentCollide = building;
	              //collideBuilding.onFocus();
	        	}	            
	            	  
	          }
	        }
	        if(currentCollide == null){
	          if(collideBuilding != null){
	  	        collideBuilding.onUnFocus();
	  	        collideBuilding = null;
	  	      } 	
	        }else{
	          Log.d("RokejitsX", "currentCollide ============================================== "+currentCollide);
	          if(collideBuilding != null){
	            if(!collideBuilding.equals(currentCollide)){
	              collideBuilding.onUnFocus();
	              collideBuilding = currentCollide;
	              collideBuilding.onFocus();
	            }	  
	          }else{
	            collideBuilding = currentCollide;
	            collideBuilding.onFocus();
	          }	
	        }
	        
	        /*if(collideBuilding != null){
	          collideBuilding.onUnFocus();
	          collideBuilding = null;
	        }*/
	        return true;
 	      }
 	      
 	    break;
 	    case TouchEvent.ACTION_UP:
 	      /*Log.d("RokejitsX", "pickedPatient = "+pickedPatient);
 		  Log.d("RokejitsX", "collideBuilding = "+collideBuilding);*/
 	      /*if(pickedPatient != null && collideBuilding != null){
 	        Log.d("RokejitsX", "collideBuilding.getClass() = "+collideBuilding.getClass());
 		    Log.d("RokejitsX", "pickedPatient.collidesWith(collideBuilding) = "+pickedPatient.collidesWith(collideBuilding));
 		    Log.d("RokejitsX", "collideBuilding.equals(pickedPatient.getCurrentBuilding()) = "+collideBuilding.equals(pickedPatient.getCurrentBuilding()));
 		    Log.d("RokejitsX", "collideBuilding.isVisible() = "+collideBuilding.isVisible());	  
 		    Log.d("RokejitsX", "collideBuilding.isVisible() = "+collideBuilding.isVisible());
 		    Log.d("RokejitsX", "pickedPatient.getNextWardType() == collideBuilding.getBuildingType() = "+(pickedPatient.getNextWardType() == collideBuilding.getBuildingType()));
 		   
 	      }*/
 	      
 	      if(pickedPatient != null){
 	    	 pickedPatient.onUnPicked();	
 	    	if(collideBuilding != null){ 	    		
 	   	      collideBuilding.onUnFocus(); 	   	      
 	    	  if(collideBuilding instanceof Ward){
 	    		//Ward ward = (Ward) collideBuilding;
 	    	    if(pickedPatient.getNextWardType() != collideBuilding.getBuildingType()){
 	    	      pickedPatient.callBack(); 
 	    	     Log.d("RokejitsX", "callback1");
 	    	    }else{
 	    	      if(!collideBuilding.receiveCharator(pickedPatient)){
 	    	        Log.d("RokejitsX", "not receive");
 	    	        pickedPatient.callBack();
 	    	      }
 	    	    }	  
 	    	  }else{
 	            if(!collideBuilding.receiveCharator(pickedPatient))
 	              pickedPatient.callBack();
 	    	  }
 	    	}else{
 	    	  pickedPatient.callBack();  	
 	    	}
 	    	  
 	        
 	      }
 	      pickedPatient = null;
 	      collideBuilding = null;	        
	      
 	      
 	    break;
 	  }
 	}
	return false;
  }

  @Override
  public void onFinishHealing(Ward ward, Patient patient) {    
    //patient.nextHealingRoute();	
    /*if(!patient.isFinishHealing())
      if(ward instanceof Triage){        
        Item item = patient.getRequireItem();      
        item.setVisible(false);
        attachChild(item);
        firstInfoWard.addPrepareQueue(item);
        if(itemOnDesk.size() < 5){
          firstInfoWard.next();	
        }
        //patient.setPickable(true);
      }*/
    	
  }

  @Override
  public void onPatientMoveOut(Patient patient) {
    Building building = patient.getCurrentBuilding();
    if(building != null)
      building.removeCharactor(patient);
    int routeIndex = patient.getCurrentNode();
    patient.setGameCharactorPosition(routeManagerList[patient.getCurrentFloor()].getRouteX(routeIndex), routeManagerList[patient.getCurrentFloor()].getRouteY(routeIndex));
    //int routeIndex = building.getActionPatientnode();
    patient.addGameCharactorListener(this);
    if(patient.getCurrentFloor() != 0){
      Elevator elevator = elevatorList[patient.getCurrentFloor()];      
      routeManagerList[patient.getCurrentFloor()].findPath(routeIndex, elevator.getActionPatientnode(), patient);
    }else{      
      routeManagerList[0].findPath(routeIndex, 0, patient);
    }
    removeItem(patient);
    patientList.remove(patient);
    patientAlmostDeadList.remove(patient);
    hospitalListener.removePatientDoughnut(patient);
    
    /*if(patient.isFinishHealing())
      hospitalListener.onPatientFinishHealing();*/
      
	
  } 
  
  
  
  @Override
  public void onPatientFinishHealing(Patient patient) {
    Building building = patient.getCurrentBuilding();
    building.removeCharactor(patient);
    int routeIndex = building.getActionPatientnode();
    patient.setGameCharactorPosition(routeManagerList[patient.getCurrentFloor()].getRouteX(routeIndex), routeManagerList[patient.getCurrentFloor()].getRouteY(routeIndex));
    patient.jump();
    hospitalListener.onPatientFinishHealing();
  }
  
  

  @Override
  public void onPatientRequestItem(Patient patient) {	
    Item item = patient.getRequireItem();      
    item.setVisible(false);
    if(item.getParent() == null)
      attachChild(item);
    firstInfoWard.addPrepareQueue(item);
    if(getAvaiableItemSlot() != -1){
      firstInfoWard.next();	
    }
  }

  @Override
  public void onGameCharactorFloorChanged(GameCharactor gameChar, int floor){
    if(floor != getFloor()){
      if(gameChar.isVisible())
        gameChar.setVisible(false);	
    }else{
      if(!gameChar.isVisible())
        gameChar.setVisible(true);	
    }	  
  }

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {
    if(gameChar.equals(nurse)){
      nurse.removeGameCharactorListener(this);
      nurse.removeGameCharactorListener(nurse.getCurrentBuilding());
      if(nurse.getCurrentBuilding() instanceof Elevator){
  	    GameObject toTarget = nurseQueue.elementAt(0);
  	    if(toTarget instanceof Item){
  	      Item item = (Item) toTarget;
  	      toTarget = item.getOwner();
  	      Log.d("ToTarget", "ToTarget = "+toTarget.getCurrentFloor());
  	    }
  	    nurse.setCurrentBuilding(elevatorList[toTarget.getCurrentFloor()]);
  	    nurse.setCurrentFloor(toTarget.getCurrentFloor());
  	  }else if(nurse.getCurrentBuilding().isDirty()){
  	    nurse.clean();  		  
  	  }
      nextQueue();
    }/*else if(gameChar.equals(ambCharator)){
      if(ambulanceTime == 0){
    	ambBuilding.setShow(true);
    	if(ambBuilding.getCurrentFloor() == getFloor()){    	  		
          ambBuilding.setVisible(true);
    	}
      }
      ambCharator.setShow(false);
      ambCharator.setVisible(false);
    }*/else if(gameChar instanceof Patient){
      Patient patient = (Patient) gameChar;
      if(!patient.isInProgress()){
        patient.removeGameCharactorListener(this);
        if(!waitingChair.receiveCharator(gameChar)){
          if(!frontWaitingQueue.receiveCharator(gameChar)){
            //gameChar.setVisible(false);
          }
        }	  
      }else if(patient.isMoveout()){
    	if(patient.getCurrentFloor() != 0){
    	  patient.setCurrentFloor(0);
    	  Elevator elevator = elevatorList[0];
    	  routeManagerList[0].findPath(elevator.getActionPatientnode(), 0, patient);    	  
    	}else{    		
          if(patientCount >= 1)
    	    patientCount--;
          patient.setVisible(false);
          patient.removeGameCharactorListener(this);
          patient.setGameCharactorFloorChangeListener(null);
          removeFloorChangeListener(patient);
    	}
      }
        
    }
	
  }

  private void removeItem(Patient patient){
    Item item = patient.getRequireItem();
    if(item != null){
      item.setVisible(false);
      GameObject owner = item.getOwner();
      if(owner instanceof Pharmacy){
        Pharmacy info = (Pharmacy) owner;
        info.removeItem(item);
      }else if(owner instanceof Nurse){
        nurse.handOut(item);	
      }
      if(removeItemOnDesk(item) != -1){        
        firstInfoWard.next();
      }
      
    }	  
  }
  @Override
  public void onReceive(Building building, GameCharactor gameChar) {	
	/*if(building.equals(ambBuilding)){
      Patient patient = (Patient) gameChar;
      patient.onAmbulance();      
      removeFloorChangeListener(patient);
      removeItem(patient);
      ambCharator.setShow(true);
      ambCharator.setVisible(true);
      ambBuilding.removeCharactor(gameChar);
      ambBuilding.setShow(false);
      ambBuilding.setVisible(false);     
      ambulanceTime = System.currentTimeMillis();
      routeManager.findPath(12, 13, ambCharator);
      
	}else */if(building instanceof Elevator){
	  if(gameChar instanceof Patient){
		Patient patient = (Patient) gameChar;
		if(patient.isInProgress()){
		  if(!patient.isMoveout()){
	        showElevatorFloorSelector((Elevator) building);
		  }else{
			patient.setCurrentFloor(0);
		    patient.addGameCharactorListener(this);
		    routeManagerList[0].findPath(building.getActionPatientnode(), 0, patient);	  
		  }
		}				
	  }
	}else if(building instanceof Transporter){
      Patient patient = (Patient) gameChar;       
      if(patientCount >= 1)
	    patientCount--;
      patient.setVisible(false);
      patient.removeGameCharactorListener(this);
      patient.setGameCharactorFloorChangeListener(null);
      removeFloorChangeListener(patient);	
      removeItem(patient);
	}/*else if(building instanceof Ward){
	  Patient patient = (Patient) gameChar;
	  Log.d("rokejitsX", "onReceive3 = "+patient.isFinishHealing());
	  Log.d("rokejitsX", "onReceive4 = "+patient.hasRequireItem());
	  if(!patient.isFinishHealing() && patient.hasRequireItem()){	          
	    Item item = patient.getRequireItem();      
	    item.setVisible(false);
	    if(item.getParent() == null)
	      attachChild(item);
	    firstInfoWard.addPrepareQueue(item);
	    if(itemOnDesk.size() < 5){
	      firstInfoWard.next();	
	    }
	        //patient.setPickable(true);
	  }
	}*/
  }

  @Override
  public void onRemove(Building building, GameCharactor gameChar) {
	
	
  }

  @Override
  public void onElevatorFloorSelected(int floorSelected) {	
	OutsideElevator elevatorWaitinfQueue = elevatorWaitingQueueList[floorSelected];
	Patient patient = (Patient) selectedElevator.getCharactor(0);
	elevatorWaitinfQueue.receiveCharator(patient);
	hideElevatorFloorSelector();
  }
  
  public interface FloorChangeListener{
    public void onFloorChanged(int floor);	  
  }
  
  

  @Override
  public void onFinishPreparingItem(Item item) {	
	addItemOnDesk(item);
	Pharmacy infoWard = infowardList[currentFloor];
	infoWard.receiveItemToQueue(item);
	if(getAvaiableItemSlot() != -1){
	  firstInfoWard.next();	
	}
  }

  @Override
  public void onNursePickedItem(Item item) {
	int index = removeItemOnDesk(item);
	if(index != -1)
	  hospitalListener.removeItemOndesk(item, index);
	if(getAvaiableItemSlot() != -1){
	  firstInfoWard.next();	
    }	
  }
  
  private void addItemOnDesk(Item item){
    int index = getAvaiableItemSlot();
    itemOnDesk[index] = item;
    hospitalListener.addItemOndesk(item, index);
  }
  
  private int removeItemOnDesk(Item item){
    for(int i = 0;i < itemOnDesk.length;i++){
      if(item.equals(itemOnDesk[i])){
    	itemOnDesk[i] = null;   
        return i;
      }
    }  	
    return -1;
  }
  
  private int getAvaiableItemSlot(){
    for(int i = 0;i < itemOnDesk.length;i++){
      if(itemOnDesk[i] == null)
        return i;
    }
    return -1;
  }
  
  
  @Override
  public void onFinishCleaning(Nurse nurse, Building building) {
	Dust dust = new Dust();
	if(getFloor() != nurse.getCurrentFloor())
	  dust.setVisible(false);	
	attachChild(dust);
	nurse.setItemToPick(dust);
	nurse.pickItem();
	
    nextQueue(); 	
  }
  
  public interface HospitalListener{
    public void onShowElevetorSelector(float x, float y);
    public void onHideElevetorSelector(); 
    public void onPatientFinishHealing();
    public void addPatientDoughnut(Patient patient);
    public void removePatientDoughnut(Patient patient);
    public void addItemOndesk(Item item, int index);
    public void removeItemOndesk(Item item, int index);
  }



  @Override
  public void onItemSelected(int index) {
	addNurseQueue(itemOnDesk[index]);
	
  }
  
}
