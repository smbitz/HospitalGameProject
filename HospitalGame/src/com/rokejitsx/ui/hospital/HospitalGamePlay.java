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
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.input.touch.TouchEvent;

import android.util.FloatMath;
import android.util.Log;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.audio.SoundList;
import com.rokejitsx.audio.SoundPlayerManager;
import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameCharactorListener;
import com.rokejitsx.data.GameCharatorFloorChangedListener;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.route.RouteManager;
import com.rokejitsx.data.xml.BuildingInfo;
import com.rokejitsx.data.xml.CourseInfoReader.CourseInfo;
import com.rokejitsx.data.xml.ObjectInfosReader.ObjectInfo;
import com.rokejitsx.menu.shopmenu.UpgradeData;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.building.BuildingListener;
import com.rokejitsx.ui.building.Chair;
import com.rokejitsx.ui.building.GlassDoor;
import com.rokejitsx.ui.building.Laundry;
import com.rokejitsx.ui.building.elevator.Elevator;
import com.rokejitsx.ui.building.elevator.ElevatorFloorSelector.ElevatorSelectorListener;
import com.rokejitsx.ui.building.others.Food;
import com.rokejitsx.ui.building.others.Plant;
import com.rokejitsx.ui.building.others.Television;
import com.rokejitsx.ui.building.others.Water;
import com.rokejitsx.ui.building.transport.Ambulance;
import com.rokejitsx.ui.building.transport.Helicopter;
import com.rokejitsx.ui.building.transport.Transporter;
import com.rokejitsx.ui.building.waitingqueue.Outside;
import com.rokejitsx.ui.building.waitingqueue.OutsideElevator;
import com.rokejitsx.ui.building.ward.Bed;
import com.rokejitsx.ui.building.ward.Cardiology;
import com.rokejitsx.ui.building.ward.Triage;
import com.rokejitsx.ui.building.ward.Ward;
import com.rokejitsx.ui.building.ward.WardListener;
import com.rokejitsx.ui.building.ward.pharmacy.FirstPharmacy;
import com.rokejitsx.ui.building.ward.pharmacy.FirstPharmacyListener;
import com.rokejitsx.ui.building.ward.pharmacy.Pharmacy;
import com.rokejitsx.ui.building.ward.pharmacy.PharmacyListener;
import com.rokejitsx.ui.item.Coffee;
import com.rokejitsx.ui.item.Dust;
import com.rokejitsx.ui.item.InfoPlate;
import com.rokejitsx.ui.item.Item;
import com.rokejitsx.ui.item.Medicine;
import com.rokejitsx.ui.item.Pill;
import com.rokejitsx.ui.nurse.Nurse;
import com.rokejitsx.ui.nurse.NurseListener;
import com.rokejitsx.ui.nurse.NurseTail;
import com.rokejitsx.ui.patient.Patient;
import com.rokejitsx.ui.patient.Patient.HealingRoute;
import com.rokejitsx.ui.patient.PatientListener;

public class HospitalGamePlay extends Hospital implements WardListener, PatientListener, 
                                                GameCharactorListener, BuildingListener, 
                                                ElevatorSelectorListener, GameCharatorFloorChangedListener, 
                                                FirstPharmacyListener, PharmacyListener, 
                                                NurseListener,
                                                SoundList, HospitalUIItemListener {
  
	private static final int PATIENT_FAILED_AFRO 		= 0;
	private static final int PATIENT_FAILED_ARAB 		= 1;
	private static final int PATIENT_FAILED_BOY 		= 2;
	private static final int PATIENT_FAILED_GRANDPA 	= 3;
	private static final int PATIENT_FAILED_GRANNY 		= 4;
	private static final int PATIENT_FAILED_WOMAN 		= 5;
	private static final int PATIENT_LEAVE 				= 6;
	private static final int PATIENT_PICKUP 			= 7;
	private static final int PATIENT_SHAKE_AFRO 		= 8;
	private static final int PATIENT_SHAKE_ARAB 		= 9;
	private static final int PATIENT_SHAKE_BOY 			= 10;
	private static final int PATIENT_SHAKE_GRANDPA 		= 11;
	private static final int PATIENT_SHAKE_GRANNY 		= 12;
	private static final int PATIENT_SHAKE_WOMAN 		= 13;
	private static final int PATIENT_SHAKE_HARD 		= 14;
	private static final int PATIENT_SUCCESS_AFRO 		= 15;
	private static final int PATIENT_SUCCESS_ARAB 		= 16;
	private static final int PATIENT_SUCCESS_BOY 		= 17;
	private static final int PATIENT_SUCCESS_GRANDPA 	= 18;
	private static final int PATIENT_SUCCESS_GRANNY 	= 19;
	private static final int PATIENT_SUCCESS_WOMAN 		= 20;
	private static final int PATIENT_THANKS 			= 21;    
	
	private static final String[][] patientSoundList = {
	  {
	    PATIENT_FAILED_AFRO1,
	    PATIENT_FAILED_AFRO2,
	    PATIENT_FAILED_AFRO3,
	    PATIENT_FAILED_AFRO4,
	    PATIENT_FAILED_AFRO5,
	    PATIENT_FAILED_AFRO6,
	    PATIENT_FAILED_AFRO7
	  },
	  {	  
		PATIENT_FAILED_ARAB1,
		PATIENT_FAILED_ARAB2,
		PATIENT_FAILED_ARAB3,
		PATIENT_FAILED_ARAB4
	  },
	  {	
		PATIENT_FAILED_BOY1,
		PATIENT_FAILED_BOY2,
		PATIENT_FAILED_BOY3,
		PATIENT_FAILED_BOY4
	  },
	  {
	    PATIENT_FAILED_GRANDPA1,
	    PATIENT_FAILED_GRANDPA2,
	    PATIENT_FAILED_GRANDPA3,
	    PATIENT_FAILED_GRANDPA4,
	    PATIENT_FAILED_GRANDPA5
	  },
	  {
        PATIENT_FAILED_GRANNY1,
        PATIENT_FAILED_GRANNY2,
        PATIENT_FAILED_GRANNY3,
        PATIENT_FAILED_GRANNY4
	  },
	  {
	    PATIENT_FAILED_WOMAN1,
	    PATIENT_FAILED_WOMAN2
	  },	    
	  {
	    PATIENT_LEAVE1,
	    PATIENT_LEAVE2,
	    PATIENT_LEAVE3,
	    PATIENT_LEAVE4,
	    PATIENT_LEAVE5
	  },
	  {
	    PATIENT_PICKUP1,
	    PATIENT_PICKUP2,
	    PATIENT_PICKUP3,
	    PATIENT_PICKUP4,
	    PATIENT_PICKUP5,
	    PATIENT_PICKUP6,
	    PATIENT_PICKUP7,
	    PATIENT_PICKUP8,
	    PATIENT_PICKUP9,
	    PATIENT_PICKUP10
	  },
	  {
	    PATIENT_SHAKE_AFRO1,
	    PATIENT_SHAKE_AFRO2,
	    PATIENT_SHAKE_AFRO3,
	    PATIENT_SHAKE_AFRO4,
	    PATIENT_SHAKE_AFRO5,
	    PATIENT_SHAKE_AFRO6,
	    PATIENT_SHAKE_AFRO7,
	    PATIENT_SHAKE_AFRO8,
	    PATIENT_SHAKE_AFRO9
	  },
	  {
	    PATIENT_SHAKE_ARAB1,
	    PATIENT_SHAKE_ARAB2,
	    PATIENT_SHAKE_ARAB3,
	    PATIENT_SHAKE_ARAB4,
	    PATIENT_SHAKE_ARAB5,
	    PATIENT_SHAKE_ARAB6,
	    PATIENT_SHAKE_ARAB7,
	    PATIENT_SHAKE_ARAB8,
	    PATIENT_SHAKE_ARAB9
	  },
	  {
	    PATIENT_SHAKE_BOY1,
	    PATIENT_SHAKE_BOY2,
	    PATIENT_SHAKE_BOY3,
	    PATIENT_SHAKE_BOY4,
	    PATIENT_SHAKE_BOY5
	  },
	  {
	    PATIENT_SHAKE_GRANDPA1,
	    PATIENT_SHAKE_GRANDPA2,
	    PATIENT_SHAKE_GRANDPA3,
	    PATIENT_SHAKE_GRANDPA4,
	    PATIENT_SHAKE_GRANDPA5
	  },
	  {
		PATIENT_SHAKE_GRANNY1,
		PATIENT_SHAKE_GRANNY2,
		PATIENT_SHAKE_GRANNY3,
		PATIENT_SHAKE_GRANNY4,
		PATIENT_SHAKE_GRANNY5,
		PATIENT_SHAKE_GRANNY6,
		PATIENT_SHAKE_GRANNY7
      },
      {
		PATIENT_SHAKE_WOMAN1,
		PATIENT_SHAKE_WOMAN2,
		PATIENT_SHAKE_WOMAN3
      },
      {		  
		PATIENT_SHAKE_HARD1,
		PATIENT_SHAKE_HARD2,
		PATIENT_SHAKE_HARD3,
		PATIENT_SHAKE_HARD4
      },
      {
		PATIENT_SUCCESS_AFRO1,
		PATIENT_SUCCESS_AFRO2,
		PATIENT_SUCCESS_AFRO3,
		PATIENT_SUCCESS_AFRO4,
		PATIENT_SUCCESS_AFRO5,
		PATIENT_SUCCESS_AFRO6
	  },
	  {
		PATIENT_SUCCESS_ARAB1,
		PATIENT_SUCCESS_ARAB2,
		PATIENT_SUCCESS_ARAB3,
		PATIENT_SUCCESS_ARAB4
	  },
	  {
		PATIENT_SUCCESS_BOY1,
		PATIENT_SUCCESS_BOY2,
		PATIENT_SUCCESS_BOY3,
		PATIENT_SUCCESS_BOY4,
		PATIENT_SUCCESS_BOY5,
		PATIENT_SUCCESS_BOY6
      },
      {
    	PATIENT_SUCCESS_GRANDPA1,
    	PATIENT_SUCCESS_GRANDPA2,
    	PATIENT_SUCCESS_GRANDPA3,
    	PATIENT_SUCCESS_GRANDPA4,
    	PATIENT_SUCCESS_GRANDPA5,
    	PATIENT_SUCCESS_GRANDPA6
      },
      {
		PATIENT_SUCCESS_GRANNY1,
		PATIENT_SUCCESS_GRANNY2,
		PATIENT_SUCCESS_GRANNY3,
		PATIENT_SUCCESS_GRANNY4,
		PATIENT_SUCCESS_GRANNY5,
		PATIENT_SUCCESS_GRANNY6
      },
      {
		PATIENT_SUCCESS_WOMAN1,
		PATIENT_SUCCESS_WOMAN2,
		PATIENT_SUCCESS_WOMAN3
      },
      {
		PATIENT_THANKS1,
		PATIENT_THANKS2,
		PATIENT_THANKS3,
		PATIENT_THANKS4,
		PATIENT_THANKS5
      }
		  
		    
	};	
	
  private static final String[] nurseSoundList = {
    SARAH_GO1,
    SARAH_GO2,
    SARAH_GO3,
    SARAH_GO4,
    SARAH_GO5	  
  };
  
  private static final String[] hospitalBgSoundList = {
    HOSPITAL_1,	  
    HOSPITAL_2,
    HOSPITAL_3,
    HOSPITAL_4,
    HOSPITAL_5,
    HOSPITAL_6,
    HOSPITAL_7,
  };
  
  private static final String[] stressHospitalBgSoundList = {
    STRESS_HOSPITAL1,	  
    STRESS_HOSPITAL2,
    STRESS_HOSPITAL3,
    STRESS_HOSPITAL4,
    STRESS_HOSPITAL5,
    STRESS_HOSPITAL6,
    STRESS_HOSPITAL7,
 
  };
  
  
  public static final int STATE_IDLE 			= -1; 
  public static final int STATE_GAMEPLAY 		= 0;
  public static final int STATE_BUY	 		    = 1;
  public static final int STATE_SELL 		    = 2;
  
  
  private int hospitalState = STATE_IDLE;	  
  
  /*private Sound[][] patientSound;
  private Sound[] nurseGoSound;*/
  //private String patientFailedSound, patientTreatedSound, patientSymtomShow, nurseClick;
  private String hospitalSound, hospitalStressSound;
  private long patientShakeSoundStartPlayTime = -1;
  
	
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
  private NurseTail[] nurseTails;
  private float nurseBoostTime, nurseTailTime;
  private int nurseTailIndex;
  private Vector<GameObject> nurseQueue;
  
  
  
  private Patient pickedPatient;
  private Building collideBuilding;  
  private int patientQueueNumber = 1;  
    
    
  private Elevator selectedElevator;  
  private Item[] itemOnDesk;  
  private Ambulance ambulance;
  private Helicopter helicopter; 
  private Vector<Patient> patientAlmostDeadList;  
  private CourseInfo[] courseInfoList;  
  private int machineBreakCount;
  private float machineBreakTime, machineBreakCountTime;  
  private int[] patinetIdList = {0, 1, 2 , 3, 4, 5, 7, 8, 9, 10};
  private int[] patinetWomanIdList = {2, 7, 10};
  private int[] patinetOldIdList = {3, 4};
  private Random random = new Random();
  
  
  private HospitalListener hospitalListener;
  private UpgradeHospitalListener upgradeHospitalListener;
  
  private int leavedHospitalCount, transporterLeveingCount;
  private boolean needUmbrella;
  public HospitalGamePlay(int maxFloor, boolean needUmbrella){
	super(maxFloor);
	this.needUmbrella = needUmbrella;
	itemOnDesk = new Item[5];
	patientList = new Vector<Patient>();
	healedPatientList = new Vector<Patient>();
	patientAlmostDeadList = new Vector<Patient>();
	buildingList = new Vector<GameObject>();
	routeManagerList = new RouteManager[maxFloor];
	infowardList = new Pharmacy[maxFloor];
	elevatorList = new Elevator[maxFloor];
	elevatorWaitingQueueList = new OutsideElevator[maxFloor];    
	leavedHospitalCount = 0;
	transporterLeveingCount = 0;
	
    setPosition(0, 0);    
    
  } 
  
  public void loadSound(int hospitalId){
	SoundPlayerManager soundManager = SoundPlayerManager.getInstance(); 
    //patientSound = new Sound[patientSoundList.length][];
    for(int i = 0;i < patientSoundList.length;i++){
      //patientSound[i] = new Sound[patientSoundList[i].length];
      for(int j = 0;j < patientSoundList[i].length;j++){
        //patientSound[i][j] = soundManager.createSound(patientSoundList[i][j]);	  
    	soundManager.createSound(patientSoundList[i][j]);  
      }
    }   
    
    //nurseGoSound = new Sound[nurseSoundList.length];
    for(int i = 0;i < nurseSoundList.length;i++){
      //nurseGoSound[i] = soundManager.createSound(nurseSoundList[i]); 	
      soundManager.createSound(nurseSoundList[i]);
    }
    
    
    //patientFailedSound = soundManager.createSound(PATIENT_FAILED);
    //patientTreatedSound = soundManager.createSound(PATIENT_TREATED);
    //patientSymtomShow = soundManager.createSound(PATIENT_SYMTOM_SHOW);
    //nurseClick = soundManager.createSound(SARAH_CLICK);
    
    
    /*hospitalSound = soundManager.createMusic(hospitalBgSoundList[hospitalId]);
    hospitalStressSound = soundManager.createMusic(stressHospitalBgSoundList[hospitalId]);
    
    hospitalSound.setLooping(true);
    hospitalStressSound.setLooping(true);
    
    hospitalSound.setVolume(70);
    hospitalStressSound.setVolume(70);*/
    
    hospitalSound = hospitalBgSoundList[hospitalId];
    hospitalStressSound = stressHospitalBgSoundList[hospitalId];
    
    soundManager.createMusic(hospitalSound);
    soundManager.createMusic(hospitalStressSound);
    
  }
  
  public void unLoad(){
    SoundPlayerManager.getInstance().stopSound(hospitalSound);	  
    SoundPlayerManager.getInstance().releaseSound(hospitalSound);
    
    SoundPlayerManager.getInstance().stopSound(hospitalStressSound);	  
    SoundPlayerManager.getInstance().releaseSound(hospitalStressSound);
  }
  
  
  
  private void playPatientOnPickedSound(Patient patient){
	if(patientShakeSoundStartPlayTime != -1 && System.currentTimeMillis() - patientShakeSoundStartPlayTime < 2000){
	  return;	
	}  
    int soundListId = -1;
    if(patient.isArab()){
      soundListId = PATIENT_SHAKE_ARAB;   	
    }else if(patient.isAfro()){
      soundListId = PATIENT_SHAKE_AFRO;   	
    }if(patient.isBoy()){
      soundListId = PATIENT_SHAKE_BOY;   	
    }if(patient.isGrandpa()){
      soundListId = PATIENT_SHAKE_GRANDPA;   	
    }if(patient.isGranny()){
      soundListId = PATIENT_SHAKE_GRANNY;   	
    }if(patient.isMan()){
      soundListId = PATIENT_SHAKE_HARD;   	
    }if(patient.isWoman()){
      soundListId = PATIENT_SHAKE_WOMAN;   	
    }
    
    if(soundListId == -1)
      return;
    patientShakeSoundStartPlayTime = System.currentTimeMillis();
    playRandomSound(patientSoundList[soundListId]);    
  }
  
  private void playPatientFinishHealingSound(Patient patient){
    int soundListId = -1;
    if(patient.isArab()){
      soundListId = PATIENT_SUCCESS_ARAB;   	
    }else if(patient.isAfro()){
      soundListId = PATIENT_SUCCESS_AFRO;   	
    }if(patient.isBoy()){
      soundListId = PATIENT_SUCCESS_BOY;   	
    }if(patient.isGrandpa()){
      soundListId = PATIENT_SUCCESS_GRANDPA;   	
    }if(patient.isGranny()){
      soundListId = PATIENT_SUCCESS_GRANNY;   	
    }if(patient.isMan()){
      soundListId = PATIENT_THANKS;   	
    }if(patient.isWoman()){
      soundListId = PATIENT_SUCCESS_WOMAN;   	
    }
	    
    if(soundListId == -1)
      return;
    playRandomSound(patientSoundList[soundListId]);	  
  }
  
  private void playPatientUnFinishHealingSound(Patient patient){
    int soundListId = -1;
    if(patient.isArab()){
      soundListId = PATIENT_FAILED_ARAB;   	
    }else if(patient.isAfro()){
      soundListId = PATIENT_FAILED_AFRO;   	
    }if(patient.isBoy()){
      soundListId = PATIENT_FAILED_BOY;   	
    }if(patient.isGrandpa()){
	  soundListId = PATIENT_FAILED_GRANDPA;   	
    }if(patient.isGranny()){
      soundListId = PATIENT_FAILED_GRANNY;   	
    }if(patient.isMan()){
      soundListId = PATIENT_LEAVE;   	
    }if(patient.isWoman()){
      soundListId = PATIENT_FAILED_WOMAN;   	
    }
		    
    if(soundListId == -1)
      return;
    playRandomSound(patientSoundList[soundListId]);	  
  }
  
  
  private void playNurseClickSound(){
	  
    /*nurseClick.play();
    playRandomSound(nurseGoSound);*/
	SoundPlayerManager.getInstance().playSound(SARAH_CLICK);  
    playRandomSound(nurseSoundList);
    
  }
  
  private void playRandomSound(String[] soundList){    
    //getRandomSound(soundList).play();
    SoundPlayerManager.getInstance().playSound(getRandomSound(soundList));
  }
  
  private String getRandomSound(String[] soundList){
    int id = Math.abs(random.nextInt() % soundList.length);
	return soundList[id]; 	  
  }
  
  
  
  @Override
  protected void onSetFloor(int floor) {
    for(int i = 0; i < itemOnDesk.length;i++){
      Item item = itemOnDesk[i];   
      if(item == null)
        continue;
      if(!nurseQueue.contains(item) && !item.equals(nurse.getItemToPick())){
    	Pharmacy infoWard = (Pharmacy) item.getOwner();
        if(infoWard.getCurrentFloor() != getFloor()){
          Pharmacy infoWard2 = infowardList[getFloor()];
          if(infoWard2 != null){
            infoWard.removeItemFromQueue(item);
            infoWard2.receiveItemToQueue(item);
          }
       }  	  
      }
    }
    super.onSetFloor(floor);
  }

  public void timeRunningOut(){
	SoundPlayerManager soundManager = SoundPlayerManager.getInstance();
	soundManager.stopSound(hospitalSound);
	soundManager.releaseSound(hospitalSound);
	soundManager.playSound(hospitalStressSound);
    /*hospitalSound.stop();    
    hospitalStressSound.play();*/
  }
  
  public void timeOut(){
	SoundPlayerManager soundManager = SoundPlayerManager.getInstance();
	soundManager.stopSound(hospitalStressSound);
	soundManager.releaseSound(hospitalStressSound);
    //hospitalSound.stop();
    //hospitalStressSound.stop();	  
  }
  
  private Vector<GameObject> upgradeBuildingList;
  private BuildingInfo[] upgradeList;
  public void buy(int hospitalId, BuildingInfo[] upgradeList){
    hospitalState = STATE_BUY;
    upgradeBuildingList = new Vector<GameObject>();
    this.upgradeList = upgradeList;
    initUpgradeBuildingList(hospitalId, upgradeList);   
    
  }
  
  
  
  public void sell(int buildingType, BuildingInfo[] upgradeList){
    hospitalState = STATE_SELL;
    upgradeBuildingList = new Vector<GameObject>();
    this.upgradeList = upgradeList;
    Enumeration<GameObject> e = buildingList.elements();
    while(e.hasMoreElements()){
      GameObject obj = e.nextElement();
      if(checkBuildingType(obj) == buildingType){
        upgradeBuildingList.add(obj);
        obj.setGameObjectReadyToUpgrade(true);
      }     
    }
  }
  
  private void initUpgradeBuildingList(int hospital, BuildingInfo[] upgradeList){
    
    
    Log.d("RokejitsX", "initUpgradeBuildingList = "+upgradeList.length);
    for(int i = 0;i < upgradeList.length;i++){
       	
      GameObject building = createBuildingFromBuildingInfo(hospital, upgradeList[i]);
      Log.d("RokejitsX", "initUpgradeBuildingList2 building = "+building);
      if(building != null){
    	  
        buildingList.add(building);
        upgradeBuildingList.add(building);
        building.setGameObjectReadyToUpgrade(true);
        if(building.getCurrentFloor() != getFloor())
          building.setVisible(false);
      }
    }
  }
  
  public void startPlay(){
    if(ambulance != null)
      ambulance.comeIn();
    if(helicopter != null)
      helicopter.comeIn();
    SoundPlayerManager.getInstance().playSound(hospitalSound);
    hospitalState = STATE_GAMEPLAY;
  }
  
  public void setMachineBreakCount(int count, float hospitalTime){
    machineBreakCount = count;	  
    machineBreakTime = hospitalTime / machineBreakCount;
    machineBreakCountTime = 0;
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
	if(getMaxFloor() == 2){
	  if(selectedElevator.getCurrentFloor() == 0)
	    onElevatorFloorSelected(1);
	  else
		onElevatorFloorSelected(0);
	  
	}else{
	  hospitalListener.onShowElevetorSelector(selectedElevator.getX() + selectedElevator.getWidth()/2, selectedElevator.getY());
	}
    //this.elevatorFloorSelector.setVisible(true);	  
  }
  
  private void hideElevatorFloorSelector(){
    //this.elevatorFloorSelector.setVisible(false);
    hospitalListener.onHideElevetorSelector();
  }
  
  
  
  
  public void setHospitalListener(HospitalListener listener){
    this.hospitalListener = listener;	  
  }
  
  public void setUpgradeHospitalListener(UpgradeHospitalListener listener){
    this.upgradeHospitalListener = listener;	  
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
  
  
  public void patientComein(CourseInfo courseInfo){
	//courseInfo.setBedRequestItem(new int[]{1,0,1});
	int iid = Math.abs(random.nextInt());	
	int pId;
	int[] bedRequestItem = courseInfo.getBedRequestItem();
	//int[] bedRequestItem = new int[]{1, 0, 1};
	
	
	if(bedRequestItem != null && bedRequestItem.length > 1){
	  iid = iid % patinetOldIdList.length;
	  pId = patinetOldIdList[iid];		
	}else if(courseInfo.isHasBabyScanInList()){
	  iid = iid % patinetWomanIdList.length;
	  pId = patinetWomanIdList[iid];
	}else{
	  iid = iid % patinetIdList.length;
	  pId = patinetIdList[iid];	
	}	
	
    Patient patient = new Patient(pId, needUmbrella);	  
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
    
    int pillId = -1;
    if(bedRequestItem != null){
      pillId = Pill.randomPill();	
    }
    
    if(babyPatient == null){
      infoPlate.setOwner(patient);            	
      patient.addWardHealingRoute(Building.TRIAGE);      
      int[] machineList = courseInfo.getMachineList();
      for(int i = 0;i < machineList.length;i++){
        int wardId = machineList[i];     
        //wardId = Building.BED;
        HealingRoute hRoute = patient.addWardHealingRoute(wardId);
        hRoute.addItem(infoPlate);
        if(wardId == Building.BED){
          if(bedRequestItem != null){
            for(int j = 0; j < bedRequestItem.length;j++){
              int id = bedRequestItem[j];
              if(id == 0){
            	Medicine medicine = new Medicine(patientQueueNumber);
            	medicine.setOwner(patient);
                hRoute.addItem(medicine);	  
              } else if(id == 1){
            	Pill pill = new Pill(pillId, patientQueueNumber);
            	pill.setOwner(patient);
                hRoute.addItem(pill);                 
              }                	
            }	  
          }	
        }
      }
      
      patient.setShowFloorNumberInBubbleBox(getMaxFloor() >= 2);      
      //patient.setShowFloorNumberInBubbleBox(true);
      patient.setQueue(patientQueueNumber++);
      
    }else{
      babyPatient.addWardHealingRoute(Building.TRIAGE);
      babyPatient.setHealthLevel(courseInfo.getStartHealth());
      babyPatient.setFeverLevel(courseInfo.getDamageAmount());          
      infoPlate.setOwner(babyPatient);           
      
      int[] machineList = courseInfo.getMachineList();
      for(int i = 0;i < machineList.length;i++){
        int wardId = machineList[i];	
        HealingRoute hRoute = babyPatient.addWardHealingRoute(wardId);
        hRoute.addItem(infoPlate);
        if(wardId == Building.BED){
          if(bedRequestItem != null){
            for(int j = 0; j < bedRequestItem.length;j++){
              int id = bedRequestItem[j];
              if(id == 0){
                Medicine medicine = new Medicine(patientQueueNumber);
                medicine.setOwner(babyPatient);
                hRoute.addItem(medicine);	  
              } else if(id == 1){
                Pill pill = new Pill(pillId, patientQueueNumber);
                pill.setOwner(babyPatient);
                hRoute.addItem(pill);                 
              }                	
            }	  
          }	
        }
      }
         	
      babyPatient.setGameCharactorFloorChangeListener(this);
      babyPatient.setShowFloorNumberInBubbleBox(getMaxFloor() > 2);
      //babyPatient.setShowFloorNumberInBubbleBox(true);
      babyPatient.setPatientListener(this);
      //babyPatient.setHealthLevel(50); 
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
 
  
  private boolean isCanAddToNurseQueue(){
    if(nurseQueue.size() == 15)
	  return false;
    return true;
  }
  
  private void addNurseQueue(GameObject obj){	
	if(!isCanAddToNurseQueue())
	  return;
	playNurseClickSound();
    nurseQueue.add(obj);   
    HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable() {		
		@Override
		public void run() {
		  startNurseQueue();
			
		}
	});
    
    //return true;
  }
  
  private void startNurseQueue(){
    if(nurse.getGameCharactorState() != Nurse.STATE_IDLE)
      return;
    nurse.setGameCharactorState(nurse.STATE_PREPARE);
    nextQueue();
  }
  private GameObject toTarget;
  private void nextQueue(){
	
    if(nurseQueue.size() == 0 || nurse.isCleaning() || nurse.isRepairing())
      return;    
    toTarget = nurseQueue.elementAt(0);
    
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
      nurse.setItemToPick(null);
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
	nurseTails = new NurseTail[15];
    for(int i = 0;i < nurseTails.length;i++){
      NurseTail tail = new NurseTail();
      addFloorChangeListener(tail);
      attachChild(tail);
      nurseTails[i] = tail;
    }
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
  
  
  
  
  //private int hospitalId;
  public void loadBuilding(int hospital, BuildingInfo[] buildingInfoList){
    //hospitalId = hospital;	  
	for(int i= 0;i < buildingInfoList.length;i++){
  	  BuildingInfo buildingInfo = buildingInfoList[i];
  	  GameObject building = createBuildingFromBuildingInfo(hospital, buildingInfo);
  	  if(building != null)
  	    buildingList.addElement(building);
	}	 	
  }
  
  private GameObject createBuildingFromBuildingInfo(int hospital, BuildingInfo buildingInfo){
    GameObject building = Building.createBuildingObject(buildingInfo.getBuildingId(), hospital);		
    if(building == null)
      return null;
	ObjectInfo objectInfo = ResourceManager.getInstance().getObjectInfo(buildingInfo.getBuildingId());	    
	addFloorChangeListener(building);
		
	if(building != null){
	  //buildingList.addElement(building);
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
	    ambulance.setInitialPosition();
	  }else if(building instanceof Helicopter){
	    helicopter = (Helicopter) building;
	    helicopter.setInitialPosition();
	  }else if(building instanceof Elevator){
	  
	    elevatorList[building.getCurrentFloor()] = (Elevator) building;	  
	  }else if(building instanceof OutsideElevator){
	    elevatorWaitingQueueList[building.getCurrentFloor()] = (OutsideElevator) building;	  
	  }	     
	}	  
	return building;
  }
  
  

  
  private float coffeTime = 10;  
  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {
	super.onManagedUpdate(pSecondsElapsed);
	/*if(upgradeBuildingList != null){
	
      Enumeration<GameObject> e = upgradeBuildingList.elements();
      int i = 0;
      while(e.hasMoreElements()){
    	
        Log.d("RokejitsX", "upgradeBuildingList "+i+" = "+e.nextElement().hasParent());
        i++;
      }
	}*/
	if(hospitalState == STATE_GAMEPLAY){
	  updateGamePlay(pSecondsElapsed);	
	}
	
	
	
  }
  
  private void updateGamePlay(float pSecondsElapsed){
    coffeTime -= pSecondsElapsed;
	if(coffeTime < 0){
	  coffeTime = 10;
	  //requestItem(Item.createItemObject(Item.COFFEE, -1));
	}
		
	if(nurseBoostTime > 0){
	  nurseBoostTime -= pSecondsElapsed;
	  if(nurseBoostTime <= 0){
	    nurse.unBoost();
	    for(int i = 0;i < nurseTails.length;i++){
	      NurseTail tail = nurseTails[i];
	      tail.setVisible(false);
	    }
	  }else{
		if(nurseTailTime <= 0){
	      AnimatedSprite tailSprite = nurse.getNurseTail();
	      if(tailSprite != null){
	        NurseTail nurseTail = nurseTails[nurseTailIndex];
	        nurseTail.setNurseTail(tailSprite, nurse.getCurrentFloor(), getFloor());
	        nurseTail.setPosition(nurse.getX(), nurse.getY());
	        nurseTailIndex++;
	        if(nurseTailIndex == nurseTails.length)
	          nurseTailIndex = 0;	 
	      }
	      nurseTailTime = 0.005f;  
		}else{
		  nurseTailTime -= pSecondsElapsed;	
		}
	  }
	}
	
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
	
	
	if(machineBreakCount > 0){
	  machineBreakCountTime += pSecondsElapsed;
	  if(machineBreakCountTime >= machineBreakTime){
	    machineBreakCountTime = 0;
	    Enumeration<GameObject> e = buildingList.elements();
	    while(e.hasMoreElements()){
	      GameObject obj = e.nextElement();
	      if(!(obj instanceof Building))
	        continue;
	      Building building = (Building) obj;
	      if(building.isBuildingCanBroke() && building.isCanReceiveVisitor()){
	        building.setState(Building.STATE_BROKED);
	        requestItem(Item.createItemObject(Item.REPAIR_TOOL, -1));
	        machineBreakCount--;
	        break;
	      }
	    }
	  }
	}
	
	Enumeration<Patient> e = patientList.elements();
	float x1 = glassDoor.getX() + glassDoor.getWidth()/2;
	float y1 = glassDoor.getY() + glassDoor.getHeight()/2;
	boolean open = false;
	
		
	for(int i = 0;i < getChildCount();i++){
	  IEntity iEn = getChild(i);
	  if(!(iEn instanceof Patient))
	    continue;
	  Patient patient= (Patient) iEn;
	  if(!patient.isOnPick() && patient.getCurrentFloor() == 0){
	    float x2 = patient.getX() + patient.getWidth()/2 ;	  
	    float y2 = patient.getY() + patient.getHeight()/2;
	    float x3 = x2 - x1;
	    float y3 = y2 - y1;
	    float distance = FloatMath.sqrt((x3 * x3) + (y3 * y3));
	    if(distance < 100){	      
	      open = true;
	      if(patient.isMoveout()){
	        patient.openUmbrella();	  
	      }else{
	    	patient.closeUmbrella();	  
	      }
	    }
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
  
  
  Comparator<? super Rectangle> objComparator = new Comparator<Rectangle>(){	 
     @Override
	 public int compare(Rectangle lhs, Rectangle rhs) {
       if(lhs instanceof Helicopter)
         return 1;
       if(rhs instanceof Helicopter)
         return -1;
       float x = lhs.getX() + lhs.getWidth()/2;
       float y = lhs.getY() + lhs.getHeight()/2 + 20;
       
       if(lhs instanceof GlassDoor || lhs instanceof Water || lhs instanceof Cardiology || lhs instanceof Building && !(lhs instanceof Bed) && !(lhs instanceof Laundry))
         y += 40;
       
       if(lhs instanceof GlassDoor)
    	 y += 20;
       
       if(lhs instanceof Patient || lhs instanceof Nurse || lhs instanceof NurseTail)
         y += lhs.getHeight() / 2 - 20;
       
       float x1 = rhs.getX() + rhs.getWidth()/2;
       float y1 = rhs.getY() + rhs.getHeight()/2 + 20;
       
       if(rhs instanceof GlassDoor || rhs instanceof Water || rhs instanceof Cardiology || rhs instanceof Building && !(rhs instanceof Bed) && !(rhs instanceof Laundry))
         y1 += 40;
       
       if(rhs instanceof GlassDoor){
    	 y1 += 20;   
       }
       
       if(rhs instanceof Patient || rhs instanceof Nurse || rhs instanceof NurseTail)
         y1 += rhs.getHeight() / 2 - 20;
       
       float x3 = x - x1;
       float y3 = y - y1;    		
       double distance = Math.sqrt((x3 * x3) + (y3 * y3));    
       double cos = x3 / distance;    
       double angle = Math.toDegrees(Math.acos(cos));    	    
       if(y1 < y)
         angle = 360 - angle;
       
       
       if(lhs instanceof GlassDoor){    	 
         if(angle> 45 && angle < 225)
    	   return -1;	    
         else if(angle == 45 || angle == 225)
           return 0;
         return 1;
       }/*else if(rhs instanceof GlassDoor){
         if(angle> 45 && angle < 225)
       	   return 1;	    
         else if(angle == 45 || angle == 225)
           return 0;
         return -1;   
       }       */
       
       if(angle > 337.5 || angle < 157.5)
         return -1;
       else if(angle == 337.5 || angle == 157.5)
         return 0;
       return 1;
       	 
    	
	 } 
  };
  
  
 /* private double getAngle(float x1, float y1, float x2, float y2){
    float x3 = x1 - x2;
    float y3 = y1 - y2;    		
    double distance = Math.sqrt((x3 * x3) + (y3 * y3));
    
    double cos = x3 / distance;    
    double angle = Math.toDegrees(Math.acos(cos));    	    
    if(y2 < y1)
      angle = 360 - angle;
    return angle;
  }*/
  
  
  
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
	Vector<Entity> tmp = new Vector<Entity>();
	
	Enumeration<GameObject> e = buildingList.elements();
	while(e.hasMoreElements()){
      GameObject obj = e.nextElement(); 
      if(obj.getCurrentFloor() == getFloor())
	    tmp.add(obj);	
	}
	
	ArrayList<IEntity> children = mChildren;
	int childCount = children.size();
	for(int i = 0; i < childCount; i++) {
	  IEntity child = (IEntity) children.get(i);
	  if(child instanceof Nurse || child instanceof NurseTail){
	    tmp.add((Entity) child);
	    
	  }else if(child instanceof Patient){
	    Patient patient = (Patient) child;
	    if((patient.getCurrentBuilding() == null || patient.isOnPick()) && patient.getCurrentFloor() == getFloor())
	      tmp.add(patient);
	  }
	}
	
	Rectangle[] list = new Rectangle[tmp.size()];
	tmp.copyInto(list);
	/*for(int i = 0;i < getChildCount();i++){
	  list[i] = ((GameObject) getChild(i));	
	}*/
	
    if(mChildren != null && mChildrenVisible) {
	  //this.onManagedDrawChildren(pGL, pCamera);
     // final ArrayList<IEntity> children = this.mChildren;
	  //final int childCount = children.size();
	 /* for(int i = 0; i < childCount; i++) {
	    IEntity child = (IEntity) children.get(i); 
	    if(!(child instanceof Patient)){
	      if(!(buildingList.contains(child)) && !(child instanceof Nurse) && !(child instanceof Item) && !(child instanceof NurseTail))
	    	
	        child.onDraw(pGL, pCamera);
	    }else{
	      Patient patient = (Patient) child;
	      if((patient.getCurrentBuilding() == null || patient.isOnPick()) && patient.getCurrentFloor() == getFloor())
	        patient.onDraw(pGL, pCamera);
	    }
	  }*/
	  
	  Arrays.sort(list, objComparator);
      for(int i = 0;i < list.length;i++){
    	 /*if(list[i] instanceof Building){
           Building b = (Building) list[i];
           b.setNum(i);
    	 }*/
    	 
	    (list[i]).onDraw(pGL, pCamera);	
		  
	  }
      
      /*for(int i = 0; i < childCount; i++) {
  	    IEntity child = (IEntity) children.get(i);
  	    if(child instanceof Nurse || child instanceof NurseTail){  	      	
  	      child.onDraw(pGL, pCamera);
  	      continue;
  	    }
  	    if(child instanceof Patient){  	      
  	      Patient patient = (Patient) child;
  	      if((patient.getCurrentBuilding() == null || patient.isOnPick()) && patient.getCurrentFloor() == getFloor()){
  	          
  	        patient.onDraw(pGL, pCamera);
  	      }
  	    }
  	  }*/
	
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
  
  /*private boolean isInUp(Object obj){
    if(upgradeBuildingList != null)
      return upgradeBuildingList.contains(obj);
    return false;
  }*/

  public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) { 	
	
    switch(hospitalState){
      case STATE_GAMEPLAY:
        return onGamePlaySceneTouchEvent(pScene, pSceneTouchEvent);
      case STATE_BUY:
      case STATE_SELL:
        return onUpgradeSceneTouchEvent(pScene, pSceneTouchEvent);     	
    }
    
  
    return false;
  }

  private int checkBuildingType(GameObject obj){
    int buildingType = -1;
    if(obj instanceof Building){
      buildingType = ((Building)obj).getBuildingType();	
    }else if(obj instanceof Plant){
	  buildingType = Building.PLANT;	
	}else if(obj instanceof Food){
	  buildingType = Building.FOOD;	
	}else if(obj instanceof Television){
	  buildingType = Building.TELEVISION;	
	}else if(obj instanceof Water){
	  buildingType = Building.WATER;	
	}
    return buildingType;
  }
  
  private boolean onUpgradeSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent){
  int pointerID = pSceneTouchEvent.getPointerID();
  Log.d("test", ""+pointerID);
  if(pointerID == 0){
    int action = pSceneTouchEvent.getAction();
    float touchX = pSceneTouchEvent.getX();
    float touchY = pSceneTouchEvent.getY();
    switch(action){
      case TouchEvent.ACTION_DOWN:
    	GameObject[] objList = new GameObject[upgradeBuildingList.size()];        
    	upgradeBuildingList.copyInto(objList);
	    GameObject obj = getTouchedObject(touchX, touchY, objList);
	    if(obj != null){
	      if(hospitalState == STATE_BUY){
	    	obj.setGameObjectReadyToUpgrade(false);
	        BuildingInfo info = upgradeList[upgradeBuildingList.indexOf(obj)];
	        
	        
	        info.setBuildingId(checkBuildingType(obj));
	        info.setEnable(true);
	        
	        //upgradeBuildingList.remove(obj);
	        	        
	        upgradeComplete(STATE_BUY, info.getBuildingId());
	      }else if(hospitalState == STATE_SELL){	
	    	 
		    BuildingInfo info = upgradeList[upgradeBuildingList.indexOf(obj)];
		    int buildingType = info.getBuildingId();
		    switch(buildingType){
	          case Building.PLANT:
	          case Building.WATER:
	          case Building.FOOD:        	
	          case Building.TELEVISION:
	          case Building.BED:
	            info.setEnable(false);	
	          break;
	          default:
	            info.setBuildingId(Building.NONE);
	            info.setEnable(false);
		    }
		    
		    
		    HospitalGameActivity.getGameActivity().sendDeattachChild(obj);
		    buildingList.remove(obj);
		    //upgradeBuildingList.remove(obj);		    
		    upgradeComplete(STATE_SELL, buildingType);	  
	      }
	        return true;
	      
	    }
      break;	  
    	  
    }	
  }
    return false;	
  }


  public void upgradeComplete(){
    upgradeComplete(-1, -1);	  
  }
  private void upgradeComplete(int action, int buildingType){
	
	 if(buildingType != -1){
	   int price = Building.getMachineCost(buildingType);
	   UpgradeData data = UpgradeData.getInstance();
       if(action == STATE_BUY){
         data.setFunds(data.getFunds() - price);	    
       }else{
    	 data.setFunds(data.getFunds() + price);  
       }		 
	 } 
	/*HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable() {		
		@Override
		public void run() {*/
	  
	  Vector<Entity> removeList = new Vector<Entity>();
		  Enumeration<GameObject> e = upgradeBuildingList.elements();		  					
		  while(e.hasMoreElements()){
		    GameObject obj = e.nextElement();
		    if(hospitalState == STATE_BUY){
		      BuildingInfo info = upgradeList[upgradeBuildingList.indexOf(obj)];
		      if(info.isEnable())
		        continue;
		      if(info.getBuildingId() != Building.PLANT &&
		    	 info.getBuildingId() != Building.WATER &&
		    	 info.getBuildingId() != Building.FOOD &&
		    	 info.getBuildingId() != Building.TELEVISION &&
		    	 info.getBuildingId() != Building.BED)
		        info.setBuildingId(Building.NONE);
		      buildingList.remove(obj);
		      //obj.detachSelf();
		      removeList.add(obj);
		  	  removeFloorChangeListener(obj);
		    }else{
		      obj.setGameObjectReadyToUpgrade(false);	
		    }
		  	    
		  	    
		  	  
		  }
		  upgradeHospitalListener.onUpgradeCompleted(action, buildingType);
		  HospitalGameActivity.getGameActivity().sendDeattachChild(removeList);	
			
	    /*}
	});*/
	
	
  } 
  
  

  private boolean onGamePlaySceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent){
  int pointerID = pSceneTouchEvent.getPointerID();
  Log.d("test", ""+pointerID);
 	if(pointerID == 0){
 	  int action = pSceneTouchEvent.getAction();
 	  float touchX = pSceneTouchEvent.getX();
 	  float touchY = pSceneTouchEvent.getY();
 	  switch(action){
 	    case TouchEvent.ACTION_DOWN:
 	      Vector<GameObject> patientCanPickList = new Vector<GameObject>();	
 	      Enumeration<Patient> e = patientList.elements();
 	      while(e.hasMoreElements()){
 	        Patient patient = e.nextElement();
 	        if(patient.canPick())
 	          patientCanPickList.add(patient);
 	        /*if(patient.isVisible() && patient.canPick() && patient.isContain(touchX, touchY)){ 	         
 	          pickedPatient = patient;	
 	          pickedPatient.onPicked(touchX, touchY);
 	          playPatientOnPickedSound(pickedPatient);
 	          break;
 	        }*/
 	      }
 	     GameObject[] objList = new GameObject[patientCanPickList.size()];
 	     patientCanPickList.copyInto(objList);
 	     pickedPatient = (Patient) getTouchedObject(touchX, touchY, objList); 
 	     Log.d("test", "pickedPatient = "+pickedPatient);
 	      if(pickedPatient == null){
 	    	Vector<GameObject> tmpBuildingList = new Vector<GameObject>();  
 	        Enumeration<GameObject> buildingElements = buildingList.elements();
 	        while(buildingElements.hasMoreElements()){
  	          GameObject obj = buildingElements.nextElement();
  		      if(!(obj instanceof Building) || obj instanceof Chair || obj instanceof Outside || obj instanceof OutsideElevator || obj instanceof Triage || obj instanceof Elevator)
  		        continue;
  		      tmpBuildingList.add(obj); 	          
  	        }
 	        
 	        objList = new GameObject[tmpBuildingList.size()];
 	        tmpBuildingList.copyInto(objList);
 	        Building building = (Building) getTouchedObject(touchX, touchY, objList);
 	        if(building != null){
 	          GameObject obj = building.isBuildingContain(touchX, touchY); 	          
 	          if(obj != null){ 	       
	            if(obj instanceof Building){
	              building = (Building) obj;
	              if(building.isCanCheck() && isCanAddToNurseQueue()){
	                building.checked();
	                addNurseQueue(obj);
	        	  }
	            }else if(obj instanceof Item){
	              Item item = (Item) obj;
	              if(item.isCanCheck() && isCanAddToNurseQueue()){
	                item.checked();
	                addNurseQueue(obj);
	              }
	            }
	            
 	          }	            
	          return true;
	        }
 	       
 	        
 	      }else{
 	        pickedPatient.onPicked(touchX, touchY);
	        playPatientOnPickedSound(pickedPatient);
 	        return true;	  
 	      }
 	    break;	    
 	    case TouchEvent.ACTION_MOVE:
 	      if(pickedPatient != null){
 	        if(pickedPatient.onDraged(touchX, touchY)){ 	          
 	          playPatientOnPickedSound(pickedPatient);
 	        }
 	       float patientX = pickedPatient.getX() + pickedPatient.getWidth()/2;
     	   float patientY = pickedPatient.getY() + pickedPatient.getHeight()/2;
     	   Vector<GameObject> tmpBuildingList = new Vector<GameObject>();
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
	          if(building.equals( pickedPatient.getCurrentBuilding()))
	            continue;
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
	          
	          tmpBuildingList.add(building);
	          /*if(building.isBuildingContain(patientX, patientY) != null && !building.equals(pickedPatient.getCurrentBuilding()) && building.isVisible()){	        	
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
	            	  
	          }*/
	        }
	       
	        objList = new GameObject[tmpBuildingList.size()];
	        tmpBuildingList.copyInto(objList);
	        currentCollide = (Building) getTouchedObject(patientX, patientY, objList);
	        if(currentCollide == null){
	          if(collideBuilding != null){
	  	        collideBuilding.onUnFocus();
	  	        collideBuilding = null;
	  	      } 	
	        }else{	          
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
  public int onPatientRequestWard(int wardType, int patientFloor) {
	int nearestFloor = -1;
	Ward[] wardList;
	wardList = isHasWard(wardType, patientFloor);
	if(wardList != null && nearestFloor == -1)
	  nearestFloor = patientFloor;
    if(isThisWardCanReceiveVisitor(wardList))
      return patientFloor;
    
	int upperFloor = patientFloor + 1;
	int lowerFloor = patientFloor - 1;
	
	
	for(; upperFloor < getMaxFloor() || lowerFloor >= 0;upperFloor++, lowerFloor--){
	  
	  if(upperFloor < getMaxFloor()){
		wardList = isHasWard(wardType, upperFloor);
		if(wardList != null && nearestFloor == -1)
		  nearestFloor = upperFloor;
	    if(isThisWardCanReceiveVisitor(wardList))
	      return upperFloor;
	  }
	  if(lowerFloor >= 0){	
		wardList = isHasWard(wardType, lowerFloor);
		if(wardList != null && nearestFloor == -1)
		  nearestFloor = lowerFloor;
	    if(isThisWardCanReceiveVisitor(wardList))
	      return lowerFloor;
	  }
	}	
  	return nearestFloor;
  }
  
  private boolean isThisWardCanReceiveVisitor(Ward[] wardList){
	if(wardList == null)
	  return false;
    for(int i = 0;i < wardList.length;i++){
      Ward ward = wardList[i];
      if(ward.isCanReceiveVisitor())
        return true;
    }
    
    return false;
  }
  
  private Ward[] isHasWard(int wardType, int onFloor){
	Vector<Ward> wardList= new Vector<Ward>();
    Enumeration<GameObject> e = buildingList.elements();    
    while(e.hasMoreElements()){
      GameObject obj = e.nextElement();
      if(!(obj instanceof Ward))
        continue;
      Ward ward = (Ward) obj;
      if(ward.getBuildingType() == wardType || wardType == -1)
        if(ward.getCurrentFloor() == onFloor || onFloor == -1){         
          wardList.add(ward);
        }
    } 
    
    if(wardList.size() == 0)
      return null;
    
    Ward[] result = new Ward[wardList.size()];
    
    wardList.copyInto(result);
    return result;
    
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
    	//Log.d("RokejitsX", "momBuilding = "+momBuilding);
    	if(momBuilding instanceof Outside){    	  
    	 // Log.d("RokejitsX", "momBuilding = OutSide");
    	  patient.whoIsYourMom().moveOut();    	  
    	}else{
    	  //Log.d("RokejitsX", "momBuilding not = OutSide "+patient.getPatientId());
    	  if(!waitingChair.equals(patient.getCurrentBuilding()))
            routeManagerList[0].findPath(routeIndex, waitingChair.getActionPatientnode(), patient);
    	  else
            waitingChair.receiveCharator(patient);
    	}
      }
    }
    removeFromPatientList(patient);
    
   
    if(patient.getBabyPatient() == null){
      if(patient.isBoy()){
        if(!patient.isFinishHealing())
          if(!waitingChair.equals(patient.getCurrentBuilding()) && patient.whoIsYourMom().getCurrentBuilding() != null){
        	leavedHospitalCount++;
            playPatientUnFinishHealingSound(patient);
          }
      }else{
        if(!patient.isFinishHealing()){
         leavedHospitalCount++;
         playPatientUnFinishHealingSound(patient);	
        }
      }
    }else{
      Patient babyPatient = patient.getBabyPatient();
      if(!babyPatient.isFinishHealing()){
    	leavedHospitalCount++;
        playPatientUnFinishHealingSound(patient);
      }
    }
  } 
  
  
  public int getPatientLeavingHospitalCount(){
    return leavedHospitalCount;	  
  }
  
  public int getPatientLeavingByTranspoter(){
    return transporterLeveingCount;	  
  }
  
  
  @Override
  public void onPatientFinishHealing(Patient patient) {
    Building building = patient.getCurrentBuilding();
    building.removeCharactor(patient);
    int routeIndex = building.getActionPatientnode();
    patient.setGameCharactorPosition(routeManagerList[patient.getCurrentFloor()].getRouteX(routeIndex), routeManagerList[patient.getCurrentFloor()].getRouteY(routeIndex));
    patient.jump();
    hospitalListener.onPatientFinishHealing(patient);
    playPatientFinishHealingSound(patient);
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
  	      //Log.d("ToTarget", "ToTarget = "+toTarget.getCurrentFloor());
  	    }
  	    nurse.setCurrentBuilding(elevatorList[toTarget.getCurrentFloor()]);
  	    nurse.setCurrentFloor(toTarget.getCurrentFloor());
  	  }else if(nurse.getCurrentBuilding().isDirty()){
  	    nurse.clean();  		  
  	  }else if(nurse.getCurrentBuilding().isBroked()){
  	    nurse.repair();	  
  	  }
      //Log.d("RokejitsX", "toTargetttttttttttttttttttttttttttttttttttttt = "+toTarget);
      if(toTarget instanceof Item){
        Item item = (Item) toTarget;	  
        item.unChecked();
        int index = getItemOnDeskIndex(item);
        if(index != -1){
          hospitalListener.onItemOnDeskUnCheck(index);	
        }
        
      }else if(toTarget instanceof Building){
    	//Log.d("RokejitsX", "toTargetttttttttttttttttttttttttttttttttttttt2 = "+toTarget);
        Building building = (Building) toTarget;
        building.unChecked();
        if(toTarget instanceof Pharmacy){
          Item[] itemInHand = nurse.getAllItemInHand();
          int itemSlotIndex = getAvaiableItemSlot();
          for(int i = 0;i < itemInHand.length &&  itemSlotIndex != -1;i++){
        	Item item = itemInHand[i];
        	
        	if(item != null){
        	  if(item instanceof Dust)
                continue;	
        	  nurse.handOut(item);
        	  addItemOnDesk(item);
        	  Pharmacy infoWard = infowardList[getFloor()];
        	  if(infoWard != null)
        	    infoWard.receiveItemToQueue(item);
        	  itemSlotIndex = getAvaiableItemSlot();	
        	}
            	  
          }
          	
        }
        
        
        //Log.d("RokejitsX", "toTargetttttttttttttttttttttttttttttttttttttt3 = "+toTarget);
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
      HospitalGameActivity.getGameActivity().sendDeattachChild(item);
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
	  if(patient.getPatientId() != 6)
	    transporterLeveingCount++;
	  removePatientFromHospital(patient);
	  removeFromPatientList(patient);	  
	  if(patient.getBabyPatient() != null){
	    Patient baby = patient.getBabyPatient();
	    removePatientFromHospital(baby);
		removeFromPatientList(baby);
	  }
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
    HospitalGameActivity.getGameActivity().sendDeattachChild(patient);    
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
    /*if(building.isBuildingCanBroke() && machineBreakCount > 0){
      building.setState(Building.STATE_BROKED);
      requestItem(Item.createItemObject(Item.REPAIR_TOOL, -1));
      machineBreakCount--;
    }	*/
	
  }
  
  @Override
  public void onPatientRequestItem(Patient patient) {	
    Item item = patient.getRequireItem();      
    item.setVisible(false);
    patient.addBillCost(20);
    requestItem(item);  
  }
  
  private void requestItem(Item item){
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
  public void onFinishPreparingItem(Vector<Item> allItemList) {
	while(getAvaiableItemSlot() != -1 && allItemList.size() > 0){
	  Item item = allItemList.elementAt(0);
	  if(item.getParent() == null)
	    attachChild(item);
	  allItemList.remove(0);
	  addItemOnDesk(item);
      Pharmacy infoWard = infowardList[getFloor()];
      if(infoWard != null)
	    infoWard.receiveItemToQueue(item);
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
	if(item instanceof Coffee){
	  nurseBoostTime = 15;
	  nurseTailTime = 0;
	  nurse.boost();
	  nurseTailIndex = 0;
	  nurse.handOut(item);
	}
  }
  
  private void addItemOnDesk(Item item){
    int index = getAvaiableItemSlot();
    itemOnDesk[index] = item;
    hospitalListener.addItemOndesk(item, index);
  }
  
  private int getItemOnDeskIndex(Item item){
    for(int i = 0;i < itemOnDesk.length;i++){
      if(item.equals(itemOnDesk[i])){    	   
        return i;
      }
    }  	
    return -1;	  
  }
  
  private int removeItemOnDesk(Item item){
	int index = getItemOnDeskIndex(item);
	if(index != -1)
      itemOnDesk[index] = null;
	return index;
    
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
    public void onItemOnDeskUnCheck(int index);
    
    
  }
  
  public interface UpgradeHospitalListener {
    public void onUpgradeCompleted(int action, int buildingType);	  
  }



  @Override
  public boolean onItemSelected(int index) {
	Item item = itemOnDesk[index];
	if(item.isCanCheck() && isCanAddToNurseQueue()){
	  item.checked();
	  addNurseQueue(item);
	  return true;
	}
	
	return false;
	
  }






  
}
