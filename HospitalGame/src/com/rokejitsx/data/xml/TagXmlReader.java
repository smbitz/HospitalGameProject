package com.rokejitsx.data.xml;

import com.rokejitsx.util.StringUtil;

public abstract class TagXmlReader extends XmlReader{

  private String[] tagList;	
  public TagXmlReader(int id, String[] tagList){
    super(id);
    this.tagList = tagList;
  }
  @Override
  protected boolean parse(String tagName) {
	int index = StringUtil.stringIndexInStringArray(tagName, tagList);
	return parseTagNode(index, tagName);
	
  }
  
  protected abstract boolean parseTagNode(int index, String tagName);
}
