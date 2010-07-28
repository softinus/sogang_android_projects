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
import com.raimsoft.matan.info.Stage1Info;
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
	private SpriteBitmap SPRITEpartner;

	private Stage1Info info;

	private boolean bRefreshImg_Bullets= true;

	private BulletConnection mConnection;
	private Bullet mBullet[]= new Bullet[8];
	private Zombie mZombie[]= new Zombie[16];
	// ************** 선언부 종료 ************** //

	public Stage1(Context managerContext)
	{
		mRes= managerContext.getResources();

		info= new Stage1Info();
		info.Init();

		SPRITEpartner= new SpriteBitmap(R.drawable.partner, mRes, 230,230,8, 20);

		for (int i=0; i<8; i++) // 마탄 초기화
			mBullet[i]= new Bullet(info.pBullet[i].x, info.pBullet[i].y, info.IDBullet[i], 70, 70);

		for (int i=0; i<16; i++) // 좀비 초기화
			mZombie[i]= new Zombie(info.pZombieStart[i].x, info.pZombieStart[i].y
					   ,new FPoint(info.pZombieStop[i].x,info.pZombieStop[i].y)
					   , R.drawable.ch_zombie_01, 400,100, mRes);


		BITMAPbackground= BitmapFactory.decodeResource(mRes, R.drawable.background_stage01);
		BITMAPbackline= BitmapFactory.decodeResource(mRes, R.drawable.bg_line);

		mConnection= new BulletConnection();

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

		for (int i=0; i<16; i++)
			mZombie[i].SPRITEwalk.Animate(canvas, (int)mZombie[i].x, (int)mZombie[i].y); // 좀비 그려줌


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

		for (int i=0; i<16; i++)
		{
			mZombie[i].Move(0.5f);
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
