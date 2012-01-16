package com.rokejitsx.ui.building;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.GameCharactor;

public class GlassDoor extends Building{
  private AnimatedSprite glassdoorSprite;	
	  
  	
  public GlassDoor() {
	super(GLASSDOOR, 0);
	/*glassdoorSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_GLASSDOOR));	  
    attachChild(glassdoorSprite);*/
  }

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
    attachChild(mainSprite);	
	return null;
  }

}
