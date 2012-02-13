package com.rokejitsx.ui.hospital;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.util.HorizontalAlign;

import com.rokejitsx.data.GameFonts;
import com.rokejitsx.data.resource.ImageResource;
import com.rokejitsx.data.resource.ResourceManager;

public class HospitalTimer extends Rectangle implements ImageResource{

  private static final int TIMER_IDLE  = 0; 	
  private static final int TIMER_COUNT = 1;
  private static final int TIMER_PAUSE = 2;
	
  
  private int state = TIMER_IDLE;  
  private float time, countTime;  
  private HospitalTimerListener listener;
  //private ChangeableText timerText;
  private AnimatedSprite pin, board;
  public HospitalTimer(){	
	super(0, 0, 100, 100);
	pin = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(INTERFACE_CLOCK));
	board = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(INTERFACE_CLOCK));
	board.setCurrentTileIndex(1);
	setWidth(board.getBaseWidth());
	setHeight(board.getBaseHeight());
	attachChild(board);
	attachChild(pin);
	setColor(0, 0, 0, 0);
	/*pin.setRotation(180);
	pin.setRotationCenter(pin.getWidth()/2, pin.getHeight()/2);*/
	/*timerText = new ChangeableText(50, 50, GameFonts.getInstance().getFont(), "00", HorizontalAlign.CENTER, 20);
	attachChild(timerText);*/
	
  }
  
  public void setHospitalTimerListener(HospitalTimerListener listener){
    this.listener = listener;	   
  }
  
  public void reset(){
    countTime = 0;
    isRunningOut = false;
    updateTime();
  }
  
  public void setTime(float seconds){
    time = seconds;
    reset();    
  }
  
  public void start(){
	reset();
    state = TIMER_COUNT;	  
  }
  
  public void stop(){
	state = TIMER_IDLE;
    countTime = 0;	  
    updateTime();
  }
  
  public void pause(){
    state = TIMER_PAUSE;	  
  }
  
  public void resume(){
    state = TIMER_COUNT;	  
  }
  
  private void updateTime(){
	float rotate = (countTime / time) * 360;
	pin.setRotation(rotate);
	pin.setRotationCenter(pin.getWidth()/2, pin.getHeight()/2);
	//timerText.setText(""+ (int)countTime);	  
  }

  private boolean isRunningOut;
  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {	
    super.onManagedUpdate(pSecondsElapsed);
    if(state == TIMER_COUNT && time > 0){
      countTime += pSecondsElapsed;
      if(countTime >= time){
    	stop();    	
    	reset();
        listener.onTimeout();        
      }else if(countTime >= time * 3 / 4 && !isRunningOut){
        isRunningOut = true;
        listener.onTimeRunningOut();
      }
      updateTime();
      
    }
  }
  
  public interface HospitalTimerListener{
    public void onTimeout();	  
    public void onTimeRunningOut();
  }
	
	
}
