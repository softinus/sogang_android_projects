package com.raimsoft.matan.object;

import android.content.res.Resources;

import com.raimsoft.matan.motion.Moveable;
import com.raimsoft.matan.util.FPoint;
import com.raimsoft.matan.util.SpriteBitmap;
import com.raimsoft.matan.util.Vector2Calc;

public class Zombie extends GameObject implements Moveable
{
	public SpriteBitmap SPRITEwalk;
	//private boolean bPositioning= false;

	//MotionVectors motion;

	private FPoint vMove;
	private FPoint vStart, vStop;


	public Zombie(float X, float Y,  FPoint endPoint, int IDimage, int Width, int Height, Resources mRes)
	{
		super(X, Y, IDimage, Width, Height);

		SPRITEwalk= new SpriteBitmap(IDimage, mRes, 100,100, 4, 10);
		vMove= new FPoint();

		vStart= new FPoint(X,Y);

		vStop= new FPoint();
		vStop= endPoint;
	}


	@Override
	public void Move(float speed)
	{
		//if (!bPositioning)
		{
			vMove= Vector2Calc.CalVec(vStart, vStop);
			vMove= Vector2Calc.CalVecNormalize(vMove);
			vMove= Vector2Calc.CalScale(vMove, speed);

			//bPositioning= true;
		}

		this.x+= vMove.x;
		this.y+= vMove.y;
	}
}

