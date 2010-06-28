package com.raimsoft.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class sendSMS extends Activity implements OnClickListener {

	private static final int PICK_REQUEST = 1337;
	EditText txtPhoneNo;
    EditText txtMessage;
      
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.sendsms);
		
		findViewById(R.id.cmd_send).setOnClickListener(this);
		findViewById(R.id.cmd_back).setOnClickListener(this);
		findViewById(R.id.cmd_address).setOnClickListener(this);
		txtPhoneNo =(EditText) findViewById(R.id.txt_phone);
		txtMessage =(EditText) findViewById(R.id.txt_message);
	}
	
    public void onClick(View v)
    {
    	switch(v.getId())
    	{
    	case R.id.cmd_address:
    		Intent i=new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
    		startActivityForResult(i,PICK_REQUEST);
    		break;
    	case R.id.cmd_send:
            String phoneNo = txtPhoneNo.getText().toString();
            String message = txtMessage.getText().toString();                 
            if (phoneNo.length()>0 && message.length()>0)
            {
                sendSMS(phoneNo, message);
                Toast.makeText(getBaseContext(), "메세지가 전송되었습니다.", Toast.LENGTH_SHORT).show();
            }
            else if(phoneNo.length()>0)
            {
            	Toast.makeText(getBaseContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            	txtMessage.requestFocus();
            }else{
            	Toast.makeText(getBaseContext(), "번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            	txtPhoneNo.requestFocus();
            }
            break;
        
    	case R.id.cmd_back:
    		Back();
    		break;
    	}
    }	
	
	private void Back()
	{
		finish();
	}
    private void sendSMS(String phoneNumber, String message)
    {        
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, sendSMS.class), 0);                
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
        this.finish();
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode==PICK_REQUEST)
		{
			if(resultCode==RESULT_OK)
			{
				
				//Cursor cur = getContentResolver().query(data.getData(),null,null,null,null);
				//int index=cur.getColumnIndex(People.NUMBER);				
				//Log.d("col:Count",Float.toString(cur.getColumnCount()));
				//Log.d("col:index",Float.toString(index));
				//txtPhoneNo.setText(cur.getString(index));
				
				txtPhoneNo.setText(data.getData().toString().substring(26));
				//startActivity(new Intent(Intent.ACTION_VIEW, data.getData()));
				//txtPhoneNo.setText(Data.getContactLookupUri(getContentResolver(), data.getData()).toString());
				//Log.d("sel", Uri.withAppendedPath(Contacts.People.CONTENT_URI, "1").toString()); sujeong@gmail.com
				
			}
		}
	}   
}
