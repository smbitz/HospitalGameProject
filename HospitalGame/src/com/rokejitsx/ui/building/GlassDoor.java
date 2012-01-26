package com.rokejitsx.ui.building;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameObject;

public class GlassDoor extends Building{	  
  	
  public GlassDoor() {
	super(GLASSDOOR, 0);	
  }

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {} 

  @Override
  protected void setGameChatactorOnReceived(GameCharactor gameChar) {}
  
  @Override
  public GameObject isBuildingContain(float x, float y){	    
    return null;
  }

}
