package com.raimsoft.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.raimsoft.sensor.SensorFactory;

public class GameActivity extends Activity {

	SensorFactory sf= SensorFactory.getSensorFactory();
	private boolean already_Next=false;
	public MediaPlayer mMedia_BGM;
	
	public void NextGameOverActivity()
	{
		if(!already_Next)
		{
			Intent intent=new Intent (GameActivity.this, GameOverActivity.class);
			startActivity(intent);
			this.already_Next=true;
			this.finish();
		}
	}
	
	SensorListener AcceleroListener = new SensorListener()
	{
		public void onSensorChanged(int sensor, float[] values)
		{
			// 센서 values 값 넘겨줌
			sf.setSensorValue(values, sensor);
			//sf.debugSensorInfo_Ori();
		}

		public void onAccuracyChanged(int sensor, int accuracy)
		{
			// 정확도 변경시
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//SurfaceView mSurface=new SurfaceView(null);
		
		setContentView(R.layout.game);
		already_Next=false;
		
		mMedia_BGM = MediaPlayer.create(this, R.raw.game_bgm);
		mMedia_BGM.setLooping(true);

		sf.sensorMgr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		
		super.onCreate(savedInstanceState);	
	}

	@Override
	protected void onResume() {
		
//		sf.sensorMgr.registerListener(OrientationListener,
//		         sf.sensorMgr.SENSOR_ORIENTATION,
//		         sf.sensorMgr.SENSOR_DELAY_GAME);
		
		sf.sensorMgr.registerListener(AcceleroListener,
		         sf.sensorMgr.SENSOR_ACCELEROMETER,
		         sf.sensorMgr.SENSOR_DELAY_GAME);
		
		super.onResume();
	}

	@Override
	protected void onStop() {

		mMedia_BGM.stop();
		mMedia_BGM.release();
		
		//sf.sensorMgr.unregisterListener(OrientationListener);
		sf.sensorMgr.unregisterListener(AcceleroListener);
		
		super.onStop();
	}

	@Override
	protected void onStart() {

		mMedia_BGM.start();
		
		super.onStart();
	}
}