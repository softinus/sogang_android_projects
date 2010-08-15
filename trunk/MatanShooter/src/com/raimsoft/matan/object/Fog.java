package com.raimsoft.matan.object;

import com.raimsoft.matan.motion.IMoving;

public class Fog extends GameObject implements IMoving
{

	public Fog(int X, int Y, int IDimage, int Width, int Height) {
		super(X, Y, IDimage, Width, Height);
	}


	@Override
	public void Move(float speed)
	{
		++this.x;
		++this.y;
	}
}
