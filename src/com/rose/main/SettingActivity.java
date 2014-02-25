package com.rose.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author jingle1267@163.com
 *
 */
public class SettingActivity extends Activity implements OnClickListener{

	public static void startActivity(Context context) {
		Intent intent = new Intent(context, SettingActivity.class);
		if (!(context instanceof Activity)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		context.startActivity(intent);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setupView();
	}
	
	public void setupView() {
		((Button)findViewById(R.id.setting_btn_btn_style)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.setting_btn_btn_style:
			Intent intent = new Intent(this, StyleActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	
}
