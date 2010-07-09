package com.raimsoft.util;

import android.graphics.Point;

public class FPoint
{
	public float x;
	public float y;

	public FPoint(float _x, float _y)
	{
		x= _x;
		y= _y;
	}

	public FPoint() {}

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
	public void setFPoint(float _x, float _y)
	{
		x= _x;
		y= _y;
	}
	public static FPoint PointToFPoint(Point _point)
	{
		FPoint tmp=new FPoint(_point.x, _point.y);
		return tmp;
	}

	public boolean equal(float _x, float _y)
	{
		if (this.x==_x && this.y==_y)
			return true;
		else
			return false;
	}
}
