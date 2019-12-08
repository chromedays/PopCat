package com.example.popcat;

import javax.microedition.khronos.opengles.GL;

import android.content.Context;
import android.view.MotionEvent;
import bayaba.engine.lib.helper.gamemain.BayabaGameMain;
import bayaba.engine.lib.helper.glview.BayabaGLView;

public class GLView extends BayabaGLView {

	public GLView(Context context, BayabaGameMain img) {
		super(context, img);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onTouchActionUp(MotionEvent event) {
		synchronized (sImg.gl) {
			
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (sImg.gl) {
			final int action = event.getAction();
			final int count = event.getPointerCount();
			
			GameMain gamemain = (GameMain) sImg;
			
			switch ( action & MotionEvent.ACTION_MASK)
			{
			case MotionEvent.ACTION_DOWN :
				Data.TouchS = true;
				Data.TouchX = event.getX()*sImg.gInfo.ScalePx;
				Data.TouchY = event.getY()*sImg.gInfo.ScalePy;
				gamemain.TouchBegan();
				break;
			case MotionEvent.ACTION_MOVE :
				Data.TouchX = event.getX()*sImg.gInfo.ScalePx;
				Data.TouchY = event.getY()*sImg.gInfo.ScalePy;
				gamemain.TouchMove();
				break;
			case MotionEvent.ACTION_UP :
				Data.TouchS = false;
				gamemain.TouchEnded();
				break;
			}
			
			//return true;
		}
		return true;
		
		
	}
}
