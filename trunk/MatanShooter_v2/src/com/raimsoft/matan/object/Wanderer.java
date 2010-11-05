package com.raimsoft.matan.object;

import android.content.res.Resources;

import com.raimsoft.matan.activity.GameActivity;
import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.info.ZombieNameEnum;
import com.raimsoft.matan.info.ZombieStateEnum;
import com.raimsoft.matan.object.effect.HitEffect;
import com.raimsoft.matan.util.SpriteBitmap;

public class Wanderer extends AbstractZombie
{

	/**
	 * 위치에 따라 생성
	 * @param RouteNum : 좀비 경로 번호 (0-~15)
	 * @param IDimage : 이미지 ID
	 * @param Width : 가로길이
	 * @param Height : 세로길이
	 * @param mRes : 리소스
	 */
	public Wanderer(int RouteNum, int IDimage, int Width, int Height, Resources mRes)
	{
		super(RouteNum, IDimage, Width, Height, mRes);

		SPRITE= new SpriteBitmap(IDimage, mRes, 100,100, 4, info.spdZombie1Walk);
		this.eName= ZombieNameEnum.WANDERER;
		this.nHP= 40;
		this.nPower= 18;
		this.fSpeed= 2.4f;
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
	public void Damage(int nMinusHP, int nDelay, int nProperty)
	{
		if (eState==ZombieStateEnum.DIE || eState==ZombieStateEnum.HIT) return;

		GameActivity.mSound.play(200);

		nHP -= nMinusHP;

		if (this.nHP <= 0)
		{
			eState= ZombieStateEnum.DIE; // 히트상태로 변경
			this.bImageRefresh= true;
			return;
		}

		eOldState= eState;
		eState= ZombieStateEnum.HIT; // 히트상태로 변경
		this.bImageRefresh= true;
	}

	@Override
	public void Attack(int damage, int delay)
	{

	}
}
