package com.rokejitsx.map;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.resource.ResourceManager;

public class MapScene extends Scene{
  public static final int HOSPITAL_START 	= 0;
  public static final int HOSPITAL_1 		= 1;
  public static final int HOSPITAL_2 		= 2;
  public static final int HOSPITAL_3 		= 3;
  public static final int HOSPITAL_4 		= 4;
  public static final int HOSPITAL_5 		= 5;
  public static final int HOSPITAL_6 		= 6;
  public static final int HOSPITAL_7 		= 7;
  public static final int HOSPITAL_FINISH 	= 8;
  
  private static final String mapResourceLocation = "media/textures/map/";  
  
  private Sprite mapSprite;
  private AnimatedSprite airPlaneSprite;
  
  private AnimatedSprite[] hospitalSpriteList;
  
  
  public MapScene(){
	BitmapTextureAtlas mBitmapTextureAtlas = ResourceManager.initBitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA, mapResourceLocation);
	TextureRegion mMapTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, HospitalGameActivity.getGameActivity(), "map_background.png", 0, 0);
	
	mapSprite = new Sprite(0, 0, mMapTextureRegion);
	
	mBitmapTextureAtlas = ResourceManager.initBitmapTextureAtlas(1024, 512, TextureOptions.BILINEAR, mapResourceLocation);
	
	
	TiledTextureRegion mAirplaneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, HospitalGameActivity.getGameActivity(), "montage_aviao.png", 0, 0, 6, 4);
	TiledTextureRegion mHospitalTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, HospitalGameActivity.getGameActivity(), "hospitals.png", 594, 0, 6, 4);
	
	
	
	airPlaneSprite = new AnimatedSprite(0, 0, mAirplaneTextureRegion);
	
	hospitalSpriteList = new AnimatedSprite[7];
	for(int i = 0; i < hospitalSpriteList.length;i++){
	  hospitalSpriteList[i] = new AnimatedSprite(0, 0, mHospitalTextureRegion.deepCopy());	
	}
	
     
  }
  
  
	
  	
}
