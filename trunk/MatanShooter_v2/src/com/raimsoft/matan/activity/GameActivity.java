package com.raimsoft.matan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.raimsoft.matan.core.GameView;
import com.raimsoft.matan.stage.BaseStage;
import com.raimsoft.matan.util.FrameManager;


public class GameActivity extends Activity
{
	GameView view;
	static int nSelStage;
	static boolean bGameStarted= false;
	static GameActivity s_GameAct;


	public void PopUpResult()
	{
		Intent intent= new Intent(GameActivity.this, StageClearActivity.class);
		startActivity(intent);
		FrameManager.bPause= true;

		MenuActivity.bStageOver= true;
		//view.gameThread.SoundStop();
	}

	public void PopUpMenu()
	{
		Intent intent= new Intent(GameActivity.this, MenuActivity.class);
		startActivity(intent);
		FrameManager.bPause= true;

		MenuActivity.bStageOver= false;
	}

	public GameActivity ReturnGameActivity()
	{
		return this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BaseStage.m_sGame= this;

		view = new GameView(this, nSelStage);
		this.bGameStarted= true;

		s_GameAct= this;

		setContentView(view);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_MENU:
			this.PopUpMenu();
			break;
		}
		return view.onKeyDown(keyCode, event);
	}

//	private void Focusable()
//	{
////		view.setFocusable(true);
////		view.setFocusableInTouchMode(true);
////		view.setClickable(true);
//	}

	public void Exit()
	{
		finish();
	}

}