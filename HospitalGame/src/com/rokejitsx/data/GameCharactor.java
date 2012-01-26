package com.rokejitsx.data;

import java.util.Enumeration;
import java.util.Vector;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.IModifier.IModifierListener;

import android.graphics.PointF;
import android.util.FloatMath;

import com.rokejitsx.data.route.PathFinderListener;
import com.rokejitsx.data.route.Route;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.ui.patient.Patient;

public abstract class GameCharactor extends GameObject implements IModifierListener<IEntity>, PathFinderListener{
  public final static int FACE_DOWN_L 	= 0;	
  public final static int FACE_DOWN_R 	= 1;
  public final static int FACE_UP_L 	= 2;
  public final static int FACE_UP_R 	= 3;
	
	
  public static final int STATE_IDLE = 0;
  public static final int STATE_MOVE = 1;
  public static final int STATE_PREPARE = 2;
  
  private int state = STATE_IDLE;
  private int faceDirection = FACE_DOWN_L;
  private float speed = 100;  //pixelPS  
  private int pathIndex;
  private Vector<GameCharactorListener> gameCharactorListener;
  private GameCharatorFloorChangedListener floorChangeListener;
  private Vector<PointF> route;
  private Building currentBuilding;    
  
  private int destinationFloor;  
  
  
  public GameCharactor(String mainImgName){
    super(mainImgName);	  
  }
  public GameCharactor(float x, float y, float width, float height, String mainImgName){
    super(x, y, width, height, mainImgName);	  
  }
  
  public void setDestinationFloor(int floor){
    this.destinationFloor = floor;	  
  }
  
  public int getDestinationFloor(){
    return destinationFloor;	  
  }
  
  public int getGameCharactorState(){
    return state;	  
  }
  
  public void setGameCharactorState(int state){
    this.state = state;
    this.faceDirection = -1;
    onSetGameCharactorState(state);
  }
  
  protected void onSetGameCharactorState(int state){}
  
  
  
  private int currentPatientActionNode;
  public void setCurrentNode(int node){
    currentPatientActionNode = node;	  
  }
  
  public int getCurrentNode(){
    return currentPatientActionNode;	  
  }
  
  public void setCurrentBuilding(Building building){
    currentBuilding = building;
    if(building != null)
      setCurrentNode(building.getActionPatientnode());
  }
  
  public Building getCurrentBuilding(){
    return currentBuilding;	  
  }
  
  public int getCurrentRouteIndex(){
	if(this instanceof Patient)
	  return getCurrentBuilding().getActionPatientnode();
    return getCurrentBuilding().getActionnode();	  
  }  
  
  public void setSpeed(float speed){
    this.speed = speed;	  
  }
  
  public float getSpeed(){
    return speed;	  
  }
  
  public void setGameCharactorPosition(float x, float y){	
    setPosition(x - getWidth()/2, y - getHeight());    
  }
  
  private void moveTo(float x, float y){	
    float startX = getX();
	float startY = getY();
	float endX = x - getWidth()/2;
	float endY = y - getHeight();	
	float distanceX = Math.abs(startX - endX);	
	float distanceY = Math.abs(startY - endY);	
	float duration = (FloatMath.sqrt((distanceX * distanceX) + (distanceY * distanceY))) / getSpeed();		
	
        
    int face;
    if(startX >= endX){
      if(startY >= endY)
        face = FACE_UP_L;
      else
    	face = FACE_DOWN_L;
    }else{
      if(startY >= endY)
        face = FACE_UP_R;
      else
      	face = FACE_DOWN_R;   	
    }
    if(faceDirection != face){
      faceDirection = face;	
      onSetFace(face);
    }
    
    MoveModifier movementModifier = new MoveModifier(duration, startX, endX, startY, endY);
	movementModifier.setRemoveWhenFinished(true);	
    movementModifier.addModifierListener(this);    
    registerEntityModifier(movementModifier);
  }
  
  
  
  public abstract void onSetFace(int face);
  public void setPositionOnBuildingReceived(float x, float y){}
  
  @Override
  public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
	// TODO Auto-generated method stub
		
  }

  @Override
  public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {	  
    if(hasMoreRoute()){
      nextMove();
    }else{
      onGameCharactorPathFinished();	
    }  		 
  }
  
  
  
  
  @Override
  public void setCurrentFloor(int floor) {
	if(floor == getCurrentFloor())
	  return;
	super.setCurrentFloor(floor);
	if(floorChangeListener != null)
	  floorChangeListener.onGameCharactorFloorChanged(this, floor);
  }

public void setGameCharactorFloorChangeListener(GameCharatorFloorChangedListener floorChangeListener){
    this.floorChangeListener = floorChangeListener;	  
  }
  
  public void addGameCharactorListener(GameCharactorListener listener){
    if(listener == null)
      return;
    if(gameCharactorListener == null)
      gameCharactorListener = new Vector<GameCharactorListener>();
    gameCharactorListener.add(listener);
  }
  
  public void removeGameCharactorListener(GameCharactorListener listener){
    if(gameCharactorListener == null)
      return;
    gameCharactorListener.remove(listener);
  }
  
  private void onGameCharactorPathFinished(){
	setGameCharactorState(STATE_IDLE);
    if(gameCharactorListener == null)
      return;
    for(Enumeration<GameCharactorListener> e = gameCharactorListener.elements();e.hasMoreElements();){
      GameCharactorListener listener = e.nextElement();
      listener.onGameCharactorPathFinished(this);
    }
  }
  
  private void nextMove(){
    PointF position = nextRoute();
    moveTo(position.x,position.y);	  
  }
  
  private PointF nextRoute(){    
    return route.elementAt(pathIndex++);
  }
  
  private boolean hasMoreRoute(){
    return pathIndex < route.size();	  
  }
  
  public GameCharactor initRoute(){
	pathIndex = 0;
	route = new Vector<PointF>();
    return this;	    
  }
  
  public GameCharactor to(float x,float y){
    route.add(new PointF(x,y));	  
    return this;	  
  }
  
  public void startMove(){
	setGameCharactorState(STATE_MOVE);
    nextMove();	  
  } 
  
  @Override
  public void onFindPath(Vector<Route> routes) {
	if(routes == null){
	  onGameCharactorPathFinished();
	  return;
	}
    initRoute();
    for(int i = 1;i < routes.size();i++){
      Route route = routes.elementAt(i);
      to(route.getX(),route.getY());	
    }   
    startMove();	
  }
  	
}
