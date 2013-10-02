package com.aub.DungeonAdventure;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.aub.DungeonAdventure.navigation.Direction;
import com.aub.DungeonAdventure.navigation.Direction.Absolute;
import com.aub.DungeonAdventure.navigation.DungeonRoom;
import com.aub.DungeonAdventure.navigation.dungeons.SimpleDungeon;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

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
		zones = new ImmutableBiMap.Builder<Integer, Direction.Relative>()		
		.put(ID_F, Direction.Relative.Forward)
		.put(ID_R, Direction.Relative.Right)
		.put(ID_B, Direction.Relative.Back)
		.put(ID_L, Direction.Relative.Left)
		.build();
		zones1 = zones.inverse();
	}
			
    private DungeonRoom map;
	private Absolute facing = Absolute.North;

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
		Direction.Relative doorClicked = zones.get(arg0.getId());
		Direction.Absolute moveDir = Direction.facingTransform(doorClicked, facing);
		map.move(moveDir);
		facing = moveDir;		
		render();
	}
	
	private void render(){
		for(int i : zones.keySet()){
			findViewById(i).setVisibility(View.INVISIBLE);
		}
		for(Direction.Relative door : Direction.facingTransform(map.getDoors(), facing)){
			findViewById(zones1.get(door)).setVisibility(View.VISIBLE);
		}
	}
}
