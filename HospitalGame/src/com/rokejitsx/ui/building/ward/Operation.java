package com.rokejitsx.ui.building.ward;

import com.rokejitsx.ui.patient.Patient;

public class Operation extends Ward{

  public Operation() {
	super(OPERATION, 1);
	setBuildingCanBroke();
	setIdleAnimationId(58);
	setHealingAnimationId(59);
	setBrokedAnimationId(60);
	setFocusTileIndex(23);
	
	setState(STATE_IDLE);
	addGameCharactorOnReceivedPosition(95, 169);
	
  }

  @Override
  public void onWardReceivePatient(Patient patient) {
	patient.idle(true);
	
  }
  
  @Override
  protected void setPatientOnReceived(Patient patient) {}

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
    if(isVisible())
      getCurrentPatient().setVisible(true);
	  
	
  }

}
