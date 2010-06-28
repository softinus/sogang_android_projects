package com.raimsoft.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.raimsoft.sound.SoundManager;

public class OptionActivity extends Activity implements OnClickListener
{
	SoundManager sm;
	private Resources mRes;
	private Button BtnSoundOpt;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.option);

		mRes= this.getResources();
		sm= new SoundManager(this);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart()
	{
		BtnSoundOpt= (Button) findViewById(R.id.btn_sound);
		BtnSoundOpt.setOnClickListener(this);

		if (sm.bSoundOpt)
		{
			BtnSoundOpt.setBackgroundDrawable(mRes.getDrawable(R.drawable.sound_icon));
		}else{
			BtnSoundOpt.setBackgroundDrawable(mRes.getDrawable(R.drawable.sound_icon2));
		}

		super.onStart();
	}

	@Override
	public void onClick(View v)
	{
		if (v==BtnSoundOpt)
		{
			if (sm.bSoundOpt)
			{
				BtnSoundOpt.setBackgroundDrawable(mRes.getDrawable(R.drawable.sound_icon2));
				sm.bSoundOpt= false;
			}else{
				BtnSoundOpt.setBackgroundDrawable(mRes.getDrawable(R.drawable.sound_icon));
				sm.bSoundOpt= true;
			}

		}
	}


}
