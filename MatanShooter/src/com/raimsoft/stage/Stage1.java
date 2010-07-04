package com.raimsoft.stage;

import com.raimsoft.util.FPoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

public class Stage1 extends BaseStage
{

	public boolean bDrag=false;
	public boolean bTest=false;

	private FPoint pStart, pEnd;
	private Paint PAINTLine, PAINText;

	public Stage1(Context managerContext)
	{
		pStart= new FPoint();
		pEnd= new FPoint();

		PAINTLine= new Paint();
		PAINTLine.setARGB(0xff, 255, 0, 255);
		PAINTLine.setStrokeWidth(12.0f);
		PAINTLine.setAntiAlias(true);

		PAINText= new Paint();
		PAINText.setARGB(0xff, 255, 0, 255);
		PAINText.setAntiAlias(true);
	}

	@Override
	public int GetStageID()
	{
		return StageManager.STAGE_1;
	}

	@Override
	public void StageRender(Canvas canvas)
	{
		if (bDrag)
		{
			canvas.drawColor(Color.BLACK);
			canvas.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y,PAINTLine);
		}
	}

	@Override
	public boolean StageUpdate(float Delay)
	{
		if (bTest)
		{
			this.NextStageID= StageManager.STAGE_SCENARIO;
			return true;
		}

		return false;
	}

	@Override
	public void Touch(int actionID, float x, float y)
	{
		switch(actionID)
		{
		case MotionEvent.ACTION_DOWN:

			this.bDrag= true;
			pStart.x= x;
			pStart.y= y;


			break;
		case MotionEvent.ACTION_MOVE:

			pEnd.x= x;
			pEnd.y= y;

			break;
		case MotionEvent.ACTION_UP:

			this.bDrag= false;
			this.bTest= true;


			break;
		}
	}

}
