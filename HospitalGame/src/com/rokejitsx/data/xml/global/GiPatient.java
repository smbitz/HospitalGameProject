package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class GiPatient extends DataHolder{
  public final static String UPDATE_CYCLE 							= "mUpdateCycle";  
  public final static String PATIENT_FADE_OUT_SPEED 				= "PatientFadeOutSpeed";
  public final static String PATIENT_ANGRY_LEAVING_TIME 			= "PatientAngryLeavingTime";
  public final static String PATIENT_BALLOON_TIME 					= "PatientBalloonTime";
  public final static String PATIENT_BALLOON_FADE_TIME 				= "PatientBalloonFadeTime";
  public final static String PATIENT_BALLOON_FADE_SPEED 			= "PatientBalloonFadeSpeed";
  public final static String PATIENT_MAX_HEALTH 					= "PatientMaxHealth";
  public final static String PATIENT_MAX_HEARTS 					= "PatientMaxHearts";
  public final static String PATIENT_SPEED 							= "PatientSpeed";
  public final static String PATIENT_MAX_ID 						= "PatientMaxId";
  public final static String PATIENT_HEALTH_FLY_SPEED 				= "PatientHealthFlySpeed";
  public final static String PATIENT_HEALTH_FLY_MOVE_FADE_IN 		= "PatientHealthFlyMoveFadeIn";
  public final static String PATIENT_HEALTH_FLY_MOVE_FADE_OUT 		= "PatientHealthFlyMoveFadeOut";
  public final static String PATIENT_HEALTH_FLY_MOVE_FADE_SPEED 	= "PatientHealthFlyFadeSpeed";
  public final static String PATIENT_DRAG_SOUND_SILENCE 			= "PatientDragSoundSilence";
  public final static String DRAG_SPEED_DECREMENT 					= "DragSpeedDecrement";
  public final static String FIRST_DRAG_FRAME 						= "FirstDragFrame";
  public final static String DRAG_FRAME_OUT 						= "DragFrameCount";
  public final static String GOTO_POSITION_MOVE_SPEED 				= "GotoPositionMoveSpeed";
  public final static String GOTO_POSITION_MOVE_FADE_IN 			= "GotoPositionMoveFadeIn";
  public final static String GOTO_POSITION_MOVE_FADE_OUT 			= "GotoPositionMoveFadeOut";
  public final static String PATIENT_START_NODE 					= "PatientStartNode";
  public final static String PATIENT_CHAIR_NODE 					= "PatientChairNode";
  public final static String PATIENT_BALLOON_SHOW_INTERVAL_TIME 	= "PatientBalloonShowIntervalTime";
  public final static String PATIENT_ALERT_HEALTH_PERCENTAGE	 	= "PatientAlertHealthPercentage";
  public final static String PATIENT_POINTS_FONT_SCALE 				= "PatientPointsFontScale";
  public final static String PATIENT_POINTS_FLY_SPEED 				= "PatientPointsFlySpeed";
  public final static String PATIENT_POINTS_FLY_MOVE_FADE_IN 		= "PatientPointsFlyMoveFadeIn";
  public final static String PATIENT_POINTS_FLY_MOVE_FADE_OUT 		= "PatientPointsFlyMoveFadeOut";
  public final static String PATIENT_POINTS_FADE_SPEED 				= "PatientPointsFadeSpeed";
  public final static String PATIENT_POINTS_FLY_TIME 				= "PatientPointsFlyTime";
  
  private final static String[] keys = {
    UPDATE_CYCLE,
    PATIENT_FADE_OUT_SPEED,
    PATIENT_ANGRY_LEAVING_TIME,
    PATIENT_BALLOON_TIME,
    PATIENT_BALLOON_FADE_TIME,
    PATIENT_BALLOON_FADE_SPEED,
    PATIENT_MAX_HEALTH,
    PATIENT_MAX_HEARTS,
    PATIENT_SPEED,
    PATIENT_MAX_ID,
    PATIENT_HEALTH_FLY_SPEED,
    PATIENT_HEALTH_FLY_MOVE_FADE_IN,
    PATIENT_HEALTH_FLY_MOVE_FADE_OUT,
    PATIENT_HEALTH_FLY_MOVE_FADE_SPEED,
    PATIENT_DRAG_SOUND_SILENCE,
    DRAG_SPEED_DECREMENT,
    FIRST_DRAG_FRAME,
    DRAG_FRAME_OUT,
    GOTO_POSITION_MOVE_SPEED,
    GOTO_POSITION_MOVE_FADE_IN,
    GOTO_POSITION_MOVE_FADE_OUT,
    PATIENT_START_NODE,
    PATIENT_CHAIR_NODE,
    PATIENT_BALLOON_SHOW_INTERVAL_TIME,
    PATIENT_ALERT_HEALTH_PERCENTAGE,
    PATIENT_POINTS_FONT_SCALE,
    PATIENT_POINTS_FLY_SPEED,
    PATIENT_POINTS_FLY_MOVE_FADE_IN,
    PATIENT_POINTS_FLY_MOVE_FADE_OUT,
    PATIENT_POINTS_FADE_SPEED,
    PATIENT_POINTS_FLY_TIME	  
  };
  
  public GiPatient(){
    super(GlobalsXmlReader.GLOBAL_GI_PATIENT, keys);	  
  }   
	
}