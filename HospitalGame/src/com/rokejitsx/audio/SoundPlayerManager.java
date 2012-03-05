package com.rokejitsx.audio;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.music.MusicManager;

import android.util.Log;

import com.rokejitsx.HospitalGameActivity;
import com.zurubu.scene.AppSharedPreference;
import com.zurubu.scene.OptionMenuListener;

public class SoundPlayerManager implements SoundList, OptionMenuListener{
  private static SoundPlayerManager self;
  //private Hashtable<String, Sound> playerList;
  
  private MySoundManager soundManager;
  private MusicManager musicManager;  
  //private Music currentMusic;
  private Hashtable<String, Object> soundPLayerList;
  //private Hashtable<String, Music> musicPLayerList;
  
  public static SoundPlayerManager getInstance(){
    if(self == null)
      self = new SoundPlayerManager();
    
    return self;
  }
  
  
  
  private SoundPlayerManager(){	
    reset();
  } 
  
  public void reset(){
	soundManager = new MySoundManager();   
    musicManager = new MusicManager();/*HospitalGameActivity.getGameActivity().getEngine().getMusicManager()*/;
    
    soundPLayerList = new Hashtable<String, Object>();  
    MySoundFactory.setAssetBasePath("media/audio/");
    MusicFactory.setAssetBasePath("media/audio/");
    
    /*soundManager.setMasterVolume(AppSharedPreference.getInstance().getSFXVolume());
    musicManager.setMasterVolume(AppSharedPreference.getInstance().getMusicVolume());*/
  }
  
  public void createSound(String[] soundList){
    for(int i = 0;i < soundList.length;i++){
      createSound(soundList[i]);	
    }	  
  }
  
  public void createSound(String fileName){
	if(soundPLayerList.containsKey(fileName)){
	  return; 	
	}
    try {
      MySound sound = MySoundFactory.createSoundFromAsset(soundManager, HospitalGameActivity.getGameActivity(), fileName);      
      soundPLayerList.put(fileName, sound);	  
	} catch (IOException e) {		
	  e.printStackTrace();
	}   	  
    
  }
  
  public void releaseAll(){	  
	/*Enumeration<String> e = soundPLayerList.keys();
	while(e.hasMoreElements()){
	  stopSound(e.nextElement());	
	}*/
    soundManager.releaseAll();
    musicManager.releaseAll();
    
  }
  
  public void playSound(String fileName){
    if(!soundPLayerList.containsKey(fileName)){
	  return; 	
	}
    
    Object obj = soundPLayerList.get(fileName);
    
    if(obj instanceof MySound){
      ((MySound)obj).setVolume((float)AppSharedPreference.getInstance().getSFXVolume() / 100);
      ((MySound)obj).play();	
    }else if(obj instanceof Music){
      ((Music)obj).setVolume((float)AppSharedPreference.getInstance().getMusicVolume() / 100);
      ((Music)obj).play();	
      //currentMusic = ((Music)obj);
    }    
  }
  
  public void stopSound(String fileName){
    if(!soundPLayerList.containsKey(fileName)){
	  return; 	
	}
	    
    Object obj = soundPLayerList.get(fileName);
	    
    if(obj instanceof MySound){
      MySound sound = (MySound) obj;
      sound.stop();     	
    }else if(obj instanceof Music){
      Music music = (Music) obj;
      music.stop();      
      /*if(music.equals(currentMusic))
        currentMusic = null;*/
    }	  
  }
  
  public void releaseSound(String fileName){
    if(!soundPLayerList.containsKey(fileName)){
	  return; 	
	}
		    
    Object obj = soundPLayerList.get(fileName);
		    
    if(obj instanceof MySound){
      MySound sound = (MySound) obj;
      
      sound.release();	      
      	      	
    }else if(obj instanceof Music){
     //((Music)obj).release();	
    }	  
    
    soundPLayerList.remove(fileName);
  }
  
  
  
  
  public void createMusic(String fileName){
	if(soundPLayerList.containsKey(fileName))
	  return;
    try {
      Log.d("RokejitsX", "Load music "+MusicFactory.sAssetBasePath+fileName);
      Music music = MusicFactory.createMusicFromAsset(musicManager, HospitalGameActivity.getGameActivity(), fileName);      
      music.setLooping(true);
      soundPLayerList.put(fileName, music);
	   
	} catch (IOException e) {
			
	  e.printStackTrace();
	}    	  
    	  
  }



  @Override
  public void onMusicValueChange(int musicVolume) {
	Log.d("RokejitsX", "onMusicValueChangeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee ="+musicVolume);
    musicManager.setMasterVolume((float)musicVolume / 100);
	/*if(currentMusic != null)
	  currentMusic.setVolume(musicVolume);*/
	
  }



  @Override
  public void onSFXValueChange(int sfxVolume) {
	Log.d("RokejitsX", "onSFXValueChangeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee ="+sfxVolume);
    soundManager.setMasterVolume((float)sfxVolume / 100);   
	
  }
  
  //0863058135 
	
  	
}
