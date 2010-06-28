package kr.co.joynuri.animalorchestra;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class ScoreHelper {
	private static final String LOG_TAG = GameView.class.getSimpleName();
	
	public static final String PREFERENCE_NAME = "SCORE";
	public static final int MAX_COUNT = 10;
	public static final String PREFIX_NAME = "NAME";
	public static final String PREFIX_SCORE = "SCORE";
	
	private Context context;
	private Score[] scores;
	
	public ScoreHelper(Context _context) {
		Log.d(LOG_TAG, "Constructor...START");
		
		context = _context;
		
		Cursor c = ((Activity)context).managedQuery(
				ScoresProvider.CONTENT_URI,
				null, null, null,
				ScoresProvider.SCORE + " DESC");
		
		scores = new Score[MAX_COUNT];
		for (int i = 0; i < MAX_COUNT; i++) {
			scores[i] = new Score();
		}
		
		if (c.moveToFirst()) {
			do {
				int i = c.getPosition();
				if (i >= MAX_COUNT) break;
				scores[i].setName( c.getString(c.getColumnIndex(ScoresProvider.NAME)) );
				scores[i].setScore( c.getLong(c.getColumnIndex(ScoresProvider.SCORE)) );
			} while (c.moveToNext());
		}
		
		Log.d(LOG_TAG, "Constructor...END");
	}
	
	public Score getScore(int i) {
		return scores[i];
	}
	
	public int getRanking(long score) {
		int index;
		for (index = 0; index < MAX_COUNT && scores[index].getScore() > score; index++);
		return (index + 1);
	}
	
	public void addScore(String name, long score) {
		Log.d(LOG_TAG, "addScore...START");
		
		int index;
		for (index = 0; index < MAX_COUNT && scores[index].getScore() > score; index++);
		if (index == MAX_COUNT) return;
		
		// flip score
		for (int i = (MAX_COUNT-1); i < index; i--) {
			scores[i].setName( scores[i-1].getName() );
			scores[i].setScore( scores[i-1].getScore() );
		}

		// new score
		scores[index].setName( name );
		scores[index].setScore( score );
		
		// editor score
		final ContentValues values = new ContentValues();
		values.clear();
		values.put(ScoresProvider.NAME, name);
		values.put(ScoresProvider.SCORE, score);
		((Activity)context).getContentResolver().insert(ScoresProvider.CONTENT_URI, values);
		
		Log.d(LOG_TAG, "addScore...END");
	}
}
