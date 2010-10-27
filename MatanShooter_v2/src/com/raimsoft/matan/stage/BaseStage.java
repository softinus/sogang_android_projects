package com.raimsoft.matan.stage;

import android.graphics.Canvas;
import android.view.KeyEvent;

import com.raimsoft.matan.activity.GameActivity;

public abstract class BaseStage
{
	public static int NextStageID= 0;
	public static GameActivity s_GameAct;
	public abstract int GetStageID();
	public abstract boolean StageUpdate();
	public abstract void StageRender(Canvas canvas);
	public abstract void Touch(int actionID, float x, float y);
	public abstract boolean KeyDown(int keyCode, KeyEvent event);

	public abstract void SoundStop();
}