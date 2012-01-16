package com.rokejitsx.data.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Log;

import com.rokejitsx.R;
import com.rokejitsx.util.StringUtil;

public class LevelInfoReader extends TagXmlReader{
	
	
  private final static String[] tagList = {
    "Time",
    "Objectives",
    "ExpertObjectives",
    "PatientCount",
    "WeatherIds",
    "MachineBreakCount",    
  };	
  private String hospital;
  private int level;
  private LevelInfo levelInfo;
	
  public LevelInfoReader(){
    super(R.xml.levels, tagList);	  
  }
  
  public LevelInfo readLevel(int hospitalNum, int level){
    hospital = "Hospital"+(hospitalNum + 1);
    this.level = level;
    try {
	  startParse();
	} catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return levelInfo;
  }
  
  @Override
  protected boolean parse(String tagName, int eventType) {	
  	if(tagName.equals(hospital) && eventType == XmlResourceParser.END_TAG){
  	  return false;  		
  	}  	
  	if(tagName.equals(hospital) && eventType == XmlResourceParser.START_TAG){
  	  levelInfo = new LevelInfo();
  	  return true;
  	}  	
  	
  	if(eventType == XmlResourceParser.END_TAG){
  	  return true;	
  	}
    return parse(tagName);	
  }
  
  @Override
  protected boolean parseTagNode(int index, String tagName){
	Log.d("RokejitsX", "LevelInfo parseTagNode = "+tagName);
	if(index == -1)
	  return true;
    if(levelInfo != null){
      String[] info = null; 	
      try {
		info = StringUtil.stringToStringArray(getStringValue());
	  } catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
      
      int value = (int) parseFloat(info[level]);
      
  	  switch(index){
  	    case 0://Time
  	      levelInfo.setTime(value);	
  	    break;
  	    case 1://Objective
  	      levelInfo.setObjective(value);  	    	
  	    break;
  	    case 2://ExpertObjective
  	      levelInfo.setExpertObjective(value);  	    	
  	    break;
  	    case 3://PatientCount
	      levelInfo.setPatientCount(value);  	    	
	    break;  	   		
  	    case 4://WeatherId
	      levelInfo.setWeatherId(value);  	    	
	    break;
  	    case 5://MachineBreakCount
	      levelInfo.setMachineBreakCount(value);  	    	
	    break;
  	  }
    }
	  
    return true;	  
	  
  }
  
  
  
  
  public class LevelInfo{
    private int time, objective, expertObjective, patientCount, weatherId, machineBreakCount;
    
    
    
    public void setTime(int time){
      this.time = time;	
    }
    
    public float getTime(){
      return time;	
    }
    
    public void setObjective(int objective){
      this.objective = objective;	
    }
    
    public int getObjective(){
      return objective; 	
    }
    
    public void setExpertObjective(int expertObjective){
      this.expertObjective = expertObjective;	
    }
      
    public int getExpertObjective(){
      return expertObjective; 	
    }
    
    public void setPatientCount(int patientCount){
      this.patientCount = patientCount;	
    }
        
    public int getPatientCount(){
      return patientCount; 	
    }
      
    public void setWeatherId(int weatherId){
      this.weatherId = weatherId;	
    }
          
    public int getWeatherId(){
      return weatherId; 	
    }
        
    public void setMachineBreakCount(int count){
      this.machineBreakCount = count;	
    }
            
    public int getMachineBreakCount(){
      return machineBreakCount; 	
    }
    
    
    
    
	   
  }





  
}
