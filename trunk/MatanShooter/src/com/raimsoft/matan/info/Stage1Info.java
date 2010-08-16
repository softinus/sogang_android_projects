package com.raimsoft.matan.info;

import android.graphics.Point;

import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.util.FPoint;

public class Stage1Info implements IStageInfo
{
	private static Stage1Info info= new Stage1Info();

	public Point pBullet[]= new Point[8]; // 마탄 포인트
	public int  IDBullet[]= new int[8]; // 마탄 열림 이미지ID
	public int  IDBullet_close[]= new int[8]; // 마탄 닫힘 이미지ID

	public FPoint pShotRoute[]= new FPoint[10]; // 마탄 슈팅 궤도

	public Point pZombieStart[]= new Point[16]; // 좀비 시작포인트
	public Point pZombieStop[]= new Point[16];  // 좀비 끝포인트

	public final int spdZombieWalk= 12;
	public final int spdZombieAtt= 7;
	public final int spdZombieHit= 50;
	public final int spdZombieDie= 3;

	public static Stage1Info getInstance()
	{
		return info;
	}

	protected Stage1Info()
	{
		for (int i=0; i<8; i++)
		{
			pBullet[i]= new Point();
		}

		for (int i=0; i<10; i++)
		{
			pShotRoute[i]= new FPoint();
		}

		for (int i=0; i<16; i++)
		{
			pZombieStart[i]= new Point();
			pZombieStop[i] = new Point();
		}
	}

	@Override
	public void Init()
	{
		pBullet[0].set(0  ,   0);
		pBullet[1].set(365,   0);
		pBullet[2].set(730,   0);
		pBullet[3].set(0  , 205);
		pBullet[4].set(730, 205);
		pBullet[5].set(0,   410);
		pBullet[6].set(365, 410);
		pBullet[7].set(730, 410);

		IDBullet[0]= R.drawable.obj_thron_open;
		IDBullet[1]= R.drawable.obj_normal_open;
		IDBullet[2]= R.drawable.obj_fire_open;
		IDBullet[3]= R.drawable.obj_normal_open;
		IDBullet[4]= R.drawable.obj_normal_open;
		IDBullet[5]= R.drawable.obj_light_open;
		IDBullet[6]= R.drawable.obj_normal_open;
		IDBullet[7]= R.drawable.obj_ice_open;

		IDBullet_close[0]= R.drawable.obj_thron_close;
		IDBullet_close[1]= R.drawable.obj_normal_close;
		IDBullet_close[2]= R.drawable.obj_fire_close;
		IDBullet_close[3]= R.drawable.obj_normal_close;
		IDBullet_close[4]= R.drawable.obj_normal_close;
		IDBullet_close[5]= R.drawable.obj_light_close;
		IDBullet_close[6]= R.drawable.obj_normal_close;
		IDBullet_close[7]= R.drawable.obj_ice_close;

		pZombieStart[0] .set(-100, -100);
		pZombieStart[1] .set( 100, -100);
		pZombieStart[2] .set( 350, -100);
		pZombieStart[3] .set( 600, -100);
		pZombieStart[4] .set( 800, -100);
		pZombieStart[5] .set(-100,   20);
		pZombieStart[6] .set( 800,   20);
		pZombieStart[7] .set(-100,  190);
		pZombieStart[8] .set( 800,  190);
		pZombieStart[9] .set(-100,  360);
		pZombieStart[10].set( 800,  360);
		pZombieStart[11].set(-100,  480);
		pZombieStart[12].set( 100,  480);
		pZombieStart[13].set( 350,  480);
		pZombieStart[14].set( 600,  480);
		pZombieStart[15].set( 800,  480);

		pZombieStop[0] .set( 270,  130);
		pZombieStop[1] .set( 300,  115);
		pZombieStop[2] .set( 350,   95);
		pZombieStop[3] .set( 400,  115);
		pZombieStop[4] .set( 430,  130);
		pZombieStop[5] .set( 265,  155);
		pZombieStop[6] .set( 435,  155);
		pZombieStop[7] .set( 250,  190);
		pZombieStop[8] .set( 450,  190);
		pZombieStop[9] .set( 265,  225);
		pZombieStop[10].set( 435,  225);
		pZombieStop[11].set( 270,  240);
		pZombieStop[12].set( 300,  250);
		pZombieStop[13].set( 350,  250);
		pZombieStop[14].set( 400,  250);
		pZombieStop[15].set( 435,  240);
	}
}

