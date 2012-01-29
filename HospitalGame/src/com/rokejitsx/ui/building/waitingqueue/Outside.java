package com.rokejitsx.ui.building.waitingqueue;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.patient.Patient;

public class Outside extends Building{  
  public Outside() {
    super(OUTSIDE, 15);
    //setAlpha(0);
    int x = 66;
    int y = 0;
    for(int i = 0;i < 15;i++, y++){
      addGameCharactorOnReceivedPosition(x +(24 * y), 41 + (20 * y));
      if(i != 0 && i % 4 == 0){
        x = x - 30;	  
        y = 0;
      }
    }
    
    //setWidth(50);
    //setHeight(50);
    //setColor(1,1,1,1);
  }

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {}
  
  
  @Override
  public boolean onReceive(GameCharactor gameChar) {    
	Patient patient = (Patient) gameChar;
    //Log.d("RokejitsX", "Chair receive patient = "+patient.getQueue());
    if(patient.getPatientId() != 5){
	  patient.setPickable(true);	
	  if(!patient.isInProgress())
        patient.nextHealingRoute();
    }else{
      patient.setPickable(true);
      patient.getBabyPatient().nextHealingRoute();
      patient.getBabyPatient().setShowBubble(false);
      //patient.getBabyPatient().setVisible(true);
    }
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
