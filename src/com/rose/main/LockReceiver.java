/*
 * 
 *
 */
package com.rose.main;

import com.rose.tools.LogHelper;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author jingle1267@163.com
 *
 */
public class LockReceiver extends DeviceAdminReceiver {

	@Override
	public void onDisabled(Context context, Intent intent) {
		// TODO Auto-generated method stub
		LogHelper.print();
		super.onDisabled(context, intent);
	}

	@Override
	public void onEnabled(Context context, Intent intent) {
		// TODO Auto-generated method stub
		LogHelper.print();
		super.onEnabled(context, intent);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		LogHelper.print();
		super.onReceive(context, intent);
	}
	
}
