package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiHUDAlert extends DataHolder{

  public final static String ALERT_BLINK_UPDATE 		= "AlertBlinkUpdate";	
  public final static String ALERT_BLINK_UPDATE_MIN 	= "AlertBlinkUpdateMin";
  
  private final static String[] keys = {
    ALERT_BLINK_UPDATE,	  
    ALERT_BLINK_UPDATE_MIN
  };
  
  public GiHUDAlert(){
    super(GlobalsXmlReader.GLOBAL_GI_HUD_ALERT, keys);	  
  }
  
  public float getAlertBlinkUpdate(){
    return getFloat(ALERT_BLINK_UPDATE);	  
  }
  	
  public float getAlertBlinkUpdateMin(){
    return getFloat(ALERT_BLINK_UPDATE_MIN);	  
  }	
}
