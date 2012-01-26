package com.rokejitsx.ui.building.ward;

import com.rokejitsx.ui.patient.Patient;

public class Psychiatry extends Ward{

  public Psychiatry() {
	super(PSYCHIATRY, 1);
    setBuildingCanBroke();
    setFocusTileIndex(13);
    setBrokedAnimationId(35);
    setHealingAnimationId(34);
    setIdleAnimationId(33);
    
    setState(STATE_IDLE);
    addGameCharactorOnReceivedPosition(148, 75);
  }

  @Override
  public void onWardReceivePatient(Patient patient) {
	// TODO Auto-generated method stub
	
  }
  
  @Override
  protected void setPatientOnReceived(Patient patient) {
    patient.layIn(true);	  
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

}
