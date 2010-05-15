package com.raimsoft.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.raimsoft.activity.GameActivity;
import com.raimsoft.activity.R;
import com.raimsoft.object.Monster;
import com.raimsoft.object.Player;
import com.raimsoft.object.Rope;
import com.raimsoft.object.TreadleManager;
import com.raimsoft.sensor.SensorFactory;
import com.raimsoft.view.GameView;

public abstract class RenderObject implements IGameFramework {

	private boolean bPlayer_ImgRefreshed=true;	// 이미지 새로고침(플레이어)
	private boolean bTreadle_ImgRefreshed=true;	// 이미지 새로고침(발판)
	private boolean bMonster_ImgRefreshed=true;	// 이미지 새로고침(몬스터)
	private boolean bRope_ImgRefreshed=true;	// 이미지 새로고침(로프)

	public Player mPlayer;				// 플레이어 객체
	public TreadleManager treadleMgr;	// 발판 객체
	public Monster mMonster;			// 몬스터 객체
	public Rope mRope;
	
	private boolean bGameClear=false;	// 게임 클리어 여부
	private int clearTranspercy=0;
	private Paint pDebug=new Paint();	// 페인트
	private Paint pScore=new Paint();
	
	private Bitmap bBackground;			// 배경
	
	private GameView view;
	
	public int BackSize=1920;			// 배경세로길이
	public int gameScore=0;
	
	public Resources mRes;
	Context gameContext;
	
	SensorFactory sf= SensorFactory.getSensorFactory();
	
	public RenderObject (Resources _Res, Context _context) // 생성자
	{
		mRes= _Res;
		gameContext= (GameActivity) _context;
	}
	


	@Override
	public void InitStage()
	{
		mPlayer= new Player(view, 150,430, 45,50, R.drawable.nui_jump_left);
		treadleMgr= new TreadleManager(view);
		mMonster= new Monster(view, -1, -1 ,50,45, R.drawable.bird_fly_1);
		mRope= new Rope(view, 140,-2, 17,168, R.drawable.new_rope);

		bBackground= BitmapFactory.decodeResource(mRes, R.drawable.background_1);
		
		pDebug.setTextSize(12);
		pDebug.setAntiAlias(true);
		pDebug.setColor(Color.argb(0xff, 255, 0, 255));
		
		pScore.setTextSize(24);
		pScore.setAntiAlias(true);
		pScore.setColor(Color.argb(0xff, 255, 0, 255));
	}
	
	
	
	
	@Override
	public void Render(Canvas canvas) {
		

		doDrawBackGround(canvas);
		doDrawObject(canvas);
		//doDrawText(canvas);
		doDrawScore(canvas);
		
	}

	@Override
	public void StageChagned() {
	
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
		if(bRope_ImgRefreshed)	// 로프 이미지 변경시
		{
			mRope.Img_Drawable= mRes.getDrawable(mRope.Img_id);
			bRope_ImgRefreshed=false;
		}
		
		if(bPlayer_ImgRefreshed) // 캐릭터 이미지 변경시
		{
			//Log.v("verbose", "Call Player_ImgRefreshed");
			mPlayer.Img_Drawable= mRes.getDrawable(mPlayer.Img_id);
			bPlayer_ImgRefreshed= false;
		}
		
		if(bTreadle_ImgRefreshed) // 구름 이미지 변경시
		{
			for (int i=0; i<treadleMgr.getCount(); i++)
			{
				
				Log.v("Draw Treadle", i + "th treadle Loading");
				treadleMgr.treadle[i].Img_Drawable= mRes.getDrawable(treadleMgr.treadle[i].Img_id);	
			}
			bTreadle_ImgRefreshed=false;
		}
		
		if(bMonster_ImgRefreshed) // 몬스터 이미지 변경시
		{
			mMonster.Img_Drawable= mRes.getDrawable(mMonster.Img_id);
			bMonster_ImgRefreshed=false;
		}
		
		
		/**
		 * 여기서부터 그리기
		 */
		
		for (int i=0; i<treadleMgr.getCount(); i++) // 화면상 구름 그림
		{
			//Log.v("Draw Treadle", i + "th treadle Drawing");
			if (IsNotClipped(mPlayer.getY(), treadleMgr.treadle[i].getY())) {
				treadleMgr.treadle[i].Img_Drawable.setBounds(treadleMgr.treadle[i].getObjectForRect());
				treadleMgr.treadle[i].Img_Drawable.draw(c);				
			}
		}
		
		mPlayer.Img_Drawable.setBounds(mPlayer.getObjectForRect());
		mPlayer.Img_Drawable.draw(c); //캐릭터 그림
		
		if (IsNotClipped(mPlayer.getY(), mMonster.getY())) // 화면상 몬스터만 그림
		{
			mMonster.Img_Drawable.setBounds(mMonster.getObjectForRect());
			mMonster.Img_Drawable.draw(c);
		}
		
		if (bGameClear)	//클리어시에 로프 그림
		{
			mRope.Img_Drawable.setBounds(mRope.getObjectForRect());
			mRope.Img_Drawable.draw(c);
		}
		
		
		
//		if (!mRope.bRopeDown)	// 로프가 다 내려오면 게임클리어 화면 보여줌
//		{
//			dGameClear= mRes.getDrawable(R.drawable.game_clear);
//			dGameClear.setBounds(20,200, 261+20,60+200);
//			dGameClear.draw(c);
//		}
		
		if (bGameClear)	// 게임 클리어시 캐릭터 위로 로프가 내려옴
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
				if (clearTranspercy<255) // FadeOff 
				{
					clearTranspercy+=5;
				}
			}
		}
		
		
		
	}
	
	public void doMove()
	{
		if (mPlayer.bCrushed)	// 몬스터와 충돌되면
		{
			mPlayer.CrushFall(); // 아래로 떨어진다. (행동불능)
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
	
	
	
	public void LifeCheck()
	{
		if (!mPlayer.isLive())
		{
			((GameActivity) gameContext).NextGameOverActivity();
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



}
