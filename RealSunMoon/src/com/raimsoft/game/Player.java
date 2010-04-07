package com.raimsoft.game;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;

import com.raimsoft.activity.R;
import com.raimsoft.view.GameView;

public class Player extends GameObject {
	

	private int spd=5;		// 속도
	
	private boolean bStop=false;
	//private boolean bJump=false;
	
	public int State=0;
	
	private int JumpIdx_Last=51;
	private int JumpIdx_Present=0;
	//private int JumpIdx[]={10,9,8,7,6,5,4,3,2,1,0,-1,-2,-3,-4,-5,-6,-7,-8,-9,-10};
	//private int JumpIdx[]={-10,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,10};
	private int JumpIdx2[]={-8,-8,-8,-7,-7,-7,-6,-6,-6,-5,-5,-5,-4,-4,-4,-3,-3,-3,-2,-2,-2,-1,-1,-1,
			0,0,0,0,0,1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,6,6,6,7,7,7,8,8};
	
	
	/**
	 * 플레이어 위치는 자동 중앙배치하는 기본 생성자
	 * @param view : 중앙위치가 계산될 뷰
	 */
	public Player(GameView view)
	{
		this.view=view;
		x = (view.getWidth() - wid)/2;
		y = (view.getHeight() - hei)/2;		
		
		Img_id=R.drawable.nui_jump_left;
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
		if (this.State==_state)	// 이전 State와 비교해서 변경이 없으면 넘어감
			return;
		
		if (_state==KeyEvent.KEYCODE_DPAD_LEFT)
		{
			this.Img_id= R.drawable.nui_jump_left;
			view.thread.setPlayerImg_Refresh();
		}
		if (_state==KeyEvent.KEYCODE_DPAD_RIGHT)
		{
			this.Img_id= R.drawable.nui_jump_right;
			view.thread.setPlayerImg_Refresh();
		}
		this.State	=_state;
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
	
	public void SensorMove(Point _val_)
	{
		
		if (_val_.x < 0 && _val_.x != 0)	// 방향 체크
		{
			this.setState(KeyEvent.KEYCODE_DPAD_LEFT);
		}else if (_val_.x > 0 && _val_.x != 0) 
		{
			this.setState(KeyEvent.KEYCODE_DPAD_RIGHT);
		}
		
		if (((this.x + _val_.x) > 0) && ((this.x + _val_.x) < view.getWidth()-this.wid))
		{// Out of screen check
			this.x += _val_.x;
		}
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
//		if (JumpIdx2[JumpIdx_Present]>0)
//		{
//			bJump=true;
//		}else{
//			bJump=false;
//		}
		
		this.y+= JumpIdx2[JumpIdx_Present];
		//Log.d("Player::y", Float.toString(JumpIdx2[JumpIdx_Present]));
		
		if (JumpIdx_Present == JumpIdx_Last)
		{
			JumpIdx_Present=0;
		}
		JumpIdx_Present++;		
	}
}
