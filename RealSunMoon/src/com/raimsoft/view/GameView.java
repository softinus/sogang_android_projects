package com.raimsoft.view;

/**
 * @author : Choi Jun Hyeok (Raim)
 * @link : http://raimsoft.com
 * @Copyright Raimsoftⓒ All reserved
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.raimsoft.activity.GameActivity;
import com.raimsoft.activity.R;
import com.raimsoft.game.Monster;
import com.raimsoft.game.Player;
import com.raimsoft.game.Rope;
import com.raimsoft.game.TreadleManager;
import com.raimsoft.sensor.SensorFactory;
import com.raimsoft.sound.SoundManager;
import com.raimsoft.stage.StageManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	public GameThread thread;
	
	
	PowerManager pm;
	PowerManager.WakeLock wl;
	
	GameActivity gameContext;
	
	public GameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		Log.i("GameView", "CALL Constructor");
		
		SurfaceHolder mHolder= getHolder();
		mHolder.addCallback(this);
		
		setFocusable(true);	// 포커스를 잡아준다. (키입력 등...)
		
		thread= new GameThread(mHolder, context);	
		gameContext= (GameActivity) context;
			
		this.thread.mStageMgr.GetStage().view= this;
		
		this.thread.mStageMgr.GetStage().mPlayer= new Player(this, 150,430, 45,50, R.drawable.nui_jump_left);
		this.thread.mStageMgr.GetStage().treadleMgr= new TreadleManager(this);
		this.thread.mStageMgr.GetStage().mMonster= new Monster(this, -1, -1 ,50,45, R.drawable.bird_fly_1);
		this.thread.mStageMgr.GetStage().mRope= new Rope(this, 140,-2, 17,168, R.drawable.new_rope);
		
		pm= (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		wl= pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
	}
	

	public class GameThread extends Thread
	{
		private SurfaceHolder mSurfaceHolder;// 화면 제어
		private boolean bRun=true;			// 동작 여부
		private boolean bMove=true;			// 움직임 여부 (일시정지)

		public Resources mRes;				// 리소스

		private int AccFrame;				// 누적프레임
		private int FPS;					// 초당 프레임
		private long Render1ForTime;			// 렌더1번에 걸리는 시간
		private long RenderAccTime;			// 1초 렌더 누적시간
		private long curTime, oldTime;		// 현재시간, 지난시간
		private int delTime=5;				// Thread딜레이
		
		Canvas canvas=null;
		
		public StageManager mStageMgr;
				
		SoundManager sm;
				
		// 메인스레드의 생성자
		public GameThread (SurfaceHolder _Holder, Context _Context)
		{
			Log.i("GameThread", "CALL Constructor");
			
			mSurfaceHolder= _Holder;
			mRes= _Context.getResources();
						
			mStageMgr=new StageManager();
			this.mStageMgr.GetStage().mRes= this.mRes;
			
			this.mStageMgr.AllSetUp();
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
						
						mStageMgr.AllDraw(canvas);
						if (bMove) mStageMgr.AllUpdate();
						
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
						
						this.LifeCheck();
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
		
		void LifeCheck()
		{
			if (!mStageMgr.GetStage().mPlayer.isLive())
			{
				gameContext.NextGameOverActivity();
			}
		}
		
		/** 스레드 동작 설정
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
	public void surfaceCreated(SurfaceHolder holder)
	{
		
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