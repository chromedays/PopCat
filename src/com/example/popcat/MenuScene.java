package com.example.popcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.provider.OpenableColumns;
import android.util.Log;
import bayaba.engine.lib.ButtonObject;
import bayaba.engine.lib.ButtonType;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.ParseUtil;
import bayaba.engine.lib.Sprite;

public class MenuScene {//메뉴씬같지만 사실은 처음에 네온사인 애니메이션만 보여주는 씬
	GameObject back1;//햇빛뒤에있는배경
	GameObject back2;// 햇빛
	GameObject back3;//쓰레기통등등
	GameObject neonlogo;//네온 로고
	GameObject fade;//페이드인/아웃 용도
	GameObject playb;//플레이 버튼
	GameObject rankingb;//랭킹 버튼
	GameObject optionb;//옵션 버튼
	GameObject black = new GameObject();
	
	ButtonObject[] button = new ButtonObject[5];
	int totalbutton = 5;
	GameObject signboard;//네온사인 간판
	Vector<GameObject> sparkList;
	GameObject fishbar = new GameObject();
	GameObject[] fish = new GameObject[7]; 
	int fishcount = 7;
	Vec2d[] fishpos = new Vec2d[7];
	
	//GameObject 
	//Sprite buttonspr = new Sprite();
	
	Sprite bspr;//버튼 스프라이트
	Sprite fadespr;//페이드인아웃
	Sprite mainimgspr;
	Sprite mainspr;
	Sprite neonspr;
	Sprite sparkspr;
	Sprite fishspr = new Sprite();
	Sprite soundspr = new Sprite();
	Sprite blackspr = new Sprite();
	Sprite howbuttonspr = new Sprite();
	public long time = 0;
	public int neonani = 5;
	public int signmode;
	public long offtimer;
	public Random rand = new Random();
	public int layer = 0;
	
	ParseUtil Parse = new ParseUtil();
	Score score;
	Sprite scoresprite = new Sprite();
	
	public MenuScene(Context context)
	{
		sparkList = new Vector<GameObject>();
		signboard = new GameObject();
		back1 = new GameObject();
		back2 = new GameObject();
		back3 = new GameObject();
		playb = new GameObject();
		rankingb = new GameObject();
		optionb = new GameObject();
		mainimgspr = new Sprite();
		neonlogo = new GameObject();
		mainspr = new Sprite();
		fadespr = new Sprite();
		//buttonspr = new Sprite();
		bspr = new Sprite();
		neonspr = new Sprite();
		sparkspr = new Sprite();
		fade = new GameObject();
		for(int i=0;i<totalbutton;i++)
		{
			button[i] = new ButtonObject();
		}
		float startx = 126+41/2;
		float starty = 18+39/2;
		for(int i=0;i<7;i++)
		{
			fish[i] = new GameObject();
			fishpos[i] = new Vec2d(startx,starty);
			startx+=41;
		}
		//Data.LoadRankData(context);
	}
	
	public void LoadData(GL10 gl, Context MainContext){
		blackspr.LoadSprite(gl, MainContext, R.drawable.black, "black.spr");
		soundspr.LoadSprite(gl, MainContext, R.drawable.option, "option.spr");
		fishspr.LoadSprite(gl, MainContext, R.drawable.fish, "fish.spr");
		sparkspr.LoadSprite(gl, MainContext, R.drawable.spark, "spark.spr");
		neonspr.LoadSprite(gl, MainContext, R.drawable.neon, "neon.spr");
		//buttonspr.LoadSprite(gl, MainContext, R.drawable.mainbutton, "mainbutton.spr");
		bspr.LoadSprite(gl, MainContext, R.drawable.mainbutton, "mainbutton.spr");
		mainspr.LoadSprite(gl, MainContext, R.drawable.main, "main.spr");
		mainimgspr.LoadSprite(gl, MainContext, R.drawable.mainback, "mainback.spr");
		fadespr.LoadSprite(gl, MainContext, R.drawable.white, "white.spr");
		back1.SetObject(mainimgspr, 0, 0, 239, 393, 0, 0);
		back2.SetObject(mainimgspr, 0, 0, 240, 330, 0, 1);
		back3.SetObject(mainimgspr, 0, 0, 240, 440, 0, 2);
		/*playb.SetObject(buttonspr, 0, 0, 240, 370, 0, 0);
		rankingb.SetObject(buttonspr, 0, 0, 240, 550, 1, 0);
		optionb.SetObject(buttonspr, 0, 0, 240, 700, 2, 0);*/
		playb.SetObject(bspr, 0, 0, 240, 370, 0, 0);
		rankingb.SetObject(bspr, 0, 0, 240, 550, 1, 0);
		optionb.SetObject(bspr, 0, 0, 240, 700, 2, 0);
		howbuttonspr.LoadSprite(gl, MainContext, R.drawable.htpbutton, "htpbutton.spr");
		button[0].SetButton(bspr, ButtonType.TYPE_ONE_CLICK, 0, 240, 370, 0);
		button[1].SetButton(bspr, ButtonType.TYPE_ONE_CLICK, 0, 240, 550, 1);
		button[2].SetButton(bspr, ButtonType.TYPE_ONE_CLICK, 0, 240, 700, 2);
		button[3].SetButton(soundspr, ButtonType.TYPE_ONE_CLICK, 1, 240, 400, 0);
		button[4].SetButton(howbuttonspr, ButtonType.TYPE_ONE_CLICK, 0, 240, 450, 0);
		signboard.SetObject(neonspr, 0, 0, 210, 157, 0, 0);
		neonlogo.SetObject(mainspr, 0, 0, 240, 100, 0, 0);
		fade.SetObject(fadespr, 0, 0, 240, 400, 0, 0);
		fishbar.SetObject(fishspr, 0, 0, 240, -40, 0, 0);
		black.SetObject(blackspr, 0, 0, 240, 400, 0, 0);
		black.trans = 0.0f;
		time = System.currentTimeMillis();
		fade.trans = 0;
		float startx = 126+41/2;
		float starty = 18+39/2-80;
		for(int i=0;i<7;i++)
		{
			fish[i].SetObject(fishspr, 0, 0, startx, starty, 1, 0);
			startx+=42;
		}
		/*back1.show = false;
		back2.show = false;
		back3.show = false;*/
		playb.show = false;
		rankingb.show = false;
		optionb.show = false;
		//signboard.show = false;
		button[3].show = false;
		back2.effect = 1;
		signmode = rand.nextInt(200);
		//back3.effect = 1;
		//neonlogo.trans = 0.3f;
		scoresprite.LoadSprite(gl, MainContext, R.drawable.score, "score.spr");
		score  = new Score(scoresprite,100,100,32);
		Data.InitSound(MainContext);
	}
	
	public void PushButton(boolean flag,float x,float y)
	{
		for(int i=0;i<totalbutton;i++) button[i].CheckLayerButton(layer,flag, x, y);
	}
	
	public void Update(Context context)
	{
		if(Data.Music.isPlaying() == false)
		{
			Data.Music.start();
		}
		layer = 0;
		black.trans = 0.0f;
		/*if(fishbar.y<40){
			fishbar.y+=1.5f;
			for(int i=0;i<7;i++)
			{
				fish[i].y+=1.5f;
			}
		}
		else if(fishbar.y>=40){
			fishbar.y = 40;
			for(int i=0;i<7;i++)
			{
				fish[i].y=18+39/2;
			}
		}*/
		if(signmode == 1)
		{
			if(signboard.frame == 0){
				signboard.frame = 1;
				offtimer = System.currentTimeMillis();
			}
		}
		if(signboard.frame == 1)
		{
			if(System.currentTimeMillis() - offtimer > 200){
				signboard.frame = 0;
				GameObject clone = new GameObject();
				clone.SetObject(sparkspr, 0, 0, rand.nextInt(371-80)+80, rand.nextInt(196-100)+100, 0, 0);
				clone.flip = rand.nextInt(2)==0 ? true:false;
				sparkList.add(clone);
				Data.PlaySound(6);
			}
		}
		for(int i=0;i<sparkList.size();i++)
		{
			sparkList.get(i).AddFrame(0.3f);
			if(sparkList.get(i).EndFrame())
			{
				sparkList.remove(i--);
			}
		}
		signmode = rand.nextInt(150);
		back2.angle+=1;
		for(int i=0;i<totalbutton;i++)
		{
			if(i == 0)
			{
				if(button[i].click == ButtonType.STATE_CLK_BUTTON){
					Data.PlaySound(2);
					Data.GameMode = GameType.LOADING_SCENE;
					button[i].ResetButton();
				}
			}
			else if(i == 1)
			{
				if(button[i].click == ButtonType.STATE_CLK_BUTTON){
					Data.PlaySound(2);
					//Data.LoadRankData(context);
					Data.GameMode = GameType.RANK_SCENE;
					button[i].ResetButton();
				}
			}
			else if(i == 2)
			{
				if(button[i].click == ButtonType.STATE_CLK_BUTTON){
					
					Data.PlaySound(2);
					Data.GameMode = GameType.OPTION_SCENE;
					button[i].ResetButton();
					button[3].show = true;
				}
			}
			else if(i==4)
			{
				if(button[i].click == ButtonType.STATE_CLK_BUTTON)
				{
					Data.PlaySound(2);
					Data.GameMode = GameType.HOW_SCENE;
					button[i].ResetButton();
				}
			}
		}
		
	}
	
	public void UpdateOption()
	{
		layer = 1;
		/*if(black.trans<0.5f)
			black.trans+=0.1f;
		else if(black.trans>=0.5f)
			black.trans = 0.5f;*/
		black.trans = 0.5f;
		if(button[3].click == ButtonType.STATE_CLK_BUTTON)
		{
			if(Data.SoundS == true){
				Data.PlaySound(2);
				button[3].motion = 1;
				Data.SoundS = false;
				Data.Music.setVolume(0, 0);
				Data.PlayMusic.setVolume(0, 0);
				button[3].ResetButton();
			}
			else if(Data.SoundS == false)
			{
				Data.PlaySound(2);
				button[3].motion = 0;
				Data.SoundS = true;
				Data.Music.setVolume(1, 1);
				Data.PlayMusic.setVolume(1, 1);
				button[3].ResetButton();
			}
		}
	}
	
	public void Draw(GameInfo gInfo){
		back1.DrawSprite(gInfo);
		back2.DrawSprite(gInfo);
		back3.DrawSprite(gInfo);
		playb.DrawSprite(gInfo);
		rankingb.DrawSprite(gInfo);
		optionb.DrawSprite(gInfo);
		signboard.DrawSprite(gInfo);
		//neonlogo.DrawSprite(gInfo);
		fade.DrawSprite(gInfo);
		fishbar.DrawSprite(gInfo);
		for(int i=0;i<sparkList.size();i++)
		{
			sparkList.get(i).DrawSprite(gInfo);
		}
		for(int i=0;i<3;i++)
		{
			button[i].DrawSprite(gInfo);
		}
		button[4].DrawSprite(gInfo);
		black.DrawSprite(gInfo);
		if(button[3].show == true)
			button[3].DrawSprite(gInfo);
		for(int i=0;i<7;i++)
		{
			fish[i].DrawSprite(gInfo);
		}
	}
	
	public void TouchEnded()
	{
		if(button[3].CheckPos((int)Data.TouchX, (int)Data.TouchY)==false){
			Data.GameMode = GameType.MAIN_SCENE;
			button[3].show = false;
		}
	}
}
