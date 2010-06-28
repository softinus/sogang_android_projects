package com.raimsoft.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class sms4u extends ListActivity {
    
	String[] Menus={"SMS 보내기","이모티콘 관리"};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Menus));
    }
    
    private void Next(Class nextClass)
    {
    	Intent intent=new Intent(sms4u.this, nextClass);
        startActivity(intent);
    }
    
    public void onListItemClick(ListView parent, View v, int position, long id) {
    	//Log.d("onListItemClick", Float.toString(position));
    	if(position==0)
    	{
    		Next(sendSMS.class);
    	}
    	else if(position==1)
    	{
    		Next(EmoticonList.class);
    	}
    }
}