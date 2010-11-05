package com.raimsoft.matan.object.effect;

import android.content.res.Resources;


public class HitBlockEffect extends BaseEffect
{
	public HitBlockEffect(float X, float Y, int IDimage, int Width, int Height, Resources _Res) {
		super(X, Y, IDimage, Width, Height);

		this.DRAWimage= _Res.getDrawable(IDimage);
	}

}
