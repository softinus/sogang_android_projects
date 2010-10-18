package com.raimsoft.matan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;

import com.raimsoft.matan.core.GameThread;
import com.raimsoft.matan.util.FrameManager;

public class ResultActivity extends Activity implements OnClickListener
{
	static GameActivity mGameAct;
	static boolean bStageOver;
	static String strTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

		setContentView(R.layout.gamemenu);
		this.setTitle(strTitle);
	}


	@Override
	protected void onStart()
	{
		findViewById(R.id.btn_gamemenu_continue).setOnClickListener(this);
		findViewById(R.id.btn_gamemenu_exit).setOnClickListener(this);

		super.onStart();
	}


	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_gamemenu_continue:

			if (bStageOver)
			{
				GameThread.GotoCurrStage();
				FrameManager.bPause= false;
				finish();
			}else
			{
				FrameManager.bPause= false;
				finish();
			}

			break;

		case R.id.btn_gamemenu_exit:
			mGameAct.finish();
			Intent intent= new Intent(ResultActivity.this, MainActivity.class);
	        startActivity(intent);
			this.finish();

			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (bStageOver)
		{
			if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_BACK)
			{
				FrameManager.bPause= false;
				GameThread.GotoCurrStage();
				finish();
			}
		}else
		{
			if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_BACK)
			{
				FrameManager.bPause= false;
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}



}
