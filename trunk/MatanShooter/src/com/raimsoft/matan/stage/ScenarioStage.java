package com.raimsoft.matan.stage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;

public class ScenarioStage extends BaseStage
{

	public ScenarioStage(Context managerContext)
	{

	}

	@Override
	public int GetStageID()
	{
		return StageManager.STAGE_SCENARIO;
	}

	@Override
	public void StageRender(Canvas canvas)
	{
		canvas.drawColor(Color.GREEN);
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
