package com.rokejitsx.ui.hospital;

import com.rokejitsx.data.GameObject;
import com.rokejitsx.ui.item.Item;

public class ItemDoughnut extends GameObject{
 
  private Item item;	 
  public ItemDoughnut(){
    super(0, 0, 0, 0, DOUGHNUT);    
    
  }
  
  public void setItem(Item item){
    this.item = item.deepCopy();
    this.item.setPosition(getWidth()/2 - this.item.getWidth()/2, getHeight()/2 - this.item.getHeight()/2);
    attachChild(this.item);	  
  }
  
  public void check(){
    item.checked();	  
  }
  
  public void unCheck(){
    item.unChecked();	  
  }
  
  public void clear(){
    if(item == null)
      return;
    detachChild(item);
    item = null;
  }
  
  public boolean containItem(){
    return item != null;	  
  }
  
  

}
