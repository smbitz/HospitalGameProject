package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class Letter extends DataHolder{
 
  public final static String LETTER_FADE_START = "LetterFadeStart";	
  public final static String LETTER_FADE_END = "LetterFadeEnd";
  public final static String LETTER_FADE_SPEED = "LetterFadeSpeed";
  public final static String LETTER_ZOOM_START = "LetterZoomStart";
  public final static String LETTER_ZOOM_END = "LetterZoomEnd";
  public final static String LETTER_ZOOM_SPEED = "LetterZoomSpeed";
  public final static String LETTER_ZOOM_FADE_IN = "LetterZoomFadeIn";
  public final static String LETTER_ZOOM_FADE_OUT = "LetterZoomFadeOut";
  
  private final static String[] keys = { 
    LETTER_FADE_START,
    LETTER_FADE_END,
    LETTER_FADE_SPEED,
    LETTER_ZOOM_START,
    LETTER_ZOOM_END,
    LETTER_ZOOM_SPEED,
    LETTER_ZOOM_FADE_IN,
    LETTER_ZOOM_FADE_OUT	  
  };
  
  public Letter(){
    super(GlobalsXmlReader.GLOBAL_LETTER, keys);	  
  }
	
}
