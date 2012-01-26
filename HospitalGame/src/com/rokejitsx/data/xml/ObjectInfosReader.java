package com.rokejitsx.data.xml;



import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.rokejitsx.R;
import com.rokejitsx.ui.building.Building;
import com.rokejitsx.util.StringUtil;

public class ObjectInfosReader extends TagXmlReader{

	
  	
  private ObjectInfo[] objInfoList;
 
  
  private final static String[] tagList = {
    "objectLinkCount",		//0
    "objectLinkPoint",		//1
    "objectImg",			//2
    "objectCheckBox",		//3
    "bedInformationObject",	//4
    "LinkedObject",			//5
    "objectFloor"			//6
  };
  
  
  
  public ObjectInfosReader(){
    super(R.xml.object_info, tagList);
    objInfoList = new ObjectInfo[Building.MAX_BUILDING];
    
  }  

  @Override
  protected boolean parseTagNode(int index, String tagName) {
	//Log.d("RokejitsX", "tagName = "+tagName+"/"+index);
	if(tagName.equals("ObjectInfo"))
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
	
	int objectType = parseInt(info[0]);
	ObjectInfo objInfo = null;
	if(index == 4){
      objectType = Building.BED;  
	}
	objInfo = objInfoList[objectType];
	if(objInfo == null)
      objInfo = objInfoList[objectType] = new ObjectInfo();
	switch(index){
      case 0: //objectLinkCount    	
        objInfo.setObjectLinkCount(Integer.parseInt(info[1]));	  
      break;
      case 1: //objectLinkPoint
        int linkId = parseInt(info[1]);
        float x = parseFloat(info[2]);
        float y = parseFloat(info[3]);
    	
        objInfo.setObjectLinkPoint(linkId, x, y);	  
      break;
      case 2: //objectImg    	  
        objInfo.setObjectImage(info[5], parseInt(info[1]), parseInt(info[2]), parseInt(info[3]), parseInt(info[4]));	  
      break;
      case 3: //objectCheckBox
        objInfo.setObjectCheckBoxPosition(parseFloat(info[1]), parseFloat(info[2]));     	       
      break;
      case 4: //bedInformationObject
    	//Log.d("Rokejitsx", "bedInformationObject = "+info[7]);
        objInfo.addLinkedObject(parseInt(info[0]), 		// type
       		                    parseFloat(info[1]), 	// x
	                  		    parseFloat(info[2]), 	// y
	                  		    parseInt(info[3]), 		// frameCount
	                            parseInt(info[4]), 		// colNum        		                  
	                            parseInt(info[5]), 		// rowNum
	                            parseInt(info[6]), 		// init frame
	                            info[7]);     	    	// imgName  	
      break;
      case 5: //LinkedObject
        objInfo.addLinkedObject(parseInt(info[1]), 		// type
        		                parseFloat(info[2]), 	// x
        		                parseFloat(info[3]), 	// y
        		                parseInt(info[4]), 		// frameCount
        		                parseInt(info[5]), 		// colNum        		                  
        		                parseInt(info[6]), 		// rowNum
        		                0,						// init frame
        		                info[7]);     	    	// imgName   
      break;
      case 6: //objectFloor
        objInfo.setFloorPosition(parseFloat(info[1]), parseFloat(info[2]));      
      break;      
    } 
	return true;
  } 
  
  public int[] getFrameSize(String imgName){
	int[] result = null;
    if(objInfoList != null){
      for(int i = 0;i < objInfoList.length;i++){
        ObjectInfo info = objInfoList[i];
        if(info == null)
          continue;
        int[] frameSize = info.getFrameSize(imgName);
        if(frameSize != null){
          if(frameSize[0] <= 0)
            frameSize[0] = 1;
          if(frameSize[1] <= 0)
            frameSize[1] = 1;
          
          result = frameSize;	
        }
      }	
    }    
    return result;
  }
  
  public ObjectInfo getObjectInfo(int type){
    return objInfoList[type];	  
  }
  
  public class ObjectInfo {
    private float[][] objectLinkPoints;
    private String objectImg;
    private int frameCount, cellCountX, cellCountY, frame;   
    private float objChkBoxX, objChkBoxY, objFloorX, objFloorY;
    private Vector<LinkedObject> linkedObjectList;
    
    
    public String getImageName(){
      return objectImg;   	
    }
    
    public int[] getFrameSize(String imageName){
      //Log.d("RokejitsX", "getFrameSize = "+objectImg);
      if(objectImg.equals(imageName)){
        return new int[]{cellCountX, cellCountY};	  
      }
      if(linkedObjectList != null){
        Enumeration<LinkedObject> e = linkedObjectList.elements();
        while(e.hasMoreElements()){
          LinkedObject lObj = e.nextElement();
         // Log.d("RokejitsX", "lObj.getImageName() = "+lObj.getImageName());
          if(lObj.getImageName().equals(imageName)){
            return new int[]{lObj.getCellContX(), lObj.getCellCountY()};	  
          }
        }
      }
      return null;	
    }
    
    public void setObjectLinkCount(int count){
      objectLinkPoints = new float[count][2];	
    }
    
    public void setObjectLinkPoint(int index, float x, float y){
      float[] position = objectLinkPoints[index];
      position[0] = x;
      position[1] = y;
    }
    
    public void setObjectImage(String imgUrl, int frameCount, int cellCountX, int cellCountY,int frame){
      this.objectImg = imgUrl.toLowerCase();
      this.frameCount = frameCount;
      this.cellCountX = cellCountX;
      this.cellCountY = cellCountY;
      this.frame = frame;
      
    }
    
    public void setObjectCheckBoxPosition(float x, float y){
      objChkBoxX = x;
      objChkBoxY = y;
    }
    
    public void addLinkedObject(int type, float x, float y, int frameCount, int cellCountX, int cellCountY, int initFrame, String imgName){
      if(linkedObjectList == null)
        linkedObjectList = new Vector<LinkedObject>();
      linkedObjectList.addElement(new LinkedObject(type, x, y, frameCount, cellCountX, cellCountY, initFrame, imgName));
    }
    
    public Vector<LinkedObject> getLinkedObjectList(){
      return linkedObjectList; 	
    }
    
    public void setFloorPosition(float x, float y){
      objFloorX = x;
      objFloorY = y;
    }
    
    public float getFloorX(){
      return objFloorX;  	
    }
    
    public float getFloorY(){
      return objFloorY;  	
    }
    
  }
  
  public class LinkedObject{
	private int type, frameCount, cellCountX, cellCountY; 
	private float x, y;
	private String imgName;
	private int initFrame;
    public LinkedObject(int type, float x, float y, int frameCount, int cellCountX, int cellCountY, int initFrame, String imgName){
      this.type = type;
      this.x = x;
      this.y = y;
      this.frameCount = frameCount;
      this.cellCountX = cellCountX;
      this.cellCountY = cellCountY;
      this.initFrame = initFrame;
      this.imgName = imgName.toLowerCase();      
    }	  
    
    public int getType(){
      return type; 	
    }
    
    public float getX(){
      return x; 	
    }
    
    public float getY(){
      return y;	
    }
    
    public int getFrameCount(){
      return frameCount; 	
    }
    
    public int getCellContX(){
      return cellCountX; 	
    }
    
    public int getCellCountY(){
      return cellCountY; 	
    }
    
    public String getImageName(){
      return imgName; 	
    }
    
  }
}
