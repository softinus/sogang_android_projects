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

import com.raimsoft.matan.activity.GameActivity;
import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.info.PartnerStateEnum;
import com.raimsoft.matan.info.StageInfo;
import com.raimsoft.matan.info.ZombieStateEnum;
import com.raimsoft.matan.object.AbstractZombie;
import com.raimsoft.matan.object.Bullet;
import com.raimsoft.matan.object.Crain;
import com.raimsoft.matan.object.Doodad;
import com.raimsoft.matan.object.GameTimer;
import com.raimsoft.matan.object.Matan;
import com.raimsoft.matan.object.MatanConnection;
import com.raimsoft.matan.object.Partner;
import com.raimsoft.matan.object.ZombieManager;
import com.raimsoft.matan.object.effect.HitEffect;
import com.raimsoft.matan.util.FPoint;
import com.raimsoft.matan.util.FrameManager;

public class Stage3 extends BaseStage
{
	// ************** 선언부 시작 ************** //
	private Resources mRes;
	private MediaPlayer mBGM;
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
	private Doodad mCrain;

	private GameTimer mGTimer;
	private GameTimer mGameOverTimer;


	// ************** 선언부 종료 ************** //




	public Stage3(Context managerContext)
	{
		// ************** 생성부 시작 ************** //

		mRes= managerContext.getResources();
		mZombieMgr= new ZombieManager(mRes);

		mVib= (Vibrator) managerContext.getSystemService(Context.VIBRATOR_SERVICE);

		// 사운드 초기화
		mAudioManager = (AudioManager)managerContext.getSystemService(Context.AUDIO_SERVICE);

		mBGM= new MediaPlayer();
		mBGM= MediaPlayer.create(managerContext, R.raw.matanstage3);
		mBGM.setLooping(true);
		mBGM.start();

		// 정보 초기화
		info.Init( 3 );

		// 파트너 초기화
		mPartner= new Partner(285,125, R.drawable.ch_crossbow12_sprite, 230,230, mRes);
		mPartner.mHPbar.DRAWimage= mRes.getDrawable(mPartner.mHPbar.IDimage); // HP스킨(아이디)
		mPartner.mHPbar.DRAWimage.setBounds(mPartner.mHPbar.getObjectForRect()); // HP스킨(위치)
		mPartner.mHPbar.DRAWimageBAR= mRes.getDrawable(mPartner.mHPbar.IDimageBAR); // HP바(아이디)
		mPartner.mHPbar.DRAWimageBAR.setBounds(mPartner.mHPbar.getRectBar4Rect(100, 100)); // HP바(위치)

		// 배경 초기화
		BITMAPbackground= BitmapFactory.decodeResource(mRes, R.drawable.background_stage03);
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



		 // 크레인 초기화
		mCrain= new Crain(0,0, R.drawable.background_stage03_crain, 155,271);
		mCrain.DRAWimage= mRes.getDrawable(mCrain.IDimage);
		mCrain.DRAWimage.setBounds(mCrain.getObjectForRect());

		// 타이머 초기화
		mGTimer= new GameTimer(managerContext);
		mGTimer.setTimer( 45 );

		mGameOverTimer= new GameTimer(managerContext);
		mGameOverTimer.setTimer( 2 );

		// ************** 생성부 종료 ************** //
	}



	@Override
	public int GetStageID()
	{
		return StageManager.STAGE_3;
	}

	@Override
	public void StageRender(Canvas canvas)
	{
		canvas.drawBitmap(BITMAPbackground, 0, 0, null);	// 배경 그려줌

		this.Render_AllZombies(canvas, 8, 16); // 좀비 뒤 그려줌

		this.Render_Partner(canvas);	// 파트너 그려줌

		this.Render_AllZombies(canvas, 0, 8); // 좀비 앞 그려줌

		mCrain.DRAWimage.draw(canvas); //신호등 그려줌
		mGTimer.ShowTimer(canvas, 102, 175);

		canvas.drawBitmap(BITMAPbackline, 0, 0, null);	// 배경라인 그려줌

		this.Render_Matans(canvas);	// 마탄 그려줌

		this.Render_BulletEffects(canvas); // 총알 이펙트 그려줌

		this.Render_Bullets(canvas); // 총알 그려줌

		this.Render_HitEffects(canvas); //
		this.Render_MatanCollisionEffects(canvas); // 마탄 충돌 이펙트
	}



	@SuppressWarnings("static-access")
	@Override
	public boolean StageUpdate()
	{
		if (FrameManager.bPause) return false;

		if ( mGTimer.Update() )
		{ // 타이머가 끝나면
			this.NextStageID= StageManager.STAGE_1;
			s_GameAct.PopUpResult();
		}



		if (FrameManager.FrameTimer(50) && (mZombieMgr.List.size() < 20)) // 50프레임마다 한마리씩 20마리 제한
		{
			mZombieMgr.AddRandomZombie( 3 );
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
			{
				if( FrameManager.FrameTimer(20) )
					GameActivity.mSound.play(201);
				mPartner.Damage(mZombie.nPower, info.spdZombie1Att*7, 0);
			}


			if( mZombie.getObjectForRect().intersect( mShot.getObjectForRect() ) ) // 탄환과 충돌
			{
				if (mShot.bShooting)
				{
					if (! (mZombie.eState==ZombieStateEnum.DIE ||
						   mZombie.eState==ZombieStateEnum.HIT ||
						   mZombie.eState==ZombieStateEnum.BLOCK ) )
				   {
						mShot.m_vHitEff.add(new HitEffect(mShot.x-50, mShot.y-25, R.drawable.eff_hit, 100,50, mRes));
						mZombie.Damage(35, 0, mShot.IDimage);
				   }
				}
			}
		}

		/* 파트너 */
		if (mPartner.bImageRefresh)	mPartner.Refresh_Partner();

		if ( mPartner.eState == PartnerStateEnum.DIE )
		{
			if (mPartner.bDeathSound)
			{
				GameActivity.mSound.play(999); // 파트너 죽는 소리 재생
				mPartner.bDeathSound= false;
			}

			this.SoundStop(); // 죽으면 바로 소리 끔

			if ( mGameOverTimer.Update() ) // 파트너 죽으면 타이머 지나감
				s_GameAct.PopUpGameOver(); // 타이머 끝나면 게임오버 화면 출력
		}



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

		for (int i=0; i<mShot.m_vEff.size(); i++)
			if (mShot.m_vEff.get(i).AlphaDrop())
			{
				mShot.m_vEff.remove(i);
				Log.i("Stage1::Eff_removed", "BulletEffectNum= "+mShot.m_vEff.size());
			}

		for (int i=0; i<mShot.m_vHitEff.size(); i++)
			if (((HitEffect) mShot.m_vHitEff.get(i)).AlphaDrop())
			{
				mShot.m_vHitEff.remove(i);
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
						GameActivity.mSound.play(2);
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

			if ( (mZombie.nRoute > nStart) && (mZombie.nRoute < nEnd) ) continue;

			if (mZombie.eState==ZombieStateEnum.WALK ||
				mZombie.eState==ZombieStateEnum.ATTACK )
			{
				mZombie.SPRITE.Animate(canvas, (int)mZombie.x, (int)mZombie.y);
			}

			if ( mZombie.eState==ZombieStateEnum.HIT ||
				 mZombie.eState==ZombieStateEnum.DIE ||
				 mZombie.eState==ZombieStateEnum.BLOCK )
			{ // 맞거나 죽으면
				if ( mZombie.SPRITE.AnimateNoLoop(canvas, (int)mZombie.x, (int)mZombie.y) )
				{ // 해당 SPRITE 애니메이션 1번 반복 끝나면
					if ( mZombie.eState 	 == ZombieStateEnum.DIE ) // 죽으면 노드 삭제
						mZombieMgr.List.remove(i);
					if ( mZombie.eOldState == ZombieStateEnum.NONE )  //
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
		for (int i=0; i<mShot.m_vEff.size(); i++)
		{
			mShot.m_vEff.get(i).DRAWimage.setBounds(mShot.m_vEff.get(i).getObjectForRect());
			mShot.m_vEff.get(i).DRAWimage.setAlpha(mShot.m_vEff.get(i).nAlpha);
			mShot.m_vEff.get(i).DRAWimage.draw(canvas);
		}
	}

	private void Render_HitEffects(Canvas canvas)
	{
		for (int i=0; i<mShot.m_vHitEff.size(); ++i)
		{
			mShot.m_vHitEff.get(i).DRAWimage.setBounds(mShot.m_vHitEff.get(i).getObjectForRect());
			mShot.m_vHitEff.get(i).DRAWimage.setAlpha(mShot.m_vHitEff.get(i).nAlpha);
			mShot.m_vHitEff.get(i).DRAWimage.draw(canvas);
		}
	}


	private void Render_MatanCollisionEffects(Canvas canvas)
	{
		for(int i=0; i<mShot.m_vCollEff.size(); i++)
		{
			if ( mShot.m_vCollEff.get(i).SPRITE.AnimateNoLoop(canvas,
					(int)mShot.m_vCollEff.get(i).x,
					(int)mShot.m_vCollEff.get(i).y) )
			{
				mShot.m_vCollEff.remove(i);
			}
		}
	}
//
//	public void Render_MatanEffect(Canvas canvas)
//	{
//		mShot.mCollisionEff.SPRITE.AnimateNoLoop(canvas, (int)mShot.x, (int)mShot.y);
//	}

	// ************** 렌더 메소드 끝 ************** //


	/**
	 * 소리 재생
	 */
	private void SoundPlay(int ID)
	{
		if (ID==-1) return;
		GameActivity.mSound.play(ID);
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
		mBGM.stop();
	}


}
