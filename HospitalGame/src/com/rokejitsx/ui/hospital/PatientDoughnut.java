package com.rokejitsx.ui.hospital;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.IModifier.IModifierListener;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

import android.util.FloatMath;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.GameObject;
import com.rokejitsx.data.resource.ImageResource;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.xml.AnimationInfo;
import com.rokejitsx.data.xml.PatientInfoReader.PatientHeadInfo;
import com.rokejitsx.ui.patient.Patient;

public class PatientDoughnut extends GameObject implements ImageResource, IModifierListener<IEntity>{
	
  
  private Patient patient;  
  private AnimatedSprite headSprite;  
  private int health = 15;
  private AnimationInfo doughnutAnimInfo, headAnimInfo;
  private MoveModifier moveModifier;
  
  public PatientDoughnut(Patient patient){
    super(0, 0, 0, 0, DOUGHNUT);
    this.patient = patient;
    PatientHeadInfo headInfo = patient.getHeadInfo();   
    headAnimInfo = ResourceManager.getInstance().getAnimationInfo(headInfo.getAnimationId(PatientHeadInfo.MAD_ID)).deepCopy();    
    doughnutAnimInfo = new AnimationInfo("", "", 1, false, true, 2);
    doughnutAnimInfo.setSequence(new int[]{0, 1});
    
    
    headSprite = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(Patient.headList[headInfo.getHeadId()]));    
    headSprite.setPosition(getWidth()/2 - headSprite.getBaseWidth()/2, getHeight()/2 - headSprite.getBaseHeight()/2);
    attachChild(headSprite);
    
    setAnimation(headSprite, headAnimInfo);
    setAnimation(mainSprite, doughnutAnimInfo);
    
  }
  
  public Patient getCurrentPatient(){
    return patient;	  
  }
  
  
  public void moveTo(float x, float y){
    if(this.getX() == x && this.getY() == y)
      return;
      
    float startX = getX();
  	float startY = getY();
  	float endX = x;
  	float endY = y;	
  	float distanceX = Math.abs(startX - endX);	
  	float distanceY = Math.abs(startY - endY);	
  	float duration = (FloatMath.sqrt((distanceX * distanceX) + (distanceY * distanceY))) / 200;
  	
  	if(moveModifier != null){
  	  unregisterEntityModifier(moveModifier);	
  	}
  	
  	moveModifier = new MoveModifier(duration, startX, endX, startY, endY);
  	moveModifier.addModifierListener(this);
  	moveModifier.setRemoveWhenFinished(true);
  	
  	registerEntityModifier(moveModifier); 	
  }
  
  
  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {	
	super.onManagedUpdate(pSecondsElapsed);
	
	if(patient.getHealthLevel() < health){
	  health -= 5;
	  doughnutAnimInfo.setFPS(doughnutAnimInfo.getFPS() * 2);
	  HospitalGameActivity.getGameActivity().runOnUpdateThread(new Runnable() {		
		@Override
		public void run() {
		  setAnimation(mainSprite, doughnutAnimInfo);  	
			
		}
	  });
	}
  }

  @Override
  public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
	//  TODO Auto-generated method stub
	
  }

  @Override
  public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
	moveModifier = null;	
  }

  
  
  
  
  
  
  
  
}
