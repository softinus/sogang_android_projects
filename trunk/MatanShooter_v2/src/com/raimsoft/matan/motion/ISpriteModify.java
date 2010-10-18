package com.raimsoft.matan.motion;

import android.content.res.Resources;

/**
 * 스프라이트 초기화 한다.
 * @param IDimage : 이미지 ID
 * @param SpriteNum : 스프라이트 갯수
 * @param Delay : 스프라이트 딜레이
 * @param mRes : Context의 Resources
 */
public interface ISpriteModify
{
	public void Init(int IDimage, int SpriteNum, int Delay, Resources mRes);
}
