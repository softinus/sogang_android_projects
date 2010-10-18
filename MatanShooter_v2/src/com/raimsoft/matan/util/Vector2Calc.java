package com.raimsoft.matan.util;

/**
 * @author Choi Jun Hyeok (http://raimsoft.com)
 */
public class Vector2Calc
{

	/**
	 * 벡터값 환산
	 * @param _v1 : 첫번째
	 * @param _v2 : 두번째
	 * @return 벡터
	 */
	public FPoint CalVec (FPoint _v1, FPoint _v2)
	{
		FPoint resVecVal=new FPoint();
		resVecVal.x=_v2.x-_v1.x;
		resVecVal.y=_v2.y-_v1.y;
		return resVecVal;
	}

	public int Vector2Step (FPoint vectorValue, FPoint directionVector)
	{
		int res;

		if (vectorValue.x==0 || directionVector.x==0) // x가 0이면
		{
			res=  ( (int) (vectorValue.y / directionVector.y) ); // y로 계산
		}else{
			res=  ( (int) (vectorValue.x / directionVector.x) ); // 아니면 x로 계산
		}
		return res;
	}

	/**
	 * 스칼라곱
	 * @param _vec : 스칼라곱의 대상
	 * @param k : 스칼라값
	 * @return : 계산 결과
	 */
	public FPoint CalScale(FPoint _vec, float k)
	{
		FPoint resVecScale=new FPoint();
		resVecScale.x= _vec.x*k;
		resVecScale.y= _vec.y*k;
		return resVecScale;
	}

	/**
	 * 벡터의 크기
	 * @param _vec : 크기를 구할 대상
	 * @return : 크기
	 */
	public float CalLenght (FPoint _vec)
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
	public FPoint CalVecNormalize (FPoint _vec)
	{
		FPoint resVecNor=new FPoint();
		float len= CalLenght(_vec);
		resVecNor.x= _vec.x/len;
		resVecNor.y= _vec.y/len;

		return resVecNor;
	}

	/**
	 * 벡터의 내적
	 * @param vec1 : 내적할 벡터1
	 * @param vec2 : 내적할 벡터2
	 * @return : 내적된 값(float)
	 */
	public float CalDot (FPoint vec1, FPoint vec2)
	{
		return (vec1.x*vec2.x + vec1.y*vec2.y);
	}

	/**
	 * 라디안 값을 디그리로 변환
	 * @param raidian : 라디안값
	 * @return : 디그리값
	 */
	public static float RadianToDegree(float raidian)
	{
		float degree;
		degree= (float) (raidian*180/Math.PI);
		return degree;
	}



	/**
	 * 외적
	 * @param _vec1
	 * @param _vec2
	 * @return
	 */
//	public static FPoint CalCross (FPoint _vec1, FPoint _vec2)
//	{
//		vec.x= _vec1.y * _vec2.y;
//		vec.y= _vec2.x - _vec1.x;
//		return vec;
//	}

	/**
	 * 투영
	 */
	public float Projection (float _vec1_len, double theta)
	{
		float projection_len;
		projection_len= (float) (_vec1_len * Math.cos(theta));
		return projection_len;
	}
}
