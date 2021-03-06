package com.rokejitsx.ui.building;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.ui.nurse.Nurse;

public class Laundry extends Building{  
  public Laundry(){
    super(LAUNDRY, 0); 
    setCheckPosition(0, 0);
  }

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {
	if(gameChar instanceof Nurse){
	  Nurse nurse = (Nurse) gameChar;	
	  nurse.handOutDust();
	}	
  }
  
  @Override
  protected void setGameChatactorOnReceived(GameCharactor gameChar) {}
  	
}
