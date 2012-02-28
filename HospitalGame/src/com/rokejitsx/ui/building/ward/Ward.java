package com.rokejitsx.ui.building.ward;

import org.anddev.andengine.audio.sound.Sound;

import android.util.Log;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.audio.SoundPlayerManager;
import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.AnimationInfo;
import com.rokejitsx.data.xml.DataHolder;
import com.rokejitsx.data.xml.global.giactionpoint.GiActionPointBuilding;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.item.Item;
import com.rokejitsx.ui.nurse.Nurse;
import com.rokejitsx.ui.patient.Patient;



public abstract class Ward extends Building{  	
	
  
  
  private double healingEffectiveLevel; 
  private long operationTime,endHealingTime, repairTime;
  private WardListener listener; 
  private int billCost;
  private Sound healingSound;
  private String healingSoundFileName;
  //private int wardType;
  
  
  public Ward(int type, int maxVisitor){
	super(type, maxVisitor);
    /*super(maxPatient);
    this.wardType = type;*/
	
    setHealingEffectiveLevel(5);
    
    
     
  }
  
 /* protected void setHealingSound(String fileName){
	healingSoundFileName = fileName;
    
  }*/
  
  /*private void playHealingSound(){
    if(healingSound != null)
      HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable() {
		
		@Override
		public void run() {
		  healingSound = SoundPlayerManager.getInstance().createSound(healingSoundFileName);
		  healingSound.setLooping(true);	
		  healingSound.play();
			
		}
	});
      
  }*/
  
  @Override
  public void onFloorChanged(int floor) {	
	super.onFloorChanged(floor);	
	Patient pateint = getCurrentPatient();
	if(pateint != null && pateint.isOnHealing())
	  onFloorChangedSetPatientOnHealingVisible(isVisible());
	
	
  }
  
  protected void onFloorChangedSetPatientOnHealingVisible(boolean wardVisible){
    getCurrentPatient().setVisible(wardVisible);  	  
  }
  
  private void stopHealingSound(){
    if(healingSound != null){
      healingSound.stop();      
    }
  }
  
  
  
  protected void initialFromGlobal(String tagName){
    DataHolder dHolder = ResourceManager.getInstance().getGlobalData(tagName);	  
    if(dHolder.get(GiActionPointBuilding.IN_USE_TIME) != null){
      Log.d("RokejitsX", "dHolder.get(GiActionPointBuilding.IN_USE_TIME) = "+dHolder.get(GiActionPointBuilding.IN_USE_TIME));
      setOperationTime((long) (dHolder.getFloat(GiActionPointBuilding.IN_USE_TIME) * 1000));	
    }
    
    if(dHolder.get(GiActionPointBuilding.DAMAGE) != null){
      setHealingEffectiveLevel(dHolder.getDouble(GiActionPointBuilding.DAMAGE));	
    }
    
    if(dHolder.get(GiActionPointBuilding.REPAIR_TIME) != null){
      setRepairTime((long) (dHolder.getFloat(GiActionPointBuilding.REPAIR_TIME) * 1000));	
    }
    
    if(dHolder.get(GiActionPointBuilding.BILL) != null){
      setBillCost((int) dHolder.getFloat(GiActionPointBuilding.BILL));	
    }
  }
  
  private void setBillCost(int cost){
    billCost = cost;  	  
  }
  

  
  private void setRepairTime(long time){
    repairTime = time;
    if(repairTime != 0)
      this.setBuildingCanBroke();
  }
  
  public long getRepairTime(){
    return repairTime;	  
  }





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
  
 
  private float currentHealingTime = -1;
  
  protected float getCurrentHealingTime(){
    return currentHealingTime;	  
  }
  
  protected void startHealing(){	
	setState(STATE_DO_WORK);	
	getCurrentPatient().setPickable(false);
	getCurrentPatient().onHealing();
    onStartHealing();      
    currentHealingTime = 0;
    endHealingTime = System.currentTimeMillis() + operationTime;
    //playHealingSound();
  }
  
  private void healing(float pSecondsElapsed){
	currentHealingTime = currentHealingTime + pSecondsElapsed;
	Patient patient = getCurrentPatient();	
	patient.setHealthLevel(patient.getHealthLevel() + (healingEffectiveLevel  * pSecondsElapsed));	
    onHealing(pSecondsElapsed);	  
    if(System.currentTimeMillis() > endHealingTime)
      finishHealing();
  }
  
  protected void finishHealing(){
	currentHealingTime = -1;
    setState(STATE_IDLE);    
    onfinishHealing();
    Patient patient = getCurrentPatient();
    patient.addBillCost(billCost);
    onGameCharactorCallback(patient);
    if(listener != null)
      listener.onFinishHealing(this, getCurrentPatient());    
    patient.setPickable(true);
    if(isVisible())
      patient.setVisible(true);
    patient.nextHealingRoute();
    stopHealingSound();        
    
  }  

  @Override
  public boolean onReceive(GameCharactor gameChar) {	
    Patient patient = (Patient) gameChar;
    patient.setPickable(false);
    onWardReceivePatient(patient);
    if(!patient.hasRequireItem()){
      startHealing();	
    }else{
      patient.showBubbleBoxItem();	
    }
    return true;
  }
  
  @Override
  protected void setGameChatactorOnReceived(GameCharactor gameChar) {
    setPatientOnReceived((Patient) gameChar);	  	   
	  
  }
  
  protected abstract void setPatientOnReceived(Patient patient);
	  
  

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
      //unChecked();
      Patient patient = getCurrentPatient();
      if(patient == null)
        return;
      
      if(this.getBuildingType() != patient.getNextWardType())
        return;
      
      if(!patient.hasRequireItem())
        return;
      Nurse nurse = (Nurse) gameChar;      
      Item item = patient.getRequireItem();
      if(nurse.isHasItemInHand(item)){
        nurse.handOut(item);
        patient.nextRequireItem();
        if(!patient.hasRequireItem()){
          startHealing();	  
        }
      }      
    }	
  }
  
  
  
}
