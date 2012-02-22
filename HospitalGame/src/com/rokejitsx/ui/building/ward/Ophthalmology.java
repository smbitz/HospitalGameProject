package com.rokejitsx.ui.building.ward;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.global.GlobalsXmlReader;
import com.rokejitsx.ui.patient.Patient;

public class Ophthalmology extends Ward{ 
  private AnimatedSprite ophBaseSprite;
  public Ophthalmology() {
	super(OPHTHALMOLOGY, 1);
	ophBaseSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_OPHTHALMOLOGY));
	ophBaseSprite.setVisible(false);
	ophBaseSprite.setCurrentTileIndex(4);
	attachChild(ophBaseSprite);
	attachChild(mainSprite);
	setBuildingCanBroke();
	setBrokedAnimationId(31);
	setFocusTileIndex(17);
	setHealingAnimationId(30);
	setIdleAnimationId(28);
	
	
	setState(STATE_IDLE);
	addGameCharactorOnReceivedPosition(149, 90);
	
	initialFromGlobal(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_OPHTHALMOLOGY);
	setCheckPosition(0, 0);
  }

  @Override
  public void onWardReceivePatient(Patient patient) { 
	
  }
  @Override
  protected void setPatientOnReceived(Patient patient) {
    patient.layIn(false);	  
  }

  @Override
  public void onWardRemovePatient(Patient patient) {
    
	
  }

  @Override
  public void onStartHealing() {
    ophBaseSprite.setVisible(true);
	
  }

  @Override
  public void onHealing(float pSecondsElapsed) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void onfinishHealing() {
    ophBaseSprite.setVisible(false);
	
  }
  
  @Override
  protected void onDrawChildren(GL10 pGL, Camera pCamera) {	
	ophBaseSprite.onDraw(pGL, pCamera);
	Patient patient = getCurrentPatient();
	if(patient != null && !patient.isOnPick()){
	  if(patient.isOnHealing()){
	    getCurrentPatient().onDraw(pGL, pCamera);
	    mainSprite.onDraw(pGL, pCamera);
	  }else{
		mainSprite.onDraw(pGL, pCamera);	  
		getCurrentPatient().onDraw(pGL, pCamera);
	  }
	}else{
	  mainSprite.onDraw(pGL, pCamera);	
	}
	drawChecker(pGL, pCamera);
	
  }

  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
	return null;
  }
  
  
	
}