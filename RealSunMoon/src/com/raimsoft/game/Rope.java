package com.raimsoft.game;

import com.raimsoft.view.GameView;

public class Rope extends GameObject{
	
	public Rope(GameView view, int x, int y, int width, int height, int Image_ID)
	{
		this.x=x;
		this.y=y;
		
		this.wid=width;
		this.hei=height;
		
		this.Img_id= Image_ID;
	}
}
