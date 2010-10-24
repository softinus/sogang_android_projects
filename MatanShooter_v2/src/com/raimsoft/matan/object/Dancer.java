package com.raimsoft.matan.object;

import com.raimsoft.matan.info.ZombieNameEnum;
import com.raimsoft.matan.info.ZombieStateEnum;
import com.raimsoft.matan.util.SpriteBitmap;

import android.content.res.Resources;

public class Dancer extends AbstractZombie
{

	/**
	 * 위치에 따라 생성
	 * @param RouteNum : 좀비 경로 번호 (0-~15)
	 * @param IDimage : 이미지 ID
	 * @param Width : 가로길이
	 * @param Height : 세로길이
	 * @param mRes : 리소스
	 */
	public Dancer(int RouteNum, int IDimage, int Width, int Height,	Resources mRes)
	{
		super(RouteNum, IDimage, Width, Height, mRes);

		SPRITE= new SpriteBitmap(IDimage, mRes, 100,100, 4, info.spdZombie3Walk);
		this.eName= ZombieNameEnum.DANCER;
		this.nHP= 50;
		this.nPower= 25;
		this.fSpeed= 5.0f;
	}

	@Override
	public void Move(float speed)
	{
		if (!(eState== ZombieStateEnum.WALK)) return; // 걷는상태에만 걷는다.

		if (nStepMax==0)
		{
			vVecVal= calc.CalVec(vStart, vStop); // 벡터값 연산

			vVecNor= calc.CalVecNormalize(vVecVal); // 벡터값 정규화
			vMove= calc.CalScale(vVecNor, fSpeed*speed); // 정규화값 스칼라곱

			nStepMax= calc.Vector2Step(vVecVal, vMove); // 총 스텝수 계산
		}

		if (nStepMax == nStepCount)
		{
			eState= ZombieStateEnum.ATTACK; // 공격상태로 변경
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
		if (eState==ZombieStateEnum.DIE || eState==ZombieStateEnum.HIT || eState==ZombieStateEnum.AVOID) return;

		if ( (Math.random()*100) <= 25 ) // 25% 확률로 피함
		{
			eOldState= eState;
			eState= ZombieStateEnum.AVOID;
			this.bImageRefresh= true;
			return;
		}

		nHP= nHP - minusHP; // 피해 입음

		if (this.nHP <= 0)
		{
			eState= ZombieStateEnum.DIE; // 죽음 상태로 변경
			this.bImageRefresh= true;
			return;
		}

		eOldState= eState;
		eState= ZombieStateEnum.HIT; // 히트 상태로 변경
		this.bImageRefresh= true;

	}

	@Override
	public void Attack(int damage, int delay)
	{
		// TODO Auto-generated method stub

	}

}
