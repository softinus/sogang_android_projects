package com.raimsoft.matan.object;


public class Bullet extends GameObject
{
	public boolean bOpen= false;
	private static int nBulletCount;
	public int nBulletNum; // 자신 마탄의 번호

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

	/**
	 * 마탄의 개수를 세주면서 각 마탄의 번호 할당
	 */
	private void BulletCountAndSetNumber()
	{
		nBulletNum= nBulletCount;
		++nBulletCount;
	}

	/**
	 * 마탄 총개수 정보 초기화 (스테이지 넘김시)
	 */
	public void BulletInfoInit()
	{
		nBulletCount=0;
	}


}
