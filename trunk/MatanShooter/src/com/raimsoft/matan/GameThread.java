package com.raimsoft.matan;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.raimsoft.activity.GameActivity;
import com.raimsoft.stage.StageManager;

public class GameThread extends Thread
{
	private final SurfaceHolder surfaceHolder;
	private boolean isThreadRun = false;

	StageManager mStageMgr;


	public GameThread(GameActivity context, SurfaceHolder holder)
	{
		start();
		mStageMgr= new StageManager(context);
		surfaceHolder = holder;
	}



	@Override
	public void run()
	{Canvas canvas = null;
		while(true)
		{
			try
			{
				Thread.sleep(10);

				synchronized(surfaceHolder)
				{
					canvas = surfaceHolder.lockCanvas(null);

					if(canvas == null)
						continue;

					mStageMgr.Render(canvas, 0);
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

}
