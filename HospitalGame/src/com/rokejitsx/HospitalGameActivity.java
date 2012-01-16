
package com.rokejitsx;

import java.util.ArrayList;
import java.util.Vector;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.rokejitsx.data.GameFonts;
import com.rokejitsx.data.resource.ResourceManager;

public class HospitalGameActivity extends BaseGameActivity {
  private static final int CAMERA_WIDTH = 800;
  private static final int CAMERA_HEIGHT = 600;
  private Camera mCamera;
  
  private static HospitalGameActivity gameAct;  
  
  
  public static HospitalGameActivity getGameActivity(){
    
    return gameAct;	  
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
	return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(4, 3), this.mCamera));
  }

  @Override
  public void onLoadResources() {
    GameFonts.getInstance().loadFont(getTextureManager(), getFontManager());
    ResourceManager.getInstance().init();
    /*try {
      GlobalsXmlReader gReader = new GlobalsXmlReader();
      gReader.startParse();
      gReader.print();
	} catch (XmlPullParserException e) {
	  Log.e("Rokejits", "XmlPullParserException =  "+e.toString());
	  e.printStackTrace();
	} catch (IOException e) {
	  Log.e("Rokejits", "IOException =  "+e.toString());	
	  e.printStackTrace();
	}*/
		
  }

  @Override
  public Scene onLoadScene() {	
	this.mEngine.registerUpdateHandler(new FPSLogger());
	return new GamePlay();
  }

  @Override
  public void onLoadComplete() {
	// TODO Auto-generated method stub
	
  }   
  
  
  public void sendAttachChild(Entity parent, Entity entity){
    Vector<Entity> list = new Vector<Entity>();
	list.add(entity);
    sendAttachChild(parent, list);	   
  }
  
  public void sendAttachChild(Entity parent, Vector<Entity> list){
    runOnUpdateThread(new AttachChildThread(parent, list));	  
  }
  
  public void sendDeattachChild(Entity parent, Entity entity){
	Vector<Entity> list = new Vector<Entity>();
	list.add(entity);
    sendDeattachChild(parent, list);  	  
  }
  
  public void sendDeattachChild(Entity parent, Vector<Entity> list){
    runOnUpdateThread(new DeAttachChildThread(parent, list));	  
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
    private Entity entity;
    private Vector<Entity> removeChild;
	
    public DeAttachChildThread(Entity entity, Vector<Entity> list){
      this.entity = entity;
      this.removeChild = list;
    }
    
	@Override
	public void run() {
      
	  for(int i = 0;i < removeChild.size();i++){
	    Entity child = removeChild.get(i);	  
	    entity.detachChild(child);
	  }	
		
	}
	  
  }
  
  
}