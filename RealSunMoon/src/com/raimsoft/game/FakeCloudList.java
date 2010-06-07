package com.raimsoft.game;

import java.util.ArrayList;

import android.util.Log;

import com.raimsoft.activity.R;
import com.raimsoft.view.GameView;

public class FakeCloudList
{
	public ArrayList<FakeCloud> fakeList= new ArrayList<FakeCloud>();
	fakeCase fakecase= new fakeCase();
	GameView view;
	int commonW, commonH, commonImgID;
	public int LastFakePos= 1920;
	int RndFakePos= 0;
	static int fakeCnt= 0;

	public FakeCloudList(GameView _view, int _width, int _height, int _imgID)
	{
		commonW= _width;
		commonH= _height;
		commonImgID=_imgID;
	}

	public FakeCloudList(GameView _view)
	{
		view= _view;
	}

	public void CreateFakes()
	{
		if (view.thread.mStageMgr.mStage.BackSize > (LastFakePos-RndFakePos-10) &&
			view.thread.mStageMgr.mStage.BackSize < (LastFakePos-RndFakePos+10))
		{
			fakecase.RandomCaseFakeSel();

			RndFakePos= (int) (100+(Math.random()*100)); //100~200

			fakeList.add(new FakeCloud(view, -2,0, fakecase.wid,fakecase.hei, fakecase.imgID));

			for (int i=0; i<fakeList.size(); i++)
			{
				Log.v("FakeList", "FakeCloud List Added." +
						"X="+ Float.toString(fakeList.get(i).x) +
					  ", Y="+ Float.toString(fakeList.get(i).y) );
				view.thread.mStageMgr.mStage.setFakeImg_Refresh(); // 아이템 갱신
			}

			Log.i("FakeList", "LastFakePos"+Float.toHexString(LastFakePos));
			Log.i("FakeList", "RndFakePos"+Float.toHexString(RndFakePos));

			LastFakePos -= RndFakePos;
		}
	}

	public void setAllChangeY (int _y)
	{
		for (int i=0; i<fakeList.size(); i++)
		{
			fakeList.get(i).y += _y;
		}
	}
}


class fakeCase // 랜덤으로 아이템을 뽑아주는 클래스
{
	int wid, hei, imgID;

	private int rndFreq, fake1_PercentFreq, fake2_PercentFreq;
	//private final int ITEM_CASE= 2;
	private final int ITEM1_FREQ= 1;	// ITEM1이 나올 빈도
	private final int ITEM2_FREQ= 0;	// ITEM1이 나올 빈도

	void RandomCaseFakeSel()
	{
		fake1_PercentFreq= ITEM1_FREQ/(ITEM1_FREQ+ITEM2_FREQ) * 100; //
		fake2_PercentFreq= ITEM2_FREQ/(ITEM1_FREQ+ITEM2_FREQ) * 100; // 각각 백분율로 환산

		rndFreq= (int) (1+(Math.random()*100)); // 1~100

		if (rndFreq > 100-fake1_PercentFreq)
		{
			wid= 80;
			hei= 30;
			imgID= R.drawable.fakecloud3;
		}
		else if (rndFreq <= fake2_PercentFreq )
		{

		}



	}
}