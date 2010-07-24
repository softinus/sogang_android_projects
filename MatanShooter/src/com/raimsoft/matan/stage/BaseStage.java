package com.raimsoft.matan.stage;

import android.graphics.Canvas;
import android.view.KeyEvent;

public abstract class BaseStage
{
	public int NextStageID= 0;
	public abstract int GetStageID();
	public abstract boolean StageUpdate();
	public abstract void StageRender(Canvas canvas);
	public abstract void Touch(int actionID, float x, float y);
	public abstract boolean KeyDown(int keyCode, KeyEvent event);
}