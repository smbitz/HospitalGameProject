package com.rokejitsx.ui.item;


public class Medicine extends Item{
  
  public Medicine(int patientNumber) {
    super(MEDICINE, patientNumber);		
    
  }
  
  

  @Override
  public Item deepCopy() {	
	return new Medicine(getPatientNumber());
  }

}
