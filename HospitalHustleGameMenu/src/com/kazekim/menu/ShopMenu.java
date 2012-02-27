package com.kazekim.menu;

import java.util.ArrayList;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.view.MotionEvent;

import com.kazekim.andengine.extend.BitmapTextureAtlasEx;
import com.kazekim.data.UserMissionSkeleton;
import com.kazekim.temp.Building;
import com.kazekim.ui.ProductImageButton;
import com.kazekim.ui.TextButton;

public class ShopMenu  extends Scene {
	

	private BaseGameActivity activity;
	

	private BitmapTextureAtlasEx layoutBitmapTextureAtlas;
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas2;
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas3;
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas4;
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas6;
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas7;
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas8;
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas9;
	private BitmapTextureAtlasEx layoutBitmapTextureAtlas10;
	
	private Text buyTabTitleText;
	private Text sellTabTitleText;
	private Text expensesTabTitleText;
	private Text outfitTabTitleText;
	
	private TiledSprite shopMenuTab;
	private Sprite shopMenuBorder;
	
	//Main Children in Menu Border
	
	private TextButton currentFundsTitle;
	private TextButton currentFundsValue;
	
	private TextButton continueButton;
	
	// Buy / Sell Tab Environment
	
	 private TiledSprite arrowUp;
	 private TiledSprite arrowDown;
	 
	 private TiledSprite productDetailImage;
	 private TiledSprite outImage;
	 private TextButton buyButton; 
	 private TextButton sellButton; 
	 private Sprite detailFrame;
	 private Text productName;
	 private Text productCost;
	 private Text productDetail;
	 
	 private TiledTextureRegion productDetailTextureRegion;
	 
	 private ProductImageButton[] productSelectButton;
	 
	 private int maxBuyPage=1;
	 private int curBuyPage=1;
	 private int maxSellPage=1;
	 private int curSellPage=1;
	 private ArrayList<Integer> buyItems;
	 private ArrayList<Integer> numItems;
	 private ArrayList<Integer> currentItems;
	
	 // Expense  Tab Environment
	 
	 private int pharmacyValue = 0;
	 private int salariesValue = 0;
	 private int totalValue = 0;
	 
	 private TextButton pharmacyExpense;
	 private TextButton salariesExpense;
	 private TextButton totalExpense;
	 
	 private TextButton pharmacyValueScreen;
	 private TextButton salariesValueScreen;
	 private TextButton totalValueScreen;
	 
	 private Sprite pharmacyScrollButton;
	 private Sprite salariesScrollButton;
	
	 
	 // Outfit Tab Environment
	 
	 private int redValue= 0;
	 private int greenValue= 0;
	 private int blueValue= 0;
	 
	 private TextButton redOutfitScroll;
	 private TextButton greenOutfitScroll;
	 private TextButton blueOutfitScroll;
	 
	//Other Environment
	 
	 private int scrollStartX;
	 private int scrollEndX;
	 
	 private TiledTextureRegion screenValueTextureRegion;
	 private TiledTextureRegion textTitleTextureRegion;
	 private TiledTextureRegion colorTextureRegion;
	 private TextureRegion scrollButtonTextureRegion;
	 
	 
	 private int oldActivatedTab=-1;
	  
	  private ShopMenu shopMenu;
	  
	  private BitmapTextureAtlas mFontTexture;
	  private Font lcdFont;
	  
	  private ShopMenuListener listener;
	  
	  private int buildingNumber=Building.NONE;
	  
	  public static final int BUYTAB=0;
	  public static final int SELLTAB=1;
	  public static final int EXPENSESTAB=2;
	  public static final int OUTFITTAB=3;
	  
	  private boolean isLoadFinish=true;
	  
	  private UserMissionSkeleton skeleton;

	public ShopMenu(BaseGameActivity activity){
		this.activity=activity;
		this.shopMenu = this;
		
		setFont();
		setBackgroundEnabled(false);
		
		newBuyItemList();
		newCurrentItemList();
		
		skeleton = UserMissionSkeleton.getInstance();
		buyItems = new ArrayList<Integer>();
		for(int i=0;i<skeleton.getMissionStation().size();i++){
			addBuyItem(skeleton.getMissionStationAtIndex(i));
		}
		setExpenseValueScreen(skeleton.getPharmacyCurValue(), skeleton.getSalaryCurValue(), skeleton.getPharmacyCurValue()+skeleton.getSalaryCurValue());
		
		layoutBitmapTextureAtlas7 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas7);
		
		colorTextureRegion =layoutBitmapTextureAtlas7.appendTiledAsset(activity, "sliderback.png", 1, 1);
		
		layoutBitmapTextureAtlas = new BitmapTextureAtlasEx(4096, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas);
		
		TiledTextureRegion shopTabTextureRegion =layoutBitmapTextureAtlas.appendTiledAsset(activity, "workshop_background_tab.png",4,1);
		shopMenuTab = new TiledSprite(0,0 , shopTabTextureRegion){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(isLoadFinish){
					int myEventAction = pSceneTouchEvent.getAction(); 
			        switch (myEventAction) {
			           case MotionEvent.ACTION_DOWN:
			        	break;
			           case MotionEvent.ACTION_MOVE: {
			            	break;}
			           case MotionEvent.ACTION_UP:
			        	   isLoadFinish=false;
			        	   if(pTouchAreaLocalX<shopMenuTab.getWidth()/4){
			        		   shopMenuTab.setCurrentTileIndex(0);
			        		   oldActivatedTab=BUYTAB;
			        		   curBuyPage=1;
			        		   changeBuyPage(curBuyPage);
			        		   activateDealTab();
			        		   
			        	   }else if(pTouchAreaLocalX<shopMenuTab.getWidth()/2){
			        		   shopMenuTab.setCurrentTileIndex(1);
			        		   oldActivatedTab=SELLTAB;
			        		   curSellPage=1;
			        		   changeSellPage(curSellPage);
			        		   activateDealTab();
			
			        	   }else if(pTouchAreaLocalX<shopMenuTab.getWidth()*3/4){
			        		   shopMenuTab.setCurrentTileIndex(2);
			        		   activateExpensesTab();
			        	   }else{
			        		   shopMenuTab.setCurrentTileIndex(3);
			        		   activateOutfitTab();
			        	   }
			        	   
			        	   
	
			                break;
			        }
				}
				return true;
				
			}
	   };	
		shopMenuTab.setPosition((InitialVal.CAMERA_WIDTH-shopMenuTab.getBaseWidth())/2,10);
		shopMenuTab.setScale(1);
		attachChild(shopMenuTab);
		registerTouchArea(shopMenuTab);
		
		layoutBitmapTextureAtlas3 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas3);
		
		TextureRegion menuBorderTextureRegion1 =layoutBitmapTextureAtlas3.appendTextureAsset(activity, "workshop_background.png");
		shopMenuBorder = new Sprite(0,0 , menuBorderTextureRegion1);	
		shopMenuBorder.setPosition((InitialVal.CAMERA_WIDTH-shopMenuBorder.getBaseWidth())/2,((int)(10+shopMenuTab.getHeight())));
		shopMenuBorder.setScale(1);
		attachChild(shopMenuBorder);
		
		layoutBitmapTextureAtlas2 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas2);
		
		textTitleTextureRegion = layoutBitmapTextureAtlas2.appendTiledAsset(activity, "montagemediobutton.png", 3, 1);  
		continueButton = new TextButton(0, 0, textTitleTextureRegion,lcdFont,"Continue"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int myEventAction = pSceneTouchEvent.getAction(); 
		        switch (myEventAction) {
		           case MotionEvent.ACTION_DOWN:
		        	   continueButton.setCurrentTileIndex(1);
		        	break;
		           case MotionEvent.ACTION_MOVE: {
		            	break;}
		           case MotionEvent.ACTION_UP:
		        	   continueButton.setCurrentTileIndex(0);
		        	   back();
						listener.onContinueButtonClick(shopMenu);
		                break;
		        }
		        
				return true;
				
			}
	   };
	   continueButton.setCurrentTileIndex(0);
	   continueButton.setColor(0, 0, 0);
	   continueButton.setPosition(shopMenuBorder.getWidth()-continueButton.getWidth()-20, shopMenuBorder.getHeight()-continueButton.getHeight());
	   continueButton.setScale(1);
	   shopMenuBorder.attachChild(continueButton);
	   registerTouchArea(continueButton);
		
	   buyTabTitleText = new Text(0, 0, lcdFont, "Buy");
	   buyTabTitleText.setPosition(((int)shopMenuTab.getWidth()/8-buyTabTitleText.getWidth()/2), shopMenuTab.getHeight()/2-buyTabTitleText.getHeight()/2);
	   buyTabTitleText.setColor(0.0f, 0.0f, 0.0f);
	   shopMenuTab.attachChild(buyTabTitleText);
		
		sellTabTitleText = new Text(0, 0, lcdFont, "Sell");
		sellTabTitleText.setPosition(((int)shopMenuTab.getWidth()/2-shopMenuTab.getWidth()/8-sellTabTitleText.getWidth()/2), shopMenuTab.getHeight()/2-sellTabTitleText.getHeight()/2);
		sellTabTitleText.setColor(0.0f, 0.0f, 0.0f);
		shopMenuTab.attachChild(sellTabTitleText);
		
		expensesTabTitleText = new Text(0, 0, lcdFont, "Expenses");
		expensesTabTitleText.setPosition(((int)shopMenuTab.getWidth()*3/4-shopMenuTab.getWidth()/8-expensesTabTitleText.getWidth()/2), shopMenuTab.getHeight()/2-expensesTabTitleText.getHeight()/2);
		expensesTabTitleText.setColor(0.0f, 0.0f, 0.0f);
		shopMenuTab.attachChild(expensesTabTitleText);
		
		outfitTabTitleText = new Text(0, 0, lcdFont, "Outfit");
		outfitTabTitleText.setPosition(((int)shopMenuTab.getWidth()-shopMenuTab.getWidth()/8-outfitTabTitleText.getWidth()/2), shopMenuTab.getHeight()/2-outfitTabTitleText.getHeight()/2);
		outfitTabTitleText.setColor(0.0f, 0.0f, 0.0f);
		shopMenuTab.attachChild(outfitTabTitleText);
		
		currentFundsTitle = new TextButton(0,0, textTitleTextureRegion.deepCopy(),lcdFont,"Current Funds");	
		currentFundsTitle.setPosition(shopMenuBorder.getWidth()/2-currentFundsTitle.getWidth()+20, 0);
		currentFundsTitle.setScale(1);
		currentFundsTitle.setColor(255, 255, 255);
		shopMenuBorder.attachChild(currentFundsTitle);
		
		layoutBitmapTextureAtlas4 = new BitmapTextureAtlasEx(1024, 1024,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas4);
		
		screenValueTextureRegion =layoutBitmapTextureAtlas4.appendTiledAsset(activity, "insertbuttonsmall.png", 1, 1);
		scrollButtonTextureRegion = layoutBitmapTextureAtlas4.appendTextureAsset(activity, "scrollbar.png");
		
		setFund(Integer.toString(skeleton.getFund()));

		oldActivatedTab=BUYTAB;
		activateDealTab();
		
	}
	
	public void setFund(String value){
	/*	if(currentFundsValue!=null){
			detachChild(currentFundsValue);
			currentFundsValue=null;
		}
		*/
		if(currentFundsValue==null){
			currentFundsValue = new TextButton(0,0, screenValueTextureRegion.deepCopy(),lcdFont,value);	
			currentFundsValue.setPosition(shopMenuBorder.getWidth()/2, 13);
			currentFundsValue.setScale(1);
			currentFundsValue.setColor(255, 255, 255);
			shopMenuBorder.attachChild(currentFundsValue);
			return;
		}
		currentFundsValue.changeText(lcdFont, value);
	}
	
	public void activateDealTab(){
		detachOldEnvironment();
		
		if(layoutBitmapTextureAtlas6!=null){
			shopMenuBorder.attachChild(detailFrame);
			
			
			
			if(oldActivatedTab==BUYTAB){
				shopMenuBorder.attachChild(buyButton);
				//sellButton.setVisible(false);
				buyButton.setVisible(true);
			}else{
				shopMenuBorder.attachChild(sellButton);
				sellButton.setVisible(true);
				//buyButton.setVisible(false);

			}
			
			shopMenuBorder.attachChild(arrowUp);
			shopMenuBorder.attachChild(arrowDown);
			
			for(int i=0;i<9;i++){
				shopMenuBorder.attachChild(productSelectButton[i]);
			}
			
			isLoadFinish=true;
			return;
		}
		
		layoutBitmapTextureAtlas6 = new BitmapTextureAtlasEx(4096, 4096,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas6);
		
		TextureRegion menuBorderTextureRegion =layoutBitmapTextureAtlas6.appendTextureAsset(activity, "separator.png");
		//TiledTextureRegion menuBorderTextureRegion =ResourceManager.getInstance().getTexture("media/textures/briefingmenu/menuobjectives.png");
		detailFrame = new Sprite(0,0 , menuBorderTextureRegion);	
		detailFrame.setPosition(shopMenuBorder.getWidth()/2,currentFundsTitle.getHeight());
		detailFrame.setScale(1);
		shopMenuBorder.attachChild(detailFrame);
		
		TiledTextureRegion continusButtonTextureRegion = layoutBitmapTextureAtlas6.appendTiledAsset(activity, "daminibuttons.png", 3, 1);  
			buyButton = new TextButton(0, 0, continusButtonTextureRegion,lcdFont,"Buy"){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					if(this.isVisible()){
						int myEventAction = pSceneTouchEvent.getAction(); 
				        switch (myEventAction) {
				           case MotionEvent.ACTION_DOWN:
				        	   buyButton.setCurrentTileIndex(1);
				        	break;
				           case MotionEvent.ACTION_MOVE: {
				            	break;}
				           case MotionEvent.ACTION_UP:
				        	   back();
				        	   buyButton.setCurrentTileIndex(0);
								listener.onBuyButtonClick(shopMenu,buildingNumber);
				                break;
				        }
					}
					return true;
					
				}
		   };
		   buyButton.setCurrentTileIndex(0);
		   buyButton.setColor(0, 0, 0);
		   buyButton.setPosition(shopMenuBorder.getWidth()/2+10
				   , currentFundsTitle.getHeight()+detailFrame.getHeight()-buyButton.getHeight()/2);
		   buyButton.setScale(1);
		   shopMenuBorder.attachChild(buyButton);
		   registerTouchArea(buyButton);

		   sellButton = new TextButton(0, 0, continusButtonTextureRegion,lcdFont,"Sell"){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					if(this.isVisible()){
						int myEventAction = pSceneTouchEvent.getAction(); 
				        switch (myEventAction) {
				           case MotionEvent.ACTION_DOWN:
				        	   sellButton.setCurrentTileIndex(1);
				        	break;
				           case MotionEvent.ACTION_MOVE: {
				            	break;}
				           case MotionEvent.ACTION_UP:
				        	   back();
				        	   sellButton.setCurrentTileIndex(0);
								listener.onSellButtonClick(shopMenu,buildingNumber);
				                break;
				        }
					}
					return true;
					
				}
		   };
		   sellButton.setCurrentTileIndex(0);
		   sellButton.setColor(0, 0, 0);
		   sellButton.setPosition(shopMenuBorder.getWidth()/2+10
				   , currentFundsTitle.getHeight()+detailFrame.getHeight()-sellButton.getHeight()/2);
		   sellButton.setScale(1);
		 //  shopMenuBorder.attachChild(sellButton);
		   registerTouchArea(sellButton);
		   
		   TiledTextureRegion outTextureRegion = layoutBitmapTextureAtlas6.appendTiledAsset(activity, "montage_cantBuy_EN.png", 2, 1);
		   outImage = new TiledSprite(0, 0, outTextureRegion);
		   outImage.setPosition(detailFrame.getX()+detailFrame.getWidth()/2-outImage.getWidth()/2,detailFrame.getY()+ detailFrame.getHeight()/2-outImage.getHeight()/2);
		   outImage.setScale(1);
		   outImage.setCurrentTileIndex(0);
		   shopMenuBorder.attachChild(outImage);
		   
	   
	   TiledTextureRegion upArrowTextureRegion = layoutBitmapTextureAtlas6.appendTiledAsset(activity, "buttonup.png", 2, 1);  
		arrowUp = new TiledSprite(0, 0, upArrowTextureRegion){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int myEventAction = pSceneTouchEvent.getAction(); 
		        switch (myEventAction) {
		           case MotionEvent.ACTION_DOWN:
		        	   arrowUp.setCurrentTileIndex(1);
		        	break;
		           case MotionEvent.ACTION_MOVE: {
		            	break;}
		           case MotionEvent.ACTION_UP:
		        	   arrowUp.setCurrentTileIndex(0);
		        	   curBuyPage--;
		        	   if(curBuyPage>=1){

		        		   changeBuyPage(curBuyPage);
		        	   }else{
		        		   curBuyPage=1;
		        	   }
		                break;
		        }
		        
				return true;
				
			}
	   };
	   arrowUp.setCurrentTileIndex(0);
	   arrowUp.setPosition(shopMenuBorder.getWidth()/4-arrowUp.getWidth()/2
			   , currentFundsTitle.getHeight());
	   arrowUp.setScale(1);
	   shopMenuBorder.attachChild(arrowUp);
	   registerTouchArea(arrowUp);
	   arrowUp.setVisible(false);
	   
	   
	   TiledTextureRegion downArrowTextureRegion = layoutBitmapTextureAtlas6.appendTiledAsset(activity, "buttondown.png", 2, 1);  
		arrowDown = new TiledSprite(0, 0, downArrowTextureRegion){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
					int myEventAction = pSceneTouchEvent.getAction(); 
			        switch (myEventAction) {
			           case MotionEvent.ACTION_DOWN:
			        	   arrowDown.setCurrentTileIndex(1);
			        	break;
			           case MotionEvent.ACTION_MOVE: {
			            	break;}
			           case MotionEvent.ACTION_UP:
			        	   arrowDown.setCurrentTileIndex(0);
			        	   curBuyPage++;
			        	   if(curBuyPage<=maxBuyPage){
			        		   changeBuyPage(curBuyPage);
			        	   }else{
			        		   curBuyPage=maxBuyPage;
			        	   }
			                break;
			        }
				
		        
				return true;
				
			}
	   };
	   arrowDown.setCurrentTileIndex(0);
	   arrowDown.setPosition(shopMenuBorder.getWidth()/4-arrowDown.getWidth()/2
			   , currentFundsTitle.getHeight()+detailFrame.getHeight());
	   arrowDown.setScale(1);
	   shopMenuBorder.attachChild(arrowDown);
	   registerTouchArea(arrowDown);
	   arrowDown.setVisible(false);
	   
	   productSelectButton = new ProductImageButton[9];
	   
	   BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
	   layoutBitmapTextureAtlas8 = new BitmapTextureAtlasEx(1024, 512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas8);
		
		layoutBitmapTextureAtlas9 = new BitmapTextureAtlasEx(2048, 2048,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas9);
		
		
		

		   TiledTextureRegion productNewButtonTextureRegion = layoutBitmapTextureAtlas9.appendTiledAsset(activity, "machineupgradesbuttons.png", 2, 2); 
		 
		   layoutBitmapTextureAtlas10 = new BitmapTextureAtlasEx(1024, 512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			
		productDetailTextureRegion = layoutBitmapTextureAtlas10.appendTiledAsset(activity, "workshopObjects.png", 6, 3); 
		  
		activity.getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas10);
		   
		 TiledTextureRegion productSelectButtonTextureRegion = layoutBitmapTextureAtlas8.appendTiledAsset(activity, "machineupgradesbuttons.png", 2, 2); 
		 
	   int j=0;
	   for(int i=0;i<9;i++){
		   if(i>0 && i%3==0){
			   j++;
		   }
			
		   productSelectButton[i] = new ProductImageButton(0, 0, productSelectButtonTextureRegion.deepCopy(),productNewButtonTextureRegion.deepCopy(),productDetailTextureRegion.deepCopy(),false,Building.WATER){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					if(this.isVisible()){
						super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
						
						int myEventAction = pSceneTouchEvent.getAction(); 
				        switch (myEventAction) {
				           case MotionEvent.ACTION_UP:
				        	   setDetailPanel(getChooseIndex(),"Artificial Plant "+getBuildingIndex(), getIndexButton()*10, "Scientificially \nengineered to \nstay green and \nleafy under \nartificial hospital \nlights.");
	
				           break;
				        }
					}
					return true;
					
					
				}
		   };
		   productSelectButton[i].setIndexButton(i);
		   
		   productSelectButton[i].setCurrentTileIndex(0);
		   productSelectButton[i].setPosition(20+productSelectButton[i].getWidth()*(i%3)
				   , currentFundsTitle.getHeight()+arrowUp.getHeight()/2+productSelectButton[i].getHeight()*j);
		   productSelectButton[i].setScale(1);
		   shopMenuBorder.attachChild(productSelectButton[i]);
		   registerTouchArea(productSelectButton[i]);
		   
		  productSelectButton[i].setVisible(false);
		  
	   }
	   productDetailImage = new TiledSprite(0,0 , productDetailTextureRegion);
		//productDetailImage.setPosition(shopMenuBorder.getWidth()/2 + productDetailImage.getWidth()/2,currentFundsTitle.getHeight()+30);
	   float scale=0.6f;
	   productDetailImage.setPosition(-productDetailImage.getWidth()*scale/4,-productDetailImage.getHeight()*scale/4);
	   productDetailImage.setScale(scale);
	   
	   setDetailPanel(Building.CLOSET3,"Artificial Plant 0", 120, "Scientificially \nengineered to \nstay green and \nleafy under \nartificial hospital \nlights.");
	   
	   isLoadFinish=true;
	}
	
	public void newBuyItemList(){
		buyItems = new ArrayList<Integer>();
		numItems = new ArrayList<Integer>();
		maxBuyPage=1;
		curBuyPage=1;
		
		
	}
	
	public void addBuyItem(int buildingIndex){
		buyItems.add(buildingIndex);
		numItems.add(0);
		
		
		if(buyItems.size()<=9){
			maxBuyPage=1;
		}else{
			double temp = (buyItems.size()-9);
			maxBuyPage = 1+(int) Math.ceil(temp/3);
			checkArrowVisible();
		}
		
		
		
	}
	
	private void newCurrentItemList(){
		currentItems = new ArrayList<Integer>();
		maxSellPage=1;
		curSellPage=1;
		
	}
	
	public void setIncreaseNumItem(int buildingIndex){
		int index = buyItems.indexOf(buildingIndex);
		
		numItems.set(index, numItems.get(index)+1);
		
		if(buildingIndex==Building.PLANT){
			skeleton.decreasePlantNum();
		}else if(buildingIndex==Building.WATER){
			skeleton.decreaseWaterNum();
		}else if(buildingIndex==Building.FOOD){
			skeleton.decreaseFoodNum();
		}else if(buildingIndex==Building.BED){
			skeleton.decreaseBedNum();
		}else if(buildingIndex==Building.TELEVISION){
			skeleton.decreaseTvNum();
		}else{
			skeleton.decreaseStationNum();
		}
		
		reGenerateCurrentItemList();
	}
	
	public void setDecreaseNumItem(int buildingIndex){
		int index = buyItems.indexOf(buildingIndex);
		
		numItems.set(index, numItems.get(index)-1);
		
		if(buildingIndex==Building.PLANT){
			skeleton.increasePlantNum();
		}else if(buildingIndex==Building.WATER){
			skeleton.increaseWaterNum();
		}else if(buildingIndex==Building.FOOD){
			skeleton.increaseFoodNum();
		}else if(buildingIndex==Building.BED){
			skeleton.increaseBedNum();
		}else if(buildingIndex==Building.TELEVISION){
			skeleton.increaseTvNum();
		}else{
			skeleton.decreaseStationNum();
		}
		
		reGenerateCurrentItemList();
		
		
	}
	
	private void reGenerateCurrentItemList(){
		newCurrentItemList();
		
		int i=0;
		while(i<numItems.size()){
			if(numItems.get(i)>0){
				currentItems.add(buyItems.get(i));
			}
			i++;
		}
		
		if(currentItems.size()<=9){
			maxSellPage=1;
		}else{
			double temp = (currentItems.size()-9);
			maxSellPage = 1+(int) Math.ceil(temp/3);
			checkArrowVisible();
		}
	}
	
	public void buyItem(int buildingIndex,int price){
		setIncreaseNumItem(buildingIndex);
		
		skeleton.setFund(skeleton.getFund()-price);
		setFund(Integer.toString(skeleton.getFund()));
	}
	
	public void sellItem(int buildingIndex,int price){
		setDecreaseNumItem(buildingIndex);
		
		skeleton.setFund(skeleton.getFund()+price);
		setFund(Integer.toString(skeleton.getFund()));
	}
	
	public void checkArrowVisible(){
		if(oldActivatedTab==BUYTAB){
			if(maxBuyPage>1){
				arrowDown.setVisible(true);
				arrowUp.setVisible(true);
			}else{
				arrowDown.setVisible(false);
				arrowUp.setVisible(false);
			}
		}else if(oldActivatedTab==SELLTAB){
			if(maxSellPage>1){
				arrowDown.setVisible(true);
				arrowUp.setVisible(true);
			}else{
				arrowDown.setVisible(false);
				arrowUp.setVisible(false);
			}
		}
	}
	
	private void changeBuyPage(int index){
		int selectIndex=3*(index-1);
		int i=0;
		while(i<productSelectButton.length){
			if(selectIndex<buyItems.size()){
				productSelectButton[i].changeImage(buyItems.get(selectIndex));
				
				if(skeleton.getIsNewAtIndex(i)){
					productSelectButton[i].setNewProduct(true);
				}else{
					productSelectButton[i].setNewProduct(false);
				}
				productSelectButton[i].setVisible(true);
			}else{
				productSelectButton[i].setVisible(false);
			}
			i++;
			selectIndex++;
		}
		checkArrowVisible();
	}
	
	private void changeSellPage(int index){
		int selectIndex=3*(index-1);
		int i=0;
		while(i<productSelectButton.length){
			if(selectIndex<currentItems.size()){
				productSelectButton[i].changeImage(currentItems.get(selectIndex));
				productSelectButton[i].setNewProduct(false);
				productSelectButton[i].setVisible(true);
			}else{
				productSelectButton[i].setVisible(false);
			}
			i++;
			selectIndex++;
		}
		checkArrowVisible();
	}
	
	
	public void activateExpensesTab(){
		detachOldEnvironment();
		oldActivatedTab=EXPENSESTAB;
		
		if(pharmacyExpense!=null){
			shopMenuBorder.attachChild(pharmacyExpense);
			
			shopMenuBorder.attachChild(salariesExpense);
			shopMenuBorder.attachChild(totalExpense);
			
			resetEpenseValueScreen();
			
			isLoadFinish=true;
			return;
		}
		
		pharmacyScrollButton =new Sprite(0,0 , scrollButtonTextureRegion);	
		salariesScrollButton =new Sprite(0,0 , scrollButtonTextureRegion);	
		
		
		
		pharmacyExpense = new TextButton(0,0, colorTextureRegion.deepCopy(),lcdFont,"Parnmacy"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				scrollStartX = (int) (getWidth()/2+getWidth()*3/16);
				scrollEndX = (int)(getWidth()/2+getWidth()*3/8);
				System.out.println(pTouchAreaLocalX+" "+scrollEndX+" "+scrollStartX+" "+(float)((pTouchAreaLocalX-scrollStartX)/(scrollEndX-scrollStartX)*skeleton.getSalaryMaxValue())+" "
						+(int)((float)((pTouchAreaLocalX-scrollStartX)/(scrollEndX-scrollStartX)*skeleton.getSalaryMaxValue()))+" "+scrollStartX
						+" ");
					int myEventAction = pSceneTouchEvent.getAction(); 
			        switch (myEventAction) {
			           case MotionEvent.ACTION_DOWN:
			        	break;
			           case MotionEvent.ACTION_MOVE: 
			           case MotionEvent.ACTION_UP:
			        	   if(pTouchAreaLocalX>=scrollStartX && pTouchAreaLocalX<=scrollEndX){
			        		   pharmacyScrollButton.setPosition(pTouchAreaLocalX-scrollStartX,pharmacyScrollButton.getY());
			        		   salariesValue = (int)((float)((pTouchAreaLocalX-scrollStartX)/(scrollEndX-scrollStartX)*skeleton.getSalaryMaxValue()));
			        		   skeleton.setSalaryCurValue(salariesValue);
			        	   }
			                break;
			        }
				
		        
				return true;
				
			}
	   };	
		pharmacyExpense.setPosition(shopMenuBorder.getWidth()/2-pharmacyExpense.getWidth(),currentFundsTitle.getHeight()+20);
		pharmacyExpense.setTextAlign(TextButton.LEFT);
		pharmacyExpense.setScale(1);
		pharmacyExpense.setColor(255, 255, 255);
		shopMenuBorder.attachChild(pharmacyExpense);
		registerTouchArea(pharmacyExpense);
		

		salariesExpense = new TextButton(0,0, colorTextureRegion.deepCopy(),lcdFont,"Salaries"){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				scrollStartX = (int) (getWidth()/2+getWidth()*3/16);
				scrollEndX = (int)(getWidth()/2+getWidth()*3/8);
				
					int myEventAction = pSceneTouchEvent.getAction(); 
			        switch (myEventAction) {
			           case MotionEvent.ACTION_DOWN:
			        	break;
			           case MotionEvent.ACTION_MOVE: 
			           case MotionEvent.ACTION_UP:
			        	   if(pTouchAreaLocalX>=scrollStartX && pTouchAreaLocalX<=scrollEndX){
			        		   salariesScrollButton.setPosition(pTouchAreaLocalX,salariesScrollButton.getY());
			        		   salariesValue = (int)((float)((pTouchAreaLocalX-scrollStartX)/(scrollEndX-scrollStartX)*skeleton.getSalaryMaxValue()));
			        		   skeleton.setSalaryCurValue(salariesValue);
			        	   }
			                break;
			        }
				
		        
				return true;
				
			}
	   };	
	   
	   scrollStartX = (int) (salariesExpense.getWidth()/2+salariesExpense.getWidth()*3/16);
		scrollEndX = (int)(salariesExpense.getWidth()/2+salariesExpense.getWidth()*3/8);
		
		salariesExpense.setPosition(shopMenuBorder.getWidth()/2-salariesExpense.getWidth(),currentFundsTitle.getHeight()*2+20);
		salariesExpense.setTextAlign(TextButton.LEFT);
		salariesExpense.setScale(1);
		salariesExpense.setColor(255, 255, 255);
		shopMenuBorder.attachChild(salariesExpense);
		registerTouchArea(salariesExpense);

		pharmacyScrollButton.setPosition(scrollStartX+((float)pharmacyValue/skeleton.getPharmacyMaxValue()*(scrollEndX-scrollStartX)),pharmacyExpense.getHeight()/2-pharmacyScrollButton.getHeight()/2);
		pharmacyScrollButton.setScale(1);
		pharmacyExpense.attachChild(pharmacyScrollButton);
		
		salariesScrollButton.setPosition(scrollStartX+((float)salariesValue/skeleton.getSalaryMaxValue()*(scrollEndX-scrollStartX)),salariesExpense.getHeight()/2-salariesScrollButton.getHeight()/2);
		salariesScrollButton.setScale(1);
		salariesExpense.attachChild(salariesScrollButton);
		
		
		totalExpense = new TextButton(0,0, textTitleTextureRegion.deepCopy(),lcdFont,"Total");	
		totalExpense.setPosition(shopMenuBorder.getWidth()/2-currentFundsTitle.getWidth()+20,currentFundsTitle.getHeight()*3+20);
		totalExpense.setScale(1);
		totalExpense.setColor(255, 255, 255);
		shopMenuBorder.attachChild(totalExpense);
		
		
		
		resetEpenseValueScreen();
		
		isLoadFinish=true;
	}
	
	public void setExpenseValueScreen(int pharmacy,int salaries,int total){
		this.pharmacyValue=pharmacy;
		this.salariesValue=salaries;
		this.totalValue=total;
	}
	
	public int getPharmacyExpenseValue(){
		return pharmacyValue;
	}
	
	public int getSalariesExpenseValue(){
		return salariesValue;
	}
	
	public int getTotalExpenseValue(){
		return totalValue;
	}
	
	public void resetEpenseValueScreen(){
		
		pharmacyValueScreen=null;
		salariesValueScreen=null;
		totalValueScreen=null;
		
		pharmacyValueScreen = new TextButton(0,0, screenValueTextureRegion.deepCopy(),lcdFont,Integer.toString(pharmacyValue));	
		pharmacyValueScreen.setPosition(shopMenuBorder.getWidth()/2,currentFundsTitle.getHeight()+20);
		pharmacyValueScreen.setScale(1);
		pharmacyValueScreen.setColor(255, 255, 255);
		shopMenuBorder.attachChild(pharmacyValueScreen);

		salariesValueScreen = new TextButton(0,0, screenValueTextureRegion.deepCopy(),lcdFont,Integer.toString(salariesValue));	
		salariesValueScreen.setPosition(shopMenuBorder.getWidth()/2,currentFundsTitle.getHeight()*2+20);
		salariesValueScreen.setScale(1);
		salariesValueScreen.setColor(255, 255, 255);
		shopMenuBorder.attachChild(salariesValueScreen);
		
		totalValueScreen = new TextButton(0,0, screenValueTextureRegion.deepCopy(),lcdFont,Integer.toString(totalValue));	
		totalValueScreen.setPosition(shopMenuBorder.getWidth()/2,currentFundsTitle.getHeight()*3+35);
		totalValueScreen.setScale(1);
		totalValueScreen.setColor(255, 255, 255);
		shopMenuBorder.attachChild(totalValueScreen);
	}	
	
	public void activateOutfitTab(){
		detachOldEnvironment();
		oldActivatedTab=OUTFITTAB;
		
		if(redOutfitScroll!=null){
			shopMenuBorder.attachChild(redOutfitScroll);
			
			shopMenuBorder.attachChild(greenOutfitScroll);
			shopMenuBorder.attachChild(blueOutfitScroll);
			
			
			isLoadFinish=true;
			return;
		}

		redOutfitScroll = new TextButton(0,0, colorTextureRegion.deepCopy(),lcdFont,"Red");	
		redOutfitScroll.setPosition(shopMenuBorder.getWidth()/2-redOutfitScroll.getWidth(),currentFundsTitle.getHeight()+20);
		redOutfitScroll.setTextAlign(TextButton.LEFT);
		redOutfitScroll.setScale(1);
		redOutfitScroll.setColor(255, 255, 255);
		shopMenuBorder.attachChild(redOutfitScroll);

		greenOutfitScroll = new TextButton(0,0, colorTextureRegion.deepCopy(),lcdFont,"Green");	
		greenOutfitScroll.setPosition(shopMenuBorder.getWidth()/2-greenOutfitScroll.getWidth(),currentFundsTitle.getHeight()*2+20);
		greenOutfitScroll.setTextAlign(TextButton.LEFT);
		greenOutfitScroll.setScale(1);
		greenOutfitScroll.setColor(255, 255, 255);
		shopMenuBorder.attachChild(greenOutfitScroll);

		blueOutfitScroll = new TextButton(0,0, colorTextureRegion.deepCopy(),lcdFont,"Blue");	
		blueOutfitScroll.setPosition(shopMenuBorder.getWidth()/2-blueOutfitScroll.getWidth(),currentFundsTitle.getHeight()*3+20);
		blueOutfitScroll.setTextAlign(TextButton.LEFT);
		blueOutfitScroll.setScale(1);
		blueOutfitScroll.setColor(255, 255, 255);
		shopMenuBorder.attachChild(blueOutfitScroll);
		
		isLoadFinish=true;
	}
	
	public void setOutfitColor(int red,int green,int blue){
		this.redValue = red;
		this.greenValue = green;
		this.blueValue = blue;
		
	}
	
	public int getOutfitColorRed(){
		return redValue;
	}
	
	public int getOutfitColorGreen(){
		return greenValue;
	}
	
	public int getOutfitColorBlue(){
		return blueValue;
	}
	
	public void setDetailPanel(int buildingIndex,String productNameString,int costString,String detailString){
		
		if(productName!=null){
			
			detailFrame.detachChildren();
		}
		    detailFrame.attachChild(productDetailImage);
			productDetailImage.setCurrentTileIndex(buildingIndex);
			
			productName = new Text(0, 0, lcdFont, productNameString);
			productName.setScale(0.9f);
			productName.setPosition(detailFrame.getWidth()*5/16, 20);
			detailFrame.attachChild(productName);
			
			productCost = new Text(0, 0, lcdFont, "Cost: "+Integer.toString(costString));
			productCost.setScale(0.9f);
			productCost.setPosition(detailFrame.getWidth()*5/16, 25+productName.getHeight());
			detailFrame.attachChild(productCost);
			
			productDetail = new Text(0, 0, lcdFont, detailString);
			productDetail.setScale(0.9f);
			productDetail.setPosition(detailFrame.getWidth()*5/16, 30+productName.getHeight()+productCost.getHeight());
			detailFrame.attachChild(productDetail);
			
			if(skeleton.getFund()<costString){
				outImage.setCurrentTileIndex(0);
				outImage.setVisible(true);
				buyButton.setVisible(false);
			}else if((buildingIndex==Building.PLANT && skeleton.getPlantNum()<=0) ||
					(buildingIndex==Building.WATER && skeleton.getWaterNum()<=0) ||
					(buildingIndex==Building.FOOD && skeleton.getFoodNum()<=0) ||
					(buildingIndex==Building.BED && skeleton.getBedNum()<=0) ||
					(buildingIndex==Building.TELEVISION && skeleton.getTVNum()<=0) ||
					(skeleton.getStationNum()<=0)){
					outImage.setCurrentTileIndex(1);
					outImage.setVisible(true);
					buyButton.setVisible(false);
			}else{
				outImage.setVisible(false);
				buyButton.setVisible(true);
			}
				
	}
	
	private void detachOldEnvironment(){
		
		if(oldActivatedTab>-1){
			shopMenuBorder.detachChildren();
			shopMenuBorder.attachChild(continueButton);
			shopMenuBorder.attachChild(currentFundsTitle);
			shopMenuBorder.attachChild(currentFundsValue);
			
			//oldActivatedTab=-1;
		}
	}

	public void setFont(){
		this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		FontFactory.setAssetBasePath("font/");
		this.lcdFont = FontFactory.createFromAsset(this.mFontTexture, activity, "LCD.ttf", 20, true, Color.WHITE);
		activity.getEngine().getTextureManager().loadTexture(this.mFontTexture);
		activity.getFontManager().loadFont(this.lcdFont);	 
	}
	
	public void setShopMenuListener(ShopMenuListener listener){
		this.listener= listener;
	}
	
	public int getActivateTab(){
		return oldActivatedTab;
	}
	
	public void loadNewResource(){
		if(oldActivatedTab==BUYTAB){
			curBuyPage=1;
			changeBuyPage(curBuyPage);
		}else if(oldActivatedTab==SELLTAB){
			curSellPage=1;
			changeSellPage(curSellPage);
		}
	}
}
