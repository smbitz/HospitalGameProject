package com.rokejitsx.data.route;

import java.util.Vector;

import android.util.Log;


public class PathFinder implements Runnable {
  private Route fromRoute, toRoute;
  private Vector<Route> closeRoute;
  private Route[] routeList;
  private RouteManager routeManager;
  private PathFinderListener listener;
  
  
  public PathFinder(RouteManager routeManager, Route[] routeList){
    this.routeManager = routeManager;  
    this.routeList = routeList;
  }	
  
  public void startFindPath(int from, int to, PathFinderListener listener){
	this.listener = listener;
	this.fromRoute = routeList[from];
	this.toRoute = routeList[to];
    closeRoute = new Vector<Route>();    
    new Thread(this).start();
    
       
  }
  
  public void run(){
	Log.d("RokejitsX", "START FIND PATH============================================");  
    Vector<Route> result = find(fromRoute);
    Log.d("RokejitsX", "END FIND PATH============================================");
    listener.onFindPath(result);
  }
  
  
  private Vector<Route> find(Route route){
	//System.out.println("find "+route.getIndex());
	Log.d("RokejitsX", "find "+route.getIndex());
	if(isInClose(route))
	  return null;
	Log.d("RokejitsX", route.getIndex()+ "not in close");
	//System.out.println(route.getIndex()+ "not in close");
	addToClose(route);
	
	
	
    Vector<Integer> parentsIndex = routeManager.getConnectionWith(route.getIndex());
    Route[] parents = new Route[parentsIndex.size()];
    for(int i = 0;i < parentsIndex.size(); i++){
      Route child = routeList[parentsIndex.elementAt(i)];
      if(!child.hasParent())
        child.setParent(route);
      parents[i] = child;
    }
        
    
    
    Vector<Route> result = null;
    float distance = -1;
    for(int i = 0; i < parents.length; i++){
      Route child = parents[i];
      //System.out.println("child index = "+child.getIndex()+" of "+route.getIndex());
      if(child.equals(toRoute)){
        Vector<Route> path = new Vector<Route>();
        path.add(route);
    	path.add(child);
        return path;
      }
      if(!child.getParent().equals(route))
        continue;
      Vector<Route> childPath = find(child);
      if(childPath != null){
    	  
        childPath.add(0, route);
        float childDistance = calDistance(childPath);
        /*System.out.println("curDistance = "+distance);
        System.out.println("childDistance = "+childDistance);*/
        if(distance == -1 || childDistance < distance){
          result = childPath;
          distance = childDistance;
        }
      }
    }
    
    return result;
  }
  
  private float calDistance(Vector<Route> path){
    float distance = 0;
    for(int i = 0;i < path.size() - 1;i++){
      Route start = path.elementAt(i);  	
      Route end   = path.elementAt(i + 1);
      
      float distanceX = start.getX() - end.getX();
      float distanceY = start.getY() - end.getY();
      
      distance += Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));      
    }
    
    return distance;
  }
  
  private void addToClose(Route route){
    if(isInClose(route))
      return;
    closeRoute.add(route);
  }
  
 /* private void addToOpen(Vector<Route> parents){
    for(Enumeration<Route> e = parents.elements(); e.hasMoreElements();){
      addToOpen(e.nextElement());	
    }	  
  }
  
  private void addToOpen(Route route){
    if(isInOpen(route) || isInClose(route))
      return;
    openRoute.add(route);
  }*/
  
  private boolean isInClose(Route route){
    return closeRoute.contains(route);	  
  }
  
  /*public boolean isInOpen(Route route){
    return openRoute.contains(route);
    		
  }*/
  
  
}
