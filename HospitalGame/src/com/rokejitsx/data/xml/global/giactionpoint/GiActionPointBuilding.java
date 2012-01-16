package com.rokejitsx.data.xml.global.giactionpoint;

import com.rokejitsx.data.xml.DataHolder;

public class GiActionPointBuilding extends DataHolder{

  public final static String IN_USE_TIME 			 = "InUseTime"; 
  public final static String DAMAGE	   				 = "Damage";
  public final static String BILL		 			 = "Bill";
  public final static String PRICE		 			 = "Price";
  public final static String ENERGY 				 = "Energy";
  public final static String SALARY  				 = "Salary";
  public final static String MAINTENANCE 			 = "Maintenance";
  public final static String MIN_HOSPITAL_LEVEL		 = "MinHospitalLevel";  
  public final static String REPAIR_TIME			 = "RepairTime";
  
  public GiActionPointBuilding(String tagName, String[] keys){
    super(tagName, keys);	  
  }
  
  public float getInUseTime(){
    return getFloat(IN_USE_TIME);	  
  }
  
  public float getDamage(){
    return getFloat(DAMAGE);	  
  }
  
  public float getBill(){
    return getFloat(BILL);	  
  }
  
  public float getPrice(){
    return getFloat(PRICE);	  
  }
  
  public float getEnergy(){
    return getFloat(ENERGY);	  
  }
  
  public float getSalary(){
    return getFloat(SALARY);	  
  }
  
  public float getMaintenance(){
    return getFloat(MAINTENANCE);	  
  }
  
  public float getMinHospitalLevel(){
    return getFloat(MIN_HOSPITAL_LEVEL);	  
  }
  
  public float getRepairTime(){
    return getFloat(REPAIR_TIME);	  
  }  
}
