package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiMachineScroller extends DataHolder{
  public final static String SCROLL_SPEED 			= "ScrollSpeed";
  public final static String SCROLL_MOVE_FADE_IN 	= "ScrollMoveFadeIn";
  public final static String SCROLL_MOVE_FADE_OUT 	= "ScrollMoveFadeOut";
  public final static String SCROLL_MOVE_AMOUNT 	= "ScrollMoveAmount";
  
  private final static String[] keys = {
    SCROLL_SPEED,
    SCROLL_MOVE_FADE_IN,
    SCROLL_MOVE_FADE_OUT,
    SCROLL_MOVE_AMOUNT
  };
  
  public GiMachineScroller(){
    super(GlobalsXmlReader.GLOBAL_GI_MACHINE_SCROLLER, keys);	  
  }
	
}
