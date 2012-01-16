package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiActionPointElevator extends DataHolder{
  public final static String DOOR_MIN_DISTANCE 	= "DoorMinDistance";	
  public final static String FRAME_SECONDS 		= "FrameSeconds";
	
  private final static String[] keys = {
    DOOR_MIN_DISTANCE,
    FRAME_SECONDS
  };
  
  public GiActionPointElevator(){
	 super(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_ELEVATOR, keys);  
  }
}
