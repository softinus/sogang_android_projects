package com.raimsoft.view;

/**
 * Authoer : Choi Jun Hyeok (Raim)
 * Homepage: http://raimsoft.com
 * Copyright Raimsoftⓒ All reserved
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import com.raimsoft.game.TreadleManager;
import com.raimsoft.sensor.SensorFactory;
import com.raimsoft.sound.SoundManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	public GameThread thread;
	
	SensorFactory sf= SensorFactory.getSensorFactory();
	
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
		
		this.thread.mPlayer= new Player(this, 150,430, 45,50, R.drawable.nui_jump_left);
		this.thread.treadleMgr= new TreadleManager(this);
		this.thread.mMonster= new Monster(this, -1, -1 ,50,45, R.drawable.bird_fly_1);
		
		pm= (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		wl= pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
	}
	


	public class GameThread extends Thread
	{
		private GameView view;
		
		private SurfaceHolder mSurfaceHolder;// 화면 제어
		private boolean bRun=true;			// 동작 여부
		private boolean bPlayer_ImgRefreshed=true;	// 이미지 새로고침(플레이어)
		private boolean bTreadle_ImgRefreshed=true;	// 이미지 새로고침(발판)
		private boolean bMonster_ImgRefreshed=true;	// 이미지 새로고침(몬스터)

		public Player mPlayer;				// 플레이어 객체
		public TreadleManager treadleMgr;	// 발판 객체
		public Monster mMonster;			// 몬스터 객체
		
		private Resources mRes;				// 리소스
		private Paint pDebug=new Paint();	// 페인트
		private Paint pScore=new Paint();

		private Bitmap bBackground;			// 배경
		
		private int AccFrame;				// 누적프레임
		private int FPS;					// 초당 프레임
		private long Render1ForTime;			// 렌더1번에 걸리는 시간
		private long RenderAccTime;			// 1초 렌더 누적시간
		private long curTime, oldTime;		// 현재시간, 지난시간
		private int delTime=5;				// Thread딜레이
		
		public int BackSize=1920;			// 배경세로길이
		public int cnt_Step=0;				// 발판 밟은 수 
		public int gameScore=0;
		
		Canvas canvas=null;
		//Bundle SaveBox=new Bundle();
		
		SoundManager sm;
				
		// 메인스레드의 생성자
		public GameThread (SurfaceHolder _Holder, Context _Context)
		{			
			mSurfaceHolder= _Holder;
			mRes= _Context.getResources();
			
			pDebug.setTextSize(12);
			pDebug.setAntiAlias(true);
			pDebug.setColor(Color.argb(0xff, 255, 0, 255));
			
			pScore.setTextSize(24);
			pScore.setAntiAlias(true);
			pScore.setColor(Color.argb(0xff, 255, 0, 255));			
			
			bBackground= BitmapFactory.decodeResource(mRes, R.drawable.background_1);
		}
		

		
		public void run()
		{
			while(true)
			{
				try
				{
					canvas= mSurfaceHolder.lockCanvas();
					synchronized (mSurfaceHolder)
					{
						oldTime= System.currentTimeMillis();
						canvas.save();
						
						doDrawBackGround(canvas);
						doDrawObject(canvas);
						//doDrawText(canvas);
						doDrawScore(canvas);
						
						if (bRun) {	doMove(); }
						
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
		
		void doDrawBackGround(Canvas c)
		{			
			c.drawBitmap(bBackground,
			new Rect(0, BackSize-view.getHeight(),view.getWidth(),BackSize)
			, new Rect(0,0,view.getWidth(),view.getHeight()), null);
		}
		
		void doDrawText(Canvas c)
		{
			c.drawText("BackSize= "+ Float.toString(BackSize), 5, 45, pDebug);

//			c.drawText("FPS= " + Float.toString(FPS), 5, 30, pDebug);
//			c.drawText("Frame= " + Float.toString(AccFrame), 5, 15, pDebug);
//			c.drawText("X= "+Float.toString(mPlayer.getX()) 
//						+ ", Y= "+Float.toString(mPlayer.getY()),
//						mPlayer.getX(), mPlayer.getY(), pDebug);
//			c.drawText("State= " + Float.toString(mPlayer.State), 5, 45, pDebug);
//			
//			c.drawText("X= " + Float.toString(sf.getSensorValue()[0])
//						+"Y= "+ Float.toString(sf.getSensorValue()[1])
//						+"Z= "+ Float.toString(sf.getSensorValue()[2])
//						, 5, 60, pDebug);
			
			//for (int i=0; i<treadleMgr.getCount(); i++)
			//{
//			c.drawText("X= "+ Float.toString(treadleMgr.treadle[i].getX())
//						+", Y= "+ Float.toString(treadleMgr.treadle[i].getY()),
//						treadleMgr.treadle[i].getX(), treadleMgr.treadle[i].getY(), p);
			//}
		}
		
		void doDrawScore(Canvas c)
		{
			c.drawText("점수 : " + Float.toString(gameScore), 5, 20, pScore);
		}
		

		
		
		void doDrawObject(Canvas c)
		{			
			if(bPlayer_ImgRefreshed)
			{
				//Log.v("verbose", "Call Player_ImgRefreshed");
				mPlayer.Img_Drawable= mRes.getDrawable(mPlayer.Img_id);
				bPlayer_ImgRefreshed= false;
			}
			
			if(bTreadle_ImgRefreshed)
			{
				for (int i=0; i<treadleMgr.getCount(); i++)
				{
					
					Log.v("Draw Treadle", i + "th treadle Loading");
					treadleMgr.treadle[i].Img_Drawable= mRes.getDrawable(treadleMgr.treadle[i].Img_id);	
				}
				bTreadle_ImgRefreshed=false;
			}
			
			if(bMonster_ImgRefreshed)
			{
				mMonster.Img_Drawable= mRes.getDrawable(mMonster.Img_id);
				bMonster_ImgRefreshed=false;
			}
			
			
			
			
			for (int i=0; i<treadleMgr.getCount(); i++)
			{
				//Log.v("Draw Treadle", i + "th treadle Drawing");
				if (IsNotClipped(mPlayer.getY(), treadleMgr.treadle[i].getY())) {
					treadleMgr.treadle[i].Img_Drawable.setBounds(treadleMgr.treadle[i].getObjectForRect());
					treadleMgr.treadle[i].Img_Drawable.draw(c);				
				}
			}
			
			if (IsNotClipped(mPlayer.getY(), mMonster.getY()))
			{
				mMonster.Img_Drawable.setBounds(mMonster.getObjectForRect());
				mMonster.Img_Drawable.draw(c);
			}
			
			
			
			mPlayer.Img_Drawable.setBounds(mPlayer.getObjectForRect());
			mPlayer.Img_Drawable.draw(c);
		}
		
		public void doMove()
		{
			if (mPlayer.bCrushed)
			{
				mPlayer.CrushFall();
			}else{
				mPlayer.SensorMove( sf.getCompressFloat2X( sf.getSensorValue() ) );
				mPlayer.JumpAlways();
			}
			
			mMonster.Move_Bird();
			treadleMgr.ALL_Treadle_Stepped();
			
			mPlayer.CollisionMonster(mMonster.getObjectForRect());
			
			for (int i=0; i<treadleMgr.getCount(); i++)
			{
				if (mPlayer.bJump==true)
				{
					mPlayer.CollisionTreadle(treadleMgr.treadle[i].getObjectForRectHalf(true)
							, treadleMgr.treadle[i],	treadleMgr);
				}
			}
		}
		
		
		/**
		 * 캐릭터와 발판들의 거리를 계산하여 화면 밖에 있는 객체들은 DRAW하지 않습니다.
		 * @param player_Y : 캐릭터의 Y값
		 * @param Object_Y : 체크할 다른 객체의 Y값
		 * @return 화면안에 있으면:true, 화면밖에 있으면:false
		 */
		boolean IsNotClipped(int player_Y, int Object_Y)
		{
			if (Math.abs(Object_Y - player_Y) > view.getHeight())
				return false;
			return true;
		}
		
		void LifeCheck()
		{
			if (!mPlayer.isLive())
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
		
		
		public void setPlayerImg_Refresh()
		{
			bPlayer_ImgRefreshed= true;
		}
		public void setTreadleImg_Refresh()
		{
			bTreadle_ImgRefreshed= true;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//this.thread.mPlayer.setState(keyCode);
		Log.d("Key", Float.toString(keyCode));
		
		if (keyCode==event.KEYCODE_MENU)
		{
			if (thread.bRun==true)
			{
//				thread.SaveBox.putInt("PLAYER_X", thread.mPlayer.getX());
//				thread.SaveBox.putInt("PLAYER_Y", thread.mPlayer.getY());
				Log.v("MENU", "bRun=false");
				thread.setRunning(false);
			}else{
//				thread.mPlayer.SetPos(thread.SaveBox.getInt("PLAYER_X"),
//									  thread.SaveBox.getInt("PLAYER_Y"));
				Log.v("MENU", "bRun=true");
				thread.setRunning(true);
			}
		}
		
		return super.onKeyDown(keyCode, event);
	}
	


	@Override
	public boolean onTouchEvent(MotionEvent event) {

		//if (thread.mPlayer.getObjectForRect().contains((int)event.getX(), (int)event.getY()))
		//	this.thread.mPlayer.setJumpIndex(0);
		
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
