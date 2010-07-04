package com.raimsoft.stage;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class StageManager
{
	final static int STAGE_MAIN		=1;
	final static int STAGE_OPTION	=2;
	final static int STAGE_SCENARIO =3;
	final static int STAGE_1		=101;
	final static int STAGE_2		=102;
	final static int STAGE_3		=103;
	final static int STAGE_4		=104;
	final static int STAGE_5		=105;
	final static int STAGE_6		=106;

	private BaseStage mStage;
	private Context ManagerContext;
	private int StageState;

	public StageManager(Context context)
	{
		Log.i("StageManager","Construct");
		ManagerContext = context;

		mStage=new Stage1(ManagerContext);
		StageState= STAGE_1;
	}

	public void ChangeStage(int _stageID)
	{
		Log.v("StageManager","ChangerStage");

		switch(_stageID)
		{
		case STAGE_OPTION:
			//mStage=new StageOption(ManagerContext);
			break;
		case STAGE_1:
			mStage=new Stage1(ManagerContext);
			break;
		case STAGE_2:
			//mStage=new Stage2(ManagerContext);
			break;

		}
	}

	/**
	 * @return
	 */
	public int GetStageState()
	{
		Log.i("StageManager","GetStageState");

		return StageState;
	}

	//--------------------------------------------------------------------------------------------------------------//
	// 화면에 출력할 모든 것을 갱신하는 함수
	//--------------------------------------------------------------------------------------------------------------//
	public void Render(Canvas canvas, float Delay)
	{
		//StageState= mStage.StageUpdate(Delay);
		mStage.StageRender(canvas);
	}

	//--------------------------------------------------------------------------------------------------------------//
	// 각 Stage의 ID를 return 하는 함수
	//--------------------------------------------------------------------------------------------------------------//
	public int GetStageID()
	{
		Log.i("StageManager","GetStageID");

		return mStage.GetStageID();
	}

//	//--------------------------------------------------------------------------------------------------------------//
//	// Touch 관련 함수
//	//--------------------------------------------------------------------------------------------------------------//
//	public void TouchUpdate(int TouchState, int Choice)
//	{
//		Log.i("StageManager","TouchUpdate");
//
//		mStage.StageTouchUpdate(TouchState, Choice);
//	}

	//--------------------------------------------------------------------------------------------------------------//
	// Touch 관련 함수
	//--------------------------------------------------------------------------------------------------------------//
		public void Touch(MotionEvent event)
		{
			Log.i("StageManager","Touch");

			mStage.Touch(event.getAction(), event.getX(), event.getY());
		}
}
