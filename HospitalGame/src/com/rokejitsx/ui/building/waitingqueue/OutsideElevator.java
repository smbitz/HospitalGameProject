package com.rokejitsx.ui.building.waitingqueue;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.ui.building.Building;

public class OutsideElevator extends Building{
  public OutsideElevator() {
	super(OUTSIDE_ELEVATOR, 9);
	setAlpha(0);
  }  
  
  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
   /* Rectangle body = new Rectangle(0, 0, 30, 30 * 5);
	body.setColor(0, 0, 1);	*/
	return null;	
  }

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {}

  @Override
  public void onFocus() {

 }

  @Override
  public void onUnFocus() {	
  }
  
  

}
