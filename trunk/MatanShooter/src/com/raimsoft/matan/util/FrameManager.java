package com.raimsoft.matan.util;

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


	public static FrameManager getInstance()
	{
		return fm;
	}
	public void IncreaseTotalFrame()
	{
		++TotalFrame;
		//Log.d("FrameManager", Float.toString(TotalFrame));
	}

	public static boolean FrameTimer (int Delay)
	{
		if (TotalFrame % Delay==0)
		{
			return true;
		}
		return false;
	}

	public static void CalRealTime(long after, long before)
	{
		RealFrameDelay= after-before;
	}

	public boolean TouchToDealy(int Delay)
	{
		if (LastTouchFrame==0)
		{
			LastTouchFrame= TotalFrame;
			return true;
		}
		if (TotalFrame > LastTouchFrame+Delay)
		{
			return true;
		}
		return false;
	}
}
