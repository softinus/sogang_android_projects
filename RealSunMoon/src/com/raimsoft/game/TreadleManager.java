package com.raimsoft.game;

import android.util.Log;

import com.raimsoft.activity.R;
import com.raimsoft.view.GameView;

public class TreadleManager {

	private class TreadleInfo
	{
		int width;
		int height;
		int imgID;
		
		int treadleCase;
		
		void Init(int _wid, int _hei, int imgID)
		{
			this.width= _wid;
			this.height= _hei;
			this.imgID= imgID;
		}
		
		TreadleInfo setInfoToCase(int _treadleCase)
		{
			int RandomCase= (int) Math.round(Math.random() * _treadleCase);
			
			switch (RandomCase)
			{
			case 0:
				this.Init(30, 18, R.drawable.cloud1_1);
				return this;
			case 1:
				this.Init(42, 25, R.drawable.cloud1_2);
				return this;
			case 2:
				this.Init(60, 30, R.drawable.cloud1_3);
				return this;
			case 3:
				this.Init(80, 30, R.drawable.cloud1_4);
				return this;
			default:
				this.Init(80, 30, R.drawable.cloud1_4);
				return this;
			}
		}
		
	}
	
	
	private GameView view;
	public Treadle[] treadle;
	TreadleInfo tInfo=new TreadleInfo();
//	private final int GAP_less=40;
//	private final int GAP_top=120;
	private final int GAP=	100;
	private int treadle_cnt;
	
	/**
	 * 생성자
	 * @param _view
	 */
	public TreadleManager(GameView _view)
	{
		this.view= _view;
		
		treadle_cnt= Math.round(view.thread.BackSize*3 / GAP);
		treadle= new Treadle[treadle_cnt];
		tInfo=new TreadleInfo();
		
		this.TreadleCreate();
	}
	

	
	
	public void TreadleCreate()
	{
		for (int i=0; i<treadle_cnt; i++)
		{
			if (i==this.getCount()-1)
			{
				treadle[i]= new Treadle(view, -1, -(GAP*(i-3))
					,80, 30, R.drawable.cloud2_4, i);
				
				Log.d("Treadles Pos DEBUG",
						"X= "+Float.toString(treadle[i].getX())
					+", Y= "+Float.toString(treadle[i].getY()));
				
				return;
			}
			
			tInfo.setInfoToCase(3);	// 4종류의 구름 (0~3)
			treadle[i]= new Treadle(view, -2, -(GAP*(i-3))
					, tInfo.width, tInfo.height, tInfo.imgID, i);
			
			
			
			// -((GAP*(i-3))
			// (int) -((Math.random()*(GAP_top-GAP_less)+GAP_less)*(i-5))
			
			Log.d("Treadles Pos DEBUG",
					"X= "+Float.toString(treadle[i].getX())
				+", Y= "+Float.toString(treadle[i].getY()));
		}
	}
	
	public void ALL_Treadle_Stepped()
	{
		for (int i=0; i<treadle_cnt; i++)
		{
			treadle[i].Treadle_Stepped();
		}
	}
	
	public int getCount()
	{
		return treadle_cnt;
	}
	
	public void setAllChangeY (int _y)
	{
		for (int i=0; i<treadle_cnt; i++)
		{
			treadle[i].y += _y;
		}
	}
	
}
