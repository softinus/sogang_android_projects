package com.raimsoft.util;

import android.graphics.Point;

public class FPoint
{
	public float x;
	public float y;

	public String FPointtoString()
	{
		String STRtoRes;

		STRtoRes= Float.toString(x) + ", " + Float.toString(y);

		return STRtoRes;
	}

	public Point ConvertToPoint()
	{
		Point point= new Point((int)x, (int)y);
		return point;
	}

	public void setPoint(Point point)
	{
		this.x= point.x;
		this.y= point.y;
	}
}
