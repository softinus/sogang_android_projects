package com.raimsoft.stage;

import com.raimsoft.activity.R;

import android.graphics.Canvas;
import android.util.Log;

public class StageManager {
	final int STAGE_1=	1;
	final int STAGE_2=	2;
	final int STAGE_3=	3;
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
			mStage.treadleMgr.treadleImgID[0]= R.drawable.cloud2_1;
			mStage.treadleMgr.treadleImgID[1]= R.drawable.cloud2_2;
			mStage.treadleMgr.treadleImgID[2]= R.drawable.cloud2_3;
			mStage.treadleMgr.treadleImgID[3]= R.drawable.cloud2_4;
			mStage.treadleMgr.treadleImgID[4]= R.drawable.cloud3_4;
			mStage.strInfo=	"Stage 2";
			mStage.stageSetup();
			break;
		case STAGE_3:	// 준비되지 않음
			//mStage.nBackgroundID= R.drawable.background_3;
			mStage.treadleMgr.treadleImgID[0]= R.drawable.cloud3_1;
			mStage.treadleMgr.treadleImgID[1]= R.drawable.cloud3_2;
			mStage.treadleMgr.treadleImgID[2]= R.drawable.cloud3_3;
			mStage.treadleMgr.treadleImgID[3]= R.drawable.cloud3_4;
			//mStage.treadleMgr.treadleImgID[4]= R.drawable.cloud4_4;
			mStage.stageSetup();
			break;
		}
	}

	public void InitStage() // 스테이지 초기화
	{
		mStage.clearTranspercy= 0;			// 스테이지 막 없앰

		mStage.BackSize= 1920;				// 배경 초기화
		mStage.mItemList.LastItemPos= 1920;	//

		mStage.treadleMgr.TreadleCreate();	// 발판 초기화
		mStage.bTreadle_ImgRefreshed= true; // 아이템 시작위치

		//mStage.gameScore= 0;				// 점수 초기화

		mStage.mPlayer.ResetPlayerPos();	// 플레이어 위치 초기화

		mStage.bGameClear= 		false;		//
		mStage.mPlayer.bStep=	false;		// 처음 발판 밟지 않았음
		mStage.view.thread.setMoveing(true);// 움직임
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
