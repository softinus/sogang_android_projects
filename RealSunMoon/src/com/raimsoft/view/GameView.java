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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.raimsoft.activity.R;
import com.raimsoft.game.Player;
import com.raimsoft.sensor.SensorFactory;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	public ImgThread thread;
	SensorFactory sf= SensorFactory.getSensorFactory();
	
	public GameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		SurfaceHolder mHolder= getHolder();
		mHolder.addCallback(this);
		
		setFocusable(true);	// 포커스를 잡아준다. (키입력 등...)
		
		thread= new ImgThread(mHolder, context);
		
		
		this.thread.mPlayer=new Player(this, 150,430, 45,50, R.drawable.base_char_left);
		this.thread.viewSize_W= this.getWidth();
		this.thread.viewSize_H= this.getHeight();
	}
	


	public class ImgThread extends Thread
	{
		private SurfaceHolder mSurfaceHolder;// 화면 제어
		private boolean mRun=true;			// 동작 여부
		private boolean bImg_Refreshed=true;
		
		private Resources mRes;				// 리소스

		private Bitmap bBackground;			// 배경
		private Drawable Base_char;			// 캐릭터 이미지
		public Player mPlayer;				// 플레이어 객체

		private int Frame, fps, curTime;	// 프레임, 초당프레임, 현재프레임
		private int delTime=5;				// Thread딜레이
		
		private int BackSize=5760;			// 배경세로길이
		private int viewSize_W, viewSize_H;	// 뷰 가로, 세로 길이

		
		// 메인스레드의 생성자
		public ImgThread (SurfaceHolder _Holder, Context _Context)
		{
			mSurfaceHolder= _Holder;
			mRes= _Context.getResources();
			
			bBackground= BitmapFactory.decodeResource(mRes, R.drawable.background);
		}
		

		
		public void run()
		{
			while(mRun)
			{
				Canvas canvas=null;
				
				try
				{
					canvas= mSurfaceHolder.lockCanvas();
					synchronized (mSurfaceHolder)
					{
						doDrawBackGround(canvas);
						doDrawText(canvas);
						doDrawPlayer(canvas);
						doMove();
						
						sleep(delTime);
						++Frame;
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
			//c.drawARGB(0xc8, 0xf0, 0xa9, 0xff);
			
			c.drawBitmap(bBackground, new Rect(0, BackSize-480, 320,BackSize), new Rect(0,0,320,480), null);
			if (BackSize>480)
				BackSize-=3;
		}
		
		void doDrawText(Canvas c)
		{
			Paint p=new Paint();
			
			p.setTextSize(12);
			p.setAntiAlias(true);
			p.setColor(Color.argb(0xff, 255, 0, 255));

			c.drawText("Frame= " + Float.toString(Frame), 5, 15, p);
			c.drawText("X= "+Float.toString(mPlayer.getX()) + "  Y= "+Float.toString(mPlayer.getY()), 5, 30, p);
			c.drawText("State= " + Float.toString(mPlayer.State), 5, 45, p);
			
			c.drawText("X= " + Float.toString(Math.round(sf.getSensorValue()[0]))
						+"Y= "+ Float.toString(Math.round(sf.getSensorValue()[1]))
						+"Z= "+ Float.toString(Math.round(sf.getSensorValue()[2]))
						, 5, 60, p);
//			c.drawText("X= " + Float.toString(sf.getSensorChangedValue().x)
//						+"Y= "+ Float.toString(sf.getSensorChangedValue().y)
//					, 5, 75, p);
//			c.drawText("X= " + Float.toString(sf.getSensorFixedValue().x)
//					+"Y= "+ Float.toString(sf.getSensorFixedValue().y)
//				, 5, 90, p);
		}
		
		void doDrawPlayer(Canvas c)
		{			
			c.save();
			
			if(bImg_Refreshed)
			{
				Base_char= mRes.getDrawable(mPlayer.Img_id);
				bImg_Refreshed= false;
			}
			
			Base_char.setBounds(mPlayer.getPlayerForRect());
			Base_char.draw(c);
			
			c.restore();
		}
		
		public void doMove()
		{
			//mPlayer.MoveAway();
			mPlayer.SensorMove(sf.compressFloat2Point(sf.getSensorValue()));
			//mPlayer.JumpAlways();
		}
		
		
		
		/** 스레드 동작 설정
		* @param _Run : 동작 설정 boolean값*/
		public void setRunning(boolean _Run)
		{
			mRun=_Run;
		}
		
		
		public void setImg_Refresh()
		{
			bImg_Refreshed= true;
		}
	}
		


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//this.thread.mPlayer.setState(keyCode);
		Log.d("Key", Float.toString(keyCode));
		
		return super.onKeyDown(keyCode, event);
	}
	


	
	
// ===================== 이 밑부터 SurfaceHolder.CallBack ===================== //
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

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
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
	}


}
