package com.rokejitsx.ui.patient;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.SequenceModifier.ISubSequenceModifierListener;

import android.util.Log;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.route.PathFinderListener;
import com.rokejitsx.data.xml.AnimationInfo;
import com.rokejitsx.data.xml.PatientInfoReader.PatientHeadInfo;
import com.rokejitsx.data.xml.PatientInfoReader.PatientInfo;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.building.ward.pharmacy.Pharmacy;
import com.rokejitsx.ui.item.Item;
import com.rokejitsx.ui.nurse.Nurse;



public class Patient extends GameCharactor implements PathFinderListener, IAnimationListener{ 
	 
  private final static String[][] bodyList = {
    {
      PATIENT_0_WALK,
      PATIENT_0_BODY,
      PATIENT_0_CURED
    }, 
    {
      PATIENT_1_WALK,
      PATIENT_1_BODY,
      PATIENT_1_CURED	
    }, 
    {
      PATIENT_2_WALK,
      PATIENT_2_BODY,
      PATIENT_2_CURED	
    }, 
    {
      PATIENT_3_WALK,
      PATIENT_3_BODY,
      PATIENT_3_CURED	
    }, 
    {
      PATIENT_4_WALK,
      PATIENT_4_BODY,
      PATIENT_4_CURED	
    }, 
    null, 
    null, 
    null, 
    {
      PATIENT_8_WALK,
      PATIENT_8_BODY,
      PATIENT_8_CURED	
    }, 
    { 
      PATIENT_9_WALK,
      PATIENT_9_BODY,
      PATIENT_9_CURED	
    }, 
    {
      PATIENT_10_WALK,
      PATIENT_10_BODY,
      PATIENT_10_CURED	
    }
    
    
  };
  
  /*private final static float[][][] neckOffset = {
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
		  
  };*/
  
  /*private final static int[] bodyAnimationIdleIds = {	  
    66 //Patient_0	  
  };*/
    
  
  
  //private float[][] neck;
  
  public final static String[] headList = {
    PATIENT_HEAD_0,	  
    PATIENT_HEAD_1,
    PATIENT_HEAD_2,
    PATIENT_HEAD_3,
    PATIENT_HEAD_4,
    PATIENT_HEAD_5,
    PATIENT_HEAD_6,
    PATIENT_HEAD_7,
    PATIENT_HEAD_8,
    null,
    PATIENT_HEAD_10,
    PATIENT_HEAD_11,
    PATIENT_HEAD_12,
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
  private boolean onWaiting, onHealing,inProgress,isFinish,moveout, onDrag;
  private BubbleBox bubbleBox;
  private PatientInfo patientInfo;
  private PatientHeadInfo headInfo;
  
  private AnimatedSprite headSprite, bodySprite, curedSprite;
  //private Hashtable<String, float[]> frameLink;
  private int patientId;
  
  private AnimationInfo headSpriteAnimationInfo, mainSpriteAnimationInfo, curedSpriteAnimationInfo,dragAnimationInfo;
  private SequenceEntityModifier bubbleFadeIn, bubbleFadeOut;
  private float bubbleTime;
  
  private boolean isShowFloorNumberInBubbleBox = false;
  private Rectangle patientInterface;
  
  
  public Patient(int patientId){     
	super(bodyList[patientId][0]);	
	
	this.patientId = patientId;
	this.healthLevel = 25;
    this.feverLevel = 1;    
    patientInfo = ResourceManager.getInstance().getPatientInfo(patientId);   
	headInfo = ResourceManager.getInstance().getPatientHeadInfo(patientInfo.randomHeadId());	
	dragAnimationInfo = ResourceManager.getInstance().getAnimationInfo(patientInfo.getAnimationId(PatientInfo.GRAB_ID));
	
	headSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(headList[headInfo.getHeadId()]));	
	setSpritePosition(headSprite, 40.5f, 2);	
	
	
    healingRouteList = new Vector<HealingRoute>();
    
    bubbleBox = new BubbleBox();    
    bubbleBox.setVisible(false);
    
        
    //setColor(1, 0, 0);
    
    bodySprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(bodyList[patientId][1]));
    curedSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(bodyList[patientId][2]));
    
    setWidth(bodySprite.getBaseWidth());
    setHeight(bodySprite.getBaseHeight());
    
    bodySprite.setVisible(false);
    curedSprite.setVisible(false);
    mainSprite.setVisible(false);
    
    bodySprite.setPosition(getWidth()/2 - bodySprite.getBaseWidth()/2, getHeight() - bodySprite.getHeight());
    curedSprite.setPosition(getWidth()/2 - curedSprite.getBaseWidth()/2, getHeight() - curedSprite.getHeight());    
    mainSprite.setPosition(getWidth()/2 - mainSprite.getBaseWidth()/2, getHeight() - mainSprite.getHeight());
    healthBar = new HealthBar();
    //healthBar.setColor(0, 0, 0, 0);
    healthBar.setHealth(getHealthLevel());
    healthBar.setVisible(false);
     
	    
    queueNumber = new NumberLineField(2);
    queueNumber.setVisible(false);
    
    patientInterface = new Rectangle(0, 0, bubbleBox.getBaseWidth(), bubbleBox.getBaseHeight());
    patientInterface.setAlpha(0);
    patientInterface.attachChild(queueNumber);
    patientInterface.attachChild(healthBar);
    patientInterface.attachChild(bubbleBox);
        
    attachChild(mainSprite);	
    attachChild(bodySprite);
    attachChild(curedSprite);
    attachChild(headSprite);
    attachChild(patientInterface);
    /*attachChild(queueNumber);
    attachChild(bubbleBox);*/
    setSpeed(patientInfo.getWalkSpeed());
    updateHealthBarPosition();  
    //setColor(1,1,1,1);
    idle(false);
  }  
  
  public int getHeadId(){
    return headInfo.getHeadId();	  
  }
  
  public PatientHeadInfo getHeadInfo(){
    return headInfo;	  
  }
  
  
  
  
  
  public void setShowFloorNumberInBubbleBox(boolean show){
    isShowFloorNumberInBubbleBox = show;	  
  }
  
  public boolean isContain(float x, float y){
	if(getCurrentBuilding() != null){
	  x = x - getCurrentBuilding().getX();
	  y = y - getCurrentBuilding().getY();
	}
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
  
  
  public void layIn(boolean flip){
    mainSprite.stopAnimation();
	bodySprite.stopAnimation();
    mainSprite.setVisible(false);
    bodySprite.setVisible(true);
    curedSprite.setVisible(false);
    
    headSpriteAnimationInfo = ResourceManager.getInstance().getAnimationInfo(headInfo.getAnimationId(headInfo.IDLE_ID)); 
    setAnimation(headSprite, headSpriteAnimationInfo);
    setAnimation(bodySprite, ResourceManager.getInstance().getAnimationInfo(patientInfo.getAnimationId(PatientInfo.PRONE_ID)));
    
    bodySprite.setFlippedHorizontal(flip);    
    headSprite.setFlippedHorizontal(flip);
    
  }
  
  public void sit(boolean flip){	
	mainSprite.stopAnimation();
	bodySprite.stopAnimation(6);
    mainSprite.setVisible(false);
    bodySprite.setVisible(true);
    curedSprite.setVisible(false);   
    
    
    //neck = neckOffset[1];
    headSpriteAnimationInfo = ResourceManager.getInstance().getAnimationInfo(headInfo.getAnimationId(headInfo.IDLE_ID));    
    setAnimation(headSprite, headSpriteAnimationInfo);
    setAnimation(bodySprite, ResourceManager.getInstance().getAnimationInfo(patientInfo.getAnimationId(PatientInfo.SIT_ID)));    
    bodySprite.setFlippedHorizontal(flip);    
    headSprite.setFlippedHorizontal(flip);
    
    
  }
  
  public void surprice(boolean flip){
    mainSprite.stopAnimation();
	bodySprite.stopAnimation();
    mainSprite.setVisible(false);
    bodySprite.setVisible(true);
    curedSprite.setVisible(false);
    
    headSprite.setFlippedHorizontal(false);
    bodySprite.setFlippedHorizontal(false);
    
  }
  
  public void idle(boolean flip){
    mainSprite.stopAnimation();
	bodySprite.stopAnimation();
    mainSprite.setVisible(false);
    bodySprite.setVisible(true);
    curedSprite.setVisible(false);
    
    
    //neck = neckOffset[1];   
    headSpriteAnimationInfo = ResourceManager.getInstance().getAnimationInfo(headInfo.getAnimationId(PatientHeadInfo.IDLE_ID));
    //mainSpriteAnimationInfo = ResourceManager.getInstance().getAnimationInfo(patientInfo.getAnimationId(patientInfo.IDLE_ID));
    setAnimation(headSprite, headSpriteAnimationInfo);	  
    setAnimation(bodySprite, ResourceManager.getInstance().getAnimationInfo(patientInfo.getAnimationId(patientInfo.IDLE_ID)));
    headSprite.setFlippedHorizontal(flip);
    bodySprite.setFlippedHorizontal(flip);
  }
  
  private void setAssHere(float x, float y){       
    float[] assPoint = patientInfo.getLinkPointPosition(PatientInfo.ASS_LINK_POINT);
    setPosition(x - assPoint[0] - getWidth()/2, y - assPoint[1] - getHeight()/2);
  }
  
  
  
  @Override
  public void setPositionOnBuildingReceived(float x, float y) {
    setAssHere(x, y);	  
  }

  @Override
  public void onSetFace(int face) {
	
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
    int walkId = patientInfo.getAnimationId(body);
    
    headSpriteAnimationInfo = ResourceManager.getInstance().getAnimationInfo(headInfo.getAnimationId(head));
    mainSpriteAnimationInfo = ResourceManager.getInstance().getAnimationInfo(walkId);
    //frameLink = ResourceManager.getInstance().getFrameLink(walkId);
    setAnimation(headSprite, headSpriteAnimationInfo);    
    setAnimation(mainSprite, mainSpriteAnimationInfo);
    if(!mainSprite.isVisible()){
        bodySprite.stopAnimation();
  	  curedSprite.stopAnimation();
  	  mainSprite.setVisible(true);
  	  bodySprite.setVisible(false);
  	  curedSprite.setVisible(false);	
  	}
    
  	
  }
  
  @Override
  public void onFloorChanged(int floor){
    if(onHealing){
      return;	
    }	  
    super.onFloorChanged(floor);
  }
  
  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
	
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
  
  private void showBubbleText(){
	removeBubbleBoxModifier();
	bubbleBox.setAlpha(1);
	bubbleBox.setScale(1);
    bubbleBox.setVisible(true);	  
  }
  
  private void hideBubbleText(){
	removeBubbleBoxModifier();
    bubbleBox.setVisible(false);	  
  }
  
  public boolean isInProgress(){
    return inProgress;	  
  }
  
  private void onWaiting(){
	bubbleTime = 2;
    onWaiting = true;
    healthBar.setVisible(true);
    queueNumber.setVisible(true);
  }
  
  private void finishHealingRoute(){
	
	isFinish = true;
	jump();
	if(listener != null)
	  listener.onPatientFinishHealing(this);
    //moveOut();	  
  }
  
  public boolean isFinishHealing(){
    return isFinish;	  
  }
  
  public void onHealing(){
    onWaiting = false;
    onHealing = true;
    hideBubbleText();
  }
  
   
  
  private void removeBubbleBoxModifier(){
	
    if(bubbleFadeIn != null)
      bubbleBox.unregisterEntityModifier(bubbleFadeIn);
    if(bubbleFadeOut != null)
      bubbleBox.unregisterEntityModifier(bubbleFadeOut);
    bubbleTime = 0;
  }
  
  public void jump(){   
    bodySprite.stopAnimation();
	curedSprite.stopAnimation();
	mainSprite.stopAnimation();
	mainSprite.setVisible(false);
	bodySprite.setVisible(false);
	curedSprite.setVisible(true);
	hideBubbleText();
	onWaiting = false;
	headSpriteAnimationInfo = ResourceManager.getInstance().getAnimationInfo(headInfo.getAnimationId(PatientHeadInfo.JUMP_ID)); 
	setAnimation(headSprite, headSpriteAnimationInfo);
    int jumpId = patientInfo.getAnimationId(PatientInfo.JUMP_ID);
    curedSpriteAnimationInfo = ResourceManager.getInstance().getAnimationInfo(jumpId); 
    //frameLink = ResourceManager.getInstance().getFrameLink(jumpId);
    setAnimation(curedSprite, curedSpriteAnimationInfo, this);
  }

  public boolean isMoveout(){
    return moveout;	  
  }
  
  public void onAmbulance(){
    onWaiting = false;
    setVisible(false);    
  }  
  
  private void moveOut(){
	setPickable(false);
	bodySprite.stopAnimation();
	curedSprite.stopAnimation();
	mainSprite.setVisible(true);
	bodySprite.setVisible(false);
	curedSprite.setVisible(false);
	
	//neck = neckOffset[0];
	
	
	
	hideBubbleText();
	onWaiting = false;
	moveout = true;
	idle(false);
    if(listener != null)
      listener.onPatientMoveOut(this);
  } 

  public HealingRoute addWardHealingRoute(int wardType, int floor){
	HealingRoute healingRoute = new HealingRoute(wardType, floor);
	healingRouteList.add(healingRoute);
    return healingRoute;
  }  
  
  public void nextHealingRoute(){
	inProgress = true;
	onHealing = false;
	if(healingRouteList.size() == 0){
	  setPickable(false);
	  finishHealingRoute();
	  return;    
	}
	
	onWaiting();	
	setPickable(true);
    currentHealingRoute = healingRouteList.elementAt(0);
    bubbleBox.setShowMachine(currentHealingRoute.getWardType(), currentHealingRoute.getFloor());
    if(hasRequireItem() && currentHealingRoute.getFloor() != -1){
      if(listener != null)
        listener.onPatientRequestItem(this);
      
    }
    	
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
    bubbleTime = 2;
    //bubbleBox.setVisible(true);
  }  
  
  public boolean hasRequireItem(){	
    if(currentHealingRoute == null || currentHealingRoute.getCurrentItem() == null){
      hideBubbleText();
      return false;
    }
    Item item = currentHealingRoute.getCurrentItem();
    
    //bubbleBox.setShowItem(item.getType(), item.getPatientNumber());
    return true;
  }
  
  public void showBubbleBoxItem(){
	Item item = currentHealingRoute.getCurrentItem();
	bubbleBox.setShowItem(item.getType(), item.getPatientNumber());
	
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
    //queueNumber.setVisible(true);
    updateQueuePosition();
  }
  
  public int getQueue(){
    return queueNumber.getNumber();	   
  }
  
  private void updateQueuePosition(){
    queueNumber.setPosition(healthBar.getX() - queueNumber.getWidth(), (healthBar.getY() + healthBar.getHeight()/2) - queueNumber.getHeight()/2);	  
  }
  
  private void updateHealthBarPosition(){
    healthBar.setPosition(/*patientInterface.getWidth()/2*/ - healthBar.getWidth()/2, patientInterface.getHeight() - 20);
	//healthBar.setPosition(getWidth()/2 - healthBar.getWidth()/2, - healthBar.getHeight());
    updateQueuePosition();
  }
  
   
  
  /*public void swapWard(Building newBuilding){
    if(getCurrentBuilding() != null)
      getCurrentBuilding().removeCharactor(this);
    newBuilding.receiveCharator(this);
  }*/
  
  public void callBack(){
    getCurrentBuilding().onGameCharactorCallback(this);	  
  }
  
  public void setPickable(boolean canPick){
    this.canPick = canPick;	  
  }
  
  public boolean canPick(){
    return canPick;	  
  }
  
  public boolean isOnPick(){
    return onDrag;	  
  }
  
  public boolean isOnHealing(){
    return onHealing;	  
  }
  
  public void onPicked(float x, float y){
	showBubbleText();
    bodySprite.stopAnimation(8);
	curedSprite.stopAnimation();
	mainSprite.setVisible(false);
	bodySprite.setVisible(true);
	curedSprite.setVisible(false);		
	//neck = neckOffset[1];
	
	setAnimation(headSprite, ResourceManager.getInstance().getAnimationInfo(headInfo.getAnimationId(headInfo.GRAB_ID)));
	dragX = oldDragX = x;
	dragY = oldDragY = y;
	
	onWaiting = false;
	onDrag = true;
  }
  
  private float oldDragX, oldDragY, dragX, dragY;
  private float force = 0;
  //private boolean dragFlip = false;
  private float dragTime;
  public void onDraged(float x, float y){
    //setPosition(x, y);	
    dragX = x;
    dragY = y;
    if(Math.abs(x - oldDragX) > 20 ||
       Math.abs(y - oldDragY) > 20){
      dragTime = 0.15f;
      force += 0.5;
      if(force > 4)
        force = 4;
      boolean dragFlip;
      if(x < oldDragX){
        dragFlip = true;	  
      }else{
    	dragFlip = false;  
      }
      oldDragX = x;
      oldDragY = y;
      bodySprite.setFlippedHorizontal(dragFlip);
      
      
      int[] sequence = dragAnimationInfo.getSequence();
      
	  int frame = (int)force + sequence[0];
	  bodySprite.setCurrentTileIndex(frame);
    }   
  }
  
  public void onUnPicked(){
    force = -1;	  
    onWaiting = true;
    bodySprite.setFlippedHorizontal(false);
    onDrag = false;
    bubbleTime = 2;
  }
  
  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {	
	if(onWaiting){
		//Log.d("Rokejitsx", "bubbleTime = "+bubbleTime);
	  if(bubbleTime > 0){
	    bubbleTime -= pSecondsElapsed;
	    if(bubbleTime <= 0){
	      bubbleTime = 0;
	      if(!bubbleBox.isVisible()){
	    	bubbleBox.setVisible(true);
	        bubbleFadeIn = new SequenceEntityModifier(
	      	  new ParallelEntityModifier(
	    	      new ScaleModifier(0.25f, 0.75f, 1.25f),		  
	    		  new AlphaModifier(0.25f, 0, 1)
	    	  ),
	    	  new ScaleModifier(0.25f, 1.25f, 1)
	    	);
	    	bubbleFadeIn.setRemoveWhenFinished(true);
	    	bubbleFadeIn.setSubSequenceModifierListener(new ISubSequenceModifierListener<IEntity>() {
	    	  @Override
	    	  public void onSubSequenceStarted(IModifier<IEntity> pModifier,
	    	    	 	IEntity pItem, int pIndex) {
	    			// TODO Auto-generated method stub
	    			
	    	  }

	    	  @Override
	    	  public void onSubSequenceFinished(IModifier<IEntity> pModifier,
	    	   		      IEntity pItem, int pIndex) {
	    		 if(pIndex == 1){
	    		   bubbleTime = 2;
	    		   bubbleBox.setAlpha(1);
	    		   bubbleBox.setVisible(true);
	    		 }
	    			
	    	  }
	    	});
	        bubbleBox.registerEntityModifier(bubbleFadeIn);	  
	      }else{
	        bubbleFadeOut = new SequenceEntityModifier(
	    		  new ScaleModifier(0.25f, 1, 1.25f),
	    		  new ParallelEntityModifier(
	    	          new ScaleModifier(0.25f, 1.25f, 0.75f),		  
	   				  new AlphaModifier(0.25f, 1, 0)
	   			  )	  
			);    			
    			
			bubbleFadeOut.setRemoveWhenFinished(true);    			
	   			
		    bubbleFadeOut.setSubSequenceModifierListener(new ISubSequenceModifierListener<IEntity>() {
	          @Override
	    	  public void onSubSequenceStarted(IModifier<IEntity> pModifier,
	      			IEntity pItem, int pIndex) {
	    					// TODO Auto-generated method stub
	    				
	    	  }

	    	  @Override
	    	  public void onSubSequenceFinished(IModifier<IEntity> pModifier,
	    	 			IEntity pItem, int pIndex) {
	    	
	    	    if(pIndex == 1){
	    	      bubbleTime = 2;
	    	      bubbleBox.setAlpha(1);
	    	      bubbleBox.setVisible(false);
	    	    }
	    					
	    	  }
	    			
	    			
	    	});
	    	bubbleBox.registerEntityModifier(bubbleFadeOut);  
	      }
	    }
	  }
	  setHealthLevel(getHealthLevel() - (feverLevel * pSecondsElapsed));	
	  if(getHealthLevel() <= 0){
	    setHealthLevel(0);
	    healthBar.setVisible(false);
	    queueNumber.setVisible(false);
	    setPickable(false);
	    Item item = getRequireItem();
	    if(item != null){
	      GameObject owner = item.getOwner();
	      if(owner != null){
	        if(owner instanceof Pharmacy){
	          Pharmacy infoWard = (Pharmacy) owner;
	          infoWard.removeItemFromQueue(item);
	      
	        }else if(owner instanceof Nurse){
	          Nurse nurse = (Nurse) owner;
	          nurse.handOut(item);
	      
	        }
	      }
	      //HospitalGameActivity.getGameActivity().sendDeattachChild((Entity) item.getParent(), item);
	      item.setVisible(false);
	    }
	    moveOut();
	  }
	}
	super.onManagedUpdate(pSecondsElapsed);
	
	//final float[] position = this.convertLocalToSceneCoordinates(40.5f - headSprite.getWidth()/2, 2 - headSprite);
	//AnimatedSprite sprite;
	if(mainSprite.isVisible()){
	  Hashtable<String, float[]> frameLink = ResourceManager.getInstance().getFrameLink(mainSpriteAnimationInfo.getId());
	  //Log.d("RokejitsX", "mainSpriteAnimationInfo.getId() = "+mainSpriteAnimationInfo.getId());
      //float[] headLink = headInfo.getLinkPointPosition(headInfo.HEAD_LINK_POINT);         	  
	  float[] headNeckLink = headInfo.getLinkPointPosition(headInfo.NECK_LINK_POINT);
	  float[] neckLink = frameLink.get("" + mainSprite.getCurrentTileIndex());
	  //Log.d("RokejitsX", "neckLink = "+neckLink);
		  /*headSprite.setPosition(curedSprite.getX() + curedSprite.getBaseWidth() / 2 + offset[0] - headSprite.getBaseWidth()/2, curedSprite.getY() 
				  + offset[1] + headSprite.getBaseHeight()/2);*/
	  
	  if(!headSpriteAnimationInfo.isFlip()){
	    headSprite.setPosition(mainSprite.getX() + mainSprite.getBaseWidth() / 2 + neckLink[0] /*+ headLink[0]*/ - headNeckLink[0] - headSprite.getBaseWidth()/2,
	    		               mainSprite.getY() + mainSprite.getBaseHeight() / 2 + neckLink[1] /*+ headLink[1]*/  - headNeckLink[1]  - headSprite.getBaseHeight()/2);
	  }else{
		  headSprite.setPosition(mainSprite.getX() + mainSprite.getBaseWidth() / 2 + neckLink[0] /*+ headLink[0]*/ + headNeckLink[0] - headSprite.getBaseWidth()/2,
	                             mainSprite.getY() + mainSprite.getBaseHeight() / 2 + neckLink[1] /*+ headLink[1]*/  - headNeckLink[1]  - headSprite.getBaseHeight()/2);  
	  }
         	  
	  
		
	  /*float[] offset = frameLink.get("" + mainSprite.getCurrentTileIndex());
	  //Log.d("RokejitsX", "Patient getNumber "+getQueue());
	  headSprite.setPosition(mainSprite.getX() + mainSprite.getBaseWidth() / 2 + offset[0] - headSprite.getBaseWidth()/2, mainSprite.getY() 
			  + offset[1] + headSprite.getBaseHeight()/2);*/
	}else if(bodySprite.isVisible()){  
	  
	  float[] neckLink = patientInfo.getLinkPointPosition(PatientInfo.NECK_LINK_POINT);	  
	  float[] headNeckLink = headInfo.getLinkPointPosition(headInfo.NECK_LINK_POINT);
	  float x;
	  float y;
	  if(onDrag){
		x = dragX;
		y = dragY;  	    
			
		if(!bodySprite.isFlippedHorizontal()){
		  x = x - neckLink[0];	
		  y = y - neckLink[1];
		}else{
		  x = x + neckLink[0];	
		  y = y - neckLink[1];	
		}
			
		x = x - getWidth() / 2;
		y = y - getHeight() / 2;
			
			
		setPosition(x, y);
	  }
		
	  x = bodySprite.getX() + bodySprite.getBaseWidth() / 2;
	  y = bodySprite.getY() + bodySprite.getBaseHeight() / 2;
      if(!bodySprite.isFlippedHorizontal()){
        x = x + neckLink[0];
         
		    /*headSprite.setPosition(bodySprite.getX() + bodySprite.getBaseWidth() / 2 + neckLink[0] - headNeckLink[0] - headSprite.getBaseWidth()/2,
	                               bodySprite.getY() + bodySprite.getBaseHeight() / 2 + neckLink[1]  - headNeckLink[1]  - headSprite.getBaseHeight()/2);*/
	  }else{
	    x = x - neckLink[0];
			/*headSprite.setPosition(bodySprite.getX() + bodySprite.getBaseWidth() / 2 - neckLink[0] - headNeckLink[0] - headSprite.getBaseWidth()/2,
                    bodySprite.getY() + bodySprite.getBaseHeight() / 2 + neckLink[1] - headNeckLink[1]  - headSprite.getBaseHeight()/2);*/	
	  }
      y = y + neckLink[1];
      
	  if(!headSprite.isFlippedHorizontal()){
	    x = x - headNeckLink[0];
	    /*headSprite.setPosition(bodySprite.getX() + bodySprite.getBaseWidth() / 2 + neckLink[0] - headNeckLink[0] - headSprite.getBaseWidth()/2,
	     		               bodySprite.getY() + bodySprite.getBaseHeight() / 2 + neckLink[1] - headNeckLink[1]  - headSprite.getBaseHeight()/2);*/
	  }else{
	    x = x + headNeckLink[0];
	    /*headSprite.setPosition(bodySprite.getX() + bodySprite.getBaseWidth() / 2 + neckLink[0] + headNeckLink[0] - headSprite.getBaseWidth()/2,
	    		               bodySprite.getY() + bodySprite.getBaseHeight() / 2 + neckLink[1] - headNeckLink[1]  - headSprite.getBaseHeight()/2);*/  
	  }
	  y = y - headNeckLink[1];
	  
	  x = x - headSprite.getBaseWidth()/2;
	  y = y - headSprite.getBaseHeight()/2;
	  headSprite.setPosition(x, y);
	  
	  
    }else{
      Hashtable<String, float[]> frameLink = ResourceManager.getInstance().getFrameLink(curedSpriteAnimationInfo.getId());
      //float[] headLink = headInfo.getLinkPointPosition(headInfo.HEAD_LINK_POINT);         	  
   	  float[] headNeckLink = headInfo.getLinkPointPosition(headInfo.NECK_LINK_POINT);
   	  float[] neckLink = frameLink.get("" + curedSprite.getCurrentTileIndex());
	  /*headSprite.setPosition(curedSprite.getX() + curedSprite.getBaseWidth() / 2 + offset[0] - headSprite.getBaseWidth()/2, curedSprite.getY() 
			  + offset[1] + headSprite.getBaseHeight()/2);*/
   	  if(!headSpriteAnimationInfo.isFlip()){
	    headSprite.setPosition(curedSprite.getX() + curedSprite.getBaseWidth() / 2 + neckLink[0] /*+ headLink[0]*/ - headNeckLink[0] - headSprite.getBaseWidth()/2,
	    		               curedSprite.getY() + curedSprite.getBaseHeight() / 2 + neckLink[1] /*+ headLink[1]*/  - headNeckLink[1]  - headSprite.getBaseHeight()/2);
	  }else{
		headSprite.setPosition(curedSprite.getX() + curedSprite.getBaseWidth() / 2 + neckLink[0] /*+ headLink[0]*/ + headNeckLink[0] - headSprite.getBaseWidth()/2,
				               curedSprite.getY() + curedSprite.getBaseHeight() / 2 + neckLink[1] /*+ headLink[1]*/  - headNeckLink[1]  - headSprite.getBaseHeight()/2);  
	  }
      
    }
	
	/*if(frameLink != null){
	  offset = frameLink.get("" + sprite.getCurrentTileIndex());
	  headSprite.setPosition(sprite.getX() + sprite.getBaseWidth() / 2 + offset[0] - headSprite.getBaseWidth()/2, sprite.getY() 
			  + offset[1] + headSprite.getBaseHeight()/2);
	  //setSpritePosition(headSprite, sprite.getX() + sprite.getWidth() / 2 + offset[0], sprite.getY() + sprite.getHeight() / 2 + offset[1]);
	}else{
	  setSpritePosition(headSprite, offset[0], offset[1]);
	}*/
	
	if(dragTime > 0)
	  dragTime -= pSecondsElapsed;
	
	if(dragTime  < 0)
	  dragTime = 0;
	
	if(dragTime == 0){
	  if(Math.abs(dragX - oldDragX) < 20 &&
	     Math.abs(dragY - oldDragY) < 20 && force > 0){
         force -= 0.1f;
	    //bodySprite.setFlippedHorizontal(dragFlip);
        int[] sequence = dragAnimationInfo.getSequence();
         
   	    int frame = (int)force + sequence[0];
	    bodySprite.setCurrentTileIndex(frame);
	  
	    
	  }
    }
	patientInterface.setPosition(headSprite.getX() + headSprite.getBaseWidth() - 20, headSprite.getY() - patientInterface.getHeight());
	
  }  
  
  
  
  
  
  
  
  @Override
  protected void onDrawChildren(GL10 pGL, Camera pCamera) {	
    if(this.mChildren != null && this.mChildrenVisible) {
	  final ArrayList<IEntity> children = this.mChildren;
  	  final int childCount = children.size();
	  for(int i = 0; i < childCount; i++) {
		if(!children.get(i).equals(patientInterface))
	      children.get(i).onDraw(pGL, pCamera);
	  }
	}
  }
  
  public void drawInterface(GL10 pGL, Camera pCamera){	  
    pGL.glPushMatrix();
	{
      if(this.getCurrentBuilding() != null && !isOnPick())	
	    this.getCurrentBuilding().myOnApplyTransformations(pGL);	
	  this.onApplyTransformations(pGL);
	  patientInterface.onDraw(pGL, pCamera);
		
	}
	pGL.glPopMatrix();
    	  
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
    private int floor; //-1 not have
    private Vector<Item> itemList;    
    public HealingRoute(int wardType, int floor){
      this.wardType = wardType;	
      itemList = new Vector<Item>();
      this.floor = floor;
    }	  
    
    public int getFloor(){
      return floor;	
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
  
  class BubbleBox extends AnimatedSprite{
    
	private AnimatedSprite machineField,floorNumberSprite;
	private Item item;
    public BubbleBox(){
      super(0, 0, ResourceManager.getInstance().getTexture(BALLOON));     
      machineField = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MACHINES));
      floorNumberSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(ELEVATOR_NUMBERS));
      attachChild(floorNumberSprite);
      floorNumberSprite.setVisible(false);
      //setFlippedHorizontal(true);
      setCurrentTileIndex(0);
      
      
    }	   
    
    public void setShowMachine(int buildingType, int floor){
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
      if(floor == -1){
        frame -= 1;
        floorNumberSprite.setVisible(true);
        floorNumberSprite.setCurrentTileIndex(4);
      }else{
    	
    	floorNumberSprite.setVisible(isShowFloorNumberInBubbleBox);	
        floorNumberSprite.setCurrentTileIndex(3 - floor);	  
      }
      /*if(showItem != null)
        showItem.setVisible(false);*/
      detachChildren();
      //machineField.setAlpha(getAlpha());
      
      //if(isVisible())
      machineField.setVisible(true);
      /*else
    	machineField.setVisible(false);*/ 
      machineField.setCurrentTileIndex(frame);
      if(machineField.getParent() == null){
    	Vector<Entity> list = new Vector<Entity>();
    	list.add(machineField);
    	list.add(floorNumberSprite);
        HospitalGameActivity.getGameActivity().sendAttachChild(this, list);
        machineField.setPosition(getBaseWidth() / 2 - machineField.getWidth() / 2, getBaseHeight() / 2 - machineField.getHeight() / 2);
        floorNumberSprite.setPosition(machineField.getX() + machineField.getWidth() - floorNumberSprite.getBaseWidth(), 
        		                      machineField.getY() + machineField.getHeight() - floorNumberSprite.getBaseHeight());
      }
      bubbleTime = 2;
    }
    
    
    
    public void setShowItem(int itemType, int patientNumber){
      item = Item.createItemObject(itemType, patientNumber);
      item.setPosition(getBaseWidth() / 2 - item.getWidth() / 2, getBaseHeight() / 2 - item.getHeight() / 2);
      //item.setAlpha(1);
      item.setVisible(true);
      /*if(isVisible())
        item.setVisible(true);
      else
    	item.setVisible(false);*/ 
      detachChildren();
      attachChild(item);
      /*HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable(){
        public void run(){
           	
        }	  
      });*/
      
      
      bubbleTime = 2;
      
    }
    
        
    
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

	@Override
	protected void doDraw(GL10 pGL, Camera pCamera) {		
		super.doDraw(pGL, pCamera);
	}
    
    
    
    
    
  }

  @Override
  public void onAnimationEnd(AnimatedSprite pAnimatedSprite) {
	if(pAnimatedSprite.equals(curedSprite)){
	  moveOut();	
	}
	
  }




  
}
