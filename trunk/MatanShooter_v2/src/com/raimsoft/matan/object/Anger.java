package com.raimsoft.matan.object;

import com.raimsoft.matan.activity.GameActivity;
import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.info.ZombieNameEnum;
import com.raimsoft.matan.info.ZombieStateEnum;
import com.raimsoft.matan.util.SpriteBitmap;

import android.content.res.Resources;

public class Anger extends AbstractZombie
{

	/**
	 * 위치에 따라 생성
	 * @param RouteNum : 좀비 경로 번호 (0-~15)
	 * @param IDimage : 이미지 ID
	 * @param Width : 가로길이
	 * @param Height : 세로길이
	 * @param mRes : 리소스
	 */
	public Anger(int RouteNum, int IDimage, int Width, int Height,	Resources mRes)
	{
		super(RouteNum, IDimage, Width, Height, mRes);

		SPRITE= new SpriteBitmap(IDimage, mRes, 150,150, 4, info.spdZombie6Walk);
		this.eName= ZombieNameEnum.ANGER;
		this.nHP= 200;
		this.nPower= 70;
		this.fSpeed= 1.1f;
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
		if ( eState==ZombieStateEnum.DIE || eState==ZombieStateEnum.HIT || eState==ZombieStateEnum.BLOCK ) return;

		if (nProperty == R.drawable.tan_sting)
		{
			GameActivity.mSound.play(200);
			nHP= nHP - nMinusHP; // 피해 입음

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
		else
		{
			GameActivity.mSound.play(202);
			eOldState= eState;
			eState= ZombieStateEnum.BLOCK;
			this.bImageRefresh= true;
		}
	}

	@Override
	public void Attack(int damage, int delay)
	{
		// TODO Auto-generated method stub

	}

}
