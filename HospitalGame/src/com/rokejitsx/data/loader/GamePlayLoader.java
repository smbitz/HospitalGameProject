package com.rokejitsx.data.loader;

import java.util.Vector;

import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.route.RouteManager;
import com.rokejitsx.data.xml.HospitalLevelReader.BuildingInfo;
import com.rokejitsx.data.xml.LevelInfoReader;
import com.rokejitsx.data.xml.LevelInfoReader.LevelInfo;
import com.rokejitsx.ui.hospital.Hospital;
import com.rokejitsx.ui.hospital.HospitalGamePlay;
import com.rokejitsx.ui.hospital.HospitalUI;

public class GamePlayLoader extends Loader{
  private int hospitalId, level, maxFloor;	
  private BuildingInfo[] buildingInfoList;
  private RouteManager[] routeManagerList;
  private HospitalUI hospitalUI;
  private HospitalGamePlay hospital;
  
  private BitmapTextureAtlas bgTextureAtlas;
  private TextureRegion[] bgTextureRegion;
  private SpriteBackground[] hospitalBg;
  private float[][] bgOffset = {
    {-80, 0},
    {-80, 165},		  
    {-80, 340},
    {-80, 505}
  };
  
  public GamePlayLoader(BuildingInfo[] buildingInfoList, RouteManager[] routeManagerList,int hospitalId, int level, int maxFloor, LoaderListener listener){
    super(listener, 100);	  
    this.hospitalId = hospitalId;
	this.level = level;
	this.buildingInfoList= buildingInfoList;
	this.routeManagerList = routeManagerList;
	this.maxFloor = maxFloor;
  }

  @Override
  protected void onLoad() {
	initialBg(hospitalId, maxFloor);
	Vector<BuildingInfo> enableBuildingInfoVector = new Vector<BuildingInfo>();		
	for(int i = 0;i <buildingInfoList.length;i++){
	  BuildingInfo info = buildingInfoList[i];
	  if(info.isEnable())
	    enableBuildingInfoVector.add(info);
	}
		
	BuildingInfo[] enableBuildingInfoList = new BuildingInfo[enableBuildingInfoVector.size()];	
	enableBuildingInfoVector.copyInto(enableBuildingInfoList);
	
    hospital = new HospitalGamePlay(maxFloor);	    
	//hospital.initialBg(hospitalId, maxFloor);
    hospital.loadBuilding(hospitalId, enableBuildingInfoList);
    hospital.loadSound(hospitalId);
    hospital.initRouteManager(routeManagerList);
    hospital.initialNurse();    
    initHospitalUi(hospitalId,level,maxFloor);
    hospitalUI.setElevetorSelectorListener(hospital);
    
    hospital.setHospitalListener(hospitalUI);
        
    hospital.setCourseInfoList(ResourceManager.getInstance().getCourseInfoListForHospital(hospitalId, level), hospitalId, level);
  }	
  
  private void initHospitalUi(int hospitalId, int level, int maxFloor){
    LevelInfoReader levelInfoReader = new LevelInfoReader();
    LevelInfo levelInfo = levelInfoReader.readLevel(hospitalId, level);
    hospitalUI = new HospitalUI(maxFloor);
    hospitalUI.setHospitalUIItemListener(hospital);	    
    hospitalUI.setTime(levelInfo.getTime());
    hospitalUI.setGoalPatient(levelInfo.getObjective());
    hospitalUI.setExpertPatient(levelInfo.getExpertObjective());
    hospitalUI.setMoney(0);
	    
    hospital.setMachineBreakCount(levelInfo.getMachineBreakCount(), levelInfo.getTime());
    hospital.setPatientMaxCount(levelInfo.getPatientCount(), levelInfo.getTime());
	    
	    
  }
  
  
  
  private void initialBg(int hospital, int maxFloor){
	bgTextureRegion = new TextureRegion[maxFloor];
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
  
  public BitmapTextureAtlas getBgTextureAtlas(){
    return bgTextureAtlas;	  
  }
  
  public SpriteBackground[] getHospitalBackground(){
    return hospitalBg;	  
  }
  
  public HospitalGamePlay getHospital(){
    return hospital;	   
  }
  
  public HospitalUI getHospitalUI(){
    return hospitalUI;	  
  }
  
  
  
  
  
  
}
