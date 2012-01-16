package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiWorkShop extends DataHolder{
  public final static String BACK_SLIDE_SPEED 		= "BackSlideSpeed"; 
  public final static String BACK_MOVE_FADE_IN 		= "BackMoveFadeIn";
  public final static String BACK_MOVE_FADE_OUT 	= "BackMoveFadeOut";
  public final static String MONEY_UPDATE_TIME 		= "MoneyUpdateTime";
  public final static String MAX_BUTTONS 			= "MaxButtons";
  public final static String BUTTON_COLS 			= "ButtonCols";
  public final static String BUTTON_ROWS 			= "ButtonRows";
  public final static String BUTTON_START_X 		= "ButtonStartX";
  public final static String BUTTON_START_Y 		= "ButtonStartY";
  public final static String BUTTON_OFFSET_X 		= "ButtonOffsetX";
  public final static String BUTTON_OFFSET_Y 		= "ButtonOffsetY";
  public final static String DONE_BUTTON_OFFSET_X 	= "DoneButtonOffsetX";
  public final static String DONE_BUTTON_OFFSET_Y 	= "DoneButtonOffsetY";
  public final static String TIME_AFTER_PLACE 		= "TimeAfterPlace";
  public final static String CHEAT_MONEY_AMOUNT 	= "CheatMoneyAmount";
  
  private final static String[] keys = {
    BACK_SLIDE_SPEED,
    BACK_MOVE_FADE_IN,
    BACK_MOVE_FADE_OUT,
    MONEY_UPDATE_TIME,
    MAX_BUTTONS,
    BUTTON_COLS,
    BUTTON_ROWS,
    BUTTON_START_X,
    BUTTON_START_Y,
    BUTTON_OFFSET_X,
    BUTTON_OFFSET_Y,
    DONE_BUTTON_OFFSET_X,
    DONE_BUTTON_OFFSET_Y,
    TIME_AFTER_PLACE,
    CHEAT_MONEY_AMOUNT	  
  };
  
  public GiWorkShop(){
    super(GlobalsXmlReader.GLOBAL_GI_WORKSHOP, keys);	  
  }
  
  
}
