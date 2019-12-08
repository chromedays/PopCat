package com.example.popcat;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import bayaba.engine.lib.ButtonObject;
import bayaba.engine.lib.ButtonType;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class HowScene {
	 Sprite howsprite = new Sprite();
	 Sprite backbuttonsprite = new Sprite();
	 GameObject how = new GameObject();
	 ButtonObject backbutton = new ButtonObject();
	 
	 public HowScene(GL10 gl,Context context)
	 {
		 this.Init(gl, context);
	 }
	 
	 public void Init(GL10 gl,Context context)
	 {
		 howsprite.LoadBitmap(gl, context, R.drawable.how);
		 backbuttonsprite.LoadSprite(gl, context, R.drawable.howbackbutton, "howbackbutton.spr");
		 backbutton.SetButton(backbuttonsprite, ButtonType.TYPE_ONE_CLICK, 0, 50, 50, 0);
	 }
	 
	 public void PushButton(boolean flag,float x,float y)
	 {
		 backbutton.CheckButton(flag, x, y);
	 }
	 
	 public void Update()
	 {
		 
		 if(backbutton.click == ButtonType.STATE_CLK_BUTTON)
		 {
			 Data.PlaySound(2);
			 Data.GameMode = GameType.MAIN_SCENE;
			 backbutton.ResetButton();
		 }
	 }
	 
	 public void Draw(GameInfo gInfo)
	 {
		 howsprite.PutImage(gInfo, 0, 0);
		 backbutton.DrawSprite(gInfo);
	 }
}
