package com.aub.DungeonAdventure.navigation.dungeons;

import java.util.List;

import com.aub.DungeonAdventure.navigation.Direction;
import com.aub.DungeonAdventure.navigation.DungeonRoom;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Lists;

public class ArrayDungeon {
	
	private class Room implements DungeonRoom{

		private Room[] exits = new Room[Direction.Absolute.values().length];

		@Override
		public List<Direction.Absolute> getDoors() {
			List<Direction.Absolute> ret = Lists.newArrayList();
			for(Direction.Absolute dir : Direction.Absolute.values()){
				if(exits[dir.ordinal()] != null){
					ret.add(dir);
				}
			}
			return ret;
		}

		@Override
		public DungeonRoom move(Direction.Absolute d) {
			return exits[d.ordinal()];
		}
		
		private void addExit(Direction.Absolute dir, Room dst){
			this.exits[dir.ordinal()] = dst;
		}
		
	}
	
	/**
	 * Map in the form of [x2-x1, y2-y1] : direction to 
	 * go from point1 to get to point 2.
	 */
	private static final BiMap<int[], Direction.Absolute> TRANSFORM;
	private static final int[] a(int ... elements){
		return elements;
	}
	static{
		TRANSFORM = new ImmutableBiMap.Builder<int[], Direction.Absolute>()
		.put( a(0,1), Direction.Absolute.East)
		.put( a(0,-1), Direction.Absolute.West)
		.put( a(1,0), Direction.Absolute.North)
		.put( a(-1,0), Direction.Absolute.South)
		.build();
	}
	
	// 2d array of the dungeon. 0,0 is the northwest corner. x is e-w.
	private Room[][] map;
	
	private ArrayDungeon(){
		map = new Room[12][12];
		
		
		
	}
	
	private void connect(int x1, int y1, int x2, int y2){
		Room r1, r2;
		r1 = map [x1][y1];
		r2 = map [x2][y2];
		
		int[] d = new int[2];
		d[0] = x2-x1;
		d[1] = y2-y1;
		
		r1.addExit(TRANSFORM.get(d), r2);
		
		d[0] = -1*d[0];
		d[1] = -1*d[1];
		r2.addExit(TRANSFORM.get(d), r1);
	}

}
