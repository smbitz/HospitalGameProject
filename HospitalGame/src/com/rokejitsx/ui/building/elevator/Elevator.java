package com.rokejitsx.ui.building.elevator;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.ui.building.Building;

public class Elevator extends Building{   	
  public Elevator() {
	super(Building.ELEVATOR, 1);	
	mainSprite.setCurrentTileIndex(2);
  }

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {}  

  /*@Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
    Rectangle body = new Rectangle(0, 0, 70, 70);
	body.setColor(1, 0, 1);
	Text infoText = new Text(0, 0, GameFonts.getInstance().getFont(), "Elevator "+"("+getCurrentFloor()+")", HorizontalAlign.CENTER);
	body.attachChild(infoText);
	return body;
  }*/

  @Override
  public void onFocus() {
	/*// TODO Auto-generated method stub
	super.onFocus();*/
  }

  @Override
  public void onUnFocus() {
	/*// TODO Auto-generated method stub
	super.onUnFocus();*/
  }
  
  

}
