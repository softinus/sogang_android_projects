package com.raimsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.raimsoft.sound.SoundManager;

public class GameOverActivity extends Activity {

//	private boolean already_Next=false;
//	
//	SoundManager sm=new SoundManager(this);
//	
//	public void NextActivity()
//	{
//		if(!already_Next)
//		{
//			Intent intent=new Intent (GameOverActivity.this, GameActivity.class);
//			startActivity(intent);
//			this.already_Next=true;
//			this.finish();
//		}		
//	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		setContentView (R.layout.gameover);
//		already_Next=false;
//		
//		sm.create();
//		sm.load(0, R.raw.gameover);
//		sm.play(0);
//		
		super.onCreate(savedInstanceState);
	}


}
