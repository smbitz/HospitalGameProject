package com.rokejitsx.ui.building.ward;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.patient.Patient;

public class Xray extends Ward{
  
  public Xray() {
	super(XRAY, 1);
	setBuildingCanBroke();
	setFocusTileIndex(7);
	setIdleAnimationId(1);
    setHealingAnimationId(2);
    setBrokedAnimationId(3);    
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
	getCurrentPatient().setVisible(false);
	
  }

  @Override
  public void onHealing(float pSecondsElapsed) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void onfinishHealing() {
	// TODO Auto-generated method stub
	if(this.isVisible())
	  getCurrentPatient().setVisible(true);
	
  }

@Override
public Shape onInitialBody(AnimatedSprite mainSprite) {
  AnimatedSprite xraySprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_XRAY));
  xraySprite.setCurrentTileIndex(0);
  attachChild(xraySprite);	
  attachChild(mainSprite);
  return null;
}
  
  
  
  

	
  	
}
