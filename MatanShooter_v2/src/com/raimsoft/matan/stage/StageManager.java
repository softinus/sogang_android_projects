package com.raimsoft.matan.stage;

import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.raimsoft.matan.activity.GameActivity;

public class StageManager
{
	public final static int STAGE_1		   =1;
	public final static int STAGE_2		   =2;
	public final static int STAGE_3		   =3;
	private BaseStage mStage;
	private GameActivity ManagerContext;
	private boolean bKeyResult;

	public StageManager(GameActivity context)
	{
		Log.i("StageManager","Construct");
		ManagerContext = context;

		mStage=new Stage1(ManagerContext);
	}

	/**
	 * StageManager 생성자
	 * @param context
	 * @param nSelectStage : 생성할 때 시작할 스테이지 번호
	 */
	public StageManager(GameActivity context, int nSelectStage)
	{
		Log.i("StageManager","Construct");
		ManagerContext = context;

		ChangeStage(nSelectStage);
	}



	public void SoundStop()
	{
		mStage.SoundStop();
	}

	public void ChangeStage(int _stageID)
	{
		Log.v("StageManager","ChangeStage : "+Float.toString(_stageID));

		switch(_stageID)
		{
		case STAGE_1:
			mStage= new Stage1(ManagerContext);
			break;
		case STAGE_2:
			mStage= new Stage2(ManagerContext);
			break;
		case STAGE_3:
			mStage= new Stage3(ManagerContext);
			break;
		default:
			Log.e("StageManager::ChangeStage", "Wrong stage number!");
			break;
		}
	}


	/**
	 * Render : 화면을 그린다.
	 * @param canvas
	 * @param Delay
	 */
	public void Render(Canvas canvas)
	{
		//StageState= mStage.StageUpdate(Delay);
		mStage.StageRender(canvas);
	}

	/**
	 * Update : 정보를 연산한다.
	 * @param Delay
	 */
	public boolean Update()
	{
		return ( mStage.StageUpdate() );
	}

	/**
	 *
	 * @return : 현재 Stage의 ID를 리턴
	 */
	public int GetStageID()
	{

		return mStage.GetStageID();
	}

	/**
	 *
	 * @return : 다음에 넘어갈 Stage의 ID를 리턴
	 */
	public int GetNextStageID()
	{
		return mStage.NextStageID;
	}

	/**
	 * 터치시에 동작
	 * @param event
	 */
	public void Touch(MotionEvent event)
	{
		Log.i("StageManager","Touch");

		if (mStage==null)
			return;

		mStage.Touch(event.getAction(), event.getX(), event.getY());
	}

	public boolean KeyDown(int keyCode, KeyEvent event)
	{
		Log.i("StageManager","KeyDown"+Float.toString(keyCode));

		bKeyResult= mStage.KeyDown(keyCode, event);
		return bKeyResult;
	}
}
