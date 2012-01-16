package com.rokejitsx.ui.building.others;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.resource.ResourceManager;

public class Water extends GameObject{ 
	 
  private AnimatedSprite waterSprite;	
	  
  public Water(){
	super(MONTAGE_WATER);
	/*waterSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_WATER));	  
    attachChild(waterSprite);*/ 
	setAnimation(mainSprite, ResourceManager.getInstance().getAnimationInfo(18));
  }		
 /* @Override
  public Shape onInitialBody() {
    Rectangle body = new Rectangle(0, 0, 50, 50);
	body.setColor(0, 1, 0);	
	return body;
  }	*/
}
