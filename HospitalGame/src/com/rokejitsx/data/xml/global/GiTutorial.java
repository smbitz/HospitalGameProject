package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiTutorial extends DataHolder{
  public final static String ARROW_FADE_SPEED 		= "ArrowFadeSpeed";
  public final static String ARROW_MOVE_OFFSET 		= "ArrowMoveOffset";
  public final static String ARROW_MOVE_SPEED 		= "ArrowMoveSpeed";
  public final static String ARROW_MOVE_FADE_IN 	= "ArrowMoveFadeIn";
  public final static String ARROW_MOVE_FADE_OUT 	= "ArrowMoveFadeOut";
  public final static String BACK_MOVE_SPEED 		= "BackMoveSpeed";
  public final static String BACK_MOVE_FADE_IN 		= "BackMoveFadeIn";
  public final static String BACK_MOVE_FADE_OUT 	= "BackMoveFadeOut";
  
  private final static String[] keys = {
    ARROW_FADE_SPEED,
    ARROW_MOVE_OFFSET,
    ARROW_MOVE_SPEED,
    ARROW_MOVE_FADE_IN,
    ARROW_MOVE_FADE_OUT,
    BACK_MOVE_SPEED,
    BACK_MOVE_FADE_IN,
    BACK_MOVE_FADE_OUT	  
  };
  
  public GiTutorial(){
    super(GlobalsXmlReader.GLOBAL_GI_TUTORIAL, keys);	  
  }
	
}
