package com.raimsoft.matan.util;


public class FrameManager
{
	private static FrameManager fm= new FrameManager();	// 싱글톤

	public int TotalFrame=0;

	public static final long DelayTime= 100;	//
	public static long CurrentTime;				//현재 시간
	public static int LastTouchFrame= 0;		//마지막으로 터치한 프레임


	public static FrameManager getInstance()
	{
		return fm;
	}
	public void IncreaseTotalFrame()
	{
		++TotalFrame;
		//Log.d("FrameManager", Float.toString(TotalFrame));
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
