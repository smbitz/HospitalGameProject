package com.rokejitsx.ui.building.ward;

import com.rokejitsx.ui.patient.Patient;

public class Dentist extends Ward{

  public Dentist() {
	super(DENTIST, 1);
	setBuildingCanBroke();
	setIdleAnimationId(48);
	setHealingAnimationId(49);
	setBrokedAnimationId(50);
	setFocusTileIndex(16);
	
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

}
