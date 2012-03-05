package com.rokejitsx.ui.nurse;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;

import android.graphics.PointF;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.AnimationInfo;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.building.ward.Ward;
import com.rokejitsx.ui.item.Dust;
import com.rokejitsx.ui.item.Item;

public class Nurse extends GameCharactor implements IAnimationListener {
  private final static int IDLE_WALK_UP_LEFT 			= 0;
  private final static int IDLE_WALK_UP_RIGHT 			= 1;
  private final static int IDLE_WALK_DOWN_LEFT 			= 2;
  private final static int IDLE_WALK_DOWN_RIGHT 		= 3;
  private final static int LEFT_HAND_WALK_UP_LEFT 		= 4;
  private final static int RIGHT_HAND_WALK_UP_RIGHT 	= 5;
  private final static int LEFT_HAND_WALK_DOWN_LEFT 	= 6;
  private final static int RIGHT_HAND_WALK_DOWN_RIGHT 	= 7;
  private final static int RIGHT_HAND_WALK_UP_LEFT 		= 8;
  private final static int LEFT_HAND_WALK_UP_RIGHT 		= 9;
  private final static int RIGHT_HAND_WALK_DOWN_LEFT 	= 10;
  private final static int LEFT_HAND_WALK_DOWN_RIGHT 	= 11;
  private final static int TWO_HAND_WALK_UP_LEFT 		= 12;
  private final static int TWO_HAND_WALK_UP_RIGHT 		= 13;
  private final static int TWO_HAND_WALK_DOWN_LEFT 		= 14;
  private final static int TWO_HAND_WALK_DOWN_RIGHT 	= 15;
  private final static int HEAL_UP_LEFT 				= 16;
  private final static int HEAL_UP_RIGHT 				= 17;  
  private final static int REPAIR_UP_LEFT 				= 18;
  private final static int REPAIR_UP_RIGHT 				= 19;
  private final static int IDLE_LOOK_SIDES 				= 20;
  
  
  private final static int[][] movementAnimationSetIds = {
    {
      IDLE_WALK_DOWN_LEFT,
  	  IDLE_WALK_DOWN_RIGHT,
	  IDLE_WALK_UP_LEFT,
	  IDLE_WALK_UP_RIGHT
	  
    },
    {
      RIGHT_HAND_WALK_DOWN_LEFT,
      RIGHT_HAND_WALK_DOWN_RIGHT,
      RIGHT_HAND_WALK_UP_LEFT,
      RIGHT_HAND_WALK_UP_RIGHT
      
    },
    {
      LEFT_HAND_WALK_DOWN_LEFT,
      LEFT_HAND_WALK_DOWN_RIGHT,
      LEFT_HAND_WALK_UP_LEFT,
      LEFT_HAND_WALK_UP_RIGHT
            
    },    
    {
      TWO_HAND_WALK_DOWN_LEFT,
      TWO_HAND_WALK_DOWN_RIGHT,	
      TWO_HAND_WALK_UP_LEFT,
      TWO_HAND_WALK_UP_RIGHT
      
    }
  };
	
  private Item itemToPick, leftItem, rightItem;
  
  private AnimatedSprite[] walkSprite;
  private AnimatedSprite healSprite, repairSprite;
  private NurseListener listener;
  private boolean isCleaning, isRepairing;
  //private int currentRouteIndex = -1; 
  
  
  
  /*public void setNurseListener(NurseListener listener){
    this.listener = listener;	  
  }
  
  public void addQueue(Object target){
    targetQueue.add(target);	  
  }*/
  
  
  public Nurse(){
    super(NURSE_IDLE);	  
    walkSprite = new AnimatedSprite[4];
    walkSprite[0] = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(NURSE_IDLE_WALK));
    walkSprite[1] = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(NURSE_RIGHT_HAND_WALK));
    walkSprite[2] = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(NURSE_LEFT_HAND_WALK));
    walkSprite[3] = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(NURSE_TWO_HAND_WALK));
    healSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(NURSE_HEAL));
    repairSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(NURSE_REPAIR));
    
    setWidth(97);
    setHeight(166);
    for(int i = 0;i < walkSprite.length;i++){
      AnimatedSprite anim = walkSprite[i];
      anim.setPosition(getWidth()/2 - anim.getWidth()/2, getHeight() - anim.getHeight());
      anim.setVisible(false);
      attachChild(anim);
    }
    mainSprite.setPosition(getWidth()/2 - mainSprite.getWidth()/2, getHeight() - mainSprite.getHeight());
    repairSprite.setPosition(getWidth()/2 - repairSprite.getWidth()/2, getHeight() - repairSprite.getHeight());
    healSprite.setPosition(getWidth()/2 - healSprite.getWidth()/2, getHeight() - healSprite.getHeight());
    
    attachChild(mainSprite);
    attachChild(repairSprite);
    attachChild(healSprite);
    
    repairSprite.setVisible(false);
    healSprite.setVisible(false);
    
    setGameCharactorState(STATE_IDLE);
    setAnimation(mainSprite, getNurseAnimationInfo(IDLE_LOOK_SIDES));
    //setSpeed(300);
    unBoost();
    
  }
  
  
  public AnimatedSprite getNurseTail(){
    if(getGameCharactorState()== STATE_MOVE){
      return walkSprite[walkAnimSet];   	
    }
    return null;
  }
  
  public boolean isBoost(){
    return getSpeed() == 600;	  
  }
  
  public void boost(){
    setSpeed(600);	  
  }
  
  public void unBoost(){
    setSpeed(300);	  
  }
  
  public void setNurseListener(NurseListener listener){
    this.listener = listener;	   
  }
  
  public boolean isHasAvailableHand(){
    return leftItem == null || rightItem == null;	  
  }
  
  private float nurseOperationTime, nurseOperationMaxTime;
  public void repair(){
    if(!isHasRepairToolInHand())
      return;
    
    hideAllSprite();
    hideAllItemInHand();
    repairSprite.setVisible(true);
    AnimationInfo animInfo  = ResourceManager.getInstance().getNurseAnimationInfo(18);    
    setAnimation(repairSprite, animInfo.getEachFrameDuration(), animInfo.getSequence(), 1, false);
    isRepairing = true;
    nurseOperationTime = 0;
    nurseOperationMaxTime = ((Ward)getCurrentBuilding()).getRepairTime();
  }
  
  
  private void hideAllItemInHand(){
    if(leftItem != null)
      leftItem.setVisible(false);	
    
    if(rightItem != null)
      rightItem.setVisible(false);
  }
  
  private void showAllItemInHand(){
    if(!this.isVisible())
      return;
    if(leftItem != null)
      leftItem.setVisible(true);	
      
    if(rightItem != null)
      rightItem.setVisible(true);
    
  }
  
  
  public boolean isRepairing(){
    return isRepairing;	  
  }
  
  private boolean isHasRepairToolInHand(){
    if(leftItem != null){
      if(leftItem.getType() == Item.REPAIR_TOOL)
        return true;
    }	  
    
    if(rightItem != null){
      if(rightItem.getType() == Item.REPAIR_TOOL)
        return true;
    }
    return false;
  }
  
  public void clean(){	 
	if(!isHasAvailableHand())
	  return;
    hideAllSprite();
    hideAllItemInHand();
    healSprite.setVisible(true);
    AnimationInfo animInfo  = ResourceManager.getInstance().getNurseAnimationInfo(17); 
    Building building = getCurrentBuilding();
    float centerX = building.getX() + building.getWidth()/2;
    boolean flip = false;
    if(centerX < getX() + getWidth()/2)
      flip = true;
    setAnimation(healSprite, animInfo.getEachFrameDuration(), animInfo.getSequence(), 0, flip, this);
    isCleaning = true;
    
  }
  
  public boolean isCleaning(){
    return isCleaning;	  
  }
  
  @Override
  protected void onSetGameCharactorState(int state) {
	switch(state){
	  case STATE_IDLE:
	    hideAllSprite();
		mainSprite.setVisible(true);
		setAnimation(mainSprite, getNurseAnimationInfo(IDLE_LOOK_SIDES));	  
	  break;
	}
	
	super.onSetGameCharactorState(state);
  }


  private int walkAnimSet;
  @Override
  public void onSetFace(int face) {  	
    hideAllSprite();    
    if(leftItem == null && rightItem == null){
      walkAnimSet = 0; 	
    }else{
      if(rightItem != null && leftItem == null)
    	walkAnimSet = 1;
      else if(leftItem != null && rightItem == null)
    	walkAnimSet = 2;
      else
    	walkAnimSet = 3;
    }   
    AnimatedSprite anim = walkSprite[walkAnimSet];
    anim.setVisible(true);
    setAnimation(anim, getNurseAnimationInfo(movementAnimationSetIds[walkAnimSet][face]));
  }
  
  
  
 


  private void hideAllSprite(){
    hideMovementSprite();
    mainSprite.setVisible(false);
    repairSprite.setVisible(false);
    healSprite.setVisible(false);
  }
  
  private void hideMovementSprite(){
    for(int i = 0;i < walkSprite.length;i++){
      walkSprite[i].setVisible(false); 	
    }	  
  }
  
  
  
  private AnimationInfo getNurseAnimationInfo(int id){
    return ResourceManager.getInstance().getNurseAnimationInfo(id); 	  
  }
  
  
  public void setItemToPick(Item item){
    this.itemToPick = item;	  
  }
  
  @Override
  public void setVisible(boolean pVisible) {	
	super.setVisible(pVisible);
	if(leftItem != null)
	  leftItem.setVisible(pVisible);
	if(rightItem != null)
	  rightItem.setVisible(pVisible);
  }

  @Override
  public void setCurrentFloor(int floor) {
	if(floor == getCurrentFloor())
	  return;
	if(leftItem != null)
	  leftItem.setCurrentFloor(floor);
	if(rightItem != null)
	  rightItem.setCurrentFloor(floor);
	super.setCurrentFloor(floor);
  }
  
  public Item[] getAllItemInHand(){
    Item[] items = new Item[2];
    items[0] = getLeftItem();
    items[1] = getRightItem();
    return items;
  }

  public Item getLeftItem(){
    return leftItem;	  
  }
  
  public Item getRightItem(){
    return rightItem;	  
  }
  
  public Item getItemToPick(){
    return itemToPick;	  
  }
  
  public void pickItem(){
    if(itemToPick != null){      
      if(leftItem == null){
        leftItem = itemToPick;	  
      }else if(rightItem == null){
        rightItem = itemToPick;	  
      }
      itemToPick.setOwner(this);
      itemToPick = null;
      setItemInHandPosition();
    }	  
  }
  
  public boolean isHasItemInHand(Item item){
    if(leftItem != null && leftItem.equals(item))
      return true;
    if(rightItem != null && rightItem.equals(item))
      return true;
    return false;
  }
  
  /*public Item[] getItemInHand(int itemType){
	Vector<Item> items = new Vector<Item>();
    if(leftItem != null && leftItem.getType() == itemType)
      items.add(leftItem);
    if(rightItem != null && rightItem.getType() == itemType)
      items.add(rightItem);
    if(items.size() == 0)
      return null;
    Item[] result = new Item[items.size()];
    items.copyInto(result);
    return result;
    
  } */
  
  public void handOut(Item item){
    if(item.equals(leftItem)){
      leftItem.setVisible(false);
      HospitalGameActivity.getGameActivity().sendDeattachChild(leftItem);
      leftItem = null;      
    }else if(item.equals(rightItem)){
      rightItem.setVisible(false);
      HospitalGameActivity.getGameActivity().sendDeattachChild(rightItem);
      rightItem = null;	
    }
  }
  
  public boolean handOutDust(){
	boolean handout = false;
    if(leftItem != null && (leftItem instanceof Dust)){
      leftItem.setVisible(false);
      leftItem = null;
      handout = true;
    }
    
    if(rightItem != null && rightItem instanceof Dust){
      rightItem.setVisible(false);
      rightItem = null;
      handout = true;
    }
    
    return handout;
  }
  
  
  
  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
	Rectangle body = new Rectangle(0, 0, 30, 50);
	body.setColor(0, 1, 0);
	return null;
  }
  
  private void setItemInHandPosition(){
    int face = getFaceDirection();
	PointF rightHandPosition = new PointF();
	PointF leftHandPosition = new PointF();
	switch(face){
	  case FACE_DOWN_R:
	    rightHandPosition.x = 44;  
	    rightHandPosition.y = 71;
	    leftHandPosition.x = 70;  
	    leftHandPosition.y = 59;
	  break;
	  case FACE_UP_R:
	    rightHandPosition.x = 70;  
	    rightHandPosition.y = 60;
	    leftHandPosition.x = 52;  
	    leftHandPosition.y = 42;
	  break;
	  case FACE_DOWN_L:
	    rightHandPosition.x = 26;  
	    rightHandPosition.y = 63;
	    leftHandPosition.x = 43;  
	    leftHandPosition.y = 70;
	  break;
	  case FACE_UP_L:
	    rightHandPosition.x = 16;  
	    rightHandPosition.y = 54;
	    leftHandPosition.x = 38;  
	    leftHandPosition.y = 42;
	  break;
	  default:	
		rightHandPosition.x = 45;  
	    rightHandPosition.y = 100;
		leftHandPosition.x = 80;  
	    leftHandPosition.y = 100;  
	}
	
	if(leftItem != null)
	  leftItem.setGameObjectPositionAsCenter(leftHandPosition.x, leftHandPosition.y);
	if(rightItem != null)
	  rightItem.setGameObjectPositionAsCenter(rightHandPosition.x, rightHandPosition.y);	  
  }
  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {
	/*if(leftItem != null){
	  leftItem.setPosition(this.getX() - leftItem.getWidth()/2, this.getY() + getHeight()/2 - leftItem.getHeight()/2);	
	}	
	if(rightItem != null){
	  rightItem.setPosition(this.getX() + getWidth()/2, this.getY() + getHeight()/2 - rightItem.getHeight()/2);	
    }*/
	
		
	super.onManagedUpdate(pSecondsElapsed);
	setItemInHandPosition();
	
    if(isRepairing){
	  nurseOperationTime += pSecondsElapsed;
	  if(nurseOperationTime >= nurseOperationMaxTime){
		isRepairing = false;
		hideAllSprite();
		showAllItemInHand();
		repairSprite.stopAnimation();	
		setGameCharactorState(STATE_IDLE);
	    Building building = getCurrentBuilding();
		building.setState(Building.STATE_IDLE);	  
		if(leftItem != null){
		  if(leftItem.getType() == Item.REPAIR_TOOL){
			HospitalGameActivity.getGameActivity().sendDeattachChild(leftItem);
		    leftItem = null;
		  }
		}
		
		if(rightItem != null){
		  if(rightItem.getType() == Item.REPAIR_TOOL){
			HospitalGameActivity.getGameActivity().sendDeattachChild(rightItem);
			rightItem = null;
		  }
		}
		if(listener != null)
		  listener.onFinishRepairing(this, building);
	  }
	}
  }
  @Override
  protected void onDrawChildren(GL10 pGL, Camera pCamera) {	  
	int face = getFaceDirection();
    switch(face){
	  case FACE_DOWN_R:
	    super.onDrawChildren(pGL, pCamera);
	    drawRightItem(pGL, pCamera);
	    drawLeftItem(pGL, pCamera);
	      
	  break;
	  case FACE_UP_R:	    
		drawLeftItem(pGL, pCamera);
	    super.onDrawChildren(pGL, pCamera);
	    drawRightItem(pGL, pCamera);
	    
	  break;
	  case FACE_DOWN_L:
		super.onDrawChildren(pGL, pCamera);
		drawRightItem(pGL, pCamera);
	    drawLeftItem(pGL, pCamera);		    
	  break;
	  case FACE_UP_L:	    
	    drawRightItem(pGL, pCamera);
	    super.onDrawChildren(pGL, pCamera);
	    drawLeftItem(pGL, pCamera);
	  break;
	  default:		
	    super.onDrawChildren(pGL, pCamera);
	    drawLeftItem(pGL, pCamera);
	    drawRightItem(pGL, pCamera);
	}
	
		
	
	
  }
  
  private void drawRightItem(GL10 pGL, Camera pCamera){
    if(rightItem != null)
      rightItem.onDraw(pGL, pCamera);
  }
  
  private void drawLeftItem(GL10 pGL, Camera pCamera){
    if(leftItem != null)
      leftItem.onDraw(pGL, pCamera);
  }


@Override
  public void onAnimationEnd(AnimatedSprite pAnimatedSprite) {	
	if(pAnimatedSprite.equals(healSprite)){
	  isCleaning = false;
	  showAllItemInHand();
	  Building building = getCurrentBuilding();
	  building.setState(Building.STATE_IDLE);
	  if(listener != null)
	    listener.onFinishCleaning(this, building);
	}
	
	//hideAllSprite();
  }



  
  
  
  
}
