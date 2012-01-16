package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiHUD extends DataHolder{

  public final static String MONEY_RAISE_PER_SECOND = "MoneyRaisePerSecond";
  
  private final static String[] keys = {
    MONEY_RAISE_PER_SECOND	  
  };
  
  public GiHUD(){
    super(GlobalsXmlReader.GLOBAL_GI_HUD, keys);	  
  }
  
  public float getMoneyRaisePerSecond(){
    return getFloat(MONEY_RAISE_PER_SECOND);	  
  }
  
}
