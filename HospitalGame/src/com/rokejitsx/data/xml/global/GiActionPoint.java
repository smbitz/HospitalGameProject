package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiActionPoint extends DataHolder {

  public final static String CHECKBOX_FADE_IN_SPEED  = "CheckBoxFadeInSpeed";
  public final static String CHECKBOX_FADE_OUT_SPEED = "CheckBoxFadeOutSpeed";
  public final static String CHECKBOX_MAX_COUNT      = "CheckBoxMaxCount";
  public final static String MIN_BLINK_FADE          = "MinBlinkFade";
  public final static String MAX_BLINK_FADE          = "MaxBlinkFade";
  public final static String BLINK_SPEED             = "BlinkSpeed";	
	
  private final static String[] keys = {    
    CHECKBOX_FADE_IN_SPEED,	  
    CHECKBOX_FADE_OUT_SPEED,
    CHECKBOX_MAX_COUNT,
    MIN_BLINK_FADE,
    MAX_BLINK_FADE,
    BLINK_SPEED
  };	
	
  public GiActionPoint() {
	super(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT, keys);	
  }
  
  public float getCheckboxFadeInSpeed(){
    return getFloat(CHECKBOX_FADE_IN_SPEED);	  
  }
  
  public float getCheckboxFadeOutSpeed(){
    return getFloat(CHECKBOX_FADE_OUT_SPEED);	  
  }
  
  public int getCheckboxMaxCount(){
    return getInt(CHECKBOX_MAX_COUNT);  	  
  }
  
  public float getMinBlinkFade(){
    return getFloat(MIN_BLINK_FADE);	  
  }
  
  public float getMaxBlinkFade(){
    return getFloat(MAX_BLINK_FADE);	  
  }
  
  public float getBlinkSpeed(){
    return getFloat(BLINK_SPEED);	  
  }

}
