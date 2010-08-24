package com.raimsoft.matan.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.raimsoft.matan.util.FrameManager;

/**
 * @author Choi Jun Hyeok (http://raimsoft.com)
 */
public class GameTimer
{
	public static long TimeLimit= 0;
	public static boolean bStart= false;
	private Paint PAINTtimer= new Paint();

	private long TimeSec;
	private long min;
	private long sec;

	//private SimpleDateFormat FORMAT = new SimpleDateFormat ("ss");
	//private DecimalFormat FORMAT = new DecimalFormat();

	public GameTimer()
	{
		PAINTtimer.setColor(Color.MAGENTA);
		PAINTtimer.setTextSize(18.0f);
		PAINTtimer.setAntiAlias(true);
		//FORMAT.applyPattern("##");
	}

	/**
	 * 타이머 셋팅
	 * @param sec
	 */
	public void setTimer(long sec)
	{
		TimeLimit= 1000*sec;
		bStart= true;
	}

	/**
	 * 시간 업데이트
	 */
	public void Update()
	{
		if(!bStart) return;
		TimeLimit -= FrameManager.RealFrameDelay;
	}

	/**
	 * 시간을 보여준다.
	 * @param canvas
	 * @param x
	 * @param y
	 */
	public void ShowTimer(Canvas canvas, int x, int y)
	{
		canvas.drawText(this.toStringMin(), x, y, PAINTtimer);
	}

//	/**
//	 * 밀리초단위로 반환
//	 * @return [밀리초]
//	 */
//	private String toStringMillSec()
//	{
//		return Long.toString(TimeLimit);
//	}
//
//	/**
//	 * 초단위로 반환
//	 * @return [초]
//	 */
//	private String toStringSec()
//	{
//		TimeSec= TimeLimit / 1000;
//		return Long.toString(TimeSec);
//	}

	/**
	 * 분단위로 반환
	 * @return [분]:[초]
	 */
	private String toStringMin()
	{
		TimeSec= TimeLimit / 1000;
		min= TimeSec/60;
		sec= TimeSec%60;

		return Long.toString(min)+"'"+ Long.toString(sec)+"\"";
	}

}
