package com.rokejitsx.menu;

import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;

import com.rokejitsx.ui.building.Building;

public class InGameMenuScene extends MyMenuScene implements IOnMenuItemClickListener{ 
  public static final int RESTART	= 994;
  public static final int NEXT 		= 995;
  public static final int QUIT 		= 996;
  public static final int BUY 		= 997;
  public static final int SELL  	= 998;
  public static final int CONTINUE  = 999;
  public static final int CANCEL 	= 1000;
    
  
  private MenuScene chooseUpgradeTypeMenuScene, chooseBuildingMenuScene, gameOverMenuScene, nextLevelMenuScene;
  
  private int upgradeType;
  private InGameMenuSceneListener listener;
  
  public InGameMenuScene(InGameMenuSceneListener listener){
    super();
    this.listener = listener;
  }
  
  public void showUpgradeMenu(){
	chooseUpgradeTypeMenuScene = initMenuScene();	
    chooseUpgradeTypeMenuScene.addMenuItem(initMenuItem("BUY", BUY));
    chooseUpgradeTypeMenuScene.addMenuItem(initMenuItem("SELL", SELL));
    chooseUpgradeTypeMenuScene.addMenuItem(initMenuItem("CONTINUE", CONTINUE));
    //chooseUpgradeTypeMenuScene.buildAnimations();
    showMenu(chooseUpgradeTypeMenuScene);	  
  }
  
  public void showChooseBuildingMenuScene(){
	showMenu(chooseBuildingMenuScene);    	  
  }
  
  public void showRestartLevelMenuScene(){
	gameOverMenuScene = initMenuScene();
    gameOverMenuScene.addMenuItem(initMenuItem("RESTART", RESTART));
    gameOverMenuScene.addMenuItem(initMenuItem("QUIT", QUIT));
    //gameOverMenuScene.buildAnimations();  
    showMenu(gameOverMenuScene);	  
  }
  
  public void showNextLevelMenuScene(){
	nextLevelMenuScene = initMenuScene();
	nextLevelMenuScene.addMenuItem(initMenuItem("NEXT", NEXT));
	nextLevelMenuScene.addMenuItem(initMenuItem("QUIT", QUIT));
	//nextLevelMenuScene.buildAnimations();  
    showMenu(nextLevelMenuScene);	  
  }

  
  
  private static final int[] STATIC_BUILDING = {
    Building.PLANT,	  
    Building.WATER,
    Building.FOOD,
    Building.TELEVISION
  };
  
  public void setBuildingList(int[] buildingTypeList){
    chooseBuildingMenuScene = initMenuScene();
    //chooseBuildingMenuScene.addMenuItem(initMenuItem("CONTINUE", CONTINUE));
    chooseBuildingMenuScene.addMenuItem(initMenuItem("CANCEL", CANCEL));
    for(int i = 0; i < STATIC_BUILDING.length;i++){
      chooseBuildingMenuScene.addMenuItem(initMenuItem(""+STATIC_BUILDING[i], STATIC_BUILDING[i])); 	    
    }
    for(int i = 0; i < buildingTypeList.length;i++){
      chooseBuildingMenuScene.addMenuItem(initMenuItem(""+buildingTypeList[i], buildingTypeList[i])); 	    
    }
    
    //chooseBuildingMenuScene.buildAnimations();	  
  }
  
  
  
  

  @Override
  public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
		float pMenuItemLocalX, float pMenuItemLocalY) {
	switch(pMenuItem.getID()){
	  case BUY:
	  case SELL:
	    upgradeType = pMenuItem.getID();
	    chooseUpgradeTypeMenuScene.back();
	    showChooseBuildingMenuScene();
   	  break;
	  case CANCEL:
	    chooseBuildingMenuScene.back();
	    showUpgradeMenu();
	  
	  break;
	  case CONTINUE:
		listener.onContinue();  
		chooseBuildingMenuScene.back();		
	  break;
	  case RESTART:
	    listener.onRestart();
	    gameOverMenuScene.back();	    
	  break;
	  case NEXT:
	    listener.onNextLevel();
	    nextLevelMenuScene.back();
	  break;
	  case QUIT:
	    listener.onQuitGame();
	  break;
	  
	  default:
	    if(upgradeType == BUY){
	      listener.onBuy(pMenuItem.getID());	        
	    }else{
	      listener.onSell(pMenuItem.getID());
	        
	    }  	  
	    chooseBuildingMenuScene.back(); 
	    //showUpgradeMenu();
   	  
	}
	return false;
  }
  
  
  
  	
  public interface InGameMenuSceneListener{
    public boolean onBuy(int buildingType);	  
    public boolean onSell(int buildingType);
    public void onContinue();
    public void onRestart();
    public void onNextLevel();
    public void onQuitGame();
  }
}
