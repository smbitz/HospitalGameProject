package com.rokejitsx.ui.building.ward;

import android.util.Log;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.AnimationInfo;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.item.Item;
import com.rokejitsx.ui.nurse.Nurse;
import com.rokejitsx.ui.patient.Patient;



public abstract class Ward extends Building{  	
	
  
  
  private double healingEffectiveLevel; 
  private long operationTime,endHealingTime;
  private WardListener listener;  
  
  
  //private int wardType;
  
  
  public Ward(int type, int maxVisitor){
	super(type, maxVisitor);
    /*super(maxPatient);
    this.wardType = type;*/
	
    setHealingEffectiveLevel(5);
     
  }
  
  
  
  
  
  /*public int getWardType(){
    return wardType;	  
  }*/
  
  public void setHealingEffectiveLevel(double healingLevel){
    healingEffectiveLevel = healingLevel;	  
  }
  
  public void setOperationTime(long time){
    operationTime = time;	  
  } 
  
  public void setWardListener(WardListener listener){
    this.listener = listener;	  
  }
  
  
  protected void setHealingAnimationId(int id){
    AnimationInfo animInfo = ResourceManager.getInstance().getAnimationInfo(id);
    setOperationTime((animInfo.getSequenceCount() * animInfo.getEachFrameDuration()));
    setDoWorkAnimationId(id);
  }
  
  
      
  
  public Patient getCurrentPatient(){
	GameObject gameChar = getCharactor(0);
	if(gameChar == null)
	  return null;
    return (Patient)gameChar ;	  
  }
  
 
  
  protected void startHealing(){
	Log.d("Rokejitsx", "StartHealing....");
	setState(STATE_DO_WORK);	
	getCurrentPatient().setPickable(false);
	getCurrentPatient().onHealing();
    onStartHealing();      
    endHealingTime = System.currentTimeMillis() + operationTime;
  }
  
  private void healing(float pSecondsElapsed){
	Patient patient = getCurrentPatient();	
	patient.setHealthLevel(patient.getHealthLevel() + (healingEffectiveLevel  * pSecondsElapsed));	
    onHealing(pSecondsElapsed);	  
    if(System.currentTimeMillis() > endHealingTime)
      finishHealing();
  }
  
  protected void finishHealing(){	
    setState(STATE_IDLE);    
    getCurrentPatient().setPickable(true);
    if(isVisible())
      getCurrentPatient().setVisible(true);
    onfinishHealing();
    if(listener != null)
      listener.onFinishHealing(this, getCurrentPatient());
  }  

  @Override
  public void onReceive(GameCharactor gameChar) {	
    Patient patient = (Patient) gameChar;
    patient.setPickable(false);
    onWardReceivePatient(patient);
    if(!patient.hasRequireItem()){
      startHealing();	
    }
    //return true;
  }

  @Override
  public void onRemove(GameCharactor gameChar) {
	onWardRemovePatient((Patient) gameChar);
	if(isBuildingCanDirty())
	  setState(STATE_DIRTY);
  }  
  
  public abstract void onWardReceivePatient(Patient patient);
  public abstract void onWardRemovePatient(Patient patient);
  public abstract void onStartHealing();
  public abstract void onHealing(float pSecondsElapsed);
  public abstract void onfinishHealing();
  
  
  
  


  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {
	switch(getState()){
	  case STATE_DO_WORK:
	    healing(pSecondsElapsed);
	  break;
	}
	super.onManagedUpdate(pSecondsElapsed);	
  } 	
  
  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {	
    if(gameChar instanceof Nurse){
      Patient patient = getCurrentPatient();
      if(patient == null || !patient.hasRequireItem())
        return;
      Nurse nurse = (Nurse) gameChar;
      Item item = patient.getRequireItem();
      if(nurse.isHasItemInHand(item)){
        nurse.handOut(item);
        patient.nextRequireItem();
        if(!patient.hasRequireItem())
          startHealing();	  
      }      
    }	
  }
  
  
  
}
