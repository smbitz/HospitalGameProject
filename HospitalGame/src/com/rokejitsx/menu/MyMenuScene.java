package com.rokejitsx.menu;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.scene.Scene;
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

public abstract class MyMenuScene extends Scene implements IOnMenuItemClickListener{
  private BitmapTextureAtlas mFontTexture;
  private Font mFont;
  
  public MyMenuScene(){	
    initFont();	    
	setBackgroundEnabled(false);	  
  }
  
  private void initFont(){
    /* Load Font/Textures. */
	HospitalGameActivity gameAct = HospitalGameActivity.getGameActivity();  
	this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	FontFactory.setAssetBasePath("font/");
	this.mFont = FontFactory.createFromAsset(this.mFontTexture, gameAct, "Plok.ttf", 48, true, Color.WHITE);
	gameAct.getEngine().getTextureManager().loadTexture(this.mFontTexture);
	gameAct.getFontManager().loadFont(this.mFont);  
				
  }
  
  protected MenuScene initMenuScene(){
	MenuScene menuScene = new MenuScene(HospitalGameActivity.getGameActivity().getCamera());    
	menuScene.setBackgroundEnabled(false);
    menuScene.setOnMenuItemClickListener(this);	  
    return menuScene;
  }
	  
  protected IMenuItem initMenuItem(String label, int id){
    IMenuItem menuItem = new ColorMenuItemDecorator(new TextMenuItem(id, mFont, label), 1.0f,0.0f,0.0f, 0.0f,0.0f,0.0f);
    menuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);    
    return menuItem;				  
  }
  
  protected void showMenu(Scene childScene){
	HospitalGameActivity.getGameActivity().sendSetChildScene(this, childScene);
  }
  
  
}
