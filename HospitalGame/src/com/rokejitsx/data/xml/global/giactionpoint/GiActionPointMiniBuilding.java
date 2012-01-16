package com.rokejitsx.data.xml.global.giactionpoint;


public class GiActionPointMiniBuilding extends GiActionPointBuilding {
  private final static String[] keys = {
    IN_USE_TIME,
    DAMAGE,
    BILL,
    PRICE,
    ENERGY,
    SALARY,
    MAINTENANCE		  	  
  };
  
  public GiActionPointMiniBuilding(String tagName){
    super(tagName, keys);	  
  }
  
}
