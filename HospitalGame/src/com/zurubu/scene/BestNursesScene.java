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

public class BestNursesScene extends Scene {
	private BitmapTextureAtlas mFontTexture;
	private Font mFont;
	private static BestNursesScene scene;
	 
	public static BestNursesScene getScene(){    
		return scene;	  
	}
	
	public BestNursesScene() {
		scene = this;
		initFont();
		
		// set layout for menu //
		initLayoutMenu(); 
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
	
	private void initLayoutMenu(){
		/* set layout menu. */ 
		// background //
		setLayout(1024, 1024, "textures/menu/main_background.png", 0, 0, false, "");
		// main logo //
		setLayout(256, 256, "textures/menu/main_logo.png", 50, 10, false, "");	
		// best nurses border //
		setLayout(1024, 1024, "textures/menu/bestnurses_border.png", 25, 25, false, "");
		
		// ok button //
		setLayout(256, 256, "textures/menu/small_button1.png", 285, 480, true, "OK");
		// achievement button //
		setLayout(256, 256, "textures/menu/small_button1.png", 285, 407, true, "Achievement");
		// nextmode button //
		setLayout(256, 256, "textures/menu/small_button1.png", 50, 407, true, "NextMode");
		// leaderboard button //
		setLayout(256, 256, "textures/menu/small_button1.png", 520, 407, true, "Leaderboard");
		
		this.setTouchAreaBindingEnabled(true);
		
		// set title //
		setText(290, 60, "Endless Easy", false);
		for (int i=0 ; i<8 ; i++) {
			setText(60, 120 + (i*35), "Endless Easy", false);
			setText(0, 120 + (i*35), ""+(i*333), true);
		}
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
	
	private void nextScene(String sceneName) {
		if (sceneName.toLowerCase().equals("ok")) {
			InterfaceActivity interfaceAct = InterfaceActivity.getInterfaceActivity();
			
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
	
	private void setText(int pX, int pY, String tx, Boolean leftside) {
		Text stx = new Text(0, 0, this.mFont, tx);
		stx.setPosition(pX, pY);
		if (leftside)
			stx.setPosition(720 - stx.getWidth(), pY);
		stx.setColor(0.0f, 0.0f, 0.0f);
		this.attachChild(stx);
	}
}
