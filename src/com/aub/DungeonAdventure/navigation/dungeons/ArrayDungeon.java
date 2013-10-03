package com.aub.DungeonAdventure.navigation.dungeons;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.aub.DungeonAdventure.navigation.Direction;
import com.aub.DungeonAdventure.navigation.DungeonRoom;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ArrayDungeon {
	
	static int nextRoom = 1;
	private class Room implements DungeonRoom{
		
		final int roomid = nextRoom++;

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
		
		@Override
		public String getDebugInfo() {
			return String.format("id: %d", roomid);
		}
		
	}
	
	private static class Coord{
		int x,y;
		public Coord(int x, int y){
			this.x=x;
			this.y=y;
		}
		@Override
		public int hashCode(){
			//TODO Hash Collision likely when y > 1000
			return x*1000 + y;
		}
		
		@Override
		public boolean equals(Object other){
			return this.hashCode() == other.hashCode();
		}
		
		public int[] asArray() {
			int[] ret = new int[2];
			ret[0]=x;
			ret[1]=y;
			return ret;
		}
		public void invert() {
			x=x*-1;
			y=y*-1;
		}
		public static Coord translate(Coord p, Coord shift) {
			return new Coord(p.x+shift.x, p.y+shift.y);
		}
	}
	
	/**
	 * Map in the form of [x2-x1, y2-y1] : direction to 
	 * go from point1 to get to point 2.
	 */
	private static final BiMap<Coord, Direction.Absolute> TRANSFORM;
	static{
		TRANSFORM = new ImmutableBiMap.Builder<Coord, Direction.Absolute>()
		.put(new Coord(0,1), Direction.Absolute.East)
		.put(new Coord(0,-1), Direction.Absolute.West)
		.put(new Coord(1,0), Direction.Absolute.North)
		.put(new Coord(-1,0), Direction.Absolute.South)
		.build();
	}
	
	/*
	 * Why am I using a map instead of a 2d array?
	 * 1. No need for boundary size.
	 * 2. Expanding into negatives is trivial.
	 * 3. It supports 3 dimensions by accident. 
	 */
	private Map<Coord, Room> map;
	
	public ArrayDungeon(){
		map = Maps.newHashMap();
		
		/* Randomly generate a dungeon by taking a random path through the map. 
		 * 
		 */
		Random rand = new Random();
		Coord head = new Coord(0,0);
		int chainLength = rand.nextInt(10)+15;
		for(int i=0; i < chainLength; i++){
			Direction.Absolute ranDir = 
					Direction.Absolute.values()
					[rand.nextInt(Direction.Absolute.values().length)];
			head = connect(head, ranDir);
		}
		
	}
	
	private void connect(int x1, int y1, int x2, int y2){
		Room r1, r2;
		r1 = getRoom(x1,y1);
		r2 = getRoom(x2,y2);
		
		Coord d = new Coord(x2-x1, y2-y1);
		
		r1.addExit(TRANSFORM.get(d), r2);
		
		d.invert();
		r2.addExit(TRANSFORM.get(d), r1);
	}
	private Coord connect(Coord p1, Direction.Absolute dir){
		Coord  shift;
		shift = TRANSFORM.inverse().get(dir);
		Coord p2 = Coord.translate(p1, shift);
		connect(p1.x, p1.y, p2.x, p2.y);
		return p2;
	}

	/**
	 * Gets a room at the given point on the grid.
	 * Will make up a new room on the spot instead of returning null.
	 * @param The coordinates of the target room;
	 * @return
	 */
	public Room getRoom(Coord xy) {
		if(map.get(xy) == null){
			map.put(xy, new Room());
		}		
		return map.get(xy);
	}
	public Room getRoom(int x, int y){
		return getRoom(new Coord(x,y));
	}

	public DungeonRoom getStart() {
		return getRoom(0,0);
	}

}
