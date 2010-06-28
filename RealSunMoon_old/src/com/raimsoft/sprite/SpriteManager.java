package com.raimsoft.sprite;

import java.util.ArrayList;

import android.graphics.Rect;
import android.util.Log;

public class SpriteManager {

	private int mTile_Rows;
	private int mTile_Cols;
	
	private int mTiles_width;
	private int mTiles_height;
	
	private Rect mSrcRect;
	
	private ArrayList<Rect> mRctArr;
	
	public SpriteManager (int Tile_Rows, int Tile_Cols, int Tiles_width, int Tiles_height)
	{
		mTile_Rows= Tile_Rows;
		mTile_Cols= Tile_Cols;
		mTiles_width= Tiles_width;
		mTiles_height= Tiles_height;
	}
	
	public SpriteManager (int Tile_Rows, int Tile_Cols, int Tiles_width, int Tiles_height, Rect src)
	{
		Rect RTiles[]=new Rect[Tile_Rows * Tile_Cols];
		
		for (int h=0; h<Tile_Rows; h++)
		{
			for(int w=0; w<Tile_Cols; w++)
			{
				Log.d("Set2matrixFor", "wid="+w+" hei="+h);
				//rct[h][w].left=w*65;
				//rct[h][w].top=h*81;
				//rct[h][w].right=w*65+65;
				//rct[h][w].top=h*81+81;
			}
		}
	}
	
	public SpriteManager () {}
	
	
	public int addRect (Rect _SrcRect)
	{
		mRctArr.add(_SrcRect);
		return mRctArr.indexOf(_SrcRect);
	}
	
	public void delRect (int _idx)
	{
		if(mRctArr.isEmpty())
		{
			mRctArr.remove(_idx);
		}else{
			Log.d("Error", "mRctArr is Empty");
		}
			
	}
	
	public void resetRectArr ()
	{
		if(!mRctArr.isEmpty())
		{
			mRctArr.removeAll(mRctArr);
			//mRctArr.clear();
		}
	}
}
