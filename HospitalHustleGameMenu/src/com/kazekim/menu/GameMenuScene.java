package com.kazekim.menu;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnAreaTouchListener;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
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
import android.location.Address;
import android.view.MotionEvent;

import com.kazekim.andengine.extend.BitmapTextureAtlasEx;
import com.kazekim.data.UserMissionSkeleton;
import com.kazekim.ui.TextButton;

public class GameMenuScene extends Scene implements  IOnSceneTouchListener, IOnAreaTouchListener,BriefMenuListener,PauseMenuListener,EndMissionMenuListener{ 
	  
	  
	  private BaseGameActivity activity;
	  
	  private BitmapTextureAtlas mFontTexture;
	  private Font lcdFont;
	  
	  private GameMenuScene scene;
	  
	  private boolean isEndGame=false;
	  
	  public GameMenuScene(final BaseGameActivity activity){	
		  
		  this.activity = activity;
		  this.scene = this;
		  
		  setFont();
		  
		  UserMissionSkeleton missionSkeleton =  new UserMissionSkeleton();
		  missionSkeleton.addMissionStation(1);
		  missionSkeleton.addMissionStation(1);
		  missionSkeleton.addMissionStation(2);

		//   System.out.println("Count "+missionSkeleton.getMissionStationNumCount()+" "+missionSkeleton.getAppearedStationListNumCount());
		
		   this.setTouchAreaBindingEnabled(true);
		   
		setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));  
	   

	    BriefingMenu briefingMenu = new BriefingMenu(activity,this, "testetstestse", "20", "300");
	    
	  //  this.attachChild(briefingMenu);
	    briefingMenu.setBriefMenuListener(this);
	   this.setOnAreaTouchListener(this);
	   
	   final PauseMenu pauseMenu = new PauseMenu(activity, scene);
		pauseMenu.setPauseMenuListener(scene);
	   
	   BitmapTextureAtlasEx layoutBitmapTextureAtlas2 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas2);
		
		TiledTextureRegion pauseButtonTextureRegion = layoutBitmapTextureAtlas2.appendTiledAsset(activity, "daminibuttons.png", 3, 1);  
		TextButton pauseButton = new TextButton(InitialVal.CAMERA_WIDTH-200, InitialVal.CAMERA_HEIGHT-100, pauseButtonTextureRegion,lcdFont,"Menu"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				pauseMenu.attachPauseMenu();
				
				return true;
				
			}
	   };
	 

	   pauseButton.setScale(1);
		this.attachChild(pauseButton);
		scene.registerTouchArea(pauseButton);
		
		BitmapTextureAtlasEx layoutBitmapTextureAtlas3 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas3);
		
		TiledTextureRegion endGameButtonTextureRegion = layoutBitmapTextureAtlas3.appendTiledAsset(activity, "daminibuttons.png", 3, 1);  
		TextButton endGameButton = new TextButton(InitialVal.CAMERA_WIDTH-700, InitialVal.CAMERA_HEIGHT-100, endGameButtonTextureRegion,lcdFont,"End"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				if(!isEndGame){
					EndMissionMenu endMissionMenu = new EndMissionMenu(activity,scene, "10000", "20", "300","1234","54251",false);
					endMissionMenu.setEndMissionMenuListener(scene);
				    isEndGame=true;
				}
				return true;
				
			}
	   };
	 

	   endGameButton.setScale(1);
		this.attachChild(endGameButton);
		scene.registerTouchArea(endGameButton);
		
		BitmapTextureAtlasEx layoutBitmapTextureAtlas4 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas4);
		
		TiledTextureRegion nextGameButtonTextureRegion = layoutBitmapTextureAtlas4.appendTiledAsset(activity, "daminibuttons.png", 3, 1);  
		TextButton nextGameButton = new TextButton(InitialVal.CAMERA_WIDTH-500, InitialVal.CAMERA_HEIGHT-100, nextGameButtonTextureRegion,lcdFont,"Next"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				if(!isEndGame){
					EndMissionMenu endMissionMenu = new EndMissionMenu(activity,scene, "10000", "20", "300","1234","54251",true);
					endMissionMenu.setEndMissionMenuListener(scene);
				    isEndGame=true;
				}
				return true;
				
			}
	   };
	 

	   nextGameButton.setScale(1);
		this.attachChild(nextGameButton);
		scene.registerTouchArea(nextGameButton);
	   
	  }
	  
	  public void setFont(){
			this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			FontFactory.setAssetBasePath("font/");
			this.lcdFont = FontFactory.createFromAsset(this.mFontTexture, activity, "LCD.ttf", 20, true, Color.WHITE);
			activity.getEngine().getTextureManager().loadTexture(this.mFontTexture);
			activity.getFontManager().loadFont(this.lcdFont);	 
		}
	  
	  public void loadGamePlay(int hospitalId, int level){
		  
	  
	  }

	  @Override
	  public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {	

		return false;
	  }

	 
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {

		return true;
	}

	@Override
	public void onOKButtonClick(TextButton textButton) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onResumeButtonClick(PauseMenu pauseMenu) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRestartButtonClick(PauseMenu pauseMenu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAchievementButtonClick(PauseMenu pauseMenu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOptionsButtonClick(PauseMenu pauseMenu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMainMenuButtonClick(PauseMenu pauseMenu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRetryButtonClick(EndMissionMenu endMissionMenu) {
		// TODO Auto-generated method stub
		isEndGame=false;
		activity.finish();
	}

	@Override
	public void onNextMissionButtonClick(EndMissionMenu endMissionMenu) {
		// TODO Auto-generated method stub
		isEndGame=false;
		activity.finish();
	}

	@Override
	public void onMainMenuButtonClick(EndMissionMenu endMissionMenu) {
		// TODO Auto-generated method stub
		isEndGame=false;
		activity.finish();
	}


}
