package com.example.popcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.text.method.MovementMethod;
import bayaba.engine.lib.ButtonObject;
import bayaba.engine.lib.ButtonType;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.ParseUtil;
import bayaba.engine.lib.Sprite;

public class PlayScene {
	private BlockManager bManager = new BlockManager();
	private Block tmp = new Block();
	private Score score;
	private Score endscore;
	private Score high;
	private Stage stage;
	private ComboManager combo;
	private EffectManager effect;
	
	private long timer;
	private long term; // 귤이 떨어지는 시간 간격
	private boolean gameover = false;
	
	
	private Sprite GUItextsprite = new Sprite();
	private Sprite TrashSprite = new Sprite();
	private Sprite ScoreSprite = new Sprite();
	private Sprite StageSprite = new Sprite();
	private Sprite NblockSprite = new Sprite();
	private Sprite ComboSprite = new Sprite();
	private Sprite CombotextSprite = new Sprite();
	private Sprite Readygospr = new Sprite();
	private Sprite Blackspr = new Sprite();
	private Sprite Endspr = new Sprite();//게임오버화면스프라이트
	private Sprite effectspr = new Sprite();
	private Sprite highspr = new Sprite();
	private Sprite stageeffectspr = new Sprite();
	
	private GameObject BG = new GameObject(); // 배경화면
	private GameObject FieldBG = new GameObject(); // 게임 필드 이미지
	private GameObject GUItext = new GameObject();
	private GameObject Trash = new GameObject();
	private GameObject NextColor = new GameObject();
	private GameObject NNextColor = new GameObject();
	private GameObject Stagetext = new GameObject();
	private GameObject Readygo = new GameObject();
	private GameObject Black = new GameObject();
	private GameObject EndBG = new GameObject();//끝났을때 배경이미지
	private GameObject StageEffect = new GameObject();
	////일시정지버튼
	private Sprite pausespr = new Sprite();
	private ButtonObject pauseb = new ButtonObject();
	private GameObject psheet = new GameObject();
	private ButtonObject conb = new ButtonObject();
	private ButtonObject retb = new ButtonObject();
	private ButtonObject mainb = new ButtonObject();
	private ButtonObject toggle = new ButtonObject();
	int layer = 0;
	////일시정지버튼
	//게임오버 버튼
	private Sprite endbspr = new Sprite();
	private ButtonObject endret = new ButtonObject();
	private ButtonObject endquit = new ButtonObject();
	//게임오버 버튼
	
	
	public PlayScene(GL10 gl,Context context){
		this.Init(gl,context);
	}
	public void Init(GL10 gl,Context context)
	{
		//게임오버버튼
		endbspr.LoadSprite(gl, context, R.drawable.endbutton, "endbutton.spr");
		endret.SetButton(endbspr, ButtonType.TYPE_ONE_CLICK, 2, 2+173/2, 112+131/2, 0);
		endquit.SetButton(endbspr, ButtonType.TYPE_ONE_CLICK, 2, 307+173/2, 114+131/2, 1);
		//게임오버버튼
		//일시정지
		pausespr.LoadSprite(gl, context, R.drawable.pause, "pause.spr");
		pauseb.SetButton(pausespr, ButtonType.TYPE_ONE_CLICK, 0, 30+46/2, 25+46/2, 1);
		psheet.SetObject(pausespr, 0, 1, 240, 320, 0, 0);
		conb.SetButton(pausespr, ButtonType.TYPE_ONE_CLICK, 1, 108+171/2, 260+53/2, 4);
		retb.SetButton(pausespr, ButtonType.TYPE_ONE_CLICK, 1, 108+168/2, 320+43/2, 5);
		mainb.SetButton(pausespr, ButtonType.TYPE_ONE_CLICK, 1, 130+126/2, 380+41/2, 6);
		toggle.SetButton(pausespr, ButtonType.TYPE_ONE_CLICK, 1, 321+63/2, 310+63/2, 2);
		//일시정지
		stageeffectspr.LoadSprite(gl, context, R.drawable.stageeffect, "stageeffect.spr");
		StageEffect.SetObject(stageeffectspr, 0, 0, 240, 120, 1, 0);
		highspr.LoadSprite(gl, context, R.drawable.highscore, "highscore.spr");
		effectspr.LoadSprite(gl, context, R.drawable.pop, "effect.spr");
		effect = new EffectManager(effectspr);
		timer = System.currentTimeMillis();
		Endspr.LoadSprite(gl, context, R.drawable.endbg, "bg.spr");
		Blackspr.LoadSprite(gl, context, R.drawable.black, "black.spr");
		Readygospr.LoadSprite(gl, context, R.drawable.ready, "ready.spr");
		ComboSprite.LoadSprite(gl, context, R.drawable.combo, "combo.spr");
		CombotextSprite.LoadSprite(gl, context, R.drawable.combotext, "combotext.spr");
		NblockSprite.LoadSprite(gl, context, R.drawable.cblock, "cblock.spr");
		StageSprite.LoadSprite(gl, context, R.drawable.stage, "stage.spr");
		ScoreSprite.LoadSprite(gl, context, R.drawable.score, "score.spr");
		TrashSprite.LoadSprite(gl, context, R.drawable.trash, "trash.spr");
		GUItextsprite.LoadSprite(gl, context, R.drawable.guitext, "guitext.spr");
		GUItext.SetObject(GUItextsprite, 0, 0, 240, 400, 0, 0);
		tmp = new Block(new Vec2d(72,204),0,0,1);
		//tmp = new Block();
		//tmp.SetObject(Data.BlockSprite[0], 0, 0, 100, 100, 0, 0);
		BG.SetObject(Data.BackSprite, 0, 0, 240, 400, 0, 0);
		FieldBG.SetObject(Data.FieldSprite, 0, 0, 240, 400, 0, 0);
		Trash.SetObject(TrashSprite, 0, 0, 240, 400, 0, 0);
		Black.SetObject(Blackspr, 0, 0, 240, 400, 0, 0);
		Black.trans = 0.0f;
		NextColor.SetObject(NblockSprite, 0, 0, 360, 122, bManager.cColor-1, bManager.cMax-1);
		NextColor.scalex = 1.4f;
		NextColor.scaley = 1.4f;
		//NextColor.SetObject(Data.BlockSprite[bManager.cColor-1], 0, 0, 349, 122, 0, 0);
		NNextColor.SetObject(NblockSprite, 0, 0, 432, 135, bManager.nnColor-1, bManager.nnMax-1);
		NNextColor.scalex = 0.9f;
		NNextColor.scaley = 0.9f;
		//NNextColor.SetObject(Data.BlockSprite[bManager.nnColor-1], 0, 0, 432, 135, 0, 0);
		score = new Score(ScoreSprite,31,149,32);
		endscore = new Score(ScoreSprite,150,550+800,32);
		high = new Score(highspr,230,750+800,40);
		stage = new Stage(StageSprite);
		stage.Set(Data.Stage);
		combo = new ComboManager(ComboSprite,CombotextSprite);
		//Readygo.SetObject(Readygospr, 0, 0, 240, 400, 0, 0);
		Readygo.SetObject(Readygospr, 0, 0, 600, 400, 0, 0);
		Readygo.speed = 40.0f;
		Readygo.move = 1;
		EndBG.SetObject(Endspr, 0, 0, 240, 1200, 0, 0);
		for(int i=0;i<5;i++)
		{
			//Data.HighScore[i] = 0;
		}
		//Data.LoadRankData(context);
		Data.Stage = 1;
		stage.Set(Data.Stage);
		//Stagetext.SetObject(ScoreSprite, 0, 0, 100, 100, 0, 0);
		/*CheckCount = 1;
		NumHits = 0;*/
		//bManager.MoveBlockAtDit(11, 0);
		//bManager.AddBlock(tmp);
		term = 20;
		
	}
	
	public void PushButton(boolean flag,float x,float y)
	{
		pauseb.CheckButton(flag, x, y);
		conb.CheckLayerButton(layer,flag, x, y);
		retb.CheckLayerButton(layer,flag, x, y);
		mainb.CheckLayerButton(layer,flag, x, y);
		toggle.CheckLayerButton(layer,flag, x, y);
		endret.CheckLayerButton(layer, flag, x, y);
		endquit.CheckLayerButton(layer, flag, x, y);
	}
	
	public void Draw(GameInfo gInfo){
		BG.DrawSprite(gInfo);
		GUItext.DrawSprite(gInfo);
		NextColor.DrawSprite(gInfo);
		NNextColor.DrawSprite(gInfo);
		FieldBG.DrawSprite(gInfo);
		bManager.DrawBlock(gInfo);
		Trash.DrawSprite(gInfo);
		score.DrawScore(gInfo,false);
		stage.DrawStage(gInfo);
		pauseb.DrawSprite(gInfo);
		effect.DrawEffect(gInfo);
		combo.DrawCombo(gInfo);
		StageEffect.DrawSprite(gInfo);
		//Stagetext.DrawSprite(gInfo);
		//tmp.DrawSprite(gInfo);
	}
	public void Update(Context context){
		layer = 0;
		effect.UpdateEffect();
		Black.trans = 0.0f;
		if(pauseb.click == ButtonType.STATE_CLK_BUTTON){
			Data.PlaySound(2);
			Data.GameMode = GameType.PAUSE_SCENE;
			pauseb.ResetButton();
		}
		//long term = 20-(Data.Stage-1)*2;
		if(term<2)
			term = 2;
		//term*=1000;
		if(score.score>100*Data.Stage*Data.Stage) // 스테이지가 올라가는 조건
		{	
			Data.Stage++;
			term = 20-Data.Stage;
			StageEffect.motion = 0;
			StageEffect.frame = 0;
			if(term>10)
			{
				term -= (Data.Stage-1)*2;
			}
			else
			{
				term = 10;	
			}
			
			if(Data.Stage>=25)
			{
				term = 5;
			}
			else if(Data.Stage>=20)
			{
				term = 6;
			}
			else if(Data.Stage>=7)
			{
				term = 7;
			}
		}
		if(StageEffect.motion == 0)
		{
			StageEffect.AddFrame(0.6f);
			if(StageEffect.EndFrame())
				StageEffect.motion = 1;
		}
		if(System.currentTimeMillis() - timer > term*1000)
		{
			//Data.Stage++;
			//score.Add(1);
			timer = System.currentTimeMillis();
			bManager.CancelCreate();
			bManager.CreateObstacle();
			bManager.cStatus = false;
			if(term<=10)
			{
				term-=2;//term-=(Data.Stage*0.5);
			}
			
		}

			if(20-(Data.Stage-1)*2 > 2){
				if(term<20-(Data.Stage-1)*2)
				{
					term = 20-(Data.Stage-1)*2;
				}
			}
			else
				if(term<2)
				{
					term = 2;
				}

		/*if(score.score > 100*stage.stage*stage.stage)
			Data.Stage++;*/
		stage.Set(Data.Stage);
		bManager.UpdateMove();
		if(bManager.cStatus == false)
		{
			if(bManager.MarkingInMap() == false){
				//if(bManager.IsAnimationing()==false)
					bManager.SetMove();
			}
			if(bManager.MarkingInMap() == false){
				//if(bManager.IsAnimationing()==false)
					bManager.SetMove();
			}
			//bManager.SetMove();
			//bManager.MarkingInMap();
		}
		//bManager.CheckRemoves(score,combo);
		bManager.NewCheckRemoves(score,combo,effect);
		combo.CheckCombo(); // 5초가 지났으면 콤보를 초기화시킨다.
		//bManager.GoOrange(/*score,combo*/);
		bManager.NewGoOrange();
		bManager.CreateBlockCheck(); // 터치한 부분에 블록을 생성한다.
		if(bManager.CheckGameOver() == true){
			Data.PlaySound(5);
			endscore.Set(score.score);
			//Data.LoadRankData(context);
			SortRank(endscore.score);
			high.Set(Data.HighScore[0]);
			Data.SaveRankData(context);
			//Data.LoadRankData(context);
			Data.GameMode = GameType.GAMEOVER_SCENE;
		}
	}
	
	public void ReadygoUpdate()
	{
		
		/*switch(Readygo.move)
		{
		case 1:
			Readygo.ResetTimer();
			Readygo.move = 2;
			break;
		case 2:
			if(System.currentTimeMillis() - Readygo.timer > 1000)
			{
				Readygo.ResetTimer();
				Readygo.frame = 1;
				Readygo.move = 3;
			}
			break;
		case 3:
			if(System.currentTimeMillis() - Readygo.timer > 1000)
			{
				Readygo.ResetTimer();
				Data.GameMode = GameType.PLAY_SCENE;
				timer = System.currentTimeMillis();
			}
		}
		*/
		switch(Readygo.move)
		{
		case 1:
			Readygo.x-=Readygo.speed;
			Readygo.speed-=2;
			if(Readygo.speed<=0 && Readygo.x>=260)
			{
				Readygo.x = 260;
				Readygo.move = 2;
				Readygo.ResetTimer();
			}
			break;
		case 2:
			if(System.currentTimeMillis() - Readygo.timer > 1500)
			{
				Readygo.ResetTimer();
				Readygo.x = 240;
				Readygo.frame = 1;
				Readygo.move = 3;
				//Readygo.speed = 
			}
			break;
		case 3:
			Readygo.x-=Readygo.speed;
			Readygo.speed+=2;
			if(Readygo.x<=-300)
			{
				Readygo.ResetTimer();
				Data.GameMode = GameType.PLAY_SCENE;
				timer = System.currentTimeMillis();
			}
			break;
		}
	}

	public void ReadygoDraw(GameInfo gInfo)
	{
		StageEffect.SetZoom(gInfo, 2, 2);
		BG.DrawSprite(gInfo);
		GUItext.DrawSprite(gInfo);
		NextColor.DrawSprite(gInfo);
		NNextColor.DrawSprite(gInfo);
		FieldBG.DrawSprite(gInfo);
		bManager.DrawBlock(gInfo);
		Trash.DrawSprite(gInfo);
		score.DrawScore(gInfo,false);
		stage.DrawStage(gInfo);
		combo.DrawCombo(gInfo);
		Readygo.DrawSprite(gInfo);
	}
	
	public boolean PauseUpdate(Context context)
	{
		layer = 1;
		Black.trans = 0.5f;
		if(conb.click == ButtonType.STATE_CLK_BUTTON){
			Data.PlaySound(2);
			Data.GameMode = GameType.PLAY_SCENE;
			conb.ResetButton();
		}
		else if(retb.click == ButtonType.STATE_CLK_BUTTON)
		{
			Data.PlaySound(2);
			Data.GameMode = GameType.PLAY_SCENE;
			retb.ResetButton();
			return true;
		}
		else if(mainb.click == ButtonType.STATE_CLK_BUTTON)
		{
			Data.PlaySound(2);
			mainb.ResetButton();
			Data.GameMode = GameType.MAIN_SCENE;
			Data.PlayMusic.stop();
			try {
				Data.PlayMusic.prepare();
				Data.PlayMusic.seekTo(0);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*Data.Music.reset();
			Data.Music.create(context, R.raw.bgm1);
			Data.Music.setLooping(true);*/
			
			Data.Music.start();
			return true;
		}
		if(toggle.click == ButtonType.STATE_CLK_BUTTON)
		{
			Data.PlaySound(2);
			if(Data.SoundS == true){
				Data.PlayMusic.setVolume(0, 0);
				Data.Music.setVolume(0, 0);
				Data.SoundS = false;
				toggle.ResetButton();
			}
			else if(Data.SoundS == false)
			{
				Data.PlayMusic.setVolume(1, 1);
				Data.Music.setVolume(1, 1);
				Data.SoundS = true;
				toggle.ResetButton();
			}
		}
		if(Data.SoundS == true)
		{
			toggle.motion = 2;
		}
		else if(Data.SoundS == false)
		{
			toggle.motion = 3;
		}
		
		return false;
	}
	
	public void PauseDraw(GameInfo gInfo)
	{
		Draw(gInfo);
		Black.DrawSprite(gInfo);
		psheet.DrawSprite(gInfo);
		conb.DrawSprite(gInfo);
		retb.DrawSprite(gInfo);
		mainb.DrawSprite(gInfo);
		toggle.DrawSprite(gInfo);
	}
	
	public boolean GameOverUpdate(Context context)
	{
		layer = 2;
		Black.trans = 0.5f;
		if(EndBG.y>400){
			EndBG.y-=80;
			for(int i=0;i<5;i++)
			{
				endscore.scoreobj[i].y-=80;
				high.scoreobj[i].y-=80;
			}
		}
		else if(EndBG.y<=400){
			EndBG.y = 400;
			for(int i=0;i<5;i++)
			{
				endscore.scoreobj[i].y=550;
				high.scoreobj[i].y=750;
			}
		}
		if(endret.click == ButtonType.STATE_CLK_BUTTON){
			Data.PlaySound(2);
			Data.GameMode = GameType.PLAY_SCENE;
			endret.ResetButton();
			return true;
		}
		else if(endquit.click == ButtonType.STATE_CLK_BUTTON){
			Data.PlaySound(2);
			Data.GameMode = GameType.MAIN_SCENE;
			Data.PlayMusic.stop();
			try {
				Data.PlayMusic.prepare();
				Data.PlayMusic.seekTo(0);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Data.Music.start();
			endret.ResetButton();
			return true;
		}
		return false;
	}
	
	public void GameOverDraw(GameInfo gInfo)
	{
		Draw(gInfo);
		Black.DrawSprite(gInfo);
		EndBG.DrawSprite(gInfo);
		endscore.DrawScore(gInfo, true);
		high.DrawScore(gInfo, true);
		endret.DrawSprite(gInfo);
		endquit.DrawSprite(gInfo);
	}
	
	public void SortRank(int score)
	{
		Data.HighScore[5] = score;
		for(int i=0;i<6;i++)
		{
			for(int j=i+1;j<6;j++)
			{
				if(Data.HighScore[i]<Data.HighScore[j])
				{
					int t = Data.HighScore[i];
					Data.HighScore[i] = Data.HighScore[j];
					Data.HighScore[j] = t;
				}
			}
		}
	}
	
	
	
	
	public void TouchBegan(){
		if(bManager.IsBlockMoving() == true)
			return;
		bManager.SetCreate();
		//bManager.CreateBlockCheck();
	}
	
	public void TouchEnded(){
		if(bManager.cCount < bManager.cMax){
			bManager.CancelCreate();
			bManager.cStatus = false;
		}
		if(bManager.cStatus == true)
		{
			bManager.MarkingInMap();
			bManager.SetMove();
			bManager.MarkingInMap();
			bManager.SetMove();
			bManager.cStatus = false;
			bManager.SetCreateColor();
		}
		bManager.cCount = 0;
		bManager.CheckCount = 3;
		NextColor.motion = bManager.cColor-1;
		NextColor.frame = bManager.cMax-1;
		NNextColor.motion = bManager.nnColor-1;
		NNextColor.frame = bManager.nnMax-1;
		//NextColor.SetObject(Data.BlockSprite[bManager.cColor-1], 0, 0, 349, 122, 0, 0);
		//NNextColor.SetObject(Data.BlockSprite[bManager.nnColor-1], 0, 0, 432, 135, 0, 0);
		Data.a = 0;
	}
}
