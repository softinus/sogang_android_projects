package kr.co.joynuri.animalorchestra;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class ScoresProvider extends ContentProvider {
	private static final String LOG_TAG = GameView.class.getSimpleName();
	
	public static final String PROVIDER_NAME = "kr.co.joynuri.animalochestra.provider.Scores";
	public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/Scores");
	
	public static final String _ID = "_ID";
	public static final String NAME = "NAME";
	public static final String SCORE = "SCORE";
	
	private static final int SCORES = 1;
	private static final int SCORE_ID = 2;
	
	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "scores", SCORES);
		uriMatcher.addURI(PROVIDER_NAME, "scores/#", SCORE_ID);
	}
	
	//--for database use--
	private SQLiteDatabase scoresDB;
	private static final String DATABASE_NAME = "AnimalOrchestra";
	private static final String DATABASE_TABLE = "Scores";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = 
		" CREATE TABLE " + DATABASE_TABLE + " ( " +
		"   _ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"   NAME TEXT NOT NULL, " +
		"   SCORE LONG NOT NULL " +
		" ) ";
	private static final String DATABASE_DROP = 
		" DROP TABLE IF EXISTS " + DATABASE_TABLE;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		private static final String LOG_TAG = GameView.class.getSimpleName();
		
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(LOG_TAG, String.format("Upgrading database from version %d to %d which will destroy all old data", oldVersion, newVersion));
			db.execSQL(DATABASE_DROP);
		}
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case SCORES:
			return "vnd.android.cursor.dir/"  + PROVIDER_NAME;
		case SCORE_ID:
			return "vnd.android.cursor.item/"  + PROVIDER_NAME;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}
	
	@Override
	public boolean onCreate() {
		Context context = getContext();
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		scoresDB = dbHelper.getWritableDatabase();
		return (scoresDB != null);
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.d(LOG_TAG, "query...START");
		
		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
		sqlBuilder.setTables(DATABASE_TABLE);
		
		if (uriMatcher.match(uri) == SCORE_ID) {
			sqlBuilder.appendWhere( String.format("%s = %s", _ID, uri.getPathSegments().get(1)) );
		}
		
		if (TextUtils.isEmpty(sortOrder)) {
			sortOrder = SCORE + " DESC";
		}
		
		Cursor c = sqlBuilder.query(scoresDB, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		Log.d(LOG_TAG, "query...END");
		return c;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.d(LOG_TAG, "insert...START");
		
		long rowID = scoresDB.insert(DATABASE_TABLE, "", values);
		
		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			Log.d(LOG_TAG, "insert...END");
			return _uri;
		}
		
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		Log.d(LOG_TAG, "update...START");
		
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case SCORES:
			count = scoresDB.update(DATABASE_TABLE, values, selection, selectionArgs);
			break;
		case SCORE_ID:
			String whereClause = 
				String.format("%s = ",
					_ID,
					uri.getPathSegments().get(1),
					(TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")")
					);
			count = scoresDB.update(DATABASE_TABLE, values, whereClause, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		Log.d(LOG_TAG, "update...END");
		return count;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.d(LOG_TAG, "delete...START");
		
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case SCORES:
			count = scoresDB.delete(DATABASE_TABLE, selection, selectionArgs);
			break;
		case SCORE_ID:
			String whereClause = 
				String.format("%s = ",
					_ID,
					uri.getPathSegments().get(1),
					(TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")")
					);
			count = scoresDB.delete(DATABASE_TABLE, whereClause, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri); 
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		Log.d(LOG_TAG, "delete...END");
		return count;
	}
}
