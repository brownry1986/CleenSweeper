package com.sweeper.simulator.ui.util;

import com.sweeper.hardware.floorplan.FloorCell;

import javax.swing.JPanel;

public interface FloorPlanCellCreator {

	JPanel createCell(FloorCell floorCell);

}
