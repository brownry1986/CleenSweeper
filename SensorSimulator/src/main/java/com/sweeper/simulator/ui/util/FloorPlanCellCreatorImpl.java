package com.sweeper.simulator.ui.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.floorplan.FloorCell;

public class FloorPlanCellCreatorImpl implements FloorPlanCellCreator {

	
	/**
	 * This method will determine what the FloorCells values are and return the appropriate 
	 * JPanel to visually represent the cell. Null FloorCells are treated as empty space.
	 * 
	 * Empty Cells = Gray
	 * Clean Cells = Green
	 * Dirt Cells = Brown
	 * 
	 * Obstacles are black bars with a white letter to denote the obstacle type.
	 * 
	 * O = Obstacle
	 * S = Stairs
	 * U = Unknown
	 *
	 * The charging Station Cell has a "C" placed on it.
	 */
	public JPanel createCell(FloorCell floorCell) {
		JPanel returnPanel = new JPanel(new BorderLayout());
		if (floorCell == null) {
			returnPanel.setBackground(FloorPlanCellColor.NULL_CELL);
			return returnPanel;
		}
		if (floorCell.isDirty()) {
			returnPanel.setBackground(FloorPlanCellColor.DIRTY_CELL);
		} else {
			returnPanel.setBackground(FloorPlanCellColor.CLEAN_CELL);
		}
		if (floorCell.isChargingStation()) {
			returnPanel.add( new JLabel("C", JLabel.CENTER), BorderLayout.CENTER);
		}
		//Add the Lines on Panels based on whether or not they are obstructed.
		drawObstructionLine(returnPanel, floorCell.getForwardObstacle(), BorderLayout.NORTH);
		drawObstructionLine(returnPanel, floorCell.getBackwardObstacle(), BorderLayout.SOUTH);
		drawObstructionLine(returnPanel, floorCell.getLeftObstacle(), BorderLayout.WEST);
		drawObstructionLine(returnPanel, floorCell.getRightObstacle(), BorderLayout.EAST);
		return returnPanel;
	}
	

	/**
	 * This helper method will add a line to the panel at the specifed x and y coordinates and will add it of the specified obstacle type.
	 * This method assumes that the border layout was used, and it is.
	 * The String location should be of type BorderLayout position Enum.
	 * 
	 * @param floorCellPanel
	 * @param obstacle
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 */
	
	private void drawObstructionLine(JPanel floorCellPanel, ObstacleType obstacle, String location) {
		if (obstacle == ObstacleType.OPEN) {
			return;
		}
		JPanel obstaclePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		obstaclePanel.setBackground(Color.BLACK);
		obstaclePanel.setPreferredSize(new Dimension(15, 15));
		
		JLabel obstacleLabel = new JLabel();
		obstacleLabel.setHorizontalAlignment(JLabel.CENTER);
		obstacleLabel.setVerticalAlignment(JLabel.TOP);
		obstacleLabel.setForeground(Color.WHITE);
		
		if (obstacle == ObstacleType.OBSTACLE) {
			obstacleLabel.setText("O");
		} else if (obstacle == ObstacleType.STAIRS) {
			obstacleLabel.setText("S");
		} else {
			obstacleLabel.setText("U");
		}
		
		obstaclePanel.add(obstacleLabel);
		floorCellPanel.add(obstaclePanel, location);
	}

}
