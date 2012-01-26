package com.rokejitsx.data.xml;

import java.io.IOException;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParserException;

import com.rokejitsx.R;
import com.rokejitsx.data.resource.ImageResource;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.util.StringUtil;

public class TransportInfoReader extends TagXmlReader implements ImageResource{

  private final static String[] tagList = {
    "ambulancePath",
    "helicopterPath",
    "ambulanceAnimation",
    "helicopterPath"
  };	
  
  private final static String[] transportList = {
    MONTAGE_HELICOPTER,
    MONTAGE_AMBULANCE
  };
  
  private final static int[][] frameList = {
    {3, 2},
    {4, 2}
  };

  
  private Hashtable<String, float[]> ambulaneRoute, helicopterRoute;
  private Hashtable<String, AnimationInfo> ambulanceAnimationList, helicopterAnimationList;
  
  public TransportInfoReader(){
    super(R.xml.transportinfo, tagList);	  
  }

  @Override
  protected boolean parseTagNode(int index, String tagName) {	
    if(index == -1)	
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
    switch(index){
      case 0:  //ambulancePath
        if(ambulaneRoute == null)
          ambulaneRoute = new Hashtable<String, float[]>();
        ambulaneRoute.put(info[0], new float[]{ parseFloat(info[1]), parseFloat(info[2])});  
      break;
      case 1:  //helicopterPath
        if(helicopterRoute == null)
          helicopterRoute = new Hashtable<String, float[]>();
        helicopterRoute.put(info[0], new float[]{ parseFloat(info[1]), parseFloat(info[2])});  
      break;
      case 2:  //ambulanceAnimation
      case 3:  //helicopterAnimation
        if(ambulanceAnimationList == null)
          ambulanceAnimationList = new Hashtable<String, AnimationInfo>();
        if(helicopterAnimationList == null)
          helicopterAnimationList = new Hashtable<String, AnimationInfo>();
        Hashtable<String, AnimationInfo> animationList;
        if(index == 2)
          animationList = ambulanceAnimationList;
        else
          animationList = helicopterAnimationList;
        int sequenceCount = parseInt(info[7]);
        AnimationInfo animation = new AnimationInfo(info[0], info[6], 
      		                                      parseFloat(info[3]), 
      		                                      false,
      		                                      false,
      		                                      sequenceCount);
        for(int i = 0;i < sequenceCount;i++){
          animation.addSequence(parseInt(info[8 + i])); 	  
        }
        animationList.put(info[0], animation);
      break;
    }
    return true;
  }
  
  public AnimationInfo getAmbulanceAnimationInfo(int id){
    return ambulanceAnimationList.get(""+id);	  
  }
  
  public AnimationInfo getHelicopterAnimationInfo(int id){
    return helicopterAnimationList.get(""+id);	  
  }
  
  public Hashtable<String, float[]> getTransportPath(int transportType){
    if(transportType == Building.AMBULANCE){
      return ambulaneRoute;	
    }else if(transportType == Building.HELICOPTER){
      return helicopterRoute;	
    }	  
    return null;
  }
  
  public int[] getFrameSize(String imgName){
    int index = StringUtil.stringIndexInStringArray(imgName, transportList);
    if(index == -1)
      return null;
    return frameList[index];
  }
  
	
}
