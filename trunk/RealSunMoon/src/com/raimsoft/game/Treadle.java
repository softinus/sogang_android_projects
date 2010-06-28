package com.raimsoft.game;

import com.raimsoft.view.GameView;

public class Treadle extends GameObject {

	public final int uNumber;
	public int Purpose_Y=0;
	public boolean bStepped=false;	// 한 번 밟힌 것 표시
	public boolean bStepped_Pre=false;	// 현재 밟혀있는지 확인
	//public boolean Down_Y=false;
	private int[] SteppedArr= {1,2,3,4,5,-5,-4,-3,-2,-1};	// 밟았을 때 살짝 내려감 (부드럽게)
	private int SteppedArr_PreIdx=0;	// 현재 살짝 내려가는 중의 인덱스
	private int SteppedArr_MaxIdx= SteppedArr.length;
	
	/**
	 * 모든 정보 입력하는 생성자
	 * @param view
	 * @param x : X값, (-1)이면 중앙배치, (-2)이면 랜덤배치
	 * @param y : Y값, (-1)이면 중앙배치
	 * @param width : 플레이어 폭
	 * @param height : 플레이어 높이
	 * @param Image_ID : 플레이어의 이미지ID
	 */
	public Treadle(GameView view, int x, int y, int width, int height, int Image_ID, int treadleNumber)
	{
		this.view= view;
		this.wid = width;
		this.hei = height;
		
		if(x==-1)
		{
			//this.x= (view.getWidth() - wid)/2;
			this.x= 150;
		}else if(x==-2)
		{
			//this.x= (int) (Math.random()* (view.getWidth()-this.wid) );
			this.x= (int) (Math.random()* 250);
		}else{
			this.x=x;
		}
		
		if(y==-1)
		{
			this.y= (view.getWidth() - wid)/2;
		}else{
			this.y=y;
		}		
		
		Img_id=Image_ID;
		
		uNumber=treadleNumber;
		
		//Purpose_Y=y;
	}
	void Treadle_Stepped()
	{
		if (bStepped_Pre)
		{
			this.SetChangeY(this.SteppedArr[SteppedArr_PreIdx++]);
		}
		if (SteppedArr_PreIdx == SteppedArr_MaxIdx)
		{
			bStepped_Pre=false;
			SteppedArr_PreIdx=0;
		}
	}
}
