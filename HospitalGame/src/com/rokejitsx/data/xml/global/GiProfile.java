package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiProfile extends DataHolder{
  public final static String NAME_MAX_CHARS = "NameMaxChars";	
  public final static String MAX_PROFILES 	= "MaxProfiles";
	
  private final static String[] keys = {
    NAME_MAX_CHARS,
    MAX_PROFILES
  };
  
  public GiProfile(){
    super(GlobalsXmlReader.GLOBAL_GI_PROFILE, keys);	  
  }
  
}
