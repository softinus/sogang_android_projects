package com.raimsoft.matan.core;

import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.raimsoft.matan.activity.GameActivity;
import com.raimsoft.matan.util.FrameManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	public GameThread gameThread;
	private boolean bKeyResult;

	public GameView(GameActivity context, int nSelStage)
	{
		super(context);

		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		this.setFocusable(true);

		gameThread = new GameThread(context, holder, nSelStage);
	}

	public void SoundStop()
	{
		gameThread.SoundStop();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height)
	{ 	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		gameThread.setRunning(true);
		FrameManager.bPause= false; //

		if ( !(Thread.State.TERMINATED == gameThread.getState()) )
			gameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
        boolean retry = true;
        gameThread.setRunning(false);

        while (retry)
        {
            try
            {
            	gameThread.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
            	Log.e("GameView", e.toString());
            }
        }
	}

//	public void DestroyThread()
//	{
//		if(gameThread == null)
//			return;
//
//		try
//		{
//			gameThread.join();
//		}
//		catch(InterruptedException e)
//		{
//
//		}
//	}


	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		gameThread.OnTouchEvent(event);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		bKeyResult= gameThread.onKeyDown(keyCode, event);

		return bKeyResult;
	}

	@Override
	protected void onFocusChanged(boolean gainFocus, int direction,	Rect previouslyFocusedRect)
	{
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
	}
}
