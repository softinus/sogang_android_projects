package com.raimsoft.activity;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.raimsoft.sensor.SensorFactory;

public class GameActivity extends Activity {

	SensorFactory sf= SensorFactory.getSensorFactory();
	
	SensorListener mySensorListener = new SensorListener()
	{
		public void onSensorChanged(int sensor, float[] values)
		{
			// 센서 values 값 넘겨줌
			sf.setSensorValue(values, sensor);
			sf.getSensorOrientation();
			
			sf.debugSensorInfo_Changed();
			sf.debugSensorInfo_Fixed();
		}

		public void onAccuracyChanged(int sensor, int accuracy)
		{
			// 정확도 변경시
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//SurfaceView mSurface=new SurfaceView(null);
		
		setContentView(R.layout.game);
		
		super.onCreate(savedInstanceState);
		
		sf.sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		
		sf.sm.registerListener(mySensorListener,
		         sf.sm.SENSOR_ORIENTATION,
		         sf.sm.SENSOR_DELAY_GAME);
		
		super.onResume();
	}

	@Override
	protected void onStop() {

		sf.sm.unregisterListener(mySensorListener);
		
		super.onStop();
	}
}