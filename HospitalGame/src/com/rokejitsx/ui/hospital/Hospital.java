package com.rokejitsx.ui.hospital;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.rokejitsx.data.xml.CourseInfoReader.CourseInfo;
import com.rokejitsx.data.xml.HospitalLevelReader.BuildingInfo;
import com.rokejitsx.data.xml.ObjectInfosReader.ObjectInfo;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.building.BuildingListener;
import com.rokejitsx.ui.building.Chair;
import com.rokejitsx.ui.building.GlassDoor;
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
import com.rokejitsx.ui.patient.Patient.HealingRoute;
import com.rokejitsx.ui.patient.PatientListener;

public class Hospital extends Entity implements WardListener, PatientListener, GameCharactorListener, BuildingListener, ElevatorSelectorListener, GameCharatorFloorChangedListener, FirstPharmacyListener, PharmacyListener, NurseListener, HospitalUIListener {
  private RouteManager[] routeManagerList;  
  private Triage analizeWard;    
  private Chair waitingChair;
  private Outside frontWaitingQueue;
  private FirstPharmacy firstInfoWard;
  private Pharmacy[] infowardList;  
  private Elevator[] elevatorList;
  private OutsideElevator[] elevatorWaitingQueueList;
  private GlassDoor glassDoor;
  private int patientInHospitalCount, patientMaxCount, patientCount;  
  private float patientComeInTime, patientComeInCountTime;
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
  
  private CourseInfo[] courseInfoList;
  
  private int machineBreakCount;
  
  
  
  
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
    setPosition(0, 0);    
    
  } 
  
  public void startPlay(){
    if(ambulance != null)
      ambulance.comeIn();
    if(helicopter != null)
      helicopter.comeIn();
  }
  
  public void setMachineBreakCount(int count){
    machineBreakCount = count;	  
  }
  
  public void setPatientMaxCount(int maxCount, float hospitalTime){
    patientMaxCount = maxCount;
    patientComeInCountTime = hospitalTime / maxCount;
    patientComeInTime = 0;
    patientInHospitalCount = 0;
    patientQueueNumber = 1;
  }
  
  private int[] courseInfoNum;
  private int[] courseInfoPercentage;
  
  public void setCourseInfoList(CourseInfo[] list, int hospitalId, int level){
    courseInfoList = list;	 
    courseInfoNum = new int[list.length];
    courseInfoPercentage = new int[list.length];
    for(int i = 0;i < list.length;i++){
      CourseInfo cInfo = courseInfoList[i];
      courseInfoPercentage[i] = cInfo.getPercent(hospitalId, level);
      courseInfoNum[i] = cInfo.getPatientCount(hospitalId, level);
    }
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
  
  
  
 /* private int[][] wardCourseInfo = {
    { Building.QUICKTREAT Building.ULTRASCANBuilding.CARDIOLOGYBuilding.BABY_SCAN},
    null,
    {Building.PSYCHIATRY,Building.PHYSIOTHERAPY},
    {Building.QUICKTREAT, Building.XRAY, Building.OPERATION, Building.DENTIST},
    {Building.PSYCHIATRY, Building.OPERATION, Building.TAC},
    {Building.DENTIST, Building.QUICKTREAT, Building.CHEMOTHERAPY, Building.OPHTHALMOLOGY},
    {Building.QUICKTREAT, Building.XRAY, Building.TAC, Building.CARDIOLOGY, Building.OPERATION}
  };*/
  
  
  private void selectCourse(){
	
    int randomNum = Math.abs(random.nextInt()) % 100;
    int selectCourseIndex = -1;
    
    //Log.d("RokejitsX", "=================================================================================");
    /*for(int i = 0;i < courseInfoList.length;i++){
        
        int percent = courseInfoPercentage[i];      
        int num = courseInfoNum[i];
        
        Log.d("RokejitsX", "randomNum = "+randomNum);
        Log.d("RokejitsX", "course = "+i);
        Log.d("RokejitsX", "percent = "+percent);
        Log.d("RokejitsX", "num = "+num);        
      }*/   
    for(int i = 0;i < courseInfoList.length;i++){
      
      int percent = courseInfoPercentage[i];      
      //int num = courseInfoNum[i];      
      if(randomNum <= percent){
        selectCourseIndex = i;
        break;
      }
    }
    
    if(selectCourseIndex == -1){
      /*for(int i = 0;i < courseInfoList.length;i++){
        int num = courseInfoNum[i];
        if(num > 0){
          selectCourseIndex = i;
          break;
        }
      }*/
    	
      selectCourseIndex = randomNum % courseInfoList.length;	
    }
    
    //courseInfoNum[selectCourseIndex] = courseInfoNum[selectCourseIndex] - 1;
    patientComein(courseInfoList[selectCourseIndex]);
    
    
  }
  
  private int[] patinetIdList = {0, 1, 2 , 3, 4, 5, 7, 8, 9, 10};
  private int[] patinetWomanIdList = {2, 7, 10};
  private Random random = new Random();
  public void patientComein(CourseInfo courseInfo){
	int iid = Math.abs(random.nextInt());	
	int pId;
	
	if(courseInfo.isHasBabyScanInList()){
	  iid = iid % patinetWomanIdList.length;
	  pId = patinetWomanIdList[iid];
	}else{
	  iid = iid % patinetIdList.length;
	  pId = patinetIdList[iid];	
	}
	
	//pId = 5;
	
    Patient patient = new Patient(pId);	  
    if(getFloor() != 0)
      patient.setVisible(false);    
    patient.setGameCharactorPosition(routeManagerList[0].getRouteX(0), routeManagerList[0].getRouteY(0));
    
    Patient babyPatient = patient.getBabyPatient();    
    patient.setHealthLevel(courseInfo.getStartHealth());
    patient.setFeverLevel(courseInfo.getDamageAmount());
    patient.setGameCharactorFloorChangeListener(this);
    patient.addGameCharactorListener(this);    
    patient.setPatientListener(this);
    
    
    InfoPlate infoPlate = new InfoPlate(patientQueueNumber);    
    infoPlate.setOwner(patient);
    if(babyPatient == null){
      patient.addWardHealingRoute(Building.TRIAGE, 0);
         
      /*int[] random = wardCourseInfo[hospitalId];
      if(random == null){
        patient.addWardHealingRoute(Building.BED, getWardFloor(Building.BED)).addItem(infoPlate);	
      }else{
        Random r = new Random();
        int id = Math.abs(r.nextInt()) % random.length;
        patient.addWardHealingRoute(random[id], getWardFloor(random[id])).addItem(infoPlate);
        patient.addWardHealingRoute(Building.BED, getWardFloor(Building.BED));
      }*/
      
      int[] machineList = courseInfo.getMachineList();
      for(int i = 0;i < machineList.length;i++){
        int wardId = machineList[i];	
        HealingRoute hRoute = patient.addWardHealingRoute(wardId, getWardFloor(wardId));
        if(wardId != Building.BED)
          hRoute.addItem(infoPlate);
      }
      
      patient.setShowFloorNumberInBubbleBox(maxFloor > 2);      
      //patient.setShowFloorNumberInBubbleBox(true);
      patient.setQueue(patientQueueNumber++);
      
    }else{
      babyPatient.addWardHealingRoute(Building.TRIAGE, 0);
      babyPatient.setHealthLevel(courseInfo.getStartHealth());
      babyPatient.setFeverLevel(courseInfo.getDamageAmount());          
      infoPlate.setOwner(babyPatient);
      
      int[] machineList = courseInfo.getMachineList();
      for(int i = 0;i < machineList.length;i++){
        int wardId = machineList[i];	
        HealingRoute hRoute = babyPatient.addWardHealingRoute(wardId, getWardFloor(wardId));
        if(wardId != Building.BED)
          hRoute.addItem(infoPlate);
      }
         	
      babyPatient.setGameCharactorFloorChangeListener(this);
      babyPatient.setShowFloorNumberInBubbleBox(maxFloor > 2);
      //babyPatient.setShowFloorNumberInBubbleBox(true);
      babyPatient.setPatientListener(this);
      babyPatient.setHealthLevel(50); 
      babyPatient.setQueue(patientQueueNumber++);
    }
    
    
    
    
    if(patientQueueNumber > 15)
      patientQueueNumber = 1;
    
    patientList.add(patient);
    attachChild(patient);   
    addFloorChangeListener(patient);
    
    if(babyPatient != null){
      patientList.add(babyPatient);
      attachChild(babyPatient);   
      addFloorChangeListener(babyPatient);	
    }
    
    
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
	/*Log.d("RokejitsX", "add nurse queue===============================================================");
	Log.d("RokejitsX", "obj = "+obj);
	Log.d("RokejitsX", "nurseQueue.contains(obj) = "+nurseQueue.contains(obj));
	Log.d("RokejitsX", "nurse.getItemToPick() = "+nurse.getItemToPick());
	
	
	
	Enumeration<GameObject> e = nurseQueue.elements();
	while(e.hasMoreElements()){
	  Log.d("RokejitsX", "Item in queue = "+e.nextElement());	
	}
	Log.d("RokejitsX", "add nurse queue===============================================================2");*/
	if(nurseQueue.contains(obj) || obj.equals(nurse.getItemToPick()))
	  return;
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
	
    if(nurseQueue.size() == 0 || nurse.isCleaning() || nurse.isRepairing())
      return;    
    GameObject toTarget = nurseQueue.elementAt(0);
    
    if(toTarget instanceof Item){
      Item item = (Item) toTarget;	
    
      Pharmacy infoWard = (Pharmacy) item.getOwner();
    
      if(infoWard.getCurrentFloor() != nurse.getCurrentFloor()){
        toTarget = elevatorList[nurse.getCurrentFloor()];	
    
      }else{
      
        nurseQueue.remove(0);
      }
      
      
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
	    ObjectInfo objectInfo = ResourceManager.getInstance().getObjectInfo(buildingInfo.getBuildingId());
	    Log.d("RokejitsX", "create building2 = "+building.getClass()+"************************************************");
	    Log.d("RokejitsX", "create building3 = "+objectInfo+"************************************************");
	    addFloorChangeListener(building);
		//GameObject building = buildingList[i];
		if(building != null){
		  buildingList.addElement(building);
		  building.setCurrentFloor(buildingInfo.getFloor());
		  building.setGameObjectPositionAsCenter(buildingInfo.getX(), buildingInfo.getY());		  
		  if(objectInfo != null){
		    float x = buildingInfo.getX() + objectInfo.getFloorX() - building.getWidth()/2;
		    float y = buildingInfo.getY() + objectInfo.getFloorY() - building.getHeight()/2;
		    building.setPosition(x, y);
		  }else{
	        building.setGameObjectPositionAsCenter(buildingInfo.getX(), buildingInfo.getY());  
		  }
		  
	      attachChild(building);
	      
	      if(building instanceof Building){
	        Building b = (Building) building;
	        b.setActionNode(buildingInfo.getActionNode());
	        b.setActionPatientNode(buildingInfo.getPatientActionNode());
	        b.setBuildingListener(this);
	        //b.setBuildingListener(this);
	      }
	      
	      if(building instanceof Ward){
	        Ward ward = (Ward) building;
	        ward.setWardListener(this);
	      }
	      
	      if(building instanceof GlassDoor){
	        glassDoor = (GlassDoor) building;	  
	      }else if(building instanceof Triage){
	        analizeWard = (Triage) building;
	        analizeWard.setWardListener(this);
	      }else if(building instanceof Chair){
	    	waitingChair = (Chair) building;  
	      }else if(building instanceof Outside){
	    	frontWaitingQueue = (Outside) building;
	    	//frontWaitingQueue.setPosition(50, 200);
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
	       // ambulance.setBuildingListener(this);
	        //ambulance.addTransporterListener(this);
	       // amb.initPosition();
	       // building.setVisible(false);   	  
	      }else if(building instanceof Helicopter){
	        helicopter = (Helicopter) building;
	        //helicopter.setBuildingListener(this);
	        //helicopter.addTransporterListener(this);
	      }else if(building instanceof Elevator){
	    	//((Elevator) building).setBuildingListener(this);
	        elevatorList[building.getCurrentFloor()] = (Elevator) building;	  
	      }else if(building instanceof OutsideElevator){
	    	elevatorWaitingQueueList[building.getCurrentFloor()] = (OutsideElevator) building;	  
	      }	     
		}
	  }	 	
  }
  
  

  
    
  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {
	if(patientComeInTime <= 0){
      if(patientInHospitalCount < 15 && patientCount < patientMaxCount){
    	HospitalGameActivity.getGameActivity().runOnUiThread(new Runnable(){
    	  public void run(){
    	    selectCourse();	  
    	  }	
    	});	    
    	patientCount++;
	    patientInHospitalCount++;
	  }
      patientComeInTime = patientComeInCountTime;
      //patientComeInTime = 5;
	}else{
	  patientComeInTime -= pSecondsElapsed;	
	}
	super.onManagedUpdate(pSecondsElapsed);
	
	
	
	
	
	Enumeration<Patient> e = patientList.elements();
	float x1 = glassDoor.getX() + glassDoor.getWidth()/2;
	float y1 = glassDoor.getY() + glassDoor.getHeight()/2;
	boolean open = false;
	
	
	for(int i = 0;i < getChildCount();i++){
	  IEntity iEn = getChild(i);
	  if(!(iEn instanceof Patient))
	    continue;
	  Patient patient= (Patient) iEn;
	  if(!patient.isOnPick() && patient.getCurrentFloor() == 0 && !open){
	    float x2 = patient.getX() + patient.getWidth()/2 ;	  
	    float y2 = patient.getY() + patient.getHeight()/2;
	    float x3 = x2 - x1;
	    float y3 = y2 - y1;
	    float distance = FloatMath.sqrt((x3 * x3) + (y3 * y3));
	    if(distance < 100)
	      open = true;
	  }
	 
	}
	if(open)
      glassDoor.open();
    else
      glassDoor.close();
	
	while(e.hasMoreElements()){
      Patient patient = e.nextElement();
      
      if(patient.isInProgress()){
        if(patient.getHealthLevel() <= 40 && !patientAlmostDeadList.contains(patient)){
          patientAlmostDeadList.add(patient);
          //add doughnut to hospital UI
          hospitalListener.addPatientDoughnut(patient);
        }else if(patient.getHealthLevel() > 40 && patientAlmostDeadList.contains(patient)){
          patientAlmostDeadList.remove(patient);
          //remove doughnut from hospital UI
          hospitalListener.removePatientDoughnut(patient);
        }
      }
	}
	
	
	
  }
  
  
  Comparator<? super GameObject> objComparator = new Comparator<GameObject>(){	 
     @Override
	 public int compare(GameObject lhs, GameObject rhs) {
       float x = lhs.getX() + lhs.getWidth()/2;
       float y = lhs.getY() + lhs.getHeight()/2;
       
       if(lhs instanceof Patient || lhs instanceof Nurse)
         y += lhs.getHeight() / 2;
       
       float x1 = rhs.getX() + rhs.getWidth()/2;
       float y1 = rhs.getY() + rhs.getHeight()/2;
       
       if(rhs instanceof Patient || rhs instanceof Nurse)
         y1 += rhs.getHeight() / 2;
       
       float x3 = x + x1;
       float y3 = y + y1;    		
       double distance = Math.sqrt((x3 * x3) + (y3 * y3));    
       double cos = x3 / distance;    
       double angle = Math.toDegrees(Math.acos(cos));    	    
       if(y1 < y)
         angle = 360 - angle;
       
       
       
       if(angle >= 315 || angle <= 135)
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
    //sortChildren();
	Vector<GameObject> tmp = new Vector<GameObject>();
	
	Enumeration<GameObject> e = buildingList.elements();
	while(e.hasMoreElements()){
	  tmp.add(e.nextElement());	
	}
	
	/*final ArrayList<IEntity> children = this.mChildren;
	final int childCount = children.size();
	for(int i = 0; i < childCount; i++) {
	  IEntity child = (IEntity) children.get(i);
	  if(child instanceof Nurse){
	    tmp.add((GameObject) child);
	    continue;
	  }
	  if(child instanceof Patient){
	    Patient patient = (Patient) child;
	    if((patient.getCurrentBuilding() == null || patient.isOnPick()) && patient.getCurrentFloor() == getFloor())
	      tmp.add(patient);
	  }
	}*/
	
	GameObject[] list = new GameObject[tmp.size()];
	tmp.copyInto(list);
	/*for(int i = 0;i < getChildCount();i++){
	  list[i] = ((GameObject) getChild(i));	
	}*/
	
    if(this.mChildren != null && this.mChildrenVisible) {
	  //this.onManagedDrawChildren(pGL, pCamera);
      final ArrayList<IEntity> children = this.mChildren;
	  final int childCount = children.size();
	  for(int i = 0; i < childCount; i++) {
	    IEntity child = (IEntity) children.get(i); 
	    if(!(child instanceof Patient)){
	      if(!(buildingList.contains(child)) && !(child instanceof Nurse) && !(child instanceof Item))
	        child.onDraw(pGL, pCamera);
	    }else{
	      Patient patient = (Patient) child;
	      if((patient.getCurrentBuilding() == null || patient.isOnPick()) && patient.getCurrentFloor() == getFloor())
	        patient.onDraw(pGL, pCamera);
	    }
	  }
	  
	  Arrays.sort(list, objComparator);
      for(int i = 0;i < list.length;i++){
	    (list[i]).onDraw(pGL, pCamera);	
		  
	  }
      
      for(int i = 0; i < childCount; i++) {
  	    IEntity child = (IEntity) children.get(i);
  	    if(child instanceof Nurse || child instanceof Item){
  	      child.onDraw(pGL, pCamera);
  	      continue;
  	    }
  	    if(child instanceof Patient){  	      
  	      Patient patient = (Patient) child;
  	      if((patient.getCurrentBuilding() == null || patient.isOnPick()) && patient.getCurrentFloor() == getFloor())
  	        patient.onDraw(pGL, pCamera);
  	    }
  	  }
	
	  for(int i = 0; i < childCount; i++) {
        IEntity child = (IEntity) children.get(i); 
	    if(child instanceof Patient){
		  Patient patient = (Patient) child;
		  if(/*patient.isVisible() && */patient.getCurrentFloor() == getFloor())
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
 	          if(building.equals(waitingChair))
 	            obj = null;
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
	            
	            if(pickedPatient.getPatientId() != 5){
	              if(ward.getBuildingType() != pickedPatient.getNextWardType() || ward.getState() != Ward.STATE_IDLE)
	                continue;
	            }else{
	              if(!(building instanceof Chair))
	                continue;
	            }
	          }
	          
	          if(building instanceof Transporter){
	            Transporter transporter = (Transporter) building;
	            if(transporter.getTransportState() != Transporter.STATE_IDLE)
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
	if(ward instanceof Triage){
	  patient.showQueueNumber();	
	}
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
      if(patient.getPatientId() != 6){    	  
        routeManagerList[0].findPath(routeIndex, 0, patient);
      }else{
    	Building momBuilding = patient.whoIsYourMom().getCurrentBuilding();
    	Log.d("RokejitsX", "momBuilding = "+momBuilding);
    	if(momBuilding instanceof Outside){    	  
    	  Log.d("RokejitsX", "momBuilding = OutSide");
    	  patient.whoIsYourMom().moveOut();    	  
    	}else{
    	  Log.d("RokejitsX", "momBuilding not = OutSide "+patient.getPatientId());
    	  if(!waitingChair.equals(patient.getCurrentBuilding()))
            routeManagerList[0].findPath(routeIndex, waitingChair.getActionPatientnode(), patient);
    	  else
            waitingChair.receiveCharator(patient);
    	}
      }
    }
    removeFromPatientList(patient);
    
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
    hospitalListener.onPatientFinishHealing(patient);
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
  	  }else if(nurse.getCurrentBuilding().isBroked()){
  	    nurse.repair();	  
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
      if(patient.isMoveToAmbulance()){
        ambulance.receiveCharator(patient);
        return;
      }      
      if(patient.isMoveToHelicopter()){
    	if(patient.getCurrentFloor() != helicopter.getCurrentFloor()){
    	  patient.setCurrentFloor(helicopter.getCurrentFloor());
    	  RouteManager rManager = routeManagerList[helicopter.getCurrentFloor()];
    	  Elevator elevator = elevatorList[helicopter.getCurrentFloor()];    	
    	  rManager.findPath(elevator.getActionPatientnode(), helicopter.getActionPatientnode(), patient);
    	}else{
    	  helicopter.receiveCharator(patient);	
    	}        
        return;
      }
      if(!patient.isInProgress() && !patient.isMoveout()){
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
    	  if(patient.getPatientId() != 6)
    	    routeManagerList[0].findPath(elevator.getActionPatientnode(), 0, patient);
    	  else
    		routeManagerList[0].findPath(elevator.getActionPatientnode(), waitingChair.getActionPatientnode(), patient);
    	}else{    	
    	  if(patient.getPatientId() != 6){
    	    removeFromPatientList(patient);
            removePatientFromHospital(patient);
    	  }else{
    		waitingChair.receiveCharator(patient);
    		removePatientFromHospital(patient);              
    	  }
    	}
      }
        
    }
	
  }
  
  

  private void removeItem(Patient patient){
    Item item = patient.getRequireItem();
    if(item != null){
      item.setVisible(false);
      if(item.getParent() != null)
        HospitalGameActivity.getGameActivity().sendDeattachChild((Entity) item.getParent(), item);
      GameObject owner = item.getOwner();
      if(owner instanceof Pharmacy){
        Pharmacy info = (Pharmacy) owner;
        info.removeItem(item);
      }else if(owner instanceof Nurse){
        nurse.handOut(item);	
      }
      int index = removeItemOnDesk(item); 
      if(index != -1){    	
    	hospitalListener.removeItemOndesk(item, index);
        firstInfoWard.next();
      }
      
    }	  
  }
  @Override
  public void onReceive(Building building, GameCharactor gameChar) {	
	if(building instanceof Elevator){
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
	  removePatientFromHospital(patient);
	  removeFromPatientList(patient);	  
	  if(patient.getPatientId() == 6){
		Transporter transporter = (Transporter) building;  
	    Patient mom = patient.whoIsYourMom();
	    waitingChair.removeCharactor(mom);
	    removePatientFromHospital(mom);
	    removeFromPatientList(mom);
	    Patient tmpMom = new Patient(7, 5);
	    tmpMom.addGameCharactorListener(this);
	    tmpMom.setGameCharactorFloorChangeListener(this);
	    tmpMom.idle(false);
	    tmpMom.setGameCharactorPosition(routeManagerList[0].getRouteX(waitingChair.getActionPatientnode()), 
                                        routeManagerList[0].getRouteY(waitingChair.getActionPatientnode()));
	    if(transporter.getBuildingType() == Building.AMBULANCE){
	      tmpMom.moveToAmbulance();	      
	      routeManagerList[0].findPath(waitingChair.getActionPatientnode(), ambulance.getActionPatientnode(), tmpMom);	      
	    }else{
	      tmpMom.moveToHelicopter();
	      routeManagerList[0].findPath(waitingChair.getActionPatientnode(), elevatorList[0].getActionPatientnode(), tmpMom);
	    }	  
	    tmpMom.setCurrentFloor(0);
	    addFloorChangeListener(tmpMom);
	    HospitalGameActivity.getGameActivity().sendAttachChild(this, tmpMom);
	    
	  }
	}
  }
  
  private void removePatientFromHospital(Patient patient){
    patient.setVisible(false);
    patient.removeGameCharactorListener(this);
    patient.setGameCharactorFloorChangeListener(null);
    removeFloorChangeListener(patient);	
    removeItem(patient);
    if(patient.getParent() != null)
      HospitalGameActivity.getGameActivity().sendDeattachChild((Entity) patient.getParent(), patient);    
  }
  
  private void removeFromPatientList(Patient patient){
	if(!patientList.contains(patient))
	  return;
	removeItem(patient);
    patientList.remove(patient);
    patientAlmostDeadList.remove(patient);
    hospitalListener.removePatientDoughnut(patient);	
    if(patient.getPatientId() != 6)
      if(patientInHospitalCount >= 1)
        patientInHospitalCount--;	  
  }
  
 /* @Override
  public void onSentPatientComplete(Transporter transporter) {
    Patient patient = (Patient) transporter.getCharactor(0);  
    removePatientFromHospital(patient);
    removeFromPatientList(patient);
  	
  }*/

  @Override
  public void onRemove(Building building, GameCharactor gameChar) {	
    if(building.isBuildingCanBroke() && machineBreakCount > 0){
      building.setState(Building.STATE_BROKED);
      requestItem(Item.createItemObject(Item.REPAIR_TOOL, -1));
      machineBreakCount--;
    }	
	
  }
  
  @Override
  public void onPatientRequestItem(Patient patient) {	
    Item item = patient.getRequireItem();      
    item.setVisible(false);
    patient.addBillCost(20);
    requestItem(item);  
  }
  
  private void requestItem(Item item){
    if(item.getParent() == null)
      attachChild(item);
    firstInfoWard.addPrepareQueue(item);
    if(getAvaiableItemSlot() != -1){
      firstInfoWard.next();	
    }	  
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
  
  @Override
  public void onFinishRepairing(Nurse nurse, Building building) {	
    nextQueue(); 	
  }
  
  public interface HospitalListener{
    public void onShowElevetorSelector(float x, float y);
    public void onHideElevetorSelector(); 
    public void onPatientFinishHealing(Patient patient);
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
