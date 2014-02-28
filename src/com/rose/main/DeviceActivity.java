package com.rose.main;

import com.rose.tools.LogHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author jingle1267@163.com
 *
 */
public class DeviceActivity extends BaseActivity {

	private AudioManager audioManager;
	private Vibrator vibrator;
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
		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	}
	
	public void clickOnItem(View view) {
		if (isLongClick) {
			isLongClick = false;
			return;
		}
		switch (Integer.parseInt(view.getTag().toString())) {
		case 1:
			
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			vibrator.vibrate(50);
			audioManager.adjustSuggestedStreamVolume(AudioManager.ADJUST_LOWER, 
					AudioManager.USE_DEFAULT_STREAM_TYPE, 0);
			break;
		case 5:
			
			break;
		case 6:
			vibrator.vibrate(50);
			audioManager.adjustSuggestedStreamVolume(AudioManager.ADJUST_RAISE, 
					AudioManager.USE_DEFAULT_STREAM_TYPE, 0);
			break;
		case 7:
			
			break;
		case 8:
			
			break;
		case 9:
			
			break;

		default:
			break;
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
