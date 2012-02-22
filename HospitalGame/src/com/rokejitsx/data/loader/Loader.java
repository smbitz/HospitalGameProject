package com.rokejitsx.data.loader;

public abstract class Loader implements Runnable{
  private float progress;
  private LoaderListener listener;
  private long delayTime;
  
  public Loader(LoaderListener listener, long delayTime){
	this.listener = listener;
	this.delayTime = delayTime;
  }
  
  public void startLoad(){
    progress = 0;
    new Thread(this).start();
  }

  
  public void run(){
	if(delayTime > 0){
	  try{
	    Thread.sleep(delayTime);	  
	  }catch(Exception e){}	
	}
    onLoad();  	  
    listener.onLoadFinish(this);
  }	
  
  protected void setProgress(float progress){
    this.progress = progress;	  
  }
  
  public float getProgress(){
    return progress;	  
  }
  
  protected abstract void onLoad();
  
  
  
}
