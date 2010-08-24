package com.raimsoft.matan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.raimsoft.matan.core.GameView;


public class GameActivity extends Activity
{
	GameView view;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		view = new GameView(this);
		view.setFocusable(true);
		setContentView(view);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return view.onKeyDown(keyCode, event);
	}

	@Override
	protected void onRestart()
	{
		view.setFocusable(true);
		super.onRestart();
	}

	@Override
	protected void onResume()
	{
		view.setFocusable(true);
		super.onResume();
	}

}