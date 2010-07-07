package com.raimsoft.matan;

import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.raimsoft.activity.GameActivity;
import com.raimsoft.stage.StageManager;
import com.raimsoft.util.FrameManager;

public class GameThread extends Thread
{
	private final SurfaceHolder surfaceHolder; // 화면관리

	StageManager mStageMgr;		// 스테이지관리
	FrameManager mFrameMgr= FrameManager.getInstance(); 	// 프레임관리 (싱글톤)
	int nPresentStageID=0;		// 현재의 StageID
	int nPrevStageID=0;			// 한단계 루프 이전의 StageID


	public GameThread(GameActivity context, SurfaceHolder holder)
	{
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


	@Override
	public void run()
	{
		Canvas canvas = null;
		while(true)
		{
			mFrameMgr.IncreaseTotalFrame();				// ++TotalFrame

			try
			{
				Thread.sleep(10);

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
		}
	}

	public void OnTouchEvent(MotionEvent event)
	{
		synchronized (surfaceHolder)
		{
			mStageMgr.Touch(event);
		}
	}

	public void onKeyDown(int keyCode, KeyEvent event)
	{
		mStageMgr.KeyDown(keyCode,event);
	}

}
