package com.rokejitsx.data.xml.global.giactionpoint;


public class GiActionPointFullBuilding extends GiActionPointBuilding{
  private final static String[] keys = {
    IN_USE_TIME,
    DAMAGE,
    BILL,
    PRICE,
    ENERGY,
    SALARY,
    MAINTENANCE,
    MIN_HOSPITAL_LEVEL,
    REPAIR_TIME
  };
  
  public GiActionPointFullBuilding(String tagName){
    super(tagName, keys);	  
  }
}
