package com.rokejitsx.ui.item;

import com.rokejitsx.data.GameObject;
import com.rokejitsx.ui.building.Checker;
import com.rokejitsx.ui.patient.NumberLineField;

public abstract class Item extends GameObject{  	
  public static final int MEDICINE  		= 0;
  public static final int COFFEE			= 2;
  public static final int DUST				= 3;  
  public static final int REPAIR_TOOL		= 4;
  public static final int PILL_1			= 5;
  public static final int PILL_2			= 6;
  public static final int PILL_3			= 7;
  public static final int PILL_4			= 8;
  public static final int INFOPLATE 		= 9;
  
  private final static String[] imgName = {    
	COMPOSE_PRESCRIPTION,
    COMPOSE_PRESCRIPTION,
    COMPOSE_PRESCRIPTION,
    COMPOSE_PRESCRIPTION,
    COMPOSE_PRESCRIPTION,
    COMPOSE_PRESCRIPTION,
    COMPOSE_PRESCRIPTION,
    COMPOSE_PRESCRIPTION,
    COMPOSE_PRESCRIPTION,
    RECIPIES
  };
  
  private int type;
  private int patientNumber;
  private GameObject owner; 
  private Checker checker;
  
  private NumberLineField queueNumber;
  public Item(int type,int patientNumber) {	
	super(imgName[type]);
	this.type = type;
	this.patientNumber = patientNumber;
	if(type < 9)
	  mainSprite.setCurrentTileIndex(type);	
	if(patientNumber > 0 && type != INFOPLATE){
	  int max = 1;
	  if(patientNumber >= 10)
	    max = 2;
	  queueNumber = new NumberLineField(max);
      queueNumber.setNumber(patientNumber);
      queueNumber.setPosition(getWidth()/2 - queueNumber.getWidth()/2, getHeight()/2 - queueNumber.getHeight()/2);
      attachChild(queueNumber);
	}
	checker = new Checker(1);
	checker.setPosition(getWidth() / 2 - checker.getWidth() / 2 , getHeight() / 2 - checker.getHeight() / 2);
	attachChild(checker);
  } 
  
  public void unShowPatientNumber(){
    if(queueNumber != null)
      queueNumber.setVisible(false);
  }
  
  public boolean isCanCheck(){
    return checker.isCanCheck();	  
  }
  
  public boolean checked(){
    return checker.checked();	  
  }
  
  public void unChecked(){
    checker.unChecked();	  
  }
  
  public void setOwner(GameObject owner){
    this.owner = owner;	  
  }
  
  public GameObject getOwner(){
    return owner;	  
  }
  
  public int getType(){
    return type;	  
  }
  
  public int getPatientNumber(){
    return patientNumber;	  
  }
  
  public abstract Item deepCopy();
  
  public static Item createItemObject(int type, int patientNumber){
    
    switch(type){
      case MEDICINE:
        return new Medicine(patientNumber);	
      case INFOPLATE:
        return new InfoPlate(patientNumber);
      case DUST:
        return new Dust();      
      case REPAIR_TOOL:
        return new RepairTool();
      case COFFEE:
        return new Coffee();
      case PILL_1:
      case PILL_2:
      case PILL_3:
      case PILL_4:
        return new Pill(type, patientNumber);
    }
	return null;  
  }


}
