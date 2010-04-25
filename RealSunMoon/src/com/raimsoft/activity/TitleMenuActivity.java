package com.raimsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class TitleMenuActivity extends Activity implements OnClickListener {
	
	private boolean already_Next;
	MediaPlayer mMedia_BGM;
	
	private void Next()
	{
		if(!already_Next)
		{
			Intent intent=new Intent(TitleMenuActivity.this, GameActivity.class);
	        startActivity(intent);
	        already_Next=true;
	        finish();
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.titlemenu);
		
		super.onCreate(savedInstanceState);
		
		findViewById(R.id.btn_start).setOnClickListener(this);
		findViewById(R.id.btn_how).setOnClickListener(this);
		findViewById(R.id.btn_option).setOnClickListener(this);
		findViewById(R.id.exit).setOnClickListener(this);
		
		mMedia_BGM = MediaPlayer.create(this, R.raw.game_bgm);
		mMedia_BGM.setLooping(true);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		mMedia_BGM.start();
		
		super.onStart();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btn_start)
		{
			Next();
		}else if(v.getId()==R.id.btn_how)
		{
			Intent intent=new Intent(TitleMenuActivity.this, HowtoplayActivity.class);
	        startActivity(intent);
		}else if(v.getId()==R.id.btn_option)
		{
			
		}
	}
}
