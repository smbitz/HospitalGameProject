package com.kazekim.ui;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.region.TextureRegion;


import android.view.MotionEvent;

public class ColorTextButton extends Sprite  {

	private Text text;

	private String title;
	private Font font;
	private int align;
	
	private float colorNormalRed=0f;
	private float colorNormalGreen=0f;
	private float colorNormalBlue=0f;
	private float colorOnClickRed=0f;
	private float colorOnClickGreen=0f;
	private float colorOnClickBlue=0f;
	
	public static int CENTER=0;
	public static int LEFT=1;
	public static int RIGHT=2;

	public boolean isSetClickColor=false;
	
	private ColorTextButton(float pX, float pY,
			TextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion);
		
	}
	
	public ColorTextButton(float pX, float pY,TextureRegion pTextureRegion, Font font, String title) {
		super(pX, pY, pTextureRegion);
		
		this.font=font;
		this.title=title;
		setText(font,title);
	}
	
	private void setText(Font font, String title){
		text = new Text(0, 0, font, title);
		
		setTextAlign(CENTER);
		text.setColor(colorNormalRed, colorNormalGreen, colorNormalBlue);
		this.attachChild(text);
	}
	
	public void changeText(Font font,String title){
		this.detachChild(text);
		setText(title);
		
	}
	
	public int getAlign(){
		return align;
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

	public void setColorNormal(float red,float green,float blue){
		this.colorNormalRed=red;
		this.colorNormalGreen=green;
		this.colorNormalBlue=blue;
		
		text.setColor(red, green, blue);
	}
	
	public void setColorOnClick(float red,float green,float blue){
		this.colorOnClickRed=red;
		this.colorOnClickGreen=green;
		this.colorOnClickBlue=blue;

		isSetClickColor=true;
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

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		int myEventAction = pSceneTouchEvent.getAction(); 
		
		float X = pSceneTouchEvent.getX();
		float Y = pSceneTouchEvent.getY();
        
        switch (myEventAction) {
           case MotionEvent.ACTION_DOWN:
        	   if(isSetClickColor){
        		   text.setColor(colorOnClickRed, colorOnClickGreen, colorOnClickBlue);
        	   }
        	break;
           case MotionEvent.ACTION_MOVE: {
        	   if(isSetClickColor){
	        	   if (X < this.getX() || X > this.getX() + this.getWidth() || Y < this.getY() || Y > this.getY() + this.getHeight()) {
	        		   text.setColor(colorNormalRed, colorNormalGreen, colorOnClickBlue);
	        	   }
        	   }
            	break;}
           case MotionEvent.ACTION_UP:
        	   text.setColor(colorNormalRed, colorNormalGreen, colorOnClickBlue);
				
                break;
        }
		return true;
	
	}
	
}
