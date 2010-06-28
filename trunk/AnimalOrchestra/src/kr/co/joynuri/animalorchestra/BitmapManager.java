package kr.co.joynuri.animalorchestra;

import java.util.Collection;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapManager {
	
	private static final String LOG_TAG = BitmapManager.class.getSimpleName();
	
	private Context mContext;
	private HashMap<Integer, Bitmap> mBitmapMap;
	private BitmapFactory.Options mOpts;
	
	public BitmapManager(Context context) {
		mContext = context;
	}
	
	public void create() {
		mBitmapMap = new HashMap<Integer, Bitmap>();
		mOpts = new BitmapFactory.Options();
		mOpts.inDither = false;
		mOpts.inJustDecodeBounds = false; 
		mOpts.inScaled = false;
		mOpts.inPurgeable = true;
		mOpts.inPreferredConfig = Bitmap.Config.RGB_565;
		mOpts.inSampleSize = 1;
		///mOpts.inTempStorage;
		///mOpts.mCancel;
		///mOpts.outHeight;
		///mOpts.outMimeType;
		///mOpts.outWidth;
	}
	
	public void destroy() {
		if (mBitmapMap != null) {
			Collection<Bitmap> bitmaps = mBitmapMap.values();
			for (Bitmap bitmap : bitmaps) {
				bitmap.recycle();
				Log.d(LOG_TAG, "destroy bitmap object " + bitmap.toString());
			}
			mBitmapMap = null;
		}
	}
	
	public void load(int resId) {
		Log.d(LOG_TAG, "load...START");
		
		if (!mBitmapMap.containsKey(resId)) {
			Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resId, mOpts);
			mBitmapMap.put(resId, bitmap);
		}
		
		Log.d(LOG_TAG, "load...END");
	}
	
	public Bitmap getBitmap(int resId) {
		return mBitmapMap.get(resId);
	}
	
}
