package com.rokejitsx.data.xml;

import android.util.Log;

import com.rokejitsx.util.StringUtil;

public class DataHolder {

  private String[] keys;
  private String tagName;
  private Object[] values;
  
  public DataHolder(String tagName, String[] keys){
    this.keys = keys;
    this.tagName = tagName;
    values = new Object[keys.length];
  }
  
  public String getTagName(){
    return tagName;	  
  }
  
  public String getString(String key){
    return (String)get(key);	  
  }
  
  public boolean getBoolean(String key){
	Object value = get(key);
	if(value instanceof String)
	  return Boolean.parseBoolean((String)value);
    return ((Boolean)value).booleanValue();	  
  }
  
  public byte getByte(String key){		
    Object value = get(key);
	if(value instanceof String)
      return Byte.parseByte((String)value);
    return ((Byte)value).byteValue(); 	  
  }
  
  public short getShort(String key){		
    Object value = get(key);
    if(value instanceof String)
      return Short.parseShort((String)value);
    return ((Short)value).shortValue(); 	  
  }
  
  public int getInt(String key){		
    Object value = get(key);
	if(value instanceof String)
	  return Integer.parseInt((String)value);
    return ((Integer)value).intValue(); 	  
  }
  
  public long getLong(String key){		
    Object value = get(key);
	if(value instanceof String)
	  return Integer.parseInt((String)value);
    return ((Integer)value).intValue(); 	  
  }
  
  public float getFloat(String key){		
    Object value = get(key);
	if(value instanceof String)
	  return Float.parseFloat((String)value);
    return ((Float)value).floatValue(); 	  
  }
  
  public double getDouble(String key){		
    Object value = get(key);
	if(value instanceof String)
	  return Double.parseDouble((String)value);
    return ((Double)value).doubleValue(); 	   	  
  }
  
  public Object get(String key){
    int index = StringUtil.stringIndexInStringArray(key, keys);
    if(index == -1)
      return null;
    return values[index];
  }
  
  public void put(String key, byte value){
    put(key, new Byte(value));	  
  }  
  
  public void put(String key, boolean value){
    put(key, new Boolean(value));	  
  }
  
  public void put(String key, short value){
    put(key, new Short(value));	  
  }
  
  public void put(String key, int value){
    put(key, new Integer(value));	  
  }
  
  public void put(String key, long value){
    put(key, new Long(value));	  
  }
  
  public void put(String key, float value){
    put(key, new Float(value));	   
  }
  
  public void put(String key, double value){
    put(key, new Double(value));	  
  }  
  
  public void put(String key, Object value){
    int index = StringUtil.stringIndexInStringArray(key, keys);
    if(index == -1)
      return;
    values[index] = value;
  }
  
  public boolean isContainKey(String key){
    return StringUtil.stringIndexInStringArray(key, keys) != -1;	  
  }
  
  public void print(){
	Log.d("Rokejits", "DataHolder "+getClass());
    for(int i = 0;i < keys.length;i++){
      Log.d("Rokejits", keys[i] + " = " + values[i]);  	
    }	  
    Log.d("Rokejits", "End DataHolder "+getClass());
  }
  
  
}
