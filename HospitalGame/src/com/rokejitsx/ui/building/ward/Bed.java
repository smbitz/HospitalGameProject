package com.rokejitsx.ui.building.ward;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.global.GlobalsXmlReader;
import com.rokejitsx.ui.patient.Patient;

public class Bed extends Ward{
  private AnimatedSprite graficoSprite,bedSprite;	
  public Bed(){    	  
	super(BED, 1);
	graficoSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_GRAFICO));
	bedSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_CAMAS));
	bedSprite.setCurrentTileIndex(3);
	bedSprite.setVisible(false);
    
    
    /*setWidth(mainSprite.getBaseWidth());
    setHeight(mainSprite.getBaseHeight());*/
    setBuildingCanDirty();
    setIdleAnimationId(11);
    setHealingAnimationId(76);
    setDirtyAnimationId(78);
    
    /*setIdleAnimationId(81);
    setHealingAnimationId(82);*/
    //graficoSprite.setCurrentTileIndex(1);
    //graficoSprite.setPosition(getWidth() - mainSprite.getBaseWidth(), 0);
    graficoSprite.setCurrentTileIndex(4);
    graficoSprite.setPosition(getWidth() - graficoSprite.getBaseWidth() - 15, -graficoSprite.getBaseHeight() + 25);
    setFocusTileIndex(4);  
    attachChild(mainSprite);
    attachChild(graficoSprite);
    attachChild(bedSprite);
    setState(STATE_IDLE);
    setOperationTime(5000);
    
    
    addGameCharactorOnReceivedPosition(90, 44);
    initialFromGlobal(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_BED);
    
  } 
  
  /*@Override
  public void onFocus() {
	mainSprite.stopAnimation(4);	
	//super.onFocus();
  }*/



  /*@Override
  public void onUnFocus() {
	setState(STATE_IDLE);
	super.onUnFocus();
  }*/

  @Override
  protected void onSetState(int state) {		
    switch(state){
      case STATE_IDLE:
      case STATE_DIRTY:
    	graficoSprite.stopAnimation(4);        
      break;
      case STATE_DO_WORK:
        setAnimation(graficoSprite, ResourceManager.getInstance().getAnimationInfo(82));	  
      break;
    }
  
  }



@Override
  public void onStartHealing() {
	
	
  }

  @Override
  public void onHealing(float pSecondsElapsed) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void onfinishHealing() {
	
	
  }
  
  @Override
  public void onWardReceivePatient(Patient patient) {
	bedSprite.setVisible(true);    	
    
  }
  
  @Override
  protected void setPatientOnReceived(Patient patient) {
    patient.layIn(false);	
  }

  @Override
  public void onWardRemovePatient(Patient patient) {
    bedSprite.setVisible(false);	
  }
  
  
  
  @Override
  protected void onDrawChildren(GL10 pGL, Camera pCamera) {
    mainSprite.onDraw(pGL, pCamera);
    graficoSprite.onDraw(pGL, pCamera);
    if(getCurrentPatient() != null && !getCurrentPatient().isOnPick())
      getCurrentPatient().onDraw(pGL, pCamera);
    bedSprite.onDraw(pGL, pCamera);
    
  }

  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
	/*Rectangle body = new Rectangle(0, 0, 30 * 4, 50);
	body.setColor(1, 1, 1);
	Text infoText = new Text(0, 0, GameFonts.getInstance().getFont(), "Healing Ward2 "+"("+getCurrentFloor()+")", HorizontalAlign.CENTER);
	body.attachChild(infoText);*/
	return null;
  }

 
  
}