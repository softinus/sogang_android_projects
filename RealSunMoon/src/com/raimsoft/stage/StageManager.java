package com.raimsoft.stage;

import android.graphics.Canvas;
import android.util.Log;

public class StageManager {
	final int STAGE_1=	1;
	final int STAGE_2=	2;
	final int SUCCESS=	99;
	
	private BaseStage mStage;
	
	/**
	 * 생성자
	 */
	public StageManager()
	{
		mStage= new Stage1();
	}
	
	void StageChange (int _StageNum)
	{
		switch (_StageNum)
		{
		case STAGE_1:
			mStage= new Stage1();
			break;
		case STAGE_2:
			mStage= new Stage2();
			break;
		case SUCCESS:
			mStage= new Success();
			break;
		}
	}
	
	public BaseStage GetStage()
	{	
		if(mStage!=null)
		{
			return mStage;
		}
		else
		{
			Log.e("StageManager","GetStage - mStage haven't Object");
			return null;
		}
	}

	public void AllSetUp()
	{
		mStage.stageSetup();
	}
		  
	public void AllUpdate()
	{
		mStage.stageUpdate();
	}
		  
	public void AllDraw(Canvas canvas)
	{
		mStage.stageDraw(canvas);
	}
}
