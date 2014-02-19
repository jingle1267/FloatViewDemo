/*
 * 
 *
 */
package com.rose.main;

import com.rose.tools.LogHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * @author jingle1267@163.com
 *
 */
public class BaseActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		FloatService.startService(getApplicationContext(), false);
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		FloatService.startService(getApplicationContext(), true);
		super.onPause();
	}
	
	public void clickOnLayout(View view){
		LogHelper.print();
		finish();
	}
	
}
