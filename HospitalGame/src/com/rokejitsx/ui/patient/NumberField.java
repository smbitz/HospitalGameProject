package com.rokejitsx.ui.patient;

import com.rokejitsx.data.GameObject;

public class NumberField extends GameObject{

  public NumberField(){
    super(NUMBERS);  
    
  }	
  
  public void setNumber(int number){
    mainSprite.setCurrentTileIndex(9 - number);	  
  }
}
