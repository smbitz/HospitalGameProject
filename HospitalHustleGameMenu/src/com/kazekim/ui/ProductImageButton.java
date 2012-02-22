package com.kazekim.ui;

import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.kazekim.temp.Building;

import android.view.MotionEvent;

public class ProductImageButton extends ImageButton {
	
	private TiledSprite layer1;
	
	private int chooseIndex=0;
	private int buildingIndex=0;
	private int indexButton=0;
	
	private TiledTextureRegion productTexture;
	
	public final static int[] indexImage= {Building.NONE,Building.BED,Building.PLANT,Building.WATER,Building.XRAY,Building.DENTIST,Building.ULTRASCAN
		,Building.TAC,Building.FOOD,Building.PHYSIOTHERAPY,Building.OPHTHALMOLOGY,Building.PSYCHIATRY,Building.TELEVISION
		,Building.CHEMOTHERAPY,Building.BABY_SCAN,Building.CARDIOLOGY,Building.OPERATION,Building.QUICKTREAT};
	  
	public ProductImageButton(float pX, float pY, TiledTextureRegion pTiledTextureRegion, TiledTextureRegion pTiledTextureRegion2) {
		super(pX, pY, pTiledTextureRegion);
		
		layer1 = new TiledSprite(0, 0, pTiledTextureRegion2);
	   layer1.setCurrentTileIndex(3);
	   layer1.setScale(1);

	   
	}
	
	public ProductImageButton(float pX, float pY, TiledTextureRegion pTiledTextureRegion, TiledTextureRegion pTiledTextureRegion2, TiledTextureRegion productTexture) {
		this(pX, pY, pTiledTextureRegion, pTiledTextureRegion2);
		this.productTexture = productTexture;
		
		setImage(productTexture);
		
	}
	
	public ProductImageButton(float pX, float pY, TiledTextureRegion pTiledTextureRegion, TiledTextureRegion pTiledTextureRegion2, TiledTextureRegion productTexture,boolean isNewProduct,int index) {
		this(pX, pY, pTiledTextureRegion,pTiledTextureRegion2);
		this.productTexture = productTexture;
		
		setImage(productTexture,isNewProduct,index);
		tiledImageOnLayer.setScale((float)0.3);
	}
	
	public void setImage(TiledTextureRegion pTiledTextureRegion,boolean isNewProduct,int index){
		
		super.setImage(pTiledTextureRegion);
		setNewProduct(isNewProduct);
		changeImage(index);
	}
	
	public void changeImage(int index){
			buildingIndex=index;
		if(index>0){
			
			for(int i=0;i<indexImage.length;i++){
				if(indexImage[i]==index){
					chooseIndex=i;
					break;
				}
			}
			tiledImageOnLayer.setCurrentTileIndex(0);
		}else{
			setVisible(false);
		}
	}
	
	public int getChooseIndex(){
		return chooseIndex;
	}
	
	public int getBuildingIndex(){
		return buildingIndex;
	}
	
	public void setIndexButton(int index){
		indexButton=index;
	}
	
	public int getIndexButton(){
		return indexButton;
	}
	
	public TiledTextureRegion getProductTexture(){
		return productTexture;
	}
	
	private void setNewProduct(boolean isNewProduct){
		if(isNewProduct){
			attachChild(layer1);
		}
	}
}
