package com.raimsoft.matan.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.raimsoft.matan.core.GameThread;
import com.raimsoft.matan.info.ScenarioInfo;
import com.raimsoft.matan.util.FrameManager;

public class ScenarioActivity extends Activity implements OnClickListener
{
	ScenarioInfo kScenInfo= new ScenarioInfo();

	private boolean already_Next= false;

	private TextView txtScript;
	public Typeface TYPEfont;

	public static int nCurrStage= 1;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.scenario);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

		findViewById(R.id.btn_scenatio_skip).setOnClickListener(this);

		txtScript=  (TextView) findViewById(R.id.txt_scenario_script);

		TYPEfont= Typeface.createFromAsset(this.getAssets(), "fonts/font.ttf");
		txtScript.setTypeface(TYPEfont);
		txtScript.setTextSize(32.0f);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart()
	{
		kScenInfo.Init( nCurrStage );

		switch(nCurrStage)
		{
		case 1:
			txtScript.setText( kScenInfo.strScript1[0] );
			break;
		case 2:
			txtScript.setText( kScenInfo.strScript2[0] );
			break;
		case 3:
			txtScript.setText( kScenInfo.strScript3[0] );
			break;
		default:
			txtScript.setText( "XXX "+ nCurrStage + " XXX");
		}


		super.onStart();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{


		if (! (event.getAction()==MotionEvent.ACTION_DOWN) )
			return super.onTouchEvent(event);

		++kScenInfo.nScriptCount;

		if(kScenInfo.nScriptCount >= kScenInfo.nScriptNum)
		{
			if ( GameActivity.bGameStarted ) // 게임이 시작되었으면
			{
				GameThread.GotoCurrStage();
				FrameManager.bPause= false;
				this.finish();

			}else{
				this.GotoStage();
				this.finish();
			}

			return super.onTouchEvent(event);
		}

		switch(nCurrStage)
		{
		case 1:
			txtScript.setText( kScenInfo.strScript1[kScenInfo.nScriptCount] );
			break;
		case 2:
			txtScript.setText( kScenInfo.strScript2[kScenInfo.nScriptCount] );
			break;
		case 3:
			txtScript.setText( kScenInfo.strScript3[kScenInfo.nScriptCount] );
			break;
		}


		return super.onTouchEvent(event);
	}

	private void GotoStage()
	{
		if(!already_Next)
		{
			Intent intent=new Intent(ScenarioActivity.this, GameActivity.class);
	        startActivity(intent);
	        already_Next=true;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return true; // 키 입력 막음
	}

	@Override
	public void onClick(View v)
	{
		this.GotoStage();
	}

}
