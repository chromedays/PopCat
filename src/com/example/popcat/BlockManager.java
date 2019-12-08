package com.example.popcat;

import java.util.Random;
import java.util.Vector;

import android.graphics.Rect;
import android.util.Log;
import bayaba.engine.lib.GameInfo;

public class BlockManager {
	
	private Vector<Block> bList; // 
	private int createX;
	private int createY;
	private int field[][] = new int[Data.MH][Data.MW];
	private Vec2d realfield[][] = new Vec2d[Data.MH][Data.MW];
	private PRect fieldrect[][] = new PRect[Data.MH][Data.MW];
	private Vector<Point> comboList = new Vector<Point>();
	public boolean cStatus;
	public int cCount;
	public int cMax;
	public int cColor;
	public int nnColor;
	public int nnMax;
	private Random rand = new Random();
	private long combotimer;
	private Vector<Vector<Point>> DeleteList = new Vector<Vector<Point>>();
	private Vector<Vector<Block>> DeleteList2 = new Vector<Vector<Block>>();
	private Vector<Vector<Block>> dlist = new Vector<Vector<Block>>();
	
	public int CheckCount; // 게임 로직함수를 한번에 실행하는 횟수
	public int NumHits; // 로직함수 실행시 블록의 연결개수 (7이상이 되면 블록이 터진다.)
	public int Combo; //애니메이션 실행할때 콤보 추가
	public int ShowCombo; //터질띠 콤보 추가
	public long ComboTimer;
	
	public BlockManager(){
		
		bList = new Vector<Block>();
		bList.clear();
		comboList.clear();
		Combo = 0;
		ShowCombo = 0;
		Vec2d start = new Vec2d(72,190);
		int startx = 72;
		int starty = 229;
		realfield[0][0] = new Vec2d(72,190);
		for(int i = 0; i < Data.MH; i++){
			int tmpx = startx;
			for(int j = 0; j < Data.MW; j++){
				realfield[i][j] = new Vec2d(startx,starty);
				startx+=Data.BS;
			}
			startx = tmpx;
			starty += Data.BS;
		}
		startx = 48;
		starty = 205;
		CheckCount = 3;
		NumHits = 0;
		for(int i = 0; i < Data.MH; i++){
			startx = 48;
			for(int j = 0; j < Data.MW; j++){
//				fieldrect[i][j] = new PRect(realfield[i][j].x-Data.BS/2,realfield[i][j].y-Data.BS/2,
//											realfield[i][j].x+Data.BS/2,realfield[i][j].y+Data.BS/2);
				fieldrect[i][j] = new PRect(startx,starty,startx+Data.BS,starty+Data.BS);
				field[i][j] = 0;
				startx+=Data.BS;
			}
			starty+=Data.BS;
		}
		cStatus = false;
		cCount = 0;
		cMax = rand.nextInt(4)+1;
		nnMax = rand.nextInt(4)+1;
		cColor = rand.nextInt(4)+1;
		nnColor = rand.nextInt(4)+1;
	}
	
	
	public void SetCreateColor()
	{
		cColor = nnColor;
		nnColor = rand.nextInt(4)+1;
		cMax = nnMax;
		nnMax = rand.nextInt(4)+1;
	}
	
	public void CreateBlockCheck() // 이전의 MakeBlock과 같다 - 터치한부분에 블록을 생성하는 함수
	{
		if(cCount>=cMax)
			return;
		if(Data.TouchS == true && cStatus == true)
		{
			for(int i=0;i<Data.MH;i++)
			{
				for(int j=0;j<Data.MW;j++)
				{
					if(TouchCrashCheck(fieldrect[i][j])==true){
//						Data.a = 1;
						if(field[i][j]!=0)
							continue;
						/*field[i][j] = 100;
						boolean isattached = false;
						if(i>0){
							if(field[i-1][j] !=0 && GetBlock(j, i-1) == null){
								isattached = true;
							}
						}
						if(i<Data.MH-1){
							if(field[i+1][j] !=0 && GetBlock(j, i+1) == null){
								isattached = true;
							}
						}
						if(j>0){
							if(field[i][j-1] !=0 && GetBlock(j-1, i) == null){
								isattached = true;
							}
						}
						if(j<Data.MW-1){
							if(field[i][j+1] !=0 && GetBlock(j+1, i) == null){
								isattached = true;
							}
						}//주변에 생성할 블록이 붙어있는지 아닌지 테스트
						if(cCount == 0)
							isattached = true;
						if(isattached == false){
							field[i][j] = 0;
							continue;
						}*/
						this.AddBlock(new Point(j,i));
						//field[i][j] = cColor;
						return;
					}

				}
			}
		}
	}
	
	public void CreateObstacle()
	{
		/*int size = Data.Stage;
		if(size>=8){
			size = 8;
			for(int i=0;i<Data.MW;i++)
			{
				if(field[0][i]==0)
					bList.addElement(new Block(realfield[0][i],i,0,5));
			}
			this.MarkingInMap();
			this.SetMove();
			this.MarkingInMap();
			this.SetMove();
			return;
		}
		

		int[] tmp = new int[size];
		
		for(int i =0;i<size;i++)
		{
			tmp[i] = rand.nextInt(Data.MW);
			for(int j=0;j<i;j++)
			{
				if(tmp[j] == tmp[i] || field[0][tmp[i]] !=0){
					i--;
					break;
				}
			}
		}
		for(int i =0;i<size;i++)
		{
			bList.addElement(new Block(realfield[0][tmp[i]],tmp[i],0,5));
		}
		for(int i=0;i<Data.MW;i++)
		{
			if(field[0][i]==0)
				bList.addElement(new Block(realfield[0][i],i,0,rand.nextInt(4)+1));
		}
		int i= 0;
		int j=0;
		int iscontinue = 0;
		while(i<size)
		{
			iscontinue = 0;
			tmp[i] = rand.nextInt(Data.MW);
			if(field[0][tmp[i]]!=0)
				continue;
			for(j=0;j<i;j++)
			{
				if(tmp[i] == tmp[j]){
					iscontinue = 1;
					break;
				}
			}
			if(iscontinue == 1)
				continue;
			i++;
		}
		for(i=0;i<size;i++)
		{
			for(j=i;j<size;j++)
			{
				if(tmp[i]>tmp[j])
				{
					int t;
					t = tmp[j];
					tmp[j] = tmp[i];
					tmp[i] = t;
				}
			}
		}
		j=0;
		for(i=0;i<Data.MW;i++)
		{
			if(field[0][i]!=0)
				continue;
			if(j<size){
				if(i == tmp[j]){
					bList.addElement(new Block(realfield[0][i],i,0,5));
					j++;
					continue;
				}
			}
			bList.addElement(new Block(realfield[0][i],i,0,rand.nextInt(4)+1));
			//this.AddBlock(new Point(i,0));
		}
		this.MarkingInMap();
		this.SetMove();
		this.MarkingInMap();
		this.SetMove();*/
		for(int i=0;i<Data.MW;i++)
		{
			if(field[0][i]!=0)
				continue;
			bList.addElement(new Block(realfield[0][i],i,0,/*rand.nextInt(4)+1*/5));
		}
	}
	
	public void CancelCreate()
	{
		while(cCount>=1)
		{
			field[bList.lastElement().my][bList.lastElement().mx]=0;
			bList.remove(bList.lastElement());
			cCount--;
		}
	}
	
	public boolean AddBlock(Point mp)
	{
		
		/*if(!(mp.x <= createX+2 && mp.x >= createX-2 
				&& mp.y <= createY+2 && mp.y >= createY-2))
			return false;*/
//		if(cCount >= cMax)
//			return false;
		int x = mp.x;
		int y = mp.y;
		if(bList.size()>=1)
		
		for(int i=0;i<bList.size();i++){
			if(bList.elementAt(i).mx == x && bList.elementAt(i).my == y){
			Data.a = 1;
				return false;
			}
			/*if(field[y][x]!=0)
				Data.a = 1;*/
		}
		cCount++;
		bList.addElement(new Block(realfield[mp.y][mp.x],mp.x,mp.y,cColor));
		return true;
	}
	
	public void AddBlock(Block tmp)
	{
		bList.addElement(tmp);
	}
	
	public void DrawBlock(GameInfo gInfo)
	{
		for(int i = 0; i < bList.size(); i++){
			bList.elementAt(i).DrawSprite(gInfo);
		}
		//bList.elementAt(0).DrawSprite(gInfo);
		/*for(int i=0;i<Data.MH;i++)
		{
			for(int j=0;j<Data.MW;j++)
			{
				if(field[i][j]>0)
					Data.BlockSprite[0].PutImage(gInfo, realfield[i][j].x, realfield[i][j].y);
			}
		}*/
	}
	
	public void UpdateMove()
	{
		for(int i = 0; i < bList.size(); i++){
			bList.elementAt(i).UpdateMove();
			bList.elementAt(i).Bounce();
		}
	}
	
	public void MoveBlockAtDit(int dit,int index)
	{
		if(dit == 0)
		{
			return;
		}
		Block clone = bList.elementAt(index);
		clone.markY = clone.y + dit * Data.BS;
		clone.markmy = clone.my+dit;
		//clone.my+=dit;
	}
	
	public boolean IsBlockMoving()
	{
		for(int i = 0; i < bList.size(); i++){
			if(bList.elementAt(i).movestate == true)
				return true;
		}
		return false;
	}
	
	public void SetCreate()
	{
		for(int i=0;i<Data.MH;i++)
		{
			for(int j=0;j<Data.MW;j++)
			{
				if(TouchCrashCheck(fieldrect[i][j])){
					cStatus = true;
					createX = j;
					createY = i;
					/*if(createX == Data.MW-1)
						createX--;
					else if(createX == 0)
						createX++;
					if(createY == Data.MH-1)
						createY--;
					else if(createY == 0)
						createY++;*/
				}
			}
		}
	}
	
	public boolean TouchCrashCheck(PRect rect)
	{
		if(rect.bottom < Data.TouchY|| 
		   rect.top > Data.TouchY	||
		   rect.right < Data.TouchX ||
		   rect.left > Data.TouchX)
			return false;
		else
			return true;
			
	}
	
	public boolean MarkingInMap()
	{
		int[][] tmpfield = new int[Data.MH][Data.MW];
		for(int i = 0; i < Data.MH; i++){
			for(int j = 0; j < Data.MW; j++){
				tmpfield[i][j] =  field[i][j];
			}
		}
		boolean ret = true;
		ClearField();
		for(int i=0;i<bList.size();i++)
		{
			if(tmpfield[bList.elementAt(i).my][bList.elementAt(i).mx]!=bList.elementAt(i).color)
				ret = false;
			if(bList.elementAt(i).motion == 1)
				field[bList.elementAt(i).my][bList.elementAt(i).mx] = bList.elementAt(i).color * -1;
			else
				field[bList.elementAt(i).my][bList.elementAt(i).mx] = bList.elementAt(i).color;
		}
		return ret;
	}
	
	public void ClearField()
	{
		for(int i = 0; i < Data.MH; i++){
			for(int j = 0; j < Data.MW; j++){
				field[i][j] = 0;
			}
		}
	}
	
	public int GetMaxBlockCount(){
		return bList.size();
		
	}
	
	public void SetMove() // 이전 프로젝트의 MoveBlock과 같다
	{
		for(int i=0;i<bList.size();i++)
		{
			if(bList.elementAt(i).motion == 1 && bList.elementAt(i).color!=5)
				continue;
			if(bList.elementAt(i).movestate == true)
				continue;
			if(bList.elementAt(i).y != bList.elementAt(i).markY)
				continue;
			int dit = (Data.MH-1)-bList.elementAt(i).my;
			for(int j=bList.elementAt(i).my+1;j<Data.MH;j++)
			{
				if(field[j][bList.elementAt(i).mx]!=0)
					dit--;
			}
			//Data.Stage = dit; 
			MoveBlockAtDit(dit, i);
		}
	}
	
	public void NewCheckRemoves(Score score,ComboManager combo,EffectManager effect){
		if(IsBlockMoving() == true)
			return;
		for(int i=0;i<bList.size();i++)
		{
			NumHits = 0;
			if(bList.elementAt(i).color == 5)
				continue;
			Vector<Block> plist = new Vector<Block>();
			NewCheckForFF(bList.elementAt(i).mx, bList.elementAt(i).my, bList.elementAt(i).color, plist);
			NewDeleteBlockFromList(plist);
		}
		
		//블럭 애니메이션체크, 주변에 새로 붙은 블럭있는지검사 는 나중에;
		for(int i=0;i<dlist.size();i++)
		{
			boolean del = false;
			int color = dlist.get(i).get(0).color;
			for(int j=0;j<dlist.get(i).size();j++)
			{
				Block clone = dlist.get(i).get(j);
				/////
				if(clone.mx-1 >=0)
				{
					if(field[clone.my][clone.mx-1]!=0){
						if(GetBlock(clone.mx-1, clone.my).color==color)
						{
							if(CheckDList(dlist.get(i), clone.mx-1, clone.my)==false)
								dlist.get(i).add(GetBlock(clone.mx-1, clone.my));
						}
					}
				}
				if(clone.mx+1 <=Data.MW-1)
				{
					if(field[clone.my][clone.mx+1]!=0){
						if(GetBlock(clone.mx+1, clone.my).color==color)
						{
							if(CheckDList(dlist.get(i), clone.mx+1, clone.my)==false)
								dlist.get(i).add(GetBlock(clone.mx+1, clone.my));
						}
					}
				}
				if(clone.my-1 >=0)
				{
					if(field[clone.my-1][clone.mx]!=0){
						if(GetBlock(clone.mx, clone.my-1).color==color)
						{
							if(CheckDList(dlist.get(i), clone.mx, clone.my-1)==false)
								dlist.get(i).add(GetBlock(clone.mx, clone.my-1));
						}
					}
				}
				if(clone.my+1 <=Data.MH-1)
				{
					if(field[clone.my+1][clone.mx]!=0){
						if(GetBlock(clone.mx, clone.my+1).color==color)
						{
							if(CheckDList(dlist.get(i), clone.mx, clone.my+1)==false)
								dlist.get(i).add(GetBlock(clone.mx, clone.my+1));
						}
					}
				}
				/////
				if(clone.motion == 0)
					clone.motion = 1;
				if(j==0)
				{
					if(clone.EndFrame())
						del = true;
					else
						clone.AddFrame(0.5f);
				}
				else
				{
					clone.frame = dlist.get(i).get(j-1).frame;
				}
				/*else if(clone.EndFrame()){
					//bList.remove(clone);
					del = true;
				}
				else
					clone.AddFrame(0.5f);*/
			}
			if(del == true){
				combo.Add();
				score.Add(dlist.get(i).size()*Data.Stage*combo.combo);
				
				for(int j=0;j<dlist.get(i).size();j++){
					effect.AddEffect(realfield[dlist.get(i).get(j).my][dlist.get(i).get(j).mx], dlist.get(i).get(j).color);
					bList.remove(dlist.get(i).get(j));
				}
				Data.PlaySound(rand.nextInt(2));
				dlist.remove(i--);
			}
		}
	}
	
	public boolean CheckDList(Vector<Block> plist, int mx,int my) // 제거목록에 추가하기 이전에 이전에 추가를 이미했었는지확인
	{
		for(int i=0;i<plist.size();i++)
		{
			if(plist.get(i).mx == mx && plist.get(i).my == my)
				return true;
		}
		return false;
	}
	public void NewCheckForFF(int mx,int my,int color,Vector<Block> plist)
	{
		if(mx<0 || mx>Data.MW-1 || my<0 || my>Data.MH-1)
			return;
		else if(field[my][mx]!=color)
			return;
		else if(field[my][mx] == 5)
			return;
		else if(bList.indexOf(GetBlock(mx,my))>=bList.size()-cCount)
			return;
		else
		{
			NumHits+=1;
			plist.add(GetBlock(mx,my));
			field[my][mx] = -color;
			NewCheckForFF(mx+1, my, color, plist);
			NewCheckForFF(mx, my+1, color, plist);
			NewCheckForFF(mx-1, my, color, plist);
			NewCheckForFF(mx, my-1, color, plist);
		}
	}
	
	public void NewDeleteBlockFromList(Vector<Block> plist)
	{
		if(NumHits>=7)
		{
			dlist.add(plist);
		}
		//dlist.add(plist);
	}
	
	public void NewGoOrange()
	{
		for(int i=0;i<bList.size();i++)
		{
			Block clone = bList.get(i);
			if(clone.color == 5 && clone.motion == 0)
			{
				if(clone.mx-1 >=0)
				{
					if(field[clone.my][clone.mx-1]!=0){
						if(GetBlock(clone.mx-1, clone.my).motion == 1 && GetBlock(clone.mx-1, clone.my).color!=5)
						{
							clone.motion = 1;
						}
					}
				}
				if(clone.mx+1 <=Data.MW-1)
				{
					if(field[clone.my][clone.mx+1]!=0){
						if(GetBlock(clone.mx+1, clone.my).motion == 1 && GetBlock(clone.mx+1, clone.my).color!=5)
						{
							clone.motion = 1;
						}
					}
				}
				if(clone.my-1 >=0)
				{
					if(field[clone.my-1][clone.mx]!=0){
						if(GetBlock(clone.mx, clone.my-1).motion == 1 && GetBlock(clone.mx, clone.my-1).color!=5)
						{
							clone.motion = 1;
						}
					}
				}
				if(clone.my+1 <=Data.MH-1)
				{
					if(field[clone.my+1][clone.mx]!=0){
						if(GetBlock(clone.mx, clone.my+1).motion == 1 && GetBlock(clone.mx, clone.my+1).color!=5)
						{
							clone.motion = 1;
						}
					}
				}
			}
			else if(clone.color == 5 && clone.motion == 1)
			{
				if(clone.EndFrame())
				{
					Data.PlaySound(4);
					bList.remove(clone);
					i--;
				}
				else
					clone.AddFrame(0.5f);
			}
		}
	}
	
	public void CheckRemoves(Score score,ComboManager cmanager){
		if(IsBlockMoving() == true)
			return;
		/*if(CheckCount <= 0)
			return;
		else if(CheckCount >= 1)
			CheckCount--;*/
		for(int i=0;i<bList.size();i++)
		{
			NumHits = 0;
			if(bList.elementAt(i).color == 5)
				continue;
			Vector<Point> plist = new Vector<Point>();
			/*Vector<Block> plist2 = new Vector<Block>();*/
			CheckForFloodFill(bList.elementAt(i).mx, bList.elementAt(i).my, bList.elementAt(i).color,plist);
			/*CheckForFloodFill(bList.elementAt(i).mx, bList.elementAt(i).my, bList.elementAt(i).color,plist2);*/
			//DeleteList.add(plist);
			//DeleteBlockAfterFF(score,bList.elementAt(i).color);
			DeleteBlockFromList(score, plist,bList.elementAt(i).color);
			
		}
		int[] dx = {0,1,0,-1};
		int[] dy = {-1,0,1,0};
		for(int i=0;i<DeleteList.size();i++)
		{
			boolean del = false;
			Point fp = DeleteList.get(i).get(0);//first poisition
			for(int j=0;j<DeleteList.get(i).size();j++)
			{
				Point tpos = DeleteList.get(i).get(j);
				int tx,ty;
				for(int k=0;k<4;k++) // 주위에 귤이있는지 검사 
				{
					ty = tpos.y+dy[k];
					tx = tpos.x+dx[k];
					if(ty<0 || ty>Data.MH-1 || tx<0 || tx>Data.MW-1)
						continue;
					if(field[ty][tx] == 5){
						if(GetBlock(tx, ty).motion == 0)
							GetBlock(tx,ty).motion = 1;
						//RemoveBlock(tx, ty);
					}
					/*else if(field[ty][tx] == GetBlock(tpos.x,tpos.y).color)
					{
						field[ty][tx] = -field[ty][tx];
						DeleteList.get(i).add(new Point(tx,ty));
					}*/
				}
				
				if(GetBlock(tpos.x,tpos.y).motion == 0) //터지기전 애니메이션상태로 만든다
					GetBlock(tpos.x,tpos.y).motion = 1;
				if(GetBlock(tpos.x,tpos.y).motion == 1 && GetBlock(tpos.x,tpos.y).EndFrame()){//애니메이션의 마지막프레임이 될경우 터진다.
					bList.remove(GetBlock(tpos.x,tpos.y));
					del = true;
				}
				else if(j == 0 && GetBlock(tpos.x,tpos.y).motion == 1)
					GetBlock(tpos.x,tpos.y).AddFrame(0.5f);
				else if(GetBlock(tpos.x,tpos.y).motion == 1)
					GetBlock(tpos.x,tpos.y).frame = GetBlock(fp.x,fp.y).frame;
				
			}
			if(del == true){//해당 좌표리스트의 블럭들이 터졌을경우 그 리쇼트를 삭제한다.
				int tcount = DeleteList.get(i).size();
				cmanager.Add();
				score.Add(tcount*Data.Stage+(tcount*cmanager.combo));
				DeleteList.remove(i--);
				
				
			}
		}
		////
		/*for(int i=0;i<DeleteList2.size();i++)
		{
			boolean del = false;
			Block fb = DeleteList2.get(i).get(0);
			Point fp = new Point(DeleteList2.get(i).get(0).mx,DeleteList2.get(i).get(0).my);//first poisition
			for(int j=0;j<DeleteList2.get(i).size();j++)
			{
				Block clone = DeleteList2.get(i).get(j);
				Point tpos = new Point(DeleteList2.get(i).get(j).mx,DeleteList2.get(i).get(j).my);
				int tx,ty;
				for(int k=0;k<4;k++) // 주위에 귤이있는지 검사 
				{
					ty = tpos.y+dy[k];
					tx = tpos.x+dx[k];
					if(ty<0 || ty>Data.MH-1 || tx<0 || tx>Data.MW-1)
						continue;
					if(field[ty][tx] == 5){
						if(GetBlock(tx, ty).motion == 0)
							GetBlock(tx,ty).motion = 1;
						//RemoveBlock(tx, ty);
					}
				}
				if(clone.motion == 0)
					clone.motion = 1;
				if(GetBlock(tpos.x,tpos.y).motion == 0) //터지기전 애니메이션상태로 만든다
					GetBlock(tpos.x,tpos.y).motion = 1;
				else if(clone.motion == 1 && clone.EndFrame()){
					bList.remove(clone);
					del = true;
				}
				else if(j==0 && clone.motion == 1)
				{
					clone.AddFrame(0.5f);
				}
				else if(clone.motion == 1)
					clone.frame = fb.frame;
				if(GetBlock(tpos.x,tpos.y).motion == 1 && GetBlock(tpos.x,tpos.y).EndFrame()){//애니메이션의 마지막프레임이 될경우 터진다.
					bList.remove(GetBlock(tpos.x,tpos.y));
					del = true;
				}
				else if(j == 0 && GetBlock(tpos.x,tpos.y).motion == 1)
					GetBlock(tpos.x,tpos.y).AddFrame(0.5f);
				else if(GetBlock(tpos.x,tpos.y).motion == 1)
					GetBlock(tpos.x,tpos.y).frame = GetBlock(fp.x,fp.y).frame;
				
			}
			if(del == true){//해당 좌표리스트의 블럭들이 터졌을경우 그 리쇼트를 삭제한다.
				int tcount = DeleteList2.get(i).size();
				cmanager.Add();
				score.Add(tcount*Data.Stage+(tcount*cmanager.combo));
				DeleteList2.remove(i--);
				
				
			}
		}*/
	}
	
	public void CheckForFloodFill(int mx,int my,int color,Vector<Point> plist/*Vector<Block> plist2*/)
	{
		/*for(int i=0;i<plist2.size();i++)
		{
			if(mx == plist2.get(i).mx && my == plist2.get(i).my)
				return;
		}*/
		if(mx<0 || mx>Data.MW-1 || my<0 || my>Data.MH-1)
			return;
		else if(field[my][mx]!=color)
			return;
		else if(field[my][mx]<0)
			return;
		/*else if(GetBlock(mx,my).movestate = true)
			return;*/
		else if(field[my][mx] == 5)
			return;
		else if(bList.indexOf(GetBlock(mx,my))>=bList.size()-cCount)
			return;
		else
		{
			NumHits+=1;
			plist.add(new Point(mx,my));
			/*plist2.add(GetBlock(mx,my));*/
			field[my][mx] = -color;
			CheckForFloodFill(mx+1, my, color,plist);
			CheckForFloodFill(mx-1, my, color,plist);
			CheckForFloodFill(mx, my+1, color,plist);
			CheckForFloodFill(mx, my-1, color,plist);
			////
			/*CheckForFloodFill(mx+1, my, color,plist2);
			CheckForFloodFill(mx-1, my, color,plist2);
			CheckForFloodFill(mx, my+1, color,plist2);
			CheckForFloodFill(mx, my-1, color,plist2);*/
		}
	}
	
	public void DeleteBlockFromList(Score score,/*Vector<Block> plist2*/Vector<Point> plist,int color)
	{
		if(NumHits>=7)
		{
			DeleteList.add(plist);
			for(int i=0;i<plist.size();i++)
			{
				Point t = plist.get(i);
				if(GetBlock(t.x,t.y).motion == 0)
					GetBlock(t.x,t.y).motion = 1;
				//bList.remove(GetBlock(t.x,t.y));
			}
			//////
			/*boolean same = false;
			for(int i=0;i<DeleteList2.size();i++)
			{
				for(int j=0;j<DeleteList2.get(i).size();j++)
				{
					Block clone = DeleteList2.get(i).get(j);
					for(int k=0;k<plist2.size();k++)
					{
						if(clone == plist2.get(k) && DeleteList2.get(i).size() != plist2.size())
						{
							Log.d("zz", "zz");
							same = true;
							DeleteList2.remove(i);
							break;
						}
					}
					if(same == true)
						break;
				}
				if(same == true)
					break;
			}
			DeleteList2.add(plist2);
			for(int i=0;i<plist2.size();i++)
			{
				plist2.get(i).motion = 0;
				plist2.get(i).frame = 0;
				if(plist2.get(i).motion == 0)
					plist2.get(i).motion = 1;
				//bList.remove(GetBlock(t.x,t.y));
			}*/
		}
		else
		{
			int[] dx = {0,1,0,-1};
			int[] dy = {-1,0,1,0};
			int tx,ty;
			int index = -1;
			for(int i=0;i<plist.size();i++)
			{
				for(int j=0;j<4;j++)
				{
					tx = plist.get(i).x+dx[j];
					ty = plist.get(i).y+dy[j];
					if(ty<0 || ty>Data.MH-1 || tx<0 || tx>Data.MW-1)
						continue;
					if(field[ty][tx] == -color)
					{
						index = GetDeleteIndexOfBlock(tx, ty);
						break;
					}
				}
				if(index>=0)
					break;
			}
			if(index>=0)
			{
				for(int i=0;i<plist.size();i++)
				{
					DeleteList.get(index).add(plist.get(i));
				}
				
			}
			/////
			/*int[] dx = {0,1,0,-1};
			int[] dy = {-1,0,1,0};
			int tx,ty;
			int index = -1;
			for(int i=0;i<plist2.size();i++)
			{
				for(int j=0;j<4;j++)
				{
					tx = plist2.get(i).mx+dx[j];
					ty = plist2.get(i).my+dy[j];
					if(ty<0 || ty>Data.MH-1 || tx<0 || tx>Data.MW-1)
						continue;
					if(field[ty][tx] == -color)
					{
						index = GetDeleteIndexOfBlock(tx, ty);
						break;
					}
				}
				if(index>=0)
					break;
			}
			if(index>=0)
			{
				for(int i=0;i<plist2.size();i++)
				{
					DeleteList2.get(index).add(plist2.get(i));
				}
				for(int i=0;i<DeleteList2.get(index).size();i++)
				{
					//DeleteList2.get(index).get(i).motion = 0;
					//DeleteList2.get(index).get(i).frame = 0;
				}
			}
			//DeleteList.remove(plist);
*/		}
	}
	
	public int GetDeleteIndexOfBlock(int mx,int my)
	{
		for(int i=0;i<DeleteList.size();i++)
		{
			for(int j=0;j<DeleteList.get(i).size();j++)
			{
				Point tpos = DeleteList.get(i).get(j);
				if(tpos.x == mx && tpos.y == my)
					return i;
			}
		}
		/*for(int i=0;i<DeleteList2.size();i++)
		{
			for(int j=0;j<DeleteList2.get(i).size();j++)
			{
				Point tpos = new Point(DeleteList2.get(i).get(j).mx,DeleteList2.get(i).get(j).my);
				if(tpos.x == mx && tpos.y == my)
					return i;
			}
		}*/
		
		return -1;
	}
	
	public boolean IsAnimationing()
	{
		for(int i=0;i<Data.MH;i++)
		{
			for(int j=0;j<Data.MW;j++)
			{
				if(field[i][j]!=0)
				{
					if(GetBlock(j, i).motion == 1)
						return true;
				}
			}
		}
		return false;
	}
	
	public void DeleteBlockAfterFF(Score score,int color) //아마 안쓸거같다
	{
		if(NumHits>=7)
		{
			boolean cancel = true;
			Combo++;
			score.Add(Combo);
			for(int i=0;i<Data.MH;i++)
			{
				for(int j=0;j<Data.MW;j++)
				{
					if(field[i][j]<0){
						int[] dx = {0,1,0,-1};
						int[] dy = {-1,0,1,0};
						int tx,ty;
						if(GetBlock(j,i).motion == 1)
							cancel = false;
						for(int k=0;k<4;k++) // 주위에 귤이있는지 검사 
						{
							ty = i+dy[k];
							tx = j+dx[k];
							if(ty<0 || ty>Data.MH-1 || tx<0 || tx>Data.MW-1)
								continue;
							if(field[ty][tx] == 5){
								if(GetBlock(tx, ty).motion == 0)
									GetBlock(tx,ty).motion = 1;
								//RemoveBlock(tx, ty);
							}
						}
						for(int k=0;k<4;k++) // 주위에 귤이있는지 검사 
						{
							ty = i+dy[k];
							tx = j+dx[k];
							if(ty<0 || ty>Data.MH-1 || tx<0 || tx>Data.MW-1)
								continue;
							if(field[ty][tx] == color && GetBlock(tx,ty).motion == 1){
								if(GetBlock(j, i).motion == 0){
									GetBlock(j,i).motion = 1;
									//GetBlock(j,i).frame = GetBlock(tx,ty).frame;
								}
								//RemoveBlock(tx, ty);
							}
						}
						GetBlock(j,i).motion = 1;
						/*field[i][j] = 0;
						RemoveBlock(j, i);*/
					}
				}
				
				
			}
			if(cancel == false)
				Combo--;
			
			String a = Integer.toString(Combo);
			Log.d(a, a);
			/*SetMove();
			MarkingInMap();
			SetMove();
			MarkingInMap();*/
		}
		else
		{
			for(int i=0;i<Data.MH;i++)
			{
				for(int j=0;j<Data.MW;j++)
				{
					if(field[i][j]<0){
						field[i][j] = -field[i][j];
					}
				}
			}
		}
	}
	
	public void GoOrange(/*Score score, ComboManager cmanager*/)//오렌지의 떨림을 애니메이션한다. 마지막 프레임일경우 삭제 *수정: 전체 블록의 떨림을 애니메이션한다 마지막 프레임일경우 삭제
	{
		/*boolean add = false;
		if(ShowCombo!=Combo){
			add = true;
			ComboTimer = System.currentTimeMillis();
			//cmanager.Set(Combo);
		}
		if(System.currentTimeMillis() - ComboTimer > 5000){
			Combo = 0;
			//cmanager.Set(Combo);
		}
		boolean moveset = false;
		for(int i=0;i<Data.MH;i++)
		{
			for(int j=0;j<Data.MW;j++)
			{
				if(field[i][j]!=0)
				{
					
					if(GetBlock(j,i).motion == 1 && GetBlock(j,i).EndFrame() == true){
						field[i][j] = 0;
						RemoveBlock(j, i);
						//score.Add(1*Data.Stage*1);
						moveset = true;
						//Data.Stage = Combo;
						ShowCombo = Combo;
					}
					if(GetBlock(j,i).motion == 1)
						GetBlock(j,i).AddFrame(0.5f);
				}
			}
		}*/
		int[] dx = {0,1,0,-1};
		int[] dy = {-1,0,1,0};
		int tx,ty;
		for(int i=0;i<Data.MH;i++)
		{
			for(int j=0;j<Data.MW;j++)
			{
				if(GetBlock(j,i).color !=5)
					continue;
				if(field[i][j] == 5 && GetBlock(j,i).motion == 0)
				{
					for(int k=0;k<4;k++)
					{
						tx = j+dx[k];
						ty = i+dy[k];
						if(ty<0 || ty>Data.MH-1 || tx<0 || tx>Data.MW-1)
							continue;
						if(GetBlock(tx,ty).motion == 1){
							Log.d("true", "true");
							GetBlock(j,i).motion = 1;
							break;
						}
					}
				}
				else if(field[i][j] == 5 && GetBlock(j,i).motion == 1 && GetBlock(j,i).EndFrame())
					bList.remove(GetBlock(j,i));
				else if(field[i][j] == 5 && GetBlock(j,i).motion == 1)
				{
					Log.d("motion:1","motion:1");
					GetBlock(j,i).AddFrameLoop(0.5f);
				}
				if(GetBlock(j,i).motion == 1 && GetBlock(j,i).EndFrame())
				{
					RemoveBlock(j, i);
				}
				else if(GetBlock(j,i).motion == 1)
					GetBlock(j,i).AddFrame(0.5f);
			}
		}
/*		if(ShowCombo == Combo && add == true)
		{
			//콤보이미지 출력
			cmanager.Set(Combo);
		}*/
	}
	public Block GetBlock(int mx,int my)
	{
		for(int i=0;i<bList.size();i++)
		{
			if(bList.elementAt(i).mx == mx && bList.elementAt(i).my == my)
			{
				return bList.elementAt(i);
			}
		}
		Block tmp = new Block();
		return tmp ;
	}
	
	public void RemoveBlock(int mx,int my)
	{
		for(int i=0;i<bList.size();i++)
		{
			if(bList.elementAt(i).mx == mx && bList.elementAt(i).my == my)
			{
				bList.remove(i);
				return;
			}
		}
	}
	
	public boolean CheckGameOver() // 게임오버인지 체크
	{
		boolean check = true;
		for(int i=0;i<Data.MH;i++)
		{
			for(int j=0;j<Data.MW;j++)
			{
				if(field[i][j] == 0)
				{
					check = false;
					break;
				}
				else if(GetBlock(i,j).motion == 1)
				{
					check = false;
					break;
				}
			}
			if(check == false)
				break;
		}
		return check;
	}
}
