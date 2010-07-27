package com.raimsoft.matan.stage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.motion.MotionVectors;
import com.raimsoft.matan.object.Bullet;
import com.raimsoft.matan.object.BulletConnection;
import com.raimsoft.matan.object.Zombie;
import com.raimsoft.matan.util.FPoint;
import com.raimsoft.matan.util.SpriteBitmap;

public class Stage1 extends BaseStage
{

	// ************** 선언부 시작 ************** //
	private Resources mRes;

	private Paint PAINTLine;

	private Bitmap BITMAPbackground, BITMAPbackline;
	private SpriteBitmap SPRITETrap;
	private SpriteBitmap SPRITEpartner;
	//private Fog mFog;

	private Zombie mZombie[]=new Zombie[16];
	MotionVectors motion;

	private boolean bRefreshImg_Bullets= true;

	private BulletConnection mConnection;
	private Bullet mBullet[]=new Bullet[8];
	// ************** 선언부 종료 ************** //

	public Stage1(Context managerContext)
	{
		mRes= managerContext.getResources();

		motion= new MotionVectors();

		SPRITETrap= new SpriteBitmap(R.drawable.trap1_sprite, mRes, 50, 50, 5, 10);
		SPRITEpartner= new SpriteBitmap(R.drawable.partner, mRes, 230,230,8, 20);

		mBullet[0]= new Bullet(0,     0, R.drawable.obj_thron_open, 70, 70);
		mBullet[1]= new Bullet(365,   0, R.drawable.obj_normal_open, 70, 70);
		mBullet[2]= new Bullet(730,   0, R.drawable.obj_fire_open, 70, 70);
		mBullet[3]= new Bullet(0,   205, R.drawable.obj_normal_open, 70, 70);
		mBullet[4]= new Bullet(730, 205, R.drawable.obj_normal_open, 70, 70);
		mBullet[5]= new Bullet(0,  	410, R.drawable.obj_light_open, 70, 70);
		mBullet[6]= new Bullet(365, 410, R.drawable.obj_normal_open, 70, 70);
		mBullet[7]= new Bullet(730, 410, R.drawable.obj_ice_open, 70, 70);


		mZombie[0]= new Zombie(-100, -100,new FPoint(270,130), R.drawable.ch_zombie_01, 400,100, mRes);
		mZombie[1]= new Zombie(800, -100,new FPoint(430,130), R.drawable.ch_zombie_01, 400,100, mRes);


		//mFog= new Fog(0,0, R.drawable.eff_fog2, 600,360);

		BITMAPbackground= BitmapFactory.decodeResource(mRes, R.drawable.background_stage01);
		BITMAPbackline= BitmapFactory.decodeResource(mRes, R.drawable.bg_line);

		mConnection= new BulletConnection();


		//mFog.DRAWimage= mRes.getDrawable(mFog.IDimage);

		PAINTLine= new Paint();
		PAINTLine.setARGB(128, 255, 0, 255);
		PAINTLine.setStrokeWidth(6.0f);
		PAINTLine.setAntiAlias(true);
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

		//for (int i=0; i<15; i++)
			mZombie[0].SPRITEwalk.Animate(canvas, (int)mZombie[0].x, (int)mZombie[0].y); // 좀비 그려줌
			mZombie[1].SPRITEwalk.Animate(canvas, (int)mZombie[1].x, (int)mZombie[1].y);

		canvas.drawBitmap(BITMAPbackline, 0, 0, null);	// 배경라인 그려줌

		if (this.bRefreshImg_Bullets) // 마탄 이미지 새로고침
		{
			for (int i=0; i<8; i++)
			{
				if (mBullet[i].bClosed)
				{
					switch (i) // 마탄 번호에 따른 열림 이미지 변경
					{
					case 0:
						mBullet[i].IDimage= R.drawable.obj_thron_open;
						break;
					case 1:
						mBullet[i].IDimage= R.drawable.obj_normal_open;
						break;
					case 2:
						mBullet[i].IDimage= R.drawable.obj_fire_open;
						break;
					case 3:
						mBullet[i].IDimage= R.drawable.obj_normal_open;
						break;
					case 4:
						mBullet[i].IDimage= R.drawable.obj_normal_open;
						break;
					case 5:
						mBullet[i].IDimage= R.drawable.obj_light_open;
						break;
					case 6:
						mBullet[i].IDimage= R.drawable.obj_normal_open;
						break;
					case 7:
						mBullet[i].IDimage= R.drawable.obj_ice_open;
						break;
					}


				}else{
					switch (i) // 마탄 번호에 따른 닫힘 이미지 변경
					{
					case 0:
						mBullet[i].IDimage= R.drawable.obj_thron_close;
						break;
					case 1:
						mBullet[i].IDimage= R.drawable.obj_normal_close;
						break;
					case 2:
						mBullet[i].IDimage= R.drawable.obj_fire_close;
						break;
					case 3:
						mBullet[i].IDimage= R.drawable.obj_normal_close;
						break;
					case 4:
						mBullet[i].IDimage= R.drawable.obj_normal_close;
						break;
					case 5:
						mBullet[i].IDimage= R.drawable.obj_light_close;
						break;
					case 6:
						mBullet[i].IDimage= R.drawable.obj_normal_close;
						break;
					case 7:
						mBullet[i].IDimage= R.drawable.obj_ice_close;
						break;
					}

				}

				mBullet[i].DRAWimage= mRes.getDrawable(mBullet[i].IDimage);
				mBullet[i].DRAWimage.setBounds(mBullet[i].getObjectForRect());
			}
			bRefreshImg_Bullets= false;
		}


		SPRITETrap.Animate(canvas, 100, 100);	// 트랩 그려줌
		SPRITEpartner.Animate(canvas, 285, 125);	// 파트너 그려줌

		//mFog.DRAWimage.setBounds(mFog.getObjectForRect());	// 안개 위치 세팅
		//mFog.DRAWimage.draw(canvas);	// 안개 그려줌



		for (int i=0; i<8; i++)
		{
			mBullet[i].DRAWimage.draw(canvas);	// 마탄 그려줌
		}

		if (mConnection.bDrag) // 드래그 상태이면
		{
			for (int i=0; i<8; i++)
			{
				if (mConnection.pConnect[0].ConvertToPoint().equals(mBullet[i].getObjectMiddleSpot()))
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

	@Override
	public boolean StageUpdate()
	{

		if (mConnection.bOut) // 선이 밖으로 나갔으면
			PAINTLine.setARGB(128, 0, 255, 0); // 쓰레기색
		else
			PAINTLine.setARGB(128, 255, 0, 255); // 아니면 초록색

		//for (int i=0; i<15; i++)
		{
			mZombie[0].Move(0.5f);
			mZombie[1].Move(0.5f);
		}

		//mFog.MoveTest();


		return false;
	}





	@Override
	public void Touch(int actionID, float touchX, float touchY)
	{
		Log.d("Stage1::Line", Float.toString(mConnection.ConnectionNum));


		switch(actionID)
		{
		case MotionEvent.ACTION_DOWN:

			mConnection.bDrag= true; // 선 시작됨

			break;
		case MotionEvent.ACTION_MOVE:

			if (!mConnection.bDrag) return; // 터치가 안된상태면 그냥 넘어감

			for(int i=0; i<8; i++)
			{
				if (mBullet[i].getObjectForRect().contains((int)touchX, (int)touchY))
				{ // 마탄을 터치했는가
					if (mBullet[i].bClosed) return; // 해당 마탄이 이미 닫혀있으면 선 추가안함

					mConnection.pConnect[mConnection.ConnectionNum].setPoint(mBullet[i].getObjectMiddleSpot());
					mConnection.AddConnectionPoint();
					mConnection.pConnect[mConnection.ConnectionNum+1].setPoint(mBullet[i].getObjectMiddleSpot());
					mBullet[i].bClosed= true; // 해당 마탄 닫힘

					this.bRefreshImg_Bullets= true; // 마탄 이미지 새로고침

					if (mConnection.LastConnectBulletNum != -1)
					{// 마지막으로 연결된 마탄의 정보가 있으면
						Log.d("Stage1::OutCheck", Float.toString(mConnection.LastConnectBulletNum)+"-"+Float.toString(i));

						if (mConnection.OutCombination(mConnection.LastConnectBulletNum, i))
						{// 마지막 연결된 마탄번호와 최근연결된 마탄번호를 통해 선이 밖으로 나갔으면
							mConnection.bOut= true; // 나갔음으로 체크
						}
						mConnection.LastConnectBulletNum= i; // 마지막 연결된 마탄번호 지정
					}else{// 없으면
						mConnection.LastConnectBulletNum= i; // 마지막 연결된 마탄번호 지정
					}
					return;
				}
			}

			if (mConnection.ConnectionNum!=0) // 기존 0번째 포인트가 있을 경우
			{
				//if (mConnection.bOut) return;
					mConnection.pConnect[mConnection.ConnectionNum].setFPoint(touchX, touchY);
			}


			break;
		case MotionEvent.ACTION_UP:

			for (int i=0; i<mConnection.pConnect.length; i++)
				mConnection.pConnect[i]= new FPoint(); // 배열 인덱스모두 동적할당

			for (int i=0; i<8; i++)
				mBullet[i].bClosed= false;	// 마탄 모두 열림
			this.bRefreshImg_Bullets= true;

			mConnection.ConnectionNum= 0;	// 연결된 개수없음

			mConnection.bDrag= false;	// 선 끊어짐
			mConnection.bOut= false;	// 선이 밖으로 나가지 않음
			mConnection.LastConnectBulletNum= -1;	// 최근 정보 초기화
			break;
		}
	}

	@Override
	public boolean KeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			return true;
		}
		return true;
	}

}
