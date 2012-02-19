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

import com.zurubu.scene.AppSharedPreference;
import com.zurubu.scene.MenuScene;
import com.zurubu.scene.TargetData;

public class HospitalHustleGameMenuActivity extends BaseGameActivity {
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 600;
	private static ArrayList<Entity> menuSceneList = new ArrayList<Entity>();
	private Camera mCamera;
	private AppSharedPreference appSettings;
	private ArrayList<TargetData> dataMode;
	private int page = 0;
	  
	private static HospitalHustleGameMenuActivity interfaceAct;
	 
	public static HospitalHustleGameMenuActivity getInterfaceActivity(){    
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
	
	public AppSharedPreference getAppSettings(){
	    return appSettings;	  
	} 
	
	public void setDataMode(ArrayList<TargetData> d) {
		dataMode = d;
	}
	
	public ArrayList<TargetData> getDataMode(){
	    return dataMode;	  
	} 
	
	public void setPage(int p) {
		page = p;
	}
	
	public int getPage(){
	    return page;	  
	} 
	
	@Override
	public Engine onLoadEngine() {
		interfaceAct = this;
		appSettings = new AppSharedPreference(interfaceAct);
		dataMode = setData();
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
}