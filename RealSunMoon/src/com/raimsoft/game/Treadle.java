package com.raimsoft.game;

import com.raimsoft.view.GameView;

public class Treadle extends GameObject {

	/**
	 * 모든 정보 입력하는 생성자
	 * @param view
	 * @param x : X값, (-1)이면 중앙배치, (-2)이면 랜덤배치
	 * @param y : Y값, (-1)이면 중앙배치
	 * @param width : 플레이어 폭
	 * @param height : 플레이어 높이
	 * @param Image_ID : 플레이어의 이미지ID
	 */
	public Treadle(GameView view, int x, int y, int width, int height, int Image_ID)
	{
		this.view= view;
		this.wid = width;
		this.hei = height;
		
		if(x==-1)
		{
			this.x= (view.getWidth() - wid)/2;
		}else if(x==-2)
		{
			this.x= (int) (Math.random()* (view.getWidth()-this.wid) );
		}else{
			this.x=x;
		}
		
		if(y==-1)
		{
			this.y= (view.getWidth() - wid)/2;
		}else{
			this.y=y;
		}		
		
		Img_id=Image_ID;
	}
}
