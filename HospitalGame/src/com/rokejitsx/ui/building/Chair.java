package com.rokejitsx.ui.building;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.ui.patient.Patient;

public class Chair extends Building {
  //private Patient[] waitingPatients;
  private AnimatedSprite chairSprite;
  public Chair(){
    super(CHAIR, 4);    
    /*chairSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_CHAIR));	  
    attachChild(chairSprite);*/
  }

  @Override
  public void onReceive(GameCharactor gameChar) {
    Patient patient = (Patient) gameChar;
	patient.setPickable(true);
	patient.sit();
	if(!patient.isInProgress())
      patient.nextHealingRoute();	
	int index = getGameObjectInVisitorQueueIndex(patient);
	float x = mainSprite.getX();
	float y = mainSprite.getY();
	patient.setPosition(getX() + x - 15 + ((patient.getWidth()/2) * index), (float) (getY() + y + (25.5 * index)));
	
	
    /*for(int i = 0;i < waitingPatients.length;i++){
      if(waitingPatients[i] == null){    	
        waitingPatients[i] = (Patient) gameChar;
        waitingPatients[i].setPosition(this.getX() + (i * gameChar.getWidth()), getY() + getHeight() - gameChar.getHeight());
        waitingPatients[i].setPickable(true);
        if(!waitingPatients[i].isInProgress())
          waitingPatients[i].nextHealingRoute();
        break;
      }	
    }*/	
    //return true;
  }
  
  

  /*@Override
  public void onRemove(GameCharactor gameChar) {
	for(int i = 0;i < waitingPatients.length;i++){
      if(gameChar.equals(waitingPatients[i])){
        waitingPatients[i] = null;
        break;
      }	
	}
        
  }*/
  
  

  @Override
  public void onFocus() {
	mainSprite.setCurrentTileIndex(1);
	//super.onFocus();
  }



  @Override
  public void onUnFocus() {
	mainSprite.setCurrentTileIndex(0);
	//super.onUnFocus();
  }
 
  
  @Override
  public void onPatientCallback(Patient patient) {
	onReceive(patient);
    /*int index = getGameObjectInVisitorQueueIndex(patient);
    if(index != -1){
      patient.sit();
      float x = mainSprite.getX();
      float y = mainSprite.getY();
      patient.setPosition(getX() + x - 15 + ((patient.getWidth()/2) * index), (float) (getY() + y + (25.5 * index)));	
    }*/
	
  }

  /*@Override
  public GameObject isBuildingContain(float x, float y) {	
	return null;
  }*/

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {
	// TODO Auto-generated method stub
	
  }

  /*@Override
  public Shape onInitialBody() {
	Rectangle body = new Rectangle(0, 0, 30 * 4, 50);
	body.setColor(0, 0, 1);	
	return body;
  }*/

	
}
