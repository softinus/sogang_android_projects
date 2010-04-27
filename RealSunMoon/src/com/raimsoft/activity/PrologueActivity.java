package com.raimsoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.raimsoft.sound.SoundManager;

public class PrologueActivity extends Activity {

	ImageView cutImg;
	TextView txtScr;
	int scene_count=1;
	private SoundManager sm= new SoundManager(this);
	
	private boolean already_Next=false;
	
	public void NextActivity()
	{
		if(!already_Next)
		{
			Intent intent=new Intent (PrologueActivity.this, GameActivity.class);
			startActivity(intent);
			this.already_Next=true;
			this.finish();
		}		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.prologue);
		
		cutImg= (ImageView) findViewById(R.id.cut_scene);
		txtScr= (TextView) findViewById(R.id.script);
		txtScr.setTextSize(24);
		
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		
		cutImg.setImageResource(R.drawable.cut_scene1);
		txtScr.setText("남성 : 이제 예정된 날이 되었네. 어쩔 수 없는 이별의 시간인가?\n\n"
						+"여성 : 어쩔 수 없는 이별이네요. 올려 줄테니 조심해요.");
		
		sm.create();
		sm.load(0, R.raw.button);
		
		super.onStart();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if (event.getAction()==MotionEvent.ACTION_DOWN)
		{

			++scene_count;
			
			sm.play(0);
			
			switch(scene_count)
			{
			case 2:
				cutImg.setImageResource(R.drawable.cut_scene2);
				txtScr.setText("남성은 널뛰기를 이용해 하늘로 올라간다. 여성은 그 모습을 보며 소매로 눈물을 훔친다.");
				break;
			case 3:
				cutImg.setImageResource(R.drawable.cut_scene3);
				txtScr.setText("어두운 밤 산길을 어머니가 이동하고 있다.");
				break;
			case 4:
				cutImg.setImageResource(R.drawable.cut_scene4);
				txtScr.setText("갑자기 어머니의 앞에 호랑이가 나타난다.\n\n" +
						"호랑이 : 크아앙");
				break;
			case 5:
				cutImg.setImageResource(R.drawable.cut_scene5);
				txtScr.setText("누이가 어머니가 올 시간이 되었는데 안오는 것을 이상하게 생각한다\n\n"
						+"누이 : 왜 이리 안오시지?");
				break;
			case 6:
				cutImg.setImageResource(R.drawable.cut_scene6);
				txtScr.setTextSize(16);
				txtScr.setText("누이가 살고 있는 초가집 앞에 등장한 호랑이. 문을 앞두고 오누이와 호랑이가 대면하고 있다.\n"
						+"동생 : 어머니다!\n" + "호랑이 : 문을 열어주렴\n"
						+"누이 : (작은 목소리로 그리고 동생을 가로 막으며) 동생아. 어머니의 목소리가 아닌거 같은데, 아무래도 무슨 일이 생긴 거 같아.\n" 
						+"오누이는 조심해서 문 밖의 사람을 보자 호랑이라는 것을 알게 된다.");
				break;
			case 7:
				cutImg.setImageResource(R.drawable.cut_scene7);
				txtScr.setTextSize(18);
				txtScr.setText("문 밖에 있는 것이 호랑이라는 것을 알게 된 누이의 앞에 뒷문과 밖에 있는 널이 보인다.\n"
						+"누이 : 저기 널이 있어. 그리고 보니 아버지가 널을 타고 올라갔던거 기억나지?\n"
						+"동생 : 응. 누나. 우리도 저걸 타고 올라가자.");
				break;
			case 8:
				cutImg.setImageResource(R.drawable.cut_scene8);
				txtScr.setTextSize(20);
				txtScr.setText("오누이가 각자 널 위에 올라선다.\n"
						+"누이 : 근데 이거는 혼자만 올라갈 수 있네. 어쩌지?\n"
						+"동생 : 한명이 남아서 버티고, 한명은 올라가서 아버지에게 도움을 청해야지");
				break;
			case 9:
				cutImg.setImageResource(R.drawable.cut_scene9_1);
				txtScr.setTextSize(24);
				txtScr.setText("누이 : (작은 목소리로)\n내가 도움을 요청할때까지 안전하길.");
				break;
			}
			
			if (scene_count==10)
			{
				NextActivity();
			}
			
		}
		
		return super.onTouchEvent(event);
	}

}
