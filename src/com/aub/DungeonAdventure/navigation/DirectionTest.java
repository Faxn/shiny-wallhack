package com.aub.DungeonAdventure.navigation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import android.text.TextUtils.StringSplitter;

public class DirectionTest {
	
	private List<Direction.Absolute> abs = Arrays.asList(Direction.Absolute.values());
	private List<Direction.Relative> rel = Arrays.asList(Direction.Relative.values());

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testAbsoluteTransforms(){
		for(Direction.Absolute facing: abs){
			int faci = abs.indexOf(facing); 
			for(Direction.Relative dir: rel){
				int reli = rel.indexOf(dir);
				int dst = (reli+faci)%4;
				Assert.assertEquals(abs.get(dst), Direction.facingTransform(dir, facing));
			}
		}
	}
	
	@Test
	public void testRelativeTransforms(){
		for(Direction.Absolute facing: abs){
			int faci = abs.indexOf(facing); 
			for(Direction.Absolute dir: abs){
				int reli = abs.indexOf(dir);
				Direction.Relative dst = rel.get((reli-faci+4)%4);
				Direction.Relative ft = Direction.facingTransform(dir, facing);
				String msg = String.format("Facing %s turned %s. Wound up facing %s. Expected %s", facing, dir, ft, dst);
				Assert.assertEquals(msg, dst, ft);
			}
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	

}
