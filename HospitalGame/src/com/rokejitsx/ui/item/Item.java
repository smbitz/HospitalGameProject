package com.rokejitsx.ui.item;

import com.rokejitsx.data.GameObject;
import com.rokejitsx.ui.building.Checker;

public abstract class Item extends GameObject{  	
  public static final int MEDICINE  		= 0;
  public static final int COFFEE			= 2;
  public static final int DUST				= 3;  
  public static final int REPAIR_TOOL		= 4;
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
  
  
  public Item(int type,int patientNumber) {	
	super(imgName[type]);
	this.type = type;
	this.patientNumber = patientNumber;
	if(type < 9)
	  mainSprite.setCurrentTileIndex(type);
	setWidth(mainSprite.getBaseWidth());
	setHeight(mainSprite.getBaseHeight());
	checker = new Checker(1);
	checker.setPosition(getWidth() / 2 - checker.getWidth() / 2 , getHeight() / 2 - checker.getHeight() / 2);
	attachChild(checker);
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
    }
	return null;  
  }


}
