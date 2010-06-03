package com.raimsoft.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class OptionActivity extends Activity implements OnClickListener
{

	public boolean bSound_ON= true;
	private Resources mRes;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.option);

		mRes= this.getResources();

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart()
	{
		findViewById(R.id.btn_sound).setOnClickListener(this);

		super.onStart();
	}

	@Override
	public void onClick(View v)
	{
		if (v.getId()==R.id.btn_sound)
		{
			if (bSound_ON)
			{
				v.setBackgroundDrawable(mRes.getDrawable(R.drawable.sound_icon2));
				bSound_ON= false;
			}else{
				v.setBackgroundDrawable(mRes.getDrawable(R.drawable.sound_icon));
				bSound_ON=true;
			}

		}
	}


}
