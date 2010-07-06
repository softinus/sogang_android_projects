package com.raimsoft.util;


public class FrameManager
{
	private static FrameManager fm= new FrameManager();	// 싱글톤

	public int TotalFrame=0;

	public static FrameManager getInstance()
	{
		return fm;
	}
	public void IncreaseTotalFrame()
	{
		++TotalFrame;
		//Log.d("FrameManager", Float.toString(TotalFrame));
	}

	public void CalcFPS()
	{

	}

}
