package kr.co.joynuri.animalorchestra;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView 
		implements SurfaceHolder.Callback, SensorEventListener {
	
	private static final String LOG_TAG = GameView.class.getSimpleName();
	
	public static final int TARGET_INTERVAL = 200;
	public static final int LEVEL_INTERVAL = 60 * 1000;
	public static final int MAX_LEVEL = 10;
	public static final int MAX_LIFE = 10;
	public static final int MAX_COMBO = 10;
	
	public static final int MODE_STOP = 0;
	public static final int MODE_READY = 1;
	public static final int MODE_RUNNING = 2;
	public static final int MODE_PAUSE = 3;
	public static final int MODE_GAMEOVER = 4;
	
	public boolean mDay;
	private Bitmap mBackgroundBitmap;
	private MediaPlayer mMediaPlayerBackground;
	private MediaPlayer mMediaPlayerDay;
	private MediaPlayer mMediaPlayerNight;
	
	private Context mContext;
	private SurfaceHolder mHolder;
	private GameThread mThread;
	private Handler mHandler;
	
	// Resource
	private BitmapManager mBitmapManager;
	private SoundManager mSoundManager;
	private SensorManager mSensorManager;
	private Vibrator mVibrator;
	
	// Enveronment
	private int mCanvasWidth;
	private int mCanvasHeight;
	private int mOffsetX;
	private int mOffsetY;
	private Paint mPaint;
	private long mGameTime;
	private long mExcessTime;
	private long mLastTargetTime;
	
	// array
	int[] mArr_canvas;
	int[] mArr_img_ready;
	int[] mArr_img_pause;
	int[] mArr_img_heart;
	int[] mArr_img_combo;
	int[] mArr_img_gameover;
	int[] mArr_img_animal_0;
	int[] mArr_img_animal_1;
	int[] mArr_img_animal_2;
	int[] mArr_img_animal_3;
	int[] mArr_img_animal_4;
	int[] mArr_img_animal_5;
	int[] mArr_img_animal_6;
	int[] mArr_img_animal_7;
	int[] mArr_img_animal_8;
	int[] mArr_practice_score_level_1;
	int[] mArr_practice_score_level_2;
	int[] mArr_game_score_level_1;
	int[] mArr_game_score_level_2;
	int[] mArr_game_gauge_level_1;
	int[] mArr_game_gauge_level_2;
	
	// Key
	private boolean[] mKey = new boolean[255];
	
	// Touch
	private float mTouchX;
	private float mTouchY;
	
	// Accelerometer
	private float mAccelerometerX;
	private float mAccelerometerY;
	private float mAccelerometerZ;
	
	// Mode
	private int mMenu;
	private int mMode;
	
	// Member
	private int mAnimalCount;
	private GameObject mReadyObj;
	private GameObject mPauseObj;
	private GameObject mGameoverObj;
	private GameObject mHeartObj;
	private GameObject mComboObj;
	private ArrayList<GameObject> mAnimalObjList;
	private ArrayList<GameObject> mSuccessObjList;
	private ArrayList<GameObject> mFailObjList;
	
	// Score
	private int mScoreLevel;
	private int mScoreTarget;
	private int mScoreSuccess;
	private int mScoreFail;
	private int mScoreLife;
	private int mScoreCombo;
	private int mScore;
	
	public Object getState() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mScoreLevel", mScoreLevel);
		map.put("mScoreTarget", mScoreTarget);
		map.put("mScoreSuccess", mScoreSuccess);
		map.put("mScoreFail", mScoreFail);
		map.put("mScoreLife", mScoreLife);
		map.put("mScoreCombo", mScoreCombo);
		map.put("mScore", mScore);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public void setState(Object o) {
		HashMap<String, Object> map = (HashMap<String, Object>) o;
		mScoreLevel = (Integer) map.get("mScoreLevel");
		mScoreTarget = (Integer) map.get("mScoreTarget");
		mScoreSuccess = (Integer) map.get("mScoreSuccess");
		mScoreFail = (Integer) map.get("mScoreFail");
		mScoreLife = (Integer) map.get("mScoreLife");
		mScoreCombo = (Integer) map.get("mScoreCombo");
		mScore = (Integer) map.get("mScore");
	}
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
		mThread = new GameThread(this);
		mHandler = new Handler();
		setFocusable(true);
	}
	
	public void create(int menu) {
		// Stop Mode
		mMode = MODE_STOP;

		// Paint
		mPaint = new Paint();
		mPaint.setARGB(255, 255, 255, 255);
		
		// Menu
		mMenu = menu;
		mDay = true;
		
		// Array
		mArr_canvas = getResources().getIntArray(R.array.arr_canvas);
		mArr_img_ready = getResources().getIntArray(R.array.arr_img_ready);
		mArr_img_pause = getResources().getIntArray(R.array.arr_img_pause);
		mArr_img_heart = getResources().getIntArray(R.array.arr_img_heart);
		mArr_img_combo = getResources().getIntArray(R.array.arr_img_combo);
		mArr_img_gameover = getResources().getIntArray(R.array.arr_img_gameover);
		mArr_img_animal_0 = getResources().getIntArray(R.array.arr_img_animal_0);
		mArr_img_animal_1 = getResources().getIntArray(R.array.arr_img_animal_1);
		mArr_img_animal_2 = getResources().getIntArray(R.array.arr_img_animal_2);
		mArr_img_animal_3 = getResources().getIntArray(R.array.arr_img_animal_3);
		mArr_img_animal_4 = getResources().getIntArray(R.array.arr_img_animal_4);
		mArr_img_animal_5 = getResources().getIntArray(R.array.arr_img_animal_5);
		mArr_img_animal_6 = getResources().getIntArray(R.array.arr_img_animal_6);
		mArr_img_animal_7 = getResources().getIntArray(R.array.arr_img_animal_7);
		mArr_img_animal_8 = getResources().getIntArray(R.array.arr_img_animal_8);
		mArr_practice_score_level_1 = getResources().getIntArray(R.array.arr_practice_score_level_1);
		mArr_practice_score_level_2 = getResources().getIntArray(R.array.arr_practice_score_level_2);
		mArr_game_score_level_1 = getResources().getIntArray(R.array.arr_game_score_level_1);
		mArr_game_score_level_2 = getResources().getIntArray(R.array.arr_game_score_level_2);
		mArr_game_gauge_level_1 = getResources().getIntArray(R.array.arr_game_gauge_level_1);
		mArr_game_gauge_level_2 = getResources().getIntArray(R.array.arr_game_gauge_level_2);
		
		// Bitmap
		mBitmapManager = new BitmapManager(mContext);
		mBitmapManager.create();
		mBitmapManager.load(R.drawable.game_background_day);
		mBitmapManager.load(R.drawable.game_background_night);
		mBitmapManager.load(R.drawable.img_notify);
		mBitmapManager.load(R.drawable.img_icon);
		mBitmapManager.load(R.drawable.img_animal_0);
		mBitmapManager.load(R.drawable.img_animal_1);
		mBitmapManager.load(R.drawable.img_animal_2);
		mBitmapManager.load(R.drawable.img_animal_3);
		mBitmapManager.load(R.drawable.img_animal_4);
		mBitmapManager.load(R.drawable.img_animal_5);
		mBitmapManager.load(R.drawable.img_animal_6);
		mBitmapManager.load(R.drawable.img_animal_7);
		mBitmapManager.load(R.drawable.img_animal_8);
		
		// GameObject
		mReadyObj = createGameObject(R.drawable.img_notify, 0, mArr_img_ready[2], mArr_img_ready[3], 2, 2, true, false);
		mPauseObj = createGameObject(R.drawable.img_notify, mArr_img_ready[2], mArr_img_pause[2], mArr_img_pause[3], 2, 2, true, false);
		mGameoverObj = createGameObject(R.drawable.img_notify, mArr_img_ready[2]+mArr_img_pause[2], mArr_img_gameover[2], mArr_img_gameover[3], 2, 2, true, false);
		mHeartObj = createGameObject(R.drawable.img_icon, 0, mArr_img_heart[4], mArr_img_heart[5], 7, 7, false, true);
		mComboObj = createGameObject(R.drawable.img_icon, mArr_img_heart[4], mArr_img_combo[4], mArr_img_combo[5], 9, 9, false, true);
		// Animals
		mAnimalObjList = new ArrayList<GameObject>();
		mAnimalObjList.add( createGameObject(R.drawable.img_animal_0, 0, mArr_img_animal_0[2], mArr_img_animal_0[3], 4, 4, false, false) );
		mAnimalObjList.add( createGameObject(R.drawable.img_animal_1, 0, mArr_img_animal_1[2], mArr_img_animal_1[3], 4, 4, false, false) );
		mAnimalObjList.add( createGameObject(R.drawable.img_animal_2, 0, mArr_img_animal_2[2], mArr_img_animal_2[3], 4, 4, false, false) );
		mAnimalObjList.add( createGameObject(R.drawable.img_animal_3, 0, mArr_img_animal_3[2], mArr_img_animal_3[3], 4, 4, false, false) );
		mAnimalObjList.add( createGameObject(R.drawable.img_animal_4, 0, mArr_img_animal_4[2], mArr_img_animal_4[3], 4, 4, false, false) );
		mAnimalObjList.add( createGameObject(R.drawable.img_animal_5, 0, mArr_img_animal_5[2], mArr_img_animal_5[3], 4, 4, false, false) );
		mAnimalObjList.add( createGameObject(R.drawable.img_animal_6, 0, mArr_img_animal_6[2], mArr_img_animal_6[3], 4, 4, false, false) );
		mAnimalObjList.add( createGameObject(R.drawable.img_animal_7, 0, mArr_img_animal_7[2], mArr_img_animal_7[3], 4, 4, false, false) );
		mAnimalObjList.add( createGameObject(R.drawable.img_animal_8, 0, mArr_img_animal_8[2], mArr_img_animal_8[3], 4, 4, false, false) );
		// Success
		mSuccessObjList = new ArrayList<GameObject>();
		mSuccessObjList.add( createGameObject(R.drawable.img_animal_0, mArr_img_animal_0[2], mArr_img_animal_0[2], mArr_img_animal_0[3], 4, 4, false, false) );
		mSuccessObjList.add( createGameObject(R.drawable.img_animal_1, mArr_img_animal_1[2], mArr_img_animal_1[2], mArr_img_animal_1[3], 4, 4, false, false) );
		mSuccessObjList.add( createGameObject(R.drawable.img_animal_2, mArr_img_animal_2[2], mArr_img_animal_2[2], mArr_img_animal_2[3], 4, 4, false, false) );
		mSuccessObjList.add( createGameObject(R.drawable.img_animal_3, mArr_img_animal_3[2], mArr_img_animal_3[2], mArr_img_animal_3[3], 4, 4, false, false) );
		mSuccessObjList.add( createGameObject(R.drawable.img_animal_4, mArr_img_animal_4[2], mArr_img_animal_4[2], mArr_img_animal_4[3], 4, 4, false, false) );
		mSuccessObjList.add( createGameObject(R.drawable.img_animal_5, mArr_img_animal_5[2], mArr_img_animal_5[2], mArr_img_animal_5[3], 4, 4, false, false) );
		mSuccessObjList.add( createGameObject(R.drawable.img_animal_6, mArr_img_animal_6[2], mArr_img_animal_6[2], mArr_img_animal_6[3], 4, 4, false, false) );
		mSuccessObjList.add( createGameObject(R.drawable.img_animal_7, mArr_img_animal_7[2], mArr_img_animal_7[2], mArr_img_animal_7[3], 4, 4, false, false) );
		mSuccessObjList.add( createGameObject(R.drawable.img_animal_8, mArr_img_animal_8[2], mArr_img_animal_8[2], mArr_img_animal_8[3], 4, 4, false, false) );
		// Fail
		mFailObjList = new ArrayList<GameObject>();
		mFailObjList.add( createGameObject(R.drawable.img_animal_0, mArr_img_animal_0[2]+mArr_img_animal_0[2], mArr_img_animal_0[2], mArr_img_animal_0[3], 4, 4, false, false) );
		mFailObjList.add( createGameObject(R.drawable.img_animal_1, mArr_img_animal_1[2]+mArr_img_animal_1[2], mArr_img_animal_1[2], mArr_img_animal_1[3], 4, 4, false, false) );
		mFailObjList.add( createGameObject(R.drawable.img_animal_2, mArr_img_animal_2[2]+mArr_img_animal_2[2], mArr_img_animal_2[2], mArr_img_animal_2[3], 4, 4, false, false) );
		mFailObjList.add( createGameObject(R.drawable.img_animal_3, mArr_img_animal_3[2]+mArr_img_animal_3[2], mArr_img_animal_3[2], mArr_img_animal_3[3], 4, 4, false, false) );
		mFailObjList.add( createGameObject(R.drawable.img_animal_4, mArr_img_animal_4[2]+mArr_img_animal_4[2], mArr_img_animal_4[2], mArr_img_animal_4[3], 4, 4, false, false) );
		mFailObjList.add( createGameObject(R.drawable.img_animal_5, mArr_img_animal_5[2]+mArr_img_animal_5[2], mArr_img_animal_5[2], mArr_img_animal_5[3], 4, 4, false, false) );
		mFailObjList.add( createGameObject(R.drawable.img_animal_6, mArr_img_animal_6[2]+mArr_img_animal_6[2], mArr_img_animal_6[2], mArr_img_animal_6[3], 4, 4, false, false) );
		mFailObjList.add( createGameObject(R.drawable.img_animal_7, mArr_img_animal_7[2]+mArr_img_animal_7[2], mArr_img_animal_7[2], mArr_img_animal_7[3], 4, 4, false, false) );
		mFailObjList.add( createGameObject(R.drawable.img_animal_8, mArr_img_animal_8[2]+mArr_img_animal_8[2], mArr_img_animal_8[2], mArr_img_animal_8[3], 4, 4, false, false) );
		// Animal Pos
		mAnimalCount = mAnimalObjList.size();
		
		// Sound
		int coundIndex = 0;
		mSoundManager = new SoundManager(mContext);
        mSoundManager.create();
        mSoundManager.load(coundIndex++, R.raw.snd_animal_0);
        mSoundManager.load(coundIndex++, R.raw.snd_animal_1);
        mSoundManager.load(coundIndex++, R.raw.snd_animal_2);
        mSoundManager.load(coundIndex++, R.raw.snd_animal_3);
        mSoundManager.load(coundIndex++, R.raw.snd_animal_4);
        mSoundManager.load(coundIndex++, R.raw.snd_animal_5);
        mSoundManager.load(coundIndex++, R.raw.snd_animal_6);
        mSoundManager.load(coundIndex++, R.raw.snd_animal_7);
        mSoundManager.load(coundIndex++, R.raw.snd_animal_8);
		
        // Sensor Manager
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(
				this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_FASTEST);
		
		// Backgound Image
		mBitmapManager.load(R.drawable.game_background_day);
		mBitmapManager.load(R.drawable.game_background_night);
		mBackgroundBitmap = mBitmapManager.getBitmap(R.drawable.game_background_day);
		
		// MediaPlayer
		mMediaPlayerDay = MediaPlayer.create(mContext, R.raw.snd_background_day);
		mMediaPlayerDay.setLooping(true);
		mMediaPlayerNight = MediaPlayer.create(mContext, R.raw.snd_background_night);
		mMediaPlayerNight.setLooping(true);
		mMediaPlayerBackground = mMediaPlayerDay;
		
		// Vibrator
		mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	private void change(int width, int height) {
		synchronized (mHolder) {
			// Canvas
			mCanvasWidth = (width < height) ? mArr_canvas[0] : mArr_canvas[1];
			mCanvasHeight = (width < height) ? mArr_canvas[1] : mArr_canvas[0];
			mOffsetX = (width - mCanvasWidth) / 2;
			mOffsetY = (height - mCanvasHeight) / 2;
			
			// Ready
			mReadyObj.mXPos = mArr_img_ready[0];
			mReadyObj.mYPos = mArr_img_ready[1];
			// Pause
			mPauseObj.mXPos = mArr_img_pause[0];
			mPauseObj.mYPos = mArr_img_pause[1];
			// Gameover
			mGameoverObj.mXPos = mArr_img_gameover[0];
			mGameoverObj.mYPos = mArr_img_gameover[1];
			// Heart
			mHeartObj.mXPos = ((width < height) ? mArr_img_heart[0] : mArr_img_heart[2]);
			mHeartObj.mYPos = ((width < height) ? mArr_img_heart[1] : mArr_img_heart[3]);
			// Combo
			mComboObj.mXPos = ((width < height) ? mArr_img_combo[0] : mArr_img_combo[2]);
			mComboObj.mYPos = ((width < height) ? mArr_img_combo[1] : mArr_img_combo[3]);
			
			// Animals
			GameObject animalObj;
			GameObject successObj;
			GameObject failObj;
			// 0
			animalObj = mAnimalObjList.get(0);
			successObj = mSuccessObjList.get(0);
			failObj = mFailObjList.get(0);
			animalObj.mXPos = mArr_img_animal_0[0];
			animalObj.mYPos = mArr_img_animal_0[1];
			successObj.mXPos = mArr_img_animal_0[0];
			successObj.mYPos = mArr_img_animal_0[1];
			failObj.mXPos = mArr_img_animal_0[0];
			failObj.mYPos = mArr_img_animal_0[1];
			// 1
			animalObj = mAnimalObjList.get(1);
			successObj = mSuccessObjList.get(1);
			failObj = mFailObjList.get(1);
			animalObj.mXPos = mArr_img_animal_1[0];
			animalObj.mYPos = mArr_img_animal_1[1];
			successObj.mXPos = mArr_img_animal_1[0];
			successObj.mYPos = mArr_img_animal_1[1];
			failObj.mXPos = mArr_img_animal_1[0];
			failObj.mYPos = mArr_img_animal_1[1];
			// 2
			animalObj = mAnimalObjList.get(2);
			successObj = mSuccessObjList.get(2);
			failObj = mFailObjList.get(2);
			animalObj.mXPos = mArr_img_animal_2[0];
			animalObj.mYPos = mArr_img_animal_2[1];
			successObj.mXPos = mArr_img_animal_2[0];
			successObj.mYPos = mArr_img_animal_2[1];
			failObj.mXPos = mArr_img_animal_2[0];
			failObj.mYPos = mArr_img_animal_2[1];
			// 3
			animalObj = mAnimalObjList.get(3);
			successObj = mSuccessObjList.get(3);
			failObj = mFailObjList.get(3);
			animalObj.mXPos = mArr_img_animal_3[0];
			animalObj.mYPos = mArr_img_animal_3[1];
			successObj.mXPos = mArr_img_animal_3[0];
			successObj.mYPos = mArr_img_animal_3[1];
			failObj.mXPos = mArr_img_animal_3[0];
			failObj.mYPos = mArr_img_animal_3[1];
			// 4
			animalObj = mAnimalObjList.get(4);
			successObj = mSuccessObjList.get(4);
			failObj = mFailObjList.get(4);
			animalObj.mXPos = mArr_img_animal_4[0];
			animalObj.mYPos = mArr_img_animal_4[1];
			successObj.mXPos = mArr_img_animal_4[0];
			successObj.mYPos = mArr_img_animal_4[1];
			failObj.mXPos = mArr_img_animal_4[0];
			failObj.mYPos = mArr_img_animal_4[1];
			// 5
			animalObj = mAnimalObjList.get(5);
			successObj = mSuccessObjList.get(5);
			failObj = mFailObjList.get(5);
			animalObj.mXPos = mArr_img_animal_5[0];
			animalObj.mYPos = mArr_img_animal_5[1];
			successObj.mXPos = mArr_img_animal_5[0];
			successObj.mYPos = mArr_img_animal_5[1];
			failObj.mXPos = mArr_img_animal_5[0];
			failObj.mYPos = mArr_img_animal_5[1];
			// 6
			animalObj = mAnimalObjList.get(6);
			successObj = mSuccessObjList.get(6);
			failObj = mFailObjList.get(6);
			animalObj.mXPos = mArr_img_animal_6[0];
			animalObj.mYPos = mArr_img_animal_6[1];
			successObj.mXPos = mArr_img_animal_6[0];
			successObj.mYPos = mArr_img_animal_6[1];
			failObj.mXPos = mArr_img_animal_6[0];
			failObj.mYPos = mArr_img_animal_6[1];
			// 7
			animalObj = mAnimalObjList.get(7);
			successObj = mSuccessObjList.get(7);
			failObj = mFailObjList.get(7);
			animalObj.mXPos = mArr_img_animal_7[0];
			animalObj.mYPos = mArr_img_animal_7[1];
			successObj.mXPos = mArr_img_animal_7[0];
			successObj.mYPos = mArr_img_animal_7[1];
			failObj.mXPos = mArr_img_animal_7[0];
			failObj.mYPos = mArr_img_animal_7[1];
			// 8
			animalObj = mAnimalObjList.get(8);
			successObj = mSuccessObjList.get(8);
			failObj = mFailObjList.get(8);
			animalObj.mXPos = mArr_img_animal_8[0];
			animalObj.mYPos = mArr_img_animal_8[1];
			successObj.mXPos = mArr_img_animal_8[0];
			successObj.mYPos = mArr_img_animal_8[1];
			failObj.mXPos = mArr_img_animal_8[0];
			failObj.mYPos = mArr_img_animal_8[1];
		}
	}
	
	public void destroy() {
		stop();
		
		mBitmapManager.destroy();
		mSoundManager.destroy();
		mSensorManager.unregisterListener(this);
	}
	
	private GameObject createGameObject(int resId, int left, int width, int height, int fps, int frameCount, boolean loop, boolean active) {
		GameObject gameObj = new GameObject();
		gameObj.init(mBitmapManager.getBitmap(resId), left, width, height, fps, frameCount, loop);
		if (active) gameObj.reset();
		return gameObj;
	}

	public void start() {
		if (!mThread.isAlive()) {
			// Key
			for (int i = 0; i < mKey.length; i++) {
				mKey[i] = false;
			}
			// Touch
			mTouchX = -1;
			mTouchY = -1;
			// Accelerometer
			mAccelerometerX = 0;
			mAccelerometerY = 0;
			mAccelerometerZ = 0;
			// Score
			mScoreLevel = 0;
			mScoreTarget = 0;
			mScoreSuccess = 0;
			mScoreFail = 0;
			mScoreLife = MAX_LIFE;
			mScoreCombo = 0;
			mScore = 0;
			
			// Thread
			synchronized (mHolder) {
				mGameTime = System.currentTimeMillis();
				mExcessTime = 0;
				mLastTargetTime = 0;
			}
			mThread = new GameThread(this);
			mThread.setRunning(true);
			mThread.start();
			
			// Ready Image On
			mReadyObj.reset();
			
			// Ready Mode
			mMode = MODE_READY;
		}
	}
	
	public void stop() {
		if (mThread.isAlive()) {
			boolean retry = true;
			mThread.setRunning(false);
			while (retry) {
				try {
					// Thread Stop
					mThread.join();
					retry = false;
					
					// Music Stop
					mMediaPlayerDay.stop();
					mMediaPlayerNight.stop();
					
					// Stop Mode
					mMode = MODE_STOP;
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	public void pause() {
		// Background Pause
		mMediaPlayerBackground.pause();
		
		// Pause Image On
		mPauseObj.reset();
		
		// Pause Mode
		mMode = MODE_PAUSE;
	}
	
	public void resume() {
		synchronized (mHolder) {
			mGameTime = System.currentTimeMillis();
		}
		
		// Background Pause
		mMediaPlayerBackground.start();
		
		if (mMode == MODE_READY) {
			// Pause Image Off
			mPauseObj.mActive = false;
		} else if (mMode == MODE_PAUSE) {
			// Pause Image Off
			mPauseObj.mActive = false;
		}
		
		// Running Mode
		mMode = MODE_RUNNING;
	}
	
	public void gameover() {
		if (mMode != MODE_GAMEOVER) {
			// HighScore
			mHandler.post(new Runnable() {
				public void run() {
					((Game)mContext).showHighScoreDialog(mScore);
				}
			});
			
			// Gameover Image On
			mGameoverObj.reset();
			
			// GameOver Mode
			mMode = MODE_GAMEOVER;
		}
	}
	
	/**	Implements Update */
	
	public void update() {
		Log.d(LOG_TAG, "update...START");
		
		// Last Time
		long currTime = System.currentTimeMillis();
		if (mMode == MODE_RUNNING) {
			mExcessTime += currTime - mGameTime;
			mLastTargetTime += currTime - mGameTime;
		}
		mGameTime = currTime;
		
		// 배경 업그레이드
		if (mDay) {
			if (mExcessTime > mScoreLevel * LEVEL_INTERVAL + (LEVEL_INTERVAL / 2)) {
				mBackgroundBitmap = mBitmapManager.getBitmap(R.drawable.game_background_night);
				mMediaPlayerDay.pause();
				mMediaPlayerNight.start();
				mMediaPlayerBackground = mMediaPlayerNight;
				mDay = false;
			}
		} else {
			if (mExcessTime > mScoreLevel * LEVEL_INTERVAL + (LEVEL_INTERVAL)) {
				mBackgroundBitmap = mBitmapManager.getBitmap(R.drawable.game_background_day);
				mMediaPlayerDay.start();
				mMediaPlayerNight.pause();
				mMediaPlayerBackground = mMediaPlayerDay;
				mDay = true;
			}
		}
		
		// Update
		switch (mMode) {
		case MODE_READY:
			// Ready
			mReadyObj.update(mGameTime);
			break;
		case MODE_PAUSE:
			// Pause
			mPauseObj.update(mGameTime);
			break;
		case MODE_GAMEOVER:
			// GameOver
			mGameoverObj.update(mGameTime);
			break;
		case MODE_RUNNING:
			// Running
			long targetTime;
    		int targetIndex;
    		switch (mMenu) {
    		case Launcher.MENU_PRACTICE:
    			// 연습게임
    			targetTime = TARGET_INTERVAL * MAX_LEVEL;
    			if (mLastTargetTime > targetTime) {
    				mLastTargetTime = 0;
    				targetIndex = mScoreTarget % mAnimalCount;
    				GameObject animalObj = mAnimalObjList.get(targetIndex);
    				GameObject successObj = mSuccessObjList.get(targetIndex);
    				GameObject failObj = mFailObjList.get(targetIndex);
    				if (!(animalObj.mActive || successObj.mActive || failObj.mActive)) {
    					animalObj.reset();
    					mScoreTarget++;
    				}
    			}
    			if (mScoreLevel < MAX_LEVEL) {
    				mScoreLevel = ((int)mExcessTime) / LEVEL_INTERVAL;
    			}
    			break;
    		case Launcher.MENU_GAME:
    			// 점수 게임 처리
    			targetTime = TARGET_INTERVAL * (MAX_LEVEL + 1 - mScoreLevel);
    			if (mLastTargetTime > targetTime) {
    				mLastTargetTime = 0;
    				targetIndex = (int)(currTime % mAnimalCount);
    				GameObject animalObj = mAnimalObjList.get(targetIndex);
    				GameObject successObj = mSuccessObjList.get(targetIndex);
    				GameObject failObj = mFailObjList.get(targetIndex);
    				if (!(animalObj.mActive || successObj.mActive || failObj.mActive)) {
    					animalObj.reset();
    					mScoreTarget++;
    				}
    			}
    			// 게임 레벨
    			if (mScoreLevel < MAX_LEVEL) {
    				mScoreLevel = ((int)mExcessTime) / LEVEL_INTERVAL;
    			}
    			// 게임 오버 처리
    			if (mScoreLife <= 0) {
    				gameover();
    			}
    			break;
    		}
    		
    		// 게임 루틴 처리
    		GameObject animalObj;
    		GameObject successObj;
    		GameObject failObj;
    		for (int index = 0; index < mAnimalCount; index++) {
    			animalObj = mAnimalObjList.get(index);
    			successObj = mSuccessObjList.get(index);
    			failObj = mFailObjList.get(index);
    			
    			// Animal
    			if (animalObj.mActive) {
    				if (animalObj.mDRectangle.contains((int)mTouchX, (int)mTouchY)) {
    					// 동물 클릭 성공 후
    					mSoundManager.play(index);
    					animalObj.mActive = false;
    					successObj.reset();
    					mScoreSuccess++;
    					if (mScoreCombo < MAX_COMBO) {
    						mScoreCombo++;
    					} else {
    						mComboObj.reset();
    						mScoreCombo = 0;
    						if (mScoreLife < MAX_LIFE) {
    							mHeartObj.reset();
        						mScoreLife++;
        					} else {
        						mScore += 10;
        					}
    					}
    					mScore++;
    				} else {
    					// 동물 클릭 성공 전
    					if (animalObj.mCurrentFrame < animalObj.mNoOfFrames) {
    						animalObj.update(mGameTime);
    					} else {
    						mVibrator.vibrate(200);
    						animalObj.mActive = false;
    						failObj.reset();
    						mScoreFail++;
    						mScoreLife--;
    						mScoreCombo = 0;
    					}
    				}
    			}
    			
    			// 동물 울음 에니메이션 처리
    			if (successObj.mCurrentFrame < successObj.mNoOfFrames) {
    				successObj.update(mGameTime);
				} else {
					successObj.mActive = false;
				}
    			
    			// 동물 울음 실패 에니메이션 처리
    			if (failObj.mCurrentFrame < failObj.mNoOfFrames) {
    				failObj.update(mGameTime);
				} else {
					failObj.mActive = false;
				}
    			
    			// Heart / Combo
    			if (mHeartObj.mCurrentFrame < mHeartObj.mNoOfFrames - 1) {
    				mHeartObj.update(mGameTime);
    			}
    			if (mComboObj.mCurrentFrame < mComboObj.mNoOfFrames - 1) {
    				mComboObj.update(mGameTime);
    			}
    		}
    		break;
		}
		
		Log.d(LOG_TAG, "update...END");
	}
	
	/**	Implements onDraw */
    
    @Override
	public void onDraw(Canvas canvas) {
    	Log.d(LOG_TAG, "onDraw...START");
    	
    	// Rounding
    	mPaint.setARGB(255, 60, 180, 60);
    	canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);
    	
    	// translate,clip
    	canvas.translate(mOffsetX, mOffsetY);
    	canvas.clipRect(mOffsetX, mOffsetY, mOffsetX+mCanvasWidth, mOffsetY+mCanvasHeight);
    	
    	// Draw BackGround
    	canvas.drawBitmap(mBackgroundBitmap, 0, 0, null);
    	
    	// Draw
    	GameObject animalObj;
		GameObject successObj;
		GameObject failObj;
		for (int index = 0; index < mAnimalCount; index++) {
			animalObj = mAnimalObjList.get(index);
			successObj = mSuccessObjList.get(index);
			failObj = mFailObjList.get(index);
			
			animalObj.draw(canvas);
			successObj.draw(canvas);
			failObj.draw(canvas);
		}
		
		switch (mMode) {
		case MODE_READY:
			// Ready
			mReadyObj.draw(canvas);
			break;
		case MODE_PAUSE:
			// Pause
			mPauseObj.draw(canvas);
			break;
		case MODE_GAMEOVER:
			// GameOver
			mGameoverObj.draw(canvas);
			break;
		case MODE_RUNNING:
			// Running
			break;
		}
    	
    	// Info
    	drawScore(canvas);
    	//debugEnv(canvas);
    	
    	Log.d(LOG_TAG, "onDraw...END");
	}
    
    private void drawScore(Canvas canvas) {
    	if (mCanvasWidth < mCanvasHeight) {
	    	switch (mMenu) {
			case Launcher.MENU_PRACTICE:
		    	mPaint.setFakeBoldText(true);
		    	mPaint.setColor(Color.WHITE);
				canvas.drawText("SUCCESS: " + mScoreSuccess, mArr_practice_score_level_1[0], mArr_practice_score_level_1[1], mPaint);
				mPaint.setColor(Color.WHITE);
				canvas.drawText("FAIL: " + mScoreFail, mArr_practice_score_level_2[0], mArr_practice_score_level_2[1], mPaint);
				break;
			case Launcher.MENU_GAME:
				mHeartObj.draw(canvas);
				mComboObj.draw(canvas);
				
				mPaint.setFakeBoldText(true);
				mPaint.setColor(Color.WHITE);
				canvas.drawText("LEVEL: " + mScoreLevel, mArr_game_score_level_1[0], mArr_game_score_level_1[1], mPaint);
				mPaint.setColor(Color.WHITE);
				canvas.drawText("SCORE: " + mScore, mArr_game_score_level_2[0], mArr_game_score_level_2[1], mPaint);
		    	
				mPaint.setColor(Color.LTGRAY);
				canvas.drawRect(mArr_game_gauge_level_1[0]-1, mArr_game_gauge_level_1[1]-1, mArr_game_gauge_level_1[0]+mArr_game_gauge_level_1[2]+1, mArr_game_gauge_level_1[1]+mArr_game_gauge_level_1[3]+1, mPaint);
				mPaint.setColor(Color.RED);
				canvas.drawRect(mArr_game_gauge_level_1[0], mArr_game_gauge_level_1[1], mArr_game_gauge_level_1[0]+(mArr_game_gauge_level_1[2]*mScoreLife/MAX_LIFE), mArr_game_gauge_level_1[1]+mArr_game_gauge_level_1[3], mPaint);
				mPaint.setColor(Color.LTGRAY);
				canvas.drawRect(mArr_game_gauge_level_2[0]-1, mArr_game_gauge_level_2[1]-1, mArr_game_gauge_level_2[0]+mArr_game_gauge_level_2[2]+1, mArr_game_gauge_level_2[1]+mArr_game_gauge_level_2[3]+1, mPaint);
				mPaint.setColor(Color.MAGENTA);
				canvas.drawRect(mArr_game_gauge_level_2[0], mArr_game_gauge_level_2[1], mArr_game_gauge_level_2[0]+(mArr_game_gauge_level_2[2]*mScoreCombo/MAX_COMBO), mArr_game_gauge_level_2[1]+mArr_game_gauge_level_2[3], mPaint);
				break;
			}
    	} else {
    		switch (mMenu) {
			case Launcher.MENU_PRACTICE:
		    	mPaint.setFakeBoldText(true);
		    	mPaint.setColor(Color.WHITE);
				canvas.drawText("SUCCESS: " + mScoreSuccess, mArr_practice_score_level_1[2], mArr_practice_score_level_1[3], mPaint);
				mPaint.setColor(Color.WHITE);
				canvas.drawText("FAIL: " + mScoreFail, mArr_practice_score_level_2[2], mArr_practice_score_level_2[3], mPaint);
				break;
			case Launcher.MENU_GAME:
				mHeartObj.draw(canvas);
				mComboObj.draw(canvas);
				
				mPaint.setFakeBoldText(true);
				mPaint.setColor(Color.WHITE);
				canvas.drawText("LEVEL: " + mScoreLevel, mArr_game_score_level_1[2], mArr_game_score_level_1[3], mPaint);
				mPaint.setColor(Color.WHITE);
				canvas.drawText("SCORE: " + mScore, mArr_game_score_level_2[2], mArr_game_score_level_2[3], mPaint);
		    	
				mPaint.setColor(Color.LTGRAY);
				canvas.drawRect(mArr_game_gauge_level_1[4]-1, mArr_game_gauge_level_1[5]+1, mArr_game_gauge_level_1[4]+mArr_game_gauge_level_1[7]+1, mArr_game_gauge_level_1[5]-mArr_game_gauge_level_1[6]-1, mPaint);
				mPaint.setColor(Color.RED);
				canvas.drawRect(mArr_game_gauge_level_1[4], mArr_game_gauge_level_1[5], mArr_game_gauge_level_1[4]+mArr_game_gauge_level_1[7], mArr_game_gauge_level_1[5]-(mArr_game_gauge_level_1[6]*mScoreLife/MAX_LIFE), mPaint);
				mPaint.setColor(Color.LTGRAY);
				canvas.drawRect(mArr_game_gauge_level_2[4]-1, mArr_game_gauge_level_2[5]+1, mArr_game_gauge_level_2[4]+mArr_game_gauge_level_2[7]+1, mArr_game_gauge_level_2[5]-mArr_game_gauge_level_2[6]-1, mPaint);
				mPaint.setColor(Color.MAGENTA);
				canvas.drawRect(mArr_game_gauge_level_2[4], mArr_game_gauge_level_2[5], mArr_game_gauge_level_2[4]+mArr_game_gauge_level_2[7], mArr_game_gauge_level_2[5]-(mArr_game_gauge_level_2[6]*mScoreCombo/MAX_LIFE), mPaint);
				break;
			}
    	}
    }
    
    private void debugEnv(Canvas canvas) {
    	Paint paint = new Paint();
    	paint.setARGB(255, 255, 255, 255);
    	
    	String enveronment = String.format(
    			"LastTime:%s / Width:%s / Height:%s / CanvasWidth:%s / CanvasHeight:%s",
    			String.valueOf(mGameTime), canvas.getWidth(), canvas.getHeight(), String.valueOf(mCanvasWidth), String.valueOf(mCanvasHeight));
    	String keydown = "";
    	for (int i = 0; i < mKey.length; i++) {
    		if (mKey[i]) keydown += "Keydown:" + i;
    	}
    	String touch = String.format(
    			"touch X:%s / Y:%s",
    			String.valueOf(mTouchX), String.valueOf(mTouchY));
    	String accelerometer = String.format(
    			"Accelerometer X:%s / Y:%s / Z:%s",
    			String.valueOf(mAccelerometerX), String.valueOf(mAccelerometerY), String.valueOf(mAccelerometerZ));
    	
    	canvas.drawText(enveronment, 20, 20, paint);
    	canvas.drawText(keydown, 20, 40, paint);
    	canvas.drawText(touch, 20, 60, paint);
    	canvas.drawText(accelerometer, 20, 80, paint);
    }
    
    /**	Implements SurfaceView */
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	Log.d(LOG_TAG, "onKeyDown " + keyCode);
    	
		synchronized (mHolder) {
			mKey[keyCode] = true;
			return false;
		}
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		Log.d(LOG_TAG, "onKeyUp " + keyCode);
		
		synchronized (mHolder) {
			mKey[keyCode] = false;
			return false;
		}
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
		Log.d(LOG_TAG, "onTouchEvent " + event.getX() + "/" + event.getY());
		
		synchronized (getHolder()) {
        	switch (mMode) {
        	case MODE_READY:
        	case MODE_PAUSE:
        		switch (event.getAction()) {
        		case MotionEvent.ACTION_UP:
        			resume();
	        		return true;
        		}
        	case MODE_GAMEOVER:
        		switch (event.getAction()) {
        		case MotionEvent.ACTION_UP:
	        		((Activity)mContext).finish();
	        		return true;
        		}
        	case MODE_RUNNING:
        		switch (event.getAction()) {
	        	case MotionEvent.ACTION_DOWN:
	        		mTouchX = event.getX() + mOffsetX;
	        		mTouchY = event.getY() + mOffsetY;
	        		return true;
	        	case MotionEvent.ACTION_UP:
	        		mTouchX = -1;
	        		mTouchY = -1;
	        		return true;
	        	case MotionEvent.ACTION_MOVE:
	        		mTouchX = event.getX() + mOffsetX;
	        		mTouchY = event.getY() + mOffsetY;
	        		return true;
	        	}
        		return true;
        	}
        }
		return false;
    }

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		Log.d(LOG_TAG, "onWindowFocusChanged " + hasWindowFocus);
		
		super.onWindowFocusChanged(hasWindowFocus);
		
		if (!hasWindowFocus) {
			pause();
		}
	}
	
	/** Implements SensorEventListener */
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Log.d(LOG_TAG, "onSensorChanged " + event.values[0] + "/" + event.values[1] + "/" + event.values[2]);
		
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			mAccelerometerX = event.values[0];
			mAccelerometerY = event.values[1];
			mAccelerometerZ = event.values[2];
			break;
		}
	}
	
	
	/**	Implements SurfaceHolder.Callback */

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(LOG_TAG, "surfaceCreated...START");
		
		Canvas canvas = null;
		try {
			canvas = holder.lockCanvas(null);
			change(canvas.getWidth(), canvas.getHeight());
		} finally {
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
		
		Log.d(LOG_TAG, "surfaceCreated...END");
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(LOG_TAG, "surfaceDestroyed...START");
		Log.d(LOG_TAG, "surfaceDestroyed...END");
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.d(LOG_TAG, "surfaceDestroyed...START");
		
		change(width, height);
		
		Log.d(LOG_TAG, "surfaceDestroyed...END");
	}

}
