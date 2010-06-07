package com.raimsoft.view;

/**
 * @author : Choi Jun Hyeok (Raim)
 * @link : http://raimsoft.com
 * @Copyright Raimsoftⓒ All reserved
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.raimsoft.activity.GameActivity;
import com.raimsoft.activity.R;
import com.raimsoft.game.DarkCloud;
import com.raimsoft.game.FakeCloudList;
import com.raimsoft.game.ItemList;
import com.raimsoft.game.Monster;
import com.raimsoft.game.Player;
import com.raimsoft.game.Rope;
import com.raimsoft.game.TreadleManager;
import com.raimsoft.stage.StageManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	public GameThread thread;

	PowerManager pm;
	PowerManager.WakeLock wl;

	public GameActivity gameContext;

	public GameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		Log.i("GameView", "Call Constructor");

		SurfaceHolder mHolder= getHolder();
		mHolder.addCallback(this);

		setFocusable(true);	// 포커스를 잡아준다. (키입력 등...)

		thread= new GameThread(mHolder, context);
		gameContext= (GameActivity) context;

		this.thread.mStageMgr.mStage.view= this;	// 뷰 넘겨줌

		this.thread.mStageMgr.mStage.mPlayer= new Player(this, 150,430, 45,50, R.drawable.nui_jump_left);
		this.thread.mStageMgr.mStage.mMonster= new Monster(this, -1, -1 ,50,45, R.drawable.bird_fly_1);
		this.thread.mStageMgr.mStage.mItemList= new ItemList(this);
		this.thread.mStageMgr.mStage.mFakeList= new FakeCloudList(this);
		this.thread.mStageMgr.mStage.mRope= new Rope(this, 140,-2, 17,168, R.drawable.new_rope);
		this.thread.mStageMgr.mStage.treadleMgr= new TreadleManager(this);
		this.thread.mStageMgr.mStage.mDark= new DarkCloud(this, 0,0, 71,38, R.drawable.darkcloud);


		pm= (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		wl= pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
	}


	public class GameThread extends Thread
	{
		private SurfaceHolder mSurfaceHolder;// 화면 제어
		private boolean bRun=true;			// 동작 여부
		private boolean bMove=true;			// 움직임 여부 (일시정지)
		private boolean bSetupInit=true;

		public Resources mRes;				// 리소스

		private int AccFrame;				// 누적프레임
		private int FPS;					// 초당 프레임
		private long Render1ForTime;			// 렌더1번에 걸리는 시간
		private long RenderAccTime;			// 1초 렌더 누적시간
		private long curTime, oldTime;		// 현재시간, 지난시간
		public  long lightTime=	0;			// 번개 타이머
		private int delTime=5;				// Thread딜레이

		Canvas canvas=null;

		public StageManager mStageMgr;

		// 메인스레드의 생성자
		public GameThread (SurfaceHolder _Holder, Context _Context)
		{
			Log.i("GameThread", "CALL Constructor");

			mSurfaceHolder= _Holder;
			mRes= _Context.getResources();

			mStageMgr=new StageManager();
			mStageMgr.mStage.mContext= _Context;
			mStageMgr.mStage.mRes= this.mRes;
		}



		public void run()
		{
			while(bRun)	// 게임 루프 (Game Loop)
			{
				try
				{
					canvas= mSurfaceHolder.lockCanvas();
					synchronized (mSurfaceHolder)
					{
						oldTime= System.currentTimeMillis();
						canvas.save();

						if (bSetupInit)
						{
							this.mStageMgr.StageSetUp();
							bSetupInit=false;
						}

						mStageMgr.StageDraw(canvas);	//현재 스테이지를 모두 그림

						if (bMove && !mStageMgr.mStage.bGameClear)
							mStageMgr.StageUpdate();

						canvas.restore();


						sleep(delTime);
						clacFrame();

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

		/**
		 * 게임오버시 게임오버Activity로
		 */
		public void gameOver()
		{
			gameContext.NextGameOverActivity();
		}

		/**
		 * 프레임 계산 모듈
		 */
		private void clacFrame()
		{
			++AccFrame;

			curTime= System.currentTimeMillis() - delTime;

			Render1ForTime= curTime - oldTime;
			RenderAccTime+= Render1ForTime;
			lightTime	 += Render1ForTime;

			if (RenderAccTime > 1000)
			{
				FPS= AccFrame;
				AccFrame=0;
				RenderAccTime=0;
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

		public void setupInit()
		{
			bSetupInit= true;
		}
		public Context getGameContext()
		{
			return gameContext;
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

		if (keyCode == KeyEvent.KEYCODE_MENU)
		{
			if (thread.bMove) 	// 일시정지와 해제
			{
				gameContext.NextOptionActivity();
				this.thread.setMoveing(false);
			}else{
				this.thread.setMoveing(true);
			}
			return super.onKeyDown(keyCode, event);
		}
		else if (keyCode == android.view.KeyEvent.KEYCODE_BACK)
		{
			return true;
		}else{
			//return super.onKeyDown(keyCode, event);
			return false;
		}
	}



	@Override
	public boolean onTouchEvent(MotionEvent event)
	{

		//if (thread.mPlayer.getObjectForRect().contains((int)event.getX(), (int)event.getY()))
		//this.thread.mPlayer.setJumpIndex(0);

		if (thread.bMove) 	// 일시정지와 해제
		{
			gameContext.NextOptionActivity();
			this.thread.setMoveing(false);
		}else{
			this.thread.setMoveing(true);
		}
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

		if ( !(Thread.State.TERMINATED == thread.getState()) )
			thread.start();


	}



	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry)
        {
            try
            {
            	wl.release();
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            	Log.e("GameView", e.toString());
            }
        }
	}

}