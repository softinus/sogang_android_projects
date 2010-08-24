package com.raimsoft.matan.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnClickListener
{
	ImageView IMG_main_bg, IMG_main_press;

	Gallery GAL_inter_mapsel;
	ImageView IMG_inter_light;

	Button BTN_main_start, BTN_main_bonus, BTN_main_option, BTN_main_exit;
	Button BTN_inter_main, BTN_inter_next;

	private boolean already_Next=false;
	private boolean bPressed= false;
	private boolean bInterMission= false;

	private Timer timer = new Timer();
	private final long nFlickDelay= 100;
	private int rndNum= (int) (Math.random()*10);
    private Integer[] mImageIds = {
            R.drawable.ui_map_light_01,
            R.drawable.ui_map_light_02,
            R.drawable.ui_map_light_03,
            R.drawable.ui_map_light_04,
            R.drawable.ui_map_light_05,
            R.drawable.ui_map_light_06,
            R.drawable.ui_map_light_07,
            R.drawable.ui_map_light_08,
            R.drawable.ui_map_light_09
    };

    TimerTask TIMERLight = new TimerTask()
    {
        public void run()
        {
        	rndNum= (int) (Math.random()*10);
        	IMG_inter_light.setImageResource(mImageIds[rndNum]);
        }
    };


	/**
	 * GameActivity로 넘어감
	 */
	private void GotoGame()
	{
		if(!already_Next)
		{
			Intent intent=new Intent(MainActivity.this, GameActivity.class);
	        startActivity(intent);
	        already_Next=true;
	        finish();
		}
	}

	/**
	 * 인터미션으로 넘어감
	 */
	private void GotoInterMission()
	{
		this.setContentView(R.layout.intermission);

		BTN_inter_main= (Button) findViewById(R.id.btn_inter_next);
		BTN_inter_main.setOnClickListener(this);

		BTN_inter_next= (Button) findViewById(R.id.btn_inter_next);
		BTN_inter_next.setOnClickListener(this);

		IMG_inter_light= (ImageView) findViewById(R.id.img_backlight);

		GAL_inter_mapsel= (Gallery) findViewById(R.id.gallery_mapsel);
		GAL_inter_mapsel.setAdapter(new ImageAdapter(this));

		GAL_inter_mapsel.setOnItemClickListener(new OnItemClickListener() {
	         public void onItemClick(AdapterView parent, View v, int position, long id) {
	             Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	         }
	     });

		timer.schedule(TIMERLight, nFlickDelay, nFlickDelay);

		bInterMission= true;
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

		case R.id.btn_inter_next:
			this.GotoGame();
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