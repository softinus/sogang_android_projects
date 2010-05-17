package com.raimsoft.stage;

import com.raimsoft.activity.R;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Stage1 extends BaseStage{
	

	public Stage1()
	{
		//view.thread.setupInit();
		//stageSetup();
	}
	private int clearTranspercy=0;
	final int STAGE_ID=1;
	
	@Override
	int GetStageID()
	{
		return STAGE_ID;
	}

	@Override
	void stageDraw(Canvas canvas)
	{
		doDrawBackGround(canvas);
		doDrawObject(canvas);
		//doDrawText(canvas);
		doDrawScore(canvas);
	}

	@Override
	void stageSetup()
	{
		bBackground= BitmapFactory.decodeResource(mRes, R.drawable.background_1);
	}

	@Override
	void stageUpdate()
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
		this.LifeCheck();
		
	}

	void LifeCheck()
	{
		if (!mPlayer.isLive())
		{
			view.thread.gameOver();
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

//		c.drawText("FPS= " + Float.toString(FPS), 5, 30, pDebug);
//		c.drawText("Frame= " + Float.toString(AccFrame), 5, 15, pDebug);
//		c.drawText("X= "+Float.toString(mPlayer.getX()) 
//					+ ", Y= "+Float.toString(mPlayer.getY()),
//					mPlayer.getX(), mPlayer.getY(), pDebug);
//		c.drawText("State= " + Float.toString(mPlayer.State), 5, 45, pDebug);
//		
//		c.drawText("X= " + Float.toString(sf.getSensorValue()[0])
//					+"Y= "+ Float.toString(sf.getSensorValue()[1])
//					+"Z= "+ Float.toString(sf.getSensorValue()[2])
//					, 5, 60, pDebug);
		
		//for (int i=0; i<treadleMgr.getCount(); i++)
		//{
//		c.drawText("X= "+ Float.toString(treadleMgr.treadle[i].getX())
//					+", Y= "+ Float.toString(treadleMgr.treadle[i].getY()),
//					treadleMgr.treadle[i].getX(), treadleMgr.treadle[i].getY(), p);
		//}
	}
	
	void doDrawScore(Canvas c)
	{
		c.drawText("점수 : " + Float.toString(gameScore), 5, 20, pScore);
	}
	

	
	
	void doDrawObject(Canvas c)
	{
		if(bRope_ImgRefreshed)
		{
			mRope.Img_Drawable= mRes.getDrawable(mRope.Img_id);
			bRope_ImgRefreshed=false;
		}
		
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
		
		if (bGameClear)	//클리어시에 로프 그림
		{
			mRope.Img_Drawable.setBounds(mRope.getObjectForRect());
			mRope.Img_Drawable.draw(c);
		}
		
		mPlayer.Img_Drawable.setBounds(mPlayer.getObjectForRect());
		mPlayer.Img_Drawable.draw(c);
		
		if (bGameClear)
		{
			mRope.setX(mPlayer.getX()+(mPlayer.getWidth()/2));
			if(mRope.getY() != -5)
			{
				mRope.RopeDown();
			}else{
				mRope.bRopeDown=false;
			}
			
			if (!mRope.bRopeDown)
			{
				c.drawARGB(clearTranspercy, 0, 0, 0);
				if (clearTranspercy<255)
				{
					clearTranspercy+=5;
				}
				if (clearTranspercy==255)
				{
					view.thread.mStageMgr.StageChange(99);
				}
			}
			
		}
		

	}
	

	
}