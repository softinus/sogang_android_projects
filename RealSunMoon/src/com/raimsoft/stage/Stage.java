package com.raimsoft.stage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.raimsoft.activity.GameActivity;
import com.raimsoft.activity.R;
import com.raimsoft.game.ItemList;
import com.raimsoft.game.Monster;
import com.raimsoft.game.Player;
import com.raimsoft.game.Rope;
import com.raimsoft.game.TreadleManager;
import com.raimsoft.sensor.SensorFactory;
import com.raimsoft.view.GameView;

public class Stage
{


	public GameView view;
	public Resources mRes;
	public Context mContext;

	SensorFactory sf= SensorFactory.getSensorFactory();

	public Player mPlayer;				// 플레이어 객체
	public TreadleManager treadleMgr;	// 발판 객체
	public Monster mMonster;			// 몬스터 객체
	public Rope mRope;					// 동아줄 객체
	public ItemList mItemList;			// 아이템 객체

	public boolean bClearStage1= false;
	int nBackgroundID= R.drawable.background_1;			// 배경 아이디값
	Bitmap bBackground;			// 배경
	Drawable dGameClear;

	Paint pDebug=new Paint();	// 페인트
	Paint pScore=new Paint();

	public int BackSize=1920;			// 배경세로길이
	public int cnt_Step=0;				// 발판 밟은 수
	public int gameScore=0;

	public boolean bGameClear=false;	// 게임 클리어 여부
	boolean bPlayer_ImgRefreshed=true;	// 이미지 새로고침(플레이어)
	boolean bTreadle_ImgRefreshed=true;	// 이미지 새로고침(발판)
	boolean bMonster_ImgRefreshed=true;	// 이미지 새로고침(몬스터)
	boolean bRope_ImgRefreshed=true;	// 이미지 새로고침(로프)
	boolean bItem_ImgRefreshed=true;	// 이미지 새로고침(아이템)


	public Stage()
	{
		//view.thread.setupInit();
		//stageSetup();

		pDebug.setTextSize(12);
		pDebug.setAntiAlias(true);
		pDebug.setColor(Color.argb(0xff, 255, 0, 255));

		pScore.setTextSize(24);
		pScore.setAntiAlias(true);
		pScore.setColor(Color.argb(0xff, 255, 0, 255));
	}
	int clearTranspercy=0;
	final int STAGE_ID=1;


	void stageDraw(Canvas canvas)
	{
		doDrawBackGround(canvas); // 배경그리기
		doDrawObject(canvas); // 오브젝트그리기
		//doDrawText(canvas); // 디버깅
		doDrawScore(canvas); // 점수그리기
	}

	/**
	 * 캐릭터와 발판들의 거리를 계산하여 화면 밖에 있는 객체들은 DRAW하지 않습니다.
	 * @param player_Y : 캐릭터의 Y값
	 * @param Object_Y : 체크할 다른 객체의 Y값
	 * @return 화면안에 있으면:true, 화면밖에 있으면:false
	 */
	boolean isInSight(int player_Y, int Object_Y)
	{
		if (Math.abs(Object_Y - player_Y) > view.getHeight())
			return false;
		return true;
	}

	void stageSetup()
	{
		bBackground= BitmapFactory.decodeResource(mRes, nBackgroundID);
	}

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
			if (mPlayer.bJump==true) //
			{
				mPlayer.CollisionTreadle(treadleMgr.treadle[i].getObjectForRectHalf(true)
						, treadleMgr.treadle[i]);
			}
		}

		for (int i=0; i<mItemList.itemList.size(); i++)
		{
			mPlayer.CollisionItem(mItemList.itemList.get(i).getObjectForRect(), mItemList.itemList.get(i), i);
		}


	this.LifeCheck(); //

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
		/* ========================= Initializing ========================= */

		if(bRope_ImgRefreshed) // 동아줄 초기화
		{
			mRope.Img_Drawable= mRes.getDrawable(mRope.Img_id);
			bRope_ImgRefreshed=false;
		}

		if(bPlayer_ImgRefreshed) // 캐릭터 초기화
		{
			//Log.v("verbose", "Call Player_ImgRefreshed");
			mPlayer.Img_Drawable= mRes.getDrawable(mPlayer.Img_id);
			bPlayer_ImgRefreshed= false;
		}

		if(bItem_ImgRefreshed) // 아이템 초기화
		{
			for (int i=0; i<mItemList.itemList.size(); i++)
			{
				mItemList.itemList.get(i).Img_Drawable= mRes.getDrawable(mItemList.itemList.get(i).Img_id);
			}
			bItem_ImgRefreshed= false;
		}

		if(bTreadle_ImgRefreshed) // 발판 초기화
		{
			for (int i=0; i<treadleMgr.getCount(); i++)
			{
				Log.v("Draw Treadle", i + "th treadle Loading");
				treadleMgr.treadle[i].Img_Drawable= mRes.getDrawable(treadleMgr.treadle[i].Img_id);
			}
			bTreadle_ImgRefreshed=false;
		}

		if(bMonster_ImgRefreshed) // 몬스터 초기화
		{
			mMonster.Img_Drawable= mRes.getDrawable(mMonster.Img_id);
			bMonster_ImgRefreshed=false;
		}


		/* ========================= Rendering ========================= */


		if (!treadleMgr.bInitializing) // 초기화 중이 아닐 때
		{
			for (int i=0; i<treadleMgr.getCount(); i++) // 발판 뿌려줌
			{
				//Log.v("Draw Treadle", i + "th treadle Drawing");
				if (isInSight(mPlayer.getY(), treadleMgr.treadle[i].getY())) {
					treadleMgr.treadle[i].Img_Drawable.setBounds(treadleMgr.treadle[i].getObjectForRect());
					treadleMgr.treadle[i].Img_Drawable.draw(c);
				}
			}
		}

		if (isInSight(mPlayer.getY(), mMonster.getY())) // 몬스터 뿌려줌 (화면 범위내의)
		{
			mMonster.Img_Drawable.setBounds(mMonster.getObjectForRect());
			mMonster.Img_Drawable.draw(c);
		}


		for (int i=0; i<mItemList.itemList.size(); i++) // 아이템 뿌려줌 (ItemList)
		{
			if (isInSight(mPlayer.getY(), mItemList.itemList.get(i).getY())) // 화면 내에 있으면
			{
				mItemList.itemList.get(i).Img_Drawable.setBounds(mItemList.itemList.get(i).getObjectForRect());
				mItemList.itemList.get(i).Img_Drawable.draw(c);
			}else{ // 화면 밖이면
				mItemList.itemList.remove(i); // 리스트에서 제외
				Log.v("ItemList", i + "th item removed.");
			}
		}




		if (bGameClear && !bClearStage1)	//클리어시에 로프 그림
		{
			mRope.Img_Drawable.setBounds(mRope.getObjectForRect());
			mRope.Img_Drawable.draw(c);
		}

		mPlayer.Img_Drawable.setBounds(mPlayer.getObjectForRect());
		mPlayer.Img_Drawable.draw(c);

		if (bGameClear) // 게임이 클리어되면
		{
			mRope.setX(mPlayer.getX()+(mPlayer.getWidth()/2));
			if(mRope.getY() != -5)
			{
				mRope.RopeDown();
				((GameActivity) mContext).stopBGM();
				((GameActivity) mContext).playSE();
			}else{
				mRope.bRopeDown=false;
			}

			if (!mRope.bRopeDown && !bClearStage1)
			{// 로프가 다 내려오고 스테이지1을 아직 클리어 안했을 때
				c.drawARGB(clearTranspercy, 0, 0, 0);
				if (clearTranspercy<255)
				{
					clearTranspercy+=5;
				}
				if (clearTranspercy==255)
				{
					bClearStage1= true;	// 스테이지1 클리어
					view.thread.mStageMgr.StageChange(2);
				}
			}
		}


	}


	public void setGameClear(boolean _Clear)
	{
		bGameClear= _Clear;
	}

	public void setPlayerImg_Refresh()
	{
		bPlayer_ImgRefreshed= true;
	}
	public void setTreadleImg_Refresh()
	{
		bTreadle_ImgRefreshed= true;
	}
	public void setItemImg_Refresh()
	{
		bItem_ImgRefreshed= true;
	}


}