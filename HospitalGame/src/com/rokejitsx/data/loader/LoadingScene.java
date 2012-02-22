package com.rokejitsx.data.loader;

import org.anddev.andengine.entity.modifier.FadeInModifier;
import org.anddev.andengine.entity.modifier.FadeOutModifier;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.Text;

import com.rokejitsx.data.GameFonts;

public class LoadingScene extends Scene {
  private Text loadingText;  
  public LoadingScene(){
    setBackground(new ColorBackground(0, 0, 0));
    loadingText = new Text(0, 0, GameFonts.getInstance().getFont(GameFonts.DEFALUT_BOLD_18_WHITE), "Loading...");
    loadingText.setPosition(800 / 2 - loadingText.getWidth()/2, 600/2 - loadingText.getHeight()/2);    
    
    
    Rectangle rect = new Rectangle(0, 0, loadingText.getWidth() + 10, loadingText.getHeight());
    rect.setColor(0, 0, 0);
    rect.setPosition(loadingText);
    rect.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new FadeOutModifier(0.5f), new FadeInModifier(1.0f))));
    //loadingText.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new FadeInModifier(5.0f), new FadeOutModifier(5.0f))));
    attachChild(loadingText);
    attachChild(rect);
    
    
    
  }
  
    
}
