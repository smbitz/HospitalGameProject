package com.rokejitsx.ui.building.transport;

import java.util.Enumeration;
import java.util.Vector;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.IModifier.IModifierListener;

import android.graphics.PointF;
import android.util.FloatMath;
import android.util.Log;

import com.rokejitsx.data.GameCharactor;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.data.route.PathFinderListener;
import com.rokejitsx.data.route.Route;
import com.rokejitsx.data.route.RouteManager;
import com.rokejitsx.data.xml.AnimationInfo;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.patient.Patient;

public class Transporter extends Building implements PathFinderListener, IModifierListener<IEntity>, IAnimationListener{

  public static final int STATE_IDLE      	= 0;
  public static final int STATE_MOVE_IN	  	= 1;
  public static final int STATE_MOVE_OUT  	= 2;
  public static final int STATE_OPEN_DOOR 	= 3;
  public static final int STATE_CLOSE_DOOR 	= 4;
	  
  private int state = STATE_IDLE;
  private int speed = 300;  //pixelPS  
  private int pathIndex;
  private Vector<PointF> route;
  private RouteManager routeManager;
 // private Vector<TransportListener> transportListener;
  private float comeInTime = 0;
  
  private AnimationInfo idleAnimationInfo, moveOutAnimationInfo, moveInAnimationInfo, openDoorAnimationInfo, closeDoorAnimationInfo;
  
  public Transporter(int type){
    super(type, 1); 
    routeManager = ResourceManager.getInstance().getTransportPath(type);
   // transportListener = new Vector<TransportListener>();
    
    
    //comeIn();
    //goOut();
  }
  	
  
 /* public void addTransporterListener(TransportListener listener){ 
    if(transportListener.contains(listener))
      return;
    transportListener.add(listener);
  }
  */
  
  
  @Override
  public void onUnFocus() {	 
	setTransportState(STATE_IDLE);
	//super.onUnFocus();
  }
  
  protected void setIdleAnimationInfo(AnimationInfo animInfo){
    idleAnimationInfo = animInfo;	  
  }
  
  protected void setMoveInAnimationInfo(AnimationInfo animInfo){
	moveInAnimationInfo = animInfo;	  
  }
  
  protected void setMoveOutAnimationInfo(AnimationInfo animInfo){
    moveOutAnimationInfo = animInfo;	  
  }
  
  protected void setOpenDoorAnimationInfo(AnimationInfo animInfo){
	openDoorAnimationInfo = animInfo;	  
  }
  
  protected void setCloseDoorAnimationInfo(AnimationInfo animInfo){
	closeDoorAnimationInfo = animInfo;	  
  }
  
  public void comeIn(){
	setTransportState(STATE_MOVE_IN);
		  
  } 
  
  protected void goOut(){
    setTransportState(STATE_CLOSE_DOOR);
		  
  }
  
  public boolean receiveCharator(GameCharactor gameChar){
	Patient patient = (Patient) gameChar;
	patient.setVisible(false);
	if(patient.getPatientId() != 6)
	  return super.receiveCharator(gameChar);	
	if(gameChar.getCurrentBuilding() != null)
	  gameChar.getCurrentBuilding().removeCharactor(gameChar);
	if(this.getBuildingListener() != null)
	  this.getBuildingListener().onReceive(this, gameChar);
	
    return true;		  
  }
  
  @Override
  protected boolean onReceive(GameCharactor gameChar) {
	gameChar.setVisible(false);	
	setTransportState(STATE_CLOSE_DOOR);	
	return false;
  }
  
  private int idleTile;
  
  protected void setIdleTile(int index){
    idleTile = index;	  
  }
  
  protected void setTransportState(int state){
    this.state = state;
    switch(state){
      case STATE_MOVE_OUT:        
        routeManager.findPath(1, 2, this);
      break;
      case STATE_MOVE_IN:
    	if(hospitalFloor == getCurrentFloor()){  
    	  setVisible(true);
    	  //mainSprite.setVisible(true);
    	}
        routeManager.findPath(0, 1, this);
      break;
      case STATE_OPEN_DOOR:
        setAnimation(mainSprite, openDoorAnimationInfo, this);
      break;
      case STATE_CLOSE_DOOR:
        setAnimation(mainSprite, closeDoorAnimationInfo, this);	
      break;
      case STATE_IDLE:
    	mainSprite.stopAnimation(idleTile);
    	//goOut();
      break;
    }
  }
  
  public int getTransportState(){
    return state;	  
  }
  
  public Transporter initRoute(){
	pathIndex = 0;
	route = new Vector<PointF>();
    return this;	    
  }
  
  private void onTransportPathFinished(){
	mainSprite.stopAnimation();
	switch(getTransportState()){
	  case STATE_MOVE_OUT:
	    setVisible(false); 
	    comeInTime = 2;
	    /*for(Enumeration<TransportListener> e = transportListener.elements();e.hasMoreElements();){
	      TransportListener listener = e.nextElement();
	      listener.onSentPatientComplete(this);
	    }*/
	  break;
	  case STATE_MOVE_IN:
	    setTransportState(STATE_OPEN_DOOR);
		//goOut();
	  break;
	}
	
    
  }
  
  
  
  public Transporter to(float x,float y){
    route.add(new PointF(x,y));	  
    return this;	  
  }
  
  private void startMove(){
	AnimationInfo animInfo;	
	if(state == STATE_MOVE_IN)
	  animInfo = moveInAnimationInfo;
	else
	  animInfo = moveOutAnimationInfo;
	setAnimation(mainSprite, animInfo.getEachFrameDuration(), animInfo.getSequence(), -1, false);
    nextMove();	  
  } 
  
  private void nextMove(){
    PointF position = nextRoute();
    moveTo(position.x,position.y);	  
  }
  
  private PointF nextRoute(){    
    return route.elementAt(pathIndex++);
  }
  
  private void moveTo(float x, float y){	
	
	
    float startX = getX();
	float startY = getY();
	
	float endX = x - getWidth()/2;
	float endY = y - getHeight()/2;	
	
	float distanceX = Math.abs(startX - endX);	
	float distanceY = Math.abs(startY - endY);	
	float duration = (FloatMath.sqrt((distanceX * distanceX) + (distanceY * distanceY))) / getSpeed();		
	MoveModifier movementModifier = new MoveModifier(duration, startX, endX, startY, endY);
	movementModifier.setRemoveWhenFinished(true);	
    movementModifier.addModifierListener(this);
    registerEntityModifier(movementModifier);    
  }
  
  @Override
  public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
  	// TODO Auto-generated method stub
  	
  }


  @Override
  public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
    if(hasMoreRoute()){
      nextMove();
    }else{
      onTransportPathFinished();	
    } 		
  	
  }
  
  private boolean hasMoreRoute(){
    return pathIndex < route.size();	  
  }
  
  public int getSpeed(){
    return speed;	  
  }
  
  @Override
  public void onFindPath(Vector<Route> routes) {
	Log.d("RokejitsX", "Transporter findpath "+routes.size());
	if(routes == null){
	  onTransportPathFinished();
	  return;
	}
    initRoute();
    for(int i = 1;i < routes.size();i++){
      Route route = routes.elementAt(i);
      to(route.getX(),route.getY());	
    }   
    Route route = routes.elementAt(0);
    setPosition(route.getX() - getWidth()/2, route.getY() - getHeight()/2);
    startMove();	
  }
  
  
  
  
  @Override
  protected void onManagedUpdate(float pSecondsElapsed) {
	if(comeInTime > 0){
	  comeInTime -= pSecondsElapsed;
	  if(comeInTime < 0)
	    comeIn();
	}
	super.onManagedUpdate(pSecondsElapsed);
  }


@Override
  public void onGameCharactorPathFinished(GameCharactor gameChar) {
	  
  }


  @Override
  public void onAnimationEnd(AnimatedSprite pAnimatedSprite) {
	switch(getTransportState()){
	  case STATE_OPEN_DOOR:
	    //mainSprite.stopAnimation(5);
	    if(getCharactor(0) != null)
	      removeCharactor((GameCharactor) getCharactor(0));
	    setTransportState(STATE_IDLE);
	  break;
	  case STATE_CLOSE_DOOR:
	    setTransportState(STATE_MOVE_OUT);	  
	  break;
	}
	
  }


  @Override
  protected void setGameChatactorOnReceived(GameCharactor gameChar) {}



}
