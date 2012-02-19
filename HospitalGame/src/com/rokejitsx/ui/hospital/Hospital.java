package com.rokejitsx.ui.hospital;

import java.util.Enumeration;
import java.util.Vector;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.util.FloatMath;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.hospital.HospitalGamePlay.FloorChangeListener;
import com.rokejitsx.ui.patient.Patient;

public class Hospital extends Entity{

  private Vector<FloorChangeListener> floorListener;
  private int currentFloor,maxFloor;  
  
  public Hospital(int maxFloor){
    this.maxFloor = maxFloor;    
  }
  
  /*private BitmapTextureAtlas bgTextureAtlas;
  private Sprite[] bg;
  private TextureRegion[] bgTextureRegion;  
  private float[][] bgOffset = {
    {-80, 0},
    {-80, 165},		  
    {-80, 340},
    {-80, 505}
  };
  
  public void initialBg(int hospital, int maxFloor){
	bgTextureRegion = new TextureRegion[maxFloor];
	bg = new Sprite[maxFloor];
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
	
	for(int i = 0;i < bg.length;i++){
	  
	  float[] offset = bgOffset[i];
	  bg[i] = new Sprite(0 + offset[0], -600 + offset[1], bgTextureRegion[i]);
	  attachChild(bg[i], 0);
	}
	
  }*/
  
  protected GameObject getTouchedObject(float x, float y, GameObject[] objList){
	GameObject currentCollide = null;
	for(int i = 0;i < objList.length;i++){
	  GameObject obj = objList[i];
	  boolean isContain = false;
	  if(obj instanceof Building)
	    isContain = ((Building)obj).isBuildingContain(x, y) != null;
	  else if(obj instanceof Patient)
		isContain = ((Patient)obj).isContain(x, y);
	  else
		isContain = obj.contains(x, y);
	  if(isContain && obj.isVisible()){
	    if(currentCollide != null){
       	  if(currentCollide.equals(obj))
  	        continue;
        	  
          float collideX = currentCollide.getX() + currentCollide.getWidth()/2;
          float collideY = currentCollide.getY() + currentCollide.getHeight()/2;
          float buildingX = obj.getX() + obj.getWidth()/2;
          float buildingY = obj.getY() + obj.getHeight()/2;
        	  
          float x1 = x - collideX;
          float y1 = y - collideY;
          float x2 = x - buildingX;
          float y2 = y - buildingY;
        	  
          float distance1 = FloatMath.sqrt((x1 * x1) + (y1 * y1));  	
          float distance2 = FloatMath.sqrt((x2 * x2) + (y2 * y2));
        	  
          if(distance2 < distance1){
            currentCollide = obj;	  
          }
        		  
        	  
        }else{	        	
          currentCollide = obj;
             //collideBuilding.onFocus();
        }
	  }
	  
	}
    return currentCollide;	  
  }
  
  public int getMaxFloor(){
    return maxFloor;	  
  }  
  
  
  public final void setFloor(int floor){	 
    currentFloor = floor;
    /*if(bg != null){
      for(int i = 0;i < bg.length;i++){
        if(i == currentFloor)
          bg[i].setVisible(true);
        else
          bg[i].setVisible(false);
      }
    }*/
    updateFloorChanged(floor);
    onSetFloor(floor);
    //Enumeration<Item> e = itemOnDesk.elements();
    
    
  }
  
  public final int getFloor(){
    return currentFloor;	  
  }
  
  
  protected void onSetFloor(int floor){}
  
  
  public void addFloorChangeListener(FloorChangeListener listener){
    if(floorListener == null)
      floorListener = new Vector<FloorChangeListener>();
    floorListener.add(listener);
  }
	  
  public void removeFloorChangeListener(FloorChangeListener listener){
    if(floorListener == null)
      return;
    floorListener.remove(listener);
  }
  
  private void updateFloorChanged(int floor){
    if(floorListener == null)
      return;
    Enumeration<FloorChangeListener> e = floorListener.elements();
    while(e.hasMoreElements()){
      FloorChangeListener listener = e.nextElement();
      listener.onFloorChanged(floor);
    }
  }
  	
}
