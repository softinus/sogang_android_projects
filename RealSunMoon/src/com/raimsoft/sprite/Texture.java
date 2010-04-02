package com.raimsoft.sprite;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Texture {
	
	private Bitmap bitmap;
	private Resources res;
	
	/**
	 * 리소스형 생성자
	 * @param _Res_id: 리소스의 아이디
	 * @param _ct: 현재 Context
	 */
	public Texture (int _Res_id, Context _ct)
	{	
		res=_ct.getResources();
		BitmapFactory.Options opts=new BitmapFactory.Options();
		opts.inJustDecodeBounds=true;
		bitmap= BitmapFactory.decodeResource(res, _Res_id, opts);
	}
	
	/**
	 * 내부 파일형 생성자
	 * @param _path: 파일의 경로
	 */
	public Texture (String _path)
	{
		BitmapFactory.Options opts=new BitmapFactory.Options();
		opts.inJustDecodeBounds=true;
		bitmap=BitmapFactory.decodeFile(_path, opts);
	}
	
	/**
	 * 비트맵 크기를 Rect형으로 리턴 
	 * @return 비트맵의 크기
	 */
	public Rect getBitmapSize_ForRect ()
	{
		Rect rct=new Rect(0,0, bitmap.getWidth(), bitmap.getHeight());
		return rct;
	}
	
	/**
	 * @return Texture의 Bitmap
	 */
	public Bitmap getBitmap ()
	{
		return bitmap;
	}
}
