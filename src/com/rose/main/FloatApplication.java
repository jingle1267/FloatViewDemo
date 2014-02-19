package com.rose.main;

import com.rose.tools.LogHelper;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.WindowManager;

/**
 * @author jingle1267@163.com
 *
 */
public class FloatApplication extends Application {

	private WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		LogHelper.print();
		super.onCreate();
		Intent intent = new Intent(this, FloatService.class);
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		LogHelper.print();
		super.onTerminate();
		unbindService(serviceConnection);
	}

	public WindowManager.LayoutParams getWindowManagerLayoutParams() {
		return layoutParams;
	}
	
	private ServiceConnection serviceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			LogHelper.print();
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			LogHelper.print();
		}
	};
	
}
