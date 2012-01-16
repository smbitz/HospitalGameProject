package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiEndScreen extends DataHolder{
  public final static String OBJECTIVES_RAISE_PER_SECOND	= "ObjectivesRaisePerSecond";
  public final static String FUNDS_RAISE_PER_SECOND 		= "FundsRaisePerSecond";
  public final static String SLIDE_SPEED 					= "SlideSpeed";
  public final static String MOVE_FADE_IN 					= "MoveFadeIn";
  public final static String MOVE_FADE_OUT 					= "MoveFadeOut";
  
  private final static String[] keys = {
    OBJECTIVES_RAISE_PER_SECOND,
    FUNDS_RAISE_PER_SECOND,
    SLIDE_SPEED,
    MOVE_FADE_IN,
    MOVE_FADE_OUT	  
  };
  
  public GiEndScreen(){
    super(GlobalsXmlReader.GLOBAL_GI_END_SCREEN, keys);	  
  }
  
  	
}
