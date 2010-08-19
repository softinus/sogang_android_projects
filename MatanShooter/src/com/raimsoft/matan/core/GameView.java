package com.raimsoft.matan.core;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.raimsoft.matan.activity.GameActivity;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	public GameThread gameThread;
	private boolean bKeyResult;

	public GameView(GameActivity context)
	{
		super(context);

		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		gameThread = new GameThread(context, holder);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height)
	{

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		this.DestroyThread();
	}

	private void DestroyThread()
	{
		if(gameThread == null)
			return;

		try
		{
			gameThread.join();
		}
		catch(InterruptedException e)
		{

		}
	}


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
}
