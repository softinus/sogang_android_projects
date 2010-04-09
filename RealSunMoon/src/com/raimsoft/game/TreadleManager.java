package com.raimsoft.game;

import com.raimsoft.activity.R;
import com.raimsoft.view.GameView;

public class TreadleManager {

	//private List<Treadle> ArrList=new ArrayList<Treadle> ();
	
	private GameView view;
	public Treadle[] treadle;
	private final int GAP=	80;
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
			treadle[i]= new Treadle(view, -2, GAP*(i+1), 105, 55, R.drawable.treadle_cloud);
		}
	}
	
	public int getCount()
	{
		return treadle_cnt;
	}
	
	
	
}
