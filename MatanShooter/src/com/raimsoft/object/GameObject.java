package com.raimsoft.object;

import android.graphics.drawable.Drawable;

public abstract class GameObject
{
	public int   mX, mY;
	public int	  mIDimage;
	public int   mWidth, mHeight;
	public Drawable mDRAWimage;

	public GameObject(int mX, int mY, int mIDimage, int mWidth, int mHeight, Drawable mDRAWimage)
	{
		this.mX = mX;
		this.mY = mY;
		this.mIDimage = mIDimage;
		this.mWidth = mWidth;
		this.mHeight = mHeight;
		this.mDRAWimage = mDRAWimage;
	}


}
