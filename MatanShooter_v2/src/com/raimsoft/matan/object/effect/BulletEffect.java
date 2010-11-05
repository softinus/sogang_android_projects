package com.raimsoft.matan.object.effect;

import android.content.res.Resources;

public class BulletEffect extends BaseEffect
{
	public int nAlpha=255;

	public BulletEffect(float _x, float _y, int IDimage, int Width, int Height, Resources _Res)
	{
		super(IDimage, Width, Height);
		this.DRAWimage= _Res.getDrawable(IDimage);
		this.x= _x;
		this.y= _y;
	}


	public boolean AlphaDrop()
	{
		nAlpha -= 25;

		if(nAlpha <= 0)
			return true;
		return false;
	}
}
