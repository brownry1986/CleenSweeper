package com.sweeper.hardware.floorplan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.sweeper.exceptions.InvalidXmlParameterException;
import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.enumerations.SurfaceType;

public class FloorCellTest {

	final ObstacleType OPEN = ObstacleType.OPEN;
	final ObstacleType OBSTACLE = ObstacleType.OBSTACLE;
	final ObstacleType STAIRS = ObstacleType.STAIRS;
	final ObstacleType UNKNOWN = ObstacleType.UNKNOWN;
	@Test
	public void vaccumDirtTest() {
		//create a bare floor cell with 3 units of dirt.
		FloorCell floor = null;
		floor = FloorCellBuilder.floorCellCreate(0, 0, SurfaceType.BARE_FLOOR, 3, true, OPEN, OPEN, OPEN, OPEN);

		assertTrue("Floor should have 3 units of dirt, but is marked as clean", floor.isDirty());
		floor.cleanCell();
		assertTrue("Floor should have 2 units of dirt, but is marked as clean", floor.isDirty());
		floor.cleanCell();
		assertTrue("Floor should have 1 unit of dirt, but is marked as clean", floor.isDirty());
		floor.cleanCell();
		assertFalse("Floor should be cleaned, but is marked as dirty", floor.isDirty());
		floor.cleanCell();
	}
	
	@Test
	public void checkObstructionsTest() {
		//create a low pile cell with all sides unobstructed
		FloorCell openFloor = null;
		openFloor = FloorCellBuilder.floorCellCreate(0, 0, SurfaceType.LOW_PILE_CARPET, 0, true, OPEN, OPEN, OPEN, OPEN);

		assertEquals("UP floor cell was not open", OPEN, openFloor.getForwardObstacle());
		assertEquals("DOWN floor cell was not open", OPEN,  openFloor.getBackwardObstacle());
		assertEquals("RIGHT floor cell was not open", OPEN, openFloor.getRightObstacle());
		assertEquals("LEFT floor cell was not open", OPEN, openFloor.getLeftObstacle());
		
		//create a high pile cell with all sides obstructed
		FloorCell closedFloor = null;
		closedFloor = FloorCellBuilder.floorCellCreate(0, 0, SurfaceType.HIGH_PILE_CARPET, 0, true, OBSTACLE, STAIRS, UNKNOWN, OBSTACLE);
		assertEquals("UP floor cell was not obstacle", OBSTACLE, closedFloor.getForwardObstacle());
		assertEquals("DOWN floor cell was not stairs", STAIRS, closedFloor.getBackwardObstacle());
		assertEquals("RIGHT floor cell was not unknown", UNKNOWN, closedFloor.getRightObstacle());
		assertEquals("LEFT floor cell was not obstacle", OBSTACLE, closedFloor.getLeftObstacle());
	}
	
	@Test
	public void allStairsTest() {
		FloorCell openFloor = null;
		openFloor = FloorCellBuilder.floorCellCreate(0, 0, SurfaceType.LOW_PILE_CARPET, 0, true, STAIRS, STAIRS, STAIRS, STAIRS);
		assertEquals("UP floor cell was not stairs", STAIRS, openFloor.getForwardObstacle());
		assertEquals("DOWN floor cell was not stairs", STAIRS,  openFloor.getBackwardObstacle());
		assertEquals("RIGHT floor cell was not stairs", STAIRS, openFloor.getRightObstacle());
		assertEquals("LEFT floor cell was not stairs", STAIRS, openFloor.getLeftObstacle());
	}
	
	@Test
	public void allObstacleTest() {
		FloorCell openFloor = null;
		openFloor = FloorCellBuilder.floorCellCreate(0, 0, SurfaceType.LOW_PILE_CARPET, 0, true, OBSTACLE, OBSTACLE, OBSTACLE, OBSTACLE);
		assertEquals("UP floor cell was not obstacle", OBSTACLE, openFloor.getForwardObstacle());
		assertEquals("DOWN floor cell was not obstacle", OBSTACLE,  openFloor.getBackwardObstacle());
		assertEquals("RIGHT floor cell was not obstacle", OBSTACLE, openFloor.getRightObstacle());
		assertEquals("LEFT floor cell was not obstacle", OBSTACLE, openFloor.getLeftObstacle());
	}
	
	@Test
	public void allUnknownTest() {
		FloorCell openFloor = null;
		openFloor = FloorCellBuilder.floorCellCreate(0, 0, SurfaceType.LOW_PILE_CARPET, 0, true, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN);
		assertEquals("UP floor cell was not unknown", UNKNOWN, openFloor.getForwardObstacle());
		assertEquals("DOWN floor cell was not unknown", UNKNOWN,  openFloor.getBackwardObstacle());
		assertEquals("RIGHT floor cell was not unknown", UNKNOWN, openFloor.getRightObstacle());
		assertEquals("LEFT floor cell was not unknown", UNKNOWN, openFloor.getLeftObstacle());
	}
	
	@Test
	public void validXmlFloorTypeTest() {
		try {
			FloorCell testFloor = FloorCellBuilder.floorCellCreateFromXml(0, 0, 2, "0124", 0, true);
			assertEquals("The x value was not 0", 0, testFloor.getX());
			assertEquals("The y value was not 0", 0, testFloor.getY());
			assertEquals("The Floor Type was not Low Pile", SurfaceType.LOW_PILE_CARPET, testFloor.getSurfaceType());
			assertEquals("The right direction was not unknown", UNKNOWN, testFloor.getRightObstacle());
			assertEquals("The left direction was not open", OPEN, testFloor.getLeftObstacle());
			assertEquals("The up direction was not not obstacle", OBSTACLE, testFloor.getForwardObstacle());
			assertEquals("The down direction was not stairs", STAIRS, testFloor.getBackwardObstacle());
			assertEquals("The number of dirt units was not 0", 0, testFloor.getDirtUnits());
			assertFalse("The floor cell was not clean", testFloor.isDirty());
			assertTrue("The cell does not contain the charging station", testFloor.isChargingStation());
		} catch (Exception e) {}
	}

	@Test //(expected=InvalidXmlParameterException.class)
	public void invalidXmlFloorTypeTest() {
		try {
			FloorCell testFloor = FloorCellBuilder.floorCellCreateFromXml(0, 0, 3, "1212", 0, true);
			fail();
		} catch (InvalidXmlParameterException e) {
			assertEquals("Inncorrect message returned", "Floor type (ss) not recognized", e.getMessage());
			assertEquals("Inncorrect message returned", "Floor type (ss) not recognized", e.toString());
		}
	}
	
	@Test //(expected=InvalidXmlParameterException.class)
	public void invalidXmlObstacleStringLengthTest1() {
		try {
			FloorCell testFloor = FloorCellBuilder.floorCellCreateFromXml(0, 0, 1, "121", 0, true);
			fail();
		} catch (InvalidXmlParameterException e) {
			assertEquals("Inncorrect message returned", "Obstacle String (ps) did not have appropriate values. Expected a string of length 4 with individual values of 0, 1, 2, or 4 for the individual characters", e.getMessage());
			assertEquals("Inncorrect message returned", "Obstacle String (ps) did not have appropriate values. Expected a string of length 4 with individual values of 0, 1, 2, or 4 for the individual characters", e.toString());
		}
	}
	
	@Test //(expected=InvalidXmlParameterException.class)
	public void invalidXmlObstacleStringLengthTest2() {
		try {
			FloorCell testFloor = FloorCellBuilder.floorCellCreateFromXml(0, 0, 1, "12112", 0, true);
			fail();
		} catch (InvalidXmlParameterException e) {
			assertEquals("Inncorrect message returned", "Obstacle String (ps) did not have appropriate values. Expected a string of length 4 with individual values of 0, 1, 2, or 4 for the individual characters", e.getMessage());
			assertEquals("Inncorrect message returned", "Obstacle String (ps) did not have appropriate values. Expected a string of length 4 with individual values of 0, 1, 2, or 4 for the individual characters", e.toString());
		}
	}
	
	@Test //(expected=InvalidXmlParameterException.class)
	public void invalidXmlObstacleStringValuesTest1() {
		try {
			FloorCell testFloor = FloorCellBuilder.floorCellCreateFromXml(0, 0, 2, "5124", 0, true);
			fail();
		} catch (InvalidXmlParameterException e) {
			assertEquals("Inncorrect message returned", "Obstacle String (ps) did not have appropriate values. Expected a string of length 4 with individual values of 0, 1, 2, or 4 for the individual characters", e.getMessage());
			assertEquals("Inncorrect message returned", "Obstacle String (ps) did not have appropriate values. Expected a string of length 4 with individual values of 0, 1, 2, or 4 for the individual characters", e.toString());
		}
	}
	
	@Test //(expected=InvalidXmlParameterException.class)
	public void invalidXmlObstacleStringValuesTest2() {
		try {
			FloorCell testFloor = FloorCellBuilder.floorCellCreateFromXml(0, 0, 4, "2624", 0, true);
			fail();
		} catch (InvalidXmlParameterException e) {
			assertEquals("Inncorrect message returned", "Obstacle String (ps) did not have appropriate values. Expected a string of length 4 with individual values of 0, 1, 2, or 4 for the individual characters", e.getMessage());
			assertEquals("Inncorrect message returned", "Obstacle String (ps) did not have appropriate values. Expected a string of length 4 with individual values of 0, 1, 2, or 4 for the individual characters", e.toString());
	
		}
	}
	
	@Test //(expected=InvalidXmlParameterException.class)
	public void invalidXmlObstacleStringValuesTest3() {
		try {
			FloorCell testFloor = FloorCellBuilder.floorCellCreateFromXml(0, 0, 2, "3452", 0, true);
			fail();
		} catch (InvalidXmlParameterException e) {
			assertEquals("Inncorrect message returned", "Obstacle String (ps) did not have appropriate values. Expected a string of length 4 with individual values of 0, 1, 2, or 4 for the individual characters", e.getMessage());
			assertEquals("Inncorrect message returned", "Obstacle String (ps) did not have appropriate values. Expected a string of length 4 with individual values of 0, 1, 2, or 4 for the individual characters", e.toString());
		}
	}
	
	@Test //(expected=InvalidXmlParameterException.class)
	public void invalidXmlObstacleStringValuesTest4() {
		try {
			FloorCell testFloor = FloorCellBuilder.floorCellCreateFromXml(0, 0, 4, "4445", 0, true);
			fail();
		} catch (InvalidXmlParameterException e) {
			assertEquals("Inncorrect message returned", "Obstacle String (ps) did not have appropriate values. Expected a string of length 4 with individual values of 0, 1, 2, or 4 for the individual characters", e.getMessage());
			assertEquals("Inncorrect message returned", "Obstacle String (ps) did not have appropriate values. Expected a string of length 4 with individual values of 0, 1, 2, or 4 for the individual characters", e.toString());
		}
	}
	
	@Test
	public void toStringTest() {
		FloorCell stringFloor = null;
		String testString = "X => -2, Y => 3, Surface => HIGH_PILE_CARPET, DirtUnits => 2, ChargingStation => false, UpObstacle => OBSTACLE, DownObstacle => STAIRS, RightObstacle => UNKNOWN, LeftObstacle => OPEN";
		stringFloor = FloorCellBuilder.floorCellCreate(-2, 3, SurfaceType.HIGH_PILE_CARPET, 2, false, OBSTACLE, STAIRS, UNKNOWN, OPEN);
		assertEquals("Strings were not equal", testString, stringFloor.toString());
	}
}
