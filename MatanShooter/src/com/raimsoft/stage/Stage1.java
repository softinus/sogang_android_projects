package com.raimsoft.stage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.raimsoft.activity.R;
import com.raimsoft.object.Bullet;
import com.raimsoft.object.BulletConnection;
import com.raimsoft.object.Fog;
import com.raimsoft.util.FPoint;
import com.raimsoft.util.SpriteBitmap;

public class Stage1 extends BaseStage
{

	// ************** 선언부 시작 ************** //
	private Resources mRes;

	private Paint PAINTLine, PAINText;

	private Bitmap BITMAPbackground;
	private SpriteBitmap SPRITETrap;
	private SpriteBitmap SPRITEpartner, SPRITEpartner2;
	private Fog mFog;

	BulletConnection mConnection;
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

		mFog= new Fog(0,0, R.drawable.eff_fog2, 600,360);

		BITMAPbackground= BitmapFactory.decodeResource(mRes, R.drawable.back2);

		mConnection= new BulletConnection();


		for (int i=0; i<8; i++)
		{
			mBullet[i].DRAWimage= mRes.getDrawable(mBullet[i].IDimage);
			mBullet[i].DRAWimage.setBounds(mBullet[i].getObjectForRect());
		}
		mFog.DRAWimage= mRes.getDrawable(mFog.IDimage);



		PAINTLine= new Paint();
		PAINTLine.setARGB(0xff, 255, 0, 255);
		PAINTLine.setStrokeWidth(6.0f);
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

		mFog.DRAWimage.setBounds(mFog.getObjectForRect());
		mFog.DRAWimage.draw(canvas);

		for (int i=0; i<8; i++)
		{
			mBullet[i].DRAWimage.draw(canvas);
		}

		if (mConnection.bDrag)
		{
			for (int i=0; i<8; i++)
			{
				if (mConnection.pConnect[0].ConvertToPoint().equals(mBullet[i].getObjectMiddleSpot()))
				{ // 0번째 포인트가 블렛의 가운데 포인트에 있으면
					if (!mConnection.pConnect[1].equal(0, 0))
						canvas.drawLine(mConnection.pConnect[0].x, mConnection.pConnect[0].y
									  , mConnection.pConnect[1].x, mConnection.pConnect[1].y, PAINTLine);
					Log.d("Stage1::LineDebug", "0st point : "+mConnection.pConnect[0].FPointtoString());
					Log.d("Stage1::LineDebug", "1st point : "+mConnection.pConnect[1].FPointtoString());
				}
			}

			for (int i=1; i<mConnection.ConnectionNum; i++)
			{
				canvas.drawLine(mConnection.pConnect[i].x, mConnection.pConnect[i].y
							, mConnection.pConnect[i+1].x, mConnection.pConnect[i+1].y, PAINTLine);
				Log.d("Stage1::LineDebug", Float.toString(i)+"th point : "+mConnection.pConnect[i].FPointtoString());
				Log.d("Stage1::LineDebug", Float.toString(i+1)+"th point : "+mConnection.pConnect[i+1].FPointtoString());
			}
		}
	}

	@Override
	public boolean StageUpdate()
	{
		mFog.MoveTest();


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
		Log.d("Stage1::Line", Float.toString(mConnection.ConnectionNum));


		switch(actionID)
		{
		case MotionEvent.ACTION_DOWN:

			mConnection.bDrag= true;

			break;
		case MotionEvent.ACTION_MOVE:

			if (!mConnection.bDrag) return;

			for(int i=0; i<8; i++)
			{
				if (mBullet[i].getObjectForRect().contains((int)touchX, (int)touchY))
				{
					if (mBullet[i].bClosed) return; // 이미 닫혀있으면 추가안함

					mConnection.pConnect[mConnection.ConnectionNum].setPoint(mBullet[i].getObjectMiddleSpot());
					mConnection.AddConnectionPoint();
					mConnection.pConnect[mConnection.ConnectionNum+1].setPoint(mBullet[i].getObjectMiddleSpot());
					mBullet[i].bClosed= true;
					return;
				}
			}

			if (mConnection.ConnectionNum!=0)
			{ // 기존 0번째 포인트가 있을 경우
				mConnection.pConnect[mConnection.ConnectionNum].setFPoint(touchX, touchY);
			}

			break;
		case MotionEvent.ACTION_UP:

			for (int i=0; i<mConnection.pConnect.length; i++)
			{
				mConnection.pConnect[i]= new FPoint();
			}
			for (int i=0; i<8; i++)
			{
				mBullet[i].bClosed= false;
			}
			mConnection.ConnectionNum= 0;


			mConnection.bDrag= false;
			break;
		}
	}

	@Override
	public boolean KeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			return true;
		}
		return true;
	}

}
