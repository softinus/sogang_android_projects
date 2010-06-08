package com.raimsoft.sound;

import java.util.Collection;
import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;


public class SoundManager {

	private static final String LOG_TAG = SoundManager.class.getSimpleName();

	private Context mContext;
	private SoundPool mSoundPool;
	private HashMap<Integer, Integer> mSoundPoolMap;
	private AudioManager  mAudioManager;

	public static boolean bSoundOpt= true;

	public SoundManager(Context context) {
		mContext = context;
	}

	public void create() {
		mSoundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
		mSoundPoolMap = new HashMap<Integer, Integer>();
		mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
	}

	public void destroy() {
		if (mSoundPoolMap != null) {
			Collection<Integer> soundIds = mSoundPoolMap.values();
			for (int soundId : soundIds) {
				mSoundPool.unload(soundId);
				Log.d(LOG_TAG, "destroy sound id " + soundId);
			}
			mSoundPoolMap = null;
		}
	}

	public void load(int key, int resId) {
		Log.d(LOG_TAG, "load...START");

		if (!mSoundPoolMap.containsKey(key)) {
			mSoundPoolMap.put(key, mSoundPool.load(mContext, resId, 1));
		}

		Log.d(LOG_TAG, "load...END");
	}

	public void play(int key) {
		Log.d(LOG_TAG, "play...START");

		int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play(
				mSoundPoolMap.get(key),
				streamVolume, streamVolume,
				1, 0, 1f);

		Log.d(LOG_TAG, "play...END");
	}

	public void playLoop(int key) {
		Log.d(LOG_TAG, "playLoop...START");

		int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		mSoundPool.play(
				mSoundPoolMap.get(key),
				streamVolume, streamVolume,
				1, -1, 1f);

		Log.d(LOG_TAG, "playLoop...END");
	}

	public void stop(int key) {
		mSoundPool.stop(mSoundPoolMap.get(key));
	}

	public void pause(int key) {
		mSoundPool.pause(mSoundPoolMap.get(key));
	}

	public void resume(int key) {
		mSoundPool.resume(mSoundPoolMap.get(key));
	}

}

