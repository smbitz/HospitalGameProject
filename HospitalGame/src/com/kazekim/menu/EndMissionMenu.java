package com.kazekim.menu;

import java.util.Vector;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.view.MotionEvent;

import com.kazekim.andengine.extend.BitmapTextureAtlasEx;
import com.kazekim.ui.TextButton;
import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.GameFonts;

public class EndMissionMenu {

	
	private BaseGameActivity activity;
	private EndMissionMenu endMissionMenu;
	
	private Sprite endMissionBorder;
	private Text objectiveTitleText;
	
	private TextButton box1;
	private TextButton box2;
	private TextButton box3;
	private TextButton box4;
	private TextButton box5;
	
	private TextButton titleBg1;
	private TextButton titleBg2;
	private TextButton titleBg3;
	private TextButton titleBg4;
	private TextButton titleBg5;

	private TextButton retryButton;
	private TextButton nextMissionButton;
	private TextButton mainMenuButton;
	
	private BitmapTextureAtlas mFontTexture;
	private Font lcdFont;
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas, layoutBitmapTextureAtlas2, layoutBitmapTextureAtlas3, layoutBitmapTextureAtlas4;
	private EndMissionMenuListener listener;
	
	public EndMissionMenu(BaseGameActivity activity,final Scene scene,String goalText,String leftHospitalText,String ambulanceText,String treatedText,String fundsText,boolean isPass){
		this.activity=activity;
		this.endMissionMenu=this;
		
		setFont();
		
		layoutBitmapTextureAtlas = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas);
		
		TextureRegion menuBorderTextureRegion =layoutBitmapTextureAtlas.appendTextureAsset(activity, "menuBGHappy.png");
		//TiledTextureRegion menuBorderTextureRegion =ResourceManager.getInstance().getTexture("media/textures/briefingmenu/menuobjectives.png");
		endMissionBorder = new Sprite(0,0 , menuBorderTextureRegion);	
		endMissionBorder.setPosition((InitialVal.CAMERA_WIDTH-endMissionBorder.getBaseWidth())/2,(InitialVal.CAMERA_HEIGHT-endMissionBorder.getHeight())/2);
		endMissionBorder.setScale(1);
		scene.attachChild(endMissionBorder);
		
		objectiveTitleText = new Text(0, 0, lcdFont, "Objectives");
		objectiveTitleText.setPosition((endMissionBorder.getWidth()-objectiveTitleText.getWidth())/2, 20);
		objectiveTitleText.setColor(0.0f, 0.0f, 0.0f);
		endMissionBorder.attachChild(objectiveTitleText);
		
		TiledTextureRegion boxTextureRegion =layoutBitmapTextureAtlas.appendTiledAsset(activity, "insertbuttonsmall.png", 1, 1); 
		box1 = new TextButton(0,0, boxTextureRegion,lcdFont,goalText);	
		box1.setPosition(endMissionBorder.getWidth()/2, 20+box1.getHeight());
		box1.setScale(1);
		box1.setColor(255, 255, 255);
		endMissionBorder.attachChild(box1);
		
		box2 = new TextButton(0, 0, boxTextureRegion,lcdFont,leftHospitalText);	
		box2.setPosition(endMissionBorder.getWidth()/2, 20+box1.getHeight()*3);
		box2.setScale(1);
		box2.setColor(255, 255, 255);
		endMissionBorder.attachChild(box2);
		
		box3 = new TextButton(0, 0, boxTextureRegion,lcdFont,ambulanceText);
		box3.setPosition(endMissionBorder.getWidth()/2, 20+box1.getHeight()*4);
		box3.setScale(1);
		box3.setColor(255, 255, 255);
		endMissionBorder.attachChild(box3);
		
		box4 = new TextButton(0, 0, boxTextureRegion,lcdFont,treatedText);
		box4.setPosition(endMissionBorder.getWidth()/2, 20+box1.getHeight()*5);
		box4.setScale(1);
		box4.setColor(255, 255, 255);
		endMissionBorder.attachChild(box4);
		
		box5 = new TextButton(0, 0, boxTextureRegion,lcdFont,fundsText);
		box5.setPosition(endMissionBorder.getWidth()/2, 20+box1.getHeight()*6);
		box5.setScale(1);
		box5.setColor(255, 255, 255);
		endMissionBorder.attachChild(box5);
		
		layoutBitmapTextureAtlas2 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas2);
		
		TiledTextureRegion titleBoxTextureRegion =layoutBitmapTextureAtlas2.appendTiledAsset(activity, "montagemediobutton.png", 3, 1);
		titleBg1 = new TextButton(0,0, titleBoxTextureRegion,lcdFont,"Goal");	
		titleBg1.setPosition(endMissionBorder.getWidth()/2-titleBg1.getWidth()+20, 5+box1.getHeight());
		titleBg1.setScale(1);
		endMissionBorder.attachChild(titleBg1);

		titleBg2 = new TextButton(0,0, titleBoxTextureRegion,lcdFont,"Left Hospital");	
		titleBg2.setPosition(endMissionBorder.getWidth()/2-titleBg1.getWidth()+20, 5+box1.getHeight()*3);
		titleBg2.setScale(1);
		endMissionBorder.attachChild(titleBg2);
		
		titleBg3 = new TextButton(0,0, titleBoxTextureRegion,lcdFont,"Ambulance");	
		titleBg3.setPosition(endMissionBorder.getWidth()/2-titleBg1.getWidth()+20, 5+box1.getHeight()*4);
		titleBg3.setScale(1);
		endMissionBorder.attachChild(titleBg3);

		titleBg4 = new TextButton(0,0, titleBoxTextureRegion,lcdFont,"Treated");	
		titleBg4.setPosition(endMissionBorder.getWidth()/2-titleBg1.getWidth()+20, 5+box1.getHeight()*5);
		titleBg4.setScale(1);
		endMissionBorder.attachChild(titleBg4);
		
		titleBg5 = new TextButton(0,0, titleBoxTextureRegion,lcdFont,"Funds");	
		titleBg5.setPosition(endMissionBorder.getWidth()/2-titleBg1.getWidth()+20, 5+box1.getHeight()*6);
		titleBg5.setScale(1);
		endMissionBorder.attachChild(titleBg5);

		layoutBitmapTextureAtlas3 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas3);
		
		if(!isPass){
			TiledTextureRegion retryButtonTextureRegion = layoutBitmapTextureAtlas3.appendTiledAsset(activity, "montagemediobutton.png", 3, 1);  
			retryButton = new TextButton(0,0, retryButtonTextureRegion,lcdFont,"Retry"){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					int myEventAction = pSceneTouchEvent.getAction(); 
			        switch (myEventAction) {
			           case MotionEvent.ACTION_DOWN:
			        	   retryButton.setCurrentTileIndex(1);
			        	break;
			           case MotionEvent.ACTION_MOVE: {
			            	break;}
			           case MotionEvent.ACTION_UP:
			        	   retryButton.setCurrentTileIndex(0);
			        	  // scene.detachChild(endMissionBorder);
							listener.onRetryButtonClick(endMissionMenu);
			                break;
			        }
					
					return true;
					
				}
		   };
		   retryButton.setCurrentTileIndex(0);
		   retryButton.setPosition(endMissionBorder.getWidth()/4-retryButton.getWidth()/2, endMissionBorder.getHeight()-retryButton.getHeight()*3/2);
			retryButton.setScale(1);
			endMissionBorder.attachChild(retryButton);
			scene.registerTouchArea(retryButton);
		}else{
			TiledTextureRegion nextButtonTextureRegion = layoutBitmapTextureAtlas3.appendTiledAsset(activity, "montagemediobutton.png", 3, 1);  
			nextMissionButton = new TextButton(0,0, nextButtonTextureRegion,lcdFont,"Next"){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					int myEventAction = pSceneTouchEvent.getAction(); 
			        switch (myEventAction) {
			           case MotionEvent.ACTION_DOWN:
			        	   nextMissionButton.setCurrentTileIndex(1);
			        	break;
			           case MotionEvent.ACTION_MOVE: {
			            	break;}
			           case MotionEvent.ACTION_UP:
			        	   nextMissionButton.setCurrentTileIndex(0);
			        	  // scene.detachChild(endMissionBorder);
							listener.onNextMissionButtonClick(endMissionMenu);
			                break;
			        }
					
					return true;
					
				}
		   };
		   nextMissionButton.setCurrentTileIndex(0);
		   nextMissionButton.setPosition(endMissionBorder.getWidth()/4-nextMissionButton.getWidth()/2, endMissionBorder.getHeight()-nextMissionButton.getHeight()*3/2);
		   nextMissionButton.setScale(1);
			endMissionBorder.attachChild(nextMissionButton);
			scene.registerTouchArea(nextMissionButton);
		}
		
		layoutBitmapTextureAtlas4 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas4);
		
		TiledTextureRegion mainMenuButtonTextureRegion = layoutBitmapTextureAtlas4.appendTiledAsset(activity, "montagemediobutton.png", 3, 1);  
		mainMenuButton = new TextButton(0,0, mainMenuButtonTextureRegion,lcdFont,"Main Menu"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int myEventAction = pSceneTouchEvent.getAction(); 
		        switch (myEventAction) {
		           case MotionEvent.ACTION_DOWN:
		        	   mainMenuButton.setCurrentTileIndex(1);
		        	break;
		           case MotionEvent.ACTION_MOVE: {
		            	break;}
		           case MotionEvent.ACTION_UP:
		        	   mainMenuButton.setCurrentTileIndex(0);
		        	  // scene.detachChild(endMissionBorder);
						listener.onMainMenuButtonClick(endMissionMenu);
		                break;
		        }
		        
				
				return true;
				
			}
	   };
	   mainMenuButton.setCurrentTileIndex(0);
	   mainMenuButton.setPosition(endMissionBorder.getWidth()*3/4-mainMenuButton.getWidth()/2, endMissionBorder.getHeight()-mainMenuButton.getHeight()*3/2);
	   mainMenuButton.setScale(1);
		endMissionBorder.attachChild(mainMenuButton);
		scene.registerTouchArea(mainMenuButton);
		
		
	}
	
	
	public void unLoad(){
	  Vector<BitmapTextureAtlas> list = new Vector<BitmapTextureAtlas>();
	  list.add(layoutBitmapTextureAtlas);
	  list.add(layoutBitmapTextureAtlas2);
	  list.add(layoutBitmapTextureAtlas3);
	  list.add(layoutBitmapTextureAtlas4);
	  HospitalGameActivity.getGameActivity().sendUnloadTextureAtlas(list);
    }
	public void setFont(){
		/*this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		FontFactory.setAssetBasePath("font/");
		this.lcdFont = FontFactory.createFromAsset(this.mFontTexture, activity, "LCD.ttf", 20, true, Color.WHITE);
		activity.getEngine().getTextureManager().loadTexture(this.mFontTexture);
		activity.getFontManager().loadFont(this.lcdFont);*/
	  lcdFont = GameFonts.getInstance().getMenuFont(GameFonts.MENU_LCD_FONT_20_WHITE);
	}
	
	public void setEndMissionMenuListener(EndMissionMenuListener listener){
		this.listener=listener;
	}
}
