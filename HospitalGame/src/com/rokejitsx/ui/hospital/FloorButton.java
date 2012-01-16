package com.rokejitsx.ui.hospital;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.resource.ImageResource;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.patient.NumberField;

public class FloorButton extends AnimatedSprite implements ImageResource{
  private NumberField floorText;
  public FloorButton(){
    super(0, 0, ResourceManager.getInstance().getTexture(FLOOR_BUTTON));
    floorText = new NumberField();
	attachChild(floorText);
	
	
	
	floorText.setPosition(getBaseWidth()/2 - floorText.getWidth()/2, getBaseHeight()/2 - floorText.getHeight()/2);
	setWidth(getBaseWidth());
	setHeight(getBaseHeight());
  }
  
  public void setNumber(int number){
    floorText.setNumber(number);	  
  }
}
