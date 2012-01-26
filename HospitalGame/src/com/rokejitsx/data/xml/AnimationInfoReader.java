package com.rokejitsx.data.xml;

import java.io.IOException;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParserException;

import android.graphics.PointF;

import com.rokejitsx.R;
import com.rokejitsx.util.StringUtil;

public class AnimationInfoReader extends XmlReader{

  private Hashtable<String, AnimationInfo> animationList;	
  private Hashtable<String, Hashtable<String, float[]>> frameLinkList;
  public AnimationInfoReader(){
    super(R.xml.animationinfo);	  
  }

  @Override
  protected boolean parse(String tagName) {
	if(!tagName.equalsIgnoreCase("animation") && !tagName.equalsIgnoreCase("framelink"))
	  return true;
	
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
    if(tagName.equals("animation")){
      if(animationList == null)
        animationList = new Hashtable<String, AnimationInfo>();      
      int sequenceCount = parseInt(info[8]);
      AnimationInfo animation = new AnimationInfo(info[0],info[4], 
    		                                      parseFloat(info[5]), 
    		                                      (parseInt(info[6]) == 1),
    		                                      (parseInt(info[7]) == 1),
    		                                      sequenceCount);
      for(int i = 0;i < sequenceCount;i++){
        animation.addSequence(parseInt(info[9 + i])); 	  
      }
      animationList.put(info[0], animation);
    }else if(tagName.equalsIgnoreCase("framelink")){
      if(frameLinkList == null)
        frameLinkList = new Hashtable<String, Hashtable<String, float[]>>();
      
      String animationId = info[0];
      String frameId = info[1];
      float x = parseFloat(info[2]);
      float y = parseFloat(info[3]);
      Hashtable<String, float[]> table = frameLinkList.get(animationId);
      if(table == null){
        table = new Hashtable<String, float[]>();
        frameLinkList.put(animationId, table);
      }
      
      table.put(frameId, new float[]{x, y});
      
        
    }	
    return true;
  }	
  
  public AnimationInfo getAnimation(int id){
    return animationList.get("" + id);	  
  }
  
  public Hashtable<String, float[]> getFrameLink(int animationId){
    return getFrameLink(""+animationId);	  
  }
  
  public Hashtable<String, float[]> getFrameLink(String animationId){
    return frameLinkList.get(animationId);	  
  }
	
  
  
}
