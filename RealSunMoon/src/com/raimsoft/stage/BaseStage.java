package com.raimsoft.stage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.raimsoft.game.Monster;
import com.raimsoft.game.Player;
import com.raimsoft.game.Rope;
import com.raimsoft.game.TreadleManager;
import com.raimsoft.sensor.SensorFactory;
import com.raimsoft.view.GameView;

public abstract class BaseStage
{
	public GameView view;
	public Resources mRes;
	public Context mContext;
	
	public Player mPlayer;				// 플레이어 객체
	public TreadleManager treadleMgr;	// 발판 객체
	public Monster mMonster;			// 몬스터 객체
	public Rope mRope;
	
	public boolean bGameClear=false;	// 게임 클리어 여부
	boolean bPlayer_ImgRefreshed=true;	// 이미지 새로고침(플레이어)
	boolean bTreadle_ImgRefreshed=true;	// 이미지 새로고침(발판)
	boolean bMonster_ImgRefreshed=true;	// 이미지 새로고침(몬스터)
	boolean bRope_ImgRefreshed=true;	// 이미지 새로고침(로프)
	
	Bitmap bBackground;			// 배경
	Drawable dGameClear;
	
	Paint pDebug=new Paint();	// 페인트
	Paint pScore=new Paint();
	
	public int BackSize=1920;			// 배경세로길이
	public int cnt_Step=0;				// 발판 밟은 수 
	public int gameScore=0;
	
	SensorFactory sf= SensorFactory.getSensorFactory();
	
	public BaseStage()
	{
		pDebug.setTextSize(12);
		pDebug.setAntiAlias(true);
		pDebug.setColor(Color.argb(0xff, 255, 0, 255));
		
		pScore.setTextSize(24);
		pScore.setAntiAlias(true);
		pScore.setColor(Color.argb(0xff, 255, 0, 255));
		
//		mContext= view.thread.getGameContext();
//		mRes= mContext.getResources();
	}
	
	abstract int GetStageID();
	abstract void stageSetup();
	abstract void stageUpdate();
	abstract void stageDraw(Canvas canvas);
	
	void Update()
	{}
	
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
	


}
