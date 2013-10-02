package com.aub.DungeonAdventure;

import java.util.HashMap;
import java.util.Map;

import com.aub.DungeonAdventure.navigation.Direction;
import com.aub.DungeonAdventure.navigation.DungeonRoom;
import com.aub.DungeonAdventure.navigation.dungeons.SimpleDungeon;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

/**
 *
 */
public class GameActivity extends Activity implements OnClickListener {
  
	private static final int ID_F = R.id.button_nav_f,
							 ID_R = R.id.button_nav_r,
							 ID_B = R.id.button_nav_b, 
							 ID_L = R.id.button_nav_l;
	private static final BiMap<Integer, Direction.Relative> zones;
	private static final Map<Direction.Relative, Integer> zones1;
	static{
		zones = HashBiMap.create();
		zones1 = zones.inverse();
		zones.put(ID_F, Direction.Relative.Forward);
		zones.put(ID_R, Direction.Relative.Right);
		zones.put(ID_B, Direction.Relative.Back);
		zones.put(ID_L, Direction.Relative.Left);
	}
			
    private DungeonRoom map;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.map=new SimpleDungeon();

        setContentView(R.layout.image_button_game);
        
        for(int id : zones.keySet()){
        	Log.i("tm", findViewById(id).toString()); 
        	findViewById(id).setOnClickListener(this);
        }
        
        render();

    }

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case ID_F: map=map.move(Direction.Relative.Forward); break;
		case ID_R: map=map.move(Direction.Relative.Right); break;
		case ID_B: map=map.move(Direction.Relative.Back); break;
		case ID_L: map=map.move(Direction.Relative.Left); break;
		}
		render();
	}
	
	private void render(){
		for(int i : zones.keySet()){
			findViewById(i).setVisibility(View.INVISIBLE);
		}
		for(Direction.Relative door : map.getDoors()){
			findViewById(zones1.get(door)).setVisibility(View.VISIBLE);
		}
	}
}
