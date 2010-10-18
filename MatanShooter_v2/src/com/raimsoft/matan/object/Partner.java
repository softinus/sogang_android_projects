package com.raimsoft.matan.object;

import android.content.res.Resources;

import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.info.PartnerStateEnum;
import com.raimsoft.matan.info.StageInfo;
import com.raimsoft.matan.info.ZombieStateEnum;
import com.raimsoft.matan.motion.IHitting;
import com.raimsoft.matan.motion.ISpriteModify;
import com.raimsoft.matan.util.FrameManager;
import com.raimsoft.matan.util.SpriteBitmap;

public class Partner extends AbstractGameObject implements IHitting, ISpriteModify
{
	public SpriteBitmap SPRITE;
	private Resources mRes;

	private int nHP= 200;
	private final int nTotalHP= nHP; // 총HP
	public HPbar mHPbar;

	public PartnerStateEnum eState= PartnerStateEnum.SHOT_6;
	public boolean bImageRefresh= false;
	public boolean bShooting= false;

	private StageInfo info;

	public Partner(float X, float Y, int IDimage, int Width, int Height, Resources _Res)
	{
		super(X, Y, IDimage, Width, Height);

		mRes= _Res;
		info= StageInfo.getInstance();

		SPRITE= new SpriteBitmap(IDimage, mRes, Width,Height, 6, info.spdPartnerShot);
		mHPbar= new HPbar(x+85, y+25, R.drawable.hp_bar, R.drawable.hp_skin, 75, 11);
	}

	public boolean Shooting(int ClosestNum, ZombieStateEnum ClosestZombieState)
	{
		if (ClosestNum==-1) return false;
		if (ClosestZombieState== ZombieStateEnum.DIE)
		{
			bShooting= false; // 쏘고 있던 적이 죽으면 슈팅 안함
			return false;
		}

		if (!bShooting)
		{
			CloseState(ClosestNum); // 가장 가까운 좀비의 방향을 새로고침함.
			this.bImageRefresh= true; // 이미지 새로고침
			this.bShooting= true; // 쏘는중
		}
		return true;
	}

	private void CloseState(int ClosestNum)
	{
		switch (ClosestNum)
		{
		case 5: case 0: case 1:
			eState= PartnerStateEnum.SHOT_11;
			break;
		case 2:
			eState= PartnerStateEnum.SHOT_12;
			break;
		case 3: case 4: case 6:
			eState= PartnerStateEnum.SHOT_1;
			break;
		case 8:
			eState= PartnerStateEnum.SHOT_3;
			break;
		case 10: case 14: case 15:
			eState= PartnerStateEnum.SHOT_5;
			break;
		case 13:
			eState= PartnerStateEnum.SHOT_6;
			break;
		case 9: case 11: case 12:
			eState= PartnerStateEnum.SHOT_7;
			break;
		case 7:
			eState= PartnerStateEnum.SHOT_9;
			break;
		}

	}

	/**
	 * 파트너 새로고침
	 */
	public void Refresh_Partner()
	{
		bImageRefresh= false;
		if (eState == PartnerStateEnum.DIE && IDimage==R.drawable.ch_die_sprite)
			return;

		switch (eState)
		{
		case SHOT_12:
			Init(R.drawable.ch_crossbow12_sprite, 6, info.spdPartnerShot, mRes);
			break;
		case SHOT_1:
			Init(R.drawable.ch_crossbow01_sprite, 6, info.spdPartnerShot, mRes);
			break;
		case SHOT_3:
			Init(R.drawable.ch_crossbow03_sprite, 6, info.spdPartnerShot, mRes);
			break;
		case SHOT_5:
			Init(R.drawable.ch_crossbow05_sprite, 6, info.spdPartnerShot, mRes);
			break;
		case SHOT_6:
			Init(R.drawable.ch_crossbow06_sprite, 6, info.spdPartnerShot, mRes);
			break;
		case SHOT_7:
			Init(R.drawable.ch_crossbow07_sprite, 6, info.spdPartnerShot, mRes);
			break;
		case SHOT_9:
			Init(R.drawable.ch_crossbow09_sprite, 6, info.spdPartnerShot, mRes);
			break;
		case SHOT_11:
			Init(R.drawable.ch_crossbow11_sprite, 6, info.spdPartnerShot, mRes);
			break;
		case DIE:
			Init(R.drawable.ch_die_sprite, 10, info.spdPartnerDie, mRes);
			break;
		}

	}

	@Override
	public void Init(int IDimage, int SpriteNum, int Delay, Resources mRes)
	{
		SPRITE.SpriteModify(IDimage, mRes, Width,Height, SpriteNum, Delay);
	}


	@Override
	public void Damage(int minusHP, int delay)
	{
		if (eState==PartnerStateEnum.DIE) return;

		if (FrameManager.FrameTimer(delay))
		{
			this.nHP -= minusHP;
			mHPbar.DRAWimageBAR.setBounds(mHPbar.getRectBar4Rect(this.nHP, nTotalHP)); // HP바(위치)

			if (this.nHP<=0)
			{
				eState= PartnerStateEnum.DIE;
				this.bImageRefresh= true;
			}
		}

	}

}
