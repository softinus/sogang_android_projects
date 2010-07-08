package com.raimsoft.object;


public class Bullet extends GameObject
{
	public boolean bUsed= false;
	private static int nBulletCount;
	public int nBulletNum;

	/**
	 * 마탄:생성자
	 * @param X : X좌표
	 * @param Y : Y좌표
	 * @param IDimage : 이미지ID
	 * @param Width : 가로크기
	 * @param Height : 세로크기
	 * @param DRAWimage : 이미지ID를 통한 Drawable객체
	 */
	public Bullet(int X, int Y, int IDimage, int Width, int Height)
	{
		super(X, Y, IDimage, Width, Height);
		BulletCountAndSetNumber();
	}

	private void BulletCountAndSetNumber()
	{
		nBulletNum= nBulletCount;
		++nBulletCount;
	}

	public void BulletInfoInit()
	{
		nBulletCount=0;
	}


}
