package com.aub.DungeonAdventure.navigation.dungeons;

import java.util.List;

import com.aub.DungeonAdventure.navigation.Direction.Relative;
import com.aub.DungeonAdventure.navigation.DungeonRoom;

public class ArrayDungeon {
	
	private class Room implements DungeonRoom{

		@Override
		public List<Relative> getDoors() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DungeonRoom move(Relative d) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private Room[][] map;
	
	private ArrayDungeon(){
		map = new Room[12][12];
		
	}
	
	private void connect(int x1, int y1, int x2, int y2){
		Room r1, r2;
		r1 = map [x1][y1];
		r2 = map [x2][y2];
		
	}

}
