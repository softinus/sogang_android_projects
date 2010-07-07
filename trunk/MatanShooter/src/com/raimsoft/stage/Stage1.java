package com.raimsoft.stage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.raimsoft.activity.R;
import com.raimsoft.object.Bullet;
import com.raimsoft.util.FPoint;
import com.raimsoft.util.SpriteBitmap;

public class Stage1 extends BaseStage
{
	public boolean bDrag=false;
	public boolean bConnect1= false;
	private Resources mRes;

	private FPoint pStart, pEnd;
	private FPoint pStart2, pEnd2;
	private Paint PAINTLine, PAINText;

	SpriteBitmap SpriteTrap, SpriteTrap2;
	Bullet mBullet[]=new Bullet[8];

	public Stage1(Context managerContext)
	{
		mRes= managerContext.getResources();
		SpriteTrap= new SpriteBitmap(R.drawable.trap1_sprite, mRes
				, 50, 50, 5, 10);
		mBullet[0]= new Bullet(0,     0, R.drawable.dummy, 70, 70);
		mBullet[1]= new Bullet(365,   0, R.drawable.dummy, 70, 70);
		mBullet[2]= new Bullet(730,   0, R.drawable.dummy, 70, 70);
		mBullet[3]= new Bullet(0,   205, R.drawable.dummy, 70, 70);
		mBullet[4]= new Bullet(730, 205, R.drawable.dummy, 70, 70);
		mBullet[5]= new Bullet(0,  	410, R.drawable.dummy, 70, 70);
		mBullet[6]= new Bullet(365, 410, R.drawable.dummy, 70, 70);
		mBullet[7]= new Bullet(730, 410, R.drawable.dummy, 70, 70);


		for (int i=0; i<8; i++)
		{
			mBullet[i].DRAWimage= mRes.getDrawable(R.drawable.dummy);
			mBullet[i].DRAWimage.setBounds(mBullet[i].getObjectForRect());
		}


		pStart= new FPoint();
		pEnd= new FPoint();

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
		canvas.drawColor(Color.BLACK);

		SpriteTrap.Animate(canvas, 100, 100);

		for (int i=0; i<8; i++)
		{
			mBullet[i].DRAWimage.draw(canvas);
		}


		if (bDrag)
		{
			canvas.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y,PAINTLine);
			canvas.drawLine(pStart2.x, pStart2.y, pEnd2.x, pEnd2.y,PAINTLine);
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

			this.bDrag= true;


			for(int i=0; i<8; i++)
			{
				if (mBullet[i].getObjectForRect().contains((int)touchX, (int)touchY))
				{
					pStart.setPoint( mBullet[i].getObjectMiddleSpot() );
					bConnect1= true;
					return;
				}
			}
			pStart.x= touchX;
			pStart.y= touchY;


			break;
		case MotionEvent.ACTION_MOVE:

//			for(int i=0; i<8; i++)
//			{
//				if (mBullet[i].getObjectForRect().contains((int)touchX, (int)touchY))
//				{
//					if (bConnect1)
//					{
//						pEnd.setPoint( mBullet[i].getObjectMiddleSpot());
//						pStart2.setPoint( mBullet[i].getObjectMiddleSpot() );
//						return;
//					}
//
//				}
//			}

			pEnd.x= touchX;
			pEnd.y= touchY;

			break;
		case MotionEvent.ACTION_UP:

			bDrag= false;
			bConnect1= false;
			break;
		}
	}

	@Override
	public void KeyDown(int keyCode, KeyEvent event)
	{

	}

}
