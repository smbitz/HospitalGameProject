package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiGame extends DataHolder{
	
  public final static String MINIMUN_PROCESS_COUNT 			= "MinimumProcessCount"; 	
  public final static String END_LEVEL_TIME_OUT 			= "EndLevelTimeout";
  public final static String MONEY_POINT_MULTIPLIER 		= "MoneyPointMultiplier";
  public final static String TIME_LEFT_FOR_LAST_PATIENT		= "TimeLeftForLastPatient";
  public final static String STAGE_COUNT 					= "StageCount";
  public final static String BLACK_FADE_SPEED 				= "BlackFadeSpeed";
  public final static String TEXT_FADE_SPEED 				= "TextFadeSpeed";
  public final static String CAMERA_SPEED 					= "CameraSpeed";
  public final static String LEVEL_ALPHA_AMOUNT 			= "LevelAlphaAmount";
  public final static String CAMERA_MOVE_FADE_IN 			= "CameraMoveFadeIn";
  public final static String CAMERA_MOVE_FADE_OUT 			= "CameraMoveFadeOut";
  public final static String LEVEL_ENTER_MULTIPLIERT 		= "LevelEnterMultiplier";
  public final static String MACHINE_SELLBACK_PERCENTAGE 	= "MachineSellbackPercentage";
  public final static String TIME_TO_CHANGE_RECIPIES 		= "TimeToChangeRecipies";
  public final static String WAIT_SCREEN_TIMEOUT 			= "WaitScreenTimeout";
  public final static String DEFAULT_MAINTENANCE_SLIDER_VALUE 	= "DefaultMaintenanceSliderValue";
  public final static String DEFAULT_PHARMACY_SLIDER_VALUE 		= "DefaultPharmacySliderValue";
  public final static String DEFAULT_SALARY_SLIDER_VALUE 		= "DefaultSalarySliderValue";
  public final static String DEFAULT_COFFEE_SLIDER_VALUE 		= "DefaultCoffeeSliderValue";
  public final static String COFFEE_START_HOSPITAL 				= "CoffeeStartHospital";
  public final static String SHELL_MOVE_SPEED 					= "ShellMoveSpeed";
  public final static String SHELL_MOVE_FADE 					= "ShellMoveFade";
  public final static String ENDLESS_MODE_MACHINE_INFO0 		= "EndlessModeMachineInfo0";
  public final static String ENDLESS_MODE_MACHINE_INFO1 		= "EndlessModeMachineInfo1";
  public final static String ENDLESS_MODE_MACHINE_INFO2 		= "EndlessModeMachineInfo2";
  public final static String MAL_PRACTICE_POINTS_LOSS 			= "MalpracticePointsLoss";
  public final static String PATIENT_ALERT_TIMEOUT 				= "PatientAlertTimeout";
  public final static String ENDLESS_EASY_HOSPITALT 			= "EndlessEasyHospital";
  public final static String ENDLESS_MEDIUM_HOSPITAL 			= "EndlessMediumHospital";
  public final static String ENDLESS_HARD_HOSPITAL 				= "EndlessHardHospital";
  
  private final static String[] keys = {
    MINIMUN_PROCESS_COUNT,
    END_LEVEL_TIME_OUT,
    MONEY_POINT_MULTIPLIER,
    TIME_LEFT_FOR_LAST_PATIENT,
    STAGE_COUNT,
    BLACK_FADE_SPEED,
    TEXT_FADE_SPEED,
    CAMERA_SPEED,
    LEVEL_ALPHA_AMOUNT,
    CAMERA_MOVE_FADE_IN,
    CAMERA_MOVE_FADE_OUT,
    LEVEL_ENTER_MULTIPLIERT,
    MACHINE_SELLBACK_PERCENTAGE,
    TIME_TO_CHANGE_RECIPIES,
    WAIT_SCREEN_TIMEOUT,
    DEFAULT_MAINTENANCE_SLIDER_VALUE,
    DEFAULT_PHARMACY_SLIDER_VALUE,
    DEFAULT_SALARY_SLIDER_VALUE,
    DEFAULT_COFFEE_SLIDER_VALUE,
    COFFEE_START_HOSPITAL,
    SHELL_MOVE_SPEED,
    SHELL_MOVE_FADE,
    ENDLESS_MODE_MACHINE_INFO0,
    ENDLESS_MODE_MACHINE_INFO1,
    ENDLESS_MODE_MACHINE_INFO2,
    MAL_PRACTICE_POINTS_LOSS,
    PATIENT_ALERT_TIMEOUT,
    ENDLESS_EASY_HOSPITALT,
    ENDLESS_MEDIUM_HOSPITAL,
    ENDLESS_HARD_HOSPITAL	  
  };
  
  public GiGame(){
    super(GlobalsXmlReader.GLOBAL_GI_GAME, keys);	  
  }
  
  
  	
}