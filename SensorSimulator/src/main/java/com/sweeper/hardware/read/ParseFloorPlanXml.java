package com.sweeper.hardware.read;

import com.sweeper.exceptions.XmlProcessingException;
import com.sweeper.hardware.floorplan.FloorCell;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class ParseFloorPlanXml {

	private ParseFloorPlanXml() {}
	
	public static Map<Point, FloorCell> getFloorCellPlan(File xmlFile) throws IOException, XmlProcessingException{
		FloorPlanXmlHandler floorPlanXmlHandler = new FloorPlanXmlHandler();
		//Create a schema object for validation
		try {
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.setNamespaceAware(true);
			SAXParser saxParser = parserFactory.newSAXParser();
			saxParser.parse(xmlFile, floorPlanXmlHandler);
		} catch (SAXException se) {
			throw new XmlProcessingException("There was an error trying to process your XML.", se);
		} catch (ParserConfigurationException pce) {
			throw new XmlProcessingException("There was an error trying to process your XML.", pce);
		}
		return floorPlanXmlHandler.getFloorCells();
	}
}
