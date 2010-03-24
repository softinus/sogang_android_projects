package com.raimsoft.activity;

import java.io.File;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

public class EmoticonList extends ListActivity {

	private final static String FILE_ROOT="emolist.txt";
	private static int cnt,cnt2;
	private String str;
	private String Arr_str[];
	StringBuffer buf=new StringBuffer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		File f=new File("/sdcard/emolist.txt");
		if(f.exists())
		{
			Log.d("DEBUG", "asasdas");
		}
		//Cursor cursor = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
        
        //startManagingCursor(cursor);
        
        //int nameIdx = cursor.getColumnIndexOrThrow(People.NAME);
        //int phoneIdx = cursor.getColumnIndexOrThrow(People.NUMBER);
        
        //Log.d("DEBUG",cursor.getString(phoneIdx));
		/*
		try
		{
			InputStream in=openFileInput(FILE_ROOT);
				
			if(in!=null)
			{
				InputStreamReader tmp=new InputStreamReader(in);
				BufferedReader reader=new BufferedReader(tmp);

				while((str=reader.readLine()) !=null)
				{
					buf.append(str+" ");
					++cnt;
					//Log.d("DEBUG", "buf.toString()="+ buf.toString());
				}
				in.close();
				StringTokenizer stok=new StringTokenizer(buf.toString());
				//Log.d("DEBUG", "cnt="+ cnt);
				Arr_str=new String[cnt];

				while(stok.hasMoreTokens())
				{
					Arr_str[cnt2]=stok.nextToken();
					Log.d("DEBUG", "stok["+cnt2+"]="+ Arr_str[cnt2]);
					++cnt2;
				}
			}
		}
		catch(Throwable t)
		{
			Toast
			.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG)
			.show();
		}*/
	}


	@Override
	protected void onStart() {
		super.onStart();
		
		//Toast
		//.makeText(this, buf, Toast.LENGTH_LONG)
		//.show();
		//setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Arr_str));
	}
}
