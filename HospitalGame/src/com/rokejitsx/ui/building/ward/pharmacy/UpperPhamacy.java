package com.rokejitsx.ui.building.ward.pharmacy;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import android.util.Log;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.item.Item;

public class UpperPhamacy extends Pharmacy{
	private final static String[][] LEVEL_IMG_NAME_LIST = {
	  {       
	     null,
	     null	
	  },
	  {
	     null,
	     null  	
	  },
	  {
	      SIGN_3_SHORT,
	      FARMACIA_MINI_BALCAO_3  	
	   },
	   {
	      SIGN_4_SHORT,
	      FARMACIA_MINI_BALCAO_4  	
	   },
	   {
	      SIGN_5_SHORT,
	      FARMACIA_MINI_BALCAO_5  	
	   },
	   {
	      SIGN_6_SHORT,
	      FARMACIA_MINI_BALCAO_6  	
	   },
	   {
	      SIGN_7_SHORT,
	      FARMACIA_MINI_BALCAO_7  	
	   }
	  };
  private AnimatedSprite counterSprite, signSprite, elevatorSprite;
  private String counterImgName, signImgName;
  
  public UpperPhamacy(int hospitalLevel) {
    super(UPPER_PHARMACY, hospitalLevel);	
    String[] list = LEVEL_IMG_NAME_LIST[hospitalLevel];
    signImgName = list[0];
    counterImgName = list[1];
    counterSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(counterImgName));
    signSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(signImgName));
    elevatorSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(MONTAGE_ELEVADORTINY));
    //mainSprite.setCurrentTileIndex(2);
    //counterSprite.setPosition(103, 43);    
    signSprite.setPosition(34/* - signSprite.getBaseWidth()/2*/, 84/* - signSprite.getBaseHeight()/2*/);
    elevatorSprite.setPosition(81, -60);    
        
   	attachChild(counterSprite);   	
   	attachChild(signSprite);
   	attachChild(elevatorSprite);
   	setWidth(counterSprite.getBaseWidth());
	setHeight(counterSprite.getBaseHeight());
	//setColor(1 ,1 , 1, 1);
    
    
  }
  
  private float[][] offset = {
    {121, 60},		  
    {99, 67},
    {72, 79},
    {43, 85},
    {18, 95},
  };
		  
  @Override
  public void setItemPosition(Item item, int prepareQueue) {
	float[] off = offset[prepareQueue];
	Log.d("RokejitsX", "prepareQueue = "+prepareQueue);  
    item.setPosition(getX() + off[0] - item.getWidth()/2, getY() + off[1] - item.getHeight()/2);		
  }
  
  @Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {	
   /* Rectangle body = new Rectangle(0, 0, 30 * 5, 50);
	body.setColor(1, 0, 1);*/
	
	return null;
  }
  
  

}
