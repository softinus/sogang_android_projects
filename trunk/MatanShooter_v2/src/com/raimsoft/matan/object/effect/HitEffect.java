package com.raimsoft.matan.object.effect;

import android.content.res.Resources;

public class HitEffect extends BaseEffect
{

	public HitEffect(float X, float Y, int IDimage, int Width, int Height, Resources _Res)
	{
		super(X, Y, IDimage, Width, Height);

		this.DRAWimage= _Res.getDrawable(IDimage);
	}

	public boolean AlphaDrop()
	{
		nAlpha -= 15;

		if(nAlpha <= 0)
			return true;
		return false;
	}
}
