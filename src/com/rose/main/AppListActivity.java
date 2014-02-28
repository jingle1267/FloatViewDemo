package com.rose.main;

import com.rose.adapter.AppListAdapter;
import com.rose.tools.LogHelper;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

/**
 * @author jingle1267@163.com
 *
 */
public class AppListActivity extends ListActivity {

	public static final int REQUEST_CODE = 1001;
	
	public static void startActivityForResult(Activity context) {
		Intent intent = new Intent(context, AppListActivity.class);
		if (!(context instanceof Activity)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		context.startActivityForResult(intent, REQUEST_CODE);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_applist);
		setListAdapter(new AppListAdapter(this));
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		LogHelper.print("position : " + position);
		ResolveInfo r = (ResolveInfo) l.getItemAtPosition(position);
		LogHelper.print("pkgName : " + r.activityInfo.packageName);
		String pkgName = r.activityInfo.packageName;
		String clsName = r.activityInfo.name;
		if (!TextUtils.isEmpty(pkgName) && !TextUtils.isEmpty(clsName)) {
			Intent intent = new Intent();
			ComponentName componentName = new ComponentName(pkgName, clsName);
			intent.setComponent(componentName);
			setResult(RESULT_OK, intent);
		} else {
			setResult(RESULT_CANCELED);
		}
		super.onListItemClick(l, v, position, id);
		finish();
	}
	
}
