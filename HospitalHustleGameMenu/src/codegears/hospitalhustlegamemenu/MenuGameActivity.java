package codegears.hospitalhustlegamemenu;

import java.util.ArrayList;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.kazekim.menu.GameMenuScene;
import com.kazekim.menu.InitialVal;
import com.zurubu.scene.OptionMenuListener;

public class MenuGameActivity extends BaseGameActivity{
	
	private static ArrayList<Entity> menuSceneList = new ArrayList<Entity>();
	private Camera mCamera;
	  
	private static MenuGameActivity activity;
	 
	public static MenuGameActivity getActivity(){    
		return activity;	  
	}
		  
	public int getCameraWidth(){	
	    return InitialVal.CAMERA_WIDTH;	  
	}
		  
	public int getCameraHeight(){
	    return InitialVal.CAMERA_HEIGHT;	  
	}
		 
	public ArrayList<Entity> getMenuSceneList () {
		return menuSceneList;
	}
	
	public Camera getCamera(){
	    return mCamera;	  
	} 
	  
	@Override
	public Engine onLoadEngine() {
		activity = this;
	    this.mCamera = new Camera(0, 0, InitialVal.CAMERA_WIDTH, InitialVal.CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(5, 3), this.mCamera));
	}
	  
	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		return new GameMenuScene();
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}

}