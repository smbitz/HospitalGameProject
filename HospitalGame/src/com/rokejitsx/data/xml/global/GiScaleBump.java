package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiScaleBump extends DataHolder{
  public final static String MAX_TIME = "MaxTime";
  
  private final static String[] keys = {
    MAX_TIME	  
  };
  
  public GiScaleBump(){
    super(GlobalsXmlReader.GLOBAL_GI_SCALE_BUMP, keys);	  
  }
}
