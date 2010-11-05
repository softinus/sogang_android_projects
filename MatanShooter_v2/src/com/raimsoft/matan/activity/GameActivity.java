package com.raimsoft.matan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.raimsoft.matan.core.GameView;
import com.raimsoft.matan.stage.BaseStage;
import com.raimsoft.matan.util.FrameManager;
import com.raimsoft.matan.util.SoundManager;


public class GameActivity extends Activity
{

	GameView view;
	static int nSelStage;
	static boolean bGameStarted= false;
	static GameActivity s_GameAct;

	static public SoundManager mSound; // 게임 효과음 총관리


	public void PopUpResult()
	{
		mSound.play(-10);

		Intent intent= new Intent(GameActivity.this, StageClearActivity.class);
		startActivity(intent);
		FrameManager.bPause= true;

		MenuActivity.bStageOver= true;
	}

	public void PopUpMenu()
	{
		Intent intent= new Intent(GameActivity.this, MenuActivity.class);
		startActivity(intent);
		FrameManager.bPause= true;

		MenuActivity.bStageOver= false;
	}

	public void PopUpGameOver()
	{
		mSound.play(-20);

		Intent intent= new Intent(GameActivity.this, StageOverActivity.class);
		startActivity(intent);
		FrameManager.bPause= true;

		MenuActivity.bStageOver= true;
	}



	public GameActivity ReturnGameActivity()
	{
		return this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BaseStage.s_GameAct= this;

		view = new GameView(this, nSelStage);
		this.bGameStarted= true;

		s_GameAct= this;

		mSound= new SoundManager (this);
		mSound.create();
		mSound.load(0, R.raw.sfx_drag);			 // 선 드래그1
		mSound.load(1, R.raw.sfx_drag_one_65);	 // 선 드래그2
		mSound.load(2, R.raw.sfx_touch_matan);	 // 마탄 터치음

		mSound.load(100, R.raw.sfx_shot_sting);	 // 가시 발사
		mSound.load(101, R.raw.sfx_bullet_2);	 // 일반 발사
		mSound.load(102, R.raw.sfx_shot_fire);	 // 불 발사
		mSound.load(103, R.raw.sfx_shot_bolt);	 // 전기 발사
		mSound.load(104, R.raw.sfx_ice_1);		 // 얼음 발사

		mSound.load(200, R.raw.sfx_hit_1);		 // 좀비 피격음
		mSound.load(201, R.raw.sfx_zombie_attack);// 좀비 공격음
		mSound.load(202, R.raw.sfx_anger_block); // anger block
		mSound.load(203, R.raw.sfx_dancer_avoid); // dancer avoid
		mSound.load(999, R.raw.partner_die);	 // 파트너 죽음

		mSound.load(-10, R.raw.sfx_game_clear);
		mSound.load(-20, R.raw.sfx_game_over);

		setContentView(view);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_MENU:
			this.PopUpMenu();
			break;
		}
		return view.onKeyDown(keyCode, event);
	}

//	private void Focusable()
//	{
////		view.setFocusable(true);
////		view.setFocusableInTouchMode(true);
////		view.setClickable(true);
//	}

	public void Exit()
	{
		finish();
	}

}