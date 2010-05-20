package com.raimsoft.stage;

import android.graphics.Canvas;

public class Stage2 extends BaseStage {

	final int STAGE_ID=2;
	
	private static Stage2 stage2= new Stage2();
	public Stage2 getStage1()
	{
		return stage2;
	}
	
	public Stage2()
	{
		//view.thread.setupInit();
		stageSetup();
	}
	
	@Override
	int GetStageID()
	{
		return 	STAGE_ID;
	}

	@Override
	void stageDraw(Canvas canvas)
	{
		
	}

	@Override
	void stageSetup()
	{
		
	}

	@Override
	void stageUpdate()
	{
		
	}

}
