package com.aub.DungeonAdventure.view;

import java.util.List;

import com.aub.DungeonAdventure.R;
import com.aub.DungeonAdventure.R.drawable;
import com.aub.DungeonAdventure.navigation.Direction;
import com.aub.DungeonAdventure.navigation.DungeonRoom;
import com.aub.DungeonAdventure.navigation.dungeons.SimpleDungeon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnKeyListener;

public class GameplaySurfaceView extends SurfaceView implements Callback, OnKeyListener{
	
	private class ZoneImage{
		final Bitmap bitmap;
		final float left, top;
		ZoneImage(int bmap, float left, float top){
			this.bitmap=BitmapFactory.decodeResource(getResources(), bmap);
			this.left=left;
			this.top=top;
		}
	}
	private java.util.Map<Direction.Relative, ZoneImage> zones;
	
	private GameLoopThread gameLoop;
	private DungeonRoom map;
	
	private Bitmap background;
	
	public GameplaySurfaceView(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}

	public GameplaySurfaceView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		
		getHolder().addCallback(this);
		setFocusable(true);
		this.map=new SimpleDungeon();
		
		zones = new java.util.HashMap<Direction.Relative, ZoneImage>();
		zones.put(Direction.Relative.Forward, new ZoneImage(R.drawable.door_n, 0, 0));
		zones.put(Direction.Relative.Right, new ZoneImage(R.drawable.door_e, 0, 0));
		zones.put(Direction.Relative.Back, new ZoneImage(R.drawable.door_s, 0, 0));
		zones.put(Direction.Relative.Left, new ZoneImage(R.drawable.door_w, 0, 0));
		
		background=BitmapFactory.decodeResource(getResources(), R.drawable.swordmaze_bgd);
		
		this.setOnKeyListener(this);
		
		//gameLoop = new GameLoopThread(getHolder(), this);
	}
	
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.MAGENTA);
		canvas.drawBitmap(background,0,0,null);
		List<Direction.Relative> doors = map.getDoors();
		for(Direction.Relative door: doors){
			ZoneImage zi = zones.get(door);
			canvas.drawBitmap(zi.bitmap, zi.left, zi.top, null);
		}
		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		gameLoop.setRunning(true);
		gameLoop.start();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		try {
			gameLoop.join();
		} catch (InterruptedException e) {
		}

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(event.getAction() != KeyEvent.ACTION_DOWN) return false;
		switch(keyCode){
		case KeyEvent.KEYCODE_W: map.move(Direction.Relative.Forward); return true;
		case KeyEvent.KEYCODE_D: map.move(Direction.Relative.Left); return true;
		case KeyEvent.KEYCODE_S: map.move(Direction.Relative.Back); return true;
		case KeyEvent.KEYCODE_A: map.move(Direction.Relative.Right); return true;
		default: return false;
		}
		
	}
	
	

}
