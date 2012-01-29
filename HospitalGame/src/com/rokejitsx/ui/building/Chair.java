package com.rokejitsx.ui.building;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;

import android.util.Log;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.ui.patient.Patient;

public class Chair extends Building { 
  public Chair(){
    super(CHAIR, 4);    
    addGameCharactorOnReceivedPosition(56, 39);
    addGameCharactorOnReceivedPosition(84, 63);
    addGameCharactorOnReceivedPosition(115, 88);
    addGameCharactorOnReceivedPosition(152, 115);
  }
  
  public boolean receiveCharator(GameCharactor gameChar){
	Patient patient = (Patient) gameChar;
	if(patient.getPatientId() != 6 && patient.getPatientId() != 5)
	  return super.receiveCharator(gameChar);
	if(patient.getPatientId() == 5){
	  if(!super.receiveCharator(patient))
	    return false;
	  patient.setPickable(false);
	  receiveCharator(patient.getBabyPatient());
	  return true;	  
	}
	if(!patient.isMoveout()){
	  gameChar.setVisible(true);
      if(gameChar.getCurrentBuilding() != null)
        gameChar.getCurrentBuilding().removeCharactor(gameChar);	
	  gameChar.setCurrentFloor(this.getCurrentFloor());
	  gameChar.setCurrentBuilding(this);	
	  if(onReceive(gameChar)){
	    onGameCharactorCallback(gameChar);     
	  }
	}else{
	  patient.setVisible(false);
	  patient.whoIsYourMom().moveOut();
	}
    return true;		  
  }
	  
  public void onGameCharactorCallback(GameCharactor gameChar){
	Patient patient = (Patient) gameChar;
	if(patient.getPatientId() != 6){
	  super.onGameCharactorCallback(gameChar);
	}else{
      //int index = getGameObjectInVisitorQueueIndex(patient.whoIsYourMom());    
	  //setGameCharactorPositionOnReceived(gameChar,index);
	  Patient momPatient = patient.whoIsYourMom();
	  patient.setPositionOnBuildingReceived(momPatient.getX() + 65, momPatient.getY() + 55);
	  setGameChatactorOnReceived(gameChar);	
	}
	
  }

  @Override
  public boolean onReceive(GameCharactor gameChar) {
    Patient patient = (Patient) gameChar;
    //Log.d("RokejitsX", "Chair receive patient = "+patient.getQueue());
    if(patient.getPatientId() != 5){
	  patient.setPickable(true);	
	  if(!patient.isInProgress())
        patient.nextHealingRoute();
	  patient.setShowBubble(true);
    }/*else{
      receiveCharator(patient.getBabyPatient());	
    }*/
    return true;	  
  }
  
  @Override
  protected void setGameChatactorOnReceived(GameCharactor gameChar) {
    Patient patient = (Patient) gameChar;
    if(patient.getPatientId() != 6)
      patient.sit(false);
    else
      patient.sit(true);      
  }
  
  @Override
  protected void onDrawChildren(GL10 pGL, Camera pCamera) {
	super.onDrawChildren(pGL, pCamera);     	   	  
    if(visitors == null)
      return;
 	for(int i = 0;i < visitors.length;i++){
 	  Patient patient = (Patient) getCharactor(i);
 	  if(patient != null && !patient.isOnPick()){
 		Patient babyPatient = patient.getBabyPatient();
 		if(babyPatient != null && !babyPatient.isOnPick() && this.equals(babyPatient.getCurrentBuilding()))
 		  babyPatient.onDraw(pGL, pCamera);
 	  }
 	   
 	    
 	}
 	  
 	  
 	//}
  }

  @Override
  public void onFocus() {
	mainSprite.setCurrentTileIndex(1);	
  }



  @Override
  public void onUnFocus() {
	mainSprite.setCurrentTileIndex(0);	
  } 

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {}
	
  /*@Override
  public GameObject isBuildingContain(float x, float y){
	if(mainSprite.contains(x, y))
      return this;
	return null;
  }*/
}
