package com.raimsoft.matan.motion;

import com.raimsoft.matan.util.FPoint;

public class MotionVectors
{

	public final FPoint pDes= new FPoint(400,240);
	public final FPoint pSrc[]=new FPoint[16];

	/*
	좀비 출발시작 위치(캐릭터 크기 100*100통일)
	목적지 : 400,240
	기본마탄튕김 : 220*200
	탄환 : 340*10
	*/
	/**
	 *
	 */
	public MotionVectors()
	{
		for (int i=0; i<16; i++)
		{
			pSrc[i]= new FPoint();
		}
		pSrc[0].set(-100, -100);
		pSrc[1].set(100, -100);
		pSrc[2].set(350, -100);
		pSrc[3].set(600, -100);
		pSrc[4].set(800, -100);
		pSrc[5].set(-100, 20);
		pSrc[6].set(800, 20);
		pSrc[7].set(-100, -190);
		pSrc[8].set(800, 190);
		pSrc[9].set(-100, 360);
		pSrc[10].set(800, 360);
		pSrc[11].set(-100, 480);
		pSrc[12].set(100, 480);
		pSrc[13].set(350, 480);
		pSrc[14].set(600, 480);
		pSrc[15].set(800, 480);
	}
}
