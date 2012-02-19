package com.zurubu.scene;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreference {
	private static Context context;
	
	private float lastMusicVolumePosition = 501;
	private float lastSFXVolumePosition = 530;
	private float lastMusicVolume;
	private float lastSFXVolume;
	private SharedPreferences preferenceOptions;
	
	public AppSharedPreference(Context _context){
		context =_context;
		
		preferenceOptions = context.getSharedPreferences("savefiles", 0);
		
		load(context);
	}
	
	public void load(Context context){
		lastMusicVolumePosition = preferenceOptions.getFloat("lastMusicVolumePosition", 0);	
		lastSFXVolumePosition = preferenceOptions.getFloat("lastSFXVolumePosition", 0);	
		lastMusicVolume = preferenceOptions.getFloat("lastMusicVolume", 0);	
		lastSFXVolume = preferenceOptions.getFloat("lastSFXVolume", 0);	
	}
	
	public void save(Context context){
		SharedPreferences.Editor editoption = preferenceOptions.edit();
		
		editoption.putFloat("lastMusicVolumePosition", lastMusicVolumePosition);
		editoption.putFloat("lastSFXVolumePosition", lastSFXVolumePosition);
		editoption.putFloat("lastMusicVolume", lastMusicVolume);
		editoption.putFloat("lastSFXVolume", lastSFXVolume);
		editoption.commit();
	}
	
	public void setMusicVolumePosition(float volume){
		this.lastMusicVolumePosition=volume;
		save(context);
		
	}
	
	public float getMusicVolumePosition(){
		return lastMusicVolumePosition;
	}
	
	public void setSFXVolumePosition(float volume){
		this.lastSFXVolumePosition=volume;
		save(context);
		
	}
	
	public float getSFXVolumePosition(){
		return lastSFXVolumePosition;
	}
	
	public void setMusicVolume(float volume){
		this.lastMusicVolume=volume;
		save(context);
		
	}
	
	public float getMusicVolume(){
		return lastMusicVolume;
	}
	
	public void setSFXVolume(float volume){
		this.lastSFXVolume=volume;
		save(context);
		
	}
	
	public float getSFXVolume(){
		return lastSFXVolume;
	}
}
