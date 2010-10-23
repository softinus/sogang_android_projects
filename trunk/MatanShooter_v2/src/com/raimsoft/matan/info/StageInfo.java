package com.raimsoft.matan.info;

import android.graphics.Path;
import android.graphics.Point;

import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.util.FPoint;

public class StageInfo implements IGameInfo
{
	private static StageInfo info= new StageInfo();

	public static StageInfo getInstance()
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
	public int IDShotEff[]= new int[8];
	public int IDShotSound[]= new int[8];

	public Point pZombieStart[]= new Point[16]; // 좀비 시작포인트
	public Point pZombieStop[]= new Point[16];  // 좀비 끝포인트

	// 원더러 속도
	public final int spdZombie1Walk=10; // 4
	public final int spdZombie1Att= 4;	// 7
	public final int spdZombie1Hit= 40; // 1
	public final int spdZombie1Die= 3;  // 8

	// 그래버 속도
	public final int spdZombie2Walk=6;  // 6
	public final int spdZombie2Att= 5; 	// 5
	public final int spdZombie2Hit= 40; // 1
	public final int spdZombie2Die= 3;	// 8

	// 댄서 속도
	public final int spdZombie3Walk=10; // 4
	public final int spdZombie3Att= 4;  // 6
	public final int spdZombie3Hit= 40; // 1
	public final int spdZombie3Die= 3;  // 7
	public final int spdZombie3Avoid=8;// 3

	// 볼러 속도
	public final int spdZombie4Walk=6;  // 6
	public final int spdZombie4Att= 6;  // 4
	public final int spdZombie4Hit= 40; // 1
	public final int spdZombie4Die= 3;  // 7

	// 드릴러 속도
	public final int spdZombie5Walk=10; // 4
	public final int spdZombie5Att= 8;  // 3
	public final int spdZombie5Hit= 40; // 1
	public final int spdZombie5Die= 3;  // 7

	public final int spdPartnerShot= 7;
	public final int spdPartnerDie= 4;

	public final float spdAllZombie= 0.3f;
	public final float spdAllBullet= 40.0f;



	protected StageInfo()
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
	public void Init(int nStage)
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

		// 탄환 (이펙트)
		IDShotEff[0]= R.drawable.tan_sting_eff;
		IDShotEff[1]= R.drawable.tan_basic_eff;
		IDShotEff[2]= R.drawable.tan_fire_eff;
		IDShotEff[3]= R.drawable.tan_basic_eff;
		IDShotEff[4]= R.drawable.tan_basic_eff;
		IDShotEff[5]= R.drawable.tan_lightning_eff;
		IDShotEff[6]= R.drawable.tan_basic_eff;
		IDShotEff[7]= R.drawable.tan_ice_eff;

		// (Sound)
		IDShotSound[0]= 100; //R.raw.sfx_shot_sting;
		IDShotSound[1]= 101; //R.raw.sfx_bullet_2;
		IDShotSound[2]= 102; //R.raw.sfx_shot_fire;
		IDShotSound[3]= 101; //R.raw.sfx_bullet_2;
		IDShotSound[4]= 101; //R.raw.sfx_bullet_2;
		IDShotSound[5]= 103; //R.raw.sfx_shot_bolt;
		IDShotSound[6]= 101; //R.raw.sfx_bullet_2;
		IDShotSound[7]= 104; //R.raw.sfx_ice_1;

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

