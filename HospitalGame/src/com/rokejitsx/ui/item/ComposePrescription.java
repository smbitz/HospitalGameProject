package com.rokejitsx.ui.item;

public class ComposePrescription extends Item{

  public ComposePrescription(int patientNumber) {
    super(MEDICINE, patientNumber);		
  }

  @Override
  public Item deepCopy() {	
	return new ComposePrescription(getPatientNumber());
  }

}
