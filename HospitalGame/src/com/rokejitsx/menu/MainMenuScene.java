package com.rokejitsx.menu;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.TextMenuItem;
import org.anddev.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

import android.graphics.Color;

import com.rokejitsx.HospitalGameActivity;

public class MainMenuScene extends MyMenuScene implements IOnMenuItemClickListener{	
  private static final int MENU_PLAY     = 0;	
  private static final int MENU_QUIT     = 1;  
  private static final int MENU_HOSPITAL1 = 4;
  private static final int MENU_HOSPITAL2 = 5;
  private static final int MENU_HOSPITAL3 = 6;
  private static final int MENU_HOSPITAL4 = 7;
  private static final int MENU_HOSPITAL5 = 8;
  private static final int MENU_HOSPITAL6 = 9;
  private static final int MENU_HOSPITAL7 = 10;	
	
  private MenuScene startMenu, hospitalMenu;  
  private MyMenuListener mListener;
  
  public MainMenuScene(MyMenuListener mListener){
	super();
	this.mListener = mListener;      
  }
  
  
  public void showStartMenu(){
	startMenu = initMenuScene(); 
	startMenu.addMenuItem(initMenuItem("PLAY", MENU_PLAY));
	startMenu.addMenuItem(initMenuItem("QUIT", MENU_QUIT));
	//startMenu.buildAnimations();  
    showMenu(startMenu);	  
  } 
  
  private void showHospitalMenu(){
    hospitalMenu = initMenuScene();
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 1", MENU_HOSPITAL1));
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 2", MENU_HOSPITAL2));
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 3", MENU_HOSPITAL3));
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 4", MENU_HOSPITAL4));
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 5", MENU_HOSPITAL5));
	hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 6", MENU_HOSPITAL6));
    hospitalMenu.addMenuItem(initMenuItem("HOSPITAL 7", MENU_HOSPITAL7));
			
	//hospitalMenu.buildAnimations();
	showMenu(hospitalMenu);
  } 
  
  @Override
  public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
		float pMenuItemLocalX, float pMenuItemLocalY) {
    switch(pMenuItem.getID()){
      case MENU_PLAY:
    	showHospitalMenu();  
        startMenu.back();
      break;      
      case MENU_QUIT:
        HospitalGameActivity.getGameActivity().finish();
      break;
      case MENU_HOSPITAL1:
      case MENU_HOSPITAL2:
      case MENU_HOSPITAL3:
      case MENU_HOSPITAL4:
      case MENU_HOSPITAL5:
      case MENU_HOSPITAL6:
      case MENU_HOSPITAL7:
    	int hospitalId = pMenuItem.getID() - 4;        
        mListener.onGamePlayLoad(hospitalId);
        hospitalMenu.back();
      break;
    }	
	return false;
  }
  
  public interface MyMenuListener{
    
    public void onGamePlayLoad(int hospitalId);
  }
  
}
