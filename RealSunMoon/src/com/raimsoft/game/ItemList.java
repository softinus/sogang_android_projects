package com.raimsoft.game;

import java.util.ArrayList;

import android.util.Log;

import com.raimsoft.activity.R;
import com.raimsoft.view.GameView;

public class ItemList
{
	public ArrayList<Item> itemList= new ArrayList<Item>();
	ItemCase itemcase= new ItemCase();
	GameView view;
	int commonW, commonH, commonImgID;
	public int LastItemPos= 1920;
	int RndItemPos= 0;
	static int ItemCnt= 0;

	public ItemList(GameView _view, int _width, int _height, int _imgID)
	{
		commonW= _width;
		commonH= _height;
		commonImgID=_imgID;
	}

	public ItemList(GameView _view)
	{
		view= _view;
	}

	public void CreateItems()
	{
		if (view.thread.mStageMgr.mStage.BackSize > (LastItemPos-RndItemPos-8) &&
			view.thread.mStageMgr.mStage.BackSize < (LastItemPos-RndItemPos+8))
		{
			itemcase.RandomCaseItemSel();

			RndItemPos= (int) (300+(Math.random()*200)); //300~500

			itemList.add(new Item(view, -2,0, itemcase.wid,itemcase.hei, itemcase.imgID));

			for (int i=0; i<itemList.size(); i++)
			{
				Log.v("ItemList", "ItemList Added." +
						"X="+ Float.toString(itemList.get(i).x) +
					  ", Y="+ Float.toString(itemList.get(i).y) );
				view.thread.mStageMgr.mStage.setItemImg_Refresh(); // 아이템 갱신
			}

			Log.i("ItemList", "LastItemPos"+Float.toHexString(LastItemPos));
			Log.i("ItemList", "RndItemPos"+Float.toHexString(RndItemPos));

			LastItemPos -= RndItemPos;
		}
	}

	public void setAllChangeY (int _y)
	{
		for (int i=0; i<itemList.size(); i++)
		{
			itemList.get(i).y += _y;
		}
	}
}


class ItemCase // 랜덤으로 아이템을 뽑아주는 클래스
{
	int wid, hei, imgID;

	private int rndFreq, item1_PercentFreq, item2_PercentFreq;
	//private final int ITEM_CASE= 2;
	private final int ITEM1_FREQ= 1;	// ITEM1이 나올 빈도
	private final int ITEM2_FREQ= 0;	// ITEM2가 나올 빈도

	void RandomCaseItemSel()
	{
		item1_PercentFreq= ITEM1_FREQ/(ITEM1_FREQ+ITEM2_FREQ) * 100; //
		item2_PercentFreq= ITEM2_FREQ/(ITEM1_FREQ+ITEM2_FREQ) * 100; // 각각 백분율로 환산

		rndFreq= (int) (1+(Math.random()*99)); // 1~100

		if (rndFreq > 100-item1_PercentFreq)
		{
			wid= 66;
			hei= 21;
			imgID= R.drawable.item_wing;
		}
		else if (rndFreq <= item2_PercentFreq )
		{
			wid= 35;
			hei= 23;
			imgID= R.drawable.trap;
		}



	}
}