package com.rokejitsx.ui.building.waitingqueue;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.ui.building.Building;

public class Outside extends Building{
  public Outside() {
    super(OUTSIDE, 9);
    setAlpha(0);
  }

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {}

  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
   /* Rectangle body = new Rectangle(0, 0, 30 * 4, 50);
	body.setColor(0, 1, 1);	*/
	return null;
  }

  

}
