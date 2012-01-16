package com.rokejitsx.ui.building.elevator;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.ui.hospital.FloorButton;

public class ElevatorFloorSelector extends Rectangle implements IOnSceneTouchListener{
  private FloorButton[] buttons;
  private ElevatorSelectorListener listener;
  public ElevatorFloorSelector(int maxFloor) {	  
	super(100, 100, 50, (maxFloor * 30) + ((maxFloor - 1) * 10));
	int cameraWidth = HospitalGameActivity.getGameActivity().getCameraWidth();
	int cameraHeight = HospitalGameActivity.getGameActivity().getCameraHeight();
	this.setPosition(cameraWidth/2 - getWidth()/2, cameraHeight/2 - getHeight()/2);
	buttons = new FloorButton[maxFloor];	
	float startX = 0;
	float startY = 0;
	for(int i = 0;i < maxFloor;i++){
	  FloorButton floorBtn = new FloorButton();
	  floorBtn.setNumber(i + 1);
	  floorBtn.setPosition(startX, startY);
	  
	  attachChild(floorBtn);
	  startX += floorBtn.getWidth();
	  
	  if(i > 0 && i % 2 == 1){
	    startX = 0;
	    startY = startY + floorBtn.getHeight();
	  }	  
	  buttons[i] = floorBtn;
	}
	setWidth(buttons[0].getBaseWidth() * 2);
	setHeight(buttons[0].getBaseHeight() * 2);
	setAlpha(0);
  }
  
  public void setElevatorFloorSelectorListener(ElevatorSelectorListener listener){
    this.listener = listener;	  
  }
  
  /*class FloorButton extends Rectangle{
	private int floorNum;  
    public FloorButton(int floorNum,float x,float y){      
      super(x,y,30,30);
      this.floorNum = floorNum;
      Text text = new Text(0, 0, GameFonts.getInstance().getFont(), ""+floorNum);
      attachChild(text);
      setColor(1,1,1);
    }	  
    
    public int getFloorNum(){
      return floorNum;	
    }
  }*/

  @Override
  public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
	if(!isVisible())
	  return false;
	int pointerID = pSceneTouchEvent.getPointerID();
	if(pointerID == 0){
	  int action = pSceneTouchEvent.getAction();
	  float touchX = pSceneTouchEvent.getX();
	  float touchY = pSceneTouchEvent.getY();
	  switch(action){
	    case TouchEvent.ACTION_DOWN:
	      for(int i = 0; i < buttons.length;i++){
	        FloorButton floorBtn = buttons[i];
	        if(floorBtn.contains(touchX, touchY)){	          
	    	  listener.onElevatorFloorSelected(i);
	    	  return true;
	        }
	      }	
	    
	  }
	}
	
	return false;
  }
  
  public interface ElevatorSelectorListener{
    public void onElevatorFloorSelected(int floorSelected);	  
  }

}
