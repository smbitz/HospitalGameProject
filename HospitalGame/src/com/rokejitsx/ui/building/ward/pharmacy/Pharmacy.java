package com.rokejitsx.ui.building.ward.pharmacy;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import android.util.Log;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.item.Item;
import com.rokejitsx.ui.nurse.Nurse;

public class Pharmacy extends Building{
	
  
	
  protected Item[] preparedItem;
  protected int preparedItemNum;
  private PharmacyListener listener;
  
  
  private int hospitalLevel;
 // private float animX, animY;
  
  
  public Pharmacy(int id, int hospitalLevel) {
	super(id, 0);	  
	/*animX = 0; 
	animY = 0;*/
	this.hospitalLevel = hospitalLevel;        
        
	/*animX = -medicoSprite.getBaseWidth();
	animY = -medicoSprite.getBaseHeight();
	
	medicoSprite.setPosition(animX, animY);*/
	//setFloorPosition(medicoSprite.getBaseWidth(), medicoSprite.getBaseHeight());
	
	
	
	
	
	preparedItem = new Item[5];
	preparedItemNum = 0;
  }
  
 
  
 /* @Override
  protected void initLinkedObjectPosition(){    
	Vector<LinkedObject> linkList = getObjectInfo().getLinkedObjectList();
	LinkedObject[] list = new LinkedObject[linkList.size()];
    Enumeration<LinkedObject> e = getObjectInfo().getLinkedObjectList().elements();
    while(e.hasMoreElements()){
      LinkedObject linkObject = e.nextElement();
      list[linkObject.getType()] = linkObject;
      
      
      
      String imgName = linkObject.getImageName();
      float x = linkObject.getX();
      float y = linkObject.getY();
      //int[] frameSize = IMAGE_FRAME_LIST[StringUtil.stringIndexInStringArray(imgName, IMAGE_LIST)];
      Log.d("Rokejitsx", "linkedObject = "+imgName);
      Log.d("Rokejitsx", "floorX = "+floorX);
      Log.d("Rokejitsx", "floorY = "+floorY);
      Log.d("Rokejitsx", "x = "+x);
      Log.d("Rokejitsx", "y = "+y);
      
      AnimatedSprite sprite = getLinkedObjectSprite(imgName);      
      x = getFloorX() + x - sprite.getWidth();
      y = getFloorY() + y - sprite.getHeight();
      sprite.setPosition(x, y);       
    }
    
    for(int i = 0;i < list.length;i++){
      LinkedObject linkObject = list[i];
      String imgName = linkObject.getImageName();
      float x = linkObject.getX();
      float y = linkObject.getY();
      AnimatedSprite sprite = getLinkedObjectSprite(imgName);
      animX = animX + x;
      animY = animY + y;
      sprite.setPosition(animX, animY);
    }
  }*/
  
  /*@Override
  protected AnimatedSprite getLinkedObjectSprite(String imgName){	
    if(imgName.equals(MONTAGE_ESTANTE)){
      return shellSprite;  	
    }else if(imgName.equals(counterImgName)){
      return counterSprite;  	
    }else if(imgName.equals(signImgName)){
      return signSprite;  	
    }else if(imgName.equals(MONTAGE_ELEVADORTINY)){
      return elevatorSprite;  	
    }    
    return null;
  }*/
  
  public void setPharmacyListener(PharmacyListener listener){
    this.listener = listener;	  
  }  

  @Override
  public void setVisible(boolean pVisible){
    super.setVisible(pVisible);
    for(int i = 0;i < preparedItem.length;i++){
      if(preparedItem[i] != null){      	
        preparedItem[i].setVisible(pVisible);            
        
      }	
    }
  }
  
  public void removeItem(Item item){    
    removeItemFromQueue(item);    
  }
  
  public void receiveItemToQueue(Item item){
    for(int i = 0;i < preparedItem.length;i++){
      Log.d("RokejitsX", "setItemPosition0 = "+i);	
      if(preparedItem[i] == null){    	
        preparedItem[i] = item;
        preparedItem[i].setOwner(this);   
        if(this.isVisible())
          preparedItem[i].setVisible(true);
        Log.d("RokejitsX", "setItemPosition1 = "+i);
        setItemPosition(preparedItem[i], i);
        Log.d("RokejitsX", "setItemPosition2 = "+i);
        break;
      }	
    }	  
  }  
  
  public void removeItemFromQueue(Item item){
    for(int i = 0;i < preparedItem.length;i++){
      Log.d("RokejitsX", "removeItemFromQueue = "+preparedItem[i]);
      
      if(preparedItem[i] != null && preparedItem[i].equals(item)){
    	Log.d("RokejitsX", "removeItemFromQueue2 = "+preparedItem[i].equals(item));  
    	//preparedItem[i].setVisible(false);
        preparedItem[i] = null;    
  
        break;
      }	
    }	  
  }
  
  public void setItemPosition(Item item, int prepareQueue){
    item.setPosition(getX() + getWidth() - (prepareQueue * item.getWidth()), getY() + 60);	  
  }
  
  
  @Override
  public GameObject isBuildingContain(float x, float y) {
	for(int i = 0;i < preparedItem.length;i++){
      if(preparedItem[i] != null && preparedItem[i].contains(x, y)){
        return preparedItem[i];
      }	
    }
	return super.isBuildingContain(x, y);
  }  

  

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {	
	
    if(gameChar instanceof Nurse){
      Nurse nurse = (Nurse) gameChar;
      Item item = nurse.getItemToPick();
      Log.d("RokejitsX", "nurseItem = "+item);
      if(item == null)
        return;
      removeItemFromQueue(item);
      nurse.pickItem();
      listener.onNursePickedItem(item);
      
    }
  }



@Override
protected void setGameChatactorOnReceived(GameCharactor gameChar) {
	// TODO Auto-generated method stub
	
}
}
