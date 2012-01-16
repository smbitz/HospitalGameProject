package com.rokejitsx.data.xml;

import java.io.IOException;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;

import com.rokejitsx.R;
import com.rokejitsx.util.StringUtil;

public class AnimationInfoReader extends XmlReader{

  private Hashtable<String, AnimationInfo> animationList;	
  public AnimationInfoReader(){
    super(R.xml.animationinfo);	  
  }

  @Override
  protected boolean parse(String tagName) {	
    if(tagName.equals("animation")){
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
      /*this.imgName = info[4].toLowerCase();
      this.pDurationEach = (long) (1000 / parseFloat(info[5]));
      this.flip = (parseInt(info[6]) == 1);
      this.doLoop = (parseInt(info[7]) == 1);*/
      int sequenceCount = parseInt(info[8]);
      AnimationInfo animation = new AnimationInfo(info[4], 
    		                                      parseFloat(info[5]), 
    		                                      (parseInt(info[6]) == 1),
    		                                      (parseInt(info[7]) == 1),
    		                                      sequenceCount);
      for(int i = 0;i < sequenceCount;i++){
        animation.addSequence(parseInt(info[9 + i])); 	  
      }
      animationList.put(info[0], animation);
    }	
    return true;
  }	
  
  public AnimationInfo getAnimation(int id){
    return animationList.get("" + id);	  
  }
	
  
  
}
