package com.raimsoft.matan.object;

import android.content.res.Resources;

import com.raimsoft.matan.info.Stage1Info;
import com.raimsoft.matan.info.ZombieStateEnum;
import com.raimsoft.matan.motion.IAttacking;
import com.raimsoft.matan.motion.IHitting;
import com.raimsoft.matan.motion.IMoving;
import com.raimsoft.matan.motion.ISpriteModify;
import com.raimsoft.matan.util.FPoint;
import com.raimsoft.matan.util.SpriteBitmap;
import com.raimsoft.matan.util.Vector2Calc;

public abstract class Zombie extends GameObject implements IMoving, IHitting, IAttacking, ISpriteModify
{
	public SpriteBitmap SPRITE;
	public boolean bImageRefresh= false;
	public ZombieStateEnum eZombieState= ZombieStateEnum.WALK;
	public ZombieStateEnum eOldState= ZombieStateEnum.NONE;

	protected FPoint vMove, vVecNor, vVecVal;
	protected FPoint vStart, vStop;

	protected int nStepCount= 0;
	protected int nStepMax;

	protected int nHP;
	public int nPower;
	public int nRoute;

	protected Vector2Calc calc;
	protected Stage1Info info;

	public Zombie(float X, float Y,  FPoint endPoint, int IDimage, int Width, int Height, Resources mRes)
	{
		super(X, Y, IDimage, Width, Height);

		info= Stage1Info.getInstance();
		calc= new Vector2Calc();

		SPRITE= new SpriteBitmap(IDimage, mRes, 100,100, 4, info.spdZombieWalk);

		vMove= new FPoint();
		vVecNor= new FPoint();
		vVecVal= new FPoint();

		vStart= new FPoint(X,Y);

		vStop= new FPoint();
		vStop= endPoint;
	}

	public Zombie(int RouteNum, int IDimage, int Width, int Height, Resources mRes)
	{
		super(IDimage, Height, Width);

		info= Stage1Info.getInstance();

		vStart= new FPoint(info.pZombieStart[RouteNum].x, info.pZombieStart[RouteNum].y);
		vStop= new FPoint(info.pZombieStop[RouteNum].x, info.pZombieStop[RouteNum].y);
		nRoute= RouteNum;

		this.x= info.pZombieStart[RouteNum].x;
		this.y= info.pZombieStart[RouteNum].y;

		calc= new Vector2Calc();

		SPRITE= new SpriteBitmap(IDimage, mRes, 100,100, 4, info.spdZombieWalk);

		vMove= new FPoint();
		vVecNor= new FPoint();
		vVecVal= new FPoint();
	}

	@Override
	public void Init(int IDimage, int SpriteNum, int Delay, Resources mRes)
	{
		SPRITE.SpriteModify(IDimage, mRes, Width,Height, SpriteNum, Delay);
	}
}


