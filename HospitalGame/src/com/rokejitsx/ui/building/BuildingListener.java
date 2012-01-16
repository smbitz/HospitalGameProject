package com.rokejitsx.ui.building;

import com.rokejitsx.data.GameCharactor;

public interface BuildingListener {
  
  public void onReceive(Building building, GameCharactor gameChar);
  public void onRemove(Building building, GameCharactor gameChar);
  
}
