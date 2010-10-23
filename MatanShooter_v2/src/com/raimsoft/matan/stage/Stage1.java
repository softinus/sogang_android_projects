package com.raimsoft.matan.stage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.info.PartnerStateEnum;
import com.raimsoft.matan.info.StageInfo;
import com.raimsoft.matan.info.ZombieStateEnum;
import com.raimsoft.matan.object.AbstractZombie;
import com.raimsoft.matan.object.Bullet;
import com.raimsoft.matan.object.GameTimer;
import com.raimsoft.matan.object.Matan;
import com.raimsoft.matan.object.MatanConnection;
import com.raimsoft.matan.object.Partner;
import com.raimsoft.matan.object.TrafficLights;
import com.raimsoft.matan.object.ZombieManager;
import com.raimsoft.matan.util.FPoint;
import com.raimsoft.matan.util.FrameManager;
import com.raimsoft.matan.util.SoundManager;

public class Stage1 extends BaseStage
{
	// ************** 선언부 시작 ************** //
	private Resources mRes;
	private SoundManager sm;
	private MediaPlayer mp;
	private AudioManager mAudioManager;
	private Vibrator mVib;

	private Paint PAINTLine;
	private PathEffect EFFPath;

	private boolean bRefreshImg_Matans= true;

	private StageInfo info= StageInfo.getInstance();

	private Bitmap BITMAPbackground, BITMAPbackline;

	private MatanConnection mConnection;

	private Partner mPartner;// private int nCloseZombie;
	private Matan mMatan[]= new Matan[8];
	private ZombieManager mZombieMgr;
	private AbstractZombie mZombie;
	private Bullet mShot;
	private TrafficLights mTraffic;
	private GameTimer mGTimer;



	//private GameActivity mGame= new GameActivity();


	// ************** 선언부 종료 ************** //




	public Stage1(Context managerContext)
	{
		// ************** 생성부 시작 ************** //

		mRes= managerContext.getResources();
		mZombieMgr= new ZombieManager(mRes);

		mVib= (Vibrator) managerContext.getSystemService(Context.VIBRATOR_SERVICE);

		// 사운드 초기화
		mAudioManager = (AudioManager)managerContext.getSystemService(Context.AUDIO_SERVICE);
		sm= new SoundManager (managerContext);
		sm.create();
		sm.load(0, R.raw.sfx_drag);
		sm.load(1, R.raw.sfx_drag_one_65);
		sm.load(2, R.raw.sfx_touch_matan);
		sm.load(100, R.raw.sfx_shot_sting);
		sm.load(101, R.raw.sfx_bullet_2);
		sm.load(102, R.raw.sfx_shot_fire);
		sm.load(103, R.raw.sfx_shot_bolt);
		sm.load(104, R.raw.sfx_ice_1);

		mp= new MediaPlayer();
		mp= MediaPlayer.create(managerContext, R.raw.bgm);
		mp.start();

		// 정보 초기화
		info.Init( 1 );

		// 파트너 초기화
		mPartner= new Partner(285,125, R.drawable.ch_crossbow12_sprite, 230,230, mRes);
		mPartner.mHPbar.DRAWimage= mRes.getDrawable(mPartner.mHPbar.IDimage); // HP스킨(아이디)
		mPartner.mHPbar.DRAWimage.setBounds(mPartner.mHPbar.getObjectForRect()); // HP스킨(위치)
		mPartner.mHPbar.DRAWimageBAR= mRes.getDrawable(mPartner.mHPbar.IDimageBAR); // HP바(아이디)
		mPartner.mHPbar.DRAWimageBAR.setBounds(mPartner.mHPbar.getRectBar4Rect(100, 100)); // HP바(위치)

		// 배경 초기화
		BITMAPbackground= BitmapFactory.decodeResource(mRes, R.drawable.background_stage01);
		BITMAPbackline= BitmapFactory.decodeResource(mRes, R.drawable.bg_line);

		// 선 초기화
		mConnection= new MatanConnection();
		EFFPath= new PathDashPathEffect(info.makePathDash(), 25, 0, PathDashPathEffect.Style.ROTATE);
		PAINTLine= new Paint();
		PAINTLine.setARGB(128, 255, 0, 255);
		PAINTLine.setPathEffect(EFFPath);
		PAINTLine.setAntiAlias(true);

		// 마탄 초기화
		for (int i=0; i<8; i++)
			mMatan[i]= new Matan(info.pMatan[i].x, info.pMatan[i].y, info.IDMatan[i], 70, 70);

		 // 탄환 초기화
		mShot= new Bullet(0,0, R.drawable.tan_basic, 25,25, mRes);



		 // 신호등 초기화
		mTraffic= new TrafficLights(0,0, R.drawable.background_stage01_time_ui, 182,265);
		mTraffic.DRAWimage= mRes.getDrawable(mTraffic.IDimage);
		mTraffic.DRAWimage.setBounds(mTraffic.getObjectForRect());

		// 타이머 초기화
		mGTimer= new GameTimer(managerContext);
		mGTimer.setTimer( 45 );

		// ************** 생성부 종료 ************** //
	}



	@Override
	public int GetStageID()
	{
		return StageManager.STAGE_1;
	}

	@Override
	public void StageRender(Canvas canvas)
	{
		canvas.drawBitmap(BITMAPbackground, 0, 0, null);	// 배경 그려줌

		this.Render_AllZombies(canvas, 8, 16); // 좀비 뒤 그려줌

		this.Render_Partner(canvas);	// 파트너 그려줌

		this.Render_AllZombies(canvas, 0, 8); // 좀비 앞 그려줌

		mTraffic.DRAWimage.draw(canvas); //신호등 그려줌
		mGTimer.ShowTimer(canvas, 95, 245);

		canvas.drawBitmap(BITMAPbackline, 0, 0, null);	// 배경라인 그려줌

		this.Render_Matans(canvas);	// 마탄 그려줌

		this.Render_BulletEffects(canvas); // 총알 이펙트 그려줌

		this.Render_Bullets(canvas); // 총알 그려줌
	}



	@SuppressWarnings("static-access")
	@Override
	public boolean StageUpdate()
	{
		if (FrameManager.bPause) return false;

		if (mGTimer.Update())
		{ // 타이머가 끝나면
			this.NextStageID= StageManager.STAGE_2;
			m_sGame.PopUpResult();
		}

		if (FrameManager.FrameTimer(50) && (mZombieMgr.List.size()<20)) // 50프레임마다 한마리씩 20마리 제한
		{
			//mZombieMgr.List.add(new Wanderer((int) (Math.random()*16), R.drawable.ch_zombie1_walk, 100,100, mRes));
			mZombieMgr.AddRandomZombie( 1 );
			Log.i("Stage1::ZombieMgr", mZombieMgr.List.size() + "th Zombie Added.");
		}

		/* 선 */
		if (mConnection.bOut) // 선이 밖으로 나갔으면
			PAINTLine.setARGB(128, 0, 255, 0); // 쓰레기색
		else
			PAINTLine.setARGB(128, 255, 0, 255); // 아니면 초록색

		/* 좀비 */
		for (int i=0; i<mZombieMgr.List.size(); i++)
		{
			mZombie= mZombieMgr.List.get(i); // 현재 좀비 가져옴

			mZombie.Move(info.spdAllZombie); // 좀비 움직임

			if (mZombie.bImageRefresh) // 좀비 이미지
				mZombieMgr.Refresh_Zombies(i);

			if (mZombie.eState== ZombieStateEnum.ATTACK) // 좀비 공격
				mPartner.Damage(mZombie.nPower, info.spdZombie1Att*7);

			if (mZombie.getObjectForRect().intersect(mShot.getObjectForRect())) // 탄환과 충돌
			{
				if (mShot.bShooting) mZombie.Damage(35, 0);
			}
		}

		/* 파트너 */
		if (mPartner.bImageRefresh)	mPartner.Refresh_Partner();

//		nCloseZombie= mZombieMgr.ClosestZombieNum();
//		if (!mPartner.Shooting(nCloseZombie, mZombieMgr.List.get(mZombieMgr.nClosestListNum).eState))
//		{
//			nCloseZombie= mZombieMgr.ClosestZombieNum();
//		}


		/* 마탄 */
		if (this.bRefreshImg_Matans) // 마탄 이미지 새로고침
			this.Refresh_Matans();

		/* 탄환 */
		if(mShot.bShooting)	mShot.Move(info.spdAllBullet); // 탄환 움직임



		for (int i=0; i<mShot.mEffList.size(); i++)
			if (mShot.mEffList.get(i).AlphaDrop())
			{
				mShot.mEffList.remove(i);
				Log.i("Stage1::Eff_removed", "BulletEffectNum= "+mShot.mEffList.size());
			}

		SoundPlay(mShot.nSoundPlayID);



		return false;
	}





	@Override
	public void Touch(int actionID, float touchX, float touchY)
	{
		Log.d("Stage1::Line", Float.toString(mConnection.ConnectionNum));

		if (mShot.bShooting) return;

		switch(actionID)
		{
		case MotionEvent.ACTION_DOWN:

			mConnection.bDrag= true; // 선 시작됨
			break;

		case MotionEvent.ACTION_MOVE:

			if (!mConnection.bDrag) return; // 터치가 안된상태면 그냥 넘어감

			for(int i=0; i<8; i++)
			{
				if (mMatan[i].getObjectForRect().contains((int)touchX, (int)touchY))
				{ // 마탄을 터치했는가
					if (mMatan[i].bOpen) return; // 해당 마탄이 이미 닫혀있으면 선 추가안함

					mConnection.AddConnectionPoint(i, mMatan[i].getObjectMiddleSpot()); // 포인트 추가
					mMatan[i].bOpen= true; // 해당 마탄 닫힘

					this.bRefreshImg_Matans= true; // 마탄 이미지 새로고침

					if (mConnection.LastConnectBulletNum != -1)
					{// 마지막으로 연결된 마탄의 정보가 있으면
						Log.d("Stage1::OutCheck", Float.toString(mConnection.LastConnectBulletNum)+"-->"+Float.toString(i));

						if (mConnection.OutCombination(mConnection.LastConnectBulletNum, i))
						{// 마지막 연결된 마탄번호와 최근연결된 마탄번호를 통해 선이 밖으로 나갔으면
							mConnection.bOut= true; // 나갔음으로 체크
						}else{
							mConnection.bSaving= true;
						}
						mVib.vibrate(50);
						sm.play(2);
						mConnection.LastConnectBulletNum= i; // 마지막 연결된 마탄번호 지정
					}else{// 없으면
						mVib.vibrate(50);

						mConnection.LastConnectBulletNum= i; // 마지막 연결된 마탄번호 지정
					}
					return;
				}
			}

			if (mConnection.ConnectionNum!=0) // 기존 0번째 포인트가 있을 경우
				mConnection.pConnect[mConnection.ConnectionNum].setFPoint(touchX, touchY);


			break;
		case MotionEvent.ACTION_UP:

			if(mConnection.bOut)
			{
				for (int i=0; i<mConnection.pConnect.length; i++)
				{
					info.pShotRoute[i]= mConnection.pConnect[i];
					mConnection.pConnect[i]= new FPoint(); // 배열 인덱스모두 동적할당
				}
			}


			if(mConnection.bOut) mShot.bShooting= true;

			for (int i=0; i<8; i++)
				mMatan[i].bOpen= false;	// 마탄 모두 열림
			this.bRefreshImg_Matans= true;

			mShot.nShootMax= mConnection.ConnectionNum; // 탄환 경로수 설정
			mConnection.ConnectionNum= 0;	// 연결된 개수없음

			mConnection.bDrag= false;	// 선 끊어짐
			mConnection.bOut= false;	// 선이 밖으로 나가지 않음
			mConnection.bSaving= false; // 세이빙 되지 않음
			mConnection.LastConnectBulletNum= -1;	// 최근 정보 초기화
			break;
		}
	}

	@Override
	public boolean KeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_MENU)
			FrameManager.TogglePause();

		if(keyCode==KeyEvent.KEYCODE_BACK)
			return true;

		if (keyCode==KeyEvent.KEYCODE_VOLUME_UP)
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);  //media volume down!

		if (keyCode==KeyEvent.KEYCODE_VOLUME_DOWN)
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);  //media volume down!
		return false;
	}

	// ************** 렌더 메소드 시작 ************** //

	private void Render_AllZombies(Canvas canvas, int nStart, int nEnd)
	{
		for (int i=0; i<mZombieMgr.List.size(); i++)
		{
			mZombie= mZombieMgr.List.get(i);

			if ((mZombie.nRoute > nStart) && (mZombie.nRoute < nEnd)) continue;

			if (mZombie.eState==ZombieStateEnum.WALK || mZombie.eState==ZombieStateEnum.ATTACK)
				mZombie.SPRITE.Animate(canvas, (int)mZombie.x, (int)mZombie.y);

			if (mZombie.eState==ZombieStateEnum.HIT || mZombie.eState==ZombieStateEnum.DIE)
			{ // 맞거나 죽으면
				if (mZombie.SPRITE.AnimateNoLoop(canvas, (int)mZombie.x, (int)mZombie.y))
				{ // 해당 SPRITE 애니메이션 1번 반복 끝나면
					if (mZombie.eState 	 == ZombieStateEnum.DIE) // 죽으면 노드 삭제
						mZombieMgr.List.remove(i);
					if (mZombie.eOldState == ZombieStateEnum.NONE) //
						continue;
					mZombie.eState   = mZombie.eOldState;	// 전상태를 현상태로
					mZombie.eOldState= ZombieStateEnum.NONE; // 전상태=NONE
					mZombie.bImageRefresh= true;
				}
			}
		}
	}



	private void Render_Partner(Canvas canvas)
	{
		if (mPartner.eState==PartnerStateEnum.DIE)
			mPartner.SPRITE.AnimateLastFrameStop(canvas, (int)mPartner.x, (int)mPartner.y);	// 파트너 그려줌
		else
			mPartner.SPRITE.Animate(canvas, (int)mPartner.x, (int)mPartner.y);	// 파트너 그려줌
		mPartner.mHPbar.DRAWimage.draw(canvas);
		mPartner.mHPbar.DRAWimageBAR.draw(canvas);
	}



	private void Render_Matans(Canvas canvas)
	{
		for (int i=0; i<8; i++)
		{
			mMatan[i].DRAWimage.draw(canvas);	// 마탄 그려줌
		}

		if (mConnection.bDrag) // 마탄(선) 그려줌, 드래그 상태이면
		{
			for (int i=0; i<8; i++)
			{
				if (mConnection.pConnect[0].ConvertToPoint().equals(mMatan[i].getObjectMiddleSpot()))
				{ // 0번째 포인트가 블렛의 가운데 포인트에 있으면
					if (!mConnection.pConnect[1].equal(0, 0))
						canvas.drawLine(mConnection.pConnect[0].x, mConnection.pConnect[0].y
									  , mConnection.pConnect[1].x, mConnection.pConnect[1].y, PAINTLine);
				}
			}

			for (int i=1; i<mConnection.ConnectionNum; i++)
			{
				canvas.drawLine(mConnection.pConnect[i].x, mConnection.pConnect[i].y
							, mConnection.pConnect[i+1].x, mConnection.pConnect[i+1].y, PAINTLine);
			}
		}
	}

	private void Render_Bullets(Canvas canvas)
	{
		if(mShot.bShooting)
		{
			if (mShot.bImageRefresh) this.Refresh_Shot();

			mShot.DRAWimage.setBounds(mShot.getObjectForRect()); // 탄환 그려줌
			mShot.DRAWimage.draw(canvas);
		}
	}

	private void Render_BulletEffects(Canvas canvas)
	{
		for (int i=0; i<mShot.mEffList.size(); i++)
		{
			mShot.mEffList.get(i).DRAWimage.setBounds(mShot.mEffList.get(i).getObjectForRect());
			mShot.mEffList.get(i).DRAWimage.setAlpha(mShot.mEffList.get(i).nAlpha);
			mShot.mEffList.get(i).DRAWimage.draw(canvas);
		}
	}

	// ************** 렌더 메소드 끝 ************** //


	private void SoundPlay(int ID)
	{
		if (ID==-1) return;
		sm.play(ID);
		mShot.nSoundPlayID= -1;
	}

	/**
	 * 마탄 새로고침
	 */
	private void Refresh_Matans()
	{
		for (int i=0; i<8; i++)
		{
			if (mMatan[i].bOpen)
				mMatan[i].IDimage= info.IDMatan[i];
			else
				mMatan[i].IDimage= info.IDMatan_close[i];

			mMatan[i].DRAWimage= mRes.getDrawable(mMatan[i].IDimage);
			mMatan[i].DRAWimage.setBounds(mMatan[i].getObjectForRect());
		}
		bRefreshImg_Matans= false;
	}

	/**
	 * 탄환 새로고침
	 */
	private void Refresh_Shot()
	{
		mShot.DRAWimage= mRes.getDrawable(mShot.IDimage);
		mShot.bImageRefresh= false;
	}



	@Override
	public void SoundStop()
	{
		mp.stop();
	}


}
