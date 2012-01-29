package com.rokejitsx.ui.building.waitingqueue;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.patient.Patient;

public class OutsideElevator extends Building{
  public OutsideElevator() {
	super(OUTSIDE_ELEVATOR, 15);
	setAlpha(0);
	
	addGameCharactorOnReceivedPosition(0, -30);
	addGameCharactorOnReceivedPosition(26, -30);
	addGameCharactorOnReceivedPosition(52, -30);
	
	addGameCharactorOnReceivedPosition(15, -15);
	addGameCharactorOnReceivedPosition(40, -15);
	addGameCharactorOnReceivedPosition(65, -15);
	
	addGameCharactorOnReceivedPosition(0, 0);
	addGameCharactorOnReceivedPosition(26, 0);
	addGameCharactorOnReceivedPosition(52, 0);
	
	addGameCharactorOnReceivedPosition(0, 100);
	addGameCharactorOnReceivedPosition(26, 100);
	addGameCharactorOnReceivedPosition(52, 100);
	
	addGameCharactorOnReceivedPosition(15, 115);
	addGameCharactorOnReceivedPosition(40, 115);
	addGameCharactorOnReceivedPosition(65, 115);
	//addGameCharactorOnReceivedPosition(40, 115);
	
	/*for(int i = 0;i < 15;i++){
      addGameCharactorOnReceivedPosition(0 +(24 * i), 41 + (20 * i));	
	}*/
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
