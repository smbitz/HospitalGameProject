package com.rokejitsx.data.loader;

import com.rokejitsx.data.resource.ResourceManager;

public class ResourceLoader extends Loader{

  public ResourceLoader(LoaderListener listener) {
 	super(listener, 100);	
  }

  @Override
  protected void onLoad() {
    ResourceManager.getInstance().init();
	
  }
  
  

}
