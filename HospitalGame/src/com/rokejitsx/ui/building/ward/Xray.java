package com.rokejitsx.ui.building.ward;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.global.GlobalsXmlReader;
import com.rokejitsx.ui.patient.Patient;

public class Xray extends Ward{
  
  public Xray() {
	super(XRAY, 1);
	setBuildingCanBroke();
	setFocusTileIndex(7);
	setIdleAnimationId(1);
    setHealingAnimationId(2);
    setBrokedAnimationId(3);    
    setState(STATE_IDLE);
    addGameCharactorOnReceivedPosition(151, 120);
    
    initialFromGlobal(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_XRAY);
    setCheckPosition(0, 0);
  } 

  @Override
  public void onWardReceivePatient(Patient patient) {
	// TODO Auto-generated method stub
	
  }
  
  @Override
  protected void setPatientOnReceived(Patient patient) {
    patient.idle(true);  	  
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
	// TODO Auto-generated method stub
	if(this.isVisible())
	  getCurrentPatient().setVisible(true);
	
  }
  
  @Override
  protected void onDrawChildren(GL10 pGL, Camera pCamera) {
	Patient patient = getCurrentPatient();
	if(patient != null && !patient.isOnPick()){
	  patient.onDraw(pGL, pCamera);	
	}
	mainSprite.onDraw(pGL, pCamera);
	drawChecker(pGL, pCamera);
  }

/*@Override
public Shape onInitialBody(AnimatedSprite mainSprite) {
  AnimatedSprite xraySprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_XRAY));
  xraySprite.setCurrentTileIndex(0);
  attachChild(xraySprite);	
  attachChild(mainSprite);
  return null;
}*/
  
  
  
  

	
  	
}
