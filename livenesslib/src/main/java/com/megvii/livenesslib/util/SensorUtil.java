package com.megvii.livenesslib.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

/**
 *传感器工具类 
 */
public class SensorUtil implements SensorEventListener {
	public float Y;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private boolean isFail;
	private Handler mHandler;

	public SensorUtil(Context context) {
		init(context);
	}

	private void init(Context context) {
		mSensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		if (mSensor != null) {
			mSensorManager.registerListener(this, mSensor,
					SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			isFail = true;
		}
		mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				isFail = true;
			}
		}, 3000);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Y = event.values[1];
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void release() {
		if (mSensor != null && mSensorManager != null) {
			mSensorManager.unregisterListener(this);
		}
		if (mHandler != null) {
			mHandler.removeCallbacksAndMessages(null);
		}
	}

	public boolean isVertical() {
		if (Y >= 8)
			return true;

		return false;
	}

	public boolean isSensorFault() {
		return isFail;
	}

}
