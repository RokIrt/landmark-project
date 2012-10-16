package com.landmark.dianping.application;

import android.app.Application;

public class MyApp extends Application {

	private static MyApp instance;
	public static SharePreferenceUtil preferences = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
	}

	public static MyApp getGlobalContext() {
		// TODO Auto-generated method stub
		return instance;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public static SharePreferenceUtil getPre() {
		if (null == preferences)
			preferences = new SharePreferenceUtil(instance);
		return preferences;
	}
	
}
