package com.rose.main;

import com.rose.tools.LogHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends BaseActivity {

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void clickOnGuesture(View view){
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
	
}
