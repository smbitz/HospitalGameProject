package com.rokejitsx.ui.hospital;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.rokejitsx.data.GameFonts;
import com.rokejitsx.data.resource.ImageResource;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.patient.NumberField;

public class HospitalFloorSelector extends Rectangle implements ImageResource{
  private AnimatedSprite arrowUp, arrowDown;
  private int currentFloor, maxFloor;
  private FloorButton floorBtn;
  private FloorSelectListener listener;
  public HospitalFloorSelector(int maxFloor) {
	super(0, 0, 50, 150);
	this.currentFloor = 0;
	this.maxFloor = maxFloor;
	//setColor(1,0,0);
	setAlpha(0);
	arrowUp = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(FLOOR_BUTTON_UP));	
	attachChild(arrowUp);
	
	floorBtn = new FloorButton();
	floorBtn.setNumber(1);
	
	floorBtn.setPosition(0, arrowUp.getBaseHeight());
	attachChild(floorBtn);
	
	arrowDown = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(FLOOR_BUTTON_DOWN));
	arrowDown.setPosition(0, arrowUp.getBaseHeight() + floorBtn.getBaseHeight());
	arrowDown.setVisible(false);
	attachChild(arrowDown);
	setWidth(floorBtn.getBaseWidth());
	setHeight(floorBtn.getBaseHeight() + arrowUp.getBaseHeight() + arrowDown.getBaseHeight());
  }
  
  public void setFloorSelectListener(FloorSelectListener listener){
    this.listener = listener;	  
  }
  
  public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
	int pointerID = pSceneTouchEvent.getPointerID();
	if(pointerID == 0){
	  int action = pSceneTouchEvent.getAction();
	  Log.d("HospitalFloorSelector ", "onSceneTouch");
	  float touchX = pSceneTouchEvent.getX();
	  float touchY = pSceneTouchEvent.getY();
	  switch(action){
	    case TouchEvent.ACTION_DOWN:
	      if(arrowUp.contains(touchX, touchY)){
	        if(currentFloor < maxFloor - 1){
	          currentFloor++;
	          floorBtn.setNumber(currentFloor + 1);
	          listener.onHospitalFloorSelected(currentFloor);
	          arrowDown.setVisible(true);
	          if(currentFloor == maxFloor - 1)
	            arrowUp.setVisible(false);
	        }
	        return true;
	      } 
	      if(arrowDown.contains(touchX, touchY)){
		    if(currentFloor > 0){
		      currentFloor--;	
		      floorBtn.setNumber(currentFloor + 1);
		      listener.onHospitalFloorSelected(currentFloor);
		      arrowUp.setVisible(true);
		      if(currentFloor == 0)
		        arrowDown.setVisible(false);
		    }
		    return true;
		  }
	    break;
	  }
	}
	return false;
  }

  public interface FloorSelectListener{
    public void onHospitalFloorSelected(int floor);  	  
  }

	
}
