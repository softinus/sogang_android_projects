package kr.co.joynuri.animalorchestra;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class GameObject {
	
	private static final String LOG_TAG = GameObject.class.getSimpleName();
	
	public Bitmap mBitmap;
	public int mSpriteHeight;
	public int mSpriteWidth;
	public Rect mSRectangle;
	public Rect mDRectangle;
	
	public int mFPS;
	public int mNoOfFrames;
	public int mCurrentFrame;
	public long mFrameTimer;
	
	public int mXPos;
	public int mYPos;
	public boolean mLoop;
	public boolean mActive;
	
	public GameObject() {
		mSRectangle = new Rect(0, 0, 0, 0);
		mDRectangle = new Rect(0, 0, 0, 0);
		mFrameTimer = 0;
		mCurrentFrame = 0;
		mXPos = 0;
		mYPos = 0;
	}
	
	public void init(Bitmap bitmap, int left, int width, int height, int fps, int frameCount, boolean loop) {
		mBitmap = bitmap;
		mSpriteWidth = width;
		mSpriteHeight = height;
		mSRectangle.top = 0;
		mSRectangle.bottom = mSpriteHeight;
		mSRectangle.left = left;
		mSRectangle.right = left + mSpriteWidth;
		mFPS = 1000 / fps;
		mNoOfFrames = frameCount;
		mLoop = loop;
		mActive = false;
	}
	
	public void update(long gameTimer) {
		Log.d(LOG_TAG, "update...START");
		
		if (mActive) {
			if(gameTimer > mFrameTimer + mFPS) {
				mFrameTimer = gameTimer;
				mCurrentFrame += 1;
		
				if(mLoop && mCurrentFrame >= mNoOfFrames) {
					mCurrentFrame = 0;
				}
			}
		 
		    mSRectangle.top = mCurrentFrame * mSpriteHeight;
		    mSRectangle.bottom = mSRectangle.top + mSpriteHeight;
		}
		
	    Log.d(LOG_TAG, "update...END");
	}
	
	public void draw(Canvas canvas) {
		Log.d(LOG_TAG, "draw...START");
		
		if (mActive) {
			mDRectangle = new Rect(mXPos, mYPos,
					mXPos + mSpriteWidth, mYPos + mSpriteHeight);
			canvas.drawBitmap(mBitmap, mSRectangle, mDRectangle, null);
		}
		
		Log.d(LOG_TAG, "draw...END");
	}
	
	public void reset() {
		mCurrentFrame = -1;
		mActive = true;
	}
}
