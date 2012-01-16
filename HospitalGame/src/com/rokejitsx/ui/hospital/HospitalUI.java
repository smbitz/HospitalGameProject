package com.rokejitsx.ui.hospital;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.input.touch.TouchEvent;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.ui.building.elevator.ElevatorFloorSelector;
import com.rokejitsx.ui.building.elevator.ElevatorFloorSelector.ElevatorSelectorListener;
import com.rokejitsx.ui.hospital.Hospital.HospitalListener;
import com.rokejitsx.ui.hospital.HospitalFloorSelector.FloorSelectListener;
import com.rokejitsx.ui.hospital.HospitalTimer.HospitalTimerListener;

public class HospitalUI extends Entity implements HospitalListener{
  private HospitalStat hospitalStat;	
  private HospitalTimer timer;
  private HospitalFloorSelector hospitalFloorSelector;
  private ElevatorFloorSelector elevatorFloorSelector;
  public HospitalUI(int maxFloor){
	int cameraWidth = HospitalGameActivity.getGameActivity().getCameraWidth();
	int cameraHeight = HospitalGameActivity.getGameActivity().getCameraHeight();
	
	hospitalStat = new HospitalStat();	
	hospitalStat.setThreatedPatient(0);
    timer = new HospitalTimer();	
    attachChild(timer);
    timer.setPosition(cameraWidth - timer.getWidth(), 0);
    
    hospitalFloorSelector = new HospitalFloorSelector(maxFloor);	
    hospitalFloorSelector.setPosition(cameraWidth - hospitalFloorSelector.getWidth() - 15, cameraHeight - hospitalFloorSelector.getHeight() - 15);
    
    this.elevatorFloorSelector = new ElevatorFloorSelector(maxFloor);
    onHideElevetorSelector();
    if(maxFloor > 1)
      attachChild(hospitalFloorSelector);
    attachChild(elevatorFloorSelector);
    attachChild(hospitalStat);
  }	
  
  public void setMoney(int money){
    hospitalStat.setMoney(money);	  
  }
  
  public void setGoalPatient(int num){
	hospitalStat.setGoalPatient(num);  
  }
  
  public void setExpertPatient(int num){
	hospitalStat.setExpertPatient(num);	  
  }

  
  public void reset(){
	timer.stop();
	timer.reset();
    hospitalStat.setMoney(0);
    hospitalStat.setThreatedPatient(0);
    hospitalStat.setGoalPatient(10);    
  }
  
  public void setTime(float seconds){
    timer.setTime(seconds);	  
  }
  
  public void startTimer(){
    timer.start();	  
  }
  
  public void pauseTimer(){
    timer.pause();	  
  }
  
  
  
  public void setHospitalTimerListener(HospitalTimerListener listener){
    timer.setHospitalTimerListener(listener);	  
  }
  
  public void setHospitalFloorListener(FloorSelectListener listener){
    hospitalFloorSelector.setFloorSelectListener(listener);	  
  }
  
  public void setElevetorSelectorListener(ElevatorSelectorListener listener){
    elevatorFloorSelector.setElevatorFloorSelectorListener(listener);	  
  }
  
  public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
    if(hospitalFloorSelector.onSceneTouchEvent(pScene, pSceneTouchEvent))
      return true;
    if(elevatorFloorSelector.onSceneTouchEvent(pScene, pSceneTouchEvent))
  	  return true;
    return false;
  }

  @Override
  public void onShowElevetorSelector(float x, float y) {
	elevatorFloorSelector.setVisible(true);	
	elevatorFloorSelector.setPosition(x - elevatorFloorSelector.getWidth()/2, y);
  }

  @Override
  public void onHideElevetorSelector() {
	elevatorFloorSelector.setVisible(false);	
  }

  @Override
  public void onPatientFinishHealing() {
	hospitalStat.increaseMoney(100);
	hospitalStat.increaseThreatedPatient(1);
	
  }
	
}
