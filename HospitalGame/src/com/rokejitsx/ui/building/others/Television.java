package com.rokejitsx.ui.building.others;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.GameObject;

public class Television extends GameObject{
  
  private AnimatedSprite tvSprite;	
	  
  public Television(){
	super(MONTAGE_TV);
	/*tvSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_TV));	  
    attachChild(tvSprite); */
  }	
	
  /*@Override
  public Shape onInitialBody() {
	Rectangle body = new Rectangle(0, 0, 50, 50);
	body.setColor(0, 1, 0);	
	return body;
  }*/
}
