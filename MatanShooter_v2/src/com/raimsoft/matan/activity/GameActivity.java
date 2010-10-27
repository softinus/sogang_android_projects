package com.raimsoft.matan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.raimsoft.matan.core.GameView;
import com.raimsoft.matan.stage.BaseStage;
import com.raimsoft.matan.util.FrameManager;
import com.raimsoft.matan.util.SoundManager;


public class GameActivity extends Activity
{
	GameView view;
	static int nSelStage;
	static boolean bGameStarted= false;
	static GameActivity s_GameAct;

	private SoundManager sm;


	public void PopUpResult()
	{
		sm.play(0);

		Intent intent= new Intent(GameActivity.this, StageClearActivity.class);
		startActivity(intent);
		FrameManager.bPause= true;

		MenuActivity.bStageOver= true;
	}

	public void PopUpMenu()
	{
		Intent intent= new Intent(GameActivity.this, MenuActivity.class);
		startActivity(intent);
		FrameManager.bPause= true;

		MenuActivity.bStageOver= false;
	}

	public void PopUpGameOver()
	{
		sm.play(1);

		Intent intent= new Intent(GameActivity.this, StageOverActivity.class);
		startActivity(intent);
		FrameManager.bPause= true;

		MenuActivity.bStageOver= true;
	}



	public GameActivity ReturnGameActivity()
	{
		return this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BaseStage.s_GameAct= this;

		view = new GameView(this, nSelStage);
		this.bGameStarted= true;

		s_GameAct= this;

		sm= new SoundManager(this);
		sm.create();
		sm.load(0, R.raw.sfx_game_clear);
		sm.load(1, R.raw.sfx_game_over);

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