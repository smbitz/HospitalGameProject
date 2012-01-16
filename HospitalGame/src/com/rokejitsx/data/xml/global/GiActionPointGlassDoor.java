package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiActionPointGlassDoor extends DataHolder {

  public final static String GLASSDOOR_MIN_DISTANCE  = "GlassDoorMinDistance";  	
  public final static String FRAME_SECONDS			 = "FrameSeconds";
  
  private final static String[] keys = {
    GLASSDOOR_MIN_DISTANCE,
    FRAME_SECONDS
  };
  
  public GiActionPointGlassDoor(){
    super(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_GLASSDOOR, keys);	  
  }
  
  public float getGlassDoorMinDistance(){
    return getFloat(GLASSDOOR_MIN_DISTANCE);	  
  }
  
  public float getFrameSeconds(){
    return getFloat(FRAME_SECONDS);	  
  }
  	
}
