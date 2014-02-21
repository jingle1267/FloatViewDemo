package com.rose.main;

import com.rose.interpolator.BounceInterpolator;
import com.rose.interpolator.EasingType;
import com.rose.tools.DisplayUtil;
import com.rose.tools.LogHelper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.LogPrinter;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

/**
 * @author jingle1267@163.com
 * 
 */
public class FloatService extends Service implements OnTouchListener,
		OnClickListener, OnLongClickListener {

	private static final String FLAG_FLOAT_VIEW_VISIBLE = "float_view_visible";

	private WindowManager windowManager;
	private WindowManager.LayoutParams layoutParams;
	private ImageView imageView;
	private boolean isLongClick = false;

	private int width, height;
	private int viewWidth = 87, viewHeight = 87;
	private int statusBarHeight = 0;

	private float preX, preY;
	private float rawX, rawY;
	private float x, y;
	private float moveLength;

	private IBinder binder = new FloatBinder();
	private Handler mHandler = new Handler();
	
	/**
	 * Float View Controller
	 * 
	 * @param context
	 * @param isShow
	 *            whether show float view or not
	 */
	public static void startService(Context context, boolean isShow) {
		Intent intent = new Intent(context, FloatService.class);
		intent.putExtra(FLAG_FLOAT_VIEW_VISIBLE, isShow);
		context.startService(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		LogHelper.print();
		return binder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		LogHelper.print();
		createFloatView();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if (intent == null) {
			return START_STICKY;
		}
		if (intent.getBooleanExtra(FLAG_FLOAT_VIEW_VISIBLE, false)) {
			if (imageView != null) {
				imageView.setVisibility(View.VISIBLE);
			}
		} else {
			if (imageView != null) {
				imageView.setVisibility(View.INVISIBLE);
			}
		}
		return START_STICKY;
	}

	public class FloatBinder extends Binder {
		FloatService getService() {
			return FloatService.this;
		}
	}

	private void createFloatView() {
		LogHelper.print();
		viewWidth = DisplayUtil.dip2px(getApplicationContext(), 87 * 2 / 3);
		viewHeight = DisplayUtil.dip2px(getApplicationContext(), 87 * 2 / 3);
		statusBarHeight = getStatusBarHeight();
		LogHelper.print("statusBarHeight : " + statusBarHeight);

		windowManager = (WindowManager) getApplicationContext()
				.getSystemService(WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(displayMetrics);
		width = displayMetrics.widthPixels;
		height = displayMetrics.heightPixels;

		LogHelper.print("width - height : " + width + " - " + height);

		layoutParams = ((FloatApplication) getApplication())
				.getWindowManagerLayoutParams();
		layoutParams.type = LayoutParams.TYPE_PHONE;
		layoutParams.format = PixelFormat.RGBA_8888;
		layoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		layoutParams.width = viewWidth;
		layoutParams.height = viewHeight;
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.x = 0;
		layoutParams.y = 0;

		imageView = new ImageView(getApplicationContext());
		imageView.setImageResource(R.drawable.launcher_icon);
		imageView.setOnTouchListener(this);
		imageView.setOnClickListener(this);
		imageView.setOnLongClickListener(this);

		if (imageView.getParent() == null) {
			windowManager.addView(imageView, layoutParams);
		}
		LogHelper.print("viewWidth - viewHeight : " + viewWidth + " - "
				+ viewHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		rawX = event.getRawX();
		rawY = event.getRawY();
		LogHelper.i("=== rawX - rawY : " + rawX + " - " + rawY);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();
			preX = rawX;
			preY = rawY;
			LogHelper.i("=== x - y : " + x + " - " + y);
			break;
		case MotionEvent.ACTION_MOVE:
			updatePosition();
			moveLength = (float) Math.sqrt((rawX - preX) * (rawX - preX)
					+ (rawY - preY) * (rawY - preY));
			break;
		case MotionEvent.ACTION_UP:
			updatePosition();
			autoMove();
			break;
		case MotionEvent.ACTION_CANCEL:

			break;
		case MotionEvent.ACTION_OUTSIDE:

			break;

		default:
			break;
		}
		return false;
	}

	private void updatePosition() {
		layoutParams.x = (int) rawX - width / 2 - (int) x + viewWidth / 2;
		layoutParams.y = (int) rawY - height / 2 - (int) y + viewHeight / 2
				- statusBarHeight / 2;
		LogHelper.i("==== params x - y : " + layoutParams.x + " - "
				+ layoutParams.y);
		windowManager.updateViewLayout(imageView, layoutParams);
	}

	private void autoMove() {
		if (layoutParams.x > 0) {
			layoutParams.gravity = Gravity.RIGHT;
		} else {
			layoutParams.gravity = Gravity.LEFT;
		}
		BounceInterpolator interpolator = new BounceInterpolator(EasingType.IN);
		LogHelper.print("getInterpolation : " + interpolator.getInterpolation(0.5f));
		int targetX = 0;
		int startX = layoutParams.x;
		if (rawX - width / 2 > 0) {
			targetX = width / 2 - viewWidth - 5;
		} else {
			targetX = -(width / 2 - viewWidth - 5);
		}
		int distance = targetX - startX;
		LogHelper.print("distance : " + distance);
		for (int i = 0 ; i < 100; i++) {
			layoutParams.x = (int) (startX + distance * interpolator.getInterpolation(i / 100f));
			LogHelper.print("interpolator.getInterpolation(" + i + " / 100f) : " + interpolator.getInterpolation(i / 100f));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mHandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					windowManager.updateViewLayout(imageView, layoutParams);
				}
			}, 0);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			if (height > width) {
				width = height + width;
				height = width - height;
				width = width - height;
			}

		} else {
			if (width > height) {
				width = height + width;
				height = width - height;
				width = width - height;
			}

		}
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @return
	 */
	public int getStatusBarHeight() {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
	 */
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		LogHelper.print();
		isLongClick = true;
		if (moveLength < 10) {
			SettingActivity.startActivity(getApplicationContext());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (isLongClick) {
			isLongClick = false;
		} else {
			LogHelper.print();
			// FloatService.startService(getApplicationContext(), false);
			MainActivity.startActivity(getApplicationContext());
		}
	}

}
