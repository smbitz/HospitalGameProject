package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiStretcher extends DataHolder{

  public final static String STRETCHER_SPEED = "StretcherSpeed";
  
  private final static String[] keys = {
    STRETCHER_SPEED	  
  };
  
  public GiStretcher(){
    super(GlobalsXmlReader.GLOBAL_GI_STRETCHER, keys);	  
  }
}
