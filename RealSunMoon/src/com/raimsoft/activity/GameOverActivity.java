package com.raimsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class GameOverActivity extends Activity implements OnClickListener {

	private boolean already_Next=false;
	
//	SoundManager sm=new SoundManager(this);
	
	public void NextActivity()
	{
		if(!already_Next)
		{
			Intent intent=new Intent (GameOverActivity.this, GameActivity.class);
			startActivity(intent);
			this.already_Next=true;
			this.finish();
		}		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		setContentView (R.layout.gameover);
		already_Next=false;
		
//		sm.create();
//		sm.load(0, R.raw.gameover);
		
		findViewById(R.id.btn_playagain).setOnClickListener(this);

		
		super.onCreate(savedInstanceState);
	}

	public void onClick(View v)
	{		
		if(v.getId()==R.id.btn_playagain)
		{
			NextActivity();
		}
	}

}
