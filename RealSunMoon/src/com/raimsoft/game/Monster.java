package com.raimsoft.game;

import android.os.Build;

import com.raimsoft.view.GameView;

public class Monster extends GameObject {

	private final int spd=4;
	
	
	/**
	 * 모든 정보 입력하는 생성자
	 * @param view
	 * @param x : X값, (-1)이면 자동배치
	 * @param y : Y값, (-1)이면 자동배치
	 * @param width : 이미지 폭
	 * @param height : 이미지 높이
	 * @param Image_ID : 몬스터의 이미지ID
	 */
	public Monster(GameView view, int x, int y, int width, int height, int Image_ID)
	{
		this.view= view;
		this.wid = width;
		this.hei = height;
		
		if(x==-1)
		{
			this.x= 380;
		}else{
			this.x=x;
		}
		
		if(y==-1)
		{
			this.y= -hei;
		}else{
			this.y=y;	
		}			
		
		Img_id=Image_ID;
	}
	
	public void Move_Tiger()
	{
		this.y+=spd;
	}
	
	public void Move_Bird()
	{
		if (view.thread.cnt_Step>5)
		{
			this.x-=spd;
			this.y+=spd;
		}
	}
	
}
