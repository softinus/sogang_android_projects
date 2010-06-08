package com.raimsoft.stage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.raimsoft.activity.R;
import com.raimsoft.view.GameView;

public class BossStage
{
	public GameView view;
	public Resources mRes;
	public Context mContext;

	private Drawable dBackground;


	void stageSetup() // 설정
	{
		dBackground= mRes.getDrawable(R.drawable.boss_background);
	}

	void stageUpdate()
	{


	}
	void stageDraw(Canvas canvas)
	{
		dBackground.setBounds(0,0, view.getWidth(), view.getHeight());
		dBackground.draw(canvas);
	}

}
