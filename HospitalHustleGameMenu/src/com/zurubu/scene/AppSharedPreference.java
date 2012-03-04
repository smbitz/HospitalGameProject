package com.zurubu.scene;

import codegears.hospitalhustlegamemenu.HospitalHustleGameMenuActivity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreference {
	private static Context context;
	
	private int lastMusicVolume;
	private int lastSFXVolume;
	private SharedPreferences preferenceOptions;
	
	public static AppSharedPreference instance;
	
	public static AppSharedPreference getInstance(){
		if(instance==null)
			new AppSharedPreference(HospitalHustleGameMenuActivity.getInterfaceActivity());
		
		return instance;
	}
	
	public AppSharedPreference(Context _context){
		instance = this;
		context =_context;
		
		preferenceOptions = context.getSharedPreferences("savefiles", 0);
		
		load(context);
	}
	
	public void load(Context context){	
		lastMusicVolume = preferenceOptions.getInt("lastMusicVolume", 100);	
		lastSFXVolume = preferenceOptions.getInt("lastSFXVolume", 100);	
	}
	
	public void save(Context context){
		SharedPreferences.Editor editoption = preferenceOptions.edit();
		
		editoption.putInt("lastMusicVolume", lastMusicVolume);
		editoption.putInt("lastSFXVolume", lastSFXVolume);
		editoption.commit();
	}
	
	
	public void setMusicVolume(int volume){
		this.lastMusicVolume=volume;
		save(context);
		
	}
	
	public int getMusicVolume(){
		return lastMusicVolume;
	}
	
	public void setSFXVolume(int volume){
		this.lastSFXVolume=volume;
		save(context);
		
	}
	
	public int getSFXVolume(){
		return lastSFXVolume;
	}
}
