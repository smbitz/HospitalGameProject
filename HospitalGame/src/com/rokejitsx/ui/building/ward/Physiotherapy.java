package com.rokejitsx.ui.building.ward;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.global.GlobalsXmlReader;
import com.rokejitsx.ui.patient.Patient;

public class Physiotherapy extends Ward{

  	
  public Physiotherapy() {
	super(PHYSIOTHERAPY, 1);
	
	setBuildingCanBroke();
	setFocusTileIndex(16);
	setIdleAnimationId(23);
	setHealingAnimationId(24);	
	setBrokedAnimationId(26);
	
	
	setOperationTime(4000);
	setState(STATE_IDLE);
	addGameCharactorOnReceivedPosition(116, 109);
	initialFromGlobal(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_PHYSIOTHERAPY);
	setCheckPosition(0, 0);
	//setColor(1, 1, 1, 1);
  }

  @Override
  public void onWardReceivePatient(Patient patient) {
	// TODO Auto-generated method stub
	
  }
  @Override
  protected void setPatientOnReceived(Patient patient) {
    patient.idle(false);	  
  }

  @Override
  public void onWardRemovePatient(Patient patient) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void onStartHealing() { 
	Patient patient = getCurrentPatient();
	if(patient.whoIsYourMom() == null){
	  patient.setPosition(83, 64);
    }else{
      patient.setPosition(103, 84);	
    }
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
