package com.raimsoft.stage;

import com.raimsoft.activity.R;

import android.graphics.Canvas;
import android.util.Log;

public class StageManager {
	final int STAGE_1=	1;
	final int STAGE_2=	2;
	final int SUCCEss= 	99;
	
	public Stage mStage;
	public int currStage;
	
	/**
	 * 생성자
	 */
	public StageManager()
	{
		mStage= new Stage();
	}
	
	public void StageChange (int _StageNum)
	{
		currStage= _StageNum;	// 현재 스테이지값을 조정
		this.InitStage();
		switch (_StageNum)
		{
		case STAGE_1:
			
			break;
		case STAGE_2:
			mStage.nBackgroundID= R.drawable.background_2;
			mStage.stageSetup();
			break;
		}
	}
	
	public void InitStage() // 스테이지 초기화
	{
		mStage.clearTranspercy= 0;			// 스테이지 막 없앰
		
		mStage.BackSize= 1920;				// 배경 초기화		
		
		mStage.treadleMgr.TreadleCreate();	// 발판 초기화
		mStage.bTreadle_ImgRefreshed= true; // 
		
		mStage.gameScore= 0;				// 점수 초기화
		mStage.mPlayer.ResetPlayerPos();	// 플레이어 위치 초기화
		
		mStage.bGameClear= false;			//
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
