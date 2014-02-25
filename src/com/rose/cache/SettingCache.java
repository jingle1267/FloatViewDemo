package com.rose.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SettingCache {
	private static final String TAG = "SettingCache";
	private static final String PREFERENCES_NAME_SETTING = "setting_info";
	private static final String SETTING_BTN_STYLE = "btn_style";

	public static void clearSetting(Context context){
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME_SETTING, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.clear();
	    editor.commit();
	}

	public static void keepBtnStyle(Context context, int style) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME_SETTING, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt(SETTING_BTN_STYLE, style);
		editor.commit();
	}
	
	
	public static int readBtnStyle(Context context) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME_SETTING, Context.MODE_PRIVATE);
		return pref.getInt(SETTING_BTN_STYLE, 0);
	}
}
