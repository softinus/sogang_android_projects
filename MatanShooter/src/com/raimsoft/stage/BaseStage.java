package com.raimsoft.stage;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class BaseStage
{
	public abstract int GetStageID();
	public abstract int StageUpdate(float Delay);
	public abstract void StageRender(Canvas canvas);
	public abstract void Touch(int actionID, float x, float y);
}