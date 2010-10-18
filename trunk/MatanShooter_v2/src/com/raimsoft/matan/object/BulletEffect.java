package com.raimsoft.matan.object;

import android.content.res.Resources;

public class BulletEffect extends AbstractGameObject
{
	public int nAlpha=255;

	protected BulletEffect(float _x, float _y, int IDimage, int Width, int Height, Resources _Res)
	{
		super(IDimage, Width, Height);
		this.DRAWimage= _Res.getDrawable(IDimage);
		this.x= _x;
		this.y= _y;
	}


	public boolean AlphaDrop()
	{
		nAlpha -= 15;

		if(nAlpha <= 0)
			return true;
		return false;
	}
}
