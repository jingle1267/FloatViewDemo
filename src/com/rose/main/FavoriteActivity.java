package com.rose.main;

import java.util.List;

import com.rose.tools.LogHelper;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

/**
 * @author jingle1267@163.com
 *
 */
public class FavoriteActivity extends BaseActivity implements OnLongClickListener{

	private int longClickTag = 1;
	private ViewGroup group;
	
	public static void startActivity(Context context) {
		Intent intent = new Intent(context, FavoriteActivity.class);
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
		setContentView(R.layout.activity_favorite);
		group = (ViewGroup) findViewById(R.id.group_layout);
		for (int i = 0 ; i < group.getChildCount(); i++) {
			View view = group.getChildAt(i);
			view.setOnLongClickListener(this);
			ComponentName componentName = SettingHelper.getIntent(getApplicationContext(), view.getTag().toString());
			if (componentName != null && isIntentAvailable(this, componentName)) {
				try {
					Drawable drawable = getPackageManager().getActivityIcon(componentName);
					view.setBackground(drawable);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}
	}
	
	public void clickOnItem(View view) {
		if (isLongClick) {
			isLongClick = false;
			return;
		}
		longClickTag = Integer.parseInt(view.getTag().toString());
		LogHelper.d("view.tag : " + longClickTag);
		ComponentName componentName = SettingHelper.getIntent(getApplicationContext(), view.getTag().toString());
		if (componentName != null) {
			Intent intent = new Intent();
			intent.setComponent(componentName);
			startActivity(intent);
		} else {
			AppListActivity.startActivityForResult(FavoriteActivity.this);
		}
	}
	
	public void clickOnBack(View view) {
		LogHelper.print();
		MainActivity.startActivity(view.getContext());
		finish();
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
	 */
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		isLongClick = true;
		longClickTag = Integer.parseInt(v.getTag().toString());
		LogHelper.d("view.tag : " + longClickTag);
		AppListActivity.startActivityForResult(FavoriteActivity.this);
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			if (requestCode == AppListActivity.REQUEST_CODE) {
				if (data != null && isIntentAvailable(this, data.getComponent())) {
					SettingHelper.setIntent(this, longClickTag + "", data.getComponent());
					View view = group.getChildAt(longClickTag - 1);
					try {
						Drawable drawable = getPackageManager().getActivityIcon(data.getComponent());
						view.setBackground(drawable);
					} catch (NameNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private boolean isIntentAvailable(Context context, ComponentName componentName) {  
	    final PackageManager packageManager = context.getPackageManager();  
	    final Intent intent = new Intent();
	    intent.setComponent(componentName);
	    List<ResolveInfo> resolveInfo =  
	            packageManager.queryIntentActivities(intent,  
	                    PackageManager.MATCH_DEFAULT_ONLY);  
	   if (resolveInfo.size() > 0) {  
	        return true;  
	    }  
	   return false;  
	}  
	
}
