package com.rokejitsx.ui.building.others;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.GameObject;

public class Food extends GameObject{
  //private AnimatedSprite foodSprite;	
	
  public Food(){
	super(MONTAGE_FOOD);  
    /*foodSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_FOOD));	  
    attachChild(foodSprite);*/
  }	
  
  /*@Override
  public Shape onInitialBody() {
    Rectangle body = new Rectangle(0, 0, 50, 50);
	body.setColor(0, 1, 0);	
	return null;
  }	*/
}
