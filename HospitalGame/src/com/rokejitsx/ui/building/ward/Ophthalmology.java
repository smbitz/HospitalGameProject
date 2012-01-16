package com.rokejitsx.ui.building.ward;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.patient.Patient;

public class Ophthalmology extends Ward{

  public Ophthalmology() {
	super(OPHTHALMOLOGY, 1);
	
	setBuildingCanBroke();
	setBrokedAnimationId(31);
	setFocusTileIndex(17);
	setHealingAnimationId(30);
	setIdleAnimationId(28);
	
	
	setState(STATE_IDLE);
  }

  @Override
  public void onWardReceivePatient(Patient patient) {
    patient.setPosition(getX() + getWidth()/2 - patient.getWidth()/2 - 15, getY() + getHeight()/2 - patient.getHeight()/2 + 45);	
    patient.layIn();
	
  }

  @Override
  public void onWardRemovePatient(Patient patient) {
	// TODO Auto-generated method stub
	
 }

  @Override
  public void onStartHealing() {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void onHealing(float pSecondsElapsed) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void onfinishHealing() {
	// TODO Auto-generated method stub
	
  }

  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
	AnimatedSprite oph = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_OPHTHALMOLOGY));
	oph.setCurrentTileIndex(4);
	attachChild(oph);
	attachChild(mainSprite);
	return null;
  }
  
  
	
}