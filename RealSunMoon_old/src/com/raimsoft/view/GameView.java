package com.raimsoft.view;

/**
 * Authoer : Choi Jun Hyeok (Raim)
 * Homepage: http://raimsoft.com
 * Copyright Raimsoftⓒ All reserved
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.raimsoft.activity.GameActivity;
import com.raimsoft.activity.R;
import com.raimsoft.game.RenderObject;
import com.raimsoft.game.StageManager;
import com.raimsoft.object.Monster;
import com.raimsoft.object.Player;
import com.raimsoft.object.Rope;
import com.raimsoft.object.TreadleManager;
import com.raimsoft.sensor.SensorFactory;
import com.raimsoft.sound.SoundManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	public GameThread thread;
	
	
	
	PowerManager pm;
	PowerManager.WakeLock wl;
	
	GameActivity gameContext;
	
	public GameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		SurfaceHolder mHolder= getHolder();
		mHolder.addCallback(this);
		
		setFocusable(true);	// 포커스를 잡아준다. (키입력 등...)
		
		thread= new GameThread(mHolder, context);	
		gameContext= (GameActivity) context;
		this.thread.view= this;
		
		
		
		pm= (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		wl= pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
	}
	


	public class GameThread extends Thread
	{
		private GameView view;
		
		private SurfaceHolder mSurfaceHolder;// 화면 제어
		private boolean bRun=true;			// 동작 여부
		private boolean bMove=true;			// 움직임 여부 (일시정지)

		private Resources mRes;				// 리소스

		//private Drawable dGameClear;

		public int cnt_Step=0;				// 발판 밟은 수 
		
		private int AccFrame;				// 누적프레임
		private int FPS;					// 초당 프레임
		private long Render1ForTime;			// 렌더1번에 걸리는 시간
		private long RenderAccTime;			// 1초 렌더 누적시간
		private long curTime, oldTime;		// 현재시간, 지난시간
		private int delTime=5;				// Thread딜레이
		
		private RenderObject renderObj;

		
		Canvas canvas=null;
		//Bundle SaveBox=new Bundle();
		
		StageManager stageMgr;
		SoundManager sm;
				
		// 메인스레드의 생성자
		public GameThread (SurfaceHolder _Holder, Context _Context)
		{			
			mSurfaceHolder= _Holder;
			mRes= _Context.getResources();
			
			renderObj= new RenderObject(mRes, _Context);
			
			
			stageMgr=new StageManager();
			
			stageMgr.ChangeStage(1);
			
//			sm=new SoundManager(_Context);
//			sm.create();
//			sm.load(0, R.raw.game_bgm);
//			sm.play(0);
			
		}
		

		
		public void run()
		{
			while(bRun)
			{
				try
				{
					canvas= mSurfaceHolder.lockCanvas();
					synchronized (mSurfaceHolder)
					{
						oldTime= System.currentTimeMillis();
						canvas.save();
						
						stageMgr.Render(canvas);
						
						if (bMove)	stageMgr.FrameMove();
						
						canvas.restore();
						
						sleep(delTime);
						++AccFrame;
						
						curTime= System.currentTimeMillis() - delTime;
						
						Render1ForTime= curTime - oldTime;
						RenderAccTime+= Render1ForTime;
						
						if (RenderAccTime > 1000)
						{
							FPS= AccFrame;
							AccFrame=0;
							RenderAccTime=0;
						}
						
						stageMgr.LikeCheck();
					}
				}catch (InterruptedException e) {
					e.printStackTrace();
				}finally
				{
					if (canvas != null)
					{
						mSurfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
		
		
		

		
		/** 캐릭터 동작 설정
		* @param _Run : 동작 설정 boolean값*/
		public void setRunning(boolean _Run)
		{
			bRun=_Run;
		}
		/**
		 * 움직임 설정 (일시정지)
		 * @param _Move : 움직임 설정 boolean값
		 */
		public void setMoveing(boolean _Move)
		{
			bMove= _Move;
		}
		

	}
		


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		
		//this.thread.mPlayer.setState(keyCode);
		Log.d("Key", Float.toString(keyCode));
		
//		if (keyCode==event.KEYCODE_MENU)
//		{
//			if (thread.bRun==true)
//			{
//				thread.SaveBox.putInt("PLAYER_X", thread.mPlayer.getX());
//				thread.SaveBox.putInt("PLAYER_Y", thread.mPlayer.getY());
//				Log.v("MENU", "bRun=false");
//				thread.setRunning(false);
//			}else{
//				thread.mPlayer.SetPos(thread.SaveBox.getInt("PLAYER_X"),
//									  thread.SaveBox.getInt("PLAYER_Y"));
//				Log.v("MENU", "bRun=true");
//				thread.setRunning(true);
//				thread.run();
//			}
//		}
		
		if (thread.bMove)
		{
			this.thread.setMoveing(false);
		}else{
			this.thread.setMoveing(true);
		}
		
		
		
		return super.onKeyDown(keyCode, event);
	}
	


	@Override
	public boolean onTouchEvent(MotionEvent event) {

		//if (thread.mPlayer.getObjectForRect().contains((int)event.getX(), (int)event.getY()))
		//this.thread.mPlayer.setJumpIndex(0);
		
		return super.onTouchEvent(event);
	}

	
	
// ===================== 이 밑부터 SurfaceHolder.CallBack ===================== //
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height)
			{}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		thread.setRunning(true);
		wl.acquire();	
		thread.start();
			
	}
	


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
            	//wl.release();
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
	}

}