package com.rokejitsx.data;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.util.HorizontalAlign;

import android.util.Log;

import com.rokejitsx.data.resource.ImageResource;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.AnimationInfo;
import com.rokejitsx.ui.hospital.Hospital.FloorChangeListener;

public class GameObject extends Rectangle implements FloorChangeListener, ImageResource{
  private int currentFloor;
    
  private ChangeableText infoText;
  protected AnimatedSprite mainSprite;
  public GameObject(String spriteName){
    this(0, 0, 0, 0, spriteName);	  
    	  
    //attachChild(chairSprite);
  }
  
  public GameObject(float x, float y, float width, float height, String spriteName){
    super(x, y, width, height);	
    //setCurrentFloor(floor);
    if(spriteName != null){
      mainSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(spriteName));
      mainSprite.setPosition(0, 0);
      setWidth(mainSprite.getBaseWidth());
      setHeight(mainSprite.getBaseHeight());
    }
    Shape body = onInitialBody(mainSprite);
    if(body != null){
      infoText = new ChangeableText(0, 0, GameFonts.getInstance().getFont(), "", HorizontalAlign.CENTER, 100);
	  body.attachChild(infoText);
      attachChild(body);    
    
      setWidth(body.getWidth());
      setHeight(body.getHeight());
    }
    
    
    setColor(0, 0, 0, 0);
  } 
  
  protected void setAnimation(AnimatedSprite sprite, AnimationInfo animInfo){
    //AnimationInfo animInfo = ResourceManager.getInstance().getAnimationInfo(animationId);		
    setAnimation(sprite, animInfo, null);	  
  }
  
  
  protected void setAnimation(AnimatedSprite sprite, AnimationInfo animInfo, IAnimationListener listener){
    //AnimationInfo animInfo = ResourceManager.getInstance().getAnimationInfo(animationId);		
	Log.d("RokejitsX", "sprite = "+sprite);
	Log.d("RokejitsX", "animInfo = "+animInfo);
    sprite.setFlippedHorizontal(animInfo.isFlip());    
    sprite.animate(animInfo.getEachFrameDuration(), animInfo.getSequence(), animInfo.doLoop(), listener);	  
  }
  
  protected void setAnimation(AnimatedSprite sprite, long fps, int[] sequence, int doLoop, boolean isFlip){
    setAnimation(sprite, fps, sequence, doLoop, isFlip, null);	  
  }
  
  protected void setAnimation(AnimatedSprite sprite, long fps, int[] sequence, int doLoop, boolean isFlip, IAnimationListener listener){
    sprite.setFlippedHorizontal(isFlip);
    if(sequence.length == 1){
      sprite.setCurrentTileIndex(sequence[0]);	
    }else{      
      sprite.animate(fps, sequence, doLoop, listener);
    }
  }
  
  
  
  public void setGameObjectPositionAsCenter(float x, float y){
    setPosition(x - getWidth() /2, y - getHeight()/2);	  
  }
  
  protected void setSpritePosition(AnimatedSprite sprite, float x, float y){
    sprite.setPosition(x - sprite.getBaseWidth()/2, y - sprite.getBaseHeight()/2);	  
  }
  
  public void setObjectPosition(float x, float y){
    setPosition(x, y);	  
  }
  
  public void setCurrentFloor(int floor){
    currentFloor = floor;	  
    if(infoText != null)
      infoText.setText(getClass().getSimpleName()+ " "+"("+getCurrentFloor()+")");
  }
  
  public int getCurrentFloor(){
    return currentFloor;	  
  }
  
  
  protected int hospitalFloor;
  @Override
  public void onFloorChanged(int floor){
	hospitalFloor = floor;
    if(floor != getCurrentFloor()){    	
      setVisible(false);	
    }else{
      setVisible(true);	
    }	  
  }
  
  public Shape onInitialBody(AnimatedSprite mainSprite){
	if(mainSprite != null)
      attachChild(mainSprite);
	return null;	  
  }
  
  /*public float getBodyX(){
    return body.getX();	  
  }
  
  public float getBodyY(){
    return body.getY();	  
  }
  
  public float getBodyWidth(){
    return body.getWidth();	  
  }
  
  public float getBodyHeight(){
    return body.getHeight();	  
  }*/
  
}
