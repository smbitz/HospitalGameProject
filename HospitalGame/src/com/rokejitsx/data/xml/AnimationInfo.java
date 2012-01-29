package com.rokejitsx.data.xml;

public class AnimationInfo {
	private String imgName;	
	private boolean flip, doLoop;
	private int[] sequenceList;
	private int sequenceIndex;
	private float fps;
	private String id;
	
    public AnimationInfo(String id, String imgName, float fps, boolean flip,boolean doLoop,int sequenceCount){
      this.id = id;
      this.imgName = imgName.toLowerCase();
      this.fps =  fps;
      this.flip = flip;
      this.doLoop = doLoop;      
      sequenceList = new int[sequenceCount];
      sequenceIndex = 0;
    }    
    
    public String getId(){
      return id;  	
    }
    
    public float getFPS(){
      return fps; 	
    }
    
    public void setFPS(float fps){
      this.fps = fps; 	
    }
    
    public void setDoLoop(boolean loop){
      doLoop = loop;	
    }
    
    public void setSequence(int[] list){
      sequenceList = list; 	
    }
    
    public void addSequence(int frame){
      sequenceList[sequenceIndex++] = frame;      
    }
    
    public long getEachFrameDuration(){
      return (long) (1000 / fps); 	
    }
    
    public long[] getEachFrameDurationList(){       
       long[] duration = new long[sequenceList.length];
       for(int i = 0;i < duration.length; i++){
         duration[i] = getEachFrameDuration();	   
       }
       return duration; 	
    }
    
    public int getSequenceCount(){
      return getSequence().length; 	
    }
    
    public int[] getSequence(){
      return sequenceList;	
    }
    
    public boolean isFlip(){
      return flip; 	
    }
    
    public int doLoop(){
      if(doLoop)
        return -1;
      return 0; 	
    }
    
    public AnimationInfo deepCopy(){
      AnimationInfo animInfo = new AnimationInfo(id, imgName, fps, flip, doLoop, getSequenceCount());
      animInfo.setSequence(getSequence());
      return animInfo;  	
    }
  }
   