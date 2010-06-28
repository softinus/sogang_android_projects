package com.raimsoft.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;

import com.raimsoft.sensor.SensorFactory;
import com.raimsoft.sound.SoundManager;

public class GameActivity extends Activity {

	SensorFactory sf= SensorFactory.getSensorFactory();
	private boolean already_Next=false;
	//boolean already_Option=false;
	//public MediaPlayer mMedia_BGM;
	public MediaPlayer mMedia_Success;

	SoundManager sm=new SoundManager(null);

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

	public void NextOptionActivity()
	{
		Intent intent=new Intent (GameActivity.this, PopupActivity.class);
		startActivity(intent);
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

//		mMedia_BGM = MediaPlayer.create(this, R.raw.game_bgm);
//		mMedia_BGM.setLooping(true);

		if (sm.bSoundOpt)	mMedia_Success= MediaPlayer.create(this, R.raw.success);

//		sm.create();
//		sm.load(0, R.raw.success);

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

	public void stopBGM()
	{
		//mMedia_BGM.stop();
	}
	public void playSE()
	{
		mMedia_Success.start();
	}

	@Override
	protected void onStop() {

//		mMedia_BGM.stop();
//		mMedia_BGM.release();

		//sf.sensorMgr.unregisterListener(OrientationListener);
		sf.sensorMgr.unregisterListener(AcceleroListener);

		super.onStop();
	}

	@Override
	protected void onStart() {

//		mMedia_BGM.start();

		super.onStart();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return true;

		return super.onKeyDown(keyCode, event);
	}
}