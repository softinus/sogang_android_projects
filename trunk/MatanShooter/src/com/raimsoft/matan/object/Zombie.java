package com.raimsoft.matan.object;

import android.content.res.Resources;

import com.raimsoft.matan.motion.MotionVectors;
import com.raimsoft.matan.motion.Moveable;
import com.raimsoft.matan.util.FPoint;
import com.raimsoft.matan.util.SpriteBitmap;
import com.raimsoft.matan.util.Vector2Calc;

public class Zombie extends GameObject implements Moveable
{
	public SpriteBitmap SPRITEwalk;
	private boolean bPositioning= false;

	MotionVectors motion;
	private FPoint vMove;


	public Zombie(float X, float Y, int IDimage, int Width, int Height, Resources mRes)
	{
		super(X, Y, IDimage, Width, Height);

		SPRITEwalk= new SpriteBitmap(IDimage, mRes, 100,100, 4, 10);
		motion= new MotionVectors();
		vMove= new FPoint();
	}


	@Override
	public void Move(float speed, int MovePattenNum)
	{
		if (!bPositioning)
		{
			vMove= Vector2Calc.CalVec(motion.pSrc[MovePattenNum], motion.pDes);
			vMove= Vector2Calc.CalVecNormalize(vMove);
			vMove= Vector2Calc.CalScale(vMove, speed);

			bPositioning= true;
		}

		this.x+= vMove.x;
		this.y+= vMove.y;
	}
}

