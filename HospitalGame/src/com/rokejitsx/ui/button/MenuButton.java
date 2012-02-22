package com.rokejitsx.ui.button;

import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class MenuButton extends TiledSprite
{
    public MenuButton(float pX, float pY, TiledTextureRegion pTiledTextureRegion)
    {
            super(pX, pY, pTiledTextureRegion);
    }
   
    public void press(final int pTileIndex)
    {
            this.setCurrentTileIndex(pTileIndex);
            //this.setScale(pScale);
            //this.setAlpha(pAlpha);
    }
   
    public void release(final int pTileIndex)
    {
            this.setCurrentTileIndex(pTileIndex);
            //this.setScale(pScale);
            //this.setAlpha(pAlpha);
    }
}