package com.example.popcat;

import android.content.Context;
import android.location.Address;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;
import bayaba.engine.lib.helper.gamemain.BayabaGameMain;

public class GameMain extends BayabaGameMain {
	private PlayScene play;
	private MenuScene menu;
	private LoadingScene loading;
	private LogoScene logo;
	private RankScene rank;
	private HowScene how;
	private GameObject tmp = new GameObject();
	public GameMain(Context context, GameInfo info) {
		super(context, info);
		initPatterns(10);
		Data.BlockSprite = new Sprite[4];
		for(int i=0;i<4;i++)
			Data.BlockSprite[i] = new Sprite();
		menu = new MenuScene(MainContext);
		// TODO Auto-generated constructor stub
		//Data.BG.LoadSprite(gl, MainContext,R.drawable.bg, "bg.spr");
		Data.GameMode = GameType.LOGO_SCENE;
	}
	
	@Override
	public void LoadData() {
		Data.BackSprite.LoadSprite(gl, MainContext, R.drawable.bg, "bg.spr");
		Data.BlockS.LoadSprite(gl, MainContext, R.drawable.block1, "block1.spr");
		Data.BlockSprite[0].LoadSprite(gl, MainContext, R.drawable.block1, "block1.spr");
		Data.BlockSprite[1].LoadSprite(gl, MainContext, R.drawable.block2, "block2.spr");
		Data.BlockSprite[2].LoadSprite(gl, MainContext, R.drawable.block3, "block3.spr");
		Data.BlockSprite[3].LoadSprite(gl, MainContext, R.drawable.block4, "block4.spr");
		Data.OrangeSprite.LoadSprite(gl, MainContext, R.drawable.orange, "orange.spr");
		Data.FieldSprite.LoadSprite(gl, MainContext, R.drawable.field,"field.spr");
		Pattern[0].LoadSprite(gl, MainContext, R.drawable.bg, "bg.spr");
		tmp.SetObject(Data.BlockSprite[0], 0, 0, 100, 100, 0, 0);
		play = new PlayScene(gl,MainContext);
		loading = new LoadingScene(gl, MainContext);
		logo = new LogoScene(gl, MainContext);
		rank = new RankScene(gl, MainContext);
		menu.LoadData(gl, MainContext);
		how = new HowScene(gl, MainContext);
		for(int i=0;i<6;i++)
		{
			Data.HighScore[i] = 0;
		}
		Data.LoadRankData(MainContext);
	}
	
	@Override
	protected void UpdateGame() {
		if(Data.GameMode == GameType.LOGO_SCENE)
		{
			logo.Update();
		}
		else if(Data.GameMode == GameType.MAIN_SCENE)
			menu.Update(MainContext);
		else if(Data.GameMode == GameType.OPTION_SCENE)
		{
			menu.UpdateOption();
		}
		else if(Data.GameMode == GameType.RANK_SCENE)
		{
			rank.Update();
		}
		else if(Data.GameMode == GameType.LOADING_SCENE)
			loading.Update(MainContext);
		else if(Data.GameMode == GameType.READYGO_SCENE)
		{
			play.ReadygoUpdate();
		}
		else if(Data.GameMode == GameType.PLAY_SCENE)
		{
			play.Update(MainContext);
		}
		else if(Data.GameMode == GameType.PAUSE_SCENE){
			if(play.PauseUpdate(MainContext)==true)
			{
				loading = new LoadingScene(gl, MainContext);
				play = new PlayScene(gl, MainContext);
			}
		}
		else if(Data.GameMode == GameType.GAMEOVER_SCENE)
		{
			if(play.GameOverUpdate(MainContext)==true)
			{
				loading = new LoadingScene(gl, MainContext);
				play = new PlayScene(gl, MainContext);
			}
		}
		else if(Data.GameMode == GameType.HOW_SCENE)
		{
			how.Update();
		}
	}
	
	@Override
	public void DoGame() {
		UpdateGame();
		if(Data.GameMode == GameType.LOGO_SCENE)
		{
			logo.Draw(gInfo);
		}
		else if(Data.GameMode == GameType.MAIN_SCENE)
			menu.Draw(gInfo);
		else if(Data.GameMode == GameType.OPTION_SCENE)
			menu.Draw(gInfo);
		else if(Data.GameMode == GameType.RANK_SCENE)
		{
			rank.Draw(gInfo);
		}
		else if(Data.GameMode == GameType.LOADING_SCENE)
			loading.Draw(gInfo);
		else if(Data.GameMode == GameType.READYGO_SCENE)
		{
			play.ReadygoDraw(gInfo);
		}
		else if(Data.GameMode == GameType.PLAY_SCENE)
		{
			Data.FieldSprite.PutImage(gInfo, 0, 40);
			play.Draw(gInfo);
			
			//tmp.DrawSprite(gInfo);
		}
		else if(Data.GameMode == GameType.PAUSE_SCENE){
			play.PauseDraw(gInfo);
		}
		else if(Data.GameMode == GameType.GAMEOVER_SCENE)
		{
			play.GameOverDraw(gInfo);
		}
		else if(Data.GameMode == GameType.HOW_SCENE)
		{
			how.Draw(gInfo);
		}
		//Data.BG.PutImage(gInfo, 0, 0);
		
	}
	
	public void TouchBegan()
	{
		if(Data.GameMode == GameType.LOGO_SCENE)
		{
			
		}
		else if(Data.GameMode == GameType.MAIN_SCENE)
		{
			menu.PushButton(true, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.RANK_SCENE)
		{
			rank.PushButton(true, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.OPTION_SCENE)
		{
			menu.PushButton(true, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.LOADING_SCENE)
		{
			
		}
		else if(Data.GameMode == GameType.READYGO_SCENE)
		{
			
		}
		else if(Data.GameMode == GameType.PLAY_SCENE)
		{
			play.TouchBegan();
			play.PushButton(true, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.PAUSE_SCENE){
			play.PushButton(true, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.GAMEOVER_SCENE)
		{
			play.PushButton(true, Data.TouchX, Data.TouchY);
		}
		
		else if(Data.GameMode == GameType.HOW_SCENE)
		{
			how.PushButton(true, Data.TouchX, Data.TouchY);
		}
	}
	
	public void TouchMove()
	{
		if(Data.GameMode == GameType.LOGO_SCENE)
		{
			
		}
		else if(Data.GameMode == GameType.MAIN_SCENE)
		{
			menu.PushButton(true, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.RANK_SCENE)
		{
			rank.PushButton(true, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.OPTION_SCENE)
		{
			menu.PushButton(true, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.READYGO_SCENE)
		{
			
		}
		else if(Data.GameMode == GameType.PLAY_SCENE)
		{
			play.PushButton(true, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.PAUSE_SCENE){
			play.PushButton(true, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.GAMEOVER_SCENE)
		{
			play.PushButton(true, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.HOW_SCENE)
		{
			how.PushButton(true, Data.TouchX, Data.TouchY);
		}
	}
	
	public void TouchEnded()
	{
		//Data.LoadRankData(MainContext);
		if(Data.GameMode == GameType.LOGO_SCENE)
		{
			
		}
		else if(Data.GameMode == GameType.MAIN_SCENE){
			menu.PushButton(false, Data.TouchX, Data.TouchY);
			//Data.GameMode = 2;
		}
		else if(Data.GameMode == GameType.RANK_SCENE)
		{
			rank.PushButton(false, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.OPTION_SCENE){
			menu.PushButton(false, Data.TouchX, Data.TouchY);
			menu.TouchEnded();
		}
		else if(Data.GameMode == GameType.READYGO_SCENE)
		{
			
		}
		else if(Data.GameMode == GameType.PLAY_SCENE){
			play.TouchEnded();
			play.PushButton(false, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.PAUSE_SCENE){
			play.PushButton(false, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.GAMEOVER_SCENE)
		{
			play.PushButton(false, Data.TouchX, Data.TouchY);
		}
		else if(Data.GameMode == GameType.HOW_SCENE)
		{
			how.PushButton(false, Data.TouchX, Data.TouchY);
		}
	}
}
