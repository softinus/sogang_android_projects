package kr.co.joynuri.animalorchestra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Launcher extends Activity {
	
	private static final String LOG_TAG = Launcher.class.getSimpleName();
	
	public static final String MENU_KEY = "MENU";
	public static final int MENU_PRACTICE = 1;
	public static final int MENU_GAME = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "onCreate...START");
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);

        final Button btnPractice = (Button)findViewById(R.id.btn_practice);
		final Button btnGame = (Button)findViewById(R.id.btn_game);
		final Button btnScore = (Button)findViewById(R.id.btn_score);
		final Button btnHelp = (Button)findViewById(R.id.btn_help);
		
		btnPractice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentGame = new Intent(Launcher.this, Game.class);
				intentGame.putExtra(MENU_KEY, MENU_PRACTICE);
				startActivity(intentGame);
			}
		});
		
		btnGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentGame = new Intent(Launcher.this, Game.class);
				intentGame.putExtra(MENU_KEY, MENU_GAME);
				startActivity(intentGame);
			}
		});
		
		btnScore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentHighscore = new Intent(Launcher.this, HighScoreList.class);
				startActivity(intentHighscore);
			}
		});
		
		btnHelp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentHelp = new Intent(Launcher.this, Help.class);
				startActivity(intentHelp);
			}
		});
		
		Log.d(LOG_TAG, "onCreate...END");
	}
}