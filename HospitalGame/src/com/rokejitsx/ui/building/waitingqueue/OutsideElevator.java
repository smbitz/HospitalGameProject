package com.rokejitsx.ui.building.waitingqueue;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.patient.Patient;

public class OutsideElevator extends Building{
  public OutsideElevator() {
	super(OUTSIDE_ELEVATOR, 15);
	setAlpha(0);
	for(int i = 0;i < 15;i++){
      addGameCharactorOnReceivedPosition(0 +(24 * i), 41 + (20 * i));	
	}
  }  

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {}
  
  @Override
  public boolean onReceive(GameCharactor gameChar) {
	
    return true;	  
  }
  @Override
  protected void setGameChatactorOnReceived(GameCharactor gameChar) {
    Patient patient = (Patient) gameChar;
    patient.setPickable(true);
    patient.idle(false);	  	  
  }

  @Override
  public void onFocus() {

 }

  @Override
  public void onUnFocus() {	
  }

  
  
  

}
