package com.rokejitsx.ui.building;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import android.util.Log;

import com.rokejitsx.data.resource.ImageResource;
import com.rokejitsx.data.resource.ResourceManager;

public class Checker extends Rectangle implements ImageResource{  
  private AnimatedSprite[] checkSprite;
  public Checker(int maxCheck){
    super(0, 0, 0, 0);    
    checkSprite = new AnimatedSprite[maxCheck];
    float x = 0;
    float y = 0;
    float maxWidth, maxHeight;
    for(int i = 0;i < maxCheck;i++){
      AnimatedSprite check = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(CHECKBOXES));
      check.setCurrentTileIndex(1);
      check.setPosition(x, y);
      check.setVisible(false);
      attachChild(check);
      checkSprite[i] = check;
      x += /*check.getBaseWidth()*/30;
      if(i > 0 && i % 2 == 1){
        y += /*check.getBaseHeight()*/30;
        x = 0;
      }
    }
    if(maxCheck > 1)
      maxWidth = checkSprite[0].getBaseWidth() * 2;
    else
      maxWidth = checkSprite[0].getBaseWidth();
    
    maxHeight = y + checkSprite[0].getBaseHeight();
    
    
    setAlpha(0);
    setWidth(maxWidth);
    setHeight(maxHeight);
  }
  
  public boolean isCanCheck(){
    for(int i = 0;i < checkSprite.length;i++){
	  AnimatedSprite check = checkSprite[i];
	  if(!check.isVisible()){	    
	    return true;
	  }
	}  
    return false;	  
  }
  
  public boolean checked(){
	for(int i = 0;i < checkSprite.length;i++){
	  AnimatedSprite check = checkSprite[i];
	  if(!check.isVisible()){
	    check.setVisible(true);
	    return true;
	  }
	}  
    return false;	  
  }
  
  public void unChecked(){
	for(int i = 0;i < checkSprite.length;i++){
	  AnimatedSprite check = checkSprite[i];
	  if(check.isVisible()){
		Log.d("RokejitsX", "unchekc "+i);
	    check.setVisible(false);
	    break;
	  }
	}  
  }
  
}
