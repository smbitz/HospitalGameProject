package com.rokejitsx.menu;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;

import com.rokejitsx.HospitalGameActivity;

public abstract class MyMenuScene extends Scene{
  
  
  public MyMenuScene(){
	onLoadResource();
	setBackgroundEnabled(false);	  
	
  }
  
  protected abstract void onLoadResource();
  public abstract void unLoadResource();
  
  
  protected void showMenu(Scene childScene){
	HospitalGameActivity.getGameActivity().sendSetChildScene(this, childScene);
  }
  
  
}
