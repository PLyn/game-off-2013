package com.Shadow.polyshifter.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
	public static final String TAG = Settings.class.getName();
	
	public static final Settings instance = new Settings();
	
	public boolean showFPS;
	
	private Preferences setting;
	
	// singleton: prevent instantiation from other classes
	private Settings () {
	setting = Gdx.app.getPreferences(Constants.PREFERENCES);
	}
	
	public void load(){
		showFPS = setting.getBoolean("ShowFPS", false);
	}
	public void save (){
		setting.putBoolean("ShowFPS", showFPS);
		setting.flush();
	}
}
