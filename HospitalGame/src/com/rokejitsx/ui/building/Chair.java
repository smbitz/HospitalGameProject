package com.rokejitsx.ui.building;

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

  @Override
  public boolean onReceive(GameCharactor gameChar) {
    Patient patient = (Patient) gameChar;
    Log.d("RokejitsX", "Chair receive patient = "+patient.getQueue());
	patient.setPickable(true);	
	if(!patient.isInProgress())
      patient.nextHealingRoute();
    return true;	  
  }
  
  @Override
  protected void setGameChatactorOnReceived(GameCharactor gameChar) {
    Patient patient = (Patient) gameChar;
    patient.sit(false);  	
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
	
  @Override
  public GameObject isBuildingContain(float x, float y){	    
    return null;
  }
}
