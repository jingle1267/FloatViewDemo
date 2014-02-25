package com.rose.adapter;

import java.util.ArrayList;

import com.rose.constants.Constants;
import com.rose.main.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BtnStyleAdapter extends BaseAdapter{
	private ArrayList<Integer> styles = null; 
	private int style = 0;
	private Context mContext;
	
	public BtnStyleAdapter(final Context ctx) {
		mContext = ctx;
	}
	
	public void setStyle(int style) {
		this.style = style;
	}
	
	public int getStyle() {
		return style;
	}
	
	public void setPosition(int position) {
		this.style = styles.get(position);
	}
	
	public void setStyles(ArrayList<Integer> styles) {
		this.styles = styles;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(styles == null) {
			return 0;
		} else {
			return styles.size();
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.item_btn_style, null);
		if(styles.get(position) == style) {
			convertView.findViewById(R.id.item_btn_style_choose_image).setVisibility(View.VISIBLE);
		} else {
			convertView.findViewById(R.id.item_btn_style_choose_image).setVisibility(View.INVISIBLE);
		}
		ImageView imageView = (ImageView) convertView.findViewById(R.id.item_btn_style_image);
		setBtnBackGround(imageView, styles.get(position));
		return convertView;
	}

	public void setBtnBackGround(ImageView btn, int style) {
		btn.clearAnimation();
		switch(style) {
		case Constants.BTN_STYLE_0:
			btn.setImageResource(R.drawable.launcher_icon);
			break;
		case Constants.BTN_STYLE_1:
			btn.setImageResource(R.drawable.static_a_64);
			break;
		case Constants.BTN_STYLE_2:
			btn.setImageResource(R.drawable.static_b_64);
			break;
		case Constants.BTN_STYLE_3:
			btn.setImageResource(R.drawable.static_c_64);
			break;
		case Constants.BTN_STYLE_4:
			btn.setImageResource(R.drawable.animation_screen_a);
			AnimationDrawable animationDrawableA = (AnimationDrawable) btn.getDrawable();  
			animationDrawableA.start();
			break;
		case Constants.BTN_STYLE_5: 
			btn.setImageResource(R.drawable.animation_screen_b);
			AnimationDrawable animationDrawableB = (AnimationDrawable) btn.getDrawable();  
			animationDrawableB.start();
			break;
		}
	}
}
