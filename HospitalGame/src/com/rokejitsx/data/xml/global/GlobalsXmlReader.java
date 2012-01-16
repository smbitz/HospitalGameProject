package com.rokejitsx.data.xml.global;

import android.util.Log;

import com.rokejitsx.R;
import com.rokejitsx.data.xml.DataHolder;
import com.rokejitsx.data.xml.DataXmlReader;
import com.rokejitsx.data.xml.global.giactionpoint.GiActionPointFullBuilding;
import com.rokejitsx.data.xml.global.giactionpoint.GiActionPointMidBuilding;
import com.rokejitsx.data.xml.global.giactionpoint.GiActionPointMiniBuilding;
import com.rokejitsx.util.StringUtil;


public class GlobalsXmlReader extends DataXmlReader{
  
  public final static String GLOBAL_GI_ACTION_POINT  			    = "giActionPoint";  	
  public final static String GLOBAL_GI_ACTION_POINT_BED  			= "giActionPointBed";
  public final static String GLOBAL_GI_ACTION_POINT_CHAIR	  		= "giActionPointChair";
  public final static String GLOBAL_GI_ACTION_POINT_XRAY	  		= "giActionPointXRay";
  public final static String GLOBAL_GI_ACTION_POINT_PHARMACY  		= "giActionPointPharmacy";
  public final static String GLOBAL_GI_ACTION_POINT_GLASSDOOR  		= "giActionPointGlassDoor";
  public final static String GLOBAL_GI_ACTION_POINT_QUICKTREAT		= "giActionPointQuickTreat";
  public final static String GLOBAL_GI_ACTION_POINT_TRIAGE  		= "giActionPointTriage";
  public final static String GLOBAL_GI_ACTION_POINT_CAT  	  		= "giActionPointCAT";  
  public final static String GLOBAL_GI_ACTION_POINT_PHYSIOTHERAPY 	= "giActionPointPhysiotherapy";  
  public final static String GLOBAL_GI_ACTION_POINT_OPHTHALMOLOGY 	= "giActionPointOphthalmology";
  public final static String GLOBAL_GI_ACTION_POINT_PSYCHIATRY	 	= "giActionPointPsychiatry";
  public final static String GLOBAL_GI_ACTION_POINT_CHEMOTHERAPY 	= "giActionPointChemotherapy";
  public final static String GLOBAL_GI_ACTION_POINT_BABYSCAN	 	= "giActionPointBabyscan";  
  public final static String GLOBAL_GI_ACTION_POINT_DENTIST		 	= "giActionPointDentist";
  public final static String GLOBAL_GI_ACTION_POINT_CARDIOLOGY	 	= "giActionPointCardiology";
  public final static String GLOBAL_GI_ACTION_POINT_OPERATION	 	= "giActionPointOperation";
  public final static String GLOBAL_GI_ACTION_POINT_ULTRA_SCAN	 	= "giActionPointUltraScan";
  public final static String GLOBAL_GI_ACTION_POINT_ELEVATOR		= "giActionPointElevator";
  public final static String GLOBAL_GI_CARRIABLE_CONTAINER 		 	= "giCarriableContainer";
  public final static String GLOBAL_GI_HUD				 		 	= "giHUD";
  public final static String GLOBAL_GI_HUD_ALERT		 		 	= "giHUDAlert";
  public final static String GLOBAL_GI_HUD_OBJECTIVES		 		= "giHUDObjectives";  
  public final static String GLOBAL_GI_NURSE			 			= "giNurse";
  public final static String GLOBAL_GI_PATIENT			 			= "giPatient";  
  public final static String GLOBAL_GI_AMBULANCE		 			= "giAmbulance";
  public final static String GLOBAL_GI_GAME				 			= "giGame";
  public final static String GLOBAL_GI_MENU				 			= "giMenu"; 
  public final static String GLOBAL_GI_END_SCREEN		 			= "giEndScreen";
  public final static String GLOBAL_GI_WORKSHOP     	 			= "giWorkshop";
  public final static String GLOBAL_GI_TUTORIAL     	 			= "giTutorial";
  public final static String GLOBAL_GI_TEXT_INPUT_BOX  	 			= "giTextInputBox";  
  public final static String GLOBAL_GI_PROFILE 		  	 			= "giProfile";
  public final static String GLOBAL_GI_MESSAGE_BOX	  	 			= "giMessageBox";
  public final static String GLOBAL_GI_MACHINE_SCROLLER	 			= "giMachineScroller";
  public final static String GLOBAL_GI_ACTION_POINT_PLANT			= "giActionPointPlant";
  public final static String GLOBAL_GI_ACTION_POINT_FOOD			= "giActionPointFood";
  public final static String GLOBAL_GI_ACTION_POINT_WATER			= "giActionPointWater";
  public final static String GLOBAL_GI_ACTION_POINT_TELEVISION		= "giActionPointTelevision";  
  public final static String GLOBAL_GI_MAP_SCREEN					= "giMapScreen";
  public final static String GLOBAL_GI_STRETCHER					= "giStretcher";
  public final static String GLOBAL_GI_MENU_HIGHSCORES				= "giMenuHighscores";
  public final static String GLOBAL_SLIDERS         				= "Sliders";
  public final static String GLOBAL_LETTER           				= "Letter";
  public final static String GLOBAL_GI_HELICOPTER      				= "giHelicopter";
  public final static String GLOBAL_GI_SCALE_BUMP      				= "giScaleBump";
  public final static String GLOBAL_ENDLESS_MODE      				= "EndlessMode";
	
  private final static String[] globalList = {
    GLOBAL_GI_ACTION_POINT_BED, 			//0 GiActionPointMidBuilding
    GLOBAL_GI_ACTION_POINT_CHAIR,			//1 GiActionPointChair
    GLOBAL_GI_ACTION_POINT_XRAY,			//2 GiActionPointFullBuilding
    GLOBAL_GI_ACTION_POINT_PHARMACY,		//3 GiActionPointPharmacy
    GLOBAL_GI_ACTION_POINT_GLASSDOOR,		//4 GiActionPointGlassDoor
    GLOBAL_GI_ACTION_POINT_QUICKTREAT,		//5 GiActionPointQuickTreat
    GLOBAL_GI_ACTION_POINT_TRIAGE,			//6 GiActionPointMiniBuilding
    GLOBAL_GI_ACTION_POINT_CAT,				//7 GiActionPointFullBuilding
    GLOBAL_GI_ACTION_POINT_PHYSIOTHERAPY,	//8 GiActionPointFullBuilding
    GLOBAL_GI_ACTION_POINT_OPHTHALMOLOGY,	//9 GiActionPointFullBuilding
    GLOBAL_GI_ACTION_POINT_PSYCHIATRY,		//10 GiActionPointFullBuilding
    GLOBAL_GI_ACTION_POINT_CHEMOTHERAPY,	//11 GiActionPointFullBuilding
    GLOBAL_GI_ACTION_POINT_BABYSCAN,		//12 GiActionPointFullBuilding
    GLOBAL_GI_ACTION_POINT_DENTIST,			//13 GiActionPointFullBuilding
    GLOBAL_GI_ACTION_POINT_CARDIOLOGY,		//14 GiActionPointFullBuilding
    GLOBAL_GI_ACTION_POINT_OPERATION,		//15 GiActionPointFullBuilding
    GLOBAL_GI_ACTION_POINT_ULTRA_SCAN,		//16 GiActionPointFullBuilding
    GLOBAL_GI_ACTION_POINT_ELEVATOR,		//17 GiActionPointElevator
    GLOBAL_GI_CARRIABLE_CONTAINER,			//18 GiCarriableContainer
    GLOBAL_GI_HUD,							//19 GiHUD
    GLOBAL_GI_HUD_ALERT,					//20 GiHUDAlert
    GLOBAL_GI_HUD_OBJECTIVES,				//21 GiHUDObjectives
    GLOBAL_GI_NURSE,						//22 GiNurse
    GLOBAL_GI_PATIENT,						//23 GiPatient
    GLOBAL_GI_AMBULANCE,					//24 GiAmbulance
    GLOBAL_GI_GAME,							//25 GiGame
    GLOBAL_GI_MENU,							//26 GiMenu
    GLOBAL_GI_END_SCREEN,					//27 GiEndScreen
    GLOBAL_GI_WORKSHOP,						//28 GiWorkShop
    GLOBAL_GI_TUTORIAL,						//29 GiTutorial
    GLOBAL_GI_TEXT_INPUT_BOX,				//30 GiTextInputBox
    GLOBAL_GI_PROFILE,						//31 GiProfile
    GLOBAL_GI_MESSAGE_BOX,					//32 GiMessageBox
    GLOBAL_GI_MACHINE_SCROLLER,				//33 GiMachineScroller
    GLOBAL_GI_ACTION_POINT_PLANT,			//34 GiActionPointObjects
    GLOBAL_GI_ACTION_POINT_FOOD,			//35 GiActionPointObjects
    GLOBAL_GI_ACTION_POINT_WATER,			//36 GiActionPointObjects
    GLOBAL_GI_ACTION_POINT_TELEVISION,		//37 GiActionPointObjects
    GLOBAL_GI_MAP_SCREEN,					//38 GiMapScreen
    GLOBAL_GI_STRETCHER,					//39 GiStretcher
    GLOBAL_GI_MENU_HIGHSCORES,				//40 GiMenuHighscores
    GLOBAL_SLIDERS,							//41 Sliders
    GLOBAL_LETTER,							//42 Letter
    GLOBAL_GI_HELICOPTER,					//43 GiHelicopter
    GLOBAL_GI_SCALE_BUMP,					//44 GiScaleBump
    GLOBAL_ENDLESS_MODE,					//45 EndlessMode
    GLOBAL_GI_ACTION_POINT                  //46 GiActionPoint
  };
  
  private DataHolder[] datas;
  
  public GlobalsXmlReader(){
    super(R.xml.globals);
    datas = new DataHolder[globalList.length];
  }

  @Override
  public DataHolder parseData(String tagName) {
    int index = StringUtil.stringIndexInStringArray(tagName, globalList);
    if(index == -1)
      return null;
    DataHolder dHolder = null;
    switch(index){
      case 0:
        dHolder = new GiActionPointMidBuilding(globalList[index]);
      break;
      case 1:
        dHolder = new GiActionPointChair();	  
      break;
      case 2:      
        dHolder = new GiActionPointFullBuilding(globalList[index]);	  
      break;
      case 3:
        dHolder = new GiActionPointPharmacy();	  
      break;
      case 4:
        dHolder = new GiActionPointGlassDoor();	  
      break;
      case 5:
    	dHolder = new GiActionPointQuickTreat(); 
      break;
      case 6:
        dHolder = new GiActionPointMiniBuilding(globalList[index]); 
      break;
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
        dHolder = new GiActionPointFullBuilding(globalList[index]);	  
      break;
      case 17:
        dHolder = new GiActionPointElevator(); 
      break;
      case 18:
        dHolder = new GiCarriableContainer(); 
      break;
      case 19:
        dHolder = new GiHUD(); 
      break;
      case 20:
        dHolder = new GiHUDAlert(); 
      break;
      case 21:
        dHolder = new GiHUDObjectives(); 
      break;
      case 22:
        dHolder = new GiNurse(); 
      break;
      case 23:
        dHolder = new GiPatient(); 
      break;
      case 24:
        dHolder = new GiAmbulance(); 
      break;
      case 25:
        dHolder = new GiGame(); 
      break;
      case 26:
        dHolder = new GiMenu(); 
      break;
      case 27:
        dHolder = new GiEndScreen(); 
      break;
      case 28:
        dHolder = new GiWorkShop(); 
      break;
      case 29:
        dHolder = new GiTutorial(); 
      break;
      case 30:
        dHolder = new GiTextInputBox(); 
      break;
      case 31:
        dHolder = new GiProfile(); 
      break;
      case 32:
        dHolder = new GiMessageBox(); 
      break;
      case 33:
        dHolder = new GiMachineScroller(); 
      break;
      case 34:
      case 35:
      case 36:
      case 37:
        dHolder = new GiActionPointObjects(globalList[index]); 
      break;
      case 38:
        dHolder = new GiMapScreen(); 
      break;
      case 39:
        dHolder = new GiStretcher(); 
      break;
      case 40:
        dHolder = new GiMenuHighscores(); 
      break;
      case 41:
        dHolder = new Sliders(); 
      break;
      case 42:
        dHolder = new Letter(); 
      break;
      case 43:
        dHolder = new GiHelicopter(); 
      break;
      case 44:
        dHolder = new GiScaleBump(); 
      break;
      case 45:
        dHolder = new EndlessMode(); 
      break;      
      case 46:
        dHolder = new GiActionPoint(); 
      break;
    }
    
    
    datas[index] = dHolder;
    return dHolder;
	
  } 
  
  
  public void print(){
    for(int i = 0;i < datas.length;i++){
      DataHolder dHolder = datas[i];
      if(dHolder == null){
        Log.d("Rokejits", "datas at "+i+" = null");	  
      }else{
        dHolder.print();	  
      }
    }	  
  } 
  
}
