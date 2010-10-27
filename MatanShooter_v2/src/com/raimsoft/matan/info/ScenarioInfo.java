package com.raimsoft.matan.info;


public class ScenarioInfo implements IGameInfo
{


	public int nScriptCount= 1;  // 대사 카운트
	public int nScriptNum; // 대사 갯수

	public String[] strScript1;
	public String[] strScript2;
	public String[] strScript3;

	@Override
	public void Init(int nStage)
	{
		switch (nStage)
		{
		case 1: // 거리

			nScriptNum= 15;
			strScript1= new String[nScriptNum];

			strScript1[0]= "Weber: This place is not the city";
			strScript1[1]= "Already, I feel thick smell of zombie..";

			strScript1[2]= "Keane: Ha ha ha! Okay. Okay. This feels good..";
			strScript1[3]= "Everything seems to be what I want. Zombie, atmosphere, smell and so on. He he he.";
			strScript1[4]= "Zombieee~ Where are you~?";

			strScript1[5]= "Weber: Keane. Do not get excited. ";
			strScript1[6]= "If this smell…That would be a lot of zombies.";
			strScript1[7]= "Keane! Watch your back ";

			strScript1[8]= "Bang!! ";

			strScript1[9]= "Keane: Oh, Thanks.";
			strScript1[10]= "Disgust Baby zombie. He he.";
			strScript1[11]= "I’ll start shoot movies!";
			strScript1[12]= "I stood in the middle of the intersection to attract them. And I'll even shoot film.";
			strScript1[13]= "I want to shoot a movie soon. Superstar in movie!";

			strScript1[14]= "Weber: OK. Now let's get started. ";

			break;

		case 2: // 공동묘지

			nScriptNum= 11;
			strScript2= new String[nScriptNum];

			strScript2[0]= "Weber: They do not seem any more zombies.";
			strScript2[1]= "Weber: Zombie... Where does one hiding?";
			strScript2[2]= "Keane: Yeah. Yeah zombies. Where are you hiding. Film are short! short!!";
			strScript2[3]= "Weber: Do not angry. I expect that there must be somewhere in zombies together.";
			strScript2[4]= "Weber: Huh? Keane. Look at that.";
			strScript2[5]= "(Zombies are comming...)";
			strScript2[6]= "Keane:Oh. There are all together. There are many zombie.Hey, hey,go, go! Weber!";
			strScript2[7]= "Keane: I'll shoot many quantity of film. He he he. ";
			strScript2[8]= "Weber: OK. A lot of zombies are hiding in a mart. kill all zombies in the mart.";
			strScript2[9]= "Keane: Zombies Comes. Weber. Kill all the zombies! ";
			strScript2[10]= "Weber: I’ll must kill all the zombies.";
			break;

		case 3: // 공사장

			nScriptNum= 11;
			strScript3= new String[nScriptNum];

			strScript3[0]= "Keane: Hahaha! Revival zombies?! World best zombie movie! Ha ha ha. ";
			strScript3[1]= "Keane: This zombie city is a lot of very interesting things.";
			strScript3[2]= "Weber: Keane. I do not know when and where they come zombies. Stay quiet. ";
			strScript3[3]= "Keane: Ha ha ha. Don’t worry. It's your bullet never makes a wrong.";
			strScript3[4]= "Keane: It might be something more mysterious. Let's find out. Weber! ";
			strScript3[5]= "(New Zombies are comming!!!";
			strScript3[6]= "Keane: Weber, Weber, Weber!! Look at that. Zombies are the tools being used..";
			strScript3[7]= "Keane: Thanks to zombie!! My film is much superior! Hu hu. ";
			strScript3[8]= "Weber: Keane. I think zombies are dangerous. We should be careful..";
			strScript3[9]= "Keane: I'm fine. Weber! I was just so excited right now.. Ha ha ha ha! ";
			strScript3[10]= "Weber: Okay. Zombies. I'll kill you all.";
			break;

		}



	}

}
