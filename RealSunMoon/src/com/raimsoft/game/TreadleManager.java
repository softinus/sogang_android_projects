package com.raimsoft.game;

import android.util.Log;

import com.raimsoft.activity.R;
import com.raimsoft.view.GameView;

public class TreadleManager {

	//private List<Treadle> ArrList=new ArrayList<Treadle> ();
	
	private GameView view;
	public Treadle[] treadle;
	private final int GAP=	120;
	private int treadle_cnt;
	
	public TreadleManager(GameView _view)
	{
		this.view= _view;
		
		treadle_cnt= Math.round(view.thread.BackSize / GAP);
		
		treadle= new Treadle[treadle_cnt];
		
		this.TreadleCreate();
	}
	public void TreadleCreate()
	{
		for (int i=0; i<treadle_cnt; i++)
		{
			treadle[i]= new Treadle(view, -2, -(GAP*(i-3)), 65, 35, R.drawable.treadle_cloud_4);

			
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
	
}
