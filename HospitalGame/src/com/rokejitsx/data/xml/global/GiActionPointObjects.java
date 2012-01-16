package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;



public class GiActionPointObjects extends DataHolder{
  public final static String PRICE 				= "Price";	
  public final static String MIN_HOSPITAL_LEVEL = "MinHospitalLevel";
  public final static String DAMAGE 			= "Damage";
  public final static String ENERGY 			= "Energy";

  private final static String[] keys = {
    PRICE,
    MIN_HOSPITAL_LEVEL,
    DAMAGE,
    ENERGY
  };
  
  public GiActionPointObjects(String tagName){
    super(tagName, keys);	  
  }
  
  
  public float getPrice(){
    return getFloat(PRICE);	  
  }
  
  public String getMinHospitalLevel(){
    return getString(MIN_HOSPITAL_LEVEL);	  
  }
  
  public float getDamage(){
    return getFloat(DAMAGE);	  
  }
  
  public float getEnergy(){
    return getFloat(ENERGY);	  
  }
  
}
