package com.rokejitsx.ui.building.ward;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.patient.Patient;

public class Cardiology extends Ward{

  public Cardiology() {
	super(CARDIOLOGY, 1);
	setBuildingCanBroke();
	setIdleAnimationId(52);
	setHealingAnimationId(54);
	setBrokedAnimationId(56);
	setFocusTileIndex(26);
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
    getCurrentPatient().setVisible(false);
	
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
    AnimatedSprite cadio = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_CARDIO));
    attachChild(cadio);
    attachChild(cadio);
    attachChild(mainSprite);
	return null;
  }
  
  

}
