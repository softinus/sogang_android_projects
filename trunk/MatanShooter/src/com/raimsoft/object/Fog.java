package com.raimsoft.object;

public class Fog extends GameObject
{

	public Fog(int X, int Y, int IDimage, int Width, int Height) {
		super(X, Y, IDimage, Width, Height);
	}

	public void MoveTest()
	{
		++this.x;
		++this.y;
	}
}
