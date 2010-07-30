package com.raimsoft.matan.object;

import android.content.res.Resources;

import com.raimsoft.matan.info.ZombieStateEnum;
import com.raimsoft.matan.motion.Moveable;
import com.raimsoft.matan.stage.Stage1;
import com.raimsoft.matan.util.FPoint;
import com.raimsoft.matan.util.SpriteBitmap;
import com.raimsoft.matan.util.Vector2Calc;

public class Zombie extends GameObject implements Moveable
{
	public SpriteBitmap SPRITE;
	//private boolean bPositioning= false;
	public boolean bImageRefresh= false;
	public ZombieStateEnum nZombieState= ZombieStateEnum.WALK;

	private FPoint vMove, vVecNor, vVecVal;
	private FPoint vStart, vStop;

	private int nStepCount= 0;
	private int nStepMax;

	private Vector2Calc calc;

	public Zombie(float X, float Y,  FPoint endPoint, int IDimage, int Width, int Height, Resources mRes)
	{
		super(X, Y, IDimage, Width, Height);

		calc= new Vector2Calc();

		SPRITE= new SpriteBitmap(IDimage, mRes, 100,100, 4, 5);

		vMove= new FPoint();
		vVecNor= new FPoint();
		vVecVal= new FPoint();

		vStart= new FPoint(X,Y);

		vStop= new FPoint();
		vStop= endPoint;
	}

	public void Init(int IDimage, int SpriteNum, int Delay, Resources mRes)
	{
		SPRITE= new SpriteBitmap(IDimage, mRes, 100,100, SpriteNum, Delay);
	}


	@Override
	public void Move(float speed)
	{
		if (nZombieState== ZombieStateEnum.ATTACK) return;

		if (nStepMax==0)
		{
			vVecVal= calc.CalVec(vStart, vStop); // 벡터값 연산

			vVecNor= calc.CalVecNormalize(vVecVal); // 벡터값 정규화
			vMove= calc.CalScale(vVecNor, speed); // 정규화값 스칼라곱

			nStepMax= calc.Vector2Step(vVecVal, vMove); // 총 스텝수 계산
		}


		//if (((int)vStop.x) == ((int)this.x) || ((int)vStop.y)==((int)this.y)) // 좀비가 끝지점에 도달
		if (nStepMax == nStepCount)
		{
			nZombieState= ZombieStateEnum.ATTACK; // 공격상태로 변경
			Stage1.bRefreshImg_Zombies= true;
			return;
		}

		this.x+= vMove.x;
		this.y+= vMove.y;
		++nStepCount;
	}
}


