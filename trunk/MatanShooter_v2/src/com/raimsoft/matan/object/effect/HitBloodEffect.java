package com.raimsoft.matan.object.effect;

import android.content.res.Resources;

public class HitBloodEffect extends BaseEffect
{
	public HitBloodEffect(float X, float Y, int IDimage, int Width, int Height, Resources _Res) {
		super(X, Y, IDimage, Width, Height);

		this.DRAWimage= _Res.getDrawable(IDimage);
	}
}
