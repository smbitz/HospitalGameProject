package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiMenuHighscores extends DataHolder{

  public final static String HIGH_SCORES_MAX_COUNT = "HighscoresMaxCount";
  
  private final static String[] keys = { 
    HIGH_SCORES_MAX_COUNT	  
  };
  public GiMenuHighscores(){
   super(GlobalsXmlReader.GLOBAL_GI_MENU_HIGHSCORES, keys);	  
  }
}
