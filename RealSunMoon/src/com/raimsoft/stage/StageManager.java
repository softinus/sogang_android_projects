package com.raimsoft.stage;

import android.graphics.Canvas;
import android.util.Log;

public class StageManager {
	final int STAGE_1=	1;
	final int STAGE_2=	2;
	final int SUCCESS=	99;
	
	private BaseStage mStage;
	public int currStage;
	
	/**
	 * 생성자
	 */
	public StageManager()
	{
		mStage= new Stage1();
	}
	
	void StageChange (int _StageNum)
	{
		currStage= _StageNum;	// 현재 스테이지값을 조정
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
	public Stage1 GetStage1()
	{
		Stage1 mStage1 = new Stage1();
		return (mStage1.getStage1());		
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
