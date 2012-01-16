package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiCarriableContainer extends DataHolder{

  public final static String RECIPE_FADE_SPEED = "RecipeFadeSpeed";
  
  private final static String[] keys = {
    RECIPE_FADE_SPEED	  
  };
	
  public GiCarriableContainer(){
    super(GlobalsXmlReader.GLOBAL_GI_CARRIABLE_CONTAINER, keys);	  
  }
  
  public float getRecipeFadeSpeed(){
    return getFloat(RECIPE_FADE_SPEED);	  
  }
  
}
