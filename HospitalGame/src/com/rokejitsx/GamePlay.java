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

import com.rokejitsx.data.loader.HospitalLevelLoader;
import com.rokejitsx.data.loader.HospitalLoader;
import com.rokejitsx.data.loader.Loader;
import com.rokejitsx.data.loader.LoaderListener;
import com.rokejitsx.data.loader.LoadingScene;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.route.RouteManager;
import com.rokejitsx.data.xml.HospitalLevelReader.BuildingInfo;
import com.rokejitsx.menu.InGameMenuScene2;
import com.rokejitsx.menu.InGameMenuScene2.InGameMenuScene2Listener;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.hospital.HospitalFloorSelector.FloorSelectListener;
import com.rokejitsx.ui.hospital.HospitalGamePlay;
import com.rokejitsx.ui.hospital.HospitalGamePlay.FloorChangeListener;
import com.rokejitsx.ui.hospital.HospitalGamePlay.UpgradeHospitalListener;
import com.rokejitsx.ui.hospital.HospitalTimer.HospitalTimerListener;
import com.rokejitsx.ui.hospital.HospitalUI;
import com.rokejitsx.ui.hospital.HospitalUIListener;
import com.zurubu.scene.MenuScene;


public class GamePlay extends Scene implements IOnSceneTouchListener, HospitalTimerListener, 
                                               FloorSelectListener, HospitalUIListener, UpgradeHospitalListener, 
                                               InGameMenuScene2Listener, LoaderListener, FloorChangeListener { 
  
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
  
  public void loadHospitalLevel(BuildingInfo[] buildingInfoList, RouteManager[] routeManagerList,int hospitalId, int level, int maxFloor){
	this.hospitalId = hospitalId;
	this.level = level;
	this.buildingInfoList= buildingInfoList;
	this.routeManagerList = routeManagerList;
	
	clear();
	new HospitalLevelLoader(buildingInfoList, routeManagerList, hospitalId, level, maxFloor, this).startLoad();    
  }
  
  @Override
  public void onLoadFinish(Loader loader) {
	if(loader instanceof HospitalLevelLoader){	  
	  HospitalLevelLoader gLoader = (HospitalLevelLoader) loader;
	  /*hospitalBg = gLoader.getHospitalBackground();
	  bgTextureAtlas = gLoader.getBgTextureAtlas();*/
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
    //upgrade();
    
    HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable() {		
        @Override
  	  public void run() {
  	    inGameMenuScene.showBriefingScene("xxxxx", hospitalUI.getGoalPatient(), 250);
  		
  		setChildScene(inGameMenuScene, false, true, true);	
  	  }
  	});
    
    
    
    
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
	
	
	HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable() {		
      @Override
	  public void run() {
		inGameMenuScene.showEndLevelMenuScene(hospitalUI.getGoalPatient(), hospital.getPatientLeavingHospitalCount(), 
				                              hospital.getPatientLeavingByTranspoter(), hospitalUI.getTreatedPatient(), hospitalUI.getMoney(), 
				                              hospitalUI.getTreatedPatient() >= hospitalUI.getGoalPatient());
		
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
    hospital.upgradeComplete();
	
  }

  @Override
  public void onUpgradeCompleted() { 
    upgrade();
	sendSetChildScene(inGameMenuScene);       
    inGameMenuScene.showChooseBuildingMenuScene();
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
    loadHospitalLevel(buildingInfoList, routeManagerList, hospitalId, level, hospital.getMaxFloor());	
  }

  @Override
  public void onNextLevel() {	
	if(level == 8)
	  if(hospitalId == 6){
		//sendSetChildScene(mainMenuScene);
	    //mainMenuScene.showStartMenu();
		/*HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable(){
		  public void run(){
		    HospitalGameActivity.getGameActivity().getEngine().setScene(MenuScene.getScene());	  
		  }	
		});*/
		onBackToMainMenu();
	  }else{
		sendSetChildScene(new LoadingScene());
	    loadHospital(hospitalId + 1);
	  }
	else{
	  sendSetChildScene(new LoadingScene());
	  loadHospitalLevel(buildingInfoList, routeManagerList, hospitalId, level + 1, hospital.getMaxFloor());
	}
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
  
  private float[][] bgOffset = {
    {-80, 0},
    {-80, 165},		  
    {-80, 340},
    {-80, 505}
  };
  
  private void initialBg(int hospital, int maxFloor){
	TextureRegion[] bgTextureRegion = new TextureRegion[maxFloor];
	hospitalBg = new SpriteBackground[maxFloor];
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
	
	for(int i = 0;i < hospitalBg.length;i++){
	  
	  float[] offset = bgOffset[i];
	  hospitalBg[i] = new SpriteBackground(new Sprite(0 + offset[0], -600 + offset[1], bgTextureRegion[i]));
	  //attachChild(bg[i], 0);
	}
		
  }
 
  public void loadHospital(int hospitalId) {	
	clear();
	if(bgTextureAtlas != null){	  
	  HospitalGameActivity.getGameActivity().sendUnloadTextureAtlas(bgTextureAtlas);
	  //bgTextureAtlas.clearTextureAtlasSources();
	  hospitalBg = null;
	  bgTextureAtlas = null;
	}
    new HospitalLoader(hospitalId, new LoaderListener() {		
      @Override
	  public void onLoadFinish(final Loader loader) {	
		HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable(){
		  public void run(){
			HospitalLoader hLoader = (HospitalLoader) loader;
		    initialBg(hLoader.getHospitalId(), hLoader.getMaxFloor());
		    loadHospitalLevel(hLoader.getBuildingInfoList(), hLoader.getRouteManagerList(), hLoader.getHospitalId(), 0, hLoader.getMaxFloor());	
		    
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