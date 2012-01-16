package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class EndlessMode extends DataHolder{

  public final static String ENDLESS_0_INITIAL_INTERVAL 	= "Endless0InitialInterval"; 	
  public final static String ENDLESS_0_DECREASE_INTERVAL 	= "Endless0DecreaseInterval";
  public final static String ENDLESS_0_DECREASE_VALUE 		= "Endless0DecreaseValue";
  public final static String ENDLESS_1_INITIAL_INTERVAL 	= "Endless1InitialInterval"; 	
  public final static String ENDLESS_1_DECREASE_INTERVAL 	= "Endless1DecreaseInterval";
  public final static String ENDLESS_1_DECREASE_VALUE 		= "Endless1DecreaseValue";
  public final static String ENDLESS_2_INITIAL_INTERVAL 	= "Endless2InitialInterval"; 	
  public final static String ENDLESS_2_DECREASE_INTERVAL 	= "Endless2DecreaseInterval";
  public final static String ENDLESS_2_DECREASE_VALUE 		= "Endless2DecreaseValue";
  
  private final static String[] keys = {
    ENDLESS_0_INITIAL_INTERVAL,
    ENDLESS_0_DECREASE_INTERVAL,
    ENDLESS_0_DECREASE_VALUE,
    ENDLESS_1_INITIAL_INTERVAL,
    ENDLESS_1_DECREASE_INTERVAL,
    ENDLESS_1_DECREASE_VALUE,
    ENDLESS_2_INITIAL_INTERVAL,
    ENDLESS_2_DECREASE_INTERVAL,
    ENDLESS_2_DECREASE_VALUE  
  };
  
  public EndlessMode(){
    super(GlobalsXmlReader.GLOBAL_ENDLESS_MODE, keys);	  
  }
	
     
}
