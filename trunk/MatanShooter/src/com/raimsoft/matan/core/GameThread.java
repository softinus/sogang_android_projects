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

	Thread.State State;
	StageManager mStageMgr;		// 스테이지관리
	FrameManager mFrameMgr= FrameManager.getInstance(); 	// 프레임관리 (싱글톤)
	int nPresentStageID=0;		// 현재의 StageID
	int nPrevStageID=0;			// 한단계 루프 이전의 StageID


	public GameThread(GameActivity context, SurfaceHolder holder)
	{
		this.setName("MainThread");
		start(); // Thread 시작
		mStageMgr= new StageManager(context);
		surfaceHolder = holder;
	}


	/**
	 * 다음 스테이지로 간다.
	 */
	private void GotoStage()
	{
		switch(mStageMgr.GetNextStageID())
		{
		case 0:
			return;
		case StageManager.STAGE_MAIN:
			mStageMgr.ChangeStage(StageManager.STAGE_MAIN);
			break;
		case StageManager.STAGE_OPTION:
			mStageMgr.ChangeStage(StageManager.STAGE_OPTION);
			break;
		case StageManager.STAGE_SCENARIO:
			mStageMgr.ChangeStage(StageManager.STAGE_SCENARIO);
			break;
		case StageManager.STAGE_STORE:
			mStageMgr.ChangeStage(StageManager.STAGE_STORE);
			break;
		case StageManager.STAGE_INTER:
			mStageMgr.ChangeStage(StageManager.STAGE_INTER);
			break;
		case StageManager.STAGE_1:
			mStageMgr.ChangeStage(StageManager.STAGE_1);
			break;
		case StageManager.STAGE_2:
			mStageMgr.ChangeStage(StageManager.STAGE_2);
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
			GotoStage();
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
		while(true)
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
				Log.w("Thread::Routine", State.toString());
			}

			bKeyResult= mStageMgr.KeyDown(keyCode,event);
			return bKeyResult;
		}
	}

}
