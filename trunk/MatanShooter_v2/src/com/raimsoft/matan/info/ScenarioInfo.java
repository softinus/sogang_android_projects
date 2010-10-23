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
		case 1:

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

		case 2:

			nScriptNum= 3;
			strScript2= new String[nScriptNum];

			strScript2[0]= "Weber: Stage2";
			strScript2[1]= "Already,Stage2.";
			strScript2[2]= "Stage2 GO.";
			break;

		case 3:

			nScriptNum= 3;
			strScript3= new String[nScriptNum];

			strScript3[0]= "Weber: Stage3";
			strScript3[1]= "Already,Stage3.";
			strScript3[2]= "Stage3 GO.";
			break;

		}



	}

}
