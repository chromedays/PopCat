package com.example.popcat;

import java.io.IOException;

import bayaba.engine.lib.helper.activity.BayabaActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;

public class PopCat extends BayabaActivity {
	
	boolean playflag = false;
	boolean mainflag = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setScreenSize(480, 800);
        makeGameMain(GameMain.class,GLView.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_pop_cat, menu);
        return true;
    }
    
    @Override
    public void onResume()
    {
    	if(mainflag)
    	{
    		Data.Music.start();
    		mainflag = false;
    	}
    	else if(playflag)
    	{
    		Data.PlayMusic.start();
    		playflag = false;
    	}
    	else
    	{
    		mainflag = false;
    		playflag = false;
    	}
    	super.onResume();
    }
    
    @Override
    public void onPause()
    {
    	if(Data.Music.isPlaying())
    	{
    		mainflag = true;
    		Data.Music.stop();
    		try {
				Data.Music.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(Data.PlayMusic.isPlaying())
    	{
    		playflag = true;
    		Data.PlayMusic.stop();
    		try {
				Data.PlayMusic.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else
    	{
    		mainflag = false;
    		playflag = false;
    	}
    	super.onPause();
    }
    
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if( keyCode == KeyEvent.KEYCODE_BACK )
		{
			//mGameView.mThread.stage1.stop=true;
			new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Quit").setMessage("끝내겠습니까?").setPositiveButton("예", new DialogInterface.OnClickListener()
			{
			public void onClick( DialogInterface dialog, int which )
			{
			finish();
			}
			}).setNegativeButton( "아니요", new DialogInterface.OnClickListener(){
			public void onClick( DialogInterface dialog, int which )
			{
			//mGameView.mThread.stage1.stop=false;
			}
			} ).show(); 
			//mGameView.mThread.stage1.stop=false;
			return true;
		}
		 
		return super.onKeyDown(keyCode, event);
    }*/
}
