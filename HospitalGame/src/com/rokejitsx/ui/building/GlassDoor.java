package com.rokejitsx.ui.building;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.xml.AnimationInfo;

public class GlassDoor extends Building{	  
  private AnimationInfo openAnim, closeAnim;	
  private boolean isopen;
  public GlassDoor() {
	super(GLASSDOOR, 0);	
	openAnim = new AnimationInfo("", "", 20, false, false, 4);
	openAnim.setSequence(new int[]{3, 2, 1,0});
	
	closeAnim = new AnimationInfo("", "", 20, false, false, 4);
	closeAnim.setSequence(new int[]{0, 1, 2, 3});
	mainSprite.setCurrentTileIndex(3);
	isopen = false;
  }
  
  public void open(){
	if(isOpen())
	  return;
	isopen = true;
	//mainSprite.stopAnimation(3);
	HospitalGameActivity.getGameActivity().runOnUiThread(new Runnable() {		
	  @Override
	  public void run() {
	    setAnimation(mainSprite, openAnim);			
	  }
	});
    	  
  }
  
  public void close(){
	if(!isopen)
	  return;
	isopen = false;
	//mainSprite.stopAnimation(0);
    
    HospitalGameActivity.getGameActivity().runOnUiThread(new Runnable() {		
  	  @Override
  	  public void run() {
  	    setAnimation(mainSprite, closeAnim);			
  	  }
  	});
  }
  
  public boolean isOpen(){
    return isopen;	  
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
