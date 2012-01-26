package com.rokejitsx.ui.building.elevator;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.patient.Patient;

public class Elevator extends Building{   	
  public Elevator() {
	super(Building.ELEVATOR, 1);	
	mainSprite.setCurrentTileIndex(2);
  }

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {}
  
  @Override  
  protected void setGameChatactorOnReceived(GameCharactor gameChar) {
    if(gameChar instanceof Patient){
      Patient patient = (Patient) gameChar;
      patient.idle(false);
    }	  
    //gameChar.setVisible(false);
  }
  
  @Override
  public void onFocus() {}

  @Override
  public void onUnFocus() {}
  
  

}
