package com.raimsoft.stage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

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
	public boolean StageUpdate(float Delay)
	{
		return false;
	}

	@Override
	public void Touch(int actionID, float x, float y)
	{
	}

}
