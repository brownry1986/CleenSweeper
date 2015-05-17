package com.sweeper.simulator.ui.util;

import com.sweeper.hardware.floorplan.FloorCell;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class DrawFloorPlan {
	private static FloorPlanCellCreator cellCreator = new FloorPlanCellCreatorImpl();

    private DrawFloorPlan() {}
	/**
	 * This class takes in a floor plan. It then reads the keys to determine how large the grid layout
	 * will need to be. Then it adds colored cells to represent the floor plan. It will also add graphics to the
	 * cells to denote obstructions, the charging base, and the sweeper location.
	 *
	 * Gray = Empty Cell
	 * Green = Clean Cell
	 * Brown = Dirty Cell
	 *
	 * @param floorPlan
	 * @return
	 */
	public static Map<Point, JPanel> draw(Map<Point, FloorCell> floorPlan, JPanel basePanel){
		Map<Point, JPanel> gridComponents = new HashMap<Point, JPanel>();
		if (floorPlan == null || floorPlan.isEmpty()){
			basePanel.add(new JLabel("Floor Plan was Empty"));
			return null;
		}

		//get the keys and get the x and y values to sort to determine the max size of the grid.
		Set<Point> keys = floorPlan.keySet();
		ArrayList<Double> xValues = new ArrayList<Double>();
		ArrayList<Double> yValues = new ArrayList<Double>();
		int lowX, highX, maxX;
		int lowY, highY, maxY;
		JPanel tempPanel;
		Point tempPoint;
		for (Point p: keys){
			xValues.add(p.getX());
			yValues.add(p.getY());
		}
		Collections.sort(xValues);
		Collections.sort(yValues);
		//determine the maximum x by summing the absolute values and incrementing by 1 to account for 0
		lowX = xValues.get(0).intValue();
		highX = xValues.get(xValues.size()-1).intValue();
		maxX = Math.abs(lowX) + Math.abs(highX) + 1;

		//determine the maximum y by summing the absolute values and incrementing by 1 to account for 0
		lowY = yValues.get(0).intValue();
		highY = yValues.get(yValues.size()-1).intValue();
		maxY = Math.abs(lowY) + Math.abs(highY) + 1;

		// create the panel
		basePanel.setLayout(new GridLayout(maxY, maxX, 1, 1));
		for (int y = highY; y >= lowY; y--){
			for (int x = lowX; x <= highX ; x++){
				tempPoint = new Point(x, y);
				tempPanel = cellCreator.createCell(floorPlan.get(new Point(x, y)));
				basePanel.add(tempPanel);
				gridComponents.put(tempPoint, tempPanel);
			}
		}
		return gridComponents;
	}
}
