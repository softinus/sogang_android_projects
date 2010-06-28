package com.raimsoft.game;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.raimsoft.view.GameView;

public class GameObject {
	protected GameView view;				// 뷰
	
	protected boolean bStop=	false;		// 멈추어져있나
	protected boolean bLive=	true;		// 살아있나
	
	protected int x,y;						// 위치(X,Y)
	protected int wid;						// 폭
	protected int hei;						// 높이
	
	public int Img_id;						// 이미지 ID
	public Drawable Img_Drawable;			// 이미지 Drawable

	public GameObject(){}					// 기본 생성자
	
	/**
	 * 현재 오브젝트 위치를 Rect값으로 리턴해준다.
	 * @return 현재 Object 객체의 Rect값
	 */
	public Rect getObjectForRect()
	{
		Rect rct = new Rect (this.x, this.y, this.x+this.wid, this.y+this.hei);
		return rct;
	}
	
	/**
	 * 현재 오브젝트 위치의 위와 아래 중 하나를 Rect값으로 리턴해준다.
	 * @param Top : true면 위, false면 아래
	 * @return 현재 Object 객체절반의 Rect값
	 */
	public Rect getObjectForRectHalf(boolean Top)
	{
		Rect rct= null;
		if (Top)
		{
			rct = new Rect (this.x, this.y, this.x+this.wid, this.y+(this.hei/2));
		}else{
			rct = new Rect (this.x, this.y+(this.hei/2), this.x+this.wid, this.y+this.hei);
		}
		return rct;
	}
	
	public void setX(int x)
	{
		this.x=x;
	}
	public void setY(int y)
	{
		this.y=y;
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
	
	public boolean isLive()
	{
		return this.bLive;
	}

	public void SetChangeX(int x)
	{
		this.x += x;
	}
	public void SetChangeY(int y)
	{
		this.y += y;
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

	
	public void setStop (boolean r)
	{
		this.bStop= r;
	}
	

}
