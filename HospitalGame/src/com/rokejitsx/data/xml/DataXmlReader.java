package com.rokejitsx.data.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;

public abstract class DataXmlReader extends XmlReader{

  public DataXmlReader(int id) {
	super(id);	
  }

  @Override
  protected boolean parse(String tagName){	
    DataHolder dHolder = parseData(tagName);
    if(dHolder != null)
	  try {
		parseDataNode(dHolder);
	  } catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
    return true;
  }
  
  protected abstract DataHolder parseData(String tagName);
  
  protected void parseDataNode(DataHolder dHolder) throws XmlPullParserException, IOException{		
    String tagName = dHolder.getTagName(); 
	    
    int eventType;
    while((eventType = next()) != XmlResourceParser.END_TAG || !getName().equals(tagName)){     
      
      if(eventType == XmlResourceParser.START_TAG){
    	String key = getName();    	
        if(dHolder.isContainKey(key)){
          eventType = next();
          
          if(eventType == XmlResourceParser.TEXT)
            dHolder.put(key, getText());
          else if(eventType == XmlResourceParser.START_TAG)
            parseDataChildNode(dHolder, key);
        }	  
      }        
    }    
    
  }
	  
  protected void parseDataChildNode(DataHolder dHolder, String key) throws XmlPullParserException, IOException{
	int eventType;
	StringBuffer buff = new StringBuffer();
    while((eventType = next()) != XmlResourceParser.END_TAG || !getName().equals(key)){
     if(eventType == XmlResourceParser.START_TAG)
       buff.append("<"+getName()+">");
     if(eventType == XmlResourceParser.TEXT)
       buff.append(getText());
     if(eventType == XmlResourceParser.END_TAG)
       buff.append("</"+getName()+">");
    }
    
    dHolder.put(key, buff.toString());
  }

}
