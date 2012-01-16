package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.global.giactionpoint.GiActionPointBuilding;

public class GiActionPointChair extends GiActionPointBuilding {
  private final static String[] keys = {
    BILL,
    PRICE,
    ENERGY,
    SALARY,
    MAINTENANCE	    
  };  
  public GiActionPointChair(){
    super(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_CHAIR, keys);	  
  }
  
}
