package com.raimsoft.matan.util;
/**
 * @author Choi Jun Hyeok (http://raimsoft.com)
 */
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class SpriteBitmap
{
	Bitmap mBitmap;
	Rect CropRect, DestRect;
	FrameManager mFrameMgr= FrameManager.getInstance();
	int width, height;
	int count, SpriteNum;
	int Delay, TotalFrame;

	/**
	 * 생성자, 스프라이트 정보를 설정하거나 초기화 한다.
	 * @param ResourceID : 이미지의 ID
	 * @param _res : Contex에서 얻어온 Resources형
	 * @param _wid : 한 이미지의 가로크기
	 * @param _hei : 한 이미지의 세로크기
	 * @param _SpriteNum : 스프라이트 개수
	 * @param _Delay : 한 이미지의 딜레이 (ms)
	 */
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

	public void SpriteModify (int ResourceID, Resources _res, int _wid, int _hei,
							  int _SpriteNum, int _Delay)
	{
		mBitmap= BitmapFactory.decodeResource(_res, ResourceID);
		width= _wid;
		height= _hei;
		SpriteNum= _SpriteNum;
		Delay= _Delay;
		count= 0;
	}


	/**
	 * 스프라이트 애니메이션을 루프로 돌린다.
	 * @param canvas : 그릴 캔버스
	 * @param DestinationX : 애니메이션을 찍을 X좌표
	 * @param DestinationY : 애니메이션을 찍을 Y좌표
	 */
	public void Animate (Canvas canvas, int DestinationX, int DestinationY)
	{
		RectSetting(DestinationX, DestinationY);
		canvas.drawBitmap(mBitmap, CropRect, DestRect, null);

		if (TotalFramePerDelay())
		{
			++count;
		}



		if (count == SpriteNum) count=0; //스프라이트 루프

	}

	/**
	 *
	 * @param canvas :
	 * @param DestinationX
	 * @param DestinationY : 애니메이션을 찍을 Y좌표
	 */
	/**
	 * 스프라이트 애니메이션을 한번 그리고 끝낸다.
	 * @param canvas : 그릴 캔버스
	 * @param DestinationX : 애니메이션을 찍을 X좌표
	 * @param DestinationY : 애니메이션을 찍을 Y좌표
	 * @return : 반복끝나면=true
	 */
	public boolean AnimateNoLoop(Canvas canvas, int DestinationX, int DestinationY)
	{
		RectSetting(DestinationX, DestinationY);
		canvas.drawBitmap(mBitmap, CropRect, DestRect, null);

		if (TotalFramePerDelay())
		{
			++count;
		}
		if (count == SpriteNum)
			return true; // 반복끝나면 true리턴
		return false;
	}


	/**
	 * SourceRect와 DestinationRect를 설정하는 메소드
	 * @param _destX
	 * @param _destY
	 */
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

	/**
	 * 딜레이를 설정한다.
	 * @return : 딜레이에 해당되면 true, 해당되지 않으면 false
	 */
	private boolean TotalFramePerDelay ()
	{
		TotalFrame= mFrameMgr.TotalFrame;

		if (TotalFrame % Delay == 0)
		{
			return true;
		}
		return false;
	}


	/**
	 * 스프라이트를 제거한다.
	 */
	public void DeleteSprite()
	{
		mBitmap=null;
		CropRect=null;
	}
}
