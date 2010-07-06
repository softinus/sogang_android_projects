package com.raimsoft.stage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.raimsoft.activity.R;
import com.raimsoft.util.FPoint;
import com.raimsoft.util.SpriteBitmap;

public class Stage1 extends BaseStage
{
	SpriteBitmap SpriteTrap, SpriteTrap2;

	public boolean bDrag=false;
	private Resources mRes;

	private FPoint pStart, pEnd;
	private Paint PAINTLine, PAINText;

	public Stage1(Context managerContext)
	{
		mRes= managerContext.getResources();
		SpriteTrap= new SpriteBitmap(R.drawable.trap1_sprite, mRes
				, 50, 50, 5, 10);

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
		canvas.drawColor(Color.BLACK);

		SpriteTrap.Animate(canvas, 100, 100);
		SpriteTrap.Animate(canvas, 100, 150);

		if (bDrag)
		{
			canvas.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y,PAINTLine);
		}
	}

	@Override
	public boolean StageUpdate()
	{
//		if (bTest) // 스테이지 넘길때는 true값
//		{
//			this.NextStageID= StageManager.STAGE_SCENARIO;
//			return true;
//		}

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


			break;
		}
	}

}
