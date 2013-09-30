package com.aub.DungeonAdventure.navigation;

import java.util.List;

public class SimpleMap implements DungeonPosition {

	private int x, y;
	private Direction.Absolute facing;
	private boolean[][] doors = { { true, false, false },
			{ false, true, true, true }, { true, true, true },
			{ true, true, false, false }, { false, false, true },
			{ true, false, true, false }, { false, true, true } };

	public SimpleMap() {
		x = 0;
		y = 0;
		facing = Direction.Absolute.North;
	}

	@Override
	public List<Direction.Relative> getDoors() {
		boolean[] nesw = new boolean[4];
		int Y = doors.length/2;
		int X = doors[0].length;
		
		nesw[0]= y>0 ? doors[2*y-1][x] : false;
		nesw[1]= x<X ? doors[2*y][x] : false;
		nesw[2]= y<Y ? doors[2*y+1][x] : false;
		nesw[3]= x>0 ? doors[2*y][x-1] : false;
		
		List<Direction.Absolute> ADirs = Direction.getFromNESW(nesw);
		return Direction.facingTransform(ADirs, facing);
	}

	@Override
	public void move(Direction.Relative d) {
		Direction.Absolute moveDir = Direction.facingTransform(d, facing);
		switch(moveDir){
		case North:
			y-=1;
			break;
		case East:
			x+=1;
			break;
		case South:
			y+= 1;
			break;
		case West:
			x-=1;
			break;
		}
		facing=moveDir;
	}

}
