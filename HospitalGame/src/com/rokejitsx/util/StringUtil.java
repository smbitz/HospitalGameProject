package com.rokejitsx.util;

import java.util.Vector;

public class StringUtil {
   
	
  public static boolean hasSameStartWith(String source1, String source2, String find){
    return (source1.indexOf(find) != -1) && (source2.indexOf(find) != -1); 	  
  }	
  public static String replace(String source, String find, String replace){
    
    int index = 0;
    int lastIndex = index;
    StringBuffer buff = new StringBuffer(); 
    while((index = source.indexOf(find, lastIndex)) != -1){
      buff.append(source.substring(lastIndex, index));
      buff.append(replace);
      lastIndex = index + find.length();
    }
    buff.append(source.substring(lastIndex, source.length()));
    return buff.toString();
  }	
	
  public static int stringIndexInStringArray(String str,String[] strArray){
    for(int i = 0;i < strArray.length;i++){
      if(str.equalsIgnoreCase(strArray[i])){
        return i;
      }
    }
    return -1;
  }
  
  public static int[] stringToIntArray(String source){
    return stringToIntArray(source, " ");	  
  }
  
  public static int[] stringToIntArray(String source, String delimeter){
    String[] strs = stringToStringArray(source, delimeter);
    int[]datas = new int[strs.length];
    for(int i = 0;i < datas.length;i++){
      datas[i] = Integer.parseInt(strs[i]);	
    }
  	return datas;	  
	  
  }
  
  public static String[] stringToStringArray(String source){
    return stringToStringArray(source, " ");	  
  }
  
  public static String[] stringToStringArray(String source, String delimeter){
    /*Vector<String> result = new Vector<String>();
    int index = 0;
    int lastIndex = index;
    while((index = source.indexOf(delimeter, lastIndex)) != -1){
      result.addElement(source.substring(lastIndex, index));
      lastIndex = index + 1;
    }
    result.addElement(source.substring(lastIndex, source.length()));
  	String[] datas = new String[result.size()];
  	result.copyInto(datas);*/
  	return source.split(delimeter);
  }
  
}
