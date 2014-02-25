package com.rose.main;

import java.util.ArrayList;

import com.rose.adapter.BtnStyleAdapter;
import com.rose.cache.SettingCache;
import com.rose.constants.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class StyleActivity extends Activity implements OnClickListener{
	private Button backBtn;
	private ListView btnStyleList;
	private BtnStyleAdapter adapter;
	private Context mContext;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_style);
		mContext = this;
		setupView();
	}
	
	public void setupView() {
		backBtn = (Button) findViewById(R.id.style_go_back);
		backBtn.setOnClickListener(this);
		btnStyleList = (ListView) findViewById(R.id.style_list);
		ArrayList<Integer> styles = new ArrayList<Integer>();
		styles.add(Constants.BTN_STYLE_0);
		styles.add(Constants.BTN_STYLE_1);
		styles.add(Constants.BTN_STYLE_2);
		styles.add(Constants.BTN_STYLE_3);
		styles.add(Constants.BTN_STYLE_4);
		styles.add(Constants.BTN_STYLE_5);
		adapter = new BtnStyleAdapter(this);
		adapter.setStyle(SettingCache.readBtnStyle(this));
		adapter.setStyles(styles);
		btnStyleList.setAdapter(adapter);
		btnStyleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				adapter.setPosition(arg2);
				SettingCache.keepBtnStyle(mContext, adapter.getStyle());
				adapter.notifyDataSetChanged();
				Intent intent=new Intent(mContext, FloatService.class);
				intent.putExtra(FloatService.FLAG_FLOAT_VIEW_STYLE, adapter.getStyle());
				startService(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.style_go_back:
			finish();
			break;
		}
	}
}
