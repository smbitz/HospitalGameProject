package com.kazekim.ui;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.view.MotionEvent;

public class ImageButton  extends TiledSprite{
	

	protected TiledSprite tiledImageOnLayer;
	private Sprite normalImageOnLayer;

	public ImageButton(float pX, float pY, TiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTiledTextureRegion);

	}
	
	public ImageButton(float pX, float pY, TiledTextureRegion pTiledTextureRegion, TiledTextureRegion pTiledTextureRegion2) {
		super(pX, pY, pTiledTextureRegion);
		
		setImage(pTiledTextureRegion2);
	}
	
	public ImageButton(float pX, float pY, TiledTextureRegion pTiledTextureRegion, TextureRegion pTiledTextureRegion2) {
		super(pX, pY, pTiledTextureRegion);
		
		setImage(pTiledTextureRegion2);
	}
	
	public void setImage(TiledTextureRegion pTiledTextureRegion){
			detachChildren();
		
		 tiledImageOnLayer = new TiledSprite(0, 0, pTiledTextureRegion);
		   tiledImageOnLayer.setCurrentTileIndex(0);
		   tiledImageOnLayer.setScale(1);
		   tiledImageOnLayer.setPosition((getWidth()-tiledImageOnLayer.getWidth())/2, (getHeight()-tiledImageOnLayer.getHeight())/2);
		   attachChild(tiledImageOnLayer);
	}
	
	public void setImage(TextureRegion pTiledTextureRegion){
		detachChildren();
	
		normalImageOnLayer = new Sprite(0,0 , pTiledTextureRegion);	
		normalImageOnLayer.setPosition((getWidth()-tiledImageOnLayer.getWidth())/2, (getHeight()-tiledImageOnLayer.getHeight())/2);
		normalImageOnLayer.setScale(1);
		attachChild(normalImageOnLayer);
}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		int myEventAction = pSceneTouchEvent.getAction(); 
        switch (myEventAction) {
           case MotionEvent.ACTION_DOWN:
        	   setCurrentTileIndex(1);
        	break;
           case MotionEvent.ACTION_MOVE: {
        	   setCurrentTileIndex(0);
            	break;}
           case MotionEvent.ACTION_UP:
        	   setCurrentTileIndex(0);
				
                break;
        }
		return true;
	
	}

}
