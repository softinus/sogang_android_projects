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

public class StageClearActivity extends Activity  implements OnClickListener
{
	private boolean already_Next= false;

	Animation animSlideD;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.stage_clear);

        final View target = findViewById(R.id.scene_stageclear);

        animSlideD = new TranslateAnimation( 0.0f,0.0f ,-400.0f,0.0f );
        animSlideD.setDuration(700);

        animSlideD.setInterpolator(AnimationUtils.loadInterpolator(this,
                android.R.anim.decelerate_interpolator));

        target.startAnimation( animSlideD );

		super.onCreate(savedInstanceState);


	}

	@Override
	protected void onStart()
	{
		findViewById(R.id.scene_stageclear).setOnClickListener(this);

		super.onStart();
	}

	@Override
	public void onClick(View v)
	{
		GameActivity.s_GameAct.view.gameThread.SoundStop();

		++ScenarioActivity.nCurrStage;

		if ( ScenarioActivity.nCurrStage == 4 )
			ScenarioActivity.nCurrStage= 1;

		this.GotoScenario();

		finish();
	}

	private void GotoScenario()
	{
		if(!already_Next)
		{
			Intent intent=new Intent(StageClearActivity.this, ScenarioActivity.class);
	        startActivity(intent);
	        already_Next=true;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return true; // 키 입력 막음
	}

}
