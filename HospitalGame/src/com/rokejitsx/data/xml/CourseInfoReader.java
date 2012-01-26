package com.rokejitsx.data.xml;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.util.Log;

import com.rokejitsx.R;
import com.rokejitsx.util.StringUtil;

public class CourseInfoReader extends TagXmlReader{
  private static final String[] tagList = {
    "Id",
    "BehaviorId",
    "StartHealth",
    "DamageAmount",
    "Machines",
    "H1", 
    "H2", 
    "H3", 
    "H4", 
    "H5", 
    "H6", 
    "H7"
    
  };
	
  private Vector<CourseInfo> courseInfoList;
  private CourseInfo courseInfo;
  public CourseInfoReader() {
	super(R.xml.courseinfo, tagList);
	courseInfoList = new Vector<CourseInfo>();
  }
  
  public CourseInfo[] getCourseInfoForHospital(int hospital,int level){
	Vector<CourseInfo> result = new Vector<CourseInfo>();
    Enumeration<CourseInfo> e = courseInfoList.elements();
    while(e.hasMoreElements()){
      CourseInfo info = e.nextElement();
      int percent = info.getPercent(hospital, level);
      if(percent != 0){
        result.add(info);	  
      }
    }
    if(result.size() == 0)
      return null;
    CourseInfo[] list = new CourseInfo[result.size()];
    result.copyInto(list);    
    return list;
    
  }

  @Override
  protected boolean parseTagNode(int index, String tagName) {
	  
	Log.d("RokejitsX", "CourseInfo read tagName = "+tagName); 
    if(index == -1){
      return true;	
    }	
    
    
    int value = -1;
    String strValue = null;
    try {
	  strValue = getStringValue();
	} catch (XmlPullParserException e1) {
	  // TODO Auto-generated catch block
	  e1.printStackTrace();
	} catch (IOException e1) {
	  // TODO Auto-generated catch block
	  e1.printStackTrace();
	}
    if(index < 4){      	    
	  value = parseInt(strValue);	  
    }
    
    switch(index){
      case 0://Id
        courseInfo = new CourseInfo(value);
        courseInfoList.add(courseInfo);
      break;
      case 1://BehaviorId
    	courseInfo.setBehaviorId(value);	  
      break;
      case 2://StartHealth
    	courseInfo.setStartHealth(value);	  
      break;
      case 3://DamageAmount
    	courseInfo.setDamageAmount(value);
      break;
      case 4://Machines
        courseInfo.setMachine(StringUtil.stringToIntArray(strValue));    	
      break;
      case 5://H1
        readHosptipalInfo(0);	  
      break;
      case 6://H2
    	readHosptipalInfo(1);	  
      break;
      case 7://H3
        readHosptipalInfo(2);
      break;
      case 8://H4
    	readHosptipalInfo(3);	  
      break;
      case 9://H5
    	readHosptipalInfo(4);  
      break;
      case 10://H6
    	readHosptipalInfo(5);	  
      break;
      case 11://H7
    	readHosptipalInfo(6);	  
      break;
    }
	  
	return true;
  }
  
  private void readHosptipalInfo(int hospitalId){
    String tagName = "H"+(hospitalId + 1);
    
    int eventType;
    int level = 0;
    try {
      Log.d("RokejitsX", "CourseInfo read level tagName0 = "+getName());	
      int[] data = StringUtil.stringToIntArray(getStringValue());
	  courseInfo.setHospitalInfo(hospitalId, level, data[0], data[1], data[2]); 
	  level++;
	  while(!((eventType = next()) == XmlResourceParser.END_TAG && getName().equals(tagName))){
		Log.d("RokejitsX", "CourseInfo read level tagName = "+getName());  
	    if(eventType == XmlResourceParser.START_TAG){
	      Log.d("RokejitsX", "CourseInfo read level tagName2 = "+getName()); 
	      data = StringUtil.stringToIntArray(getStringValue());
		  courseInfo.setHospitalInfo(hospitalId, level, data[0], data[1], data[2]); 
		  level++;
	    }
	  }
	} catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    
  }
  
  
  public class CourseInfo{
    private int id, behaviorId, startHealth, damageAmount;
    private int[] machines;
    private int[][][] hospitalData;
    
    public CourseInfo(int id){
      setId(id);	
      hospitalData = new int[7][9][3];
    }
    
    public void setId(int id){
      this.id = id;	
    }
    
    public int getId(){
      return id; 	
    }
    
    public void setBehaviorId(int id){
      this.behaviorId = id;	
    }
      
    public int getBehaviorId(){
      return behaviorId; 	
    }
    
    public void setStartHealth(int level){
      this.startHealth = level;	
    }
        
    public int getStartHealth(){
      return startHealth; 	
    }
    
    
    public void setDamageAmount(int damage){
      this.damageAmount = damage;	
    }
          
    public int getDamageAmount(){
      return damageAmount; 	
    }    
    
    public void setMachine(int[] list){
      this.machines = list;	
    }
    
    public int[] getMachineList(){
      return machines; 	
    }
    
    public void setHospitalInfo(int hospitalId, int level, int percent, int patientCount, int priority){
      int[]data = hospitalData[hospitalId][level];
      data[0] = percent;
      data[1] = patientCount;
      data[2] = priority;
    }
    
    public int getPercent(int hospitalId, int level){
      return hospitalData[hospitalId][level][0]; 	
    }
    
    public int getPatientCount(int hospitalId, int level){
      return hospitalData[hospitalId][level][1]; 	
    }
    
    public int getPriority(int hospitalId, int level){
      return hospitalData[hospitalId][level][2];	
    }
    
    
  }

}