package com.raimsoft.game;

import java.util.ArrayList;

import com.raimsoft.activity.R;
import com.raimsoft.view.GameView;

public class ItemList
{
	ArrayList<Item> itemList= new ArrayList<Item>();
	GameView view;
	int commonW, commonH, commonImgID;
	
	public ItemList(GameView _view, int _width, int _height, int _imgID)
	{
		commonW= _width;
		commonH= _height;
		commonImgID=_imgID;
	}
	
	public void CreateItems()
	{
		int cnt= 0;
		
		while(true)
		{
			++cnt;
			itemList.add(new Item(view, -1,-1, 28,30, R.drawable.icon));
			//if (view.thread.mStageMgr.mStage.BackSize > )	break;
		}
		
	}

	
}
