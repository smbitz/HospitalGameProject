package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiHelicopter extends DataHolder{
	
  public final static String HELICOPTER_SPEED 			= "HelicopterSpeed";	
  public final static String HELICOPTER_MOVE_FADE_IN 	= "HelicopterMoveFadeIn";
  public final static String HELICOPTER_MOVE_FADE_OUT 	= "HelicopterMoveFadeOut";
	
  private final static String[] keys = {
    HELICOPTER_SPEED,
    HELICOPTER_MOVE_FADE_IN,
    HELICOPTER_MOVE_FADE_OUT
  };
  
  public GiHelicopter(){
    super(GlobalsXmlReader.GLOBAL_GI_HELICOPTER, keys);	   
  }
	
}
