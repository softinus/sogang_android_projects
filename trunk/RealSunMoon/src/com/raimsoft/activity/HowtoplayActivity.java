package com.raimsoft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class HowtoplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.submenu_howtoplay);
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		finish();
		
		return super.onTouchEvent(event);
	}
}
