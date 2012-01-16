package com.rokejitsx.ui.building.ward;

import com.rokejitsx.ui.patient.Patient;

public class QuickTreat extends Ward{
  //private AnimatedSprite quickSprite;	
		
	  	
  public QuickTreat() {
	super(QUICKTREAT, 1);
	
    setBuildingCanDirty();
    setIdleAnimationId(5);
    setHealingAnimationId(6);
    setDirtyAnimationId(7);
    setState(STATE_IDLE);
    
    //setColor(1,1,1,1);
    //quickSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MON));	  
    //attachChild(quickSprite);
    
  }
  
  @Override
  public void onFocus() {
	mainSprite.stopAnimation(11);	
	//super.onFocus();
  }



  @Override
  public void onUnFocus() {
	setState(STATE_IDLE);
	super.onUnFocus();
  }

  @Override
  public void onStartHealing() {	
	
  }

  @Override
  public void onHealing(float pSecondsElapsed) {
	
	
  }
  
  @Override
  public void onfinishHealing() {
	
	
  }
  
  @Override
  public void onWardReceivePatient(Patient patient) {
    patient.setPosition(getX() + getWidth()/2 - patient.getWidth()/2 - 15, getY() + getHeight()/2 - patient.getHeight()/2 + 45);	
    patient.layIn();
  }

  @Override
  public void onWardRemovePatient(Patient patient) {
	
	
  }
  

 /* @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
	Rectangle body = new Rectangle(0, 0, 30 * 4, 50);
	body.setColor(1, 1, 1);	
	return body;
  }*/

  

  

}
