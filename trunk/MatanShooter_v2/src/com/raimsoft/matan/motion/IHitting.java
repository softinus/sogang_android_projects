package com.raimsoft.matan.motion;

public interface IHitting {
	/**
	 * 데미지를 입힌다.
	 * @param minusHP 깍일 HitPoint
	 */
	public void Damage(int nMinusHP, int nDelay, int nProperty);
}
