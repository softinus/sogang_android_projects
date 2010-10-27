package com.raimsoft.matan.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class StageOverActivity extends Activity
{
	private TextView txtGameOver;
	private View	 viewGameOver;
	private Typeface TYPEfont;

	private Animation animFadein;
	private final long GAMEOVER_TEXT_DURATION= 2500;

	private boolean already_Next= false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.game_over);
		TYPEfont= Typeface.createFromAsset(this.getAssets(), "fonts/font.ttf");

		txtGameOver=  (TextView) findViewById(R.id.txt_gameover);
		viewGameOver= findViewById(R.id.view_gameover);

		txtGameOver.setTypeface(TYPEfont);
		txtGameOver.setTextSize(70.0f);

		super.onCreate(savedInstanceState);
	}



	@Override
	protected void onStart()
	{
		animFadein = new AlphaAnimation( 0.0f, 1.0f );
		animFadein.setDuration(GAMEOVER_TEXT_DURATION);

		viewGameOver.setAnimation(animFadein);



		super.onStart();
	}


	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		this.GotoMain();

		return super.onTouchEvent(event);
	}

	private void GotoMain()
	{
		if(!already_Next)
		{
			if( ! animFadein.hasEnded() ) return;

	        GameActivity.s_GameAct.bGameStarted= false;

			Intent intent= new Intent(StageOverActivity.this, MainActivity.class);
	        startActivity(intent);

	        GameActivity.s_GameAct.finish();
			this.finish();

	        already_Next=true;
		}
	}



}
