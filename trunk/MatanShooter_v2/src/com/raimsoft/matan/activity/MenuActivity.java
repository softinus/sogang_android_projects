package com.raimsoft.matan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.raimsoft.matan.core.GameThread;
import com.raimsoft.matan.util.FrameManager;

public class MenuActivity extends Activity implements OnClickListener
{
	static boolean bStageOver;
	private boolean already_Next= false;

	Animation animSlideR;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

		setContentView(R.layout.gamemenu);

        final View target = findViewById(R.id.view_gamemenu);

        animSlideR = new TranslateAnimation( -500.0f,0.0f ,0.0f,0.0f );
        animSlideR.setDuration(700);
        animSlideR.setStartOffset(0);

        animSlideR.setInterpolator(AnimationUtils.loadInterpolator(this,
                android.R.anim.decelerate_interpolator));

        target.startAnimation( animSlideR );
	}


	@Override
	protected void onStart()
	{
		GameActivity.s_GameAct.view.gameThread.SoundStop();

		findViewById(R.id.btn_gamemenu_continue).setOnClickListener(this);
		findViewById(R.id.btn_gamemenu_exit).setOnClickListener(this);

		FrameManager.bPause= true;

		super.onStart();
	}


	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_gamemenu_continue:

			FrameManager.bPause= false;
			finish();

			break;

		case R.id.btn_gamemenu_exit:

			GameActivity.s_GameAct.bGameStarted= false;

			Intent intent= new Intent(MenuActivity.this, MainActivity.class);
	        startActivity(intent);

	        GameActivity.s_GameAct.finish();
			this.finish();

			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_BACK)
		{
			FrameManager.bPause= false;
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void GotoScenario()
	{
		if(!already_Next)
		{
			Intent intent=new Intent(MenuActivity.this, ScenarioActivity.class);
	        startActivity(intent);
	        already_Next=true;
		}
	}





}
