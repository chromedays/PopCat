package com.example.popcat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Vector;

import android.R.integer;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import bayaba.engine.lib.ParseUtil;
import bayaba.engine.lib.Sprite;

public class Data {
	public static int a = 0;
	public static int GameMode = 1;
	public static int Stage = 1;
	public static boolean SoundS = true;
	public static boolean TouchS = false; // 터치상태
	public static float TouchX; // 터치 x좌표
	public static float TouchY; // 터치 y좌표
	public final static int MH = 12;
	public final static int MW = 8;
	public final static int BS = 48;
	public final static float MoveTime = 0.02f; // 한 칸을 움직일때 드는 시간
	public static int HighScore[] = new int[6];
	public static Sprite BlockSprite[];
	public static Sprite OrangeSprite = new Sprite();
	public static Sprite BlockS = new Sprite();
	public static Sprite BackSprite = new Sprite();
	public static Sprite FieldSprite = new Sprite();
	public static ParseUtil Parse = new ParseUtil();
	public static MediaPlayer Music;
	public static MediaPlayer PlayMusic;
	public static SoundPool SndPool;
	public static int[] SndBuff = new int[20];
	public static String setname = "setting.txt"; // 옵션의 세팅값을 저장하는 텍스트파일 이름
	public static String rankname = "rank.txt"; // 랭킹 스코어를 저장하는 텍스트파일 이름
	public static String fishname = "fish.txt"; // 추가 물고기의 개수를 정하는 시간정보를 저장하는 텍스트파일 이름ㄴ
	public static void InitSound(Context context)
	{
		Music = MediaPlayer.create(context, R.raw.bgm1);
		PlayMusic = MediaPlayer.create(context, R.raw.bgm2);
		//Music.setLooping(true);
		//Music.start();
		SndPool = new SoundPool(7, AudioManager.STREAM_MUSIC, 0);
		SndBuff[0] = SndPool.load(context, R.raw.cat, 0);
		SndBuff[1] = SndPool.load(context, R.raw.cat2, 0);
		SndBuff[2] = SndPool.load(context, R.raw.click, 0);
		SndBuff[3] = SndPool.load(context, R.raw.down, 0);
		SndBuff[4] = SndPool.load(context, R.raw.orange, 0);
		SndBuff[5] = SndPool.load(context, R.raw.over, 0);
		SndBuff[6] = SndPool.load(context, R.raw.spark, 0);
		
	}
	public static void PlaySound(int num)
	{
		if(SoundS == false)
			return;
		else
			SndPool.play(SndBuff[num], 1f, 1f, 0, 0, 1f);
	}
	public static void LoadRankData(Context context)
	{
		/*File file = new File("rank.txt");
		if(file.exists() == false)
			return;*/
		
		try
		{
			FileInputStream fis = context.openFileInput(rankname);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			for(int i=0;i<5;i++)
			{
				Data.HighScore[i] = Integer.parseInt(br.readLine());
			}
			fis.close();
			br.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void SaveRankData(Context context)
	{
		
		try
		{
			
			FileOutputStream fos = context.openFileOutput(rankname, context.MODE_WORLD_READABLE);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(int i=0;i<5;i++)
			{
				String str = Integer.toString(HighScore[i]);
				bw.write(str);
				bw.write("\r\n");
			}
			bw.close();
			fos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}