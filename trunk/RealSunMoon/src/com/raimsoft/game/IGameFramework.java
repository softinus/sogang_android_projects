package com.raimsoft.game;

import android.graphics.Canvas;

public abstract interface IGameFramework {
	abstract void Render(Canvas canvas);
	abstract void StageChagned();
	abstract void FrameMove();
	abstract void LifeCheck();
	abstract void InitStage();
}
