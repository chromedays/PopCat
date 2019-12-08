package com.example.popcat;

import java.util.Vector;

import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class EffectManager {
	private Sprite pattern;
	private Vector<GameObject> effect;
	public EffectManager(Sprite pat){
		pattern = pat;
		effect = new Vector<GameObject>();
		effect.clear();
	}
	public void AddEffect(Vec2d pos,int color)
	{
		GameObject clone = new GameObject();
		clone.SetObject(pattern, 0, 0, pos.x, pos.y, color-1, 0);
		clone.move = 1;
		clone.trans = 0.0f;
		effect.add(clone);
	}
	
	public void UpdateEffect()
	{
		for(int i=0;i<effect.size();i++)
		{
			effect.get(i).y-=2;
			switch(effect.get(i).move)
			{
			case 1:
				effect.get(i).trans+=0.1f;
				if(effect.get(i).trans>=1.0f)
					effect.get(i).move = 2;
				break;
			case 2:
				effect.get(i).trans-=0.1f;
				break;
			}
			if(effect.get(i).trans<=0.0f)
			{
				effect.remove(i--);
			}
		}
	}
	
	public void DrawEffect(GameInfo gInfo)
	{
		for(int i=0;i<effect.size();i++)
		{
			effect.get(i).DrawSprite(gInfo);
		}
	}
}
