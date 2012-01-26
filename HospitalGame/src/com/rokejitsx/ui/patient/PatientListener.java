package com.rokejitsx.ui.patient;


public interface PatientListener{
  public void onPatientMoveOut(Patient patient);	
  public void onPatientFinishHealing(Patient patient);
  public void onPatientRequestItem(Patient patient);
}