package com.raimsoft.matan.stage;

import android.content.Context;
import android.graphics.Canvas;
import android.view.KeyEvent;

public class OptionStage extends BaseStage
{

	public OptionStage(Context managerContext)
	{

	}

	@Override
	public int GetStageID()
	{
		return StageManager.STAGE_OPTION;
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

	@Override
	public boolean KeyDown(int keyCode, KeyEvent event)
	{
		return false;
	}

}
