package com.raimsoft.matan.util;

import android.graphics.Point;
/**
 * @author Choi Jun Hyeok (http://raimsoft.com)
 */
public class FPoint
{
	public float x;
	public float y;

	/**
	 * Float형 Point 기본 생성자
	 * @param _x
	 * @param _y
	 */
	public FPoint(float _x, float _y)
	{
		x= _x;
		y= _y;
	}

	/**
	 * 디폴트 생성자
	 */
	public FPoint() {}

	/**
	 * (trace용) String으로 바꿔줌
	 * @return
	 */
	public String FPointtoString()
	{
		String STRtoRes;

		STRtoRes= Float.toString(x) + ", " + Float.toString(y);

		return STRtoRes;
	}


	/**
	 * 기본 java.util.Point 형으로 바꿔줌
	 * @return 소수자리는 삭제됨
	 */
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

	/**
	 * 같은지 확인
	 * @param _x
	 * @param _y
	 * @return
	 */
	public boolean equal(float _x, float _y)
	{
		if (this.x==_x && this.y==_y)
			return true;
		else
			return false;
	}

	public void set(int _x, int _y)
	{
		x= _x;
		y= _y;
	}

	public void set(float _x, float _y)
	{
		x= _x;
		y= _y;
	}


	/**
	 * 마이너스 연산자 오버로딩
	 * @param p : 연산할 FPoint
	 * @return 연산된 값
	 */
	public void Minus(FPoint p)
	{
		this.x -= p.x;
		this.y -= p.y;
	}

	/**
	 * 역수 반환
	 * @return -1곱
	 */
	public FPoint Inverse()
	{
		FPoint pTmp= new FPoint();
		pTmp.x= -1*this.x;
		pTmp.y= -1*this.y;
		return pTmp;
	}
}
