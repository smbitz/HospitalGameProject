package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiHUDObjectives extends DataHolder{

  public final static String SATISFACTION_GAIN_PER_SECOND = "SatisfactionGainPerSecond";
  
  private final static String[] keys = {
    SATISFACTION_GAIN_PER_SECOND  	  
  };
  
  public GiHUDObjectives(){
    super(GlobalsXmlReader.GLOBAL_GI_HUD_OBJECTIVES, keys);	  
  }
  
  public float getSatisfactionGainPerSecond(){
    return getFloat(SATISFACTION_GAIN_PER_SECOND);	  
  }
  
}
