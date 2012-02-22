package com.rokejitsx.ui.building.ward;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.global.GlobalsXmlReader;
import com.rokejitsx.ui.patient.Patient;

public class Chemotherapy extends Ward{
  private AnimatedSprite chemoBaseSprite;
  public Chemotherapy() {
    super(CHEMOTHERAPY, 1);
    chemoBaseSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_CHEMOTHERAPY));
    
    setBuildingCanBroke();
    setIdleAnimationId(38);
    setHealingAnimationId(39);
    setBrokedAnimationId(40);
    setFocusTileIndex(9);
    chemoBaseSprite.setVisible(false);
    attachChild(chemoBaseSprite);
    attachChild(mainSprite);
    
    setState(STATE_IDLE);
    addGameCharactorOnReceivedPosition(103, 101);
    
    initialFromGlobal(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_CHEMOTHERAPY);
    setCheckPosition(0, 0);
    
  }

  @Override
  public void onWardReceivePatient(Patient patient) {}
  
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
	// TODO Auto-generated method stub
	getCurrentPatient().setVisible(false);  
    chemoBaseSprite.setVisible(true);
    setAnimation(chemoBaseSprite, ResourceManager.getInstance().getAnimationInfo(38));
	
  }

  @Override
  public void onHealing(float pSecondsElapsed) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void onfinishHealing() {
	chemoBaseSprite.stopAnimation(0);  
    chemoBaseSprite.setVisible(false);
    if(isVisible())
      getCurrentPatient().setVisible(true);
	
	
  }

  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
	/*AnimatedSprite chemo = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_CHEMOTHERAPY));
	chemo.setCurrentTileIndex(0);
	attachChild(chemo);
	attachChild(mainSprite);*/
	return null;
  }

  

}
