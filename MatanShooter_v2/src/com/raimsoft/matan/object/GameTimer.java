package com.raimsoft.matan.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.raimsoft.matan.util.FrameManager;

/**
 * @author Choi Jun Hyeok (http://raimsoft.com)
 */
public class GameTimer
{
	public long TimeLimit= 0;
	public boolean bStart= false;
	//public static boolean bTimeup= false;
	private Paint PAINTtimer= new Paint();
	private Typeface TYPEfont;

	private long TimeSec;
	private long min;
	private long sec;

	public GameTimer(Context mContext)
	{
		TYPEfont= Typeface.createFromAsset(mContext.getAssets(), "fonts/font.ttf");
		PAINTtimer.setColor(Color.MAGENTA);
		PAINTtimer.setTypeface(TYPEfont);
		PAINTtimer.setTextSize(24.0f);
		PAINTtimer.setAntiAlias(true);
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
	 * @return 시작전(false), 진행중(false), 끝남(true)
	 */
	public boolean Update()
	{
		if(!bStart) return false;
		TimeLimit -= FrameManager.RealFrameDelay;

		if (TimeLimit <= 0)
			return true;
		else
			return false;
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

		return Long.toString(min)+"'"+ String.format("%02d", sec)+"\"";
	}

}
