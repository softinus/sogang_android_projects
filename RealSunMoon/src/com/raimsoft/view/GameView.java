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
	}
	


	class ImgThread extends Thread
	{
		private GameView view;
		
		private SurfaceHolder mSurfaceHolder;
		private boolean mRun=true;
		
		private Resources mRes;
		//private Bitmap Player_Bitmap;
		private Bitmap bBackground;
		private Drawable Base_Left, Base_Right;

		private Player mPlayer;
		private int Frame, fps, curTime;
		private int delTime=3, BackSize=5760;

		
		public ImgThread (SurfaceHolder _Holder, Context _Context)
		{
			mSurfaceHolder= _Holder;
			mRes= _Context.getResources();
			
			mPlayer=new Player(view, 150,430, 45,50, R.drawable.base_char_left);
			
			Base_Left= mRes.getDrawable(R.drawable.base_char_left);
			Base_Right= mRes.getDrawable(R.drawable.base_char_right);
			
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
		}
		
		void doDrawPlayer(Canvas c)
		{
			//Player_Bitmap= BitmapFactory.decodeResource(mRes, mPlayer.Img_id);
			//Rect rPlayer=new Rect(mPlayer.getX(), mPlayer.getY(), mPlayer.getX()+mPlayer.getWidth(), mPlayer.getY()+mPlayer.getHeight());
			//c.drawBitmap(BitmapFactory.decodeResource(mRes, mPlayer.Img_id), mPlayer.getStateImgRect(), mPlayer.getPlayerForRect(), null);
			//c.drawBitmap(mPlayer_Bitmap, mPlayer.getX(), mPlayer.getY(), null);
			
			c.save();
			
			if (mPlayer.State==KeyEvent.KEYCODE_DPAD_LEFT)
			{
				Base_Left.setBounds(mPlayer.getPlayerForRect());
				Base_Left.draw(c);
			}
			
			if (mPlayer.State==KeyEvent.KEYCODE_DPAD_RIGHT)
			{
				Base_Right.setBounds(mPlayer.getPlayerForRect());
				Base_Right.draw(c);
			}
			c.restore();
		}
		
		public void doMove()
		{
			mPlayer.MoveAway();
			//mPlayer.JumpAlways();
		}
		
		
		
		/** 스레드 동작 설정
		* @param _Run : 동작 설정 boolean값*/
		public void setRunning(boolean _Run)
		{
			mRun=_Run;
		}
	}
		


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		this.thread.mPlayer.setState(keyCode);
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
