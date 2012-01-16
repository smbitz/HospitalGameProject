package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiMessageBox extends DataHolder{
  public final static String BLACK_FADE_ALPHA = "BlackFadeAlpha";
  public final static String BLACK_FADE_SPEED = "BlackFadeSpeed";
	
  private final static String[] keys = {
    BLACK_FADE_ALPHA,
    BLACK_FADE_SPEED
  };
  
  public GiMessageBox(){
    super(GlobalsXmlReader.GLOBAL_GI_MESSAGE_BOX, keys);	  
  }
}
