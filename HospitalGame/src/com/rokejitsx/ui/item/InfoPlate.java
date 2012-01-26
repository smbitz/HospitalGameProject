package com.rokejitsx.ui.item;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.resource.ResourceManager;

public class InfoPlate extends Item{  
  private AnimatedSprite numberSprite; 	
  public InfoPlate(int patientNumber) {
	super(INFOPLATE, patientNumber);
	numberSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(RECIPIES));
	numberSprite.setCurrentTileIndex(15 - patientNumber);
	attachChild(numberSprite);
	
	mainSprite.setCurrentTileIndex(16);
	
  }

  /*@Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {	
	
	return null;
	
  }*/

  @Override
  public Item deepCopy() {	
	return new InfoPlate(getPatientNumber());
  }
  

}
