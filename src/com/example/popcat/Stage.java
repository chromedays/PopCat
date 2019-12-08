package com.example.popcat;

import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class Stage {
	public int stage;
	public int[] stagearray = new int[2];
	public GameObject[] stageobj = new GameObject[2];
	
	public Stage(Sprite pattern)
	{
		stage = 0;
		float startx = 211;
		
		for(int i=0;i<2;i++)
		{
			stagearray[i] = 0;
			stageobj[i] = new GameObject();
			stageobj[i].SetObject(pattern, 0, 0, startx, 120, 0, 0);
			startx+=80;
		}
		
	}
	
	public void Add(int add)
	{
		stage+=add;
		int tmp = stage;
		for(int i=1;i>=0;i--)
		{
			stagearray[i] = tmp%10;
			tmp/=10;
			stageobj[i].frame = stagearray[i];
		}
		
	}
	
	public void Set(int set)
	{
		stage=set;
		int tmp = stage;
		for(int i=1;i>=0;i--)
		{
			stagearray[i] = tmp%10;
			tmp/=10;
			stageobj[i].frame = stagearray[i];
		}
	}
	
	public void DrawStage(GameInfo gInfo)
	{
		for(int i=0;i<2;i++)
		{
			stageobj[i].DrawSprite(gInfo);
		}
	}
}
