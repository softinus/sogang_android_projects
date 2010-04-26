package com.raimsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class GameOverActivity extends Activity {

	private boolean already_Next=false;
	
	public void NextGameOverActivity()
	{
		if(!already_Next)
		{
			Intent intent=new Intent (GameOverActivity.this, TitleMenuActivity.class);
			startActivity(intent);
			this.already_Next=true;
			this.finish();
		}		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		setContentView (R.layout.gameover);
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		NextGameOverActivity();
		
		return super.onTouchEvent(event);
	}
	
	

	
}
