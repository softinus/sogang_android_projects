package com.raimsoft.matan.stage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.KeyEvent;

public class IntermissonStage extends BaseStage {

	Paint PAINTtext1= new Paint();
	Paint PAINTtext2= new Paint();
	Paint PAINTtext3= new Paint();
	Paint PAINTtext4= new Paint();
	Paint PAINTtext5= new Paint();
	Typeface TYPEfont1, TYPEfont2, TYPEfont3, TYPEfont4, TYPEfont5;

	public IntermissonStage(Context managerContext)
	{
		TYPEfont1= Typeface.createFromAsset(managerContext.getAssets(), "fonts/font1.ttf");
		TYPEfont2= Typeface.createFromAsset(managerContext.getAssets(), "fonts/font2.ttf");
		TYPEfont3= Typeface.createFromAsset(managerContext.getAssets(), "fonts/font3.ttf");
		TYPEfont4= Typeface.createFromAsset(managerContext.getAssets(), "fonts/font4.ttf");
		TYPEfont5= Typeface.createFromAsset(managerContext.getAssets(), "fonts/font5.ttf");

		PAINTtext1.setTextSize(40);	
		PAINTtext1.setTypeface(TYPEfont1);
		PAINTtext1.setColor(Color.BLACK);
		PAINTtext1.setAntiAlias(true);

		PAINTtext2.setTextSize(40);
		PAINTtext2.setTypeface(TYPEfont2);
		PAINTtext2.setColor(Color.BLACK);
		PAINTtext2.setAntiAlias(true);

		PAINTtext3.setTextSize(40);
		PAINTtext3.setTypeface(TYPEfont3);
		PAINTtext3.setColor(Color.BLACK);
		PAINTtext3.setAntiAlias(true);

		PAINTtext4.setTextSize(40);
		PAINTtext4.setTypeface(TYPEfont4);
		PAINTtext4.setColor(Color.BLACK);
		PAINTtext4.setAntiAlias(true);

		PAINTtext5.setTextSize(40);
		PAINTtext5.setTypeface(TYPEfont5);
		PAINTtext5.setColor(Color.BLACK);
		PAINTtext5.setAntiAlias(true);
	}

	@Override
	public int GetStageID()
	{
		return StageManager.STAGE_INTER;
	}

	@Override
	public boolean KeyDown(int keyCode, KeyEvent event)
	{
		return false;
	}

	@Override
	public void StageRender(Canvas canvas)
	{
		canvas.drawColor(Color.WHITE);
		canvas.drawText("Hello! Font_Test1", 50, 50, PAINTtext1);
		canvas.drawText("Hello! Font_Test2", 50, 100, PAINTtext2);
		canvas.drawText("Hello! Font_Test3", 50, 150, PAINTtext3);
		canvas.drawText("Hello! Font_Test4", 50, 200, PAINTtext4);
		canvas.drawText("Hello! Font_Test5", 50, 250, PAINTtext5);

	}

	@Override
	public boolean StageUpdate()
	{
		return false;
	}

	@Override
	public void Touch(int actionID, float x, float y)
	{

	}

}
