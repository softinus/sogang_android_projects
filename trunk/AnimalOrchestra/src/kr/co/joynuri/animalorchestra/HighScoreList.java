package kr.co.joynuri.animalorchestra;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HighScoreList extends ListActivity {
	
	private static final String LOG_TAG = Launcher.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "onCreate...START");
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);
        
        Score highScore;
        ArrayList<Score> arrayList = new ArrayList<Score>();
        ScoreHelper scoreHelper = new ScoreHelper(this);
        for (int i = 0; i < ScoreHelper.MAX_COUNT; i++) {
        	highScore = scoreHelper.getScore(i);
        	if (!TextUtils.isEmpty(highScore.getName()) || highScore.getScore() > 0) {
        		arrayList.add( highScore );
        	}
        }
        
        HighScoreAdapter adapter =
        	new HighScoreAdapter(this, R.layout.highscore_row, arrayList);
        setListAdapter(adapter);
		
		Log.d(LOG_TAG, "onCreate...END");
	}
	
	private class HighScoreAdapter extends ArrayAdapter<Score> {
		private ArrayList<Score> items;
		
		public HighScoreAdapter(Context context, int textViewResourceId, ArrayList<Score> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.highscore_row, null);
			}
			
			Score highScore = items.get(position);
			if (highScore != null) {
				TextView txtName = (TextView) v.findViewById(R.id.txt_highscore_name);
				TextView txtScore = (TextView) v.findViewById(R.id.txt_highscore_score);
				if (txtName != null) txtName.setText( String.format("%d. %s", position+1, highScore.getName()) );
				if (txtScore != null) txtScore.setText( "Score: " + String.valueOf(highScore.getScore()) );
			}
			
			return v;
		}
	}
}
