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
	 * ���ҽ��� ������
	 * @param _Res_id: ���ҽ��� ���̵�
	 * @param _ct: ���� Context
	 */
	public Texture (int _Res_id, Context _ct)
	{	
		res=_ct.getResources();
		BitmapFactory.Options opts=new BitmapFactory.Options();
		opts.inJustDecodeBounds=true;
		bitmap= BitmapFactory.decodeResource(res, _Res_id, opts);
	}
	
	/**
	 * ���� ������ ������
	 * @param _path: ������ ���
	 */
	public Texture (String _path)
	{
		BitmapFactory.Options opts=new BitmapFactory.Options();
		opts.inJustDecodeBounds=true;
		bitmap=BitmapFactory.decodeFile(_path, opts);
	}
	
	/**
	 * ��Ʈ�� ũ�⸦ Rect������ ���� 
	 * @return ��Ʈ���� ũ��
	 */
	public Rect getBitmapSize_ForRect ()
	{
		Rect rct=new Rect(0,0, bitmap.getWidth(), bitmap.getHeight());
		return rct;
	}
	
	/**
	 * @return Texture�� Bitmap
	 */
	public Bitmap getBitmap ()
	{
		return bitmap;
	}
}
