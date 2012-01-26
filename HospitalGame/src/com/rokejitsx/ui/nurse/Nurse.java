package com.rokejitsx.ui.nurse;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.AnimationInfo;
import com.rokejitsx.ui.building.Building;
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
  private boolean isCleaning;
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
    setSpeed(300);
    
  }
  
  
  public void setNurseListener(NurseListener listener){
    this.listener = listener;	   
  }
  
  
  public void clean(){
	if(leftItem != null && rightItem != null)
	  return;
    hideAllSprite();
    healSprite.setVisible(true);
    AnimationInfo animInfo  = ResourceManager.getInstance().getNurseAnimationInfo(17);    
    setAnimation(healSprite, animInfo.getEachFrameDuration(), animInfo.getSequence(), 1, false, this);
    isCleaning = true;
    return;
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



  @Override
  public void onSetFace(int face) {  	
    hideAllSprite();
    int set;
    if(leftItem == null && rightItem == null){
      set = 0; 	
    }else{
      if(rightItem != null)
        set = 1;
      else if(leftItem != null)
        set = 2;
      else
    	set = 3;
    }   
    AnimatedSprite anim = walkSprite[set];
    anim.setVisible(true);
    setAnimation(anim, getNurseAnimationInfo(movementAnimationSetIds[set][face]));
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
      leftItem = null;      
    }else if(item.equals(rightItem)){
      rightItem.setVisible(false);
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

  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {
	if(leftItem != null){
	  leftItem.setPosition(this.getX() - leftItem.getWidth()/2, this.getY() + getHeight()/2 - leftItem.getHeight()/2);	
	}
	
	if(rightItem != null){
	  rightItem.setPosition(this.getX() + getWidth()/2, this.getY() + getHeight()/2 - rightItem.getHeight()/2);	
    }
	super.onManagedUpdate(pSecondsElapsed);
  }



  @Override
  public void onAnimationEnd(AnimatedSprite pAnimatedSprite) {	
	if(pAnimatedSprite.equals(healSprite)){
	  isCleaning = false;
	  Building building = getCurrentBuilding();
	  building.setState(Building.STATE_IDLE);
	  if(listener != null)
	    listener.onFinishCleaning(this, building);
	}
	
	//hideAllSprite();
  }



  
  
  
  
}
