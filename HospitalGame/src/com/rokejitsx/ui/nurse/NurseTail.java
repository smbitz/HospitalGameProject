package com.rokejitsx.ui.nurse;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.ui.hospital.HospitalGamePlay.FloorChangeListener;

public class NurseTail extends Entity implements FloorChangeListener{  
  	
  private float tailTime;
  private int currentFloor;
  public NurseTail(){
    super(0, 0);  	  
    setVisible(false);
  }	
  
  public void setNurseTail(AnimatedSprite tail, int floor, int hospitalFloor){
	currentFloor = floor;
    detachChildren();
    AnimatedSprite sprite = new AnimatedSprite(0, 0, tail.getTextureRegion().deepCopy());
    sprite.setCurrentTileIndex(tail.getCurrentTileIndex());
    sprite.setFlippedHorizontal(tail.isFlippedHorizontal());
    attachChild(sprite);
    tailTime = 0.35f;    
    if(currentFloor == hospitalFloor)
      setVisible(true);
    else
      setVisible(false);
  }

  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {	
	super.onManagedUpdate(pSecondsElapsed);
    if(tailTime > 0){
      tailTime -= pSecondsElapsed;
      if(tailTime <= 0)
        setVisible(false);
    }
  }

  @Override
  public void onFloorChanged(int floor) {
    if(currentFloor == floor){
      if(isVisible())
        setVisible(true);
    }else{
      setVisible(false);
    }
	
  }
  
  
  
}
