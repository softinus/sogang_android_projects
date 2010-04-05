package com.raimsoft.game;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.KeyEvent;

import com.raimsoft.activity.R;
import com.raimsoft.view.GameView;

public class Player {
	
	private GameView view;	// ��
	
	private int x,y;		// ��ġ(X,Y)
	private int wid;		// ��
	private int hei;		// ����
	private int spd=5;		// �ӵ�
	
	private boolean bStop=false;
	
	public int Img_id;		// �̹��� ID
	public int State=0;
	
	private int JumpIdx_Last=10;
	private int JumpIdx_Present=0;
	public int JumpIdx[]={5,4,3,2,1,-1,-2,-3,-4,-5};
	
	/**
	 * �÷��̾� ��ġ�� �ڵ� �߾ӹ�ġ�ϴ� �⺻ ������
	 * @param view : �߾���ġ�� ���� ��
	 */
	public Player(GameView view)
	{
		this.view=view;
		x = (view.getWidth() - wid)/2;
		y = (view.getHeight() - hei)/2;		
		
		Img_id=R.drawable.ms;
	}
	
	/**
	 * �÷��̾� ��ġ�� �������ִ� ������
	 * @param view
	 * @param x : X��, (-1)�̸� �߾ӹ�ġ
	 * @param y : Y��, (-1)�̸� �߾ӹ�ġ
	 */
	public Player (GameView view, int x, int y)
	{
		this.view=view;
		if(x==-1)
		{
			this.x = (view.getWidth() - wid)/2;
		}else{
			this.x=x;
		}
		
		if(y==-1)
		{
			this.y = (view.getHeight() - hei)/2;
		}else{
			this.y=y;	
		}	
	}
	
	/**
	 * ��� ���� �Է��ϴ� ������
	 * @param view
	 * @param x : X��, (-1)�̸� �߾ӹ�ġ
	 * @param y : Y��, (-1)�̸� �߾ӹ�ġ
	 * @param width : �÷��̾� ��
	 * @param height : �÷��̾� ����
	 * @param Image_ID : �÷��̾��� �̹���ID
	 */
	public Player(GameView view, int x, int y, int width, int height, int Image_ID)
	{
		this.view= view;
		this.wid = width;
		this.hei = height;
		
		if(x==-1)
		{
			this.x= (view.getWidth() - wid)/2;
		}else{
			this.x=x;
		}
		
		if(y==-1)
		{
			this.y= (view.getHeight() - hei)/2;
		}else{
			this.y=y;	
		}			
		
		Img_id=Image_ID;
	}
	
	/**
	 * ���� ĳ���Ͱ��� Rect�� �������ش�.
	 * @return ���� Player ��ü�� Rect��
	 */
	public Rect getPlayerForRect()
	{
		Rect rct = new Rect (this.x, this.y, this.x+this.wid, this.y+this.hei);
		return rct;
	}
	
	// ���� Bitmap�� Drawable�� �ٲٸ鼭 �Ⱦ��� �Ǿ���.
	public Rect getStateImgRect()
	{
		Rect rct = new Rect(0,0,this.wid,this.hei);
		if (this.State==KeyEvent.KEYCODE_DPAD_LEFT)
		{
			 rct= new Rect(0,0,this.wid,this.hei);
		}
		
		if (this.State==KeyEvent.KEYCODE_DPAD_RIGHT)
		{
			rct = new Rect(this.wid+1,0,this.wid*2,this.hei);
		}
		return rct;
	}
	
	public void setState(int _state)
	{
		this.State	=_state;
		
		if (this.State==KeyEvent.KEYCODE_DPAD_LEFT)
		{
			this.Img_id= R.drawable.base_char_left;
			view.thread.setImg_Refresh();
		}
		if (this.State==KeyEvent.KEYCODE_DPAD_RIGHT)
		{
			this.Img_id= R.drawable.base_char_right;
			view.thread.setImg_Refresh();
		}
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
		Point p = null;
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

	public void setSpeed(int Speed)
	{
		this.spd= Speed;
	}
	
	public void setStop (boolean r)
	{
		this.bStop= r;
	}
	
	/**
	 * ������ �Է¹޾�  �̵��Ѵ�.
	 * @param dir : ����
	 */
	public void Move(int dir)
	{
		if(dir == KeyEvent.KEYCODE_DPAD_UP)
		{
			this.y-= spd;
			if(y < 0)
				y = 0;
		}
		if(dir == KeyEvent.KEYCODE_DPAD_DOWN)
		{
			this.y+= spd;
			if(y > view.getHeight() - this.hei)
			{
				y = view.getHeight() - this.hei;
			}
		}
		if(dir == KeyEvent.KEYCODE_DPAD_LEFT)
		{
			this.x-= spd;
			if(x < 0)
				x = 0;
		}
		if(dir == KeyEvent.KEYCODE_DPAD_RIGHT)
		{
			this.x+= spd;
			if(x > view.getWidth() - this.wid)
				x = view.getWidth() - this.wid;
		}
	}

	/**
	 * �ѹ� �Է¹��� ������ �� ����.
	 */
	public void MoveAway()
	{
		if(bStop) return;		
		switch(this.State)
		{
		case KeyEvent.KEYCODE_DPAD_UP:
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			this.x-= spd;
			if(x < 0)
				x = 0;
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			this.x+= spd;
			if(x > view.getWidth() - this.wid)
				x = view.getWidth() - this.wid;
			break;
		}
	}
	
	public void JumpAlways()
	{
		this.y+= JumpIdx[JumpIdx_Present];
		if (JumpIdx_Present == JumpIdx_Last)
		{
			JumpIdx_Present=0;
		}
		JumpIdx_Present++;
	}
}
