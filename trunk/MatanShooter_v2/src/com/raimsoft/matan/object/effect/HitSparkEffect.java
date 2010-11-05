package com.raimsoft.matan.object.effect;

import android.content.res.Resources;

public class HitSparkEffect extends BaseEffect
{
	public HitSparkEffect(int IDimage, int width, int height, Resources _Res)
	{
		super(IDimage, width, height);
		this.DRAWimage= _Res.getDrawable(IDimage);
	}
}
