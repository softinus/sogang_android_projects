package com.raimsoft.matan.object;

import com.raimsoft.matan.motion.Moveable;
import com.raimsoft.matan.util.SpriteBitmap;

public class Zombie extends GameObject implements Moveable
{
	public SpriteBitmap SPRITEwalk;

	public Zombie(int X, int Y, int IDimage, int Width, int Height)
	{
		super(X, Y, IDimage, Width, Height);
	}

	@Override
	public void Move(float speed)
	{
		
	}

}
