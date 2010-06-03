package com.raimsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.raimsoft.sound.SoundManager;

public class TitleMenuActivity extends Activity implements OnClickListener {

	private boolean already_Next;
	//public MediaPlayer mMedia_BGM;
	private SoundManager sm= new SoundManager(this);

	private void Next()
	{
		if(!already_Next)
		{
			Intent intent=new Intent(TitleMenuActivity.this, PrologueActivity.class);
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

		findViewById(R.id.btn_challengemode).setOnClickListener(this);
		findViewById(R.id.btn_how).setOnClickListener(this);
		findViewById(R.id.btn_option).setOnClickListener(this);
		findViewById(R.id.exit).setOnClickListener(this);

//		mMedia_BGM = MediaPlayer.create(this, R.raw.game_bgm);
//		mMedia_BGM.setLooping(true);

		sm.create();
		sm.load(0, R.raw.button);
	}

	@Override
	protected void onStart()
	{
//		mMedia_BGM.start();

		super.onStart();
	}

	@Override
	public void onClick(View v)
	{
		sm.play(0);

		if(v.getId()==R.id.btn_challengemode)
		{
			Next();
		}else if(v.getId()==R.id.btn_how)
		{
			Intent intent=new Intent(TitleMenuActivity.this, HowtoplayActivity.class);
	        startActivity(intent);
		}
		else if(v.getId()==R.id.btn_option)
		{
			Intent intent=new Intent(TitleMenuActivity.this, OptionActivity.class);
	        startActivity(intent);
		}
	}


	@Override
	protected void onDestroy() {

//		mMedia_BGM.stop();
//		mMedia_BGM.release();
		sm.destroy();

		super.onDestroy();
	}
}
