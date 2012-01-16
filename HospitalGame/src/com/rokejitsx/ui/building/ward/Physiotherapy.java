package com.rokejitsx.ui.building.ward;

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
	
	//setColor(1, 1, 1, 1);
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

}
