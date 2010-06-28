package com.raimsoft.game;

import android.graphics.Canvas;

public class StageManager {
	
	final int STAGE_GAMEOVER= 0;
	final int STAGE_1= 1;
	final int STAGE_2= 2;
	final int STAGE_3= 3;
	final int STAGE_WIN= 99;
	
	int currStageNum=-1;
	
	
	IGameFramework gameWork;
	
	public void Render(Canvas canvas)
	{
		gameWork.Render(canvas);
	}
	
	public void FrameMove()
	{
		gameWork.FrameMove();
	}
	
	public void LikeCheck()
	{
		gameWork.LifeCheck();
	}
	
	public void NextStage()
	{
		ChangeStage(2);
	}
	
	public void ChangeStage(int _StageNum)
	{
		switch (_StageNum)
		{
		case STAGE_1:
			currStageNum=1;
			gameWork.StageChagned();
			
			break;
		case STAGE_2:
			currStageNum=2;
			gameWork.StageChagned();
			
			break;
		}
	}
	
	
	
}
