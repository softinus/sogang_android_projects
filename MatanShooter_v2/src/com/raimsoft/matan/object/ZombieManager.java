package com.raimsoft.matan.object;

import java.util.ArrayList;

import android.content.res.Resources;

import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.info.StageInfo;
import com.raimsoft.matan.info.ZombieNameEnum;

public class ZombieManager
{
	public ArrayList<AbstractZombie> List;
	private Resources mRes;
	private StageInfo info;
	private int nClosestStepCount= -1;
	public int nClosestRouteNum= -1;
	public int nClosestListNum= -1;

	public ZombieManager(Resources _Res)
	{
		mRes= _Res;
		info= StageInfo.getInstance();
		List= new ArrayList<AbstractZombie>();
	}

	/**
	 * 가장 앞에 있는 좀비
	 * @return 가장 가까이 있는 좀비의 경로번호
	 */
	public int ClosestZombieNum()
	{
		for (int i=0; i<List.size(); i++)
		{
			if (nClosestStepCount < List.get(i).nStepCount)
			{
				nClosestListNum= i;
				nClosestStepCount= List.get(i).nStepCount;
				nClosestRouteNum= List.get(i).nRoute;
			}
		}
		return nClosestRouteNum;
	}

	/**
	 * 스테이지에 따른 좀비를 랜덤으로 불러옵니다.
	 * @param _StageNum 1~3
	 */
	public void AddRandomZombie(int _StageNum)
	{
		ZombieNameEnum eZombieKind= GetRandomKindOfZombie( _StageNum );

		switch( eZombieKind )
		{
		case WANDERER:
			List.add(new Wanderer((int) (Math.random()*16), R.drawable.ch_zombie1_walk, 100,100, mRes));
			break;
		case GRABBER:
			List.add(new Grabber((int) (Math.random()*16), R.drawable.ch_zombie2_walk, 100,100, mRes));
			break;
		case DANCER:
			List.add(new Dancer((int) (Math.random()*16), R.drawable.ch_zombie3_walk, 100,100, mRes));
			break;
		case BOWLER:
			List.add(new Bowler((int) (Math.random()*16), R.drawable.ch_zombie4_walk, 100,100, mRes));
			break;
		case DRILLER:
			List.add(new Driller((int) (Math.random()*16), R.drawable.ch_zombie5_walk, 100,100, mRes));
			break;
		case ANGER:
			List.add(new Anger((int) (Math.random()*16), R.drawable.ch_zombie6_walk, 150,150, mRes));
			break;
		}

	}

	/**
	 * 스테이지에 따라 좀비 종류를 랜덤으로 반환함.
	 * @param StageNum : 1~3
	 * @return
	 * 1스테이지일 경우 : 1,2 반환,
	 * 2스테이지일 경우 : 3,4 반환,
	 * 3스테이지일 경우 : 5,6 반환,
	 */
	private ZombieNameEnum GetRandomKindOfZombie(int _StageNum)
	{
		int nZombieKind= (int) (Math.random() * 2) + ((_StageNum-1) * 2);

		switch(nZombieKind)
		{
		case 0:
			return ZombieNameEnum.WANDERER;
		case 1:
			return ZombieNameEnum.GRABBER;
		case 2:
			return ZombieNameEnum.DANCER;
		case 3:
			return ZombieNameEnum.BOWLER;
		case 4:
			return ZombieNameEnum.DRILLER;
		case 5:
			return ZombieNameEnum.ANGER;
		default:
			return ZombieNameEnum.WANDERER;
		}
	}


	/**
	 * 좀비 새로고침
	 */
	public void Refresh_Zombies(int idx)
	{
		switch (List.get(idx).eName)
		{
		case WANDERER:
			switch (List.get(idx).eState)
			{
			case WALK:
				List.get(idx).Init(R.drawable.ch_zombie1_walk, 4, info.spdZombie1Walk, mRes);
				break;
			case ATTACK:
				List.get(idx).Init(R.drawable.ch_zombie1_attack, 7, info.spdZombie1Att, mRes);
				break;
			case HIT:
				List.get(idx).Init(R.drawable.ch_zombie1_hit, 1, info.spdZombie1Hit, mRes);
				break;
			case DIE:
				List.get(idx).Init(R.drawable.ch_zombie1_die, 8, info.spdZombie1Die, mRes);
				break;
			default:
				List.get(idx).Init(R.drawable.ch_zombie1_walk, 4, info.spdZombie1Walk, mRes);
				break;
			}
		break;

		case GRABBER:
			switch (List.get(idx).eState)
			{
			case WALK:
				List.get(idx).Init(R.drawable.ch_zombie2_walk, 6, info.spdZombie2Walk, mRes);
				break;
			case ATTACK:
				List.get(idx).Init(R.drawable.ch_zombie2_attack, 5, info.spdZombie2Att, mRes);
				break;
			case HIT:
				List.get(idx).Init(R.drawable.ch_zombie2_hit, 1, info.spdZombie2Hit, mRes);
				break;
			case DIE:
				List.get(idx).Init(R.drawable.ch_zombie2_die, 8, info.spdZombie2Die, mRes);
				break;
			default:
				List.get(idx).Init(R.drawable.ch_zombie2_walk, 6, info.spdZombie2Walk, mRes);
				break;
			}
		break;

		case DANCER:
			switch (List.get(idx).eState)
			{
			case WALK:
				List.get(idx).Init(R.drawable.ch_zombie3_walk, 4, info.spdZombie3Walk, mRes);
				break;
			case ATTACK:
				List.get(idx).Init(R.drawable.ch_zombie3_attack, 6, info.spdZombie3Att, mRes);
				break;
			case HIT:
				List.get(idx).Init(R.drawable.ch_zombie3_hit, 1, info.spdZombie3Hit, mRes);
				break;
			case DIE:
				List.get(idx).Init(R.drawable.ch_zombie3_die, 7, info.spdZombie3Die, mRes);
				break;
			case AVOID:
				List.get(idx).Init(R.drawable.ch_zombie3_avoid, 3, info.spdZombie3Avoid, mRes);
				break;
			default:
				List.get(idx).Init(R.drawable.ch_zombie3_walk, 4, info.spdZombie3Walk, mRes);
				break;
			}
		break;

		case BOWLER:
			switch (List.get(idx).eState)
			{
			case WALK:
				List.get(idx).Init(R.drawable.ch_zombie4_walk, 6, info.spdZombie4Walk, mRes);
				break;
			case ATTACK:
				List.get(idx).Init(R.drawable.ch_zombie4_attack, 4, info.spdZombie4Att, mRes);
				break;
			case HIT:
				List.get(idx).Init(R.drawable.ch_zombie4_hit, 1, info.spdZombie4Hit, mRes);
				break;
			case DIE:
				List.get(idx).Init(R.drawable.ch_zombie4_die, 7, info.spdZombie4Die, mRes);
				break;
			default:
				List.get(idx).Init(R.drawable.ch_zombie4_walk, 6, info.spdZombie4Walk, mRes);
				break;
			}
		break;

		case DRILLER:
			switch (List.get(idx).eState)
			{
			case WALK:
				List.get(idx).Init(R.drawable.ch_zombie5_walk, 4, info.spdZombie5Walk, mRes);
				break;
			case ATTACK:
				List.get(idx).Init(R.drawable.ch_zombie5_attack, 3, info.spdZombie5Att, mRes);
				break;
			case HIT:
				List.get(idx).Init(R.drawable.ch_zombie5_hit, 1, info.spdZombie5Hit, mRes);
				break;
			case DIE:
				List.get(idx).Init(R.drawable.ch_zombie5_die, 7, info.spdZombie5Die, mRes);
				break;
			default:
				List.get(idx).Init(R.drawable.ch_zombie5_walk, 4, info.spdZombie5Walk, mRes);
				break;
			}
		break;

		case ANGER:
			switch (List.get(idx).eState)
			{
			case WALK:
				List.get(idx).Init(R.drawable.ch_zombie6_walk, 4, info.spdZombie6Walk, mRes);
				break;
			case ATTACK:
				List.get(idx).Init(R.drawable.ch_zombie6_attack, 4, info.spdZombie6Att, mRes);
				break;
			case HIT:
				List.get(idx).Init(R.drawable.ch_zombie6_hit, 1, info.spdZombie6Hit, mRes);
				break;
			case DIE:
				List.get(idx).Init(R.drawable.ch_zombie6_die,7, info.spdZombie6Die, mRes);
				break;
			case BLOCK:
				List.get(idx).Init(R.drawable.ch_zombie6_block,1, info.spdZombie6Block, mRes);
				break;
			default:
				List.get(idx).Init(R.drawable.ch_zombie6_walk, 4, info.spdZombie6Walk, mRes);
				break;
			}
		break;
		}

		List.get(idx).bImageRefresh= false;
	}
}