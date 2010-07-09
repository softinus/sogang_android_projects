package com.raimsoft.stage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;

import com.raimsoft.activity.R;
import com.raimsoft.object.GameButton;
import com.raimsoft.util.FrameManager;

public class MainTitleStage extends BaseStage
{
	private boolean bNext= false, bTap= false;
	private Resources mRes;
	private Bitmap BITMAPbackground1, BITMAPbackground2;
	private FrameManager mFrameMgr= FrameManager.getInstance(); 	// 프레임관리 (싱글톤)
	private GameButton BTNstory, BTNbonus, BTNoption, BTNexit;
	private Bitmap BITMAPpresstouch;


	public MainTitleStage(Context managerContext)
	{

		mRes= managerContext.getResources();

		BITMAPbackground1= BitmapFactory.decodeResource(mRes, R.drawable.ui_mainbackground_01);
		BITMAPbackground2= BitmapFactory.decodeResource(mRes, R.drawable.ui_mainbackground_fade_01);
		//SPRITEcoin= new SpriteBitmap(R.drawable.ui_main_button_point_sprite_02,mRes,70,70,9,5);

		BITMAPpresstouch= BitmapFactory.decodeResource(mRes, R.drawable.ui_mainbutton_presstouch);
		BTNstory= new GameButton(125,221, 247,36, R.drawable.ui_buttonmain_story_1,R.drawable.ui_buttonmain_story_2);
		BTNbonus= new GameButton(155,281, 247,36, R.drawable.ui_buttonmain_bonus_1,R.drawable.ui_buttonmain_bonus_2);
		BTNoption= new GameButton(185,341,247,36, R.drawable.ui_buttonmain_option_1,R.drawable.ui_buttonmain_option_2);
		BTNexit= new GameButton(215,401, 247,36, R.drawable.ui_buttonmain_exit_1,R.drawable.ui_buttonmain_exit_2);

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
			BTNstory.ImageRefreshAndBound(mRes);
			BTNbonus.ImageRefreshAndBound(mRes);
			BTNoption.ImageRefreshAndBound(mRes);
			BTNexit.ImageRefreshAndBound(mRes);

			canvas.drawBitmap(BITMAPbackground2, 0, 0, null);
			BTNstory.DRAWimage.draw(canvas);
			BTNbonus.DRAWimage.draw(canvas);
			BTNoption.DRAWimage.draw(canvas);
			BTNexit.DRAWimage.draw(canvas);
			return;
		}
		canvas.drawBitmap(BITMAPbackground1, 0, 0, null);
		canvas.drawBitmap(BITMAPpresstouch, 300, 400, null);
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
		if (!mFrameMgr.TouchToDealy(10)) return; // 터치 딜레이 (100ms)


		if (BTNstory.ButtonPress(actionID, (int)x, (int)y))
			bNext= true;

		BTNbonus.ButtonPress(actionID, (int)x, (int)y);
		BTNoption.ButtonPress(actionID, (int)x, (int)y);
		BTNexit.ButtonPress(actionID, (int)x, (int)y);

		if (!bTap)
			bTap=true;
	}

	@Override
	public boolean KeyDown(int keyCode, KeyEvent event)
	{
		return false;
	}

}
