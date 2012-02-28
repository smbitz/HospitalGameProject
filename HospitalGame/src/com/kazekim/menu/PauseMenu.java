package com.kazekim.menu;

import java.util.Vector;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.view.MotionEvent;

import com.kazekim.andengine.extend.BitmapTextureAtlasEx;
import com.kazekim.ui.TextButton;
import com.rokejitsx.HospitalGameActivity;

public class PauseMenu extends Scene {
	
	private PauseMenuListener listener;
	private BaseGameActivity activity;
	
	private TextButton resumeButton;
	private TextButton restartButton;
	private TextButton achievementButton;
	private TextButton optionsButton;
	private TextButton mainmenuButton;
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas,layoutBitmapTextureAtlas2,layoutBitmapTextureAtlas3, layoutBitmapTextureAtlas4, layoutBitmapTextureAtlas5, layoutBitmapTextureAtlas6;
	private BitmapTextureAtlas mFontTexture;
	  private Font lcdFont;
	  
	  private Sprite pauseMenuBorder;
	  
	  private PauseMenu pauseMenu;

	public PauseMenu(BaseGameActivity activity){
		this.activity=activity;
		this.pauseMenu = this;
		
		setFont();
		setBackgroundEnabled(false);
		
		layoutBitmapTextureAtlas = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas);
		
		TextureRegion menuBorderTextureRegion =layoutBitmapTextureAtlas.appendTextureAsset(activity, "interface_background.png");
		//TiledTextureRegion menuBorderTextureRegion =ResourceManager.getInstance().getTexture("media/textures/briefingmenu/menuobjectives.png");
		pauseMenuBorder = new Sprite(0,0 , menuBorderTextureRegion);	
		pauseMenuBorder.setPosition((InitialVal.CAMERA_WIDTH-pauseMenuBorder.getBaseWidth())/2,(InitialVal.CAMERA_HEIGHT-pauseMenuBorder.getHeight())/2);
		pauseMenuBorder.setScale(1);
		attachChild(pauseMenuBorder);
		
		layoutBitmapTextureAtlas2 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas2);
		
		TiledTextureRegion buttonTextureRegion = layoutBitmapTextureAtlas2.appendTiledAsset(activity, "montagemediobutton.png", 3, 1);  
		resumeButton = new TextButton(0, 0, buttonTextureRegion,lcdFont,"Resume"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int myEventAction = pSceneTouchEvent.getAction(); 
		        switch (myEventAction) {
		           case MotionEvent.ACTION_DOWN:
		        	   resumeButton.setCurrentTileIndex(1);
		        	break;
		           case MotionEvent.ACTION_MOVE: {
		            	break;}
		           case MotionEvent.ACTION_UP:
		        	   resumeButton.setCurrentTileIndex(0);
		        	   back();
						listener.onResumeButtonClick(pauseMenu);
		                break;
		        }
		        
				return true;
				
			}
	   };
	   resumeButton.setCurrentTileIndex(0);
	   resumeButton.setColor(0, 0, 0);
	   resumeButton.setPosition((pauseMenuBorder.getWidth()-resumeButton.getWidth())/2, 20);
	   resumeButton.setScale(1);
	   pauseMenuBorder.attachChild(resumeButton);
	   registerTouchArea(resumeButton);
		
		 layoutBitmapTextureAtlas3 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas3);
		
		TiledTextureRegion buttonTextureRegion2 = layoutBitmapTextureAtlas3.appendTiledAsset(activity, "montagemediobutton.png", 3, 1);  
		restartButton = new TextButton(0, 0, buttonTextureRegion2,lcdFont,"Restart"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int myEventAction = pSceneTouchEvent.getAction(); 
		        switch (myEventAction) {
		           case MotionEvent.ACTION_DOWN:
		        	   restartButton.setCurrentTileIndex(1);
		        	break;
		           case MotionEvent.ACTION_MOVE: {
		            	break;}
		           case MotionEvent.ACTION_UP:
		        	   restartButton.setCurrentTileIndex(0);
		        	   back();
						listener.onRestartButtonClick(pauseMenu);
		                break;
		        }
		       
				return true;
				
			}
	   };
	   restartButton.setCurrentTileIndex(0);
	   restartButton.setColor(0, 0, 0);
	   restartButton.setPosition((pauseMenuBorder.getWidth()-restartButton.getWidth())/2,20+ restartButton.getHeight()*2/3);
	   restartButton.setScale(1);
	   pauseMenuBorder.attachChild(restartButton);
		registerTouchArea(restartButton);
		
		 layoutBitmapTextureAtlas4 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas4);
		
		TiledTextureRegion buttonTextureRegion3 = layoutBitmapTextureAtlas4.appendTiledAsset(activity, "montagemediobutton.png", 3, 1);  
		achievementButton = new TextButton(0, 0, buttonTextureRegion3,lcdFont,"Achievement"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int myEventAction = pSceneTouchEvent.getAction(); 
		        switch (myEventAction) {
		           case MotionEvent.ACTION_DOWN:
		        	   achievementButton.setCurrentTileIndex(1);
		        	break;
		           case MotionEvent.ACTION_MOVE: {
		            	break;}
		           case MotionEvent.ACTION_UP:
		        	   achievementButton.setCurrentTileIndex(0);
		        	   back();
						listener.onAchievementButtonClick(pauseMenu);
		                break;
		        }
		        
				return true;
				
			}
	   };
	   achievementButton.setCurrentTileIndex(0);
	   achievementButton.setColor(0, 0, 0);
	   achievementButton.setPosition((pauseMenuBorder.getWidth()-achievementButton.getWidth())/2, 20+achievementButton.getHeight()*2*2/3);
	   achievementButton.setScale(1);
	   pauseMenuBorder.attachChild(achievementButton);
		registerTouchArea(achievementButton);
		
		 layoutBitmapTextureAtlas5 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas5);
		
		TiledTextureRegion buttonTextureRegion4 = layoutBitmapTextureAtlas5.appendTiledAsset(activity, "montagemediobutton.png", 3, 1);  
		optionsButton = new TextButton(0, 0, buttonTextureRegion4,lcdFont,"Options"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int myEventAction = pSceneTouchEvent.getAction(); 
		        switch (myEventAction) {
		           case MotionEvent.ACTION_DOWN:
		        	   optionsButton.setCurrentTileIndex(1);
		        	break;
		           case MotionEvent.ACTION_MOVE: {
		            	break;}
		           case MotionEvent.ACTION_UP:
		        	   optionsButton.setCurrentTileIndex(0);
		        	   back();
						listener.onOptionsButtonClick(pauseMenu);
		                break;
		        }
		        
				return true;
				
			}
	   };
	   optionsButton.setCurrentTileIndex(0);
	   optionsButton.setColor(0, 0, 0);
	   optionsButton.setPosition((pauseMenuBorder.getWidth()-optionsButton.getWidth())/2, 20+optionsButton.getHeight()*3*2/3);
	   optionsButton.setScale(1);
	   pauseMenuBorder.attachChild(optionsButton);
		registerTouchArea(optionsButton);
		
		 layoutBitmapTextureAtlas6 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas6);
		
		TiledTextureRegion buttonTextureRegion5 = layoutBitmapTextureAtlas6.appendTiledAsset(activity, "montagemediobutton.png", 3, 1);  
		mainmenuButton = new TextButton(0, 0, buttonTextureRegion5,lcdFont,"Main Menu"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int myEventAction = pSceneTouchEvent.getAction(); 
		        switch (myEventAction) {
		           case MotionEvent.ACTION_DOWN:
		        	   mainmenuButton.setCurrentTileIndex(1);
		        	break;
		           case MotionEvent.ACTION_MOVE: {
		            	break;}
		           case MotionEvent.ACTION_UP:
		        	   mainmenuButton.setCurrentTileIndex(0);
		        	   back();
						listener.onMainMenuButtonClick(pauseMenu);
		                break;
		        }
		        
				return true;
				
			}
	   };
	   mainmenuButton.setCurrentTileIndex(0);
	   mainmenuButton.setColor(0, 0, 0);
	   mainmenuButton.setPosition((pauseMenuBorder.getWidth()-mainmenuButton.getWidth())/2, 20+mainmenuButton.getHeight()*4*2/3);
	   mainmenuButton.setScale(1);
	   pauseMenuBorder.attachChild(mainmenuButton);
		registerTouchArea(mainmenuButton);
	}
	
	public void unLoad(){
	  Vector<BitmapTextureAtlas> list = new Vector<BitmapTextureAtlas>();
	  list.add(layoutBitmapTextureAtlas);
	  list.add(layoutBitmapTextureAtlas2);
	  list.add(layoutBitmapTextureAtlas3);
	  list.add(layoutBitmapTextureAtlas4);
	  list.add(layoutBitmapTextureAtlas5);
	  list.add(layoutBitmapTextureAtlas6);
	  HospitalGameActivity.getGameActivity().sendUnloadTextureAtlas(list);
	}
	

	
	public void setFont(){
		this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		FontFactory.setAssetBasePath("font/");
		this.lcdFont = FontFactory.createFromAsset(this.mFontTexture, activity, "LCD.ttf", 20, true, Color.WHITE);
		activity.getEngine().getTextureManager().loadTexture(this.mFontTexture);
		activity.getFontManager().loadFont(this.lcdFont);	 
	}
	

	public void setPauseMenuListener(PauseMenuListener listener){
		this.listener= listener;
	}
}
