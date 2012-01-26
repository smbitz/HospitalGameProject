package com.rokejitsx.data.xml;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import android.graphics.PointF;

import com.rokejitsx.R;
import com.rokejitsx.util.StringUtil;

public class PatientInfoReader extends TagXmlReader{

  private final static String[] patientInfoKeys = {
    PatientInfo.BEHAVIOR_ID,
    PatientInfo.IDLE_ID,
    PatientInfo.JUMP_ID,
    PatientInfo.UP_L_ID,
    PatientInfo.UP_R_ID,
    PatientInfo.DOWN_L_ID,
    PatientInfo.DOWN_R_ID,
    PatientInfo.SIT_ID,
    PatientInfo.PRONE_ID,
    PatientInfo.GRAB_ID,
    PatientInfo.NECK_LINK_POINT,
    PatientInfo.ASS_LINK_POINT,
    PatientInfo.FEET_LINK_POINT,
    PatientInfo.GRAB_LINK_POINT,
    PatientInfo.WALK_SPEED		 
  };
  
	
  private final static String[] patientHeadInfoKeys = {
	PatientHeadInfo.IDLE_ID,
	PatientHeadInfo.JUMP_ID,
	PatientHeadInfo.MAD_ID,
	PatientHeadInfo.GRAB_ID,
	PatientHeadInfo.SICK_ID,
	PatientHeadInfo.SMOKE_ID,
	PatientHeadInfo.IDLE_UP_L_ID,
	PatientHeadInfo.IDLE_UP_R_ID,
	PatientHeadInfo.IDLE_DOWN_L_ID,
	PatientHeadInfo.IDLE_DOWN_R_ID,
	PatientHeadInfo.MAD_UP_L_ID,
	PatientHeadInfo.MAD_UP_R_ID,
	PatientHeadInfo.MAD_DOWN_L_ID,
	PatientHeadInfo.MAD_DOWN_R_ID,
	PatientHeadInfo.BALLOON_LINK_POINT,
	PatientHeadInfo.NECK_LINK_POINT,
	PatientHeadInfo.INFO_LINK_POINT,
	PatientHeadInfo.HEAD_LINK_POINT	
	};
	
  private final static String[] tagList = {
    "patientBalloon",
    "patientBody",
    "patientBodyIds",
    "patientBodyHeads",
    "patientHead",
    "patientHeadIds"
  };	
  private float balloonLinkpointX, balloonLinkpointY, balloonLinkpointPicX, balloonLinkpointPicY;
  
  
  private Hashtable<String, PatientInfo> patientInfoList;
  private Hashtable<String, PatientHeadInfo> headInfoList;
	
  public PatientInfoReader() {
	super(R.xml.patientinfo, tagList);
		
  }
  
  public PatientInfo getPatientInfo(int patientId){
    return patientInfoList.get("" + patientId);	  
  }
  
  public PatientHeadInfo getPatientHeadInfo(int headId){
    return headInfoList.get("" + headId);	  
  }

  @Override
  protected boolean parseTagNode(int index, String tagName) {
    if(index == -1)
      return true;
    if(patientInfoList == null)
      patientInfoList = new Hashtable<String, PatientInfo>(); 
    if(headInfoList == null)
      headInfoList = new Hashtable<String, PatientHeadInfo>();
    
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
    PatientInfo patientInfo = null;
    PatientHeadInfo headInfo = null;
    if(index != 0 && index <= 3){
      patientInfo = patientInfoList.get(info[0]);
      if(patientInfo == null)
        patientInfoList.put(info[0], patientInfo = new PatientInfo());	  
    }else if(index > 3){
      headInfo = headInfoList.get(info[0]);
      if(headInfo == null)
        headInfoList.put(info[0], headInfo = new PatientHeadInfo(parseInt(info[0])));	
    }
    int infoIndex = 1;
    switch(index){
      case 0: //patientBalloon
        balloonLinkpointX = parseFloat(info[0]);
        balloonLinkpointY = parseFloat(info[1]);
        balloonLinkpointPicX = parseFloat(info[2]); 
        balloonLinkpointPicY = parseFloat(info[3]);	  
      break;
      case 1: //patientBody
        patientInfo.setWalkSpeed(parseFloat(info[1]));
        patientInfo.setLinkPointPosition(patientInfo.NECK_LINK_POINT, parseFloat(info[2]), parseFloat(info[3]));
        patientInfo.setLinkPointPosition(patientInfo.ASS_LINK_POINT, parseFloat(info[4]), parseFloat(info[5]));
        patientInfo.setLinkPointPosition(patientInfo.FEET_LINK_POINT, parseFloat(info[6]), parseFloat(info[7]));
        patientInfo.setLinkPointPosition(patientInfo.GRAB_LINK_POINT, parseFloat(info[8]), parseFloat(info[9]));
      break;
      case 2: //patientBodyIds
    	infoIndex = 1;
        patientInfo.setAnimationId(patientInfo.BEHAVIOR_ID, parseInt(info[infoIndex++]));  	  
        patientInfo.setAnimationId(patientInfo.IDLE_ID, parseInt(info[infoIndex++]));
        patientInfo.setAnimationId(patientInfo.JUMP_ID, parseInt(info[infoIndex++]));
        patientInfo.setAnimationId(patientInfo.UP_L_ID, parseInt(info[infoIndex++]));
        patientInfo.setAnimationId(patientInfo.UP_R_ID, parseInt(info[infoIndex++]));
        patientInfo.setAnimationId(patientInfo.DOWN_L_ID, parseInt(info[infoIndex++]));
        patientInfo.setAnimationId(patientInfo.DOWN_R_ID, parseInt(info[infoIndex++]));
        patientInfo.setAnimationId(patientInfo.SIT_ID, parseInt(info[infoIndex++]));
        patientInfo.setAnimationId(patientInfo.PRONE_ID, parseInt(info[infoIndex++]));
        patientInfo.setAnimationId(patientInfo.GRAB_ID, parseInt(info[infoIndex++]));        
      break;      
      case 3: //patientBodyHeads
    	infoIndex = 1;
        int count = parseInt(info[infoIndex++]);
        patientInfo.setHeadIdCount(count);
        for(int i = 0;i < count;i++){
          patientInfo.setHeadId(i, parseInt(info[infoIndex++]));	
        }        
      break;
      case 4: //patientHead
    	infoIndex =1;
        headInfo.setLinkPointPosition(headInfo.BALLOON_LINK_POINT, parseFloat(info[infoIndex++]), parseFloat(info[infoIndex++]));	    
        headInfo.setLinkPointPosition(headInfo.NECK_LINK_POINT, parseFloat(info[infoIndex++]), parseFloat(info[infoIndex++]));
        headInfo.setLinkPointPosition(headInfo.INFO_LINK_POINT, parseFloat(info[infoIndex++]), parseFloat(info[infoIndex++]));
        headInfo.setLinkPointPosition(headInfo.HEAD_LINK_POINT, parseFloat(info[infoIndex++]), parseFloat(info[infoIndex++]));
      break;
      case 5: //patientHeadIds
        infoIndex = 1;
        headInfo.setAnimationId(headInfo.IDLE_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.JUMP_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.MAD_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.GRAB_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.SICK_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.SMOKE_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.IDLE_UP_L_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.IDLE_UP_R_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.IDLE_DOWN_L_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.IDLE_DOWN_R_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.MAD_UP_L_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.MAD_UP_R_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.MAD_DOWN_L_ID, parseInt(info[infoIndex++]));
        headInfo.setAnimationId(headInfo.MAD_DOWN_R_ID, parseInt(info[infoIndex++]));        
      break;
    }
    return true;
    
    
  }

  
  public class PatientInfo extends DataHolder{	
	public final static String BEHAVIOR_ID 	= "behaviorId";
	public final static String IDLE_ID 		= "idleId";
	public final static String JUMP_ID 		= "jumpId";
	public final static String UP_L_ID 		= "uplId";
	public final static String UP_R_ID 		= "uprId";
	public final static String DOWN_L_ID 	= "downlId";
	public final static String DOWN_R_ID 	= "downrId";
	public final static String SIT_ID 		= "sitId";
	public final static String PRONE_ID 	= "proneId";
	public final static String GRAB_ID 		= "grabId";
	public final static String NECK_LINK_POINT 		= "neckLinkPoint";
	public final static String ASS_LINK_POINT 		= "assLinkPoint";
	public final static String FEET_LINK_POINT 		= "feetLintPoint";
	public final static String GRAB_LINK_POINT 		= "grabLinkPoint";
	public final static String WALK_SPEED	 		= "walkSpeed";
	
	
	
	
	private int[] headId;
    private Random random;
    
    public PatientInfo(){
      super("", patientInfoKeys);
      random = new Random();
    }
    
    public void setHeadIdCount(int count){
      headId = new int[count];	
    }
    
    public void setHeadId(int index, int id){
      headId[index] = id;	
    }
    
    public int randomHeadId(){
      return headId[Math.abs(random.nextInt() % headId.length)];	
    }
    
    public void setAnimationId(String animationType, int id){
      put(animationType, id);	
    }
    
    public int getAnimationId(String animationType){
      return getInt(animationType);	
    }
    
    public void setWalkSpeed(float speed){
      put(WALK_SPEED, speed);	
    }
    
    public float getWalkSpeed(){
      return getFloat(WALK_SPEED); 	
    }
    
    public void setLinkPointPosition(String linkPointType, float x, float y){
      put(linkPointType, new float[]{x, y});  
    }
    
    public float[] getLinkPointPosition(String linkPointType){
      return (float[]) get(linkPointType);	
    }    
  }
  
  public class PatientHeadInfo extends DataHolder{	
	public final static String IDLE_ID 			= "idleId";
	public final static String JUMP_ID 			= "jumpId";
	public final static String MAD_ID 			= "madId";
	public final static String GRAB_ID 			= "grabId";
	public final static String SICK_ID 			= "sickId";
	public final static String SMOKE_ID 		= "smokeId";
	public final static String IDLE_UP_L_ID 	= "idleuplId";
	public final static String IDLE_UP_R_ID 	= "idleuprId";
	public final static String IDLE_DOWN_L_ID 	= "ideldownlId";
	public final static String IDLE_DOWN_R_ID 	= "idledownrId";
	public final static String MAD_UP_L_ID 		= "maduplId";
	public final static String MAD_UP_R_ID 		= "maduprId";
	public final static String MAD_DOWN_L_ID 	= "maddownlId";
	public final static String MAD_DOWN_R_ID 	= "maddownrId";
	
	public final static String BALLOON_LINK_POINT 	= "balloonLinkPoint";
	public final static String NECK_LINK_POINT 		= "neckLinkPoint";
	public final static String INFO_LINK_POINT 		= "infoLinkPoint";
	public final static String HEAD_LINK_POINT 		= "headLinkPoint";
	
	private int headId;
	public PatientHeadInfo(int id) {
	  super("", patientHeadInfoKeys);
	  headId = id;
	}
	
	public int getHeadId(){
      return headId;		
	}
	
	public void setAnimationId(String animationType, int id){
      put(animationType, id);	
    }
	    
    public int getAnimationId(String animationType){
      return getInt(animationType);	
    }
	
    public void setLinkPointPosition(String linkPointType, float x, float y){
      put(linkPointType, new float[]{x, y});  
    }
      
    public float[] getLinkPointPosition(String linkPointType){
      return (float[]) get(linkPointType);	
    }
  }

  public int[] getFrameSize(String imgName) {
    if(imgName.indexOf("body") != -1){
      return new int[]{4, 3};	  
    }else if(imgName.indexOf("walk") != -1){
      return new int[]{4, 4};	  
    }else if(imgName.indexOf("cured") != -1){
      return new int[]{2, 2};	  
    }else if(imgName.indexOf("head") != -1){
	  return new int[]{5, 3};	  
    }	
  return null;
}
	
	
}
