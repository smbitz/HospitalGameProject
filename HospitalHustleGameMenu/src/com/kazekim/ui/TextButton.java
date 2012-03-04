package com.kazekim.ui;

import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class TextButton extends TiledSprite{
	
	private Text text;
	private float red=0;
	private float green=0;
	private float blue=0;
	private String title;
	private Font font;
	private int align;
	
	public static int CENTER=0;
	public static int LEFT=1;
	public static int RIGHT=2;

	private TextButton(float pX, float pY, float pTileWidth, float pTileHeight,
			TiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion);
	

	}
	
	private TextButton(float pX, float pY,
			TiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTiledTextureRegion);
		
	}
	
	public TextButton(float pX, float pY,TiledTextureRegion pTiledTextureRegion, Font font, String title) {
		super(pX, pY, pTiledTextureRegion);
		
		this.font=font;
		this.title=title;
		setText(font,title);
	}
	
	private void setText(Font font, String title){
		text = new Text(0, 0, font, title);
		
		setTextAlign(CENTER);
		text.setColor(red, green, blue);
		this.attachChild(text);
	}
	
	public void changeText(Font font,String title){
		this.detachChild(text);
		setText(title);
		
	}
	
	public void setTextAlign(int align){
		this.align=align;
		if(align==LEFT){
			text.setPosition((20+text.getWidth())/2, (this.getHeight()-text.getHeight())/2);
		}else if(align==CENTER){
			text.setPosition((this.getWidth()-text.getWidth())/2, (this.getHeight()-text.getHeight())/2);
		}else{
			text.setPosition(this.getWidth()-text.getWidth()/2-20, (this.getHeight()-text.getHeight())/2);
		}
	}

	public void setColor(float red,float green,float blue){
		this.red=red;
		this.green=green;
		this.blue=blue;
		
		text.setColor(red, green, blue);
	}
	
	public void setText(String title){
		this.detachChildren();
		
		this.title=title;
		
		setText(font,title);
	}
	
	public void setFont(Font font){
		this.detachChildren();
		
		this.font=font;
		
		setText(font,title);
	}
	
	public String getTitle(){
		return title;
	}
	
	public Font getFont(){
		return font;
	}
	
}
