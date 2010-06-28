package com.raimsoft.game;

import com.raimsoft.activity.R;
import com.raimsoft.view.GameView;

public class DarkCloud extends GameObject
{
	public class Light extends GameObject
	{
		public int nLightyAlpha= 255;
		private boolean bLightly= false;
		private boolean bLightning= false;



		private Light(GameView view, int x, int y, int width, int height, int Image_ID)
		{
			this.view= view;
			this.wid = width;
			this.hei = height;
			this.x= x;
			this.y= y;
			this.Img_id= Image_ID;
		}
	}


	public Light lt;
	public int State=1; // 0:멈춤, 1:오른쪽, 2:왼쪽
	public boolean bLightningSound= true;		// 천둥 사운드


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

		lt= new Light(this.view, this.x+15, this.y+20, 51,234, R.drawable.lightning);
	}

	public void Lightning(boolean light)
	{
		lt.bLightning= light;
	}
	public boolean getLightning()
	{
		return lt.bLightning;
	}
	public void Lightly(boolean light)
	{
		lt.bLightly= light;
	}
	public boolean getLightly()
	{
		return lt.bLightly;
	}

}