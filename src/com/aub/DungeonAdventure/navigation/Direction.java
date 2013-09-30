package com.aub.DungeonAdventure.navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum Direction {
	;

	public static enum Relative{
		Forward,  Right, Back, Left;
	}

	public static enum Absolute{
		North, East, South, West;	
	}
	
	private List<Direction.Absolute> abs = Arrays.asList(Direction.Absolute.values());
	private List<Direction.Relative> rel = Arrays.asList(Direction.Relative.values());
	
	public static List<Absolute> getFromNESW(boolean[] nesw) {
		ArrayList<Absolute> ret = new ArrayList<Absolute>();
		for(Absolute i :  Absolute.values()){
			if(nesw[i.ordinal()]) ret.add(i);
		}
		return ret;
	}
	
	public static Absolute facingTransform(Relative d, Absolute facing){
		return Absolute.values()[(d.ordinal()+facing.ordinal())%4];
	}
	
	public static List<Relative> facingTransform(List<Absolute> aDirs, Absolute facing) {
		List<Relative> ret = new ArrayList<Relative>();
		for(Absolute a: aDirs){
			ret.add(facingTransform(a, facing));
		}
		return ret;
	}
	
	public static Relative facingTransform(Absolute dir, Absolute facing){
		return Relative.values()[(dir.ordinal()-facing.ordinal()+4)%4];
	}

}
