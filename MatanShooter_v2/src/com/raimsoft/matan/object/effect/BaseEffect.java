package com.raimsoft.matan.object.effect;

import com.raimsoft.matan.object.AbstractGameObject;

public class BaseEffect extends AbstractGameObject
{
	public int nAlpha= 255;

	public BaseEffect(float X, float Y, int IDimage, int Width, int Height)
	{
		super(X, Y, IDimage, Width, Height);
	}

	public BaseEffect(int IDimage, int width, int height)
	{
		super(IDimage, width, height);
	}

}
