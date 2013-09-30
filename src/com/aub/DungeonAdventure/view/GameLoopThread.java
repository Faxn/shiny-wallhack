package com.aub.DungeonAdventure.view;

import com.aub.DungeonAdventure.navigation.DungeonPosition;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

public class GameLoopThread extends Thread {
	// flag to hold game state
	private boolean running;
	private SurfaceHolder surfaceHolder;
	private GameplaySurfaceView gameSurface;
	private long tick=0;

	public GameLoopThread(DungeonPosition map, View v) {
		//this.surfaceHolder = sh;
		this.gameSurface = gameSurface;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@SuppressLint("WrongCall") @Override
	public void run() {
		
		while (running) {
			tick++;
			if(tick % 100 == 0) Log.v("Heartbeat", tick+"ticks");
			Canvas canvas = null;
			try{
				canvas  = this.surfaceHolder.lockCanvas();
				gameSurface.onDraw(canvas);
			}catch(Exception e){
				Log.e("Loop", "", e);
			}finally{
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}else{
					return;
				}
			}
			
		}
	}
}
