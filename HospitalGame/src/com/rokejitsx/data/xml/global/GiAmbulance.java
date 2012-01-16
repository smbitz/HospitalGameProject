package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiAmbulance extends DataHolder{
  
  public final static String AMBULANCE_SPEED 			= "AmbulanceSpeed";	
  public final static String AMBULANCE_LEAVE_MOVE_FADE 	= "AmbulanceLeaveMoveFade";
  public final static String AMBULANCE_ENTER_MOVE_FADE 	= "AmbulanceEnterMoveFade";
  public final static String AMBULANCE_BREAK_DISTANCE 	= "AmbulanceBrakeDistance";
  
  private final static String[] keys = {
    AMBULANCE_SPEED,
    AMBULANCE_LEAVE_MOVE_FADE,
    AMBULANCE_ENTER_MOVE_FADE,
    AMBULANCE_BREAK_DISTANCE	  
  };
  
  public GiAmbulance(){
    super(GlobalsXmlReader.GLOBAL_GI_AMBULANCE, keys);	  
  }
   
	
}