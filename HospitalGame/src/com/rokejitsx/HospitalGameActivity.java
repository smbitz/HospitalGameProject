
package com.rokejitsx;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

import com.rokejitsx.audio.SoundPlayerManager;
import com.rokejitsx.data.GameFonts;
import com.rokejitsx.data.loader.Loader;
import com.rokejitsx.data.loader.LoaderListener;
import com.rokejitsx.data.loader.ResourceLoader;
import com.rokejitsx.data.resource.MenuResourceManager;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.menu.shopmenu.UpgradeMenu;
import com.rokejitsx.save.GameStatManager;
import com.rokejitsx.save.ProfileManager;
import com.rokejitsx.ui.scene.HospitalDetailScene;
import com.rokejitsx.ui.scene.LoadingScene;
import com.rokejitsx.ui.scene.SplashScreen;
import com.rokejitsx.ui.scene.SplashScreen.SplashScreenListener;
import com.rokejitsx.ui.scene.map.MapScene;
import com.rokejitsx.ui.scene.map.MapScene.MapSceneListener;
import com.zurubu.scene.AppSharedPreference;
import com.zurubu.scene.MenuScene;
import com.zurubu.scene.TargetData;

public class HospitalGameActivity extends BaseGameActivity implements SplashScreenListener, MapSceneListener {
  private static final int CAMERA_WIDTH = 800;
  private static final int CAMERA_HEIGHT = 600;
  private Camera mCamera;
  
  private static HospitalGameActivity gameAct;  
  
  private ArrayList<TargetData> dataMode;
  private AppSharedPreference appSettings;
  private MenuScene menuScene;
  
  public static HospitalGameActivity getGameActivity(){
    
    return gameAct;	  
  }  
  
  
  
  
  @Override
  public void finish() {
	SoundPlayerManager.getInstance().releaseAll();
	GameFonts.getInstance().unLoadAllFonts();
	MenuResourceManager.getInstance().unLoadAll();
	//onDestroy();  
	super.finish();
  }
  
  public ArrayList<TargetData> getDataMode(){
    return dataMode;	  
  } 

  public AppSharedPreference getAppSettings(){
	return appSettings;	  
  }


  public int getCameraWidth(){	
    return CAMERA_WIDTH;	  
  }
  
  public int getCameraHeight(){
    return CAMERA_HEIGHT;	  
  }
  
  public Camera getCamera(){
	
    return mCamera;	  
  }
  
  
  
  @Override
  public Engine onLoadEngine() {
	
	gameAct = this;
	appSettings = new AppSharedPreference(this);
	dataMode = setData();
    this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
    Engine e = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(4, 3), this.mCamera)/*.setNeedsSound(true).setNeedsMusic(true)*/);
    //e.getEngineOptions().getTouchOptions().setRunOnUpdateThread(true);
	return e; 	
  }
  private ArrayList<TargetData> setData() {
	ArrayList<TargetData> data = new ArrayList<TargetData>();
	String[] title = {"Endless Easy", "Page2", "Page3", "Page4"};
	for (int j=0 ; j<4 ; j++) {
	  TargetData d = new TargetData();
	  d.title = title[j];
	  d.tx = new String[8];
	  d.value = new int[8];
	  for (int i=0 ; i<8 ; i++) {
	    d.tx[i] = title[j];
	 	d.value[i] = i*333;
	  }
	  data.add(d);
  	}
	return data;
  }
  //private MainMenuScene menuScene;
  @Override
  public void onLoadResources() {
	ProfileManager profileManager = ProfileManager.getInstance();
	profileManager.loadProfile();  
	  
	if(profileManager.getProfileCount() > 0){
	  GameStatManager.getInstance().setSaveName(profileManager.getCurrentProfileKey());
	  GameStatManager.getInstance().loadStat();
	}
	/*GameStatManager.getInstance().setSaveName("testsave6");
	GameStatManager.getInstance().loadStat();*/
	SoundPlayerManager.getInstance().reset();
    GameFonts.getInstance().loadFont(getTextureManager(), getFontManager());
     
		
  }
  

  @Override
  public Scene onLoadScene() {	
	this.mEngine.registerUpdateHandler(new FPSLogger());
	return new LoadingScene();
  }

  private int hospitalId = 0;
  
  @Override
  public void onLoadComplete() {
	ResourceLoader rLoader = new ResourceLoader(new LoaderListener() {	
		@Override
		public void onLoadFinish(Loader loader) {
		  //sendSetScene(new UpgradeMenu());
		  //showSplashScreen();
		  
		  
		  
		  
		  showMainMenu();		
		  /*hospitalId = -1;
		  showHospitalDetailScene();*/
		  
		  
		  //sendSetScene(new MapScene(hospitalId, gameAct));
		}
	});
	rLoader.startLoad();	
  } 
  
  private void showHospitalDetailScene(){
	hospitalId++;
	if(hospitalId == 8)
	  hospitalId = 0;
	Log.d("Rokejitsx", "ResourceManager.getInstance().getHospitalName(0) ============================================= "+ResourceManager.getInstance().getHospitalName(hospitalId));
    sendSetScene(new HospitalDetailScene(ResourceManager.getInstance().getHospitalName(hospitalId), 0, 0, new HospitalDetailScene.HospitalDetailSceneListener() {
			
		@Override
		public void onFinishShowHospitalDetail() {
		  showHospitalDetailScene();		
				
		}
	}));  
  }
  
  @Override
  public void onAirplaneArrivedHospital() {
	hospitalId++;
	if(hospitalId == 8)
	  hospitalId = 0;
    sendSetScene(new MapScene(hospitalId, gameAct));  	
  }
  
  private void showSplashScreen(){    
	runOnUpdateThread(new Runnable() {
		
		@Override
		public void run() {
		  SplashScreen screen = new SplashScreen(gameAct);
		  mEngine.setScene(screen);
		  screen.nextSplash();
			
		}
	});
	    
  }
  
  @Override
  public void onFinishShowSplashScreen() {
  	showMainMenu();
  	
  }
  
  public void showMainMenu(){
    runOnUpdateThread(new Runnable() {
		
		@Override
		public void run() {
		  if(menuScene != null){
		    menuScene.unload();
		    menuScene = null;
		  }
		  
		  mEngine.setScene(menuScene = new MenuScene());
			
		}
	});	  
  }
  
  public void sendAttachChild(Entity parent, Entity entity){
    Vector<Entity> list = new Vector<Entity>();
	list.add(entity);
    sendAttachChild(parent, list);	   
  }
  
  public void sendAttachChild(Entity parent, Vector<Entity> list){
    runOnUpdateThread(new AttachChildThread(parent, list));	  
  }
  
  public void sendDeattachChild(Entity entity){
	Vector<Entity> list = new Vector<Entity>();
	list.add(entity);
    sendDeattachChild(list);  	  
  }
  
  public void sendDeattachChild(Vector<Entity> list){
    runOnUpdateThread(new DeAttachChildThread(list));	  
  }
  
  public void sendSetChildScene(Scene motherScene, Scene childScene){
    runOnUpdateThread(new SetChildSceneThread(motherScene, childScene));	  
  }
  
  public void sendSetScene(Scene scene){
    runOnUpdateThread(new SetSceneThread(scene));	  
  }
  
  public void sendUnloadTextureAtlas(BitmapTextureAtlas ba){
    Vector<BitmapTextureAtlas> v = new Vector<BitmapTextureAtlas>();
    v.add(ba);
    sendUnloadTextureAtlas(v);
    
  }
  
  public void sendUnloadTextureAtlas(Vector<BitmapTextureAtlas> list){
    runOnUpdateThread(new SendUnloadTextureAtlasThread(list));	  
  }
  
  public void sendDetachChildren(Entity entity){
    runOnUpdateThread(new SendDetachChildren(entity));	  
  }
  
  class SendDetachChildren implements Runnable{
	private Entity entity;
	
	public SendDetachChildren(Entity entity){
	  this.entity = entity;	
	}
	
    public void run(){
      entity.detachChildren();	
    }	    
  }
  
  class SendUnloadTextureAtlasThread implements Runnable{
    private Vector<BitmapTextureAtlas> unloadList; 
	   
    public SendUnloadTextureAtlasThread(Vector<BitmapTextureAtlas> list){
      this.unloadList = list;	
    }
    @Override
	public void run() {	
	  Enumeration<BitmapTextureAtlas> e = unloadList.elements();
	  while(e.hasMoreElements()){
	    BitmapTextureAtlas b = e.nextElement();
	    if(b == null)
	      continue;
	    mEngine.getTextureManager().unloadTexture(b);
	    b.clearTextureAtlasSources();	    
	  }
	}
	  
  }
  
  class SetSceneThread implements Runnable{
    private Scene scene;
    
    public SetSceneThread(Scene scene){
      this.scene = scene; 	
    }
	  
    public void run(){
      getEngine().setScene(scene);	
    }	  
  }
  
  class AttachChildThread implements Runnable{
    private Entity entity;
    private Vector<Entity> attachChild;
    
    public AttachChildThread(Entity entity, Vector<Entity> list){
      this.entity = entity;
      this.attachChild = list;
    }
    
	@Override
	public void run() {
	  for(int i = 0;i < attachChild.size();i++){
	    Entity child = attachChild.get(i);	  
	    entity.attachChild(child);
	  }
		
	}	  
  }
  
  class DeAttachChildThread implements Runnable{    
    private Vector<Entity> removeChild;
	
    public DeAttachChildThread(Vector<Entity> list){      
      this.removeChild = list;
    }
    
	@Override
	public void run() {
      
	  for(int i = 0;i < removeChild.size();i++){
	    Entity child = removeChild.get(i);	  
	    child.detachSelf();	    
	  }	
		
	}
	  
  }
  
  class SetChildSceneThread implements Runnable{
    
	private Scene motherScene, childScene;    
    public SetChildSceneThread(Scene motherScene,Scene childScene){
      this.motherScene = motherScene;
      this.childScene = childScene;
    }	 
    
    public void run(){
      if(childScene instanceof org.anddev.andengine.entity.scene.menu.MenuScene)
        ((org.anddev.andengine.entity.scene.menu.MenuScene)childScene).buildAnimations();
      motherScene.setChildScene(childScene, false, true, true);	
    }
    
    
  }








  
  
}