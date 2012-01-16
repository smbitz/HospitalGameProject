package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiMenu extends DataHolder {

  public final static String NEXT_BUTTON_SLIDE_TIMEOUT 	= "NextButtonSlideTimeout";	
  public final static String BUTTON_SLIDE_SPEED 		= "ButtonSlideSpeed";
  public final static String BUTTON_MOVE_FADE_IN 		= "ButtonMoveFadeIn";
  public final static String BUTTON_MOVE_FADE_OUT 		= "ButtonMoveFadeOut";
  public final static String BACK_SLIDE_SPEED 			= "BackSlideSpeed";
  public final static String BACK_MOVE_FADE_IN 			= "BackMoveFadeIn";
  public final static String BACK_MOVE_FADE_OUT 		= "BackMoveFadeOut";
  public final static String MENU_FADE_IN_SPEED 		= "MenuFadeInSpeed";
  public final static String MENU_FADE_OUT_SPEED 		= "MenuFadeOutSpeed";
	
  private final static String[] keys = {    
    NEXT_BUTTON_SLIDE_TIMEOUT,
    BUTTON_SLIDE_SPEED,
    BUTTON_MOVE_FADE_IN,
    BUTTON_MOVE_FADE_OUT,
    BACK_SLIDE_SPEED,
    BACK_MOVE_FADE_IN,
    BACK_MOVE_FADE_OUT,
    MENU_FADE_IN_SPEED,
    MENU_FADE_OUT_SPEED
  };	
	
  public GiMenu() {
	super(GlobalsXmlReader.GLOBAL_GI_MENU, keys);	
  }  

}
