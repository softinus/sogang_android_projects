package com.raimsoft.object;

import android.content.res.Resources;
import android.view.MotionEvent;

public class GameButton extends GameObject
{
	private int IDpressimg;
	private boolean bPressed= false;
	private boolean bNormalAlreadySetting= false;
	private boolean bPressedAlreadySetting= false;

	public GameButton(int X, int Y, int Width, int Height, int IDimage,int IDpressImage)
	{
		super(X, Y, IDimage, Width, Height);
		IDpressimg= IDpressImage;
	}

	public boolean ButtonPress(int actionID, int mouseX, int mouseY)
	{
		if(this.getObjectForRect().contains(mouseX, mouseY))
		{
			bPressed= true;
			return true;
		}
		if(actionID == MotionEvent.ACTION_UP)
		{
			bPressed= false;
			return false;
		}
		return false;
	}

	public void ImageRefreshAndBound(Resources _Res)
	{
		if(!bNormalAlreadySetting)
		{
			this.DRAWimage =_Res.getDrawable(IDimage);
			this.DRAWimage.setBounds(this.getObjectForRect());
			bNormalAlreadySetting= true;
		}

		if(bPressed && !bPressedAlreadySetting)
		{
			this.DRAWimage =_Res.getDrawable(IDpressimg);
			this.DRAWimage.setBounds(this.getObjectForRect());
			bPressedAlreadySetting= true;
		}
	}
}
