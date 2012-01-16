package com.rokejitsx.ui.building.ward;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.patient.Patient;

public class BabyScan extends Ward{

  public BabyScan() {
	super(BABY_SCAN, 1);
	setBuildingCanBroke();
	setIdleAnimationId(43);
	setHealingAnimationId(45);
	setBrokedAnimationId(46);
	setFocusTileIndex(19);
	setState(STATE_IDLE);
  }

  @Override
  public void onWardReceivePatient(Patient patient) {
	// TODO Auto-generated method stub
	
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
	AnimatedSprite babyScan = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_BABYSCAN));
	babyScan.setCurrentTileIndex(0);
	attachChild(babyScan);
	attachChild(mainSprite);
	return null;
  }

	
  
}
