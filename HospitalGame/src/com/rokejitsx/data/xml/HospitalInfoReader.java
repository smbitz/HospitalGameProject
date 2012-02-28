package com.rokejitsx.data.xml;



import java.io.IOException;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.rokejitsx.R;
import com.rokejitsx.util.StringUtil;

public class HospitalInfoReader extends TagXmlReader{
	
  	
  private int stageNum, hospitalNum;
  private String imgFolder, infoFolder, hospitalImage;
  private Hashtable<String, String[]> hospitalInfoXml;
  private String[] hosPitalName;
  
  private final static String[] tagList = {
    "Stages",
    "Number",
    "ImagesFolder",
    "InfoFolder",
    "HospitalImage",    
    "Name"
  };
  
  private String[] xmlNameList = {
    "hospital_1_floor1.xml",
    "hospital_2_floor1.xml",
    "hospital_3_floor1.xml",
    "hospital_3_floor2.xml",
    "hospital_4_floor1.xml",
    "hospital_4_floor2.xml",
    "hospital_5_floor1.xml",
    "hospital_5_floor2.xml",
    "hospital_5_floor3.xml",
    "hospital_6_floor1.xml",
    "hospital_6_floor2.xml",
    "hospital_6_floor3.xml",
    "hospital_7_floor1.xml",
    "hospital_7_floor2.xml",
    "hospital_7_floor3.xml",
    "hospital_7_floor4.xml",
  };
  
  private int[] xmlIdList = {
    R.xml.hospital_1_floor1,		  
    R.xml.hospital_2_floor1,
    R.xml.hospital_3_floor1,
    R.xml.hospital_3_floor2,
    R.xml.hospital_4_floor1,
    R.xml.hospital_4_floor2,
    R.xml.hospital_5_floor1,
    R.xml.hospital_5_floor2,
    R.xml.hospital_5_floor3,
    R.xml.hospital_6_floor1,
    R.xml.hospital_6_floor2,
    R.xml.hospital_6_floor3,
    R.xml.hospital_7_floor1,
    R.xml.hospital_7_floor2,
    R.xml.hospital_7_floor3,
    R.xml.hospital_7_floor4,
  };
	
  public HospitalInfoReader(){
    super(R.xml.hospitals, tagList);
    hospitalInfoXml = new Hashtable<String, String[]>();
  }

  
  private int index = 0;
  @Override
  protected boolean parseTagNode(int index, String tagName){
	//Log.d("RokejitsX", "HospitalInfo ==================================== "+tagName);	 
	if(tagName.equals("HospitalInfo"))
	  return true;
	try {  
      switch(index){
        case 0:
          stageNum = getIntValue();	
        break;
        case 1:
          hospitalNum = getIntValue();
          hosPitalName = new String[hospitalNum];
        break;
        case 2:
       	  imgFolder = getStringValue();
        break;
        case 3:
       	  infoFolder = getStringValue();
        break;
        case 4:
       	  hospitalImage = getStringValue();	
        break;   
        case 5:
         hosPitalName[this.index] = getStringValue();
         this.index++;
        break;
        default:
          next();//skip to <Levels>        
          int levelNum = getIntValue();
          next();//skip </Levels>
            
          String[] levelInfos = new String[levelNum];
          for(int i = 0;i < levelNum;i++){
            next();//skip to <Levelx>
            next();//skip to <info>
            levelInfos[i] = getStringValue().replace("-", "_");
            next();//skip </info>
            next();//skip </Levelx>          
          }
          hospitalInfoXml.put(tagName, levelInfos);	
        break;         
      }     
	} catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();		
    }  	
	return true;
  }
  
  public int[] getHospitalXml(int hospitalId){
	String[] xmlNames = hospitalInfoXml.get("Hospital"+(hospitalId + 1));
	int[] ids = new int[xmlNames.length];
	for(int i = 0;i < ids.length;i++){
	  ids[i] = xmlIdList[StringUtil.stringIndexInStringArray(xmlNames[i], xmlNameList)]; 	
	}
    return ids; 	  
  }
  
  public String getHospitalName(int hospitalId){
    return hosPitalName[hospitalId];	  
  }
  
}
