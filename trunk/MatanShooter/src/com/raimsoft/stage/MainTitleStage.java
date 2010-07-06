package com.raimsoft.stage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.raimsoft.activity.R;
import com.raimsoft.util.SpriteBitmap;

public class MainTitleStage extends BaseStage
{
	private boolean bNext= false, bTap= false;
	private Resources mRes;
	private Bitmap BITMAPbackground1, BITMAPbackground2;
	private SpriteBitmap SPRITEcoin;

	public MainTitleStage(Context managerContext)
	{
		mRes= managerContext.getResources();

		BITMAPbackground1= BitmapFactory.decodeResource(mRes, R.drawable.ui_mainbackground_01);
		BITMAPbackground2= BitmapFactory.decodeResource(mRes, R.drawable.ui_mainbackground_fade_01);
		SPRITEcoin= new SpriteBitmap(R.drawable.ui_main_button_point_sprite_02,mRes,70,70,9,5);
	}

	@Override
	public int GetStageID()
	{
		return StageManager.STAGE_MAIN;
	}

	@Override
	public void StageRender(Canvas canvas)
	{
		if (bTap)
		{
			canvas.drawBitmap(BITMAPbackground2, 0, 0, null);
			SPRITEcoin.Animate(canvas, 100, 230);
			return;
		}
		canvas.drawBitmap(BITMAPbackground1, 0, 0, null);
	}

	@Override
	public boolean StageUpdate()
	{
		if(bNext)
		{
			this.NextStageID= StageManager.STAGE_1;
			return true;
		}
		return false;
	}

	@Override
	public void Touch(int actionID, float x, float y)
	{
		//bNext= true;
		if (!bTap)
			bTap=true;
	}

}
