package com.sweeper.clean.control.memory;

import static org.junit.Assert.*; 
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;

public class TestFloorCell {
	
	@Test
	public void test_constructor_init() throws Exception{
		
		int x = 3;
		int y = 4;
		
		ObstacleType backward = ObstacleType.OPEN;
		ObstacleType forward = ObstacleType.OBSTACLE;
		ObstacleType right = ObstacleType.STAIRS;
		ObstacleType left = ObstacleType.UNKNOWN;
		
		SurfaceType surface = SurfaceType.BARE_FLOOR;
		
		boolean chargingStation = true;
		
		FloorCellState state = FloorCellState.DIRTY;
		
		FloorCell tested = new FloorCell(x, y, chargingStation, forward, backward, right, left, surface, state);
		
		assertThat(x, is(equalTo(tested.getX())));
		assertThat(y, is(equalTo(tested.getY())));
		assertThat(chargingStation, is(equalTo(tested.isChargingStation())));
		assertThat(forward, is(equalTo(tested.getForwardObstacle())));
		assertThat(backward, is(equalTo(tested.getBackwardObstacle())));
		assertThat(left, is(equalTo(tested.getLeftObstacle())));
		assertThat(right, is(equalTo(tested.getRightObstacle())));
		assertThat(surface, is(equalTo(tested.getSurfaceType())));
		assertThat(state, is(equalTo(tested.getState())));
	}
	
	@Test
	public void test_constructor_scan() throws Exception{
		
		int x = 3;
		int y = 4;
		
		ObstacleType backward = ObstacleType.OPEN;
		ObstacleType forward = ObstacleType.OBSTACLE;
		ObstacleType right = ObstacleType.STAIRS;
		ObstacleType left = ObstacleType.UNKNOWN;
		
		SurfaceType surface = SurfaceType.BARE_FLOOR;
			
		FloorCellState state = FloorCellState.DIRTY;
		
		FloorCell tested = new FloorCell(x, y, forward, backward, right, left, surface, state);
		
		assertThat(x, is(equalTo(tested.getX())));
		assertThat(y, is(equalTo(tested.getY())));
		assertThat(false, is(equalTo(tested.isChargingStation())));
		assertThat(forward, is(equalTo(tested.getForwardObstacle())));
		assertThat(backward, is(equalTo(tested.getBackwardObstacle())));
		assertThat(left, is(equalTo(tested.getLeftObstacle())));
		assertThat(right, is(equalTo(tested.getRightObstacle())));
		assertThat(surface, is(equalTo(tested.getSurfaceType())));
		assertThat(state, is(equalTo(tested.getState())));
	}

}
