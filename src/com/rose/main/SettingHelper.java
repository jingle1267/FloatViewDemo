package com.rose.main;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * @author jingle1267@163.com
 * 
 */
public class SettingHelper {

	private static final String SHARE_PREFERENCES_NAME = "user_config";

	public static ComponentName getIntent(Context context, String tag) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHARE_PREFERENCES_NAME, Activity.MODE_PRIVATE);
		String flattenComponentName = sharedPreferences.getString(tag, "");
		if (TextUtils.isEmpty(flattenComponentName)) {
			return null;
		} else {
			String[] pkgCls = flattenComponentName.split("/");
			if (pkgCls != null && pkgCls.length == 2) {
				ComponentName componentName = new ComponentName(pkgCls[0], pkgCls[1]);
				return componentName;
			} else {
				return null;
			}
		}
	}

	public static void setIntent(Context context, String tag, ComponentName componentName) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				SHARE_PREFERENCES_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString(tag, componentName.flattenToString());
		editor.commit();
	}
	
	public static boolean getAutoAlign(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				SHARE_PREFERENCES_NAME, Activity.MODE_PRIVATE);
		return sharedPreferences.getBoolean("is_auto_align", true);
	}
	
	public static void setAutoAlign(Context context, boolean autoAlign) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				SHARE_PREFERENCES_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putBoolean("is_auto_align", autoAlign);
		editor.commit();
	}

}
