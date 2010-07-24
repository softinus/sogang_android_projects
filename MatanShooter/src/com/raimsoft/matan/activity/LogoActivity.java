package com.raimsoft.matan.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class LogoActivity extends Activity
{
	private final long LogoTime=3000;
	private final long NextActivity=4500;
	private boolean already_Next=false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.logo);
        Log.d("StartActivity", "onCreate()");
    }

	private void Next()
	{
		if(!already_Next)
		{
			Intent intent=new Intent(LogoActivity.this, MainActivity.class);
	        startActivity(intent);
	        already_Next=true;
	        finish();
		}
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		final ImageView imageID=(ImageView) findViewById(R.id.logo);

		final Animation fadein = new AlphaAnimation( 0.0f, 1.0f );
        fadein.setDuration(LogoTime);

        imageID.startAnimation(fadein);


        TimerTask Next = new TimerTask()
        {
            public void run()
            {
            	Next();
            }
        };

        Timer timer = new Timer();
        timer.schedule(Next, NextActivity);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		Next();

		return super.onTouchEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		Next();

		return super.onKeyDown(keyCode, event);
	}

}
