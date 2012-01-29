package com.rokejitsx.ui.building.elevator;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.xml.AnimationInfo;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.patient.Patient;

public class Elevator extends Building{   	
  private AnimationInfo openAnim, closeAnim;
  public Elevator() {
	super(Building.ELEVATOR, 1);	
	openAnim = new AnimationInfo("", "", 20, false, false, 4);
	openAnim.setSequence(new int[]{3 ,2, 1, 0});
	
	closeAnim = new AnimationInfo("", "", 20, false, false, 4);
	closeAnim.setSequence(new int[]{0, 1, 2, 3});
	
	mainSprite.setCurrentTileIndex(3);
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
  public void onFocus() {
    setAnimation(mainSprite, openAnim);	  
  }

  @Override
  public void onUnFocus() {
    setAnimation(mainSprite, closeAnim);	  
  }
  
  

}
