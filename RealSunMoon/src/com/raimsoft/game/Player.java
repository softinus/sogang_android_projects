package com.raimsoft.game;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.KeyEvent;

import com.raimsoft.activity.R;
import com.raimsoft.view.GameView;

public class Player {
	
	private GameView view;	// 뷰
	
	private int x,y;		// 위치(X,Y)
	private int wid;		// 폭
	private int hei;		// 높이
	private int spd=5;		// 속도
	
	private boolean bStop=false;
	
	public int Img_id;		// 이미지 ID
	public int State=0;
	
	private int JumpIdx_Last=10;
	private int JumpIdx_Present=0;
	public int JumpIdx[]={5,4,3,2,1,-1,-2,-3,-4,-5};
	
	/**
	 * 플레이어 위치는 자동 중앙배치하는 기본 생성자
	 * @param view : 중앙위치가 계산될 뷰
	 */
	public Player(GameView view)
	{
		this.view=view;
		x = (view.getWidth() - wid)/2;
		y = (view.getHeight() - hei)/2;		
		
		Img_id=R.drawable.ms;
	}
	
	/**
	 * 플레이어 위치만 설정해주는 생성자
	 * @param view
	 * @param x : X값, (-1)이면 중앙배치
	 * @param y : Y값, (-1)이면 중앙배치
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
	 * 모든 정보 입력하는 생성자
	 * @param view
	 * @param x : X값, (-1)이면 중앙배치
	 * @param y : Y값, (-1)이면 중앙배치
	 * @param width : 플레이어 폭
	 * @param height : 플레이어 높이
	 * @param Image_ID : 플레이어의 이미지ID
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
	 * 현재 캐릭터값을 Rect로 리턴해준다.
	 * @return 현재 Player 객체의 Rect값
	 */
	public Rect getPlayerForRect()
	{
		Rect rct = new Rect (this.x, this.y, this.x+this.wid, this.y+this.hei);
		return rct;
	}
	
	// 현재 Bitmap을 Drawable로 바꾸면서 안쓰게 되었음.
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
	 * 방향을 입력받아  이동한다.
	 * @param dir : 방향
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
	
	public void SensorMove(Point _val_Pos)
	{
		if (_val_Pos.x > 0 && _val_Pos.x != 0)
		{
			this.setState(KeyEvent.KEYCODE_DPAD_LEFT);
		}else if (_val_Pos.x < 0 && _val_Pos.x != 0) 
		{
			this.setState(KeyEvent.KEYCODE_DPAD_RIGHT);
		}
		
		if(Math.abs(_val_Pos.x) < 10)
		{// 일정 속도 이상의 값이면 무시
			if (((this.x - _val_Pos.x) > 0) && ((this.x - _val_Pos.x) < view.getWidth()-this.wid))
			{// Out of screen check
				this.x -= _val_Pos.x;
			}
		}
		//this.y += _val_Pos.y;
	}

	/**
	 * 한번 입력받은 방향대로 쭉 간다.
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
