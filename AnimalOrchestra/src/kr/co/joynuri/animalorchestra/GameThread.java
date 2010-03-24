package kr.co.joynuri.animalorchestra;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
	
	private static final String LOG_TAG = GameThread.class.getSimpleName();
	
	private GameView mGameView;
	private SurfaceHolder mSurfaceHolder;
	private boolean mRun;
	
	public GameThread(GameView gameView) {
		mGameView = gameView;
		mSurfaceHolder = gameView.getHolder();
	}
	
	public void setRunning(boolean run) {
		Log.d(LOG_TAG, "setRunning " + run);
		mRun = run;
	}
	
	public boolean isRunning() {
		return mRun;
	}
	
	/** Implements Run */
	@Override
	public void run() {
		Log.d(LOG_TAG, "run...START");
		
		while (mRun) {
			Canvas canvas = null;
			try {
				canvas = mSurfaceHolder.lockCanvas(null);
				if (canvas != null) {
					synchronized (mSurfaceHolder) {
						mGameView.update();
						mGameView.onDraw(canvas);
					}
				}
			} finally {
				if (canvas != null) {
					mSurfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
		
		Log.d(LOG_TAG, "run...END");
	}

}
