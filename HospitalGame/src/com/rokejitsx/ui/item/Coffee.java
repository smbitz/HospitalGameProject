package com.rokejitsx.ui.item;

public class Coffee extends Item{

  public Coffee(){
    super(COFFEE, 0);	  
  }

  @Override
  public Item deepCopy() {
	// TODO Auto-generated method stub
	return new Coffee();
  }	
}
