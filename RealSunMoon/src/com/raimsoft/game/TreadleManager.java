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
			switch (_treadleCase)
			{
			case 1:
				this.Init(30, 18, R.drawable.cloud1_1);
				return this;
			case 2:
				this.Init(42, 25, R.drawable.cloud1_2);
				return this;
			case 3:
				this.Init(80, 30, R.drawable.cloud1_3);
				return this;
			default:
				this.Init(80, 30, R.drawable.cloud1_3);
				return this;
			}
		}
		
	}
	
	
	private GameView view;
	public Treadle[] treadle;
	TreadleInfo info;
//	private final int GAP_less=40;
//	private final int GAP_top=120;
	private final int GAP=	110;
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
		
		this.TreadleCreate();
	}
	

	
	
	public void TreadleCreate()
	{
		for (int i=0; i<treadle_cnt; i++)
		{ 
			treadle[i]= new Treadle(view, -2, -(GAP*(i-6)), info.width, info.height, info.imgID);

			
			Log.d("Treadles Pos DEBUG",
					"X= "+Float.toString(treadle[i].getX())
				+", Y= "+Float.toString(treadle[i].getY()));
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
	public TreadleInfo rndImg(int TreadleImgNumber)
	{
		int Treadlecase= (int) Math.round(Math.random()*TreadleImgNumber);
		
		switch (Treadlecase)
		{
		case 1:
			return info.setInfoToCase(1);
		case 2:
			return info.setInfoToCase(2);
		case 3:
			return info.setInfoToCase(3);
		default:
			return info.setInfoToCase(3);
		}
	}
	
}
