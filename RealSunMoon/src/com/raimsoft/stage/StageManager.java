package com.raimsoft.stage;

import android.graphics.Canvas;
import android.util.Log;

public class StageManager {
	final int STAGE_1=	1;
	final int STAGE_2=	2;
	final int SUCCESS=	99;
	
	public Stage mStage;
	public int currStage;
	
	/**
	 * 생성자
	 */
	public StageManager()
	{
		mStage= new Stage();
	}
	
//	public void StageChange (int _StageNum)
//	{
//		currStage= _StageNum;	// 현재 스테이지값을 조정
//		switch (_StageNum)
//		{
//		case STAGE_1:
//			mStage= new Stage1();
//			break;
//		case STAGE_2:
//			mStage= new Stage2();
//			break;
//		case SUCCESS:
//			mStage= new Success();
//			break;
//		}
//	}
	
	public void InitStage() // 스테이지 초기화
	{
		mStage.BackSize= 0;
		mStage.treadleMgr.TreadleCreate();
	}

	public void StageSetUp()
	{
		mStage.stageSetup();
	}
		  
	public void StageUpdate()
	{
		mStage.stageUpdate();
	}
		  
	public void StageDraw(Canvas canvas)
	{
		mStage.stageDraw(canvas);
	}
}
