package com.rokejitsx.ui.item;

import com.rokejitsx.data.GameObject;

public abstract class Item extends GameObject{  	
  public static final int MEDICINE  = 0;
  public static final int COFFEE	= 2;
  public static final int DUST		= 3;  
  public static final int INFOPLATE = 9;
  
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
  
  public Item(int type,int patientNumber) {	
	super(imgName[type]);
	this.type = type;
	this.patientNumber = patientNumber;
	if(type < 9)
	  mainSprite.setCurrentTileIndex(type);
	setWidth(mainSprite.getBaseWidth());
	setHeight(mainSprite.getBaseHeight());
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
    }
	return null;  
  }


}
