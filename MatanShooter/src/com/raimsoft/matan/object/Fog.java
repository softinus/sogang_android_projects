package com.raimsoft.matan.object;

public class Fog extends GameObject
{

	public Fog(int X, int Y, int IDimage, int Width, int Height) {
		super(X, Y, IDimage, Width, Height);
	}

	/**
	 * 안개가 움직인다.
	 */
	public void MoveTest()
	{
		++this.x;
		++this.y;
	}
}
