package com.example.popcat;

import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class Block extends GameObject
{
	//2���� �迭���� ��ǥ�� mx1,my1 �̴�.
	//������ ���´� move�� ���� �����ȴ�.
	//������ type�� ���� �����ȴ�.
	//public float markX;
	public int mx;
	public int my;
	public int color;
	public float markY;
	public int markmy;
	public float movedit; // �ʴ� �����̴� ���ǵ带 ����
	public boolean movestate;
	public boolean bouncestate;
	private int zoom;
	public int odelay;//������������
	public Block(){
		super();
	}
	public Block(Vec2d pos,int mx,int my, int color){ // realx realy mapx mapy color
		super();
		//this.InitBlock(mx, my, color);
		this.mx = mx;
		this.my = my;
		this.color = color;
		switch(color)
		{
		case 1:
			this.SetObject(Data.BlockSprite[0], 0, 0, pos.x, pos.y, 0, 0);
			break;
		case 2:
			this.SetObject(Data.BlockSprite[1], 0, 0, pos.x, pos.y, 0, 0);
			break;
		case 3:
			this.SetObject(Data.BlockSprite[2], 0, 0, pos.x, pos.y, 0, 0);
			break;
		case 4:
			this.SetObject(Data.BlockSprite[3], 0, 0, pos.x, pos.y, 0, 0);
			break;
		case 5:
			this.SetObject(Data.OrangeSprite, 0, 0, pos.x, pos.y, 0, 0);
			break;
		}
		//markX = pos.x;
		markY = pos.y;
		markmy = my;
		//movestatus = true;
		movestate = false;
		bouncestate = false;
		zoom = 1;
		movedit = Data.BS / (60 * Data.MoveTime) ; // 1�����Ӵ� �̵��ؾߵǴ� �Ÿ��� ���Ѵ�.
		odelay = 1;
		/*if(this.color == 5)
			this.motion = 1;*/
	}
	public void InitBlock(int mx,int my,int color){
		this.mx = mx;
		this.my = my;
		this.type = color;
	}
	
	public void UpdateMove()
	{
		if(this.y < markY){
//			if(move == 0)
//			{
//				movedit = (markY - this.y) / (60 * Data.MoveTime); // �ʴ� �̵��ؾߵǴ� 
//			}
			if(movestate == false)
				movestate = true;
			this.y += movedit;
		}
		if(this.y>=markY && this.my!=markmy)
		{
			
			bouncestate = true;
			zoom = 1;
			
		}
		if(this.y >= markY)
		{
			if(movestate = true){
				//movestate = false;
				//bouncestate = true;
				//zoom = 1;
			}
			this.y = markY;
			movestate = false;
			this.my = markmy;
		}
	}
	
	public void Bounce(){
		if(bouncestate == false || movestate == true){
			return;
		}
		if(zoom == 1){
			//this.y+=48*0.1;
			this.scaley-=0.05f;
			this.scalex+=0.05f;
		}
		else if(zoom == 2){
			//this.y-=48*0.1;
			this.scaley+=0.05f;
			this.scalex-=0.05f;
		}
		if(this.scalex>1.15f)
			zoom=2;
		if(this.scalex<=1.0f){
			//this.y = markY;
			this.scaley = 1.0f;
			this.scalex = 1.0f;
			bouncestate = false;
			zoom = 1;
		}
	}
	public void OrangeDelay()
	{
		
	}
	
}
