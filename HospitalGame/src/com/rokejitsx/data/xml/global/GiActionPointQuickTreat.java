package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.global.giactionpoint.GiActionPointBuilding;

public class GiActionPointQuickTreat extends GiActionPointBuilding {
  public final static String TIME_BEFORE_CLEAN		 = "TimeBeforeCleanParticles";
  
  private final static String[] keys = {
    IN_USE_TIME,
	DAMAGE,
	BILL,
	PRICE,
	ENERGY,
	SALARY,
	TIME_BEFORE_CLEAN,
	MAINTENANCE,
	MIN_HOSPITAL_LEVEL  	  
  };
  
  public GiActionPointQuickTreat(){
   super(GlobalsXmlReader.GLOBAL_GI_ACTION_POINT_QUICKTREAT, keys);	  
  }
  
  public float getTimeBeforeClean(){
    return getFloat(TIME_BEFORE_CLEAN);	  
  }
  
}
