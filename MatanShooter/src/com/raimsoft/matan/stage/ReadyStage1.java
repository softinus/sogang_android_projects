package com.raimsoft.matan.stage;

import com.raimsoft.matan.activity.GameActivity;

import android.graphics.Canvas;
import android.view.KeyEvent;

public class ReadyStage1 extends BaseStage {

	public ReadyStage1(GameActivity managerContext)
	{
	}

	@Override
	public int GetStageID()
	{
		return StageManager.STAGE_READY_1;
	}

	@Override
	public boolean KeyDown(int keyCode, KeyEvent event)
	{
		return false;
	}

	@Override
	public void StageRender(Canvas canvas)
	{


	}

	@Override
	public boolean StageUpdate()
	{
		return false;
	}

	@Override
	public void Touch(int actionID, float x, float y)
	{

	}

}
