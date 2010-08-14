package com.raimsoft.matan.object;

import android.content.res.Resources;

import com.raimsoft.matan.info.ZombieStateEnum;
import com.raimsoft.matan.motion.IHitting;
import com.raimsoft.matan.motion.IMoving;
import com.raimsoft.matan.util.FPoint;
import com.raimsoft.matan.util.SpriteBitmap;
import com.raimsoft.matan.util.Vector2Calc;

public class Zombie extends GameObject implements IMoving, IHitting
{
	public SpriteBitmap SPRITE;
	//private boolean bPositioning= false;
	public boolean bImageRefresh= false;
	public ZombieStateEnum nZombieState= ZombieStateEnum.WALK;
	public ZombieStateEnum nOldState= ZombieStateEnum.NONE;

	private FPoint vMove, vVecNor, vVecVal;
	private FPoint vStart, vStop;

	private int nStepCount= 0;
	private int nStepMax;

	private int nHP= 50;

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

	/**
	 * 스프라이트 초기화 한다.
	 * @param IDimage : 이미지 ID
	 * @param SpriteNum : 스프라이트 갯수
	 * @param Delay : 스프라이트 딜레이
	 * @param mRes : Context의 Resources
	 */
	public void Init(int IDimage, int SpriteNum, int Delay, Resources mRes)
	{
		SPRITE.SpriteModify(IDimage, mRes, 100,100, SpriteNum, Delay);
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
	public void Damage(int minusHP)
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
}


