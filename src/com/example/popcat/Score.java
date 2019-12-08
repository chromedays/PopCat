package com.example.popcat;

import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class Score {
	public int score;
	public int[] scorearray = new int[5];
	public GameObject[] scoreobj = new GameObject[5];
	
	public Score(Sprite pattern,float startx,float starty,int fontsize)
	{
		score = 0;
		
		
		for(int i=0;i<5;i++)
		{
			scorearray[i] = 0;
			scoreobj[i] = new GameObject();
			scoreobj[i].SetObject(pattern, 0, 0, startx, starty, 0, 0);
			startx+=fontsize;
		}
	}
	
	public void Add(int add)
	{
		score+=add;
		int tmp = score;
		for(int i=4;i>=0;i--)
		{
			scorearray[i] = tmp%10;
			tmp/=10;
			scoreobj[i].frame = scorearray[i];
		}
		
	}
	
	public void Set(int set)
	{
		score = set;
		int tmp = score;
		for(int i=4;i>=0;i--)
		{
			scorearray[i] = tmp%10;
			tmp/=10;
			scoreobj[i].frame = scorearray[i];
		}
	}
	
	public void DrawScore(GameInfo gInfo,boolean skipzero)
	{
		for(int i=0;i<5;i++)
		{
			if(scoreobj[i].frame == 0 && skipzero == true && i!=4)
				continue;
			else if(scoreobj[i].frame !=0)
				skipzero = false;
			scoreobj[i].DrawSprite(gInfo);
		}
	}
}
