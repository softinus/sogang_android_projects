package com.raimsoft.matan.object;

import android.content.res.Resources;

import com.raimsoft.matan.info.StageInfo;
import com.raimsoft.matan.util.SpriteBitmap;

public class MatanCollisionEffect extends AbstractGameObject
{
	public SpriteBitmap SPRITE;
	private StageInfo info= StageInfo.getInstance();

	protected MatanCollisionEffect(float X, float Y, int IDimage, int Width, int Height, Resources mRes)
	{
		super(X, Y, IDimage, Width, Height);

		SPRITE= new SpriteBitmap(IDimage, mRes, 220,200, 8, info.spdMatanCollisionEffect);
	}

}
