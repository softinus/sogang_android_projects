package com.raimsoft.matan.object;

import java.util.ArrayList;

public class ZombieManager
{
	public ArrayList<Zombie> List;
	private int nClosest= -1;

	public ZombieManager()
	{
		List= new ArrayList<Zombie>();
	}

	/**
	 * 가장 앞에 있는 좀비
	 * @return 가장 가까이 있는 좀비의 번호
	 */
	public int ClosestZombieNum()
	{
		for (int i=0; i<List.size(); i++)
			if (nClosest < List.get(i).nRoute)
				nClosest= List.get(i).nRoute;
		return nClosest;
	}
}