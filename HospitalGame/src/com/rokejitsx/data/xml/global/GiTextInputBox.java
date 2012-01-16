package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiTextInputBox extends DataHolder{
  public final static String CARRET_ON_TIME = "CarretOnTime"; 
  public final static String CARRET_OFF_TIME = "CarretOffTime";
  
  private final static String[] keys = {
    CARRET_ON_TIME,
    CARRET_OFF_TIME
  };
  
  public GiTextInputBox(){
    super(GlobalsXmlReader.GLOBAL_GI_TEXT_INPUT_BOX, keys);	  
  }
	
}
