package com.sweeper.clean.control;

import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.FloorCell;
import com.sweeper.clean.control.memory.FloorCellState;
import com.sweeper.clean.control.memory.State;
import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;

import org.junit.Test;

import java.awt.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class TestIsEndOfCleanCycle {

	@Test(expected = ControllerException.class)
	public void test_decisionImpl_null_state() throws Exception{
		IsEndOfCleanCycle decision = new IsEndOfCleanCycle();
		decision.decideImpl(null, null);
		fail("No Exception");
	}
	
	@Test
	public void test_decisionImpl_empty_floorplan() throws Exception{
		
		State state = new State();
		
		IsEndOfCleanCycle decision = new IsEndOfCleanCycle();
		boolean result = decision.decideImpl(null, state);
		
		assertThat(result, is(false));
	}
	
	@Test
	public void test_decisionImpl_floorplan_completed() throws Exception{
		
		State state = new State();
		
		FloorCell cell1 = new FloorCell(0, 0, true, ObstacleType.OPEN, ObstacleType.OBSTACLE,ObstacleType.OBSTACLE,ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR, FloorCellState.CLEAN);
		FloorCell cell2 = new FloorCell(0, 1, ObstacleType.OBSTACLE, ObstacleType.OPEN,ObstacleType.OBSTACLE,ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR, FloorCellState.CLEAN);
		
		state.addFloorCell(new Point(0,0), cell1);
		state.addFloorCell(new Point(0,1), cell2);
		
		IsEndOfCleanCycle decision = new IsEndOfCleanCycle();
		boolean result = decision.decideImpl(null, state);
		
		assertThat(result, is(true));
	}
	
	@Test
	public void test_decisionImpl_floorplan_not_all_clean() throws Exception{
		
		State state = new State();
		
		FloorCell cell1 = new FloorCell(0, 0, true, ObstacleType.OPEN, ObstacleType.OBSTACLE,ObstacleType.OBSTACLE,ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR, FloorCellState.CLEAN);
		FloorCell cell2 = new FloorCell(0, 1, ObstacleType.OBSTACLE, ObstacleType.OPEN,ObstacleType.OBSTACLE,ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR, FloorCellState.DIRTY);
		
		state.addFloorCell(new Point(0,0), cell1);
		state.addFloorCell(new Point(0,1), cell2);
		
		IsEndOfCleanCycle decision = new IsEndOfCleanCycle();
		boolean result = decision.decideImpl(null, state);
		
		assertThat(result, is(false));
	}
	
	@Test
	public void test_decisionImpl_floorplan_not_all_cells_visited() throws Exception{
		
		State state = new State();
		
		FloorCell cell1 = new FloorCell(0, 0, true, ObstacleType.OPEN, ObstacleType.OBSTACLE,ObstacleType.OBSTACLE,ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR, FloorCellState.CLEAN);
		FloorCell cell2 = new FloorCell(0, 1, ObstacleType.OPEN, ObstacleType.OPEN,ObstacleType.OBSTACLE,ObstacleType.OBSTACLE, SurfaceType.BARE_FLOOR, FloorCellState.CLEAN);
		FloorCell cell3 = new FloorCell(0, 2);
		
		state.addFloorCell(new Point(0,0), cell1);
		state.addFloorCell(new Point(0,1), cell2);
		state.addFloorCell(new Point(0,2), cell3);
		
		IsEndOfCleanCycle decision = new IsEndOfCleanCycle();
		boolean result = decision.decideImpl(null, state);
		
		assertThat(result, is(false));
	}
	
	@Test
	public void test_getActivityLogMsg() throws Exception {
		IsEndOfCleanCycle decision = new IsEndOfCleanCycle();
		assertNotNull(decision.getActivityLogMsg());
	}
	
}
