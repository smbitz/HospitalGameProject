package com.rokejitsx;

import java.util.Vector;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

import android.util.Log;
import android.widget.Toast;

import com.kazekim.data.UserMissionSkeleton;
import com.kazekim.menu.ShopMenu;
import com.rokejitsx.data.loader.HospitalLevelLoader;
import com.rokejitsx.data.loader.HospitalLoader;
import com.rokejitsx.data.loader.Loader;
import com.rokejitsx.data.loader.LoaderListener;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.route.RouteManager;
import com.rokejitsx.data.xml.BuildingInfo;
import com.rokejitsx.menu.InGameMenuScene2;
import com.rokejitsx.menu.InGameMenuScene2.InGameMenuScene2Listener;
import com.rokejitsx.menu.shopmenu.UpgradeData;
import com.rokejitsx.save.GameStatManager;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.hospital.HospitalFloorSelector.FloorSelectListener;
import com.rokejitsx.ui.hospital.HospitalGamePlay;
import com.rokejitsx.ui.hospital.HospitalGamePlay.FloorChangeListener;
import com.rokejitsx.ui.hospital.HospitalGamePlay.UpgradeHospitalListener;
import com.rokejitsx.ui.hospital.HospitalTimer.HospitalTimerListener;
import com.rokejitsx.ui.hospital.HospitalUI;
import com.rokejitsx.ui.hospital.HospitalUIListener;
import com.rokejitsx.ui.scene.HospitalDetailScene;
import com.rokejitsx.ui.scene.HospitalDetailScene.HospitalDetailSceneListener;
import com.rokejitsx.ui.scene.LoadingScene;
import com.rokejitsx.ui.scene.map.MapScene;
import com.rokejitsx.ui.scene.map.MapScene.MapSceneListener;


public class GamePlay extends Scene implements IOnSceneTouchListener, HospitalTimerListener, 
                                               FloorSelectListener, HospitalUIListener, UpgradeHospitalListener, 
                                               InGameMenuScene2Listener, LoaderListener, FloorChangeListener, MapSceneListener, HospitalDetailSceneListener { 
  
  private int hospitalId, level;	
  private BuildingInfo[] buildingInfoList;
  private RouteManager[] routeManagerList;
  private HospitalUI hospitalUI;
  private HospitalGamePlay hospital;
  private SpriteBackground[] hospitalBg;
  private BitmapTextureAtlas bgTextureAtlas;
  private InGameMenuScene2 inGameMenuScene;  
  
  
  //private MainMenuScene mainMenuScene;
  
  
  //private Vector<BuildingInfo> fixedBuildingList, dropAreaBuildingList, equipmentBuildingList;  
  
  
  public GamePlay(){		
	inGameMenuScene = new InGameMenuScene2(this);	
    setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));  
    this.setOnSceneTouchListener(this);    
    
  } 
  
  
  
  private void sendSetChildScene(Scene child){
    HospitalGameActivity.getGameActivity().sendSetChildScene(this, child);	  
  }
  
  public void loadHospitalLevel(BuildingInfo[] buildingInfoList, RouteManager[] routeManagerList,int hospitalId, int maxFloor){
	this.hospitalId = hospitalId;
	this.level = GameStatManager.getInstance().getHospitalLevel();
	this.buildingInfoList= buildingInfoList;
	this.routeManagerList = routeManagerList;
	
	clear();
	new HospitalLevelLoader(buildingInfoList, routeManagerList, hospitalId, level, maxFloor, this).startLoad();    
  }
  
  
  private static final int[] STATIC_BUILDING = {
    Building.PLANT,	  
    Building.WATER,
    Building.FOOD,
    Building.TELEVISION
  };  
  @Override
  public void onLoadFinish(Loader loader) {
	if(loader instanceof HospitalLevelLoader){	  
	  HospitalLevelLoader gLoader = (HospitalLevelLoader) loader;	  
	  hospitalBg = gLoader.getHospitalBg();
	  bgTextureAtlas = gLoader.getBgBitmapTextureAtlas();
	  
	  UpgradeData data = UpgradeData.getInstance();
	  GameStatManager stat = GameStatManager.getInstance();
	  
	  data.setFunds(stat.getTotalMoney());
	  data.setPhamacy(stat.getPhamacy());
	  data.setSalaries(stat.getSalaries());
	  data.setRed(stat.getRed());
	  data.setGreen(stat.getGreen());
	  data.setBlue(stat.getBlue());
	  
	  
	  int[] avaiableBuildingType = ResourceManager.getInstance().getBuildingListInLevel(hospitalId, level);
	  int[] availableList = new int[STATIC_BUILDING.length + avaiableBuildingType.length];
		
	  System.arraycopy(STATIC_BUILDING, 0, availableList, 0, STATIC_BUILDING.length);
	  System.arraycopy(avaiableBuildingType, 0, availableList, STATIC_BUILDING.length, avaiableBuildingType.length);
	  
	  
	  GameStatManager.getInstance().loadAppearedBuilding();
	  inGameMenuScene.setBuildingList(availableList, buildingInfoList);
      hospital = gLoader.getHospital();
      hospital.addFloorChangeListener(this);
      hospitalUI = gLoader.getHospitalUI();
      hospitalUI.setHospitalUIListner(this);
      hospitalUI.setHospitalTimerListener(this);
      hospitalUI.setHospitalFloorListener(this);
      hospital.setUpgradeHospitalListener(this);    
      hospital.setFloor(0);
      Vector<Entity> list = new Vector<Entity>();
      list.add(hospital);
      list.add(hospitalUI);
      HospitalGameActivity.getGameActivity().sendAttachChild(this, list);
	}
	Log.d("RokejitsX", "GamePlayLoader load finish = "+getChildScene());
    /*if(getChildScene() != null)
      getChildScene().back();*/
    if(GameStatManager.getInstance().getDayCount() == 0)
      HospitalGameActivity.getGameActivity().sendSetChildScene(this, new MapScene(hospitalId, this));
    else
      showHospitalDetailScene();
    
    //needMapScene = false;
    
    
    
    
    
  }
  
  
  
  private String[] brifStrings = {
    "First days on the job are always nerve racking.  But if you pay attention to your patients needs, then you'll do great!",
	"There are even more ways to improve the hospital.  Let's take a look at how to do this.",
	"The recent heat wave is causing a lot of patients to come in needing rest.  Make sure there are enough beds for them.",
	"It's another hot day outside, perhaps having some refreshments in the waiting area will give us more time to help patients.",
	"The much needed rain has ended the heat wave, but it's also caused some illnesses to spread.  Someone even had Coyote Fever!",
	"Have you noticed that some patients have needed X-rays?  Let's make sure we have the equipment to see those bones.",
	"Expert nurses like yourself can improve the efficiency of the entire hospital.  I'll show you how to do this.",
	"Get an Operation table set up, stat!  A lot of people are hurt trying to win the \"Who Wants to be a Stuntman?\" challenge.",
	"There's just one more star to earn.  After that, I'm sure you'll get an offer to help improve another hospital.",
	"Welcome to the Albuquerque Medical Center.  With your help, we'll grow into the best family hospital around.",
	"Let's put a television to help keep the kids calm while they're in the waiting area.  I'll bet they all love that show with the talking dingo.",
	"There have been a lot of emergency calls today.  Expect to see a lot of patients that need Quick Treatment help.",
	"The demand for mothers needing Babyscans is rising.  Buying one for the hospital would benefit both us and our patients.",
	"The word is spreading about how great a job you're doing here!  I expect that we'll see more families coming to us today.",
	"Having equipment break on you can be very frustrating!  There must be a way to keep them in tip-top shape.",
	"We've got an offer from an excellent Dentist to set up shop here in our hospital.  Make sure there's room for him to work.",
	"I've never seen it so busy!  There are a lot of patients today that need to use all of our equipment.",
	"You've really helped turn this hospital into an amazing family center.  We'll be sad to see you go.  Thanks so much!",
	"Welcome to the Minneapolis Emergency Center.  We could use your help to expand our therapy services to multiple floors.",
	"Use the space on the different floors wisely.  Planning the layout of the hospital will make everything more efficient.",
	"A lot of patients have scheduled X-rays today.  Be sure to have enough equipment available.",
	"A local walk-a-thon is causing a lot of people to come in today that simply need bed rest.",
	"We would like to expand our operation services.  Please purchase enough equipment to satisfy the growing demand.",
	"The recent snowy weather has caused many car accidents on the roads.  Be ready for many patients.",
	"We have a new Skin Treatment center that has become real popular in Hollywood.  Try it out!",
	"We've received many calls from patients that are on their way here.  Be prepared for anything.",
	"We can't thank you enough for the wonderful effect you've had on our hospital.  The patients all love you!",
	"The Portland Emergancy Center is known for the special care it offers senior citizens.  We're especially proud of our 2nd floor Cardiology Center.",
	"A turbulent stock market has increased our patients' need for Cardiology treatment.",
	"The Hospital wants to purchase a Cat Scan to better diagnose our patients.",
	"Sometimes the elderly require a lot of pills and a lot of bedrest in order to feel better.",
	"Many of our patients suffer from deteriorating eyesight.  Let's add an Opthamology center.",
	"The DMV is cracking down on people that can't see well enough to drive.  I'm sure many patients will need Ophthalmology help today.",
	"The new extreme swing dancing craze has lead to a lot of broken hips.  These patients need emergancy operations right away!",
	"We've been receiving so many wonderful comments from our patients about you.  No wonder our reputation has improved.",
	"This has really become a fantastic center for helping senior citizens.  It's been a pleasure to have you on call.",
	"Welcome to New Orleans Suburb Hospital.  We get a little busier than you're probably used to seeing.",
	"Feel free to change the layout of the hospital so that you can do your best work.",
	"A new Ultrascan machine has become available to better diagnose our patients.",
	"That Ultrascan is pretty weird, huh? Technology is amazing these days.",
	"No one ever asks for Quick Treatment anymore.  Let's sell that old machine and buy a Cardiology Center.",
	"Word about the new Cardiology Center has gotten out.  Expect to see more requests for it today.",
	"I've noticed that many patients like to visit the hospital during your shift.  It must be that great care you provide.",
	"You're doing a great job. Don't forget about the coffee!",
	"Today is going to be busy, so don't forget to use the ambulence if you start to feel overwhelmed.",
	"Welcome to the Los Angeles Central Hospital.  Recent renovations have left our three story hosiptal with too much empty space.",
	"We need to add some more equipment right away.  I would suggest an Babyscan machine before anything else.",
	"A CAT scan is crucial to the success of any major hospital.  Be sure we don't have to turn away any patients that might need it.",
	"Patients needing X-rays are on the rise.  Our hospital should be the best place to help patients with those needs.",
	"A proper Cardiology center is necessary for the patients coming in today.  Are we prepared to handle the increased volume?",
	"The hospital is growing fast!  The word is spreading and we expect even more patients today than ever before.",
	"A machine malfunction at another hospital has caused an influx of patients needing operations.  Be ready.",
	"In order to offer extended services to our patients, we should have the proper Psychiatry and Physiotherapy equipment on hand.",
	"Wow!  It's hard to believe how quickly our hospital has expanded!  You should be very proud of yourself.",
	"Welcome to New York Central Hospital.  Don't expect the work here to be easy.  Only the best nurses can do well here.",
	"Get an Ophthalmology station in here stat!  It's cheap and we've got patients asking for it.",
	"New York can be a stressful city sometimes.  Let's provide Psychiatry treatment to help our patients cope.",
	"It seems like some bad weather is on the way.  Many expecting mothers are asking to use a Babyscan before it hits.",
	"This cold weather is making a lot of patients complain about toothaches.  Let's get a Dentist in here to help out.",
	"We need a Skin Treatment center.  Too many people out there have clogged pores.",
	"The ice and snow build up is causing a lot of people to slip and hurt themselves.  Physiotherapy is a must.",
	"After working for awhile in New York, I thought that I had seen everything.  But those Ultrascans baffle me.  We need one.",
	"It doesn't get more hectic than this!  Expect to see patients today that need every kind of help.  Best of luck to you!"		  
  };
  
  private void showBriefingScene(){
    HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable() {		
      @Override
	  public void run() {
	    inGameMenuScene.showBriefingScene(brifStrings[9 * GameStatManager.getInstance().getHospitalId() + GameStatManager.getInstance().getHospitalLevel()], hospitalUI.getGoalPatient(), hospitalUI.getGoalPatient() * 50);  		
	  	setChildScene(inGameMenuScene, false, true, true);	
	  }
	});	  
  }
  
  private void showHospitalDetailScene(){
    HospitalGameActivity.getGameActivity().sendSetChildScene(this, new HospitalDetailScene(ResourceManager.getInstance().getHospitalName(hospitalId), GameStatManager.getInstance().getDayCount() + 1, level + 1, this));	  
  }
  
  @Override
  public void onFinishShowHospitalDetail() {
    showBriefingScene();  	
  }
  
  
  @Override
  public void onAirplaneArrivedHospital() {
    showHospitalDetailScene();  	
  }
  
  public void startPlay(){
    hospitalUI.startTimer();
    hospital.startPlay();	  
  }
  
  private void upgrade(){
	//hospitalUI.upgrade();
	//sendSetChildScene(inGameMenuScene);
	setChildScene(inGameMenuScene, false, true, true);
	inGameMenuScene.showUpgradeMenu();
  }
  
  public boolean buy(int buildingType){
    BuildingInfo[] list = getUpgradeBuildingInfoWithType(buildingType, true);
    if(list == null){
      Toast.makeText(HospitalGameActivity.getGameActivity(), "Not enough space", Toast.LENGTH_SHORT).show();
      return false;
    }
    hospital.buy(hospitalId, list);
    return true;
  }
  
  public boolean sell(int buildingType){    
	BuildingInfo[] list = getUpgradeBuildingInfoWithType(buildingType, false);
	if(list == null){
	  Toast.makeText(HospitalGameActivity.getGameActivity(), "No station to sell", Toast.LENGTH_SHORT).show();
	  return false;
	}
    hospital.sell(buildingType, list);
    return true;
  }
  
  
  
  private BuildingInfo[] getUpgradeBuildingInfoWithType(int buildingType, boolean buy){
	Log.d("RokejitsX", "getUpgradeBuildingInfoWithType ========================================================== "+buildingType);
    Vector<BuildingInfo> list = new Vector<BuildingInfo>();
    for(int i = 0;i < buildingInfoList.length;i++){
      BuildingInfo info = buildingInfoList[i];
      Log.d("RokejitsX", "info "+i+" = "+info.getBuildingId());
      switch(buildingType){
        case Building.PLANT:
        case Building.WATER:
        case Building.FOOD:        	
        case Building.TELEVISION:
        case Building.BED:
          if(buy){
            if(info.getBuildingId() == buildingType && !info.isEnable()){
              list.add(info);	   
            }
          }else{
        	if(info.getBuildingId() == buildingType && info.isEnable()){
              list.add(info);	   
            }	  
          }
        break;
        default:
          if(buy){
            if(info.getBuildingId() == Building.NONE){
              info.setBuildingId(buildingType);
              list.add(info);
            }
          }else{
        	if(info.getBuildingId() == buildingType)
              list.add(info);    
          }
        break;       
      }
    }
    
    
    BuildingInfo[] result = null;
    if(list.size() > 0){
      result = new BuildingInfo[list.size()];
      list.copyInto(result);
    }
    return result;
  }
  
  /*private void initHospitalUi(int hospitalId, int level, int maxFloor){
    LevelInfoReader levelInfoReader = new LevelInfoReader();
    LevelInfo levelInfo = levelInfoReader.readLevel(hospitalId, level);
    hospitalUI = new HospitalUI(maxFloor);
    hospitalUI.setHospitalUIItemListener(hospital);
    hospitalUI.setHospitalTimerListener(this);
    hospitalUI.setHospitalFloorListener(this);   
    
    hospitalUI.setTime(levelInfo.getTime()10);
    hospitalUI.setGoalPatient(levelInfo.getObjective());
    hospitalUI.setExpertPatient(levelInfo.getExpertObjective());
    hospitalUI.setMoney(0);
    
    hospital.setMachineBreakCount(levelInfo.getMachineBreakCount(), levelInfo.getTime());
    hospital.setPatientMaxCount(levelInfo.getPatientCount(), levelInfo.getTime());
    
    
  }*/
  
  private void clear(){
	
	Vector<Entity> removeList = new Vector<Entity>();
    if(hospital != null && hospital.getParent() != null){
      removeList.add(hospital);
      //hospital.detachSelf();
    }	  
    
    if(hospitalUI != null && hospitalUI.getParent() != null){
      removeList.add(hospitalUI);
      //hospitalUI.detachSelf();
    }
    if(hospital != null)
      hospital.unLoad();
    
    if(hospitalUI != null)
      hospitalUI.unload();
    hospital = null;
    hospitalUI = null;       
    if(removeList.size() != 0)
      HospitalGameActivity.getGameActivity().sendDeattachChild(removeList);   
    
    if(bgTextureAtlas != null){	  
  	  HospitalGameActivity.getGameActivity().sendUnloadTextureAtlas(bgTextureAtlas);
  	  //bgTextureAtlas.clearTextureAtlasSources();
  	  hospitalBg = null;
  	  bgTextureAtlas = null;
  	}
  }
  
  
  
  
  
  

  @Override
  public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {	
	if(hospitalUI.onSceneTouchEvent(pScene, pSceneTouchEvent))
	  return true;
	Log.d("RokejitsX", "hospital touch event");
	if(hospital.onSceneTouchEvent(pScene, pSceneTouchEvent))
	  return true;	
	return false;
  }

  @Override
  public void onHospitalFloorSelected(int floor) {
	hospital.setFloor(floor);
	
  }

  @Override
  public void onTimeout() {	  
	GameStatManager gameStat = GameStatManager.getInstance();
	final boolean isPass = hospitalUI.getTreatedPatient() >= hospitalUI.getGoalPatient();
	if(isPass){
	  gameStat.setDayCount(gameStat.getDayCount() + 1);
	  if(level == 8){	  
	    gameStat.setHospitalId(hospitalId + 1);  
	    gameStat.setHospitalLevel(0);
	    gameStat.setDayCount(0);
	    gameStat.saveBuilding(null);
	  }else{
	    gameStat.setHospitalLevel(level + 1);
	    gameStat.saveBuilding(buildingInfoList);
	    /*sendSetChildScene(new LoadingScene());
	    loadHospitalLevel(buildingInfoList, routeManagerList, hospitalId, hospital.getMaxFloor());*/
	  }
	  
	  
	  gameStat.setTotalMoney(UpgradeData.getInstance().getFunds() + hospitalUI.getMoney());
	  
	  gameStat.setPhamacy(UpgradeData.getInstance().getPhamacy());
	  gameStat.setSalaries(UpgradeData.getInstance().getSalaries());
	  gameStat.setRed(UpgradeData.getInstance().getRed());
	  gameStat.setBlue(UpgradeData.getInstance().getBlue());
	  gameStat.setGreen(UpgradeData.getInstance().getGreen());
	  
	  
	  
	  gameStat.saveStat();
	  int[] avaiableBuildingType = ResourceManager.getInstance().getBuildingListInLevel(hospitalId, level);
	  int[] availableList = new int[STATIC_BUILDING.length + avaiableBuildingType.length];			
	  System.arraycopy(STATIC_BUILDING, 0, availableList, 0, STATIC_BUILDING.length);
	  System.arraycopy(avaiableBuildingType, 0, availableList, STATIC_BUILDING.length, avaiableBuildingType.length);
		
	  gameStat.saveAppearedBuilding(availableList, true);
	}
	
	hospital.timeOut();	
	HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable() {		
      @Override
	  public void run() {
		inGameMenuScene.showEndLevelMenuScene(hospitalUI.getGoalPatient(), hospital.getPatientLeavingHospitalCount(), 
				                              hospital.getPatientLeavingByTranspoter(), hospitalUI.getTreatedPatient(), hospitalUI.getMoney(), 
				                              isPass);
		
		setChildScene(inGameMenuScene, false, true, true);	
	  }
	});
	
	
	/*sendSetChildScene(inGameMenuScene);
	
	if(hospitalUI.getTreatedPatient() >= hospitalUI.getGoalPatient()){
	  inGameMenuScene.showNextLevelMenuScene();	
	}else{
	  inGameMenuScene.showRestartLevelMenuScene();	
	}*/
  }

  @Override
  public void onTimeRunningOut() {
    hospital.timeRunningOut(); 	
  }

  @Override
  public void onUiBtnClicked(int btnType) {
	if(btnType == BTN_CANCEL){
      hospital.upgradeComplete();
	}else{
      sendSetChildScene(inGameMenuScene);
	  inGameMenuScene.showPauseMenu();
	  
	}
	
  }

  @Override
  public void onUpgradeCompleted(int action, int buildingType) {
	/*if(action != -1){
	  if(action == HospitalGamePlay.STATE_BUY){
	    inGameMenuScene.onBuy(buildingType, 0);	  
	  }else{
		inGameMenuScene.onSell(buildingType, 0);  
	  }	
	}*/
    upgrade();
    
	/*sendSetChildScene(inGameMenuScene);       
    //inGameMenuScene.showChooseBuildingMenuScene();
	inGameMenuScene.showUpgradeMenu();
	
	hospitalUI.upgrade();
	sendSetChildScene(inGameMenuScene);
	inGameMenuScene.showUpgradeMenu();*/
	  
	
  }

  @Override
  public boolean onBuy(int buildingType) {
	if(buy(buildingType)){
	  inGameMenuScene.back();
	  return true;
	}
	inGameMenuScene.showUpgradeMenu();
	return false;
  }

  @Override
  public boolean onSell(int buildingType) {
	if(sell(buildingType)){
	  inGameMenuScene.back();
	  return true;
	}
	inGameMenuScene.showUpgradeMenu();
	return false;
  }

  @Override
  public void onContinue() {	
    startPlay();	
    inGameMenuScene.back();
	
  }

  @Override
  public void onRestart(boolean needUpdateDayCount) {	
    //clearChildScene();
	setChildScene(new LoadingScene(), true, false, false);
    if(needUpdateDayCount){
	  GameStatManager gameStat = GameStatManager.getInstance();
	  gameStat.setDayCount(gameStat.getDayCount() + 1);
	  gameStat.saveStat();
    }
	loadHospitalLevel(GameStatManager.getInstance().loadBuilding(), routeManagerList, hospitalId, hospital.getMaxFloor());   	
  }

  @Override
  public void onNextLevel() {	
	if(level == 8){
	  if(hospitalId == 6){		
		GameStatManager gameStat = GameStatManager.getInstance();
		gameStat.setHospitalId(0);
		gameStat.setHospitalLevel(0);
		gameStat.setDayCount(0);
		gameStat.saveStat();
		gameStat.saveBuilding(null);
		onBackToMainMenu();
	  }else{
		sendSetChildScene(new LoadingScene());
		GameStatManager.getInstance().saveBuilding(null);
	    loadHospital();
	  }
	}else{	  
	  sendSetChildScene(new LoadingScene());
	  loadHospitalLevel(buildingInfoList, routeManagerList, hospitalId, hospital.getMaxFloor());
	}
	
	//int[] avaiableBuildingType = ResourceManager.getInstance().getBuildingListInLevel(hospitalId, level);
	/*Vector<Integer> list = new Vector<Integer>();
	for(int i = 0;i < buildingInfoList.length;i++){
	  int buildingType = buildingInfoList[i].getBuildingId();
	  if(buildingType == Building.PLANT ||
	     buildingType == Building.WATER ||
	     buildingType == Building.TELEVISION ||
	     buildingType == Building.FOOD ||
	     buildingType == Building.BED){
	     if(!buildingInfoList[i].isEnable())
	       buildingType = -1;
	  }
	  
	  if(buildingType != -1){
	    if(!list.contains(buildingType))
	      list.add(buildingType);
	  }
	  
	}
	
	int[] buildingList = new int[list.size()];
	for(int i = 0;i < list.size();i++){
	  buildingList[i] = list.elementAt(i);	
	}*/
	
	
    //this.getChildScene().back();	
  }

  @Override
  public void onQuitGame() {
	HospitalGameActivity.getGameActivity().finish();
	
  } 
  
  @Override
  public void onBackToMainMenu() {
	clear();
	HospitalGameActivity.getGameActivity().showMainMenu();	
  }  
  
  
  
  
  //private boolean needMapScene = false;
  public void loadHospital() {	
	clear();
	//hospitalId = 6;	
    new HospitalLoader(GameStatManager.getInstance().getHospitalId(), new LoaderListener() {		
      @Override
	  public void onLoadFinish(final Loader loader) {	
		HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable(){
		  public void run(){
			HospitalLoader hLoader = (HospitalLoader) loader;		 
			//needMapScene = hLoader.isNeedMapScreen();
		    loadHospitalLevel(hLoader.getBuildingInfoList(), hLoader.getRouteManagerList(), hLoader.getHospitalId(), hLoader.getMaxFloor());		    
		  }	
		});
		
	  }
      
	}).startLoad();
      
    if(getChildScene() == null || !(getChildScene() instanceof LoadingScene))
      sendSetChildScene(new LoadingScene());
	
  }

  @Override
  public void onFloorChanged(int floor) {
	setBackground(hospitalBg[floor]);
	
  }











    
  



   	
} 