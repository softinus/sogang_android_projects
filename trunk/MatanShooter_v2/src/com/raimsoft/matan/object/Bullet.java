package com.raimsoft.matan.object;

import java.util.Vector;

import android.content.res.Resources;

import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.info.StageInfo;
import com.raimsoft.matan.motion.IMoving;
import com.raimsoft.matan.object.effect.BaseEffect;
import com.raimsoft.matan.object.effect.BulletEffect;
import com.raimsoft.matan.object.effect.MatanCollisionEffect;
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

	private int nStepCount= 0;	// 움직임 횟수 (연산된 벡터에서의 진행된 스칼라 카운트)
	private int nStepMax;		// 총 움직임수 (연산된 벡터에서의 총 스칼라 횟수)
	private int nShootCount= 0; // 발사될 횟수
	public 	int nShootMax= 0;	// 총 탄환 경로수 (몇번을 튕겼나)


	private Vector2Calc calc= new Vector2Calc();

	public Vector<BulletEffect> 		m_vEff    =  new Vector<BulletEffect>(); 		 // 마탄 꼬리 이펙트
	public Vector<MatanCollisionEffect> m_vCollEff=  new Vector<MatanCollisionEffect>(); // 마탄효과 이펙트
	public Vector<BaseEffect>	m_vHitEff =  new Vector<BaseEffect>(); 			 // hit effect

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
		if (nShootCount == nShootMax-1) return true; // 다 쐈으면 true

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


		this.MatanEffectAdd();	// 마탄 충돌 이펙트 추가

		return false;
	}

	private void InitStep()
	{
		nStepCount= 0;
		nStepMax= 0;
		nShootCount= 0;
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

		m_vEff.add(new BulletEffect(x,y, nEffID, 25, 25, mRes));
		//Log.i("Bullet::Eff_added", "BulletEffectNum= "+mEffList.size());


		if (!bShooting) return;


		this.x+= vMove.x;
		this.y+= vMove.y;
		++nStepCount;
	}



	private void MatanEffectAdd()
	{
		if (nShootCount == 0) return; // 0번째이면 마탄 충돌 이펙트 추가 안함

		switch( info.nShotRoute[nShootCount-1] ) // 부딪힌 마탄에 따라 다른 이펙트 생성
		{
		case 0:
			m_vCollEff.add(new MatanCollisionEffect(this.x - 110,this.y - 100
					,R.drawable.eff_spark_sting, 220,200, mRes));
			break;
		case 1:
			m_vCollEff.add(new MatanCollisionEffect(this.x - 110,this.y - 100
					,R.drawable.eff_spark_basic, 220,200, mRes));
			break;
		case 2:
			m_vCollEff.add(new MatanCollisionEffect(this.x - 110,this.y - 100
					,R.drawable.eff_spark_fire, 220,200, mRes));
			break;
		case 3:
			m_vCollEff.add(new MatanCollisionEffect(this.x - 110,this.y - 100
					,R.drawable.eff_spark_basic, 220,200, mRes));
			break;
		case 4:
			m_vCollEff.add(new MatanCollisionEffect(this.x - 110,this.y - 100
					,R.drawable.eff_spark_basic, 220,200, mRes));
			break;
		case 5:
			m_vCollEff.add(new MatanCollisionEffect(this.x - 110,this.y - 100
					,R.drawable.eff_spark_lightning, 220,200, mRes));
			break;
		case 6:
			m_vCollEff.add(new MatanCollisionEffect(this.x - 110,this.y - 100
					,R.drawable.eff_spark_basic, 220,200, mRes));
			break;
		case 7:
			m_vCollEff.add(new MatanCollisionEffect(this.x - 110,this.y - 100
					,R.drawable.eff_spark_ice, 220,200, mRes));
			break;
		}

	}


}
