package com.example.popcat;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class LogoScene {
	Sprite logospr = new Sprite();
	GameObject logo = new GameObject();
	
	int mode = 1;
	
	public LogoScene(GL10 gl,Context context){
		logospr.LoadSprite(gl, context, R.drawable.logo, "bg.spr");
		logo.SetObject(logospr, 0, 0, 240, 400, 0, 0);
		logo.trans = 0.0f;
		logo.ResetTimer();
	}
	public void Update()
	{
		switch(mode)
		{
		case 1:
			logo.ResetTimer();
			logo.trans+=0.05f;
			if(logo.trans>=1.0f){
				logo.trans = 1.0f;
				mode = 2;
				
			}
			break;
		case 2:
			if(System.currentTimeMillis() - logo.timer > 1000)
				mode = 3;
			break;
		case 3:
			logo.trans-=0.05f;
			if(logo.trans<=0){
				Data.GameMode = GameType.MAIN_SCENE;
				Data.Music.setLooping(true);
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
				Data.Music.start();
			}
			break;
		}
	}
	public void Draw(GameInfo gInfo)
	{
		logo.DrawSprite(gInfo);
	}
}
