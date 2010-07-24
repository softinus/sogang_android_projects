package com.raimsoft.matan.activity;

import android.app.Activity;
import android.os.Bundle;

import com.raimsoft.matan.core.GameView;


public class GameActivity extends Activity
{
	GameView view;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		view = new GameView(this);
		setContentView(view);
	}
}
