package com.example.popcat;

import javax.microedition.khronos.opengles.GL10;

import bayaba.engine.lib.ButtonObject;
import bayaba.engine.lib.ButtonType;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

import android.content.Context;

public class RankScene {
	
	GameObject bg = new GameObject();
	Score[] ranking = new Score[5];
	ButtonObject backb = new ButtonObject();
	
	
	Sprite rankspr = new Sprite();
	Sprite bgspr = new Sprite();
	Sprite bbuttonspr = new Sprite();
	public RankScene(GL10 gl,Context context)
	{ 
		bbuttonspr.LoadSprite(gl, context, R.drawable.rankbackbutton, "rankbackbutton.spr");
		rankspr.LoadSprite(gl, context, R.drawable.ranktext, "ranktext.spr");
		bgspr.LoadSprite(gl, context, R.drawable.rankbg, "bg.spr");
		bg.SetObject(bgspr, 0, 0, 240, 400, 0, 0);
		ranking[0] = new Score(rankspr,217,157,35);
		ranking[1] = new Score(rankspr,217,307,35);
		ranking[2] = new Score(rankspr,217,437,35);
		ranking[3] = new Score(rankspr,217,597,35);
		ranking[4] = new Score(rankspr,217,717,35);
		for(int i=0;i<5;i++)
		{
			ranking[i].Set(Data.HighScore[i]);
		}
		backb.SetButton(bbuttonspr, ButtonType.TYPE_ONE_CLICK, 0, 59, 50, 0);
	}
	
	public void PushButton(boolean flag,float x,float y)
	{
		backb.CheckButton(flag, x, y);
	}
	
	public void Update()
	{
		for(int i=0;i<5;i++)
		{
			ranking[i].Set(Data.HighScore[i]);
		}
		if(backb.click == ButtonType.STATE_CLK_BUTTON)
		{
			backb.ResetButton();
			Data.GameMode = GameType.MAIN_SCENE;
		}
	}
	
	public void Draw(GameInfo gInfo)
	{
		bg.DrawSprite(gInfo);
		for(int i=0;i<5;i++)
		{
			ranking[i].DrawScore(gInfo, true);
		}
		backb.DrawSprite(gInfo);
	}
}
