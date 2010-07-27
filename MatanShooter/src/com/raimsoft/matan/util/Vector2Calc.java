package com.raimsoft.matan.util;


public class Vector2Calc
{
	private static FPoint vec=new FPoint();

	/**
	 * 벡터값 환산
	 * @param _v1 : 첫번째
	 * @param _v2 : 두번째
	 * @return 벡터
	 */
	public static FPoint CalVec (FPoint _v1, FPoint _v2)
	{
		vec.x=_v2.x-_v1.x;
		vec.y=_v2.y-_v1.y;
		return vec;
	}

	/**
	 * 스칼라곱
	 * @param _vec : 스칼라곱의 대상
	 * @param k : 스칼라값
	 * @return : 계산 결과
	 */
	public static FPoint CalScale(FPoint _vec, float k)
	{
		vec.set(k*_vec.x, k*_vec.y);
		return vec;
	}

	/**
	 * 벡터의 크기
	 * @param _vec : 크기를 구할 대상
	 * @return : 크기
	 */
	static float CalLenght (FPoint _vec)
	{
		float len= (float) Math.sqrt( _vec.x*_vec.x + _vec.y*_vec.y);
		return len;
	}

	/**
	 * 벡터의 정규화
	 * @param _vec : 정규화할 대상
	 * @param _VecSize : 크기
	 * @return : 정규화된 값
	 */
	public static FPoint CalVecNormalize (FPoint _vec)
	{
		float len= CalLenght(_vec);
		vec.set(len/vec.x, len/vec.y);

		return vec;
	}

	/**
	 * 벡터의 내적
	 * @param vec1 : 내적할 벡터1
	 * @param vec2 : 내적할 벡터2
	 * @return : 내적된 값(float)
	 */
	public static float Inner (FPoint vec1, FPoint vec2)
	{
		return (vec1.x*vec2.x + vec1.y*vec2.y);
	}

	/**
	 * 외적
	 * @param _vec1
	 * @param _vec2
	 * @return
	 */
	public static FPoint Cross (FPoint _vec1, FPoint _vec2)
	{
		vec.x= _vec1.y * _vec2.y;
		vec.y= _vec2.x - _vec1.x;
		return vec;
	}

	public static float Projection (float _vec1_len, double theta)
	{
		float projection_len;
		projection_len= (float) (_vec1_len * Math.cos(theta));
		return projection_len;
	}
}
