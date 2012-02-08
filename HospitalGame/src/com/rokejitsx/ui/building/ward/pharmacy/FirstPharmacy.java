package com.rokejitsx.ui.building.ward.pharmacy;

import java.util.Vector;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import android.util.Log;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.item.Item;

public class FirstPharmacy extends Pharmacy{ /* private static final int STATE_IDLE      = 0;
  private static final int STATE_PREPARING = 1;
  private int state;*/
	
	private final static String[][] LEVEL_IMG_NAME_LIST = {
	  {       
	     SIGN_1_LONG,
	     FARMACIA_BALCAO_1	
	  },
	  {
	     SIGN_2_LONG,
	     FARMACIA_BALCAO_2  	
	  },
	  {
	      SIGN_3_LONG,
	      FARMACIA_BALCAO_3  	
	   },
	   {
	      SIGN_4_LONG,
	      FARMACIA_BALCAO_4  	
	   },
	   {
	      SIGN_5_LONG,
	      FARMACIA_BALCAO_5  	
	   },
	   {
	      SIGN_6_LONG,
	      FARMACIA_BALCAO_6  	
	   },
	   {
	      SIGN_7_LONG,
	      FARMACIA_BALCAO_7  	
	   }
	  };		
  private Vector<Item> prepareQueue;
  private long startPrepareTime;
  private AnimatedSprite shellSprite, counterSprite, signSprite, elevatorSprite;
  private String counterImgName, signImgName;
  private FirstPharmacyListener listener;
  
  public FirstPharmacy(int hospitalLevel){    
	super(PHARMACY, hospitalLevel);
	String[] list = LEVEL_IMG_NAME_LIST[hospitalLevel];
    signImgName = list[0];
    counterImgName = list[1];
    shellSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_ESTANTE));    	
    counterSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(counterImgName));
    signSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(signImgName));
    elevatorSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_ELEVADORTINY));
    shellSprite.setCurrentTileIndex(2);
    counterSprite.setPosition(73, 43);    
    signSprite.setPosition(140 + 108 / 2 - signSprite.getBaseWidth()/2, 135 +71/2 - signSprite.getBaseHeight()/2);
    elevatorSprite.setPosition(202, -30);
    mainSprite.setPosition(92, 42);
    
    attachChild(shellSprite);    
	attachChild(counterSprite);
	attachChild(mainSprite);
	attachChild(signSprite);
	attachChild(elevatorSprite);
	
	setWidth(316);
	setHeight(230);
    prepareQueue = new Vector<Item>();    
    setIdleAnimationId(16);
    setDoWorkAnimationId(17);
    setState(STATE_IDLE);
  }
  
  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {	
   /* Rectangle body = new Rectangle(0, 0, 30 * 5, 50);
	body.setColor(1, 0, 1);*/
	
	return null;
  }
  
  public void setFirstInfomationListener(FirstPharmacyListener listener){
    this.listener = listener;	   
  }
  
  public void addPrepareQueue(Item item){
	item.setOwner(this);
    prepareQueue.add(item);	  
    //next();
  }
	  
  public void next(){
    if(getState() != STATE_IDLE || prepareQueue.size() == 0)
      return;
    startPreparing();
  }
	  
  private void startPreparing(){
	setState(STATE_DO_WORK);  
    startPrepareTime = System.currentTimeMillis();
    
  }
	  
  private void finishPreparing(){
    setState(STATE_IDLE);
    Item item = prepareQueue.elementAt(0);
    //receiveItemToQueue(item);
    
    prepareQueue.remove(0);
    listener.onFinishPreparingItem(item);
    //next();
    
  }
  
  public void removeItem(Item item){
    int index = prepareQueue.indexOf(item);	  
    if(index != -1){
      prepareQueue.remove(item);
      if(index == 0){
    	setState(STATE_IDLE);
        //next();        
      }	
    }else{
      removeItemFromQueue(item);	
    }
  }
  
 /* public void removeItemFromQueue(Item item){
    for(int i = 0;i < preparedItem.length;i++){
      if(preparedItem[i] != null && preparedItem[i].equals(item)){
    	//preparedItem[i].setVisible(false);
        preparedItem[i] = null;     
        next();
        break;
      }	
    }	  
  }*/
  
  private float[][] offset = {
    {238, 108},		  
    {204, 121},
    {174, 132},
    {138, 144},
    {105, 169},
  };
  
  @Override
  public void setItemPosition(Item item, int prepareQueue) {
	Log.d("RokejitsX", "prepareQueue = "+prepareQueue);  
	float[] off = offset[prepareQueue];
	item.setPosition(/*getX() + */off[0] - item.getWidth()/2, /*getY() + */off[1] - item.getHeight()/2);			
  }
  
  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {
	switch(getState()){
	  case STATE_DO_WORK:
	    if(System.currentTimeMillis() > startPrepareTime + 5000){
	      finishPreparing(); 	
	    }
	  break;
	}
	super.onManagedUpdate(pSecondsElapsed);	
  }

  
  
  
  
}
