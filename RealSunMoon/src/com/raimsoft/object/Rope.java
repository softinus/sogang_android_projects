package com.raimsoft.object;

import com.raimsoft.view.GameView;

public class Rope extends GameObject{
	
	public boolean bRopeDown=true;
	
	public Rope(GameView view, int x, int y, int width, int height, int Image_ID)
	{
		
		this.x=x;
		
		if (y==-2)
		{
			this.y= -(height);
		}else{
			this.y=y;
		}
		
		
		this.wid=width;
		this.hei=height;
		
		this.Img_id= Image_ID;
	}
	
	public void RopeDown()
	{
		this.y++;
	}
}
