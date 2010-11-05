package com.raimsoft.matan.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpActivity extends Activity implements OnClickListener
{
	private ImageView imgHelp;
	private TextView  txtHelp;
	private Typeface  TYPEfont;
	private Button	  btnBack;

	private boolean already_Next= false;

	private int		  nImgCount= 0;
	private final int IMG_MAX= 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.howtoplay);

		TYPEfont= Typeface.createFromAsset(this.getAssets(), "fonts/font.ttf");

		imgHelp= (ImageView) findViewById(R.id.img_help);
		txtHelp= (TextView)  findViewById(R.id.txt_help_detail);

		btnBack= (Button) findViewById(R.id.btn_help_next);
		btnBack.setOnClickListener(this);

		txtHelp.setTypeface(TYPEfont);
		txtHelp.setTextSize(35.0f);
		txtHelp.setText("Tip: More connect, More damage!");

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (! (event.getAction() == MotionEvent.ACTION_DOWN)) return true;

		++nImgCount;

		switch (nImgCount)
		{
		case 0:
			imgHelp.setImageResource(R.drawable.ui_help_screen_01);
			txtHelp.setText("Tip: More connect more damage!");
			break;
		case 1:
			imgHelp.setImageResource(R.drawable.ui_help_screen_02);
			txtHelp.setText("Tip: One Button, One way");
			break;
		}

		if (nImgCount == IMG_MAX)
			nImgCount= -1;

		return super.onTouchEvent(event);
	}

	@Override
	public void onClick(View v)
	{
		if(!already_Next)
		{
			Intent intent=new Intent(HelpActivity.this, MainActivity.class);
	        startActivity(intent);
	        already_Next=true;
		}
	}

}
