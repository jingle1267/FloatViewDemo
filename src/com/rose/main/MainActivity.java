package com.rose.main;

import com.rose.tools.LogHelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends BaseActivity {

	private static final int REQUEST_PERMISSION = 1001;
	
	private DevicePolicyManager policyManager;
	private ComponentName componentName;
	
	public static void startActivity(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		if (!(context instanceof Activity)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LogHelper.print();
		policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		componentName = new ComponentName(this, LockReceiver.class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void clickOnGuesture(View view){
		boolean isActive = policyManager.isAdminActive(componentName);
		if (isActive) {
			policyManager.lockNow();
			finish();
		} else {
			activeManage();
		}
	}
	
	public void clickOnFavorite(View view){
		FavoriteActivity.startActivity(view.getContext());
		finish();
	}
	
	public void clickOnBack(View view){
		finish();
	}
	
	public void clickOnDevice(View view){
		// getWindow().setWindowAnimations(R.anim.zoomout);
		DeviceActivity.startActivity(view.getContext());
		finish();
	}
	
	public void clickOnMainscreen(View view){
		Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(mHomeIntent);		
	}
	
	private void activeManage() {
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getResources().getString(R.string.lock_screen_title));
		startActivityForResult(intent, REQUEST_PERMISSION);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_PERMISSION && resultCode == RESULT_OK) {
			policyManager.lockNow();
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
