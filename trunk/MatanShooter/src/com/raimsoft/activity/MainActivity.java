package com.raimsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener
{
	ImageView IMG_main_bg, IMG_main_press;
	Button BTN_main_start, BTN_main_bonus, BTN_main_option, BTN_main_exit;

	Button BTN_inter_shop, BTN_inter_opening, BTN_inter_save, BTN_inter_next;

	Button BTN_store_buy, BTN_store_exit;

	private boolean already_Next=false;
	private boolean bPressed= false;

	private void NextActivity()
	{
		if(!already_Next)
		{
			Intent intent=new Intent(MainActivity.this, GameActivity.class);
	        startActivity(intent);
	        already_Next=true;
	        finish();
		}
	}

	private void GotoInterMission()
	{
		this.setContentView(R.layout.intermission);

		BTN_inter_shop= (Button) findViewById(R.id.btn_inter_shop);
		BTN_inter_shop.setOnClickListener(this);
	}

	private void GotoStore()
	{
		this.setContentView(R.layout.store);

		BTN_store_buy= (Button) findViewById(R.id.btn_store_buy);
		BTN_store_buy.setOnClickListener(this);

		BTN_store_exit= (Button) findViewById(R.id.btn_store_exit);
		BTN_store_exit.setOnClickListener(this);
	}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        IMG_main_bg= (ImageView) findViewById(R.id.imageview_main_bg);
        IMG_main_press= (ImageView) findViewById(R.id.imageview_presstouch);

        BTN_main_start= (Button) findViewById(R.id.btn_main_story);
        BTN_main_start.setOnClickListener(this);

        BTN_main_bonus= (Button) findViewById(R.id.btn_main_bonus);
        BTN_main_bonus.setOnClickListener(this);

        BTN_main_option= (Button) findViewById(R.id.btn_main_option);
        BTN_main_option.setOnClickListener(this);

        BTN_main_exit= (Button) findViewById(R.id.btn_main_exit);
        BTN_main_exit.setOnClickListener(this);
    }

	@Override
	public void onClick(View v)
	{
		if (!bPressed) return;

		switch (v.getId())
		{
		case R.id.btn_main_story:
			GotoInterMission();
			break;
		case R.id.btn_main_bonus:
			break;
		case R.id.btn_main_option:
			break;
		case R.id.btn_main_exit:
			finish();
			break;

		case R.id.btn_inter_shop:
			this.GotoStore();
			break;


		case R.id.btn_store_exit:
			this.GotoInterMission();
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (!bPressed)
		{
			IMG_main_bg.setImageResource(R.drawable.ui_mainbackground_fade_01);
			IMG_main_press.setVisibility(View.INVISIBLE);
			BTN_main_start.setVisibility(View.VISIBLE);
			BTN_main_bonus.setVisibility(View.VISIBLE);
			BTN_main_option.setVisibility(View.VISIBLE);
			BTN_main_exit.setVisibility(View.VISIBLE);
			bPressed= true;
		}


		return super.onTouchEvent(event);
	}


}