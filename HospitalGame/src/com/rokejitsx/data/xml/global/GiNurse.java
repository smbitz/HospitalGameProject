package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiNurse extends DataHolder {
  
  public final static String NURSE_SPEED 				= "NurseSpeed";	
  public final static String CARRY_LIMIT 				= "mCarryLimit";
  public final static String TIME_BETWEEN_CHECK_AND_GO  = "TimeBetweenCheckAndGo";
  public final static String NURSE_SPEED_INCREASE_TIMEOUT 	 = "NurseSpeedIncreaseTimeout";
  public final static String NURSE_SPEED_INCREASE_MULTIPLIER = "NurseSpeedIncreaseMultiplier";
  public final static String NURSE_TRAIL_START_ALPHA 		 = "NurseTrailStartAlpha";
  public final static String NURSE_TRAIL_LIFE_TIME 			 = "NurseTrailLifeTime";
  public final static String NURSE_TRAIL_UPDATE_TIME			 = "NurseTrailUpdateTime";
  
  private final static String[] keys = {
    NURSE_SPEED,
    CARRY_LIMIT,
    TIME_BETWEEN_CHECK_AND_GO,
    NURSE_SPEED_INCREASE_TIMEOUT,
    NURSE_SPEED_INCREASE_MULTIPLIER,
    NURSE_TRAIL_START_ALPHA,
    NURSE_TRAIL_LIFE_TIME,
    NURSE_TRAIL_UPDATE_TIME	  
  };
  
  public GiNurse(){
    super(GlobalsXmlReader.GLOBAL_GI_NURSE, keys);	  
  }
  
  public float getNurseSpeed(){
    return getFloat(NURSE_SPEED);	  
  }
  
  public int getCurryLimit(){
    return getInt(CARRY_LIMIT);	  
  }  
  
  public float getTimeBetweenCheckAndGo(){
    return getFloat(TIME_BETWEEN_CHECK_AND_GO);	  
  }

  public float getNurseSpeedIncreaseTimeout(){
    return getFloat(NURSE_SPEED_INCREASE_TIMEOUT);	  
  }
  
  public float getNurseSpeedIncreaseMultiplier(){
    return getFloat(NURSE_SPEED_INCREASE_MULTIPLIER);	  
  }
  
  public float getNurseTailStartAlpha(){
    return getFloat(NURSE_TRAIL_START_ALPHA);	  
  }
  
  public float getNurseTailLifeTime(){
    return getFloat(NURSE_TRAIL_LIFE_TIME);	  
  }
  
  public float getNurseTailUpdateTime(){
    return getFloat(NURSE_TRAIL_UPDATE_TIME);	  
  }
    
	
}
