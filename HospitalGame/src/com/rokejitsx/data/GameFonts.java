package com.rokejitsx.data;

import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.font.FontManager;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

import com.rokejitsx.HospitalGameActivity;

import android.graphics.Color;
import android.graphics.Typeface;

public class GameFonts {
	
  public static final int DEFALUT_BOLD_18_BLACK = 0;	
  public static final int DEFALUT_BOLD_18_WHITE = 1;  
  public static final int DEFALUT_BOLD_20_BLUE  = 2;
  private static final int MAX_FONT = 3;
  
  public static final int MENU_LCD_FONT_20_WHITE   = 0;
  public static final int MENU_PLOK_FONT_18_RED    = 1;
  public static final int MENU_PLOK_FONT_20_BLUE   = 2;
  public static final int MENU_LCD_FONT_20_BLUE    = 3;
  public static final int MENU_PLOK_FONT_18_BLACK  = 4;
  
  private static final int MENU_MAX_FONT = 4;
	
  
  private static final Typeface[] FONT_TYPE_FACE = {
    Typeface.DEFAULT,	   
    Typeface.DEFAULT,
    Typeface.DEFAULT
  };
  
  private static final int[] FONT_STYLE = {
    Typeface.BOLD,	   
	Typeface.BOLD,
	Typeface.BOLD
  };
  
  private static final int[] FONT_SIZE = {
    18,	   
	18,
	20
  };
  
  private static final int[] FONT_COLOR = {
	Color.BLACK,	   
    Color.WHITE,
    Color.BLUE,
  };  
  
  private static final String[] MENU_FONT_TTF = {
    "LCD.ttf",
    "Plok.ttf",
    "Plok.ttf",
    "LCD.ttf",
  };
  
  private static final int[] MENU_FONT_SIZE = {
    20,
    18,
    20,
    20
  };
  
  private static final int[] MENU_FONT_COLOR = {	   
    Color.WHITE,
    Color.RED,
    Color.BLUE,
    Color.BLUE
  };
  
  
  private Font[] mFont;
  private Font[] menuFont;
  
  private static GameFonts self;
  
  
  public static GameFonts getInstance(){
    if(self == null)
      self = new GameFonts();
    return self;
  }
  
  
  private BitmapTextureAtlas fontTexture;
  private BitmapTextureAtlas[] menuFontTexture;
  
  public void loadFont(TextureManager textureManager, FontManager fontManager){
    mFont = new Font[MAX_FONT];
    fontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    textureManager.loadTexture(fontTexture);
    for(int i = 0; i < mFont.length;i++){
      mFont[i] = new Font(fontTexture, Typeface.create(FONT_TYPE_FACE[i], FONT_STYLE[i]), FONT_SIZE[i], true, FONT_COLOR[i]);
      fontManager.loadFont(mFont[i]);
    }	
    
    menuFont = new Font[MENU_MAX_FONT];
    menuFontTexture = new BitmapTextureAtlas[MENU_MAX_FONT];
    FontFactory.setAssetBasePath("font/");
    for(int i = 0;i < MENU_MAX_FONT;i++){
      BitmapTextureAtlas bTextureAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
	  Font font = FontFactory.createFromAsset(bTextureAtlas, HospitalGameActivity.getGameActivity(), MENU_FONT_TTF[i], MENU_FONT_SIZE[i], true, MENU_FONT_COLOR[i]);
	  textureManager.loadTexture(bTextureAtlas);
	  fontManager.loadFont(font);
	  menuFontTexture[i] = bTextureAtlas;
	  menuFont[i] = font;  
    }
    
    
    
  }
  
  public void unLoadAllFonts(){	
    fontTexture.clearTextureAtlasSources();
    for(int i = 0;i < MENU_MAX_FONT;i++){
      menuFontTexture[i].clearTextureAtlasSources();
    }
    mFont = null;
    menuFont = null;
    
  }
  
  public Font getFont(int fontIndex){
    return mFont[fontIndex];	    
  }
  
  public Font getMenuFont(int fontIndex){
    return menuFont[fontIndex];	    
  }
	
}
