package com.raimsoft.matan.object;

import java.util.ArrayList;

import android.content.res.Resources;
import android.util.Log;

import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.info.StageInfo;
import com.raimsoft.matan.motion.IMoving;
import com.raimsoft.matan.util.FPoint;
import com.raimsoft.matan.util.Vector2Calc;

public class Bullet extends AbstractGameObject implements IMoving
{
	public boolean bShooting= false; // 쏘는중?

	private StageInfo info= StageInfo.getInstance();
	private Resources mRes;

	public boolean bImageRefresh= true;
	public int nEffID= R.drawable.tan_basic_eff;
	public int nSoundPlayID= -1;

	private FPoint vMove, vVecNor, vVecVal; // 움직임(결과), 정규화, 벡터값
	private FPoint vStart, vStop; // 시작, 멈춤 포인트

	private int nStepCount= 0;	// 움직임 횟수
	private int nStepMax;		// 총 움직임수
	private int nShootCount= 0; // 현재 탄환 횟수
	public int nShootMax= 0;	// 총 탄환 경로수

	private Vector2Calc calc= new Vector2Calc();

	public ArrayList<BulletEffect> mEffList= new ArrayList<BulletEffect>(); // 이펙트

	public Bullet(float X, float Y, int IDimage, int Width, int Height, Resources _Res)
	{
		super(X, Y, IDimage, Width, Height);

		mRes= _Res;

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
		if (nShootCount==nShootMax-1) return true; // 다 쐈으면 true

		this.IDimage= info.IDShot[info.nShotRoute[nShootCount]]; // 다음이미지ID 대입
		bImageRefresh= true; // 이미지 바꿈
		this.nEffID= info.IDShotEff[info.nShotRoute[nShootCount]];
		nSoundPlayID= info.IDShotSound[info.nShotRoute[nShootCount]];

		this.x= (info.pShotRoute[nShootCount].x); // 다음 루트대입
		this.y= (info.pShotRoute[nShootCount].y); //
		vStart.set(info.pShotRoute[nShootCount].x, info.pShotRoute[nShootCount].y); // 시작좌표 대입
		vStop.set(info.pShotRoute[nShootCount+1].x, info.pShotRoute[nShootCount+1].y); // 끝좌표 대입
		nStepCount= 0; // 걸음수초기화
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

		mEffList.add(new BulletEffect(x,y, nEffID, 25, 25, mRes));
		Log.i("Bullet::Eff_added", "BulletEffectNum= "+mEffList.size());


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
