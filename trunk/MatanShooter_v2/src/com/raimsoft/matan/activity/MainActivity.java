package com.raimsoft.matan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

	Button BTN_main_start, BTN_main_bonus, BTN_main_option, BTN_main_help, BTN_main_exit;
	Button BTN_inter_main, BTN_inter_next;

	private boolean already_Next=false;
	private boolean bPressed= false;

	private int nMapSel= -1;
	private static MainActivity Instance;


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

	private final long nFlickDelay= 200;
    private InterMissionThread mThread= new InterMissionThread();
    private Handler handler= new Handler()
    {
    	public void handleMessage(Message msg)
    	{
    		rndNum= (int) (Math.random()*9);
    		IMG_inter_light.setImageResource(mImageIds[rndNum]);
    	}
    };

    class InterMissionThread extends Thread
    {
    	public void run()
    	{
    		while(true)
    		{
    			Message msg= handler.obtainMessage();
    			handler.sendMessage(msg);
    			try {
					sleep(nFlickDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}
    }









	private void WrongMessage()
	{
		Toast.makeText(MainActivity.this, "잘못된 맵을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
		GAL_inter_mapsel.setSelection(2, true); // 중간으로 이동 (마지막 애니메이션시 1,3으로 이동됨.
		return;
	}

	private void StartMessage(int _nStage)
	{
		Toast.makeText(MainActivity.this, "한번 더 터치하면 "+ _nStage + "번 스테이지로 이동합니다.", Toast.LENGTH_SHORT).show();
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

        BTN_main_help= (Button) findViewById(R.id.btn_main_help);
        BTN_main_help.setOnClickListener(this);

        BTN_main_exit= (Button) findViewById(R.id.btn_main_exit);
        BTN_main_exit.setOnClickListener(this);

        Instance= this;
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
			Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
			break;

		case R.id.btn_main_option:
			break;

		case R.id.btn_main_help:
			this.GotoHowtoplay();
			break;

		case R.id.btn_main_exit:
			finish();
			break;


		case R.id.btn_inter_next:

			int nStage= GAL_inter_mapsel.getSelectedItemPosition();

	       	if ( nStage==0 || nStage==4 )
	       	{
	       		WrongMessage();
	    		return;
	       	}
			this.GotoGame(nStage);

			break;

		case R.id.btn_inter_main:
			this.GotoMain();
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
			//BTN_main_option.setVisibility(View.VISIBLE);
			BTN_main_help.setVisibility(View.VISIBLE);
			BTN_main_exit.setVisibility(View.VISIBLE);
			bPressed= true;
		}


		return super.onTouchEvent(event);
	}


	/**
	 * Help
	 */
	private void GotoHowtoplay()
	{
		if(!already_Next)
		{
			Intent intent=new Intent(MainActivity.this, HelpActivity.class);
	        startActivity(intent);

	        already_Next=true;
		}
	}

	/**
	 * GameActivity로 넘어감
	 */
	private void GotoGame(int _nStage)
	{
		if(!already_Next)
		{
			GameActivity.nSelStage= _nStage;
			ScenarioActivity.nCurrStage= _nStage;

			Intent intent=new Intent(MainActivity.this, ScenarioActivity.class);
	        startActivity(intent);

	        already_Next=true;
		}
	}

	/**
	 * 인터미션으로 넘어감
	 */
	private void GotoInterMission()
	{
		this.setContentView(R.layout.intermission);

		BTN_inter_main= (Button) findViewById(R.id.btn_inter_main);
		BTN_inter_main.setVisibility(View.INVISIBLE);
		//BTN_inter_main.setOnClickListener(this);

		BTN_inter_next= (Button) findViewById(R.id.btn_inter_next);
		BTN_inter_next.setOnClickListener(this);

		IMG_inter_light= (ImageView) findViewById(R.id.img_backlight);

		GAL_inter_mapsel= (Gallery) findViewById(R.id.gallery_mapsel);
		GAL_inter_mapsel.setAdapter(new ImageAdapter(this));

		GAL_inter_mapsel.setSelection(1); // 첫번째 화면으로 이동

		GAL_inter_mapsel.setOnItemClickListener(new OnItemClickListener()
		{
	         @SuppressWarnings("unchecked")
			public void onItemClick(AdapterView parent, View v, int position, long id)
	         {

	        	 if ( position == 0 || position == 4 )
	        	 {
	        		 WrongMessage();
	        		 return;
	        	 }

	        	 if (nMapSel == -1)
	        	 {
	        		 nMapSel= position; // 첫번째 선택
	        		 StartMessage(nMapSel);
	        		 return;
	        	 }

	        	 if (nMapSel == position)
	        	 {
		        	 GotoGame(nMapSel);
	        	 }else{
	        		 nMapSel= position; // 첫번째 선택
	        		 StartMessage(nMapSel);
	        	 }

	         }
	     });
		mThread.start();
	}

	/**
	 * InterMission -> Main (BACK)
	 */
	private void GotoMain()
	{
		Log.d("MainActivity","GotoMain()");
		bPressed= false;

        setContentView(R.layout.mainmenu);
	}

	public static MainActivity GetInstance()
	{
		return Instance;
	}

}