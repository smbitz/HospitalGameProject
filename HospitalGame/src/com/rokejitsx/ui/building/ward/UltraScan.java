package com.rokejitsx.ui.building.ward;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.global.GlobalsXmlReader;
import com.rokejitsx.ui.patient.Patient;

public class UltraScan extends Ward{

  private AnimatedSprite ultraScan;	
  public UltraScan(){
    super(ULTRASCAN, 1);
    ultraScan = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_ULTRASCAN));
    ultraScan.setCurrentTileIndex(0);
    ultraScan.setVisible(false);
    setBuildingCanBroke();
    
    setBuildingCanBroke();
	setBrokedAnimationId(64);
	setFocusTileIndex(9);
	setHealingAnimationId(62);
	setIdleAnimationId(62);
	setOperationTime(3000);
	
	attachChild(mainSprite);
	attachChild(ultraScan);
	
	
	setState(STATE_IDLE);
	addGameCharactorOnReceivedPosition(119, 140);
	initialFromGlobal(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_ULTRA_SCAN);
  }

  @Override
  protected void setPatientOnReceived(Patient patient) {
	patient.idle(false);	
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
	ultraScan.setVisible(true);
	setAnimation(ultraScan, ResourceManager.getInstance().getAnimationInfo(63));
	
  }

  @Override
  public void onHealing(float pSecondsElapsed) {
	// TODO Auto-generated method stub
	
  }
  
  

  @Override
  public void onfinishHealing() {
    ultraScan.stopAnimation(0);
    ultraScan.setVisible(false);
	
  }	
  
  @Override
  protected void onDrawChildren(GL10 pGL, Camera pCamera) {	
	mainSprite.onDraw(pGL, pCamera);
	Patient patient = getCurrentPatient();
	if(patient != null && !patient.isOnPick()){
	  patient.onDraw(pGL, pCamera);  	
	}
	ultraScan.onDraw(pGL, pCamera);
	
  }
  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
	return null;
  }
}
