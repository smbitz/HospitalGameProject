package com.zurubu.scene;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.graphics.Color;

import com.rokejitsx.InterfaceActivity;

public class OptionsScene extends Scene{
	private final float minX = 485;
	private final float maxX = 555;
	private float musicLastX;
	private float sfxLaxtX;
	private Sprite musicThumb;
	private Sprite sfxThumb;
	private BitmapTextureAtlas mFontTexture;
	private Font mFont;
	private static OptionsScene scene;
	private static float musicVolume;
	private static float sfxVolume;
	 
	public static OptionsScene getScene(){    
		return scene;	  
	}
	
	public static int getMusicVolume(){    
		return (int) musicVolume;	  
	}
	
	public static int getSFXVolume(){    
		return (int) sfxVolume;	  
	}
	
	public OptionsScene() {
		scene = this;
		initFont();
		musicLastX = 501;
		sfxLaxtX = 530;
		
		// set layout for menu //
		initLayoutOptions();
		
		// init volume //
		musicVolume = calVolumn("music");
		sfxVolume = calVolumn("sfx");
	}

	private void initFont(){
	    /* Load Font/Textures. */
		InterfaceActivity interfaceAct = InterfaceActivity.getInterfaceActivity();  
		this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		FontFactory.setAssetBasePath("font/");
		this.mFont = FontFactory.createFromAsset(this.mFontTexture, interfaceAct, "Plok.ttf", 18, true, Color.RED);
		interfaceAct.getEngine().getTextureManager().loadTexture(this.mFontTexture);
		interfaceAct.getFontManager().loadFont(this.mFont);	  
	}
	
	public void initLayoutOptions(){
		/* set layout menu. */  
		// background //
		setLayout(1024, 1024, "textures/menu/main_background.png", 0, 0, false, "");
		// options border //
		setLayout(1024, 1024, "textures/menu/options_border.png", 81, 115, false, "");
		// main logo //
		setLayout(256, 256, "textures/menu/main_logo.png", 50, 10, false, "");
		
		// music volume bar //
		setVolumeLayout(1024, 1024, "textures/menu/sliderback.png", 151, 160, true, "Music Volume");
		// sfx volume bar //
		setVolumeLayout(1024, 1024, "textures/menu/sliderback.png", 151, 260, true, "SFX Volume");
		
		// ok button //
		setLayout(256, 256, "textures/menu/small_button.png", 151, 360, true, "OK");
		// credits button //
		setLayout(256, 256, "textures/menu/small_button.png", 326, 360, true, "Credits");
		// cancle button //
		setLayout(256, 256, "textures/menu/small_button.png", 501, 360, true, "Cancle");
		
		// thumb music button //
		musicThumb = setThumbLayout(256, 256, "textures/menu/scrollbar.png", musicLastX, 167);
		this.attachChild(musicThumb);
		this.registerTouchArea(musicThumb);
		// thumb sfx button //
		sfxThumb = setThumbLayout(256, 256, "textures/menu/scrollbar.png", sfxLaxtX, 267);
		this.attachChild(sfxThumb);
		this.registerTouchArea(sfxThumb);
		
		this.setTouchAreaBindingEnabled(true);
	}
	
	private void setLayout(int pWidth, int pHeight, String path, int pX, int pY, Boolean setTx, String tx) {
		InterfaceActivity interfaceAct = InterfaceActivity.getInterfaceActivity();  
			
		BitmapTextureAtlas layoutBitmapTextureAtlas;
		TextureRegion layoutTextureRegion;
		Sprite layout;
		
		layoutBitmapTextureAtlas = new BitmapTextureAtlas(pWidth, pHeight,TextureOptions.DEFAULT);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/");
		layoutTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(layoutBitmapTextureAtlas, interfaceAct, path,0, 0);
		interfaceAct.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas);
		
		if (setTx) {
			layout = new Sprite(pX, pY, layoutTextureRegion){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
							int eventaction = pSceneTouchEvent.getAction(); 
			                float X = pSceneTouchEvent.getX();
			                float Y = pSceneTouchEvent.getY();
			                switch (eventaction) {
			                   case TouchEvent.ACTION_DOWN: {
			                	   System.out.println("#######  Down  ######");
			                	   this.getChild(0).setColor(1.0f,0.0f,0.0f);
			                	   break;
			                   }
			                   case TouchEvent.ACTION_MOVE:{
			                	   System.out.println("#######  Move  ######");
			                	   X = pSceneTouchEvent.getX();
					               Y = pSceneTouchEvent.getY();
			                	   if (X < this.getX() || X > this.getX() + this.getWidth() || Y < this.getY() || Y > this.getY() + this.getHeight()) {
			                		   this.getChild(0).setColor(0.0f,0.0f,0.0f);
			                		   System.out.println("#######  Cancel  ######");
			                	   } else {
			                		   this.getChild(0).setColor(1.0f,0.0f,0.0f);
			                	   }
			                	   break;
			                   }
			                   case TouchEvent.ACTION_UP:{
			                	   System.out.println("#######  UP  ######");
			                	   this.getChild(0).setColor(0.0f, 0.0f, 0.0f);
			                	   if (X < this.getX() || X > this.getX() + this.getWidth() || Y < this.getY() || Y > this.getY() + this.getHeight()) {	     
			                		   System.out.println("#######  Cancel  ######");
			                	   } else {
			                		   nextScene(((Text)this.getChild(0)).getText());
			                	   }
			                		   
			                	   break;
			                   }
			                }
					return true;
				}
			};	
			Text stx = new Text(0, 0, this.mFont, tx);
			stx.setPosition((layout.getWidth()/2) - (stx.getWidth()/2), (layout.getHeight()/2) - (stx.getHeight()/2) + 2);
			stx.setColor(0.0f, 0.0f, 0.0f);
			layout.attachChild(stx);
			this.registerTouchArea(layout);
		} 
		else {
			layout = new Sprite(pX, pY, layoutTextureRegion);
		}
		layout.setScale(1);
		this.attachChild(layout);
	}
	
	private void setVolumeLayout(int pWidth, int pHeight, String path, int pX, int pY, Boolean setTx, String tx) {
		InterfaceActivity interfaceAct = InterfaceActivity.getInterfaceActivity();  
		
		BitmapTextureAtlas layoutBitmapTextureAtlas;
		TextureRegion layoutTextureRegion;
		Sprite layout;
		
		layoutBitmapTextureAtlas = new BitmapTextureAtlas(pWidth, pHeight,TextureOptions.DEFAULT);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/");
		layoutTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(layoutBitmapTextureAtlas, interfaceAct, path,0, 0);
		interfaceAct.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas);
		
		layout = new Sprite(pX, pY, layoutTextureRegion);
		layout.setScale(1);
		this.attachChild(layout);
		
		Text stx = new Text(0, 0, this.mFont, tx);
		stx.setPosition(30, (layout.getHeight()/2) - (stx.getHeight()/2) + 2);
		stx.setColor(0.0f, 0.0f, 0.0f);
		layout.attachChild(stx);
		this.registerTouchArea(layout);
	}
	
	private float diff = 0;
	private Sprite setThumbLayout(int pWidth, int pHeight, String path, float pX, float pY) {
		InterfaceActivity interfaceAct = InterfaceActivity.getInterfaceActivity();  
		
		BitmapTextureAtlas layoutBitmapTextureAtlas;
		TextureRegion layoutTextureRegion;
		Sprite layout;
		
		layoutBitmapTextureAtlas = new BitmapTextureAtlas(pWidth, pHeight,TextureOptions.DEFAULT);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/");
		layoutTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(layoutBitmapTextureAtlas, interfaceAct, path,0, 0);
		interfaceAct.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas);
		
		layout = new Sprite(pX, pY, layoutTextureRegion){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						int eventaction = pSceneTouchEvent.getAction(); 
		                float startX = pSceneTouchEvent.getX();
		                switch (eventaction) {
		                   case TouchEvent.ACTION_DOWN: {
		                	   System.out.println("#######  Down  ######");
		                	   diff = startX - this.getX();
		                	   break;
		                   }
		                   case TouchEvent.ACTION_MOVE:{
		                	   System.out.println("#######  Move  ######");
		                	   if (pSceneTouchEvent.getX()-diff >= minX && pSceneTouchEvent.getX()-diff <= maxX) {
		                		   this.setPosition((pSceneTouchEvent.getX()- diff), this.getY());
		                	   } else {
		                		   if (pSceneTouchEvent.getX()-diff < minX) {
		                			   this.setPosition(minX, this.getY());
		                		   } else {
		                			   this.setPosition(maxX, this.getY());
		                		   }
		                	   }
		                	   
		                	   // set volume //
		                	   if (this == musicThumb) {
		                		   musicVolume = calVolumn("music");
		                	   } else {
		                		   sfxVolume = calVolumn("sfx");
		                	   }
		                	   System.out.println("#######  Music Volume  ######" + musicVolume);
		                	   System.out.println("#######  Sfx Volume  ######" + sfxVolume);
		                	   break;
		                   }
		                   case TouchEvent.ACTION_UP:{
		                	   System.out.println("#######  UP  ######");
		                	   break;
		                   }
		                }
				return true;
			}
		};	
		layout.setScale(1);
		return layout;
	}
	
	private void nextScene(String sceneName) {
		if (sceneName.toLowerCase().equals("cancle")) {
			InterfaceActivity interfaceAct = InterfaceActivity.getInterfaceActivity();
			
			// set volume position back //
			musicThumb.setPosition(musicLastX, musicThumb.getY());
			sfxThumb.setPosition(sfxLaxtX, sfxThumb.getY());
			musicVolume = calVolumn("music");
			sfxVolume = calVolumn("sfx");
						
			// call option scene //	
		    // switch to the new scene
			if (MenuScene.getScene() != null) {
				interfaceAct.getEngine().setScene(MenuScene.getScene());
			} else {
				final MenuScene menuScene = new MenuScene();
				interfaceAct.getEngine().setScene(menuScene);
			}
		} else if (sceneName.toLowerCase().equals("ok")) {
			InterfaceActivity interfaceAct = InterfaceActivity.getInterfaceActivity();
			
			// record new volume position //
			musicLastX = musicThumb.getX();
			sfxLaxtX = sfxThumb.getX();
			
			
			// call option scene //	
		    // switch to the new scene
			if (MenuScene.getScene() != null) {
				interfaceAct.getEngine().setScene(MenuScene.getScene());
			} else {
				final MenuScene menuScene = new MenuScene();
				interfaceAct.getEngine().setScene(menuScene);
			}
		}
	}
	
	private float calVolumn(String s) {
		float range = maxX - minX;
		float v;
		if (s == "music") {
			v = musicThumb.getX() - minX;	
		} else {
			v = sfxThumb.getX() - minX;	
		}
		return (100/range)*v;
	}
}
