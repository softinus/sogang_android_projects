package com.raimsoft.matan.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter
{
    private Context mContext;

    private Integer[] mImageIds = {
            R.drawable.ui_map_01_01,
            R.drawable.ui_map_01_02,
            R.drawable.ui_map_01_03,
            R.drawable.ui_map_01_04,
            R.drawable.ui_map_01_05
    };

    public ImageAdapter(Context c)
    {
        mContext = c;
        TypedArray a = mContext.obtainStyledAttributes(R.styleable.Gallery1);
        a.recycle();
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
        ImageView i = new ImageView(mContext);

        i.setImageResource(mImageIds[position]);
        i.setLayoutParams(new Gallery.LayoutParams(410, 280));
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
	}

	@Override
	public int getCount()
	{
		return mImageIds.length;
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}




}
