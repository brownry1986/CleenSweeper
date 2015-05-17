package com.sweeper.clean.control.nav.ret;

import com.sweeper.clean.ActionFactory;
import com.sweeper.clean.HardwareInterfacePack;
import com.sweeper.clean.IAction;
import com.sweeper.clean.control.exceptions.ControllerException;
import com.sweeper.clean.control.memory.FloorCell;
import com.sweeper.clean.control.memory.State;
import com.sweeper.clean.control.nav.AbsMove;
import com.sweeper.hardware.enumerations.Direction;
import com.sweeper.hardware.enumerations.ObstacleType;

import java.math.BigDecimal;

public class ReturnToChargerController {

	private ActionFactory actionFactory = new ActionFactory();
		
	/**
	 * Handles sending the robot to the charging station
	 * 
	 * @param sensors
	 *            - the hardware interface
	 * @param state
	 *            - robots memory
	 * @throws ControllerException
	 *             - if there is an error
	 */
	public void returnToStation(HardwareInterfacePack sensors, State state) throws ControllerException {

		if (sensors == null) {
			throw new ControllerException("State was not provided unable to Move");
		}

		if (state == null) {
			throw new ControllerException("Sensors was not provided unable to Move");
		}

        FloorCell currentFloorCell = state.getCurrentFloorCell();

        BigDecimal powerCost = null;
        Direction moveDirection = null;
        int x = currentFloorCell.getX();
        int y = currentFloorCell.getY();

        for ( Direction direction : Direction.values() ) {
            if ( currentFloorCell.getObstacle(direction) == ObstacleType.OPEN ) {
                FloorCell floorCell = state.getSurroundingFloorCell(x, y, direction);
                if ( floorCell != null ) {
                    BigDecimal floorCellPowerCost = floorCell.getPowerCost();
                    if ( powerCost == null || floorCellPowerCost.compareTo(powerCost) < 0) {
                        powerCost = floorCellPowerCost;
                        moveDirection = direction;
                    }
                }
            }
        }

        IAction moveAction = determineMoveAction(moveDirection);
        moveAction.execute(sensors, state);
    }

	/**
	 * @return the factory that creates actions
	 */
	protected ActionFactory getActionFactory() {
		return actionFactory;
	}

    private AbsMove determineMoveAction(Direction moveDirection) {
        switch(moveDirection) {
            case LEFT:
                return getActionFactory().getLeft();
            case FORWARD:
                return getActionFactory().getForward();
            case RIGHT:
                return getActionFactory().getRight();
            case BACKWARD:
                return getActionFactory().getBackward();
        }

        return null;
    }

}
