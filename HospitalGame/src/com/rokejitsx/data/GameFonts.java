package com.rokejitsx.data;

import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontManager;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

import android.graphics.Color;
import android.graphics.Typeface;

public class GameFonts { 
  private Font mFont;
  private static GameFonts self;
  
  
  public static GameFonts getInstance(){
    if(self == null)
      self = new GameFonts();
    return self;
  }
  
  public void loadFont(TextureManager textureManager, FontManager fontManager){
    BitmapTextureAtlas fontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    this.mFont = new Font(fontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 18, true, Color.BLACK);
	textureManager.loadTexture(fontTexture);
	fontManager.loadFont(mFont);
  }
  
  public Font getFont(){
    return mFont;	    
  }
	
}
