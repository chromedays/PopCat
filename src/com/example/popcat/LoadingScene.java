package com.example.popcat;

import java.io.IOException;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class LoadingScene {
	GameObject bg = new GameObject();
	Sprite bgspr = new Sprite();
	Random rand = new Random();
	int count = 1;
	public LoadingScene(GL10 gl,Context context){
		bgspr.LoadSprite(gl, context, R.drawable.loading, "loading.spr");
		bg.SetObject(bgspr, 0, 0, 240, 400, 0, rand.nextInt(2));
		bg.ResetTimer();
		count = 1;
	}
	public void Update(Context context){
		if(Data.GameMode == GameType.LOADING_SCENE && count>=1)
		{
			bg.ResetTimer();
			count--;
		}
		if(System.currentTimeMillis() - bg.timer > 3000){
			Data.GameMode=GameType.READYGO_SCENE;
			//Data.Music.create(context, R.raw.bgm2);
			Data.Music.stop();
			try {
				Data.Music.prepare();
				Data.Music.seekTo(0);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Data.PlayMusic.setLooping(true);
			
			Data.PlayMusic.start();
		}
	}
	
	public void Draw(GameInfo gInfo)
	{
		bg.DrawSprite(gInfo);
	}
}
