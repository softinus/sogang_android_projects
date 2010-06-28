package com.raimsoft.game;

import android.graphics.Rect;
import android.util.Log;

import com.raimsoft.activity.R;
import com.raimsoft.game.DarkCloud.Light;
import com.raimsoft.view.GameView;

public class BossMonster extends GameObject
{
	public boolean bScratch= false;
	private int nDirection= 1;

	/**
	 * 모든 정보 입력하는 생성자
	 * @param view
	 * @param x : X값, (-1)이면 중앙배치, (-2)이면 랜덤배치
	 * @param y : Y값, (-1)이면 중앙배치
	 * @param width : 몬스터 폭
	 * @param height : 몬스터 높이
	 * @param Image_ID : 몬스터 이미지ID
	 */
	public BossMonster(GameView view, int x, int y, int width, int height, int Image_ID)
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

	public void DirectionSet()
	{
		if (this.x <= 0)
		{
			nDirection=1;
		}
		else if (this.x >= view.getWidth()-94)
		{
			nDirection=2;
		}
	}
	public void MoveToDirection()
	{
		if (nDirection==1)
		{
			x=x+2;
		}
		else if (nDirection==2)
		{
			x=x-2;
		}
	}


	public void CollisionBoss(Rect rct)
	{
		Log.v("Collision", "Call CollisionBoss");
		if (this.getObjectForRectHalf(true).intersect(rct))
		{
			view.thread.mStageMgr.mStage.mPlayer.bCrushed=true;
		}
		else if (this.getObjectForRectHalf(true).intersect(rct))
		{

		}
	}
}
