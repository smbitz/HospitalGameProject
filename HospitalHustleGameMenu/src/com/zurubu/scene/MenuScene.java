package com.zurubu.scene;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.kazekim.menu.GameMenuScene;
import com.kazekim.menu.InitialVal;
import com.kazekim.ui.ColorTextButton;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import codegears.hospitalhustlegamemenu.*;

public class MenuScene extends Scene implements IOnSceneTouchListener{ 
	
	private static MenuScene scene;
	
	private Sprite background;
	private Sprite menuBorder;
	
	private Font mFont;
	
	private ColorTextButton playButton;
	private ColorTextButton endlessModeButton;
	private ColorTextButton optionsButton;
	private ColorTextButton bestNurseButton;
	private ColorTextButton tellFriendButton;
	private ColorTextButton connectButton;
	private ColorTextButton changePlayerButton;
	 
	private BitmapTextureAtlas buttonBitmapTextureAtlas;
	private TextureRegion buttonTextureRegion;
	private BitmapTextureAtlas screenBitmapTextureAtlas;
	private BitmapTextureAtlas menuBitmapTextureAtlas;

	private HospitalHustleGameMenuActivity interfaceAct;
	
	private static final int PLAYBUTTON=0;
	private static final int ENDLESSMODEBUTTON=1; 
	private static final int OPTIONSBUTTON=2;
	private static final int BESTNURSESBUTTON=3;
	private static final int TELLFRIENDBUTTON=4;
	private static final int CONNECTBUTTON=5;
	private static final int CHANGEPLAYERBUTTON=6;
	
	public static MenuScene getScene(){    
		return scene;	  
	}
	
	public MenuScene() {
		
		interfaceAct = HospitalHustleGameMenuActivity.getInterfaceActivity();
		
		scene = this;
		
		this.mFont=interfaceAct.getFont();
		
		// set layout for menu //
		initLayoutMenu(); 
		
		
	}

	
	
	private void initLayoutMenu(){
		/* set layout menu. */ 
		// background //
		screenBitmapTextureAtlas = new BitmapTextureAtlas(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/");
		
		interfaceAct.getEngine().getTextureManager().loadTexture(screenBitmapTextureAtlas);
		
		TextureRegion bgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(screenBitmapTextureAtlas, interfaceAct, "textures/menu/main_background.png",0, 0);
		background = new Sprite(0,0, bgTextureRegion);
		this.attachChild(background);
		
		// mainmenu border //
		menuBitmapTextureAtlas = new BitmapTextureAtlas(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/");
		
		interfaceAct.getEngine().getTextureManager().loadTexture(menuBitmapTextureAtlas);

		TextureRegion menuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBitmapTextureAtlas, interfaceAct, "textures/menu/mainmenu_border.png",0, 0);
		menuBorder = new Sprite(InitialVal.CAMERA_WIDTH/40, InitialVal.CAMERA_HEIGHT/6, menuTextureRegion);
		this.attachChild(menuBorder);
		
		// menu button play //
		
		buttonBitmapTextureAtlas = new BitmapTextureAtlas(512, 512,TextureOptions.DEFAULT);
		buttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonBitmapTextureAtlas, interfaceAct, "textures/menu/menu_button1.png",0, 0);
		interfaceAct.getEngine().getTextureManager().loadTexture(buttonBitmapTextureAtlas);
		
		playButton = addButton(buttonTextureRegion.deepCopy(), mFont, "Play",PLAYBUTTON);
		playButton.setPosition((int)(menuBorder.getWidth()/2-playButton.getWidth()/2), (int)(menuBorder.getHeight()*0.1));
		menuBorder.attachChild(playButton);
		
		endlessModeButton =addButton(buttonTextureRegion.deepCopy(), mFont, "Endless Mode",ENDLESSMODEBUTTON);
		endlessModeButton.setPosition((int)(menuBorder.getWidth()/2-playButton.getWidth()/2), (int)(menuBorder.getHeight()*0.1+(playButton.getHeight()-10)));
		menuBorder.attachChild(endlessModeButton);
		
		optionsButton =addButton(buttonTextureRegion.deepCopy(), mFont, "Options",OPTIONSBUTTON);
		optionsButton.setPosition((int)(menuBorder.getWidth()/2-playButton.getWidth()/2), (int)(menuBorder.getHeight()*0.1+(playButton.getHeight()-10)*2));
		menuBorder.attachChild(optionsButton);
		
		bestNurseButton =addButton(buttonTextureRegion.deepCopy(), mFont, "Best Nurses",BESTNURSESBUTTON);
		bestNurseButton.setPosition((int)(menuBorder.getWidth()/2-playButton.getWidth()/2), (int)(menuBorder.getHeight()*0.1+(playButton.getHeight()-10)*3));
		menuBorder.attachChild(bestNurseButton);
		
		tellFriendButton =addButton(buttonTextureRegion.deepCopy(), mFont, "Tell a Friend",TELLFRIENDBUTTON);
		tellFriendButton.setPosition((int)(menuBorder.getWidth()/2-playButton.getWidth()/2), (int)(menuBorder.getHeight()*0.1+(playButton.getHeight()-10)*4));
		menuBorder.attachChild(tellFriendButton);
		
		
		connectButton =addButton(buttonTextureRegion.deepCopy(), mFont, "Connect",CONNECTBUTTON);
		connectButton.setPosition((int)(menuBorder.getWidth()/2-playButton.getWidth()/2), (int)(menuBorder.getHeight()*0.15+(playButton.getHeight()-10)*5));
		menuBorder.attachChild(connectButton);
		
		changePlayerButton =addButton(buttonTextureRegion.deepCopy(), mFont, "Change Player",CHANGEPLAYERBUTTON);
		changePlayerButton.setPosition((int)(InitialVal.CAMERA_WIDTH-changePlayerButton.getWidth()), 0);
		this.attachChild(changePlayerButton);
		
		setLayout(256, 256, "textures/menu/main_logo.png", 50, 10, false, "");	
		// menu insert button //
		setLayout(256, 256, "textures/menu/menu_insertbutton.png", 310, 17, false, "");	
		// menu button change player //
	//	setLayout(512, 512, "textures/menu/menu_button1.png", 540, 0, true, "Change Player");	
		
		this.setTouchAreaBindingEnabled(true);
	}
	
	private ColorTextButton addButton(TextureRegion texture,Font font,String text,final int menuType){
		ColorTextButton button = new ColorTextButton(0,0, texture, font, text){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
						int eventaction = pSceneTouchEvent.getAction(); 
		                switch (eventaction) {
		                   case TouchEvent.ACTION_DOWN: 
		                	   break;
		                   
		                   case TouchEvent.ACTION_MOVE:
		                	   break;
		                   
		                   case TouchEvent.ACTION_UP:{
		                	
		                		   nextScene(menuType);
		                		   
		                	   break;
		                   }
		                }
				return true;
			}
		};
		button.setColorNormal(0.0f,0.0f,0.0f);
		button.setColorOnClick(1.0f,0.0f,0.0f);
		this.registerTouchArea(button);
		
		return button;
	}
	
	private void setLayout(int pWidth, int pHeight, String path, int pX, int pY, Boolean setTx, String tx) {
		  
			
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
	}
	
	private void nextScene(int type) {
		if(type == PLAYBUTTON){
		
			/*Intent myIntent;
			HospitalHustleGameMenuActivity interfaceAct = HospitalHustleGameMenuActivity.getInterfaceActivity();

			myIntent = new Intent(interfaceAct,
					MenuGameActivity.class);
			
			
			interfaceAct.startActivityForResult(myIntent,1);
			*/
			final GameMenuScene gameMenuScene = new GameMenuScene();
			interfaceAct.getEngine().setScene(gameMenuScene);
		}else if (type == OPTIONSBUTTON) {

			// call option scene //	
		    // switch to the new scene
	/*		if (OptionsScene.getScene() != null) {
				interfaceAct.getEngine().setScene(OptionsScene.getScene());
			} else {*/
				final OptionsScene optionsScene = new OptionsScene();
				interfaceAct.getEngine().setScene(optionsScene);
		//	}
		} else if (type == BESTNURSESBUTTON) {
	
			// call option scene //	
		    // switch to the new scene
			if (BestNursesScene.getScene() != null) {
				interfaceAct.getEngine().setScene(BestNursesScene.getScene());
			} else {
				final BestNursesScene bestnursesScene = new BestNursesScene();
				interfaceAct.getEngine().setScene(bestnursesScene);
			}
		} else if (type == CONNECTBUTTON) {

			String url = "http://www.facebook.com/pages/Hospital-Hustle-Game/150163501688464";  
			Intent i = new Intent(Intent.ACTION_VIEW);  
			Uri u = Uri.parse(url);  
			i.setData(u);  
			try {  
			  // Start the activity  
			  interfaceAct.startActivity(i);  
			} catch (ActivityNotFoundException e) {  
			  // Raise on activity not found  
//			  Toast toast = Toast.makeText(interfaceAct, "Browser not found.", Toast.LENGTH_SHORT);  
			}  
		}
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void recycle(){
		buttonBitmapTextureAtlas.clearTextureAtlasSources();
		screenBitmapTextureAtlas.clearTextureAtlasSources();
		menuBitmapTextureAtlas.clearTextureAtlasSources();
	}

}

