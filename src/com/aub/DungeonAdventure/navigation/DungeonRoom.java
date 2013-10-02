package com.aub.DungeonAdventure.navigation;

import java.util.List;

public interface DungeonRoom {
	
	/**
	 * 
	 * @return List of valid exits from the room.
	 */
	public List<Direction.Absolute> getDoors();
	
	/**
	 * @param The relative direction that you move in to reach the next room.
	 * @return The room that you wind up in after movement.
	 */
	public DungeonRoom move(Direction.Absolute d);
	
	
}