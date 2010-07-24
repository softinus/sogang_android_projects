package com.raimsoft.matan.util;


public class Vector2Calc
{
	private FPoint vec=new FPoint();

	FPoint CalVec (FPoint _v1, FPoint _v2)
	{
		vec.x=_v2.x-_v1.x;
		vec.y=_v2.y-_v1.y;
		return vec;
	}
	float CalVecSize (FPoint _vec)
	{
		float len= (float) Math.sqrt( Math.pow(_vec.x,2) + Math.pow(_vec.y,2));
		return len;
	}
	FPoint CalVecNormalize (FPoint _vec, float _VecSize)
	{
		vec.x= _vec.x/_VecSize;
		vec.y= _vec.y/_VecSize;
		return vec;
	}

	float Inner (FPoint vec1, FPoint vec2)
	{
		return (vec1.x*vec2.x + vec1.y*vec2.y);
	}

	FPoint Cross (FPoint _vec1, FPoint _vec2)
	{
		vec.x= _vec1.y * _vec2.y;
		vec.y= _vec2.x - _vec1.x;
		return vec;
	}

	float Projection (float _vec1_len, double theta)
	{
		float projection_len;
		projection_len= (float) (_vec1_len * Math.cos(theta));
		return projection_len;
	}
}
