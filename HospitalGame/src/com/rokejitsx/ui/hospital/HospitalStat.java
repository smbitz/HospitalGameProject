package com.rokejitsx.ui.hospital;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import android.util.Log;

import com.rokejitsx.data.resource.ImageResource;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.patient.NumberLineField;

public class HospitalStat extends Rectangle implements ImageResource{
  private NumberLineField moneyField,threatedPatientField, goalPatientField;	
  private int money, threatedPatient,goalPatient, expertPateint;	
  private AnimatedSprite bgSprite;
  private AnimatedSprite barSprite;
  
  public HospitalStat(){
	super(0, 0, 0, 0);
	bgSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(INTERFACE_METER_EN));
	barSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(INTERFACE_METER_EN)); 
	barSprite.setCurrentTileIndex(2);
	
	attachChild(bgSprite);
	attachChild(barSprite);
	
	
    moneyField = new NumberLineField(7);
    threatedPatientField = new NumberLineField(3);
    goalPatientField = new NumberLineField(3);    
    moneyField.setPosition(barSprite.getBaseWidth() - moneyField.getWidth() - 18, 20);
    threatedPatientField.setPosition(132, 71);
    goalPatientField.setPosition(207, 71);
    
    
    
    
    attachChild(moneyField);
    attachChild(threatedPatientField);
    attachChild(goalPatientField);
    
    setWidth(barSprite.getBaseWidth());
    setHeight(barSprite.getBaseHeight());
    setAlpha(0);
  }
  
  public void reset(){
    setMoney(0);
    setThreatedPatient(0);
    setGoalPatient(goalPatient);
  }
  
  public void setMoney(int money){
    this.money = money;
    moneyField.setNumber(money);
  }
  
  public void increaseMoney(int value){
    setMoney(money + value); 	  
  }
  
  
  public void setThreatedPatient(int num){
    this.threatedPatient = num;	  
    threatedPatientField.setNumber(threatedPatient);
    if(num == goalPatient){
      //goalPatientField.setLabel("Expert :");
      bgSprite.setCurrentTileIndex(1);
      goalPatientField.setNumber(expertPateint);
    }
  }
	  
  public void increaseThreatedPatient(int value){
    setThreatedPatient(threatedPatient + value); 	  
  }
  
  public void setGoalPatient(int goal){
    goalPatient = goal;	  
    bgSprite.setCurrentTileIndex(0);
    goalPatientField.setNumber(goal);
  }
  
  public void setExpertPatient(int expert){
    expertPateint = expert;	  
  }
  

  
  /*class Stat extends Rectangle{
    private ChangeableText valueEntity;
    private ChangeableText labelEntity;
    
    public Stat(String label){
      super(0,0,150,50);
      setColor(0, 1, 0);
      labelEntity = new ChangeableText(0, 20, GameFonts.getInstance().getFont(), label, 10);
      
      valueEntity = new ChangeableText(0, 20, GameFonts.getInstance().getFont(), "0", 10);
      attachChild(labelEntity);
      attachChild(valueEntity);
    }
    
    public void setLabel(String label){
      labelEntity.setText(label);      
    }
    
    public void setValue(int value){
      valueEntity.setText(""+value);	
      valueEntity.setPosition(getWidth() - valueEntity.getWidth(), 20);
    }
    
	  
  }*/
  
}


