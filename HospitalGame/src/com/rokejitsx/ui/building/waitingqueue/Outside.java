package com.rokejitsx.ui.building.waitingqueue;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.patient.Patient;

public class Outside extends Building{  
  public Outside() {
    super(OUTSIDE, 15);
    //setAlpha(0);
    for(int i = 0;i < 15;i++){
      addGameCharactorOnReceivedPosition(66 +(24 * i), 41 + (20 * i));	
    }
    
    //setWidth(50);
    //setHeight(50);
    //setColor(1,1,1,1);
  }

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {}
  
  
  @Override
  public boolean onReceive(GameCharactor gameChar) {    
    return true;	  
  }
  
  @Override
  protected void setGameChatactorOnReceived(GameCharactor gameChar) {
	//Log.d("rokejitsX", "OutSide setGameChatactorOnReceived = "+gameChar.isVisible()+"***************************************************");
	Patient patient = (Patient) gameChar;
	patient.setPickable(true);
    patient.idle(false);	  
  }
  
  
  
  

}
