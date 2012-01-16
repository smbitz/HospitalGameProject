package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.global.giactionpoint.GiActionPointBuilding;

public class GiActionPointPharmacy extends GiActionPointBuilding {
  public final static String TIME_BETWEEN_NEW_AND_CALL	 = "TimeBetweenNewAndCall";
  public final static String RECIPE_COST			 	 = "RecipeCost";
	
  private final static String[] keys = {
    TIME_BETWEEN_NEW_AND_CALL,
    RECIPE_COST,
    PRICE,
    ENERGY,
    SALARY,
    MAINTENANCE,
	IN_USE_TIME	  
  };	
  
  public GiActionPointPharmacy(){
    super(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_PHARMACY, keys);	  
  }
  
  public float getTimeBetweenNewAndCall(){
    return getFloat(TIME_BETWEEN_NEW_AND_CALL);	  
  }
  
  public float getRecipeCost(){
    return getFloat(RECIPE_COST);	  
  }
  
	
}
