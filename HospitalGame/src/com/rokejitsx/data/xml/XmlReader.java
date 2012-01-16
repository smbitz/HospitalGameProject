package com.rokejitsx.data.xml;

import java.io.IOException;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;

import com.rokejitsx.HospitalGameActivity;

public abstract class XmlReader {
  /*protected final static String STRING   = String.class.toString();
  protected final static String INTEGER  = Integer.class.toString();
  protected final static String LONG     = Long.class.toString();
  protected final static String FLOAT    = Float.class.toString();
  protected final static String DOUBLE   = Double.class.toString();*/
	
	
  private XmlResourceParser parser;  
  public XmlReader(int id){
    parser = HospitalGameActivity.getGameActivity().getResources().getXml(id);  	  
  }	
  
  
  protected void put(Hashtable data, String key){    
    data.put(key, getText());    
  }
  
  protected int next() throws XmlPullParserException, IOException{
    parser.next();	 
    return parser.getEventType(); 
  }
  
  protected String getName(){
    return parser.getName();	  
  }
  
  protected String getText(){
    return parser.getText();	  
  }
  
  protected String getStringValue() throws XmlPullParserException, IOException{
    next();
    return getText();
  }
  
  protected int getIntValue() throws XmlPullParserException, IOException{
    next();
    return getInt();
  }
  
  protected long getLongValue() throws XmlPullParserException, IOException{
    next();
    return getLong();
  }
  protected float getFloatValue() throws XmlPullParserException, IOException{
    next();
    return getFloat();
  }
  protected Double getDoubleValue() throws XmlPullParserException, IOException{
	next();
    return getDouble();
  }
  
  protected int getInt(){
    return Integer.parseInt(getText());	  
  }
  
  protected long getLong(){
    return Long.parseLong(getText());	  
  }
  
  protected float getFloat() {
    return Float.parseFloat(getText());  	
  }
  
  protected double getDouble(){
    return Double.parseDouble(getText());	  
  }
  
  public void startParse() throws XmlPullParserException, IOException{
	int eventType;
    while((eventType = next()) != XmlResourceParser.END_DOCUMENT){
      if(eventType == XmlResourceParser.START_TAG || eventType == XmlResourceParser.END_TAG){
        if(!parse(getName(), eventType))
          break;
      }
    }  	  
  } 
  
  protected boolean parse(String tagName, int eventType){
    if(eventType == XmlResourceParser.END_TAG)
	  return true;
    return parse(tagName);
  }
  
  protected abstract boolean parse(String tagName);
  
  
  protected int parseInt(String string){
    return Integer.parseInt(string);	  
  }
  
  protected long parseLong(String string){
    return Long.parseLong(string);	  
  }
  
  protected float parseFloat(String string){
    return Float.parseFloat(string);	  
  }
  
  
  
}
