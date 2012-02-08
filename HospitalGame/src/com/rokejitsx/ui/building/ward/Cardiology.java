package com.rokejitsx.ui.building.ward;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.global.GlobalsXmlReader;
import com.rokejitsx.ui.patient.Patient;

public class Cardiology extends Ward{
  private AnimatedSprite cadio;
  public Cardiology() {
	super(CARDIOLOGY, 1);
	
	cadio = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_CARDIO));
	cadio.setVisible(false);
	attachChild(cadio);
	attachChild(mainSprite);
	setBuildingCanBroke();
	setIdleAnimationId(52);
	setHealingAnimationId(55);
	setBrokedAnimationId(56);
	setFocusTileIndex(26);
	setState(STATE_IDLE);
	addGameCharactorOnReceivedPosition(119, 103);
	
	initialFromGlobal(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_CARDIOLOGY);
	setCheckPosition(0, 0);
  }

  @Override
  public void onWardReceivePatient(Patient patient) {}
  
  @Override
  protected void setPatientOnReceived(Patient patient) {
    patient.layIn(false);	  
  }

  @Override
  public void onWardRemovePatient(Patient patient) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void onStartHealing() {
    //getCurrentPatient().setVisible(false);
	setAnimation(cadio, ResourceManager.getInstance().getAnimationInfo(53));
    cadio.setVisible(true);
	
  }

  @Override
  public void onHealing(float pSecondsElapsed) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void onfinishHealing() {
	cadio.stopAnimation();
	cadio.setVisible(false);
	
  }
  @Override
  protected void onDrawChildren(GL10 pGL, Camera pCamera) {
    cadio.onDraw(pGL, pCamera);
    Patient patient = getCurrentPatient(); 
    if(patient != null && !patient.isOnPick()){
      if(patient.isOnHealing()){
        patient.onDraw(pGL, pCamera);
        mainSprite.onDraw(pGL, pCamera);
      }else{
        mainSprite.onDraw(pGL, pCamera);
        patient.onDraw(pGL, pCamera);
      }
      
      
    }else{
      mainSprite.onDraw(pGL, pCamera);
    }
    drawChecker(pGL, pCamera);
    
  }

  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
    
    /*attachChild(cadio);    
    attachChild(mainSprite);*/
	return null;
  }
  
  

}
