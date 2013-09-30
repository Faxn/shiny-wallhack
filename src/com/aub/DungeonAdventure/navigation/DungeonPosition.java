package com.aub.DungeonAdventure.navigation;

import java.util.List;

public interface DungeonPosition {
	
	public List<Direction.Relative> getDoors();
	public void move(Direction.Relative d);
	
	
}