package com.raimsoft.matan.core;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.raimsoft.matan.activity.GameActivity;
import com.raimsoft.matan.stage.StageManager;
import com.raimsoft.matan.util.FrameManager;

public class GameThread extends Thread
{

	private final SurfaceHolder surfaceHolder; // 화면관리
	private boolean bKeyResult;
	private boolean bLoop= true;

	private Thread.State State;
	static private StageManager mStageMgr;		// 스테이지관리
	private FrameManager mFrameMgr= FrameManager.getInstance(); 	// 프레임관리 (싱글톤)


	GameThread(GameActivity context, SurfaceHolder holder, int nSelStage)
	{
		this.setName("GameThread");
		//start(); // Thread 시작
		mStageMgr= new StageManager(context, nSelStage);
		surfaceHolder = holder;
	}

	public void SoundStop()
	{
		mStageMgr.SoundStop();
	}


	/**
	 * 다음 스테이지로 간다.
	 */
	public static void GotoCurrStage()
	{
		switch(mStageMgr.GetNextStageID())
		{
		case StageManager.STAGE_1:
			mStageMgr.ChangeStage(StageManager.STAGE_1);
			break;
		case StageManager.STAGE_2:
			mStageMgr.ChangeStage(StageManager.STAGE_2);
			break;
		case StageManager.STAGE_3:
			mStageMgr.ChangeStage(StageManager.STAGE_3);
			break;
		}
	}

	/**
	 * 다음 스테이지로 넘어가길 원하면
	 * @param _UpdateRes : Update에서 넘어온 결과
	 */
	private void bStageUpdated(boolean _UpdateRes)
	{
		if (_UpdateRes)
		{
			GotoCurrStage();
		}
	}

	private void beforeRoutine()
	{
		mFrameMgr.IncreaseTotalFrame();				// ++TotalFrame
		FrameManager.CurrentTime= SystemClock.currentThreadTimeMillis();
	}


	@Override
	public void run()
	{
		Canvas canvas = null;

		while( bLoop )
		{
			beforeRoutine();

			try
			{
				Thread.sleep(FrameManager.FrameDealy);

				synchronized(surfaceHolder)
				{
					canvas = surfaceHolder.lockCanvas(null);

					if(canvas == null)
						continue;

					if( !FrameManager.bPause )
						bStageUpdated(mStageMgr.Update());	// Update

					mStageMgr.Render(canvas);			// Render
				}
			}
			catch(Exception e)
			{
				Log.e("GameLoop::Exception", e.toString());
			}
			finally
			{
				if(canvas != null)
					surfaceHolder.unlockCanvasAndPost(canvas);
			}
			FrameManager.CurrentAfterTime= SystemClock.currentThreadTimeMillis();
			FrameManager.CalRealTime(FrameManager.CurrentAfterTime, FrameManager.CurrentTime);
		}
	}

	public void OnTouchEvent(MotionEvent event)
	{
		mStageMgr.Touch(event);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		synchronized (surfaceHolder)
		{
			if (keyCode==KeyEvent.KEYCODE_MENU)
			{
				State= Thread.currentThread().getState();
				Log.i("Thread::Routine", State.toString());
			}

			bKeyResult= mStageMgr.KeyDown(keyCode,event);
			return bKeyResult;
		}
	}

	public void setRunning(boolean _bRun)
	{
		bLoop= _bRun;
	}

}
