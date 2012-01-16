package com.rokejitsx.data.xml.global.giactionpoint;


public class GiActionPointMidBuilding extends GiActionPointBuilding{
  private final static String[] keys = {
    IN_USE_TIME,
    DAMAGE,
    BILL,
    PRICE,
    ENERGY,
	SALARY,
	MAINTENANCE,
	MIN_HOSPITAL_LEVEL	   
  };
  
  public GiActionPointMidBuilding(String tagName){
    super(tagName, keys);	  
  }
  	
	
}
