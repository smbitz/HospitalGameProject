package com.rokejitsx.data.route;



public class Route {
  private float x,y;
  private Route parent;
  private int index;
  private float distanceFromParent;
  
  public Route(int index){
    setIndex(index);	  
  }	
  
  public void setDistance(float distance){
    this.distanceFromParent = distance;	  
  }
  
  public float getDistanceFromStart(){  
    if(hasParent())	  
      return distanceFromParent + parent.getDistanceFromStart();	
    return 0;
  }
  
  
  public Route setIndex(int index){
    this.index = index;	
    return this;
  }
  
  public int getIndex(){
    return index;	  
  }
  
  public void setParent(Route parent){    
    this.parent = parent;
  }
  
  public Route getParent(){
    return parent;	  
  }
  
  public boolean hasParent(){
    return parent != null;	  
  }
  
  public void setPosition(float x, float y){
    this.x = x;
    this.y = y;
  }
  
  public float getX(){
    return x;	  
  }
  
  public float getY(){
    return y;	  
  } 
  
  public Route clone(){
    Route route = new Route(getIndex());
    route.setPosition(getX(), getY());
    return route;
  }
  
}
