package com.zurubu.scene;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
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

public class MenuScene extends Scene implements IOnSceneTouchListener { 
	private BitmapTextureAtlas mFontTexture;
	private Font mFont;
	private static MenuScene scene;
	 
	public static MenuScene getScene(){    
		return scene;	  
	}
	
	public MenuScene() {
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
		// mainmenu border //
		setLayout(512, 512, "textures/menu/mainmenu_border.png", 25, 105, false, "");
		// menu button play //
		setLayout(512, 512, "textures/menu/menu_button1.png", 45, 130, true, "Play");	
		// menu button endless mode //
		setLayout(512, 512, "textures/menu/menu_button1.png", 45, 200, true, "Endless Mode");	
		// menu button option //
		setLayout(512, 512, "textures/menu/menu_button1.png", 45, 270, true, "Options");
		// menu button bess nuress //
		setLayout(512, 512, "textures/menu/menu_button1.png", 45, 340, true, "Best Nuress");	
		// menu button tell a friend //
		setLayout(512, 512, "textures/menu/menu_button1.png", 45, 410, true, "Tell a Friend");
		// menu button connect //
		setLayout(512, 512, "textures/menu/menu_button1.png", 45, 505, true, "Connect");	
		// main logo //
		setLayout(256, 256, "textures/menu/main_logo.png", 50, 10, false, "");	
		// menu insert button //
		setLayout(256, 256, "textures/menu/menu_insertbutton.png", 310, 17, false, "");	
		// menu button change player //
		setLayout(512, 512, "textures/menu/menu_button1.png", 540, 0, true, "Change Player");	
		
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
	
	private void nextScene(String sceneName) {
		if (sceneName.toLowerCase().equals("options")) {
			InterfaceActivity interfaceAct = InterfaceActivity.getInterfaceActivity();
			
			// call option scene //	
		    // switch to the new scene
			if (OptionsScene.getScene() != null) {
				interfaceAct.getEngine().setScene(OptionsScene.getScene());
			} else {
				final OptionsScene optionsScene = new OptionsScene();
				interfaceAct.getEngine().setScene(optionsScene);
			}
		} else if (sceneName.toLowerCase().equals("best nuress")) {
			InterfaceActivity interfaceAct = InterfaceActivity.getInterfaceActivity();			
			
			// call option scene //	
		    // switch to the new scene
			if (BestNursesScene.getScene() != null) {
				interfaceAct.getEngine().setScene(BestNursesScene.getScene());
			} else {
				final BestNursesScene bestnursesScene = new BestNursesScene();
				interfaceAct.getEngine().setScene(bestnursesScene);
			}
		}
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}
}

