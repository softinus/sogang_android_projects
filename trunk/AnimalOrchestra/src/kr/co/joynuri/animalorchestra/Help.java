package kr.co.joynuri.animalorchestra;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Help extends Activity {
	
	private static final String LOG_TAG = Launcher.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "onCreate...START");
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
		
		Log.d(LOG_TAG, "onCreate...END");
	}
	
}
