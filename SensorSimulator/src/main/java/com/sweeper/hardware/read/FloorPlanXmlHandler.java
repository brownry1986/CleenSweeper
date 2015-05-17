package com.sweeper.hardware.read;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sweeper.exceptions.InvalidXmlParameterException;
import com.sweeper.hardware.floorplan.FloorCell;
import com.sweeper.hardware.floorplan.FloorCellBuilder;

public class FloorPlanXmlHandler extends DefaultHandler {
	//Not sure if list is optimal return type for the FloorCell objects.
	private Map<Point, FloorCell> floorPlanElements = new HashMap<Point, FloorCell>();
		
	public Map<Point, FloorCell> getFloorCells() {
		return floorPlanElements;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		
		if (localName.equalsIgnoreCase("cell")){
			//begin parsing the cells values
			int xs = Integer.parseInt(attributes.getValue("xs"));
			int ys = Integer.parseInt(attributes.getValue("ys"));
			int ss = Integer.parseInt(attributes.getValue("ss"));
			String ps = attributes.getValue("ps");
			int ds = Integer.parseInt(attributes.getValue("ds"));
			Boolean cs = Boolean.parseBoolean(attributes.getValue("cs"));
			try {
				floorPlanElements.put(new Point(xs, ys), FloorCellBuilder.floorCellCreateFromXml(xs, ys, ss, ps, ds, cs));
			} catch (InvalidXmlParameterException xe) {
				/*Removed the throwing of a runtime exception as execution should not be stopped for this issue.
				The intent was to eventually log this event, however logging on the simulator was not a 
				priority. It is still listed as a to do, but the exception is currently just swallowed. Also
				I do not believe that classes above this one actually catch the Runtime Exception that was here.
				-Zach
				*/
                //TODO log this exception
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		//I don't believe this needs to be overridden
		super.endElement(uri, localName, qName);
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		//I don't believe this needs to be overridden
		super.characters(ch, start, length);
	}
}
