package com.rokejitsx.ui.building.transport;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.AnimationInfo;

public class Ambulance extends Transporter {
  public Ambulance(){
    super(AMBULANCE);
    AnimationInfo moveInfo = ResourceManager.getInstance().getAmbulanceAnimationInfo(0);
    moveInfo.setDoLoop(true);
    AnimationInfo openDoorInfo = ResourceManager.getInstance().getAmbulanceAnimationInfo(1);
    openDoorInfo.setDoLoop(false);
    AnimationInfo closeDoorInfo = openDoorInfo.deepCopy();
    closeDoorInfo.setDoLoop(false);
    closeDoorInfo.setSequence(new int[]{5, 4});
    
    /*AnimationInfo idleAnimInfo = new AnimationInfo(MONTAGE_AMBULANCE, 0, false, false, 1);
    idleAnimInfo.addSequence(5);    
    setIdleAnimationInfo(idleAnimInfo);*/
    setFocusTileIndex(6);
    setMoveInAnimationInfo(moveInfo);
    setMoveOutAnimationInfo(moveInfo);    
    setOpenDoorAnimationInfo(openDoorInfo);
    setCloseDoorAnimationInfo(closeDoorInfo);
    setIdleTile(5);
  }

  

  /*@Override
  public void onFocus() {
	mainSprite.setCurrentTileIndex(6);
	// TODO Auto-generated method stub
    //super.onFocus();
  }*/

  

  
  
  
  
  
}
