package com.raimsoft.matan.info;

import android.graphics.Path;
import android.graphics.Point;

import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.util.FPoint;

public class Stage1Info implements IStageInfo
{
	private static Stage1Info info= new Stage1Info();

	public static Stage1Info getInstance()
	{
		return info;
	}

	public Point pMatan[]= new Point[8]; // 마탄 포인트
	public int  IDMatan[]= new int[8]; // 마탄 열림 이미지ID
	public int  IDMatan_close[]= new int[8]; // 마탄 닫힘 이미지ID

	public FPoint pShotRoute[]= new FPoint[10]; // 마탄 슈팅 궤도
	public int nShotRoute[]= new int[10];

	public int IDShot[]= new int[8];
	public int IDShot_saving[]= new int[8];

	public Point pZombieStart[]= new Point[16]; // 좀비 시작포인트
	public Point pZombieStop[]= new Point[16];  // 좀비 끝포인트

	// 좀비 속도
	public final int spdZombieWalk= 10;
	public final int spdZombieAtt= 4;
	public final int spdZombieHit= 40;
	public final int spdZombieDie= 3;

	public final int spdPartnerShot= 7;
	public final int spdPartnerDie= 4;



	protected Stage1Info()
	{
		for (int i=0; i<8; i++)
		{
			pMatan[i]= new Point();
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


    public Path makePathDash()
    {
        Path p = new Path();

        p.moveTo(4, 0);
        p.lineTo(0, -4);
        p.lineTo(8, -4);
        p.lineTo(12, 0);
        p.lineTo(8, 4);
        p.lineTo(0, 4);
//        p.moveTo(0,10);
//        p.lineTo(10,2);
//        p.lineTo(15,2);
//        p.lineTo(25,10);
//        p.lineTo(25,6);
//        p.lineTo(18,0);
//        p.lineTo(25,-6);
//        p.lineTo(25,-10);
//        p.lineTo(15,-2);
//        p.lineTo(10,-2);
//        p.lineTo(0,-10);
//        p.lineTo(0,-6);
//        p.lineTo(7,0);
//        p.lineTo(0,6);
        return p;
    }

	@Override
	public void Init()
	{
		// 마탄 좌표
		pMatan[0].set(0  ,   0);
		pMatan[1].set(365,   0);
		pMatan[2].set(730,   0);
		pMatan[3].set(0  , 205);
		pMatan[4].set(730, 205);
		pMatan[5].set(0,   410);
		pMatan[6].set(365, 410);
		pMatan[7].set(730, 410);

		// 마탄의 이미지ID (열림)
		IDMatan[0]= R.drawable.obj_thron_open;
		IDMatan[1]= R.drawable.obj_normal_open;
		IDMatan[2]= R.drawable.obj_fire_open;
		IDMatan[3]= R.drawable.obj_normal_open;
		IDMatan[4]= R.drawable.obj_normal_open;
		IDMatan[5]= R.drawable.obj_light_open;
		IDMatan[6]= R.drawable.obj_normal_open;
		IDMatan[7]= R.drawable.obj_ice_open;

		// (닫힘)
		IDMatan_close[0]= R.drawable.obj_thron_close;
		IDMatan_close[1]= R.drawable.obj_normal_close;
		IDMatan_close[2]= R.drawable.obj_fire_close;
		IDMatan_close[3]= R.drawable.obj_normal_close;
		IDMatan_close[4]= R.drawable.obj_normal_close;
		IDMatan_close[5]= R.drawable.obj_light_close;
		IDMatan_close[6]= R.drawable.obj_normal_close;
		IDMatan_close[7]= R.drawable.obj_ice_close;

		// 탄환 (일반)
		IDShot[0]= R.drawable.tan_sting;
		IDShot[1]= R.drawable.tan_basic;
		IDShot[2]= R.drawable.tan_fire;
		IDShot[3]= R.drawable.tan_basic;
		IDShot[4]= R.drawable.tan_basic;
		IDShot[5]= R.drawable.tan_lightning;
		IDShot[6]= R.drawable.tan_basic;
		IDShot[7]= R.drawable.tan_ice;

		// 탄환 (세이빙)
		IDShot_saving[0]= R.drawable.tan_sting_saving01;
		IDShot_saving[1]= R.drawable.tan_basic_saving01;
		IDShot_saving[2]= R.drawable.tan_fire_saving01;
		IDShot_saving[3]= R.drawable.tan_basic_saving01;
		IDShot_saving[4]= R.drawable.tan_basic_saving01;
		IDShot_saving[5]= R.drawable.tan_lightning_saving01;
		IDShot_saving[6]= R.drawable.tan_basic_saving01;
		IDShot_saving[7]= R.drawable.tan_ice_saving01;

		// 좀비 시작 좌표
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

		// 좀비 도착 좌표
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

