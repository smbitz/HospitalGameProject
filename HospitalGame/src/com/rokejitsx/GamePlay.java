package com.rokejitsx;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.TextMenuItem;
import org.anddev.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.xmlpull.v1.XmlPullParserException;

import android.graphics.Color;

import com.rokejitsx.HospitalGameActivity.DeAttachChildThread;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.route.RouteManager;
import com.rokejitsx.data.xml.HospitalLevelReader;
import com.rokejitsx.data.xml.HospitalLevelReader.BuildingInfo;
import com.rokejitsx.data.xml.LevelInfoReader;
import com.rokejitsx.data.xml.LevelInfoReader.LevelInfo;
import com.rokejitsx.ui.hospital.Hospital;
import com.rokejitsx.ui.hospital.HospitalFloorSelector.FloorSelectListener;
import com.rokejitsx.ui.hospital.HospitalTimer.HospitalTimerListener;
import com.rokejitsx.ui.hospital.HospitalUI;


public class GamePlay extends Scene implements IOnSceneTouchListener, HospitalTimerListener, FloorSelectListener, IOnMenuItemClickListener { 
  private static final int MENU_PLAY     = 0;	
  private static final int MENU_QUIT     = 1;
  private static final int MENU_RESUME   = 2;  
  private static final int MENU_RESTART  = 3;
  private static final int MENU_HOSPITAL1 = 4;
  private static final int MENU_HOSPITAL2 = 5;
  private static final int MENU_HOSPITAL3 = 6;
  private static final int MENU_HOSPITAL4 = 7;
  private static final int MENU_HOSPITAL5 = 8;
  private static final int MENU_HOSPITAL6 = 9;
  private static final int MENU_HOSPITAL7 = 10;
	
  private HospitalUI hospitalUI;
  private Hospital hospital;
  private MenuScene startMenu, pauseMenu, endMenu, hospitalMenu;
  private BitmapTextureAtlas mFontTexture;
  private Font mFont;
  private Vector<BuildingInfo> fixedBuildingList, dropAreaBuildingList, equipmentBuildingList;  
  
  
  public GamePlay(){	
	initFont();
	initMenu();	
    setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));  
    this.setOnSceneTouchListener(this);
    
    
    HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable(){
      public void run(){
        setChildScene(startMenu, false, true, true);	  
      }	
    });
    
  }
  
  public void loadGamePlay(int hospitalId, int level){
    clear();  	  
    ResourceManager.getInstance().loadLevel(hospitalId);
    fixedBuildingList = new Vector<BuildingInfo>();
    dropAreaBuildingList = new Vector<BuildingInfo>();
    equipmentBuildingList = new Vector<BuildingInfo>();    
    int[] levelInfo = ResourceManager.getInstance().getHospitalXmlIds(hospitalId);   
    int maxFloor = levelInfo.length; 
    RouteManager[] routeManagerList = new RouteManager[maxFloor];
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
      hospitalLevelReader.getFixedBuilding(fixedBuildingList);
      hospitalLevelReader.getDropBuilding(dropAreaBuildingList);
      hospitalLevelReader.getEquipmentBuilding(equipmentBuildingList);
      routeManagerList[i] = hospitalLevelReader.getRoute();
    }    
    
    Vector<BuildingInfo> buildingList = new Vector<BuildingInfo>();
    
    Enumeration<BuildingInfo> e = fixedBuildingList.elements();
    while(e.hasMoreElements()){
      buildingList.add(e.nextElement());	
    }
    
    e = dropAreaBuildingList.elements();
    while(e.hasMoreElements()){
      buildingList.add(e.nextElement());	
    }
    
    e = equipmentBuildingList.elements();
    while(e.hasMoreElements()){
      buildingList.add(e.nextElement());	
    }
    
    BuildingInfo[] buildingInfoList = new BuildingInfo[buildingList.size()];
    buildingList.copyInto(buildingInfoList);
    
    
    
    hospital = new Hospital(maxFloor);
    hospital.initialBg(hospitalId, maxFloor);
    hospital.loadBuilding(hospitalId, buildingInfoList);
    hospital.initRouteManager(routeManagerList);
    hospital.initialNurse();
    
    
    initHospitalUi(hospitalId,level,maxFloor);
    hospitalUI.setElevetorSelectorListener(hospital);
    hospital.setHospitalListener(hospitalUI);
    hospital.setFloor(0);
    attachChild(hospital);    
    attachChild(hospitalUI);
    hospitalUI.startTimer();
    hospital.startPlay();
  }
  
  private void initHospitalUi(int hospitalId, int level, int maxFloor){
    LevelInfoReader levelInfoReader = new LevelInfoReader();
    LevelInfo levelInfo = levelInfoReader.readLevel(hospitalId, level);
    hospitalUI = new HospitalUI(maxFloor);
    hospitalUI.setHospitalTimerListener(this);
    hospitalUI.setHospitalFloorListener(this);   
    
    hospitalUI.setTime(levelInfo.getTime());
    hospitalUI.setGoalPatient(levelInfo.getObjective());
    hospitalUI.setExpertPatient(levelInfo.getExpertObjective());
    hospitalUI.setMoney(0);
    
    
  }
  
  private void clear(){
	Vector<Entity> removeList = new Vector<Entity>();
    if(hospital != null && hospital.getParent() != null){
      removeList.add(hospital);
      this.detachChild(hospital);
    }	  
    
    if(hospitalUI != null && hospitalUI.getParent() != null){
      removeList.add(hospitalUI);
      this.detachChild(hospitalUI);
    }
    
    hospital = null;
    hospitalUI = null;
    fixedBuildingList = null;
    dropAreaBuildingList = null;
    equipmentBuildingList = null;   
    if(removeList.size() != 0)
      HospitalGameActivity.getGameActivity().sendDeattachChild(this, removeList);   
  }
  
  private void initFont(){
    /* Load Font/Textures. */
	HospitalGameActivity gameAct = HospitalGameActivity.getGameActivity();  
	this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	FontFactory.setAssetBasePath("font/");
	this.mFont = FontFactory.createFromAsset(this.mFontTexture, gameAct, "Plok.ttf", 48, true, Color.WHITE);
	gameAct.getEngine().getTextureManager().loadTexture(this.mFontTexture);
	gameAct.getFontManager().loadFont(this.mFont);	  
	
  }
  
  private void initMenu(){	
	startMenu = initMenuScene(); 
	startMenu.addMenuItem(initMenuItem("PLAY", MENU_PLAY));
	startMenu.addMenuItem(initMenuItem("QUIT", MENU_QUIT));
	startMenu.buildAnimations();
	
	pauseMenu = initMenuScene();
	pauseMenu.addMenuItem(initMenuItem("RESUME", MENU_RESUME));
	pauseMenu.addMenuItem(initMenuItem("QUIT", MENU_QUIT));
	pauseMenu.buildAnimations();
	
	endMenu = initMenuScene();
	endMenu.addMenuItem(initMenuItem("RESTART", MENU_RESTART));
	endMenu.addMenuItem(initMenuItem("QUIT", MENU_QUIT));
	endMenu.buildAnimations();
	
	hospitalMenu = initMenuScene();
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 1", MENU_HOSPITAL1));
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 2", MENU_HOSPITAL2));
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 3", MENU_HOSPITAL3));
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 4", MENU_HOSPITAL4));
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 5", MENU_HOSPITAL5));
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 6", MENU_HOSPITAL6));
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 7", MENU_HOSPITAL7));
	
	hospitalMenu.buildAnimations();
	
  }
  
  private MenuScene initMenuScene(){
	MenuScene menuScene = new MenuScene(HospitalGameActivity.getGameActivity().getCamera());    
	menuScene.setBackgroundEnabled(false);
    menuScene.setOnMenuItemClickListener(this);	  
    return menuScene;
  }
  
  private IMenuItem initMenuItem(String label, int id){
    IMenuItem menuItem = new ColorMenuItemDecorator(new TextMenuItem(id, mFont, label), 1.0f,0.0f,0.0f, 0.0f,0.0f,0.0f);
    menuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);    
    return menuItem;
		  
  }

  @Override
  public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {	
	if(hospitalUI.onSceneTouchEvent(pScene, pSceneTouchEvent))
	  return true;
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
    HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable(){
      public void run(){
        setChildScene(endMenu, false, true, true);	  
      }	
    });	
  }

  private int hospitalId;
  @Override
  public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
		float pMenuItemLocalX, float pMenuItemLocalY) {
    switch(pMenuItem.getID()){
      case MENU_PLAY:
    	  HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable(){
    	      public void run(){
    	        setChildScene(hospitalMenu, false, true, true);	  
    	      }	
    	    });
        startMenu.back();
      break;
      case MENU_RESUME:
       pauseMenu.back();
      break;
      case MENU_RESTART:
    	  HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable(){
           	  public void run(){
           	    loadGamePlay(hospitalId, 0);
           	  }	
           	});
    	
        endMenu.back();
      break;
      case MENU_QUIT:
        HospitalGameActivity.getGameActivity().finish();
      break;
      case MENU_HOSPITAL1:
      case MENU_HOSPITAL2:
      case MENU_HOSPITAL3:
      case MENU_HOSPITAL4:
      case MENU_HOSPITAL5:
      case MENU_HOSPITAL6:
      case MENU_HOSPITAL7:
    	hospitalId = pMenuItem.getID() - 4; 
        HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable(){
       	  public void run(){
       	    loadGamePlay(hospitalId, 0);
       	  }	
       	});
        hospitalMenu.back();
      break;
    }	
	return false;
  }

   	
} 