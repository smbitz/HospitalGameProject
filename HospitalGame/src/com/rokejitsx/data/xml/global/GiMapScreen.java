package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiMapScreen extends DataHolder{
  public final static String PLANE_MOVE_SPEED 		= "PlaneMoveSpeed"; 
  public final static String PLANE_MOVE_FADE_IN 	= "PlaneMoveFadeIn";
  public final static String PLANE_MOVE_FADE_OUT 	= "PlaneMoveFadeOut";
  public final static String PLANE_DIST_TO_FADE 	= "PlaneDistToFade";
  public final static String PLANE_DIST_TO_SCALE 	= "PlaneDistToScale";
  public final static String PLANE_SCALE_SPEED 		= "PlaneScaleSpeed";
  public final static String PLANE_MIN_FADE 		= "PlaneMinFade";
  public final static String PLANE_MAX_FADE 		= "PlaneMaxFade";
  public final static String PLANE_MIN_SCALE 		= "PlaneMinScale";
  public final static String PLANE_MAX_SCALE 		= "PlaneMaxScale";
  public final static String HOSPITAL_BLINK_SPEED 	= "HospitalBlinkSpeed";
  public final static String HOSPITAL_MAX_FADE 		= "HospitalMaxFade";
  public final static String HOSPITAL_MIN_FADE 		= "HospitalMinFade";
  
  private final static String[] keys = {
    PLANE_MOVE_SPEED,
    PLANE_MOVE_FADE_IN,
    PLANE_MOVE_FADE_OUT,
    PLANE_DIST_TO_FADE,
    PLANE_DIST_TO_SCALE,
    PLANE_SCALE_SPEED,
    PLANE_MIN_FADE,
    PLANE_MAX_FADE,
    PLANE_MIN_SCALE,
    PLANE_MAX_SCALE,
    HOSPITAL_BLINK_SPEED,
    HOSPITAL_MAX_FADE,
    HOSPITAL_MIN_FADE	  
  };
  
  public GiMapScreen(){
    super(GlobalsXmlReader.GLOBAL_GI_MAP_SCREEN, keys);	  
  }
	
    
}
