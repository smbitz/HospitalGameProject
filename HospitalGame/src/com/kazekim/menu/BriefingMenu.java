package com.kazekim.menu;

import java.util.Vector;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnAreaTouchListener;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.entity.text.Text;
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

import com.kazekim.andengine.extend.BitmapTextureAtlasEx;
import com.kazekim.ui.TextButton;
import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.GameFonts;


public class BriefingMenu{


	private BaseGameActivity activity;
	//private Scene scene;
	
	private TextButton okButton;
	private TextButton box1;
	private TextButton box2;
	private TextButton titleBg1;
	private TextButton titleBg2;
	private Sprite menuBorder;
	private Text objectiveDetailText;
	private Text objectiveTitleText;
	
	private BriefMenuListener listener;
	
	private BitmapTextureAtlas mFontTexture;
	  private Font lcdFont;
	
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas;
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas2;
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas3;
	public BriefingMenu(BaseGameActivity activity,final Scene scene,String objectiveString, String numPatient, String funds){

		this.activity=activity;
		//this.scene = scene;
		
		setFont();
		

		layoutBitmapTextureAtlas = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas);
		
		TextureRegion menuBorderTextureRegion =layoutBitmapTextureAtlas.appendTextureAsset(activity, "menuobjectives.png");
		//TiledTextureRegion menuBorderTextureRegion =ResourceManager.getInstance().getTexture("media/textures/briefingmenu/menuobjectives.png");
		menuBorder = new Sprite(99, 65, menuBorderTextureRegion);		
		menuBorder.setScale(1);
		scene.attachChild(menuBorder);
		
		TiledTextureRegion boxTextureRegion =layoutBitmapTextureAtlas.appendTiledAsset(activity, "insertbuttonsmall.png", 1, 1); 
		box1 = new TextButton(311, 235, boxTextureRegion,lcdFont,numPatient);	
		box1.setScale(1);
		box1.setColor(255, 255, 255);
		menuBorder.attachChild(box1);
		
		box2 = new TextButton(311, 275, boxTextureRegion,lcdFont,funds);	
		box2.setScale(1);
		box2.setColor(255, 255, 255);
		menuBorder.attachChild(box2);
		
		layoutBitmapTextureAtlas2 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas2);
		
		TiledTextureRegion titleBoxTextureRegion =layoutBitmapTextureAtlas2.appendTiledAsset(activity, "montagemediobutton.png", 3, 1);
		titleBg1 = new TextButton(101, 220, titleBoxTextureRegion,lcdFont,"Patients");	
		titleBg1.setScale(1);
		menuBorder.attachChild(titleBg1);

		titleBg2 = new TextButton(101, 260, titleBoxTextureRegion,lcdFont,"Funds");	
		titleBg2.setScale(1);
		menuBorder.attachChild(titleBg2);
		
		layoutBitmapTextureAtlas3 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas3);
		
		TiledTextureRegion okButtonTextureRegion = layoutBitmapTextureAtlas3.appendTiledAsset(activity, "daminibuttons.png", 3, 1);  
		okButton = new TextButton(451, 285, okButtonTextureRegion,lcdFont,"OK"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				scene.detachChild(menuBorder);
				listener.onOKButtonClick(okButton);
				return true;
				
			}
	   };

		okButton.setScale(1);
		menuBorder.attachChild(okButton);
		scene.registerTouchArea(okButton);
		
		objectiveDetailText = new Text(0, 0, lcdFont, objectiveString);
		objectiveDetailText.setPosition(20, 20);
		objectiveDetailText.setColor(0.0f, 0.0f, 0.0f);
		menuBorder.attachChild(objectiveDetailText);
		
		objectiveTitleText = new Text(0, 0, lcdFont, "Objectives");
		objectiveTitleText.setPosition((menuBorder.getWidth()-objectiveTitleText.getWidth())/2, 200);
		objectiveTitleText.setColor(0.0f, 0.0f, 0.0f);
		menuBorder.attachChild(objectiveTitleText);
		
	
	}
	
	public void unLoad(){
	  Vector<BitmapTextureAtlas> list = new Vector<BitmapTextureAtlas>();
      list.add(layoutBitmapTextureAtlas);
      list.add(layoutBitmapTextureAtlas2);
      list.add(layoutBitmapTextureAtlas3);
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
	
	
	public void setBriefMenuListener(BriefMenuListener listener){
		this.listener=listener;
	}

}
