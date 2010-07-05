package com.raimsoft.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class SpriteBitmap
{
	Bitmap mBitmap;
	Rect CropRect, DestRect;
	FrameManager mFrameMgr= FrameManager.getInstance();
	int width, height;
	int count, SpriteNum;
	int Delay, TotalFrame;

	public SpriteBitmap(int ResourceID, Resources _res, int _wid, int _hei,
						int _SpriteNum, int _Delay)
	{
		mBitmap= BitmapFactory.decodeResource(_res, ResourceID);
		width= _wid;
		height= _hei;
		SpriteNum= _SpriteNum;
		Delay= _Delay;
		count= 0;

		CropRect= new Rect();
		DestRect= new Rect();
	}


	public synchronized void Animate (Canvas canvas, int DestinationX, int DestinationY)
	{
		if (TotalFramePerDelay())
		{
			++count;
		}

		RectSetting(DestinationX, DestinationY);
		canvas.drawBitmap(mBitmap, CropRect, DestRect, null);

		if (count == SpriteNum) count=0; //스프라이트 반복
	}

	private void RectSetting(int _destX, int _destY)
	{
		CropRect.left	= width*count;
		CropRect.top	= 0;
		CropRect.right	= width*count+width;
		CropRect.bottom	= height;

		DestRect.left	= _destX;
		DestRect.top	= _destY;
		DestRect.right	= _destX+width;
		DestRect.bottom	= _destY+height;
	}

	private boolean TotalFramePerDelay ()
	{
		TotalFrame= mFrameMgr.TotalFrame;

		if (TotalFrame % Delay == 0)
		{
			return true;
		}
		return false;
	}


	public void DeleteSprite()
	{
		mBitmap=null;
		CropRect=null;
	}
}
