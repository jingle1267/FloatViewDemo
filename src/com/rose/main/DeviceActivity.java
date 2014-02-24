package com.rose.main;

import com.rose.tools.LogHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author jingle1267@163.com
 *
 */
public class DeviceActivity extends BaseActivity {

	private ViewGroup group;
	
	public static void startActivity(Context context) {
		Intent intent = new Intent(context, DeviceActivity.class);
		if (!(context instanceof Activity)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		context.startActivity(intent);
	}
	
	private boolean isLongClick = false;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device);
		group = (ViewGroup) findViewById(R.id.group_layout);
		for (int i = 0 ; i < group.getChildCount(); i++) {
			// View view = group.getChildAt(i);
			// view.setOnLongClickListener(this);
		}
	}
	
	public void clickOnItem(View view) {
		if (isLongClick) {
			isLongClick = false;
			return;
		}
	}
	
	public void clickOnBack(View view) {
		LogHelper.print();
		MainActivity.startActivity(view.getContext());
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
