package com.raimsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.raimsoft.stage.Stage;

public class PopupActivity extends Activity implements OnClickListener {

	Intent intent;
	public boolean bContinue;
	private GameActivity gameContext= new GameActivity();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.popup);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {

		findViewById(R.id.btn_popup_continue).setOnClickListener(this);
		findViewById(R.id.btn_popup_oncemore).setOnClickListener(this);
		findViewById(R.id.btn_popup_gameclose).setOnClickListener(this);

		super.onStart();
	}


	@Override
	protected void onDestroy() {



		super.onDestroy();
	}

	@Override
	public void onClick(View v)
	{

		if(v.getId()==R.id.btn_popup_continue)
		{
			finish();

		}
		else if(v.getId()==R.id.btn_popup_oncemore)
		{
			intent=new Intent(PopupActivity.this, GameActivity.class);
	        startActivity(intent);
	        finish();
		}
		else if(v.getId()==R.id.btn_popup_gameclose)
		{
			intent=new Intent(PopupActivity.this, TitleMenuActivity.class);
	        startActivity(intent);

	        gameContext.finish();
	        finish();
		}


	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_BACK)
			finish();

		return super.onKeyDown(keyCode, event);
	}

}