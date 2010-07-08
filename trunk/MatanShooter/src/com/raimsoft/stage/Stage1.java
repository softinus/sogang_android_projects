package com.raimsoft.stage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.raimsoft.activity.R;
import com.raimsoft.object.Bullet;
import com.raimsoft.util.SpriteBitmap;

public class Stage1 extends BaseStage
{

	// ************** 선언부 시작 ************** //
	private Resources mRes;

	private Paint PAINTLine, PAINText;

	private Bitmap BITMAPbackground;
	SpriteBitmap SPRITETrap;
	private SpriteBitmap SPRITEpartner, SPRITEpartner2;

	//BulletConnection mConnection;
	Bullet mBullet[]=new Bullet[8];
	// ************** 선언부 종료 ************** //

	public Stage1(Context managerContext)
	{
		mRes= managerContext.getResources();
		SPRITETrap= new SpriteBitmap(R.drawable.trap1_sprite, mRes, 50, 50, 5, 10);
		SPRITEpartner= new SpriteBitmap(R.drawable.man_test, mRes, 150,150,8, 20);
		SPRITEpartner2= new SpriteBitmap(R.drawable.man_test, mRes, 150,150,8, 100);

		mBullet[0]= new Bullet(0,     0, R.drawable.bullet_close, 70, 70);
		mBullet[1]= new Bullet(365,   0, R.drawable.bullet_close, 70, 70);
		mBullet[2]= new Bullet(730,   0, R.drawable.bullet_open, 70, 70);
		mBullet[3]= new Bullet(0,   205, R.drawable.bullet_close, 70, 70);
		mBullet[4]= new Bullet(730, 205, R.drawable.bullet_open, 70, 70);
		mBullet[5]= new Bullet(0,  	410, R.drawable.bullet_close, 70, 70);
		mBullet[6]= new Bullet(365, 410, R.drawable.bullet_open, 70, 70);
		mBullet[7]= new Bullet(730, 410, R.drawable.bullet_close, 70, 70);

		BITMAPbackground= BitmapFactory.decodeResource(mRes, R.drawable.back2);

		//mConnection= new BulletConnection();

		for (int i=0; i<8; i++)
		{
			mBullet[i].DRAWimage= mRes.getDrawable(mBullet[i].IDimage);
			mBullet[i].DRAWimage.setBounds(mBullet[i].getObjectForRect());
		}


		PAINTLine= new Paint();
		PAINTLine.setARGB(0xff, 255, 0, 255);
		PAINTLine.setStrokeWidth(12.0f);
		PAINTLine.setAntiAlias(true);

		PAINText= new Paint();
		PAINText.setARGB(0xff, 255, 0, 255);
		PAINText.setAntiAlias(true);
	}

	@Override
	public int GetStageID()
	{
		return StageManager.STAGE_1;
	}

	@Override
	public void StageRender(Canvas canvas)
	{
		//canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(BITMAPbackground, 0, 0, null);

		SPRITETrap.Animate(canvas, 100, 100);
		SPRITEpartner.Animate(canvas, 325, 165);
		SPRITEpartner2.Animate(canvas, 475, 165);


		for (int i=0; i<8; i++)
		{
			mBullet[i].DRAWimage.draw(canvas);
		}
	}

	@Override
	public boolean StageUpdate()
	{
//		if (bTest) // 스테이지 넘길때는 true값
//		{
//			this.NextStageID= StageManager.STAGE_SCENARIO;
//			return true;
//		}

		return false;
	}

	@Override
	public void Touch(int actionID, float touchX, float touchY)
	{



		switch(actionID)
		{
		case MotionEvent.ACTION_DOWN:

//			mConnection.bDrag= true;
//
//			for(int i=0; i<8; i++)
//			{
//				if (mBullet[i].getObjectForRect().contains((int)touchX, (int)touchY))
//				{
//					mConnection.pStart.setPoint( mBullet[i].getObjectMiddleSpot() );
//					Log.d("Stage1",Float.toString(mBullet[i].nBulletNum));
//					return;
//				}
//			}
//			mConnection.pStart.x= touchX;
//			mConnection.pStart.y= touchY;


			break;
		case MotionEvent.ACTION_MOVE:

			for(int i=0; i<8; i++)
			{
				if (mBullet[i].getObjectForRect().contains((int)touchX, (int)touchY))
				{


				}
			}

			break;
		case MotionEvent.ACTION_UP:

			//mConnection.bDrag= false;
			break;
		}
	}

	@Override
	public void KeyDown(int keyCode, KeyEvent event)
	{

	}

}
