package com.landmark.dianping.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
public class SharePreferenceUtil {
	/**
	 * SharedPreferences ��ݴ洢
	 * 
	 * @author Administrator
	 * 
	 */
		private Context mContext;

		public SharePreferenceUtil(Context context) {
			this.mContext = context;
		}

		/**
		 * SharedPreferences ��ʼ��
		 * 
		 * @return
		 */
		private SharedPreferences getSharedPreferences() {
			return mContext.getSharedPreferences("DianpingDemo", Context.MODE_APPEND);
		}

		/**
		 * SharedPreferences �洢string�������
		 * 
		 * @param key
		 * @param value
		 */
		public void putString(String key, String value) {
			SharedPreferences sharedPreferences = getSharedPreferences();
			Editor editor = sharedPreferences.edit();
			editor.putString(key, value);
			editor.commit();
		}

		/**
		 * SharedPreferences �洢int�������
		 * 
		 * @param key
		 * @param value
		 */
		public void putInt(String key, int value) {
			SharedPreferences sharedPreferences = getSharedPreferences();
			Editor editor = sharedPreferences.edit();
			editor.putInt(key, value);
			editor.commit();
		}

		/**
		 * SharedPreferences �洢boolean�������
		 * 
		 * @param key
		 * @param value
		 */
		public void putBoolean(String key, Boolean value) {
			SharedPreferences sharedPreferences = getSharedPreferences();
			Editor editor = sharedPreferences.edit();
			editor.putBoolean(key, value);
			editor.commit();
		}

		/**
		 * SharedPreferences ��ȡstring�������
		 * 
		 * @param key
		 * @return
		 */
		public String getString(String key) {
			SharedPreferences sharedPreferences = getSharedPreferences();
			return sharedPreferences.getString(key, "");
		}

		/**
		 * SharedPreferences ��ȡint�������
		 * 
		 * @param key
		 * @return
		 */
		public int getInt(String key) {
			SharedPreferences sharedPreferences = getSharedPreferences();
			return sharedPreferences.getInt(key, 0);
		}

		/**
		 * SharedPreferences ��ȡboolean�������
		 * 
		 * @param key
		 * @return
		 */
		public boolean getBoolean(String key) {
			SharedPreferences sharedPreferences = getSharedPreferences();
			return sharedPreferences.getBoolean(key, false);
		}
		
		/**
		 * ɾ��
		 * @param key
		 */
		public void remove(String key){
			SharedPreferences sharedPreferences = getSharedPreferences();
			Editor editor=sharedPreferences.edit();
			editor.remove(key);
			editor.commit();
		}
	
}
