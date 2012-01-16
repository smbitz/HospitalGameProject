package com.rokejitsx.ui.building.ward;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.patient.Patient;

public class Chemotherapy extends Ward{

  public Chemotherapy() {
    super(CHEMOTHERAPY, 1);
    setBuildingCanBroke();
    setIdleAnimationId(38);
    setHealingAnimationId(39);
    setBrokedAnimationId(40);
    setFocusTileIndex(9);
    
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
	AnimatedSprite chemo = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_CHEMOTHERAPY));
	chemo.setCurrentTileIndex(0);
	attachChild(chemo);
	attachChild(mainSprite);
	return null;
  }

  

}
