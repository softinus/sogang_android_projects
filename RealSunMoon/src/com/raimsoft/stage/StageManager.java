package com.raimsoft.stage;

import android.graphics.Canvas;

import com.raimsoft.activity.R;

public class StageManager {
	final int STAGE_1=	1;
	final int STAGE_2=	2;
	final int STAGE_3=	3;
	final int STAGE_4= 	4;
	final int STAGE_BOSS= 5;
	final int STAGE_SUCCESS= 	99;

	public Stage mStage;
	public BossStage mBossStage;
	public int currStage= 1;

	/**
	 * 생성자
	 */
	public StageManager()
	{
		mStage= new Stage();
		mBossStage= new BossStage();
	}

	public void StageChange (int _StageNum)
	{
		currStage= _StageNum;	// 현재 스테이지값을 조정
		this.InitStage();		// 스테이지 초기화
		switch (_StageNum)
		{
		case STAGE_1:
			mStage.strInfo=	"Stage 1";
			currStage= 1;


			break;
		case STAGE_2:
			mStage.nBackgroundID= R.drawable.background_2;
			mStage.treadleMgr.treadleImgID[0]= R.drawable.cloud2_1;
			mStage.treadleMgr.treadleImgID[1]= R.drawable.cloud2_2;
			mStage.treadleMgr.treadleImgID[2]= R.drawable.cloud2_3;
			mStage.treadleMgr.treadleImgID[3]= R.drawable.cloud2_4;
			mStage.treadleMgr.treadleImgID[4]= R.drawable.cloud3_4;


			mStage.stageSetup();

			break;
		case STAGE_3:	// 준비되지 않음
			mStage.nBackgroundID= R.drawable.background_3;
			mStage.treadleMgr.treadleImgID[0]= R.drawable.cloud3_1;
			mStage.treadleMgr.treadleImgID[1]= R.drawable.cloud3_2;
			mStage.treadleMgr.treadleImgID[2]= R.drawable.cloud3_3;
			mStage.treadleMgr.treadleImgID[3]= R.drawable.cloud3_4;
			mStage.treadleMgr.treadleImgID[4]= R.drawable.star4_3;


			mStage.stageSetup();
			break;

		case STAGE_4:	// 준비되지 않음
			mStage.nBackgroundID= R.drawable.background_4;
			mStage.treadleMgr.treadleImgID[0]= R.drawable.star4_1;
			mStage.treadleMgr.treadleImgID[1]= R.drawable.star4_2;
			mStage.treadleMgr.treadleImgID[2]= R.drawable.star4_3;
			mStage.treadleMgr.treadleImgID[3]= R.drawable.star4_3;
			//mStage.treadleMgr.treadleImgID[4]= R.drawable.cloud4_4;


			mStage.stageSetup();
			break;

		case STAGE_BOSS:

			break;

		case STAGE_SUCCESS:
			break;
		}
	}

	public void InitStage() // 스테이지 초기화
	{
		mStage.clearTranspercy= 0;			// 스테이지 막 없앰

		mStage.BackSize= 1920;				// 배경 위치 초기화
		mStage.mItemList.LastItemPos= 1920;	// 아이템 위치 초기화
		mStage.mFakeList.LastFakePos= 1920;	// 아이템 위치 초기화

		mStage.bTreadle_ImgRefreshed= true; // 아이템 시작위치
		mStage.mPlayer.bItemGet= false;

		//mStage.gameScore= 0;				// 점수 초기화

		mStage.mPlayer.ResetPlayerPos();	// 플레이어 위치 초기화

		mStage.bGameClear= 		false;		//
		mStage.mPlayer.bStep=	false;		// 처음 발판 밟지 않았음
		mStage.view.thread.setMoveing(true);// 움직임
		mStage.setGameClear(false);

		mStage.mRope.setY(-140);			// 로프 초기화
		mStage.mRope.bRopeDown= true;		//

		mStage.strInfo=	"Stage "+currStage; // 스테이지 알림 텍스트
		mStage.STAGE_ID =currStage;			//

		mStage.treadleMgr.setPlusGAP(20);	// 스테이지마다 발판 배치를 20씩 증가 (harder)

	}

	public void StageSetUp()
	{
		switch (this.currStage)
		{
		case STAGE_1:	case STAGE_2:	case STAGE_3:	case STAGE_4:
			mStage.stageSetup();
			break;
		case STAGE_BOSS:
			mBossStage.stageSetup();
			break;
		}

	}

	public void StageUpdate()
	{
		switch (this.currStage)
		{
		case STAGE_1:	case STAGE_2:	case STAGE_3:	case STAGE_4:
			mStage.stageUpdate();
			break;
		case STAGE_BOSS:
			mBossStage.stageUpdate();
			break;
		}
	}

	public void StageDraw(Canvas canvas)
	{
		switch (this.currStage)
		{
		case STAGE_1:	case STAGE_2:	case STAGE_3:	case STAGE_4:
			mStage.stageDraw(canvas);
			break;
		case STAGE_BOSS:
			mBossStage.stageDraw(canvas);
			break;
		}
	}
}
