package com.raimsoft.game;

import android.graphics.Point;
import android.graphics.Rect;

import com.raimsoft.view.GameView;

public class GameObject {
	protected GameView view;	// 뷰
	
	protected int x,y;		// 위치(X,Y)
	protected int wid;		// 폭
	protected int hei;		// 높이
	
	public int Img_id;		// 이미지 ID
	

	public GameObject()
	{
		
	}
	

	
	/**
	 * 현재 오브젝트 위치를 Rect값으로 리턴해준다.
	 * @return 현재 Player 객체의 Rect값
	 */
	public Rect getPlayerForRect()
	{
		Rect rct = new Rect (this.x, this.y, this.x+this.wid, this.y+this.hei);
		return rct;
	}
	
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return wid;
	}
	public int getHeight()
	{
		return hei;
	}
	
	public Point getPos()
	{
		Point p = new Point();
		p.x=this.x;
		p.y=this.y;
		return p;
	}

	public void SetChangeX(int x)
	{
		this.x += x;
	}
	public void SetChangeY(int y)
	{
		this.x += y;
	}

	public void SetChangePos(int x, int y)
	{
		this.x += x;
		this.y += y;
	}
	
	public void SetPos(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	public void SetPos(Point p)
	{
		this.x=p.x;
		this.y=p.y;
	}
	
	public void SetImage(int Image_ID)
	{
		this.Img_id=Image_ID;
	}
}
