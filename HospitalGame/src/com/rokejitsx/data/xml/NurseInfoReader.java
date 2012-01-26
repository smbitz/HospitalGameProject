package com.rokejitsx.data.xml;

import java.io.IOException;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParserException;

import com.rokejitsx.R;
import com.rokejitsx.util.StringUtil;

public class NurseInfoReader extends XmlReader{
	
  private Hashtable<String, AnimationInfo> animationList;
  public NurseInfoReader() {
	super(R.xml.nurseinfo);	
  }

  @Override
  protected boolean parse(String tagName) {
    if(tagName.equals("nurseAnimation")){
      if(animationList == null)
        animationList = new Hashtable<String, AnimationInfo>();
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
      
      int sequenceCount = parseInt(info[7]);
      AnimationInfo animationInfo = new AnimationInfo(info[0], info[6], parseFloat(info[3]), parseInt(info[1]) == 1, true, sequenceCount);
      for(int i = 0;i < sequenceCount;i++){
        animationInfo.addSequence(parseInt(info[8 + i]));	  
      }		  
      		  
      animationList.put(info[0], animationInfo);
      
      
      
    }
    return true;
  }
  
  public AnimationInfo getAnimation(int id){
    return animationList.get("" + id);	  
  }
}
