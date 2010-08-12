package com.raimsoft.matan.object;

import com.raimsoft.matan.info.Stage1Info;
import com.raimsoft.matan.motion.IMoving;
import com.raimsoft.matan.util.FPoint;
import com.raimsoft.matan.util.Vector2Calc;

public class Shot extends GameObject implements IMoving
{
	public boolean bShooting= false; // 쏘는중?

	private Stage1Info info= Stage1Info.getInstance();

	private FPoint vMove, vVecNor, vVecVal; // 움직임(결과), 정규화, 벡터값
	private FPoint vStart, vStop; // 시작, 멈춤 포인트

	private int nStepCount= 0;	// 움직임 횟수
	private int nStepMax;		// 총 움직임수
	private int nShootCount= 0; // 현재 탄환 횟수
	public int nShootMax= 0;	// 총 탄환 경로수

	private Vector2Calc calc= new Vector2Calc();

	public Shot(float X, float Y, int IDimage, int Width, int Height)
	{
		super(X, Y, IDimage, Width, Height);

		vStart= new FPoint();
		vStop= new FPoint();

		vVecNor= new FPoint();
		vVecVal= new FPoint();
		vMove= new FPoint();
	}


	/**
	 * 경로 변환
	 */
	private boolean InitRoute()
	{
		if (nShootCount==nShootMax-1) return true;

		this.x= (info.pShotRoute[nShootCount].x);
		this.y= (info.pShotRoute[nShootCount].y);
		vStart.set(info.pShotRoute[nShootCount].x, info.pShotRoute[nShootCount].y);
		vStop.set(info.pShotRoute[nShootCount+1].x, info.pShotRoute[nShootCount+1].y);
		nStepCount= 0;
		++nShootCount;
		return false;
	}


	@Override
	public void Move(float speed)
	{

		if (nStepMax == nStepCount) // end
		{
			if(InitRoute()) // 다음 경로 변환 없으면
			{
				bShooting= false; // 끝내고
				InitStep();
				return; // 진행 금지
			}
			nStepMax= 0; // 초기화
		}


		if (nStepMax==0) //
		{
			vVecVal= calc.CalVec(vStart, vStop); // 벡터값 연산

			vVecNor= calc.CalVecNormalize(vVecVal); // 벡터값 정규화
			vMove= calc.CalScale(vVecNor, speed); // 정규화값 스칼라곱

			nStepMax= calc.Vector2Step(vVecVal, vMove); // 총 스텝수 계산
		}

		if (!bShooting) return;

		this.x+= vMove.x;
		this.y+= vMove.y;
		++nStepCount;
	}

	private void InitStep()
	{
		nStepCount= 0;
		nStepMax= 0;
		nShootCount= 0;
	}



}
