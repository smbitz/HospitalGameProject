package com.rokejitsx.ui.building.ward;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.patient.Patient;

public class Physiotherapy extends Ward{

  	
  public Physiotherapy() {
	super(PHYSIOTHERAPY, 1);
	
	setBuildingCanBroke();
	setFocusTileIndex(16);
	setIdleAnimationId(23);
	setHealingAnimationId(24);	
	setBrokedAnimationId(11);
	
	
	setOperationTime(4000);
	setState(STATE_IDLE);
	addGameCharactorOnReceivedPosition(17, 133);
	
	//setColor(1, 1, 1, 1);
  }

  @Override
  public void onWardReceivePatient(Patient patient) {
	// TODO Auto-generated method stub
	
  }
  @Override
  protected void setPatientOnReceived(Patient patient) {
    patient.idle(true);	  
  }

  @Override
  public void onWardRemovePatient(Patient patient) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void onStartHealing() { 
	Patient patient = getCurrentPatient();
	patient.setPosition(83, 64);
	patient.onSetFace(Patient.FACE_UP_R);
		
  }

  @Override
  public void onHealing(float pSecondsElapsed) {
	if(getCurrentHealingTime() >= 2 && getCurrentPatient().isVisible()){
	  setAnimation(mainSprite, ResourceManager.getInstance().getAnimationInfo(25));
	  getCurrentPatient().setVisible(false);
	}
	
  }

  @Override
  public void onfinishHealing() {
	if(this.isVisible())
      getCurrentPatient().setVisible(true);
	
  }

}
