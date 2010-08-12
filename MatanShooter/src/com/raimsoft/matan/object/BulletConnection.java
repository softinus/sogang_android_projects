package com.raimsoft.matan.object;

import com.raimsoft.matan.util.FPoint;


public class BulletConnection
{
	//public ArrayList<FPoint> pConnect= new ArrayList<FPoint>();
	public FPoint[] pConnect= new FPoint[10];

	public boolean bDrag= false;
	public boolean bOut= false;
	public int ConnectionNum= 0;
	public int LastConnectBulletNum= -1;

	/**
	 * 배열 모든 인덱스 동적 할당
	 */
	public BulletConnection()
	{
		for (int i=0; i<10; i++)
		{
			pConnect[i]= new FPoint();
		}
	}

	/**
	 * 연결된 선이 추가되었음을 알린다.
	 */
	public void AddConnectionPoint()
	{
		++ConnectionNum;
	}

	/**
	 * 밖으로 나갈 수 있는 경우의 수를 조사한다.
	 * @param BulletNum1 : 시작포인트에 연결된 Bullet의 번호
	 * @param BulletNum2 : 끝포인트에 연결된 Bullet의 번호
	 * @return 나갈 경우 : ture, 나가지 않을 경우 : false
	 */
	public boolean OutCombination(int BulletNum1, int BulletNum2)
	{
		switch(BulletNum1)
		{
		case 0:
			if (BulletNum2==4 || BulletNum2==6 || BulletNum2==7)
				return true;
			return false;
		case 1:
			if (BulletNum2==3 || BulletNum2==4 || BulletNum2==5 || BulletNum2==6 || BulletNum2==7)
				return true;
			return false;
		case 2:
			if (BulletNum2==3 || BulletNum2==5 || BulletNum2==6)
				return true;
			return false;
		case 3:
			if (BulletNum2==1 || BulletNum2==2 || BulletNum2==4 || BulletNum2==6 || BulletNum2==7)
				return true;
			return false;
		case 4:
			if (BulletNum2==0 || BulletNum2==1 || BulletNum2==3 || BulletNum2==5 || BulletNum2==6)
				return true;
			return false;
		case 5:
			if (BulletNum2==1 || BulletNum2==2 || BulletNum2==4)
				return true;
			return false;
		case 6:
			if (BulletNum2==0 || BulletNum2==1 || BulletNum2==2 || BulletNum2==3 || BulletNum2==4)
				return true;
			return false;
		case 7:
			if (BulletNum2==0 || BulletNum2==1 || BulletNum2==3)
				return true;
			return false;
		default:
			return false;
		}
	}
}
