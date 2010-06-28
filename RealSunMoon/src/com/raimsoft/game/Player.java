package com.raimsoft.game;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;

import com.raimsoft.activity.R;
import com.raimsoft.sound.SoundManager;
import com.raimsoft.view.GameView;

public class Player extends GameObject {

	private float spd;	// 속도 G1: 0.9~1.2, 오드로이드: 2.4~2.7

	public  boolean bStep=		false;	// 처음점프했나
	public 	boolean bJump=		false;	// 올라가고있나
	public	boolean bCrushed=	false;	// 부딫혔나
	public	boolean bShorked=	false;	// 감전되었나
	public	boolean bItemGet=	false;	// 아이템먹었나

	private int nShorkSprite=0;	// 쇼크애니메이션

	public int State=0;
	public int OriX, OriY;

	public SoundManager sm;

	private int JumpIdxArr_First[]={-14,-13,-12,-11,-10,-9,-9,-9,-8,-8,-8,-7,-7,-7,-6,-6,-6,-5,-5,-5,
			-4,-4,-4,-3,-3,-3,-2,-2,-2,-1,-1,-1,0,0,0,0,0,
			1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,6,6,6,7,7,7,8,8,8,9,9,9,
			10,11,12,13};//67 (37)

	private int JumpIdx_Last= JumpIdxArr_First.length;	// 점프 배열 수 (변경될 수 있음)
	private int JumpIdx_Present=0;	// 현재 사용되는 점프배열인덱스


	private int JumpIdxArr_Always[]={-14,-13,-12,-11,-10,-9,-9,-9,-8,-8,-8,-7,-7,-7,-6,-6,-6,-5,-5,-5,
			-4,-4,-4,-3,-3,-3,-2,-2,-2,-1,-1,-1,0,0,0,0,0,
			1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,6,6,6,7,7,7,8,8,8,9,9,9,
			10,11,12,13,14,  15,16,17,18,19,20,22,24,26,28,30};//79

	private int FlyWingIdxArr[]= {-1,-1,-2,-2,-3,-4,-5,-6,-7,-8,-9,-10,-12,
			-14,-16,-19,-23,-25,-27,-30,-32,-35,-40,-45,-50,
			-45,-40,-30,-20,-19,-18,-17,-16,-15,-14,-13,-12,-11,-10,-9,-9,-9,-8,-8,-8,-7,-7,-7,-6,-6,-6,-5,-5,-5,
			-4,-4,-4,-3,-3,-3,-3,-3,-2,-2,-2,-2,-2,-1,-1,-1,-1,-1,0,0,0,0,0,0,0}; //

	private int FlyIdx_Present=0;
	private int FlyIdx_Last= FlyWingIdxArr.length;

	/**
	 * SDK별 센서 스피드 조절
	 */
	private void setSDKforSpeed()
	{
		if (Build.VERSION.SDK_INT <= 4)
		{
			spd= (float) 2.1;
		}else{
			spd= (float) 2.9;
		}
	}
	private void OriginalPos(int _x, int _y)
	{
		OriX= _x;
		OriY= _y;
	}
	public void ResetPlayerPos()
	{
		this.SetPos(OriX, OriY);
	}

	/**
	 * 플레이어 위치는 자동 중앙배치하는 기본 생성자
	 * @param view : 중앙위치가 계산될 뷰
	 */
	public Player(GameView view)
	{
		this.view=view;
		x = (view.getWidth() - wid)/2;
		y = (view.getHeight() - hei)/2;

		Img_id=R.drawable.nui_stand_left;

		setSDKforSpeed();
		OriginalPos(x,y);
		SoundCreate();
	}

	/**
	 * 플레이어 위치만 설정해주는 생성자
	 * @param view
	 * @param x : X값, (-1)이면 중앙배치
	 * @param y : Y값, (-1)이면 중앙배치
	 */
	public Player (GameView view, int x, int y)
	{
		this.view=view;
		if(x==-1)
		{
			this.x = (view.getWidth() - wid)/2;
		}else{
			this.x=x;
		}

		if(y==-1)
		{
			this.y = (view.getHeight() - hei)/2;
		}else{
			this.y=y;
		}

		setSDKforSpeed();
		OriginalPos(x,y);
		SoundCreate();
	}

	/**
	 * 모든 정보 입력하는 생성자
	 * @param view
	 * @param x : X값, (-1)이면 중앙배치
	 * @param y : Y값, (-1)이면 중앙배치
	 * @param width : 플레이어 폭
	 * @param height : 플레이어 높이
	 * @param Image_ID : 플레이어의 이미지ID
	 */
	public Player(GameView view, int x, int y, int width, int height, int Image_ID)
	{
		this.view= view;
		this.wid = width;
		this.hei = height;

		if(x==-1)
		{
			this.x= (view.getWidth() - wid)/2;
		}else{
			this.x=x;
		}

		if(y==-1)
		{
			this.y= (view.getHeight() - hei)/2;
		}else{
			this.y=y;
		}

		Img_id=Image_ID;

		setSDKforSpeed();
		OriginalPos(x,y);
		SoundCreate();
	}


	private void SoundCreate()
	{
		sm= new SoundManager(view.gameContext);
		sm.create();
		sm.load(0, R.raw.jump);
		sm.load(1, R.raw.getitem);
		sm.load(2, R.raw.birdprey);
		sm.load(3, R.raw.touchstar);
		sm.load(4, R.raw.blink);
		sm.load(5, R.raw.lightning);
	}

	// 현재 Bitmap을 Drawable로 바꾸면서 안쓰게 되었음.
	public Rect getStateImgRect()
	{
		Rect rct = new Rect(0,0,this.wid,this.hei);
		if (this.State==KeyEvent.KEYCODE_DPAD_LEFT)
		{
			 rct= new Rect(0,0,this.wid,this.hei);
		}

		if (this.State==KeyEvent.KEYCODE_DPAD_RIGHT)
		{
			rct = new Rect(this.wid+1,0,this.wid*2,this.hei);
		}
		return rct;
	}


	public void setState(int _state)
	{
		if (this.State==_state)	// 이전 State와 비교해서 변경이 없으면 넘어감
			return;


		this.State	=_state;
	}


	public void fallcheck()	// 화면 밑으로 캐릭터가 떨어지면
	{
		//Log.v("checkLife()", "Call checkLife()");
		if (this.y > view.getHeight())
		{
			this.bLive= false;
		}
	}

	/**
	 * 방향을 입력받아  이동한다.
	 * @param dir : 방향
	 */
	public void Move(int dir)
	{
		if(dir == KeyEvent.KEYCODE_DPAD_UP)
		{
			this.y-= spd;
			if(y < 0)
				y = 0;
		}
		if(dir == KeyEvent.KEYCODE_DPAD_DOWN)
		{
			this.y+= spd;
			if(y > view.getHeight() - this.hei)
			{
				y = view.getHeight() - this.hei;
			}
		}
		if(dir == KeyEvent.KEYCODE_DPAD_LEFT)
		{
			this.x-= spd;
			if(x < 0)
				x = 0;
		}
		if(dir == KeyEvent.KEYCODE_DPAD_RIGHT)
		{
			this.x+= spd;
			if(x > view.getWidth() - this.wid)
				x = view.getWidth() - this.wid;
		}
	}

	public void SensorMove(Point _val_)
	{
		this.setPlayerImgChange();

		if (_val_.x < 0 && _val_.x != 0)	// 방향 체크
		{
			this.setState(KeyEvent.KEYCODE_DPAD_LEFT);
		}else if (_val_.x > 0 && _val_.x != 0)
		{
			this.setState(KeyEvent.KEYCODE_DPAD_RIGHT);
		}

		if (((this.x + _val_.x) > 0) && ((this.x + _val_.x) < view.getWidth()-this.wid))
		{// Out of screen check
			this.x += _val_.x*spd;
		}
	}

	public void SensorMove(float _x)
	{
		this.setPlayerImgChange();

		if (_x < 0 && _x != 0)	// 방향 체크
		{
			this.setState(KeyEvent.KEYCODE_DPAD_LEFT);
		}else if (_x > 0 && _x != 0)
		{
			this.setState(KeyEvent.KEYCODE_DPAD_RIGHT);
		}

		//if (((this.x + _x) > 0) && ((this.x + _x) < view.getWidth()-this.wid))
		//{// Out of screen check
			this.x += (_x * spd);
		//}

		if (this.x + _x < -this.wid - spd)
		{
			this.x= view.getWidth();
		}

		if (this.x + _x > this.wid + view.getWidth() +spd)
		{
			this.x= -this.wid;
		}
	}

	/**
	 * 한번 입력받은 방향대로 쭉 간다.
	 */
	public void MoveAway()
	{
		if(bStop) return;

		switch(this.State)
		{
		case KeyEvent.KEYCODE_DPAD_UP:
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			this.x-= spd;
			if(x < 0)
				x = 0;
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			this.x+= spd;
			if(x > view.getWidth() - this.wid)
				x = view.getWidth() - this.wid;
			break;
		}
	}

	/**
	 * 몬스터와 충돌시
	 */
	public void CrushFall()
	{
		this.fallcheck();
		this.y+=5;

		this.Img_id=R.drawable.nui_fall_3;
		view.thread.mStageMgr.mStage.setPlayerImg_Refresh();
	}
	/**
	 * 번개와 충돌시
	 */
	public void ShorkChar()
	{
		if (nShorkSprite%2==0)
		{
			this.Img_id=R.drawable.nui_shork_2;
			view.thread.mStageMgr.mStage.setPlayerImg_Refresh();

		}else if (nShorkSprite%2==1){
			this.Img_id=R.drawable.nui_shork_1;
			view.thread.mStageMgr.mStage.setPlayerImg_Refresh();
		}
		++nShorkSprite;
		if (nShorkSprite==120)
		{
			view.thread.gameOver();
		}
	}

	/**
	 * 반복 점프
	 */
	public void JumpAlways()
	{
		this.fallcheck(); 	// 떨어졌는지 체크

		if(bStop) return; // 일시정지 상태이면 점프안함


		if(bStep && bItemGet)	// 아이템을 먹었을 시의 점프
		{
			if (FlyIdx_Present==FlyIdx_Last)
			{
				bItemGet=false;
				this.setJumpIndex(38); // 점프인덱스변경 (중간)
				return;
			}
			if (this.y + FlyWingIdxArr[FlyIdx_Present] < 150) // 캐릭터가 150px이상 화면으로 올라가
			{
				view.thread.mStageMgr.mStage.treadleMgr.setAllChangeY(-FlyWingIdxArr[FlyIdx_Present]); 	// 면 구름을 점프한만큼 내림
				view.thread.mStageMgr.mStage.mItemList.setAllChangeY(-FlyWingIdxArr[FlyIdx_Present]);	// 면 아이템 내림
				view.thread.mStageMgr.mStage.mFakeList.setAllChangeY(-FlyWingIdxArr[FlyIdx_Present]);	// 면 가짜구름 내림

				if (view.thread.mStageMgr.mStage.mMonster.bFly)	// 고 몬스터가 날고있을 때
				{
					view.thread.mStageMgr.mStage.mMonster.SetChangeY(-FlyWingIdxArr[FlyIdx_Present]); // 몬스터 내림
				}
				if (view.getHeight() < view.thread.mStageMgr.mStage.BackSize) // 고 화면이 백사이즈보다 작을 때
				{
					view.thread.mStageMgr.mStage.BackSize += Math.round(FlyWingIdxArr[FlyIdx_Present] / 3);
					//view.thread.mStageMgr.mStage.mItemList.CreateItems(); // 아이템 만들어줌
					if (view.thread.mStageMgr.currStage != 1)
						view.thread.mStageMgr.mStage.mFakeList.CreateFakes(); // 가짜구름 만들어줌
					//Log.d("BackSize= ", Float.toString(view.thread.mStageMgr.mStage.BackSize));
				}

			}else{
				//if (!(FlyIdx_Last==34)) {FlyIdx_Last=34;}
				this.y+= FlyWingIdxArr[FlyIdx_Present];
			}
			++FlyIdx_Present;
		}

		else if(bStep && (!bItemGet)) // 아이템 안먹었을 시의 점프
		{


			if (this.y + JumpIdxArr_Always[JumpIdx_Present] < 150) // 캐릭터가 150px이상 화면으로 올라가
			{
				view.thread.mStageMgr.mStage.treadleMgr.setAllChangeY(-JumpIdxArr_Always[JumpIdx_Present]); 	// 면 구름을 점프한만큼 내림
				view.thread.mStageMgr.mStage.mItemList.setAllChangeY(-JumpIdxArr_Always[JumpIdx_Present]);		// 면 아이템 내림
				view.thread.mStageMgr.mStage.mFakeList.setAllChangeY(-JumpIdxArr_Always[JumpIdx_Present]);		// 면 아이템 내림

				if (view.thread.mStageMgr.mStage.mMonster.bFly)	// 고 몬스터가 날고있을 때 몬스터 내림
				{
					view.thread.mStageMgr.mStage.mMonster.SetChangeY(-JumpIdxArr_Always[JumpIdx_Present]);
				}

				if (view.getHeight() < view.thread.mStageMgr.mStage.BackSize) // 고 화면이 백사이즈보다 작을 때
				{
					view.thread.mStageMgr.mStage.BackSize += Math.round(JumpIdxArr_Always[JumpIdx_Present] / 3);
					view.thread.mStageMgr.mStage.mItemList.CreateItems(); // 아이템 만들어줌

					if (view.thread.mStageMgr.currStage != 1)
						view.thread.mStageMgr.mStage.mFakeList.CreateFakes(); // 가짜구름 만들어줌
					//Log.d("BackSize= ", Float.toString(view.thread.mStageMgr.mStage.BackSize));
				}
			}
			else
			{
				if (!(JumpIdx_Last==79)) {JumpIdx_Last=79;}
				this.y+= JumpIdxArr_Always[JumpIdx_Present];
			}
		}
		else
		{
			if (bItemGet) return;
			if (!(JumpIdx_Last==67)) {JumpIdx_Last=67;}
			this.y+= JumpIdxArr_First[JumpIdx_Present];
		}

		//Log.d("Player::y", Float.toString(JumpIdx2[JumpIdx_Present]));

		if (JumpIdx_Present == JumpIdx_Last)
		{
			JumpIdx_Present=0;
		}

		if (JumpIdxArr_Always[JumpIdx_Present] > 0)	// 내려갈때와 올라갈 때를 설정
		{
			bJump=true;

		}else{
			bJump=false;
		}

		JumpIdx_Present++;
	}

	/**
	 * 발판과의 충돌을 감지합니다.
	 * @param rct
	 * @param _tra
	 * @param _mgr
	 */
	public void CollisionTreadle(Rect rct, Treadle _tra)
	{
		if (this.getObjectForRectHalf(false).intersect(rct))
		{
			Log.v("Collision", "Call CollisionTreadle");

			this.setJumpIndex(0);	// 다시 점프
			view.thread.mStageMgr.mStage.cnt_Step++; // 카운팅

			if ((view.thread.mStageMgr.mStage.treadleMgr.getCount()-1)==_tra.uNumber)	//마지막 발판 밟을시
			{
				view.thread.setMoveing(false);
				view.thread.mStageMgr.mStage.setGameClear(true);

			}

			switch (_tra.Img_id)	// 발판별 스코어 점수증가
			{
				case R.drawable.cloud1_1:	case R.drawable.cloud2_1:	case R.drawable.cloud3_1:
					if (!_tra.bStepped)
						view.thread.mStageMgr.mStage.gameScore+=70;
					_tra.bStepped=true;
					break;
				case R.drawable.cloud1_2:	case R.drawable.cloud2_2:	case R.drawable.cloud3_2:
					if (!_tra.bStepped)
						view.thread.mStageMgr.mStage.gameScore+=50;
					_tra.bStepped=true;
					break;
				case R.drawable.cloud1_3:	case R.drawable.cloud2_3:	case R.drawable.cloud3_3:
					if (!_tra.bStepped)
						view.thread.mStageMgr.mStage.gameScore+=30;
					_tra.bStepped=true;
					break;
				case R.drawable.cloud1_4:	case R.drawable.cloud2_4:	case R.drawable.cloud3_4:
					if (!_tra.bStepped)
						view.thread.mStageMgr.mStage.gameScore+=10;
					_tra.bStepped=true;
					break;
			}
			_tra.bStepped_Pre= true;
			if(sm.bSoundOpt) sm.play(0);
			this.bStep=true;
		}
	}

	/**
	 * 아이템과의 충돌
	 * @param rct : 플레이어와 충돌체크할 렉트
	 * @param _item : 아이템 객체
	 * @param _idx : 아이템 인덱스 (List)
	 */
	public void CollisionItem(Rect rct, Item _item, int _idx)
	{
		if (this.getObjectForRectHalf(false).intersect(rct))
		{
			Log.v("Collision", "Call CollisionItem");

			switch (_item.Img_id)
			{
			case R.drawable.item_wing:
				view.thread.mStageMgr.mStage.gameScore+=100;
				this.bItemGet= true;
				this.FlyIdx_Present= 0;
				break;
			}
			if(sm.bSoundOpt) sm.play(3);
			view.thread.mStageMgr.mStage.mItemList.itemList.remove(_idx);
		}
	}

	/**
	 * 가짜구름과 충돌
	 * @param rct
	 * @param _fake
	 * @param _idx
	 */
	public void CollisionFakeCloud(Rect rct, FakeCloud _fake, int _idx)
	{
		if (this.getObjectForRect().intersect(rct))
		{
			Log.v("Collision", "Call CollisionItem");

			switch (_fake.Img_id)
			{
			case R.drawable.fakecloud3:
				break;
			}
			if(sm.bSoundOpt) sm.play(4);
			view.thread.mStageMgr.mStage.mFakeList.fakeList.remove(_idx);
		}
	}

	/**
	 * 번개와 충돌
	 * @param rct
	 */
	public void CollisionLight(Rect rct)
	{// 번개 치는동안 캐릭터가 번개에 맞으면
		if (this.getObjectForRectHalf(false).intersect(rct) && view.thread.mStageMgr.mStage.mDark.getLightning())
		{
			Log.v("Collision", "Call CollisionLight");
			this.bShorked=true;
		}
	}

	/**
	 * 몬스터와의 충돌
	 * @param rct
	 */
	public void CollisionMonster(Rect rct)
	{
		if (this.getObjectForRectHalf(false).intersect(rct))
		{
			Log.v("Collision", "Call CollisionMonster");
			this.bCrushed=true;
		}
	}
	


	/**
	 * 점프인덱스 변경
	 * @param _idx
	 */
	public void setJumpIndex(int _idx)
	{
		JumpIdx_Present= _idx;
		//FlyIdx_Present= _idx;
	}

	public void setPlayerImgChange()
	{
		if (this.State==KeyEvent.KEYCODE_DPAD_LEFT) // 왼쪽일 때
		{
			if (bItemGet)
			{
				this.Img_id= R.drawable.nui_wing_left;
				view.thread.mStageMgr.mStage.setPlayerImg_Refresh();
				return;
			}

			if (bJump) // 점프상태(boolean)
			{
				this.Img_id= R.drawable.nui_stand_left;
			}else{
				this.Img_id= R.drawable.nui_jump_left;
			}
			view.thread.mStageMgr.mStage.setPlayerImg_Refresh();
		}
		if (this.State==KeyEvent.KEYCODE_DPAD_RIGHT)
		{
			if (bItemGet)
			{
				this.Img_id= R.drawable.nui_wing_right;
				view.thread.mStageMgr.mStage.setPlayerImg_Refresh();
				return;
			}

			if (bJump) // 점프상태(boolean)
			{
				this.Img_id= R.drawable.nui_stand_right;
			}else{
				this.Img_id= R.drawable.nui_jump_right;
			}
			view.thread.mStageMgr.mStage.setPlayerImg_Refresh();
		}
	}
}
