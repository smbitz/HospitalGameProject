package com.rokejitsx.ui.hospital;

import java.util.Enumeration;
import java.util.Vector;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.kazekim.andengine.extend.BitmapTextureAtlasEx;
import com.kazekim.ui.TextButton;
import com.rokejitsx.HospitalGameActivity;
import com.rokejitsx.data.GameFonts;
import com.rokejitsx.data.resource.ImageResource;
import com.rokejitsx.data.resource.ResourceManager;
import com.rokejitsx.save.GameStatManager;
import com.rokejitsx.ui.building.elevator.ElevatorFloorSelector;
import com.rokejitsx.ui.building.elevator.ElevatorFloorSelector.ElevatorSelectorListener;
import com.rokejitsx.ui.hospital.HospitalFloorSelector.FloorSelectListener;
import com.rokejitsx.ui.hospital.HospitalGamePlay.HospitalListener;
import com.rokejitsx.ui.hospital.HospitalTimer.HospitalTimerListener;
import com.rokejitsx.ui.item.Item;
import com.rokejitsx.ui.patient.Patient;

public class HospitalUI extends Entity implements HospitalListener, ImageResource{
  private static int UI_BTN_CANCEL 		= 0;
  private static int UI_BTN_MENU	    = 1;
  private HospitalStat hospitalStat;	
  private HospitalTimer timer;
  private HospitalFloorSelector hospitalFloorSelector;
  private ElevatorFloorSelector elevatorFloorSelector;
  private Vector<PatientDoughnut> patientDoughnutList;
  private HospitalUIListener listener;
  private HospitalUIItemListener doughtnutListener;
  
  private ItemDoughnut[] itemDoughnutList;
  
  private TextButton cancelBtn, menuBtn;
  private BitmapTextureAtlasEx layoutBitmapTextureAtlas2;
  
  public HospitalUI(int maxFloor){	
	
	patientDoughnutList = new Vector<PatientDoughnut>();
	itemDoughnutList = new ItemDoughnut[5];
	
	for(int i = 0;i < itemDoughnutList.length;i++){
	  itemDoughnutList[i] = new ItemDoughnut();	
	}
	
	AnimatedSprite itemBar = new AnimatedSprite(0, 0, ResourceManager.getInstance().getTexture(ITEM_TABLE));
	itemBar.setScaleX(0.5f);
	itemBar.setScaleY(0.75f);
	itemBar.setPosition(400 - itemBar.getBaseWidth()/2, 600 - itemBar.getHeightScaled());
	attachChild(itemBar);
	
	ItemDoughnut itemDo = itemDoughnutList[0];
	float x = 400 - (itemDo.getWidth() * 2 + itemDo.getWidth()/2 + 20);
	float y = itemBar.getY() - itemDo.getHeight() / 2 + 10;
	
	for(int i = 0;i < itemDoughnutList.length;i++){
	  itemDoughnutList[i].setPosition(x, y);
	  x += itemDoughnutList[i].getWidth() + 10;
	  attachChild(itemDoughnutList[i]);
    }
	
	
	
	
	int cameraWidth = HospitalGameActivity.getGameActivity().getCameraWidth();
	int cameraHeight = HospitalGameActivity.getGameActivity().getCameraHeight();
	
	hospitalStat = new HospitalStat();	
	hospitalStat.setThreatedPatient(0);
    timer = new HospitalTimer();	
    attachChild(timer);
    timer.setPosition(cameraWidth - timer.getWidth(), 0);
    
    hospitalFloorSelector = new HospitalFloorSelector(maxFloor);	
    hospitalFloorSelector.setPosition(cameraWidth - hospitalFloorSelector.getWidth() - 15, cameraHeight - hospitalFloorSelector.getHeight() - 50);
    
    this.elevatorFloorSelector = new ElevatorFloorSelector(maxFloor);
    onHideElevetorSelector();
    if(maxFloor > 1)
      attachChild(hospitalFloorSelector);
    attachChild(elevatorFloorSelector);
    attachChild(hospitalStat);
    
    x = hospitalStat.getX() + hospitalStat.getWidth() + 5;
    for(int i = 0;i < 9;i++){
      AnimatedSprite star = new AnimatedSprite(x, 0, ResourceManager.getInstance().getTexture(INTERFACE_STAR)); 
      if(i <= GameStatManager.getInstance().getHospitalLevel()){
        star.setCurrentTileIndex(1);	  
      }	
      attachChild(star);
      x += star.getBaseWidth() + 5;
    }
    
    
    layoutBitmapTextureAtlas2 = new BitmapTextureAtlasEx(1024, 128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("media/textures/gamemenu/");
	HospitalGameActivity.getGameActivity().getEngine().getTextureManager().loadTexture(layoutBitmapTextureAtlas2);
	
	TiledTextureRegion titleBoxTextureRegion =layoutBitmapTextureAtlas2.appendTiledAsset(HospitalGameActivity.getGameActivity(), "montagemediobutton.png", 3, 1);
	
	
	cancelBtn = new TextButton(0, 0, titleBoxTextureRegion.deepCopy(),GameFonts.getInstance().getMenuFont(GameFonts.MENU_LCD_FONT_20_WHITE),"Cancel");   
	cancelBtn.setPosition(800 - cancelBtn.getBaseWidth(), 600 - cancelBtn.getBaseHeight());
	
	menuBtn = new TextButton(0, 0, titleBoxTextureRegion.deepCopy(),GameFonts.getInstance().getMenuFont(GameFonts.MENU_LCD_FONT_20_WHITE),"Menu");   
	menuBtn.setPosition(800 - menuBtn.getBaseWidth(), 600 - menuBtn.getBaseHeight());
	
	menuBtn.setVisible(false);	
	cancelBtn.setVisible(true);
	
	attachChild(cancelBtn);
	attachChild(menuBtn);
    
  }	
  
  public void setHospitalUIListner(HospitalUIListener listener){
    this.listener = listener;	  
  }
  
  public void setHospitalUIItemListener(HospitalUIItemListener listener){
    this.doughtnutListener = listener;	  
  }
  
  public void unload(){
    HospitalGameActivity.getGameActivity().sendUnloadTextureAtlas(layoutBitmapTextureAtlas2);	  
  }
  
  /*public void upgrade(){	 
	uiBtn.setVisible(true);
	if(!uiBtn.hasParent())
      HospitalGameActivity.getGameActivity().sendAttachChild(this, uiBtn);  
  }*/
  
  /*private void onUiBtnClicked(){
	if(uiBtn.getTitle().equals("Cancel")){
      listener.onUiBtnClicked(HospitalUIListener.BTN_CANCEL);
	}else{
	  //show pause menu	
	  listener.onUiBtnClicked(HospitalUIListener.BTN_MENU);
	}
  }*/
  
  public void setMoney(int money){
    hospitalStat.setMoney(money);	  
  }
  
  public int getMoney(){
    return hospitalStat.getMoney();	  
  }
  
  public int getGoalPatient(){
    return hospitalStat.getGoalPatient();	  
  }
  
  public int getTreatedPatient(){
    return hospitalStat.getTreatedPatient();	  
  }
  
  public void setGoalPatient(int num){
	hospitalStat.setGoalPatient(num);  
  }
  
  public void setExpertPatient(int num){
	hospitalStat.setExpertPatient(num);	  
  }

  
  public void reset(){
	timer.stop();
	timer.reset();
    hospitalStat.setMoney(0);
    hospitalStat.setThreatedPatient(0);
    hospitalStat.setGoalPatient(10);    
  }
  
  public void setTime(float seconds){
    timer.setTime(seconds);	  
  }
  
  public void startTimer(){
	//uiBtn.setText("Menu");
	cancelBtn.setVisible(false);
	menuBtn.setVisible(true);
    timer.start();	  
  }
  
  public void pauseTimer(){
    timer.pause();	  
  }
  
  
  
  public void setHospitalTimerListener(HospitalTimerListener listener){
    timer.setHospitalTimerListener(listener);	  
  }
  
  public void setHospitalFloorListener(FloorSelectListener listener){
    hospitalFloorSelector.setFloorSelectListener(listener);	  
  }
  
  public void setElevetorSelectorListener(ElevatorSelectorListener listener){
    elevatorFloorSelector.setElevatorFloorSelectorListener(listener);	  
  }
  
  public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
	if(!elevatorFloorSelector.isVisible())
      if(hospitalFloorSelector.onSceneTouchEvent(pScene, pSceneTouchEvent))
        return true;
    if(elevatorFloorSelector.onSceneTouchEvent(pScene, pSceneTouchEvent))
  	  return true;
    int action = pSceneTouchEvent.getAction();
    float touchX = pSceneTouchEvent.getX();
	float touchY = pSceneTouchEvent.getY();
	if(action == TouchEvent.ACTION_DOWN){
	  if(cancelBtn.isVisible() && cancelBtn.contains(touchX, touchY)){
		listener.onUiBtnClicked(HospitalUIListener.BTN_CANCEL);
	    return true;	   
	  }else if(menuBtn.isVisible() && menuBtn.contains(touchX, touchY)){
	    listener.onUiBtnClicked(HospitalUIListener.BTN_MENU);
	    return true;	   
	  }
      for(int i = 0;i < itemDoughnutList.length;i++){
        ItemDoughnut itemDo = itemDoughnutList[i];      
        if(itemDo.contains(touchX, touchY) && itemDo.containItem()){
          if(doughtnutListener.onItemSelected(i))
            itemDo.check();
          return true;
        }
      }
	}
    
    return false;
  }

  @Override
  public void onShowElevetorSelector(float x, float y) {
	elevatorFloorSelector.setVisible(true);	
	elevatorFloorSelector.setPosition(x - elevatorFloorSelector.getWidth()/2, y);
  }

  @Override
  public void onHideElevetorSelector() {
	elevatorFloorSelector.setVisible(false);	
  }

  @Override
  public void onPatientFinishHealing(Patient patient) {
	hospitalStat.increaseMoney(patient.getBillCost());
	hospitalStat.increaseThreatedPatient(1);
	
  }

  @Override
  public void addPatientDoughnut(Patient patient) {
	PatientDoughnut doughNut = new PatientDoughnut(patient);
	doughNut.setPosition(0, 600);
	patientDoughnutList.add(doughNut);	
	attachChild(doughNut);
	updateDoughnutPosition();
	
  }

  @Override
  public void removePatientDoughnut(Patient patient) {
    Enumeration<PatientDoughnut> e = patientDoughnutList.elements();
    PatientDoughnut doughnut = null;
    while(e.hasMoreElements()){
      PatientDoughnut pDo = e.nextElement();
      if(pDo.getCurrentPatient().equals(patient)){
        doughnut = pDo;
        break;
      }
    		  
    }    
    patientDoughnutList.remove(doughnut);
    detachChild(doughnut);
    updateDoughnutPosition();
	
  }
  
  private void updateDoughnutPosition(){
    float x = 0;
    float y = hospitalStat.getX() + hospitalStat.getHeight();
    Enumeration<PatientDoughnut> e = patientDoughnutList.elements();
    while(e.hasMoreElements()){
      PatientDoughnut pDo = e.nextElement();
      pDo.moveTo(x, y);
      y += pDo.getHeight();
    }  
  }

  @Override
  public void addItemOndesk(Item item, int index) {
    itemDoughnutList[index].setItem(item);	
	
  }

  @Override
  public void removeItemOndesk(Item item, int index) {
    itemDoughnutList[index].clear();	
	
  }

  @Override
  public void onItemOnDeskUnCheck(int index) {
    itemDoughnutList[index].unCheck();
	
  }
  
  
	
}
