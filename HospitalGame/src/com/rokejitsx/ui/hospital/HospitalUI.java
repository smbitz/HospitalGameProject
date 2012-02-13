package com.rokejitsx.ui.hospital;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.input.touch.TouchEvent;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.ui.building.elevator.ElevatorFloorSelector;
import com.rokejitsx.ui.building.elevator.ElevatorFloorSelector.ElevatorSelectorListener;
import com.rokejitsx.ui.hospital.HospitalGamePlay.HospitalListener;
import com.rokejitsx.ui.hospital.HospitalFloorSelector.FloorSelectListener;
import com.rokejitsx.ui.hospital.HospitalTimer.HospitalTimerListener;
import com.rokejitsx.ui.item.Item;
import com.rokejitsx.ui.patient.Patient;

public class HospitalUI extends Entity implements HospitalListener{
  private HospitalStat hospitalStat;	
  private HospitalTimer timer;
  private HospitalFloorSelector hospitalFloorSelector;
  private ElevatorFloorSelector elevatorFloorSelector;
  private Vector<PatientDoughnut> patientDoughnutList;
  private HospitalUIListener listener;
  private ItemDoughnut[] itemDoughnutList;
  public HospitalUI(int maxFloor, HospitalUIListener listener){
	this.listener = listener;
	patientDoughnutList = new Vector<PatientDoughnut>();
	itemDoughnutList = new ItemDoughnut[5];
	
	for(int i = 0;i < itemDoughnutList.length;i++){
	  itemDoughnutList[i] = new ItemDoughnut();	
	}
	
	ItemDoughnut itemDo = itemDoughnutList[0];
	float x = 450 - (itemDo.getWidth() * 2 + itemDo.getWidth()/2 + 20);
	float y = 600 - itemDo.getHeight();
	
	for(int i = 0;i < itemDoughnutList.length;i++){
	  itemDoughnutList[i].setPosition(x, y);
	  x += itemDoughnutList[i].getWidth() + 10;
	  attachChild(itemDoughnutList[i]);
    }
	
	
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
	if(!elevatorFloorSelector.isVisible())
      if(hospitalFloorSelector.onSceneTouchEvent(pScene, pSceneTouchEvent))
        return true;
    if(elevatorFloorSelector.onSceneTouchEvent(pScene, pSceneTouchEvent))
  	  return true;
    int action = pSceneTouchEvent.getAction();
    float touchX = pSceneTouchEvent.getX();
	float touchY = pSceneTouchEvent.getY();
	if(action == TouchEvent.ACTION_DOWN){
      for(int i = 0;i < itemDoughnutList.length;i++){
        ItemDoughnut itemDo = itemDoughnutList[i];      
        if(itemDo.contains(touchX, touchY) && itemDo.containItem()){
          if(listener.onItemSelected(i))
            itemDo.check();
          return true;
        }
      }
	}
    
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
  public void onPatientFinishHealing(Patient patient) {
	hospitalStat.increaseMoney(patient.getBillCost());
	hospitalStat.increaseThreatedPatient(1);
	
  }

  @Override
  public void addPatientDoughnut(Patient patient) {
	PatientDoughnut doughNut = new PatientDoughnut(patient);
	doughNut.setPosition(0, 600);
	patientDoughnutList.add(doughNut);	
	attachChild(doughNut);
	updateDoughnutPosition();
	
  }

  @Override
  public void removePatientDoughnut(Patient patient) {
    Enumeration<PatientDoughnut> e = patientDoughnutList.elements();
    PatientDoughnut doughnut = null;
    while(e.hasMoreElements()){
      PatientDoughnut pDo = e.nextElement();
      if(pDo.getCurrentPatient().equals(patient)){
        doughnut = pDo;
        break;
      }
    		  
    }    
    patientDoughnutList.remove(doughnut);
    detachChild(doughnut);
    updateDoughnutPosition();
	
  }
  
  private void updateDoughnutPosition(){
    float x = 0;
    float y = hospitalStat.getX() + hospitalStat.getHeight();
    Enumeration<PatientDoughnut> e = patientDoughnutList.elements();
    while(e.hasMoreElements()){
      PatientDoughnut pDo = e.nextElement();
      pDo.moveTo(x, y);
      y += pDo.getHeight();
    }  
  }

  @Override
  public void addItemOndesk(Item item, int index) {
    itemDoughnutList[index].setItem(item);	
	
  }

  @Override
  public void removeItemOndesk(Item item, int index) {
    itemDoughnutList[index].clear();	
	
  }

  @Override
  public void onItemOnDeskUnCheck(int index) {
    itemDoughnutList[index].unCheck();
	
  }
	
}
