package kr.co.joynuri.animalorchestra;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class Game extends Activity {
	
	private static final String LOG_TAG = Game.class.getSimpleName();
	
	public static final int DIALOG_HIGHSCORE_DIALOG = 0;
	
	private GameView mGameView;
	
	@Override
    public Object onRetainNonConfigurationInstance() {
		return mGameView.getState();
	}
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "onCreate...START");

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.game);
		
		Intent intent = getIntent();
		int menu = intent.getIntExtra(Launcher.MENU_KEY, Launcher.MENU_PRACTICE);
		mGameView = (GameView) findViewById(R.id.game_view);
		mGameView.create(menu);
		mGameView.start();
		
		// restore
		Object o = getLastNonConfigurationInstance();
		if (o != null) {
			mGameView.setState(o);
		}
		
		Log.d(LOG_TAG, "onCreate...END");
	}
	
	@Override
	protected void onDestroy() {
		Log.d(LOG_TAG, "onDestroy...START");
		
		mGameView.stop();
		mGameView.destroy();
		super.onDestroy();
		
		Log.d(LOG_TAG, "onDestroy...END");
	}
	
	@Override
	protected void onPause() {
		Log.d(LOG_TAG, "onPause...START");
		
		mGameView.pause();
		super.onPause();
		
		Log.d(LOG_TAG, "onPause...END");
	}

	@Override
	protected void onResume() {
		Log.d(LOG_TAG, "onResume...START");
		
		super.onResume();
		//mGameView.resume();
		
		Log.d(LOG_TAG, "onResume...END");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.menu, menu);
    	return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_launcher:
			finish();
			return true;
		case R.id.menu_pause:
			mGameView.pause();
			return true;
		case R.id.menu_highscore:
			Intent intentHighscore = new Intent(this, HighScoreList.class);
			startActivity(intentHighscore);
			return true;
		case R.id.menu_help:
			Intent intentHelp = new Intent(this, Help.class);
			startActivity(intentHelp);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    
    private long mScore;
    private ScoreHelper scoreHelper;
    public void showHighScoreDialog(long score) {
    	mScore = score;
		scoreHelper = new ScoreHelper(this);
		int ranking = scoreHelper.getRanking(mScore);
		if (ranking <= ScoreHelper.MAX_COUNT) {
			showDialog(Game.DIALOG_HIGHSCORE_DIALOG);
		}
	}
	
    @Override
	protected Dialog onCreateDialog(int id) {
    	switch(id){
    	case DIALOG_HIGHSCORE_DIALOG:
    		final LayoutInflater factory = LayoutInflater.from(this);
            final View textEntryView = factory.inflate(R.layout.highscore_dialog, null);
            final EditText txtName = (EditText)textEntryView.findViewById(R.id.txt_highscore_name);
            return new AlertDialog.Builder(this)
               .setIcon(R.drawable.icon)
               .setTitle("High Score")
               .setView(textEntryView)
               .setMessage("Please Input High Score.")
               .setPositiveButton(R.string.alert_dialog_save, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int whichButton) {
                	   scoreHelper.addScore(txtName.getText().toString(), mScore);
                	   finish();
                   }
               })
               .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int whichButton) {
                	   finish();
                   }
               })
               .create();
		}
		return super.onCreateDialog(id);
    }
    
}
