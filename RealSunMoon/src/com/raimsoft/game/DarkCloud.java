package com.raimsoft.game;

import com.raimsoft.view.GameView;

public class DarkCloud extends GameObject
{
	/**
	 * 모든 정보 입력하는 생성자
	 * @param view
	 * @param x : X값, (-1)이면 중앙배치, (-2)이면 랜덤배치
	 * @param y : Y값, (-1)이면 중앙배치
	 * @param width : 먹구름 폭
	 * @param height : 먹구름 높이
	 * @param Image_ID : 먹구름 이미지ID
	 */
	public DarkCloud(GameView view, int x, int y, int width, int height, int Image_ID)
	{
		this.view= view;
		this.wid = width;
		this.hei = height;

		if(x==-1)
		{
			this.x= 160-wid/2;
		}else if(x==-2)
		{
			this.x= (int) (Math.random()* 250);
		}else{
			this.x=x;
		}

		if(y==-1)
		{
			this.y= 240-hei/2;
		}else{
			this.y=y;
		}

		Img_id=Image_ID;
	}
}