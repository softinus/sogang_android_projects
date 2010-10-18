package com.raimsoft.matan.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

public class ScenarioActivity extends Activity
{
	private Typeface TYPEfont;
	private TextView txtScript;

	private int nScriptCount= 1;  // 대사 카운트
	private int nScriptNum= 15; // 대사 갯수
	private String[] strScript= new String[nScriptNum];

	private boolean already_Next= false;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.scenario);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart()
	{
		TYPEfont= Typeface.createFromAsset(this.getAssets(), "fonts/font.ttf");
		txtScript= (TextView) findViewById(R.id.txt_scenario_script);
		txtScript.setTypeface(TYPEfont);
		txtScript.setTextSize(32.0f);

		strScript[0]= "Weber: This place is not the city";
		strScript[1]= "Already, I feel thick smell of zombie..";

		strScript[2]= "Keane: Ha ha ha! Okay. Okay. This feels good..";
		strScript[3]= "Everything seems to be what I want. Zombie, atmosphere, smell and so on. He he he.";
		strScript[4]= "Zombieee~ Where are you~?";

		strScript[5]= "Weber: Keane. Do not get excited. ";
		strScript[6]= "If this smell…That would be a lot of zombies.";
		strScript[7]= "Keane! Watch your back ";

		strScript[8]= "Bang!! ";

		strScript[9]= "Keane: Oh, Thanks.";
		strScript[10]= "Disgust Baby zombie. He he.";
		strScript[11]= "I’ll start shoot movies!";
		strScript[12]= "I stood in the middle of the intersection to attract them. And I'll even shoot film.";
		strScript[13]= "I want to shoot a movie soon. Superstar in movie!";

		strScript[14]= "Weber: OK. Now let's get started. ";

		txtScript.setText( strScript[0] );

		super.onStart();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (! (event.getAction()==MotionEvent.ACTION_DOWN) )
			return super.onTouchEvent(event);

		++nScriptCount;

		if(nScriptCount == nScriptNum)
		{
			this.GotoStage();
			return super.onTouchEvent(event);
		}

		txtScript.setText( strScript[nScriptCount] );


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
