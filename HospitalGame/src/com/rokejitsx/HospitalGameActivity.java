
package com.rokejitsx;

import java.util.Vector;

import org.anddev.andengine.audio.sound.SoundManager;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.rokejitsx.audio.SoundPlayerManager;
import com.rokejitsx.data.GameFonts;
import com.rokejitsx.data.loader.HospitalLoader;
import com.rokejitsx.data.loader.Loader;
import com.rokejitsx.data.loader.LoaderListener;
import com.rokejitsx.data.loader.LoadingScene;
import com.rokejitsx.data.loader.ResourceLoader;
import com.rokejitsx.menu.MainMenuScene;
import com.rokejitsx.menu.MainMenuScene.MyMenuListener;

public class HospitalGameActivity extends BaseGameActivity {
  private static final int CAMERA_WIDTH = 800;
  private static final int CAMERA_HEIGHT = 600;
  private Camera mCamera;
  
  private static HospitalGameActivity gameAct;  
  
  
  public static HospitalGameActivity getGameActivity(){
    
    return gameAct;	  
  }  
  
  
  
  
  @Override
  public void finish() {
	SoundPlayerManager.getInstance().releaseAll();
	//onDestroy();  
	super.finish();
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
    this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(4, 3), this.mCamera)/*.setNeedsSound(true).setNeedsMusic(true)*/);	
  }
  //private MainMenuScene menuScene;
  @Override
  public void onLoadResources() {
	SoundPlayerManager.getInstance().reset();
    GameFonts.getInstance().loadFont(getTextureManager(), getFontManager());
     
		
  }
  

  @Override
  public Scene onLoadScene() {	
	this.mEngine.registerUpdateHandler(new FPSLogger());
	return new LoadingScene();
  }

  @Override
  public void onLoadComplete() {
	ResourceLoader rLoader = new ResourceLoader(new LoaderListener() {	
		@Override
		public void onLoadFinish(Loader loader) {
		  runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
			  mEngine.setScene(new GamePlay());				
			}
		});
		  
			
		}
	});
	rLoader.startLoad();	
  }    
  
  /*@Override
  public void onGamePlayLoad(int hospitalId, int level) {
    mEngine.setScene(new LoadingScene());
    
    new HospitalLoader(hospitalId, new LoaderListener() {		
	  @Override
	  public void onLoadFinish(Loader loader) {		
		
		HospitalLoader hLoader = (HospitalLoader) loader;	    
	    GamePlay gamePlay = new GamePlay();
	    gamePlay.loadGamePlay(hLoader.getBuildingInfoList(), hLoader.getRouteManagerList(), hLoader.getHospitalId(), 0, hLoader.getMaxFloor());	    
	    mEngine.setScene(gamePlay);	 
	    gamePlay.setChildScene(new LoadingScene());
	    //gamePlay.upgrade();	    
	  }
	}).startLoad();   
    
  	
  }*/
  
  
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
      if(childScene instanceof MenuScene)
        ((MenuScene)childScene).buildAnimations();
      motherScene.setChildScene(childScene, false, true, true);	
    }
    
    
  }


  
  
}