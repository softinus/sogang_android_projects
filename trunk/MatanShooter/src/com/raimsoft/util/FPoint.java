package com.raimsoft.util;

public class FPoint
{
	public Float x;
	public Float y;

	public String FPointtoString()
	{
		String STRtoRes;

		STRtoRes= x.toString() + ", " + y.toString();

		return STRtoRes;
	}
}
