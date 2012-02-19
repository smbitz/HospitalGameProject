package com.rokejitsx;

import java.util.Vector;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;
import android.widget.Toast;

import com.rokejitsx.audio.SoundPlayerManager;
import com.rokejitsx.data.loader.GamePlayLoader;
import com.rokejitsx.data.loader.HospitalLoader;
import com.rokejitsx.data.loader.Loader;
import com.rokejitsx.data.loader.LoaderListener;
import com.rokejitsx.data.loader.LoadingScene;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.route.RouteManager;
import com.rokejitsx.data.xml.HospitalLevelReader.BuildingInfo;
import com.rokejitsx.data.xml.LevelInfoReader;
import com.rokejitsx.data.xml.LevelInfoReader.LevelInfo;
import com.rokejitsx.menu.InGameMenuScene;
import com.rokejitsx.menu.InGameMenuScene.InGameMenuSceneListener;
import com.rokejitsx.menu.MainMenuScene;
import com.rokejitsx.menu.MainMenuScene.MyMenuListener;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.hospital.HospitalFloorSelector.FloorSelectListener;
import com.rokejitsx.ui.hospital.HospitalGamePlay;
import com.rokejitsx.ui.hospital.HospitalGamePlay.FloorChangeListener;
import com.rokejitsx.ui.hospital.HospitalGamePlay.UpgradeHospitalListener;
import com.rokejitsx.ui.hospital.HospitalTimer.HospitalTimerListener;
import com.rokejitsx.ui.hospital.HospitalUI;
import com.rokejitsx.ui.hospital.HospitalUIListener;


public class GamePlay extends Scene implements IOnSceneTouchListener, HospitalTimerListener, 
                                               FloorSelectListener, HospitalUIListener, UpgradeHospitalListener, 
                                               InGameMenuSceneListener, LoaderListener, MyMenuListener, FloorChangeListener { 
  
  private int hospitalId, level;	
  private BuildingInfo[] buildingInfoList;
  private RouteManager[] routeManagerList;
  private HospitalUI hospitalUI;
  private HospitalGamePlay hospital;
  private SpriteBackground[] hospitalBg;
  private BitmapTextureAtlas bgTextureAtlas;
  private InGameMenuScene inGameMenuScene;
  private MainMenuScene mainMenuScene;
  
  
  //private Vector<BuildingInfo> fixedBuildingList, dropAreaBuildingList, equipmentBuildingList;  
  
  
  public GamePlay(){		
	inGameMenuScene = new InGameMenuScene(this);
	mainMenuScene = new MainMenuScene(this);
    setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));  
    this.setOnSceneTouchListener(this);    
    sendSetChildScene(mainMenuScene);
    mainMenuScene.showStartMenu();
  } 
  
  
  
  private void sendSetChildScene(Scene child){
    HospitalGameActivity.getGameActivity().sendSetChildScene(this, child);	  
  }
  
  public void loadGamePlay(BuildingInfo[] buildingInfoList, RouteManager[] routeManagerList,int hospitalId, int level, int maxFloor){
	this.hospitalId = hospitalId;
	this.level = level;
	this.buildingInfoList= buildingInfoList;
	this.routeManagerList = routeManagerList;
	
	clear();
	new GamePlayLoader(buildingInfoList, routeManagerList, hospitalId, level, maxFloor, this).startLoad();    
  }
  
  @Override
  public void onLoadFinish(Loader loader) {
	if(loader instanceof GamePlayLoader){
	  GamePlayLoader gLoader = (GamePlayLoader) loader;
	  hospitalBg = gLoader.getHospitalBackground();
	  bgTextureAtlas = gLoader.getBgTextureAtlas();
	  inGameMenuScene.setBuildingList(ResourceManager.getInstance().getBuildingListInLevel(hospitalId, level));
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
    if(getChildScene() != null)
      getChildScene().back();
    /*setChildScene(inGameMenuScene);
    inGameMenuScene.showUpgradeMenu();*/
    upgrade();
  }
  
  public void startPlay(){
    hospitalUI.startTimer();
    hospital.startPlay();	  
  }
  
  private void upgrade(){
	hospitalUI.upgrade();
	sendSetChildScene(inGameMenuScene);
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
    Vector<BuildingInfo> list = new Vector<BuildingInfo>();
    for(int i = 0;i < buildingInfoList.length;i++){
      BuildingInfo info = buildingInfoList[i];
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
    
    hospital = null;
    hospitalUI = null;       
    if(removeList.size() != 0)
      HospitalGameActivity.getGameActivity().sendDeattachChild(removeList);   
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
	hospital.timeOut();    	
	sendSetChildScene(inGameMenuScene);
	if(hospitalUI.getTreatedPatient() >= hospitalUI.getGoalPatient()){
	  inGameMenuScene.showNextLevelMenuScene();	
	}else{
	  inGameMenuScene.showRestartLevelMenuScene();	
	}
  }

  @Override
  public void onTimeRunningOut() {
    hospital.timeRunningOut(); 	
  }

  @Override
  public void onUiBtnClicked(int btnType) {
    hospital.upgradeComplete();
	
  }

  @Override
  public void onUpgradeCompleted() { 
    //upgrade();
	sendSetChildScene(inGameMenuScene);       
    //inGameMenuScene.showChooseBuildingMenuScene();
	inGameMenuScene.showUpgradeMenu();
	  
	
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
  public void onRestart() {	
    sendSetChildScene(new LoadingScene());
    loadGamePlay(buildingInfoList, routeManagerList, hospitalId, level, hospital.getMaxFloor());	
  }

  @Override
  public void onNextLevel() {	
	if(level == 8)
	  if(hospitalId == 6){
		sendSetChildScene(mainMenuScene);
	    mainMenuScene.showStartMenu();
	  }else{
		sendSetChildScene(new LoadingScene());
	    onGamePlayLoad(hospitalId + 1);
	  }
	else{
	  sendSetChildScene(new LoadingScene());
      loadGamePlay(buildingInfoList, routeManagerList, hospitalId, level + 1, hospital.getMaxFloor());
	}
    //this.getChildScene().back();	
  }

  @Override
  public void onQuitGame() {
	HospitalGameActivity.getGameActivity().finish();
	
  }

  @Override
  public void onGamePlayLoad(int hospitalId) {	  
	
	if(bgTextureAtlas != null)
	  HospitalGameActivity.getGameActivity().getEngine().getTextureManager().unloadTexture(bgTextureAtlas);
    new HospitalLoader(hospitalId, new LoaderListener() {		
      @Override
	  public void onLoadFinish(Loader loader) {	
			
	    HospitalLoader hLoader = (HospitalLoader) loader;		    
	    loadGamePlay(hLoader.getBuildingInfoList(), hLoader.getRouteManagerList(), hLoader.getHospitalId(), 0, hLoader.getMaxFloor());	
	    if(getChildScene() == null || !(getChildScene() instanceof LoadingScene))
		sendSetChildScene(new LoadingScene());
		    //gamePlay.upgrade();	    
	  }
	}).startLoad();   	
	
  }

  @Override
  public void onFloorChanged(int floor) {
	setBackground(hospitalBg[floor]);
	
  }    
  



   	
} 