package com.rokejitsx.data.route;

import java.util.Enumeration;
import java.util.Vector;

public class RouteManager {
  private Vector<Route> routes;
  private Vector<int[]> connections;

  /*public static RouteManager self;
  
  public static RouteManager getInstance(){
    if(self == null)
      self = new RouteManager();
    return self;
  }*/
  
  public RouteManager(){
    initRoute();	  
  } 
  
  public void initRoute(){
    routes = new Vector<Route>();
    connections = new Vector<int[]>();	  
  }
  
  public void addRoute(float x, float y){
	Route route = new Route(routes.size());
	route.setPosition(x, y);
    routes.add(route);	  
  }
  
  public float getRouteX(int index){
    return routes.elementAt(index).getX();	  
  }
  
  public float getRouteY(int index){
    return routes.elementAt(index).getY();	  
  }
  
  public int getRouteCount(){
    return routes.size();	  
  }
  
  public int getConnectionCount(){
    return connections.size();	   
  }
  
  public int[] getConnection(int index){
    return connections.elementAt(index);	  
  }
  
  /*public PointF getRoute(int index){
    return routes.elementAt(index);	  
  }*/
  
  public void addConnection(int left, int right){    
	for(Enumeration<int[]> e = connections.elements(); e.hasMoreElements();){
	  int[] conn = e.nextElement();
	  if((conn[0] == left && conn[1] == right) ||
		 (conn[0] == right && conn[1] == left)){
	    return;	  
	  }
	}
    connections.add(new int[]{left, right});
  }
  
  public Vector<Integer> getConnectionWith(int index){    
    Vector<Integer> result = new Vector<Integer>();
    for(Enumeration<int[]> e = connections.elements(); e.hasMoreElements();){
      int[] conn = e.nextElement();
      if(conn[0] == index){
        result.add(conn[1]);	  
      }else if(conn[1] == index){
    	result.add(conn[0]);	  
      }
    }   
    return result;
  }	
  
  public void findPath(int from, int to, PathFinderListener listener){
    if(from == to){
      listener.onFindPath(null);
      return;
    }
	Route[] routeList = new Route[routes.size()];
	for(int i = 0;i < routeList.length; i++){
      routeList[i] = routes.elementAt(i).clone();    		
	}
    PathFinder finder = new PathFinder(this, routeList);
    finder.startFindPath(from, to, listener);
  }
  
}
