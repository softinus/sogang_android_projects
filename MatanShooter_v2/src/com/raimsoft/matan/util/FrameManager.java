package com.raimsoft.matan.util;

import android.util.Log;

/**
 * @author Choi Jun Hyeok (http://raimsoft.com)
 */
public class FrameManager
{
	private static FrameManager fm= new FrameManager();	// 싱글톤

	public static int TotalFrame=0;				// 총 동작된 프레임수
	public static final long FrameDealy= 1;		// 지정 딜레이 타임 (ms)
	public static final long TouchDelay= 100;	// 터치 딜레이 타임 (ms)
	public static long RealFrameDelay;			// 실제 딜레이 타임 (ms)
	public static long CurrentTime;				// 스레드 동작 시간- 루프돌기전 (ms)
	public static long CurrentAfterTime;		// 스레드 동작 시간- 루프돌고서 (ms)
	public static int LastTouchFrame= 0;		// 마지막으로 터치한 프레임

	public static boolean bPause= false;		// 일시정지


	public static FrameManager getInstance()
	{
		return fm;
	}

	/**
	 * 총 프레임 수 증가
	 */
	public void IncreaseTotalFrame()
	{
		++TotalFrame;
		//Log.d("FrameManager", Float.toString(TotalFrame));
	}

	/**
	 * 딜레이마다 true값 반환
	 * @param Delay : 지연시간 (ms)
	 * @return 해당시간이 올때마다 true
	 */
	public static boolean FrameTimer (int Delay)
	{
		if (TotalFrame % Delay==0)
		{
			return true;
		}
		return false;
	}

	/**
	 * 렌더되는 실제시간을 계산
	 * @param after : 1Loop가 돌기전의 절대시간값
	 * @param before : 1Loop가 돌고나서의 절대시간값
	 */
	public static void CalRealTime(long after, long before)
	{
		RealFrameDelay= after - before;
	}

	/**
	 * 터치 딜레이
	 * @param Delay : 딜레이만큼 터치 안됨
	 * @return
	 */
	public boolean TouchToDealy(int Delay)
	{
		if (LastTouchFrame==0)
		{
			LastTouchFrame= TotalFrame;
			return true;
		}
		if (TotalFrame > LastTouchFrame + Delay)
		{
			return true;
		}
		return false;
	}

	/**
	 * 일시정지 버튼
	 * @return 현재상태 [일시정지(true), 정지상태아님(false)]
	 */
	public static boolean TogglePause()
	{
		if (bPause)
		{
			bPause= false;
			Log.i("FrameManager", "Pause= false");
		}
		else
		{
			bPause= true;
			Log.i("FrameManager", "Pause= true");
		}

		return bPause;
	}
}
