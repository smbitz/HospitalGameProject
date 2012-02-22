package com.rokejitsx;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.R.array;

import com.rokejitsx.HospitalGameActivity.AttachChildThread;
import com.rokejitsx.HospitalGameActivity.DeAttachChildThread;
import com.rokejitsx.data.GameFonts;
import com.rokejitsx.data.resource.ResourceManager;
import com.zurubu.scene.MenuScene;

public class InterfaceActivity extends BaseGameActivity {
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 600;
	private static ArrayList<Entity> menuSceneList = new ArrayList<Entity>();
	private Camera mCamera;
	  
	private static InterfaceActivity interfaceAct;
	 
	public static InterfaceActivity getInterfaceActivity(){    
		return interfaceAct;	  
	}
		  
	public int getCameraWidth(){	
	    return CAMERA_WIDTH;	  
	}
		  
	public int getCameraHeight(){
	    return CAMERA_HEIGHT;	  
	}
		 
	public ArrayList<Entity> getMenuSceneList () {
		return menuSceneList;
	}
	
	public Camera getCamera(){
	    return mCamera;	  
	} 
	  
	@Override
	public Engine onLoadEngine() {
		interfaceAct = this;
	    this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(5, 3), this.mCamera));
	}
	  
	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		return new MenuScene();
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
}
