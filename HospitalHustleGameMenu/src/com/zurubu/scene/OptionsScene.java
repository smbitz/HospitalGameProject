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

import com.kazekim.menu.InitialVal;
import com.kazekim.ui.ColorTextButton;

import android.graphics.Color;


import codegears.hospitalhustlegamemenu.*;;

public class OptionsScene extends Scene  implements OptionMenuListener{
	private int minX = 0;
	private int maxX = 0;
	private Sprite musicThumb;
	private Sprite sfxThumb;

	private Font mFont;
	private static OptionsScene scene;
	private static int musicVolume;
	private static int sfxVolume;
	private int musicPosX=0;
	private int sfxPosX=0;
	
	private BitmapTextureAtlas buttonBitmapTextureAtlas;
	private BitmapTextureAtlas scrollBitmapTextureAtlas;
	private BitmapTextureAtlas screenBitmapTextureAtlas;
	private BitmapTextureAtlas logoBitmapTextureAtlas;
	private BitmapTextureAtlas menuBitmapTextureAtlas;
	private BitmapTextureAtlas scrollButtonBitmapTextureAtlas;
	
	private Sprite background;
	private Sprite logo;
	private Sprite menuBorder;
	private Sprite scrollBar1;
	private Sprite scrollBar2;
	
	private ColorTextButton okButton;
	private ColorTextButton creditButton;
	private ColorTextButton cancelButton;
	
	private static final int OKBUTTON=0;
	private static final int CREDITBUTTON=1;
	private static final int CANCELBUTTON=2;
	
	private static final int SCROLLMUSICVALUME=0;
	private static final int SCROLLSFXVOLUME=1;
	
	private float diff = 0;
	
	private HospitalHustleGameMenuActivity interfaceAct;
	
	private OptionMenuListener listener;
	 
/*	public static OptionsScene getScene(){    
		return scene;	  
	}
	*/
	public static int getMusicVolume(){    
		return (int) musicVolume;	  
	}
	
	public static int getSFXVolume(){    
		return (int) sfxVolume;	  
	}
	
	public OptionsScene() {
		// load data //
		interfaceAct = HospitalHustleGameMenuActivity.getInterfaceActivity();
		musicVolume = AppSharedPreference.getInstance().getMusicVolume();
		sfxVolume = AppSharedPreference.getInstance().getSFXVolume();
		
	
		
		scene = this;
		this.mFont = interfaceAct.getFont();
		
		// set layout for menu //
		initLayoutOptions();
		
		setOptionMenuListener(this);
		
		// init volume //
		//musicVolume = calVolumn("music");
		//sfxVolume = calVolumn("sfx");
	}

	
	public void setOptionMenuListener(OptionMenuListener listener){
		this.listener = listener;
	}
	
	public void initLayoutOptions(){
		/* set layout menu. */  
		// background //
		// background //
		screenBitmapTextureAtlas = new BitmapTextureAtlas(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/");
		
		interfaceAct.getEngine().getTextureManager().loadTexture(screenBitmapTextureAtlas);
		
		TextureRegion bgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(screenBitmapTextureAtlas, interfaceAct, "textures/menu/main_background.png",0, 0);
		background = new Sprite(0,0, bgTextureRegion);
		this.attachChild(background);
		
		// options border //
		
		menuBitmapTextureAtlas = new BitmapTextureAtlas(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/");
		
		interfaceAct.getEngine().getTextureManager().loadTexture(menuBitmapTextureAtlas);

		TextureRegion menuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBitmapTextureAtlas, interfaceAct, "textures/menu/options_border.png",0, 0);
		menuBorder = new Sprite(0,0, menuTextureRegion);
		menuBorder.setPosition(InitialVal.CAMERA_WIDTH/2 - menuBorder.getWidth()/2, InitialVal.CAMERA_HEIGHT/2-menuBorder.getHeight()/2);
		this.attachChild(menuBorder);

		// main logo //
		logoBitmapTextureAtlas= new BitmapTextureAtlas(256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/");
		
		interfaceAct.getEngine().getTextureManager().loadTexture(logoBitmapTextureAtlas);

		TextureRegion logoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(logoBitmapTextureAtlas, interfaceAct, "textures/menu/main_logo.png",0, 0);
		logo = new Sprite(InitialVal.CAMERA_WIDTH/16,InitialVal.CAMERA_HEIGHT/80, logoTextureRegion);
		this.attachChild(logo);
		
	
		// music volume bar //
		
		scrollBitmapTextureAtlas= new BitmapTextureAtlas(512, 512,TextureOptions.DEFAULT);
		interfaceAct.getEngine().getTextureManager().loadTexture(scrollBitmapTextureAtlas);
		
		TextureRegion scrollTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(scrollBitmapTextureAtlas, interfaceAct, "textures/menu/sliderback.png",0, 0);
		
		scrollBar1 = new Sprite(0,0, scrollTextureRegion.deepCopy());
		scrollBar1.setPosition(menuBorder.getWidth()/2 - scrollBar1.getWidth()/2, (int)(menuBorder.getHeight()*0.1));
		menuBorder.attachChild(scrollBar1);
		
		Text musicText = new Text(0, 0, this.mFont, "Music Volume");
		musicText.setPosition((int)(scrollBar1.getWidth()*0.02), (int)(scrollBar1.getHeight()/2-musicText.getHeight()/2));
		musicText.setColor(0.0f, 0.0f, 0.0f);
		scrollBar1.attachChild(musicText);

		// sfx volume bar //
		
		scrollBar2 = new Sprite(0,0, scrollTextureRegion.deepCopy());
		scrollBar2.setPosition(menuBorder.getWidth()/2 - scrollBar2.getWidth()/2, scrollBar2.getHeight()*2);
		menuBorder.attachChild(scrollBar2);
		
		Text sfxText = new Text(0, 0, this.mFont, "SFX Volume");
		sfxText.setPosition((int)(scrollBar1.getWidth()*0.02), (int)(scrollBar1.getHeight()/2-sfxText.getHeight()/2));
		sfxText.setColor(0.0f, 0.0f, 0.0f);
		scrollBar2.attachChild(sfxText);
		
		//setVolumeLayout(1024, 1024, "textures/menu/sliderback.png", 151, 260, true, "SFX Volume");
		
		// ok button //
		buttonBitmapTextureAtlas = new BitmapTextureAtlas(256, 256,TextureOptions.DEFAULT);
		interfaceAct.getEngine().getTextureManager().loadTexture(buttonBitmapTextureAtlas);
		
		TextureRegion buttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonBitmapTextureAtlas, interfaceAct, "textures/menu/small_button.png",0, 0);
		okButton = addButton(buttonTextureRegion.deepCopy(), mFont, "OK",OKBUTTON);
		okButton.setPosition((int)(menuBorder.getWidth()/2-okButton.getWidth()*3/2), (int)(scrollBar2.getHeight()*4));
		menuBorder.attachChild(okButton);
		
		// credits button //
		
		creditButton = addButton(buttonTextureRegion.deepCopy(), mFont, "Credits",CREDITBUTTON);
		creditButton.setPosition((int)(menuBorder.getWidth()/2-okButton.getWidth()/2), (int)(scrollBar2.getHeight()*4));
		menuBorder.attachChild(creditButton);
		
		
		// cancel button //
		cancelButton = addButton(buttonTextureRegion.deepCopy(), mFont, "Cancel",CANCELBUTTON);
		cancelButton.setPosition((int)(menuBorder.getWidth()/2+okButton.getWidth()/2), (int)(scrollBar2.getHeight()*4));
		menuBorder.attachChild(cancelButton);
		
		
		// thumb music button //
		
		scrollButtonBitmapTextureAtlas = new BitmapTextureAtlas(256, 256,TextureOptions.DEFAULT);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/");
		TextureRegion scrollButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(scrollButtonBitmapTextureAtlas, interfaceAct, "textures/menu/scrollbar.png",0, 0);
		interfaceAct.getEngine().getTextureManager().loadTexture(scrollButtonBitmapTextureAtlas);
		
		
		minX = (int)(scrollBar1.getWidth()*13/20);
		maxX = (int)(scrollBar2.getWidth()*8/10);
		
		musicThumb = new Sprite(0,0, scrollButtonTextureRegion.deepCopy()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						int eventaction = pSceneTouchEvent.getAction(); 
		                float startX = pSceneTouchEvent.getX();
		                switch (eventaction) {
		                   case TouchEvent.ACTION_DOWN: {
		                	   diff = startX - this.getX();
		                	   break;
		                   }
		                   case TouchEvent.ACTION_MOVE:{
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
		                		   musicVolume = calVolumn(SCROLLMUSICVALUME);
		                		   listener.onMusicValueChange(musicVolume);
		                	   break;
		                   }
		                   case TouchEvent.ACTION_UP:{
		                	   break;
		                   }
		                }
				return true;
			}
		};	
		musicPosX=getScrollPosXfromVolume(musicVolume);
		musicThumb.setPosition(musicPosX, (int)(scrollBar1.getHeight()/2-musicThumb.getHeight()/2));
		musicThumb.setScale(1);
		scrollBar1.attachChild(musicThumb);
		this.registerTouchArea(musicThumb);
		// thumb sfx button //
		sfxThumb = new Sprite(0,0, scrollButtonTextureRegion.deepCopy()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						int eventaction = pSceneTouchEvent.getAction(); 
		                float startX = pSceneTouchEvent.getX();
		                switch (eventaction) {
		                   case TouchEvent.ACTION_DOWN: {
		                	   diff = startX - this.getX();
		                	   break;
		                   }
		                   case TouchEvent.ACTION_MOVE:{
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
		                		   sfxVolume = calVolumn(SCROLLSFXVOLUME);
		                		   listener.onSFXValueChange(sfxVolume);
		                	   break;
		                   }
		                   case TouchEvent.ACTION_UP:{
		                	   break;
		                   }
		                }
				return true;
			}
		};	
		sfxPosX=getScrollPosXfromVolume(sfxVolume);
		sfxThumb.setPosition(sfxPosX, (int)(scrollBar2.getHeight()/2-sfxThumb.getHeight()/2));
		sfxThumb.setScale(1);
		scrollBar2.attachChild(sfxThumb);
		this.registerTouchArea(sfxThumb);
		
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
	
	private void nextScene(int menuType) {
		if (menuType==CANCELBUTTON) {

			// set volume position back //
			musicThumb.setPosition(musicPosX, musicThumb.getY());
			sfxThumb.setPosition(sfxPosX, sfxThumb.getY());
			musicVolume = AppSharedPreference.getInstance().getSFXVolume();
			sfxVolume = AppSharedPreference.getInstance().getMusicVolume();
			listener.onSFXValueChange(musicVolume);
			listener.onMusicValueChange(sfxVolume);
						
			// call option scene //	
		    // switch to the new scene
			if (MenuScene.getScene() != null) {
				interfaceAct.getEngine().setScene(MenuScene.getScene());
			} else {
				final MenuScene menuScene = new MenuScene();
				interfaceAct.getEngine().setScene(menuScene);
			}
		} else if (menuType==OKBUTTON) {

			// record new volume position //
			musicVolume = calVolumn(SCROLLMUSICVALUME);
			sfxVolume = calVolumn(SCROLLSFXVOLUME);
			// save new volume //
			AppSharedPreference.getInstance().setMusicVolume(musicVolume);
			AppSharedPreference.getInstance().setSFXVolume(sfxVolume);
			sfxPosX=(int)(getScrollPosXfromVolume(sfxVolume));
			musicPosX=(int)(getScrollPosXfromVolume(musicVolume));
				
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
	
	private int getScrollPosXfromVolume(int volume){

		return (int)(minX+(maxX-minX)*volume/100);
		
	}
	
	private int calVolumn(int scrollType) {
		float range = maxX - minX;
		float v;
		if (scrollType == SCROLLMUSICVALUME) {
			v = musicThumb.getX() - minX;	
		} else {
			v = sfxThumb.getX() - minX;	
		}
		return (int)((float)(100f/range)*v);
	}
	
	public void recycle(){
		buttonBitmapTextureAtlas.clearTextureAtlasSources();
		screenBitmapTextureAtlas.clearTextureAtlasSources();
		menuBitmapTextureAtlas.clearTextureAtlasSources();
		scrollBitmapTextureAtlas.clearTextureAtlasSources();
		scrollButtonBitmapTextureAtlas.clearTextureAtlasSources();
	}

	@Override
	public void onMusicValueChange(int musicVolume) {
		// TODO Auto-generated method stub
		System.out.println("music change!!");
	}

	@Override
	public void onSFXValueChange(int sfxVolume) {
		// TODO Auto-generated method stub
		System.out.println("sfx change!!");
	}
}
