package com.sweeper.hardware.read;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sweeper.exceptions.XmlProcessingException;
import com.sweeper.hardware.floorplan.FloorCell;

import static org.junit.Assert.fail;

public class FloorPlanXmlReadTest {

	@Test
	public void readFloorPlanTest() throws IOException, XmlProcessingException {
		URI uri = null;
		try {
			uri = getClass().getResource("/JunitFloorPlanTestFile.xml").toURI();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		File xmlFile = new File(uri);
		Map<Point, FloorCell> testFloorPlan = ParseFloorPlanXml.getFloorCellPlan(xmlFile);
		assertEquals("The test list was not size 4", 4, testFloorPlan.size());
		FloorCell cell1 = testFloorPlan.get(new Point(0,0));
		assertTrue("First cell was not charging station", cell1.isChargingStation());
		//TODO add more assertions and also see why Assert class has to be called
	}
	
	/**
	 @Test
	public void badFloorPlanTest() {
		URI uri = null;
		try {
			uri = getClass().getResource("/NotAFloorPlan.xml").toURI();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		File xmlFile = new File(uri);
		Map<Point, FloorCell> testFloorPlan = ParseFloorPlanXml.getFloorCellPlan(xmlFile);
	}
	
	@Test
	public void nonXmlFileTest() {
		URI uri = null;
		try {
			uri = getClass().getResource("/JustATextFile.xml").toURI();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		File xmlFile = new File(uri);
		Map<Point, FloorCell> testFloorPlan = ParseFloorPlanXml.getFloorCellPlan(xmlFile);
	}
	*/

}
