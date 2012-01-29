package com.rokejitsx.ui.building.transport;

import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.AnimationInfo;

public class Helicopter extends Transporter{
  public Helicopter() {
	super(HELICOPTER);
	AnimationInfo idleInfo =  ResourceManager.getInstance().getAnimationInfo(234);
	AnimationInfo startInfo = ResourceManager.getInstance().getAnimationInfo(235);
	AnimationInfo endInfo = new AnimationInfo("", "", startInfo.getFPS(), false, false, startInfo.getSequenceCount());
	int[] sequence = new int[startInfo.getSequenceCount()];
	int[] tmp = startInfo.getSequence();
	for(int i = 0;i < sequence.length;i++){
	  sequence[i] = tmp[tmp.length - (i + 1)];	
	}
	endInfo.setSequence(sequence);
	AnimationInfo moveInfo =  ResourceManager.getInstance().getAnimationInfo(236);
	
	//setIdleAnimationInfo(idleInfo);
	setCloseDoorAnimationInfo(startInfo);
	
	
	setOpenDoorAnimationInfo(endInfo);
	
	
	setMoveInAnimationInfo(moveInfo);
	setMoveOutAnimationInfo(moveInfo);
	setIdleTile(0);
	//setAlpha(1);
		
  }
  
  @Override
  public void onFocus() {
	//mainSprite.setCurrentTileIndex(6);
	// TODO Auto-generated method stub
    //super.onFocus();
  }
  
  @Override
  public void onUnFocus() {
	//mainSprite.setCurrentTileIndex(6);
	// TODO Auto-generated method stub
    //super.onFocus();
  }

}
