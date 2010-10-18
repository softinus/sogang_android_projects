package com.raimsoft.matan.object;

import java.util.ArrayList;

import android.content.res.Resources;

import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.info.StageInfo;

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
			}break;
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
			}break;
	//	case BOWLER:
	//		switch (mZombieMgr.List.get(idx).eState)
	//		{
	//		case WALK:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_walk, 4, info.spdZombieWalk, mRes);
	//			break;
	//		case ATTACK:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_attack, 7, info.spdZombieAtt, mRes);
	//			break;
	//		case HIT:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_hit, 1, info.spdZombieHit, mRes);
	//			break;
	//		case DIE:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_die, 8, info.spdZombieDie, mRes);
	//			break;
	//		}
	//	case DANCER:
	//		switch (mZombieMgr.List.get(idx).eState)
	//		{
	//		case WALK:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_walk, 4, info.spdZombieWalk, mRes);
	//			break;
	//		case ATTACK:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_attack, 7, info.spdZombieAtt, mRes);
	//			break;
	//		case HIT:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_hit, 1, info.spdZombieHit, mRes);
	//			break;
	//		case DIE:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_die, 8, info.spdZombieDie, mRes);
	//			break;
	//		}
	//	case DRILLER:
	//		switch (mZombieMgr.List.get(idx).eState)
	//		{
	//		case WALK:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_walk, 4, info.spdZombieWalk, mRes);
	//			break;
	//		case ATTACK:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_attack, 7, info.spdZombieAtt, mRes);
	//			break;
	//		case HIT:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_hit, 1, info.spdZombieHit, mRes);
	//			break;
	//		case DIE:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_die, 8, info.spdZombieDie, mRes);
	//			break;
	//		}
	//	case ANGER:
	//		switch (mZombieMgr.List.get(idx).eState)
	//		{
	//		case WALK:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_walk, 4, info.spdZombieWalk, mRes);
	//			break;
	//		case ATTACK:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_attack, 7, info.spdZombieAtt, mRes);
	//			break;
	//		case HIT:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_hit, 1, info.spdZombieHit, mRes);
	//			break;
	//		case DIE:
	//			mZombieMgr.List.get(idx).Init(R.drawable.ch_zombie1_die, 8, info.spdZombieDie, mRes);
	//			break;
	//		}
		}

		List.get(idx).bImageRefresh= false;
	}
}