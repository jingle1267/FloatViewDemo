/*
 * 
 *
 */
package com.rose.main;

import com.rose.tools.LogHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;

/**
 * @author jingle1267@163.com
 *
 */
public class FavoriteActivity extends BaseActivity implements OnLongClickListener{

	public static void startActivity(Context context) {
		Intent intent = new Intent(context, FavoriteActivity.class);
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
		setContentView(R.layout.activity_favorite);
	}
	
	public void clickOnItem(View view) {
		LogHelper.d("view.tag : " + view.getTag());
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
		return false;
	}
	
}
