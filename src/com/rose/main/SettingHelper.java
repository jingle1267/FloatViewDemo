/*
 * 
 *
 */
package com.rose.main;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * @author jingle1267@163.com
 * 
 */
public class SettingHelper {

	private static final String SHARE_PREFERENCES_NAME = "user_config";

	public static ComponentName getIntent(Context context, String tag) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHARE_PREFERENCES_NAME, Activity.MODE_PRIVATE);
		String com = sharedPreferences.getString(tag, "");
		// 使用toast信息提示框显示信息
		return null;
	}

	public static void setIntent(Context context, String tag, ComponentName componentName) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				SHARE_PREFERENCES_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString(tag, pkg + ";" + cls);
		editor.commit();
	}

}
