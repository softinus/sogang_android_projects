package com.raimsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainTitleActivity extends Activity implements OnClickListener
{
	Button BTNstart, BTNexit;
	private boolean already_Next=false;

	private void Next()
	{
		if(!already_Next)
		{
			Intent intent=new Intent(MainTitleActivity.this, GameActivity.class);
	        startActivity(intent);
	        already_Next=true;
	        finish();
		}
	}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        BTNstart= (Button) findViewById(R.id.btn_start);
        BTNstart.setOnClickListener(this);
        BTNexit= (Button) findViewById(R.id.btn_exit);
        BTNexit.setOnClickListener(this);
    }

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_start:
			Next();
			break;
		case R.id.btn_exit:
			finish();
			break;

		}
	}


}