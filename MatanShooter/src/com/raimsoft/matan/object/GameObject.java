package com.raimsoft.matan.object;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public abstract class GameObject
{
	public float   x, y;
	public int	 IDimage;
	public int   Width, Height;
	public Drawable DRAWimage;

	public GameObject(float X, float Y, int IDimage, int Width, int Height)
	{
		this.x = X;
		this.y = Y;
		this.IDimage = IDimage;
		this.Width = Width;
		this.Height = Height;
	}

	/**
	 * 현재 오브젝트 위치를 Rect형으로 리턴해준다.
	 * @return 현재 Object의 Rect값
	 */
	public Rect getObjectForRect()
	{
		Rect rct = new Rect ((int)x, (int)y, (int)x+Width, (int)y+Height);
		return rct;
	}

	/**
	 * 현재 오브젝트의 중점을 Point형으로 리턴해준다.
	 * @return 현재 Object의 중점
	 */
	public Point getObjectMiddleSpot()
	{
		Point point= new Point ((int)x+Width/2, (int)y+Height/2);
		return point;
	}

}
