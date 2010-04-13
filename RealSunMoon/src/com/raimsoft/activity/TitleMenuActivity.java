package com.raimsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class TitleMenuActivity extends Activity implements OnClickListener {
	
	private boolean already_Next;
	
	private void Next()
	{
		if(!already_Next)
		{
			Intent intent=new Intent(TitleMenuActivity.this, GameActivity.class);
	        startActivity(intent);
	        already_Next=true;
	        finish();
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.titlemenu);
		
		super.onCreate(savedInstanceState);
		
		findViewById(R.id.start).setOnClickListener(this);
		//findViewById(R.id.img_start).setOnClickListener(this);
		findViewById(R.id.exit).setOnClickListener(this);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.start)
		{
			Next();
		}else
		{
			finish();
		}
	}
}
