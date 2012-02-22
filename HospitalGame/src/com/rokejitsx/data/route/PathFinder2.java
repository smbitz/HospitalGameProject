package com.rokejitsx.data.route;

import java.util.Vector;

import android.util.Log;


public class PathFinder2 implements Runnable {
  private Route fromRoute, toRoute;
  private Vector<Route> closeRoute, openRoute;
  private Route[] routeList;
  private RouteManager routeManager;
  private PathFinderListener listener;
  
  
  public PathFinder2(RouteManager routeManager, Route[] routeList){
    this.routeManager = routeManager;  
    this.routeList = routeList;
  }	
  
  public void startFindPath(int from, int to, PathFinderListener listener){
	this.listener = listener;
	this.fromRoute = routeList[from];
	this.toRoute = routeList[to];
    closeRoute = new Vector<Route>();    
    openRoute = new Vector<Route>();
    
    new Thread(this).start();
    
       
  }
  
  public void run(){
	 if(fromRoute.equals(toRoute)){
       listener.onFindPath(null);		 
	 }
	
	Log.d("RokejitsX", "Find path from "+fromRoute.getIndex()+" to "+toRoute.getIndex());
	addToOpen(fromRoute);  
	while(openRoute.size() > 0){
	  Route route = openRoute.get(0);
	  Log.d("RokejitsX", "current index "+route.getIndex());
	  addToClose(route);
	  Vector<Integer> parentsIndex = routeManager.getConnectionWith(route.getIndex());
	  Log.d("RokejitsX", "parentsIndexSize "+parentsIndex.size());
	  //Route[] parents = new Route[parentsIndex.size()];
	  for(int j = 0;j < parentsIndex.size(); j++){
	    Route child = routeList[parentsIndex.elementAt(j)];
	    if(child.equals(fromRoute))
	      continue;
	    float distance = calDistance(route, child);
	    if(!child.hasParent()){
	      child.setParent(route);
	      child.setDistance(distance);
	    }else{	        
	      float oldDistance = child.getDistanceFromStart();
	      float newDistance = route.getDistanceFromStart() + distance;
	        
	      if(newDistance < oldDistance){
	        child.setParent(route);
	        child.setDistance(distance);
	      }
	    }
	    //parents[j] = child;
	    if(!isInClose(child))
	      addToOpen(child);
	  }
	}  
	
	Vector<Route> result = new Vector<Route>();
	
	Route route = toRoute;
	Log.d("RokejitsX", "toRoute.hasParent() "+toRoute.hasParent());
	while(route.hasParent()){
	  result.insertElementAt(route, 0);
	  route = route.getParent();
	}
	
	result.insertElementAt(fromRoute, 0);
	
    listener.onFindPath(result);
  }
  
  private float calDistance(Route start, Route end){      
    float distanceX = start.getX() - end.getX();
    float distanceY = start.getY() - end.getY();	      
	float distance = (float) Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));      
	    
	    
	return distance;	  
  }
  
  
  /*private float calDistance(Vector<Route> path){
    float distance = 0;
    for(int i = 0;i < path.size() - 1;i++){
      Route start = path.elementAt(i);  	
      Route end   = path.elementAt(i + 1);
      
      float distanceX = start.getX() - end.getX();
      float distanceY = start.getY() - end.getY();
      
      distance += Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));      
    }
    
    return distance;
  }*/
  
  private void addToClose(Route route){
    if(isInClose(route))
      return;
    if(isInOpen(route))
      removeFromOpen(route);
    closeRoute.add(route);
  }  
  
  private void removeFromOpen(Route route){
    if(!isInOpen(route))
      return;
    openRoute.remove(route);
  }
  
  private void addToOpen(Route route){
    if(isInOpen(route) || isInClose(route))
      return;
    openRoute.add(route);
  }
  
  private boolean isInClose(Route route){
    return closeRoute.contains(route);	  
  }
  
  public boolean isInOpen(Route route){
    return openRoute.contains(route);
    		
  }
  
  
}
