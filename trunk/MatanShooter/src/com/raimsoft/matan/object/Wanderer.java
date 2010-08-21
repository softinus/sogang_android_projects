package com.raimsoft.matan.object;

import android.content.res.Resources;

import com.raimsoft.matan.info.ZombieStateEnum;

public class Wanderer extends Zombie
{

	public Wanderer(int RouteNum, int IDimage, int Width, int Height, Resources mRes)
	{
		super(RouteNum, IDimage, Width, Height, mRes);
		this.nHP= 50;
		this.nPower= 5;
	}

	@Override
	public void Move(float speed)
	{
		if (!(nZombieState== ZombieStateEnum.WALK)) return; // 걷는상태에만 걷는다.

		if (nStepMax==0)
		{
			vVecVal= calc.CalVec(vStart, vStop); // 벡터값 연산

			vVecNor= calc.CalVecNormalize(vVecVal); // 벡터값 정규화
			vMove= calc.CalScale(vVecNor, speed); // 정규화값 스칼라곱

			nStepMax= calc.Vector2Step(vVecVal, vMove); // 총 스텝수 계산
		}

		if (nStepMax == nStepCount)
		{
			nZombieState= ZombieStateEnum.ATTACK; // 공격상태로 변경
			this.bImageRefresh= true;
			return;
		}

		this.x+= vMove.x;
		this.y+= vMove.y;
		++nStepCount;
	}

	@Override
	public void Damage(int minusHP, int delay)
	{
		if (nZombieState==ZombieStateEnum.DIE || nZombieState==ZombieStateEnum.HIT) return;

		nHP -= minusHP;

		if (this.nHP <= 0)
		{
			nZombieState= ZombieStateEnum.DIE; // 히트상태로 변경
			this.bImageRefresh= true;
			return;
		}

		nOldState= nZombieState;
		nZombieState= ZombieStateEnum.HIT; // 히트상태로 변경
		this.bImageRefresh= true;
	}

	@Override
	public void Attack(int damage, int delay)
	{

	}
}
