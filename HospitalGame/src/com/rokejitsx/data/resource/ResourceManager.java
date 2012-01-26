package com.rokejitsx.data.resource;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.xmlpull.v1.XmlPullParserException;

import android.graphics.PointF;
import android.util.Log;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.route.RouteManager;
import com.rokejitsx.data.xml.AnimationInfo;
import com.rokejitsx.data.xml.AnimationInfoReader;
import com.rokejitsx.data.xml.CourseInfoReader;
import com.rokejitsx.data.xml.HospitalInfoReader;
import com.rokejitsx.data.xml.NurseInfoReader;
import com.rokejitsx.data.xml.ObjectInfosReader;
import com.rokejitsx.data.xml.ObjectInfosReader.ObjectInfo;
import com.rokejitsx.data.xml.PatientInfoReader;
import com.rokejitsx.data.xml.PatientInfoReader.PatientHeadInfo;
import com.rokejitsx.data.xml.PatientInfoReader.PatientInfo;
import com.rokejitsx.data.xml.TransportInfoReader;

public class ResourceManager implements ImageResource{
  private static ResourceManager self;
  private ObjectInfosReader infoReader;
  private TransportInfoReader transportReader;
  private PatientInfoReader patientInfoReader;
  private AnimationInfoReader animateInfoReader;
  private CourseInfoReader courseReader;
  private HospitalInfoReader hospitalReader;
  private NurseInfoReader nurseInfoReader;
  private Hashtable<String, TiledTextureRegion> staticTextureList, dinamicTextureList;
  private Vector<BitmapTextureAtlas> staticAtlasList;
  private BitmapTextureAtlas levelAtlas;
  
  public static ResourceManager getInstance(){
   if(self == null){
     self = new ResourceManager();	     
   }	  
   return self;
  }
  
  public ObjectInfo getObjectInfo(int type){
    return infoReader.getObjectInfo(type);	  
  }
  
  public RouteManager getTransportPath(int transportType){
    Hashtable<String, float[]> pathList = transportReader.getTransportPath(transportType);
    RouteManager routeManager = new RouteManager();
    for(int i = 0;i < pathList.size();i++){
      float[] position = pathList.get(""+i);
      routeManager.addRoute(position[0], position[1]);
    }
    
    routeManager.addConnection(0, 1);
    routeManager.addConnection(1, 2);
    
    return routeManager;
  }
  
  public int[] getHospitalXmlIds(int hospital){
    return hospitalReader.getHospitalXml(hospital);	  
  }
  
  public AnimationInfo getAmbulanceAnimationInfo(int id){ 
    return transportReader.getAmbulanceAnimationInfo(id);	  
  }
  
  public AnimationInfo getHelicopterAnimationInfo(int id){
    return transportReader.getHelicopterAnimationInfo(id);	  
  }
  
  public TiledTextureRegion getTexture(String imgName){
	Log.d("RokejitsX", "getTexture = "+imgName+"/"+staticTextureList.containsKey(imgName));
    TiledTextureRegion texture = staticTextureList.get(imgName);
    
    if(texture == null)
      texture = dinamicTextureList.get(imgName);
    return texture.deepCopy();    
  }
  
  public PatientInfo getPatientInfo(int patientId){
    return patientInfoReader.getPatientInfo(patientId);	  
  }
  
  public PatientHeadInfo getPatientHeadInfo(int headId){
    return patientInfoReader.getPatientHeadInfo(headId);	  
  }
  
  public AnimationInfo getAnimationInfo(int animateId){
    return animateInfoReader.getAnimation(animateId);	  
  }
  
  public Hashtable<String, float[]> getFrameLink(int animationId){
    return animateInfoReader.getFrameLink(animationId);	  
  }
  
  public Hashtable<String, float[]> getFrameLink(String animationId){
    return animateInfoReader.getFrameLink(animationId);	  
  }
  
  public AnimationInfo getNurseAnimationInfo(int id){
    return nurseInfoReader.getAnimation(id);	  
  }
  
  
  public void init(){
    infoReader = new ObjectInfosReader();
    transportReader = new TransportInfoReader();
    patientInfoReader = new PatientInfoReader();
    animateInfoReader = new AnimationInfoReader();
    nurseInfoReader = new NurseInfoReader();
    courseReader = new CourseInfoReader();  
    hospitalReader = new HospitalInfoReader();
    
    try {
	  
	  transportReader.startParse();
	  patientInfoReader.startParse();
	  animateInfoReader.startParse();
	  nurseInfoReader.startParse();
	  infoReader.startParse();  
	  courseReader.startParse();  
	  hospitalReader.startParse();
	  /*int id = 6;
	  courseReader.print(id, 0);
	  Log.d("RokejitsX", "=========================");
	  courseReader.print(id, 1);
	  Log.d("RokejitsX", "=========================");
	  courseReader.print(id, 2);
	  Log.d("RokejitsX", "=========================");
	  courseReader.print(id, 3);
	  Log.d("RokejitsX", "=========================");
	  courseReader.print(id, 4);
	  Log.d("RokejitsX", "=========================");
	  courseReader.print(id, 5);
	  Log.d("RokejitsX", "=========================");
	  courseReader.print(id, 6);
	  Log.d("RokejitsX", "=========================");
	  courseReader.print(id, 7);
	  Log.d("RokejitsX", "=========================");
	  courseReader.print(id, 8);
	  Log.d("RokejitsX", "=========================");*/
	  
	} catch (XmlPullParserException e) {
		
	  e.printStackTrace();
	} catch (IOException e) {
	  
	  e.printStackTrace();
	}
    
    initStaticResource();
  }
  
  public void loadLevel(int hospitalLevel){
    if(levelAtlas != null){
      HospitalGameActivity.getGameActivity().getEngine().getTextureManager().unloadTexture(levelAtlas);	     
    }  
    
    dinamicTextureList = new Hashtable<String, TiledTextureRegion>();
    levelAtlas = loadTexture(dinamicResourceList[hospitalLevel], 
    		                 dinamicTextureAtlasSize[hospitalLevel], 
    		                 dinamicImgPositionInTextureAtlas[hospitalLevel], 
    		                 dinamicImgFrameSize[hospitalLevel],
    		                 dinamicResourceListLocation[hospitalLevel], 
    		                 dinamicTextureList);
    
    /*int[] bufferSize = dinamicTextureAtlasSize[hospitalLevel];
    String resourceLocation = dinamicResourceListLocation[hospitalLevel];
    int[][] imgPositionInAtlas = dinamicImgPositionInTextureAtlas[hospitalLevel];  
    
    levelAtlas = new BitmapTextureAtlas(bufferSize[0], bufferSize[1], TextureOptions.BILINEAR);
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(resourceLocation);
    
    for(int j = 0;j < dinamicResourceList.length;j++){
      String imgName = StringUtil.replace(dinamicResourceList[j], "%d", ""+(hospitalLevel + 1));   
      Log.d("rokejitsx", "imgName = "+imgName);
      int[] imgPosition = imgPositionInAtlas[j];
      int[] frameSize = infoReader.getFrameSize(imgName);
      TiledTextureRegion mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(levelAtlas, HospitalGameActivity.getGameActivity(), imgName, imgPosition[0], imgPosition[1], frameSize[0], frameSize[1]);        
      dinamicTextureList.put(dinamicResourceList[j], mTextureRegion);
    }      
    HospitalGameActivity.getGameActivity().getEngine().getTextureManager().loadTexture(levelAtlas);    */
  }
  
  private void initStaticResource(){
    if(staticTextureList == null){
      staticTextureList = new Hashtable<String, TiledTextureRegion>();	
    }   
    if(staticAtlasList == null)
      staticAtlasList = new Vector<BitmapTextureAtlas>();
    initTexture(staticResourceSet, staticTextureAtlasSize, staticImgPositionInTextureAtlas, staticImgFrameSize, staticResourceListLocation, staticTextureList);        	  
  }
  
  private void initTexture(String[][] resourceSet,int[][] textureAtlasSize,int[][][] imgPositionInAtlasList, int[][][] frameSizes, String[] resourceLocation, Hashtable<String, TiledTextureRegion> store){
    for(int i = 0; i < resourceSet.length; i++){
      String[] set = resourceSet[i];
      int[] bufferSize = textureAtlasSize[i];
      int[][] imgPositionInAtlas = imgPositionInAtlasList[i];
      staticAtlasList.add(loadTexture(set, bufferSize, imgPositionInAtlas, frameSizes[i], resourceLocation[i], store));      
    }	  
  }
  
  private BitmapTextureAtlas loadTexture(String[] set,int[] bufferSize, int[][] imgPositionInAtlas,int[][] frameSizes, String resourceLocation, Hashtable<String , TiledTextureRegion> store){
    BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(bufferSize[0], bufferSize[1], TextureOptions.BILINEAR);
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(resourceLocation);
    for(int j = 0;j < set.length;j++){
      String imgName = set[j];      
      Log.d("RokejitsX", "imgName = "+imgName);
      int[] imgPosition = imgPositionInAtlas[j];      
      int[] frameSize = frameSizes[j];
      /*if(frameSize == null)
        frameSize = transportReader.getFrameSize(imgName);
      if(frameSize == null)
        frameSize = patientInfoReader.getFrameSize(imgName);*/
      //Log.d("RokejitsX", "imgName = "+imgName);
      TiledTextureRegion mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, HospitalGameActivity.getGameActivity(), imgName, imgPosition[0], imgPosition[1], frameSize[0], frameSize[1]);        
      store.put(imgName, mTextureRegion);
    }      
    HospitalGameActivity.getGameActivity().getEngine().getTextureManager().loadTexture(mBitmapTextureAtlas);	 
    return mBitmapTextureAtlas;
	  
  }
	
 	
}
