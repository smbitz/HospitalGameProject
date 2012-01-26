package com.rokejitsx.ui.building.ward;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.patient.Patient;

public class BabyScan extends Ward{
  private AnimatedSprite babyScan;
  public BabyScan() {
	super(BABY_SCAN, 1);
	babyScan = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_BABYSCAN));
	babyScan.setVisible(false);
	
	attachChild(babyScan);
	attachChild(mainSprite);
	setBuildingCanBroke();
	setIdleAnimationId(43);
	setHealingAnimationId(45);
	setBrokedAnimationId(46);
	setFocusTileIndex(19);
	setState(STATE_IDLE);
	
	addGameCharactorOnReceivedPosition(110, 99);
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
	babyScan.setVisible(true);
	setAnimation(babyScan, ResourceManager.getInstance().getAnimationInfo(44));
	
 }

  @Override
  public void onHealing(float pSecondsElapsed) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void onfinishHealing() {
    babyScan.stopAnimation(0);
    babyScan.setVisible(false);	
  }
  
  @Override
  protected void setPatientOnReceived(Patient patient) {
    patient.layIn(true);	  
  }

  @Override
  protected void onDrawChildren(GL10 pGL, Camera pCamera) {
    babyScan.onDraw(pGL, pCamera);
    Patient patient = getCurrentPatient(); 
    if(patient != null){
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
    
  }
  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
	/*AnimatedSprite babyScan = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_BABYSCAN));
	babyScan.setCurrentTileIndex(0);
	attachChild(babyScan);
	attachChild(mainSprite);*/
	return null;
  }

	
  
}
