package com.rose.main;

import com.rose.tools.DisplayUtil;
import com.rose.tools.LogHelper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

/**
 * @author jingle1267@163.com
 * 
 */
public class FloatService extends Service implements OnTouchListener {

	private static final String FLAG_FLOAT_VIEW_VISIBLE = "float_view_visible";

	private WindowManager windowManager;
	private WindowManager.LayoutParams layoutParams;
	private ImageView imageView;
	private GestureDetector gestureDetector;

	private int width, height;
	private int viewWidth = 87, viewHeight = 87;
	private int statusBarHeight = 0;

	private float rawX, rawY;
	private float x, y;

	private IBinder binder = new FloatBinder();

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
		statusBarHeight = DisplayUtil.getStatusBarHeight(getApplicationContext());
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

		gestureDetector = new GestureDetector(this, new GestureListener());
		imageView = new ImageView(getApplicationContext());
		imageView.setImageResource(R.drawable.launcher_icon);
		imageView.setOnTouchListener(this);

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
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			updatePosition();
			break;
		case MotionEvent.ACTION_UP:
			updatePosition();
			if (SettingHelper.getAutoAlign(getApplicationContext())) {
				autoMove();
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		case MotionEvent.ACTION_OUTSIDE:
			break;
		default:
			break;
		}
		return gestureDetector.onTouchEvent(event);
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
		while (true) {
			if (layoutParams.x <= 0 && layoutParams.x > -width / 2 + 5) {
				layoutParams.x = layoutParams.x - 5;
				windowManager.updateViewLayout(imageView, layoutParams);
			} else if (layoutParams.x > 0 && layoutParams.x < width / 2 - 5) {
				layoutParams.x = layoutParams.x + 5;
				windowManager.updateViewLayout(imageView, layoutParams);
			} else {
				break;
			}
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

	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		LogHelper.print();
		SettingActivity.startActivity(getApplicationContext());
		return false;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		LogHelper.print();
		// FloatService.startService(getApplicationContext(), false);
		MainActivity.startActivity(getApplicationContext());
	}

	class GestureListener extends SimpleOnGestureListener {

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			// TODO Auto-generated method stub
			LogHelper.print();
			return super.onDoubleTap(e);
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			LogHelper.print();
			SettingActivity.startActivity(getApplicationContext());
			super.onLongPress(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			LogHelper.print();
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub
			LogHelper.print();
			MainActivity.startActivity(getApplicationContext());
			return super.onSingleTapConfirmed(e);
		}

	}

}
