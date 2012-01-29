package com.rokejitsx.ui.building.ward;

import android.util.Log;

import com.rokejitsx.data.xml.global.GlobalsXmlReader;
import com.rokejitsx.ui.patient.Patient;

public class TAC extends Ward{

  public TAC() {
	super(TAC, 1);	
	Log.d("rokejitsx", "TAC.......................");
	setBuildingCanBroke();
	setFocusTileIndex(6);
	setIdleAnimationId(19);
	setHealingAnimationId(20);
	setBrokedAnimationId(21);
	setOperationTime(3500);
	setState(STATE_IDLE);
	addGameCharactorOnReceivedPosition(84, 101);
	initialFromGlobal(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_CAT);
  }

  @Override
  public void onWardReceivePatient(Patient patient) {
	// TODO Auto-generated method stub
	
  }
  @Override
  protected void setPatientOnReceived(Patient patient) {
    patient.sit(true);	  
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
	if(isVisible())
	  getCurrentPatient().setVisible(true);
	
  }  

}
