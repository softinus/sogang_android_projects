package com.raimsoft.stage;

import android.graphics.Canvas;

public class Success extends BaseStage
{
	final int STAGE_ID=99;

//	Drawable dStageClear;
	
	public Success()
	{
		//view.thread.setupInit();
		stageSetup();
	}
	
	@Override
	int GetStageID() {
		return STAGE_ID;
	}
	
	@Override
	void stageDraw(Canvas canvas)
	{
//		dStageClear.setBounds(20,200, 261+20,60+200);
//		dStageClear.draw(canvas);
	}

	@Override
	void stageSetup()
	{
//		dStageClear= mRes.getDrawable(R.drawable.game_clear);
		
	}

	@Override
	void stageUpdate()
	{
		
	}
	

}
