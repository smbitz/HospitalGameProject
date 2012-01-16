package com.rokejitsx.ui.patient;

import java.util.Vector;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import android.content.Entity;
import android.util.Log;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.route.PathFinderListener;
import com.rokejitsx.data.xml.PatientInfoReader.PatientHeadInfo;
import com.rokejitsx.data.xml.PatientInfoReader.PatientInfo;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.building.ward.pharmacy.Pharmacy;
import com.rokejitsx.ui.item.Item;
import com.rokejitsx.ui.nurse.Nurse;



public class Patient extends GameCharactor implements PathFinderListener{ 
	 
  private final static String[][] bodyList = {
    {
      PATIENT_0_WALK,
      PATIENT_0_BODY,
      PATIENT_0_CURED
    }
    
  };
  
  private final static float[][][] neckOffset = {
	{// PATIENT_0_WALK
      {40.5f, 0}, 
      {40.5f, 0}, 
      {40.5f, -2}, 
      {40.5f, -2}, 
      {40.5f, 0}, 
      {40.5f, 0}, 
      {40.5f, -2},
      {40.5f, -2},
      {40.5f, 0},
      {40.5f, 0},
      {40.5f, -2},
      {40.5f, -2},
      {40.5f, 0},
      {40.5f, 0},
      {40.5f, -2},
      {40.5f, -2}
	},
	{// PATIENT_0_BODY
      {70.5f, -5}, 
	  {70.5f, -5}, 
	  {70.5f, -5}, 
	  {70.5f, -5}, 
	  {70.5f, -5}, 
	  {70.5f, -5}, 
	  {70.5f, -5},
	  {70.5f, -5},
	  {77.5f, -5},
	  {77.5f, -5},
	  {77.5f, -5},
	  {77.5f, -5}	  
	}
		  
  };
  
  private final static int[] bodyAnimationIdleIds = {	  
    66 //Patient_0	  
  };
    
  
  
  private float[][] neck;
  
  private final static String[] headList = {
    PATIENT_HEAD_0	  
  };
	
  private double healthLevel; // max 100
  private double feverLevel; // max 5  
  private HealthBar healthBar;
  //private ChangeableText queueNumber;
  private NumberLineField queueNumber;
  //private int queue;
  private PatientListener listener;
  private boolean canPick = false;    
  private Vector<HealingRoute> healingRouteList;
  private HealingRoute currentHealingRoute;    
  private boolean onWaiting,inProgress,isFinish,moveout;
  private BubbleBox bubbleBox;
  private PatientInfo patientInfo;
  private PatientHeadInfo headInfo;
  
  private AnimatedSprite headSprite, bodySprite, curedSprite;
  private int patientId;
  
  public Patient(int patientId){     
	super(bodyList[patientId][0]);
	this.patientId = patientId;
	this.healthLevel = 50;
    this.feverLevel = 1;
    neck = neckOffset[0];
    patientInfo = ResourceManager.getInstance().getPatientInfo(patientId);
	headInfo = ResourceManager.getInstance().getPatientHeadInfo(patientInfo.randomHeadId()); 
	/*PointF patientNeckPosition = patientInfo.getLinkPointPosition(patientInfo.NECK_LINK_POINT);
	PointF headNeckPosition = headInfo.getLinkPointPosition(headInfo.NECK_LINK_POINT);*/
	headSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(headList[/*headInfo.getHeadId()*/0]));	
	setSpritePosition(headSprite, 40.5f, 2);
		
    
	
	
    healingRouteList = new Vector<HealingRoute>();
    
    bubbleBox = new BubbleBox();    
    bubbleBox.setVisible(false);
    bubbleBox.setPosition(0, headSprite.getY() - bubbleBox.getHeight());
        
    //setColor(1, 0, 0);
    
    bodySprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(PATIENT_0_BODY));
    curedSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(PATIENT_0_CURED));
    
    setWidth(bodySprite.getBaseWidth());
    setHeight(bodySprite.getBaseHeight());
    
    bodySprite.setVisible(false);
    curedSprite.setVisible(false);
    
    bodySprite.setPosition(getWidth()/2 - bodySprite.getBaseWidth()/2, getHeight() - bodySprite.getHeight());
    curedSprite.setPosition(getWidth()/2 - curedSprite.getBaseWidth()/2, getHeight() - curedSprite.getHeight());    
    
    healthBar = new HealthBar();
    //healthBar.setColor(0, 0, 0, 0);
    healthBar.setHealth(getHealthLevel());
    healthBar.setVisible(false);
     
	    
    queueNumber = new NumberLineField(2);
    queueNumber.setVisible(false);
        
    
    attachChild(bodySprite);
    attachChild(curedSprite);
    attachChild(headSprite);
    attachChild(healthBar);
    attachChild(queueNumber);
    attachChild(bubbleBox);
    setSpeed(patientInfo.getWalkSpeed());
    updateHealthBarPosition();  
    //setColor(1,1,1,1);
  }  
  
  public boolean isContain(float x, float y){
	if(mainSprite.isVisible()){
	  if(mainSprite.contains(x, y))
	    return true;
	}else if(bodySprite.isVisible()){
	  if(bodySprite.contains(x, y))
	    return true;
	}else if(curedSprite.isVisible()){
	  if(curedSprite.contains(x, y))
        return true;
	} 
    return false;	  
  }
  
  
  public void layIn(){
    mainSprite.stopAnimation();
	bodySprite.stopAnimation(7);
    mainSprite.setVisible(false);
    bodySprite.setVisible(true);
    curedSprite.setVisible(false);
	    
	    
    neck = neckOffset[1];
    setAnimation(headSprite, ResourceManager.getInstance().getAnimationInfo(headInfo.getAnimationId(headInfo.IDLE_ID)));	  
  }
  
  public void sit(){
	mainSprite.stopAnimation();
	bodySprite.stopAnimation(6);
    mainSprite.setVisible(false);
    bodySprite.setVisible(true);
    curedSprite.setVisible(false);
    
    
    neck = neckOffset[1];
    setAnimation(headSprite, ResourceManager.getInstance().getAnimationInfo(headInfo.getAnimationId(headInfo.IDLE_ID)));
    
    
  }
  
  public void idle(){
    mainSprite.stopAnimation();
	bodySprite.stopAnimation();
    mainSprite.setVisible(false);
    bodySprite.setVisible(true);
    curedSprite.setVisible(false);
    
    
    neck = neckOffset[1];   
    
    setAnimation(headSprite, ResourceManager.getInstance().getAnimationInfo(headInfo.getAnimationId(PatientHeadInfo.IDLE_ID)));	  
    setAnimation(bodySprite, ResourceManager.getInstance().getAnimationInfo(bodyAnimationIdleIds[patientId]));
  }
  
  @Override
  protected void onSetFace(int face) {
	String body = null, head = null;
    switch (face) {
	  case FACE_DOWN_L:
	    body = PatientInfo.DOWN_L_ID;
	    head = PatientHeadInfo.IDLE_DOWN_L_ID;
	  break;	  
	  case FACE_DOWN_R:
		body = PatientInfo.DOWN_R_ID;
	    head = PatientHeadInfo.IDLE_DOWN_R_ID;
	  break;
	  case FACE_UP_L:
		body = PatientInfo.UP_L_ID;
	    head = PatientHeadInfo.IDLE_UP_L_ID;
	  break;
	  case FACE_UP_R:
	    body = PatientInfo.UP_R_ID;
	    head = PatientHeadInfo.IDLE_UP_R_ID;
	  break;
	}
    
    
    setAnimation(headSprite, ResourceManager.getInstance().getAnimationInfo(headInfo.getAnimationId(head)));
    setAnimation(mainSprite, ResourceManager.getInstance().getAnimationInfo(patientInfo.getAnimationId(body)));
  	
  }
  
  
  
  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
	attachChild(mainSprite);	
	mainSprite.setPosition(0, 0);
	Rectangle body = new Rectangle(0, 0, 30, 50);
	body.setColor(1, 0, 0);
    
	/*AnimationInfo animInfo = ResourceManager.getInstance().getAnimationInfo(patientInfo.getAnimationId(patientInfo.DOWN_L_ID));
	
	mainSprite.setFlippedHorizontal(animInfo.isFlip());
	mainSprite.animate(animInfo.getFPSList(), animInfo.getSequence(), animInfo.doLoop());*/
	return null;
  } 
  
  /*public void showBubble(String text){
    if(!bubbleBox.isVisible())
      bubbleBox.setVisible(true);
    bubbleBox.setText(text);
  }*/
  
  public void hideBubbleText(){
   bubbleBox.hideitem();
   bubbleBox.setVisible(false);	  
  }
  
  public boolean isInProgress(){
    return inProgress;	  
  }
  
  private void onWaiting(){
    onWaiting = true;
    healthBar.setVisible(true);
  }
  
  private void finishHealing(){
	isFinish = true;
    moveOut();	  
  }
  
  public boolean isFinishHealing(){
    return isFinish;	  
  }
  
  public void onHealing(){
    onWaiting = false;	  
  }
  
  

  public boolean isMoveout(){
    return moveout;	  
  }
  
  public void onAmbulance(){
    onWaiting = false;
    setVisible(false);    
  }  
  
  private void moveOut(){
	bodySprite.stopAnimation();
	curedSprite.stopAnimation();
	mainSprite.setVisible(true);
	bodySprite.setVisible(false);
	curedSprite.setVisible(false);
	
	neck = neckOffset[0];
	
	
	
	hideBubbleText();
	onWaiting = false;
	moveout = true;
    if(listener != null)
      listener.onPatientMoveOut(this);
  } 

  public HealingRoute addWardHealingRoute(int wardType){
	HealingRoute healingRoute = new HealingRoute(wardType);
	healingRouteList.add(healingRoute);
    return healingRoute;
  }  
  
  public void nextHealingRoute(){
	inProgress = true;	
	if(healingRouteList.size() == 0){
	  finishHealing();
	  return;    
	}
	onWaiting();	
	setPickable(true);
    currentHealingRoute = healingRouteList.elementAt(0);
    bubbleBox.showMachine(currentHealingRoute.getWardType(), true);
    /*switch(currentHealingRoute.getWardType()){
      case Building.TRIAGE:
        bubbleBox.showMachine(Building.TRIAGE, true);
      break;
      case Building.BED:
    	bubbleBox.showMachine(Building.BED, true);
      break;
      case Building.QUICKTREAT:
    	bubbleBox.showMachine(Building.QUICKTREAT, true);
      break;
      
    }*/
    healingRouteList.remove(0);    
    bubbleBox.setVisible(true);
  }  
  
  public boolean hasRequireItem(){	
    if(currentHealingRoute == null || currentHealingRoute.getCurrentItem() == null){
      hideBubbleText();
      return false;
    }
    bubbleBox.showItem(currentHealingRoute.getCurrentItem().deepCopy());
    return true;
  }
  
  public void nextRequireItem(){
    currentHealingRoute.nextItem();	  
  }
  
  public Item getRequireItem(){
    return currentHealingRoute.getCurrentItem();	  
  }
  
  public int getNextWardType(){
    return currentHealingRoute.getWardType();	  
  }
  
  public void setQueue(int queue){    
    queueNumber.setNumber(queue);
    queueNumber.setVisible(true);
    updateQueuePosition();
  }
  
  public int getQueue(){
    return queueNumber.getNumber();	   
  }
  
  private void updateQueuePosition(){
    queueNumber.setPosition(healthBar.getX() - queueNumber.getWidth(), (healthBar.getY() + healthBar.getHeight()/2) - queueNumber.getHeight()/2);	  
  }
  
  private void updateHealthBarPosition(){
    healthBar.setPosition(getWidth()/2 - healthBar.getWidth()/2, headSprite.getY() - healthBar.getHeight());
    updateQueuePosition();
  }
  
   
  
  /*public void swapWard(Building newBuilding){
    if(getCurrentBuilding() != null)
      getCurrentBuilding().removeCharactor(this);
    newBuilding.receiveCharator(this);
  }*/
  
  public void callBack(){
    getCurrentBuilding().onPatientCallback(this);	  
  }
  
  public void setPickable(boolean canPick){
    this.canPick = canPick;	  
  }
  
  public boolean canPick(){
    return canPick;	  
  }
  
  public void onPicked(float x, float y){	
    bodySprite.stopAnimation(8);
	curedSprite.stopAnimation();
	mainSprite.setVisible(false);
	bodySprite.setVisible(true);
	curedSprite.setVisible(false);		
	neck = neckOffset[1];
	
	setAnimation(headSprite, ResourceManager.getInstance().getAnimationInfo(headInfo.getAnimationId(headInfo.GRAB_ID)));
	oldDragX = x;
	oldDragY = y;
	onWaiting = false;
  }
  
  public void onUnPicked(){
    force = 0;	  
    onWaiting = true;
    bodySprite.setFlippedHorizontal(false);
  }
  
  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {	
	if(onWaiting){
	  setHealthLevel(getHealthLevel() - (feverLevel * pSecondsElapsed));	
	  if(getHealthLevel() <= 0){
	    setHealthLevel(0);
	    healthBar.setVisible(false);
	    queueNumber.setVisible(false);
	    setPickable(false);
	    Item item = getRequireItem();
	    if(item != null){
	      GameObject owner = item.getOwner();
	      if(owner instanceof Pharmacy){
	        Pharmacy infoWard = (Pharmacy) owner;
	        infoWard.removeItemFromQueue(item);
	      
	      }else if(owner instanceof Nurse){
	        Nurse nurse = (Nurse) owner;
	        nurse.handOut(item);
	      
	      }
	      item.setVisible(false);
	    }
	    moveOut();
	  }
	}
	super.onManagedUpdate(pSecondsElapsed);
	
	//final float[] position = this.convertLocalToSceneCoordinates(40.5f - headSprite.getWidth()/2, 2 - headSprite);
	AnimatedSprite sprite;
	if(mainSprite.isVisible()){
	  sprite = mainSprite;	
	}else if(bodySprite.isVisible()){
	  sprite = bodySprite;	
    }else{
      sprite = curedSprite;	
    }
	float[] offset = neck[sprite.getCurrentTileIndex()];
	setSpritePosition(headSprite, offset[0], offset[1]);
	if(force > 0){
	  bodySprite.setFlippedHorizontal(dragFlip);	
	  bodySprite.setCurrentTileIndex((force % 100) + 8);
	  force -= 10;
	}
  }
  
  
  
  
  private float oldDragX, oldDragY;
  private int force = 0;
  private boolean dragFlip = false;
  public void onDraged(float x, float y){
    setPosition(x, y);	
    if(Math.abs(x - oldDragX) > 20 ||
       Math.abs(y - oldDragY) > 20){
      force += 5;
      if(force > 300)
        force = 300;
      if(x < oldDragX){
        dragFlip = true;	  
      }
    }
  }
  
  public void setPatientListener(PatientListener listener){
    this.listener = listener;  	  
  }
  
  public void setHealthLevel(double level){	
	if(level > 100)
      level = 100;	
    this.healthLevel = level;    
    /*healthBar.setWidth((float) (level / 2));    
    healthBar.setPosition(getWidth()/2 - healthBar.getWidth()/2, -20);*/
    healthBar.setHealth(healthLevel);
    updateHealthBarPosition();
    /*if(queueNumber.isVisible())
      updateQueuePosition();*/
  }
  
  public double getHealthLevel(){
    return healthLevel;	  
  } 
  
  public class HealingRoute{
    private int wardType;
    private Vector<Item> itemList;    
    public HealingRoute(int wardType){
      this.wardType = wardType;	
      itemList = new Vector<Item>();
    }	  
    
    public int getWardType(){
      return wardType;      	
    }
    
    public HealingRoute addItem(Item item){
      itemList.add(item);
      return this;
    }
    
    public boolean hasMoreItem(){
      return itemList.size() > 0; 	
    }
    
    public Item nextItem(){
      if(itemList.size() == 0)
        return null;
      Item item = itemList.elementAt(0);
      itemList.remove(0);
      return item;
    }
    
    public Item getCurrentItem(){
      if(itemList.size() == 0)
        return null;
      return itemList.elementAt(0);     
    }
    
	  
  }
  
  class BubbleBox extends Rectangle{
    
	private AnimatedSprite balloonField, machineField;
    public BubbleBox(){
      super(0, 0, 0, 0);
      balloonField = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(BALLOON));
      
      machineField = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MACHINES));     
      
      attachChild(balloonField);
      attachChild(machineField);     
      
      setWidth(balloonField.getBaseWidth());
      setHeight(balloonField.getBaseHeight());
      balloonField.setFlippedHorizontal(true);
      setColor(0, 0, 0, 0);
    }	   
    
    public void showMachine(int buildingType, boolean hasInHospital){
      int frame = 0;
      switch(buildingType){
        case Building.PHYSIOTHERAPY:
          frame = 1;
        break;
        case Building.OPHTHALMOLOGY:
          frame = 3;
        break;
        case Building.OPERATION:
          frame = 5;
        break;
        case Building.CHEMOTHERAPY:
          frame = 7;
        break;
        case Building.TAC:
          frame = 9;
        break;
        case Building.PSYCHIATRY:
          frame = 11;
        break;
        case Building.ULTRASCAN:
          frame = 13;
        break;
        case Building.BABY_SCAN:
          frame = 15;
        break;
        case Building.CARDIOLOGY:
          frame = 17;
        break;
        case Building.DENTIST:
          frame = 19;
        break;
        case Building.XRAY:
          frame = 21;
        break;
        case Building.BED:
          frame = 23;
        break;
        case Building.QUICKTREAT:
          frame = 31;
        break;
        case Building.TRIAGE:
          frame = 32;
        break;
        
        
      }    	
      if(!hasInHospital)
        frame -= 1;  
      /*if(showItem != null)
        showItem.setVisible(false);*/
      hideitem();
      machineField.setVisible(true);
      machineField.setCurrentTileIndex(frame);
    }
    
    private Item showItem;
    
    public void hideitem(){
      for(int i = 0;i < getChildCount();i++){
    
        IEntity child = getChild(i);
        Log.d("RokejitsX", "Bubble removeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee = "+ child.getClass());
        if(!child.equals(machineField) && !child.equals(balloonField)){
          Log.d("RokejitsX", "Bubble removeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee2 = "+ child.getClass());
          detachChild(child);
        }
      }   
        //detachChild(showItem);
        //HospitalGameActivity.getGameActivity().sendDeattachChild(this, showItem);
    }
    
    public void showItem(Item item){
      showItem = item;
      attachChild(showItem);
      machineField.setVisible(false);
    }
    
    /*public void setText(String text){
      this.text.setText(text);	
    }*/    
    
  }
  
  class HealthBar extends Rectangle{
    	    
    
    public HealthBar(){
      super(0, 0, 0, 0);      
      AnimatedSprite[] hearts = new AnimatedSprite[5];
      int x = 0;
      float width = -1, height = -1;
      for(int i = 0;i < hearts.length;i++){    	  
        AnimatedSprite heart = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(HEART));
        heart.setCurrentTileIndex(2);
        if(width == -1)
          width = heart.getBaseWidth();
        if(height == -1)
          height = heart.getBaseHeight();
        heart.setPosition(x, 0);
        attachChild(heart);
        x += heart.getWidth();       
      }
      
      setColor(0, 0, 0, 0);
      
      setWidth(width * 5);
      setHeight(height);
    }
    
    public void setHealth(double heal){
      float width = (float) (getWidth() * heal / 100);      
      setWidth(width);      
    }
  }




  
}
