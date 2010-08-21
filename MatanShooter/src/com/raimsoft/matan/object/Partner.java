package com.raimsoft.matan.object;

import android.content.res.Resources;
import android.util.Log;

import com.raimsoft.matan.activity.R;
import com.raimsoft.matan.info.PartnerStateEnum;
import com.raimsoft.matan.info.Stage1Info;
import com.raimsoft.matan.motion.IHitting;
import com.raimsoft.matan.motion.ISpriteModify;
import com.raimsoft.matan.util.FrameManager;
import com.raimsoft.matan.util.SpriteBitmap;

public class Partner extends GameObject implements IHitting, ISpriteModify
{
	public SpriteBitmap SPRITE;

	int nHP= 100;
	public HPbar mHPbar;

	public PartnerStateEnum nState= PartnerStateEnum.SHOT_12;
	public boolean bImageRefresh= false;

	private Stage1Info info;

	public Partner(float X, float Y, int IDimage, int Width, int Height, Resources mRes)
	{
		super(X, Y, IDimage, Width, Height);

		info= Stage1Info.getInstance();

		SPRITE= new SpriteBitmap(IDimage, mRes, Width,Height, 6, info.spdPartnerShot);
		mHPbar= new HPbar(x+85, y+25, R.drawable.hp_bar, R.drawable.hp_skin, 75, 11);
	}


	@Override
	public void Init(int IDimage, int SpriteNum, int Delay, Resources mRes)
	{
		SPRITE.SpriteModify(IDimage, mRes, Width,Height, SpriteNum, Delay);
	}


	@Override
	public void Damage(int minusHP, int delay)
	{
		if (FrameManager.FrameTimer(delay))
		{
			this.nHP -= minusHP;
			Log.d("Wanderer:Attack", "HP : "+Float.toString(nHP));

			mHPbar.DRAWimageBAR.setBounds(mHPbar.getRectBar4Rect(this.nHP, 100)); // HP바(위치)
		}

	}

}
