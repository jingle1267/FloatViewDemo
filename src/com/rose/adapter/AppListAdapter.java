package com.rose.adapter;

import java.util.List;

import com.rose.main.R;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author jingle1267@163.com
 * 
 */
public class AppListAdapter extends BaseAdapter {

	private List<ResolveInfo> allApp;
	private Context context;
	private LayoutInflater mInflater;

	/**
	 * @param context
	 */
	public AppListAdapter(Context context) {
		super();
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		setAllApp(context);
	}

	private void setAllApp(Context context) {
		Intent baseIntent = new Intent(Intent.ACTION_MAIN, null);
        baseIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager packageManager = context.getPackageManager();
        allApp = packageManager.queryIntentActivities(baseIntent,
                0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allApp != null ? allApp.size() : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return allApp != null ? allApp.get(position) : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		ResolveInfo resolveInfo = allApp.get(position);
    
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.applist_item, null);
    
            holder = new ViewHolder();
            holder.appName = (TextView) convertView.findViewById(R.id.app_name);
            holder.appIcon = (ImageView) convertView.findViewById(R.id.app_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
         } 
        
        String app_name = (String) resolveInfo.loadLabel(context.getPackageManager());
        if(TextUtils.isEmpty(app_name)){
        	app_name = null;
        }
        holder.appName.setText(app_name);
        Drawable app_icon = resolveInfo.loadIcon(context.getPackageManager());
        if (app_icon != null) {
        	holder.appIcon.setImageDrawable(app_icon);
        } else {
        	holder.appIcon.setImageResource(R.drawable.launcher_icon);
        }
        return convertView;
	}

	private class ViewHolder {
		TextView appName;
		ImageView appIcon;
	}
	
}
