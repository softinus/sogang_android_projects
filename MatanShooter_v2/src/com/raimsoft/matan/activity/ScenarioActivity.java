package com.raimsoft.matan.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.raimsoft.matan.core.GameThread;
import com.raimsoft.matan.info.ScenarioInfo;
import com.raimsoft.matan.util.FrameManager;

public class ScenarioActivity extends Activity
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
			if ( nCurrStage == 1 )
			{
				this.GotoStage();
				this.finish();
			}else
			{
				GameThread.GotoCurrStage();
				FrameManager.bPause= false;
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

}