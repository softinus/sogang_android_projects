package com.raimsoft.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class StartActivity extends Activity {
	private final long LogoTime=3000;
	private final long NextActivity=4500;
	private boolean already_Next=false;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
         
        setContentView(R.layout.main);
        Log.d("StartActivity", "onCreate()");    
    }
    
	private void Next()
	{
		if(!already_Next)
		{
			Intent intent=new Intent(StartActivity.this, TitleMenuActivity.class);
	        startActivity(intent);
	        already_Next=true;
	        finish();
		}
	}
    
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();

		final ImageView imageID=(ImageView) findViewById(R.id.logo);
		
		final Animation fadein = new AlphaAnimation( 0.0f, 1.0f );
        fadein.setDuration(LogoTime);
        
        final Animation fadeout = new AlphaAnimation( 1.0f, 0.0f );
        fadeout.setDuration(LogoTime);
        
        imageID.startAnimation(fadein);
        
        
        TimerTask Next = new TimerTask() {
            public void run() {
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
}