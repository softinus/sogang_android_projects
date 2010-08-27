package com.raimsoft.matan.object;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class HPbar extends AbstractGameObject
{
	private Rect rBarDst;
	public int IDimageBAR;
	public Drawable DRAWimageBAR;

	/**
	 * HP바 생성자
	 * @param X : X좌표
	 * @param Y : Y좌표
	 * @param IDimageBAR : 바 이미지
	 * @param IDimageSKIN : 껍데기 이미지
	 * @param Width : 가로
	 * @param Height : 세로
	 */
	HPbar(float X, float Y, int IDimageBAR, int IDimageSKIN, int Width, int Height)
	{
		super(X, Y, IDimageSKIN, Width, Height);

		this.IDimageBAR= IDimageBAR;

		rBarDst= new Rect();
	}

	/**
	 * HP바 사각형 설정
	 * @param presentHP : 현재 HP
	 * @param totalHP : 총 HP
	 */
	public Rect getRectBar4Rect(int presentHP, int totalHP)
	{
		float barSize= 62*((float)presentHP/(float)totalHP);

		rBarDst.set((int)x+7, (int)y, (int)x+7+(int)barSize, (int)y+11);

		return rBarDst;
	}
}
