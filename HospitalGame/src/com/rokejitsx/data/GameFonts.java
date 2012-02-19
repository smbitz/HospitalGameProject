package com.rokejitsx.data;

import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontManager;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

import android.graphics.Color;
import android.graphics.Typeface;

public class GameFonts {
	
  public static final int DEFALUT_BOLD_18_BLACK = 0;	
  public static final int DEFALUT_BOLD_18_WHITE = 1;
  
  
  private static final int MAX_FONT = 2;
	
  
  private static final Typeface[] FONT_TYPE_FACE = {
    Typeface.DEFAULT,	   
    Typeface.DEFAULT,
  };
  
  private static final int[] FONT_STYLE = {
    Typeface.BOLD,	   
	Typeface.BOLD,
  };
  
  private static final int[] FONT_SIZE = {
    18,	   
	18,
  };
  
  private static final int[] FONT_COLOR = {
	Color.BLACK,	   
    Color.WHITE,
  };
  
  private Font[] mFont;
  
  private static GameFonts self;
  
  
  public static GameFonts getInstance(){
    if(self == null)
      self = new GameFonts();
    return self;
  }
  
  
  public void loadFont(TextureManager textureManager, FontManager fontManager){
	mFont = new Font[MAX_FONT];
    BitmapTextureAtlas fontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    textureManager.loadTexture(fontTexture);
    for(int i = 0; i < mFont.length;i++){
     mFont[i] = new Font(fontTexture, Typeface.create(FONT_TYPE_FACE[i], FONT_STYLE[i]), FONT_SIZE[i], true, FONT_COLOR[i]);
     fontManager.loadFont(mFont[i]);
    }    
	
		
  }
  
  public Font getFont(int fontIndex){
    return mFont[fontIndex];	    
  }
	
}
