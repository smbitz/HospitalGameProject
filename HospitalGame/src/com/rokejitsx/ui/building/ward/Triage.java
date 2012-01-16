package com.rokejitsx.ui.building.ward;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.ui.patient.Patient;

public class Triage extends Ward{
  	
  private final static String[][] LEVEL_IMG_NAME_LIST = {
    {       
      MONTAGE_MESATRIAGEM_1	
    },
    {       
      MONTAGE_MESATRIAGEM_2	
    },
    {       
      MONTAGE_MESATRIAGEM_3	
    },
    {       
      MONTAGE_MESATRIAGEM_4	
    },
    {       
      MONTAGE_MESATRIAGEM_5
    },
    {       
      MONTAGE_MESATRIAGEM_6
    },
    {       
      MONTAGE_MESATRIAGEM_7	
    }
  };	
  private AnimatedSprite counterSprite;
  private String counterImgName;
  private int hospitalLevel;
  public Triage(int hospitalLevel){
	super(TRIAGE, 1);
	String[] list = LEVEL_IMG_NAME_LIST[hospitalLevel];    
    counterImgName = list[0];
    counterSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(counterImgName));
    setWidth(counterSprite.getBaseWidth());
    setHeight(counterSprite.getBaseHeight());
    
    mainSprite.setPosition(40, -9);
	attachChild(counterSprite);
	attachChild(mainSprite);
	//mainSprite.setColor(1, 0, 0);
	
	this.hospitalLevel = hospitalLevel;
    //setOperationTime(6375);       
    
    
    setIdleAnimationId(13);
    setHealingAnimationId(14);
    setState(STATE_IDLE);
    
	
    
  }
  
  
  
  @Override
  public void onFocus() {
	counterSprite.setCurrentTileIndex(1);
	super.onFocus();
  }



  @Override
  public void onUnFocus() {
	counterSprite.setCurrentTileIndex(0);
	//super.onUnFocus();
  }



@Override
  public Shape onInitialBody(AnimatedSprite mainSprite) {
		
	return null;
  }
  
  
  /*@Override
  protected void initLinkedObjectPosition(){    
    Enumeration<LinkedObject> e = getObjectInfo().getLinkedObjectList().elements();
    while(e.hasMoreElements()){
      LinkedObject linkObject = e.nextElement();
      if(linkObject.getType() != hospitalLevel)
        continue;
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
  }*/
  
 /* @Override
  protected AnimatedSprite getLinkedObjectSprite(String imgName){
    if(imgName.equals(counterImgName)){
      return counterSprite;	
    }	  
    return null;
  }*/

  @Override
  public void onStartHealing() {
    	  
  }

  @Override
  public void onHealing(float pSecondsElapsed) {}

  @Override
  public void onfinishHealing() {}

  @Override
  public void onWardReceivePatient(Patient patient) {	
    patient.setPosition(getX() + getWidth() - patient.getWidth(), getY() + getHeight() - patient.getHeight());	
    patient.idle();
  }
  
  @Override
  public void onPatientCallback(Patient patient) {
	onWardReceivePatient(patient);	
  }
  
  @Override
  public void onWardRemovePatient(Patient patient) {}
 

  

  @Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {}
  
  /*@Override
  public GameObject isBuildingContain(float x, float y) {	
	return null;
  }*/

	
}
