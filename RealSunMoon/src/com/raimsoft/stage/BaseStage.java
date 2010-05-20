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
	

	

	
	SensorFactory sf= SensorFactory.getSensorFactory();
	
	public BaseStage()
	{

		
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
	



}
