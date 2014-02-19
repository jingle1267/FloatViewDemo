package com.rose.main;

import com.rose.tools.LogHelper;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.util.DisplayMetrics;
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
public class FloatService extends Service implements OnTouchListener{

	private WindowManager windowManager;
	private WindowManager.LayoutParams layoutParams;
	private ImageView imageView;
	
	private int width, height;
	private int viewWidth = 87, viewHeight = 87;
	
	private float rawX, rawY;
	private float x, y;
	
	private IBinder binder = new FloatBinder();
	
	/* (non-Javadoc)
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
		return super.onStartCommand(intent, flags, startId);
	}

	public class FloatBinder extends Binder {
		FloatService getService() {
			return FloatService.this;
		}
	}
	
	private void createFloatView() {
		LogHelper.print();
		windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(displayMetrics);
		width = displayMetrics.widthPixels;
		height = displayMetrics.heightPixels;
		
		LogHelper.print("width - height : " + width + " - " + height);
		
		layoutParams = ((FloatApplication)getApplication()).getWindowManagerLayoutParams();
		layoutParams.type = LayoutParams.TYPE_PHONE;
		layoutParams.format = PixelFormat.RGBA_8888;
		layoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		layoutParams.width = LayoutParams.WRAP_CONTENT;
		layoutParams.height = LayoutParams.WRAP_CONTENT;
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.x = 0;
		layoutParams.y = 0;
				
		imageView = new ImageView(getApplicationContext());
		imageView.setImageResource(R.drawable.launcher_icon);
		imageView.setOnTouchListener(this);

		
		if (imageView.getParent() == null) {
			windowManager.addView(imageView, layoutParams);
		}
		LogHelper.print("viewWidth - viewHeight : " + viewWidth + " - " + viewHeight);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		rawX = event.getRawX();
		rawY = event.getRawY();
		LogHelper.print("=== rawX - rawY : " + rawX + " - " + rawY);
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
			// autoMove();
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
		layoutParams.x = (int) rawX - width / 2;//(int) (rawX - x);
		layoutParams.y = (int) rawY - height / 2;//(int) (rawY - y);
		LogHelper.print("==== params x - y : " + layoutParams.x + " - " + layoutParams.y);
		windowManager.updateViewLayout(imageView, layoutParams);
	}
	
	private void autoMove() {
		if (layoutParams.x > 0) {
			layoutParams.gravity = Gravity.RIGHT;
		} else {
			layoutParams.gravity = Gravity.LEFT;
		}
		windowManager.updateViewLayout(imageView, layoutParams);
	}
	
}
