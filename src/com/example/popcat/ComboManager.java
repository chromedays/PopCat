package com.example.popcat;

import java.util.Vector;

import android.util.Log;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class ComboManager {
	public int combo;
	public int[] comboarray = new int[2];
	public Vector<GameObject> comboobj1;
	public Vector<GameObject> comboobj2;
	public Vector<GameObject> textobj;
	public Sprite pattern;
	public Sprite textpat;
	//public GameObject[] comboobj = new GameObject[2];
	int mode = 0;
	long timer;
	
	public ComboManager(Sprite pattern,Sprite textpattern)
	{
		
		combo = 0;
		float startx = 200;
		comboobj1 = new Vector<GameObject>();
		comboobj1.clear();
		comboobj2 = new Vector<GameObject>();
		comboobj2.clear();
		textobj = new Vector<GameObject>();
		textobj.clear();
		this.pattern = pattern;
		this.textpat = textpattern;
		/*for(int i=0;i<2;i++)
		{
			comboarray[i] = 0;
			comboobj[i] = new GameObject();
			comboobj[i].SetObject(pattern, 0, 0, startx, 200, 0, 0);
			comboobj[i].trans = 0;
			startx+=80;
		}*/
		
	}
	
	public void Add()
	{
		combo++;
		int tmp = combo;
		for(int i=1;i>=0;i--)
		{
			comboarray[i] = tmp%10;
			tmp/=10;
			//comboobj[i].frame = comboarray[i];
		}
		GameObject clone = new GameObject();
		clone.SetObject(pattern, 0, 0, 205, 300, 0, 0);
		clone.trans = 0.0f;
		clone.frame = comboarray[0];
		clone.move = 1;
		GameObject clone2 = new GameObject();
		clone2.SetObject(pattern, 0, 0, 275, 300, 0, 0);
		clone2.trans = 0.0f;
		clone2.frame = comboarray[1];
		clone2.move = 1;
		GameObject txtclone = new GameObject();
		txtclone.SetObject(textpat, 0, 0, 240, 360, 0, 0);
		txtclone.trans = 0.0f;
		if(comboarray[0]==0) //콤보가 한자리수일때
		{
			clone.show = false;
			clone2.x = 240;
		}
		if(comboarray[1] == 0)
			clone2.show = false;
		if(comboarray[0]==0 && comboarray[1] == 0)
			txtclone.show = false;
		comboobj1.add(clone);
		comboobj2.add(clone2);
		textobj.add(txtclone);
		timer = System.currentTimeMillis();
	}
	
	public void Set(int set)
	{
		String a = Integer.toString(mode);
		Log.d(a, a);
		if(combo == set){
			//mode = 3;
			//mode = 0;
			return;
		}
		combo=set;
		int tmp = combo;
		for(int i=1;i>=0;i--)
		{
			comboarray[i] = tmp%10;
			tmp/=10;
			//comboobj[i].frame = comboarray[i];
		}
		GameObject clone = new GameObject();
		clone.SetObject(pattern, 0, 0, 200, 200, 0, 0);
		clone.trans = 0.0f;
		clone.frame = comboarray[0];
		clone.move = 1;
		GameObject clone2 = new GameObject();
		clone2.SetObject(pattern, 0, 0, 250, 200, 0, 0);
		clone2.trans = 0.0f;
		clone2.frame = comboarray[1];
		clone2.move = 1;
		if(comboarray[0]==0) //콤보가 한자리수일때
		{
			clone.show = false;
			clone2.x = 200;
		}
		if(comboarray[0]==0 && comboarray[1] == 0){
			clone2.show = false;
		}
		
		comboobj1.add(clone);
		comboobj2.add(clone2);
		mode = 1;
		timer = System.currentTimeMillis();
	}
	
	public void CheckCombo()
	{
		if(System.currentTimeMillis() - timer > 5000)
			combo = 0;
	}
	
	public void DrawCombo(GameInfo gInfo)
	{
		for(int i=0;i<comboobj1.size();i++)
		{
			comboobj1.elementAt(i).DrawSprite(gInfo);
			comboobj2.elementAt(i).DrawSprite(gInfo);
			textobj.elementAt(i).DrawSprite(gInfo);
		}
		for(int i=0;i<comboobj1.size();i++)
		{
			switch(comboobj1.elementAt(i).move)
			{
			case 1:
				comboobj1.elementAt(i).trans+=0.1f;
				comboobj2.elementAt(i).trans+=0.1f;
				textobj.elementAt(i).trans+=0.1f;
				if(comboobj1.elementAt(i).trans>=1.0f)
				{
					comboobj1.elementAt(i).trans = 1.0f;
					comboobj2.elementAt(i).trans = 1.0f;
					textobj.elementAt(i).trans = 1.0f;
					comboobj1.elementAt(i).move = 2;
					comboobj1.elementAt(i).timer = System.currentTimeMillis();
				}
				break;
			case 2:
				if(System.currentTimeMillis() - comboobj1.elementAt(i).timer > 100)
				{
					comboobj1.elementAt(i).move = 3;
				}
				break;
			case 3:
				if(comboobj1.elementAt(i).trans>0.0f)
				{
					comboobj1.elementAt(i).trans-=0.1f;
					comboobj2.elementAt(i).trans-=0.1f;
					textobj.elementAt(i).trans-=0.1f;
				}
				break;
			}
		}
		for(int i=0;i<comboobj1.size();i++)
		{
			if(comboobj1.elementAt(i).trans <= 0.0f && comboobj1.elementAt(i).move == 3)
			{
				comboobj1.remove(i);
				comboobj2.remove(i);
				textobj.remove(i);
				i--;
			}
		}
		/*if(mode == 0)
		{
			timer = System.currentTimeMillis();
		}
		else if(mode == 1)
		{
			for(int i=0;i<2;i++)
			{
				if(comboobj[i].trans<1.0f)
					comboobj[i].trans+=0.1f;
			}
			if(comboobj[0].trans>=1.0f && /*System.currentTimeMillis() - timer > 1000)
			{
				timer = System.currentTimeMillis();
				mode = 2;
			}
		}
		else if(mode == 2)
		{
			for(int i=0;i<2;i++)
			{
				comboobj[i].trans=1.0f;
			}
			if(System.currentTimeMillis() - timer > 2000)
				mode = 3;
		}
		else if(mode == 3)
		{
			for(int i=0;i<2;i++)
			{
				if(comboobj[i].trans>0.0f)
					comboobj[i].trans-=0.1f;
			}
			if(comboobj[0].trans<=0.0f){
				//mode = 0;
				combo = 0;
			}
		}*/
	}

}
