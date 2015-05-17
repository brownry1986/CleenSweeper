package com.sweeper.hardware.sensors;

import com.sweeper.hardware.enumerations.ObstacleType;
import com.sweeper.hardware.interfaces.ObstacleSensor;
import com.sweeper.hardware.state.SweeperState;

public class ObstacleSensorImpl implements ObstacleSensor {

    public ObstacleType getLeftSensorQuery() {
        return SweeperState.getLeftObstacleType();
    }

    public ObstacleType getRightSensorQuery() {
        return SweeperState.getRightObstacleType();
    }

    public ObstacleType getBackwardSensorQuery() {
        return SweeperState.getBackwardObstacleType();
    }

    public ObstacleType getForwardSensorQuery() {
        return SweeperState.getForwardObstacleType();
    }
    
    public String toString(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("Obstacles: \n");
    	sb.append("\tLeft: " + getLeftSensorQuery() + "\n");
    	sb.append("\tRight: " + getRightSensorQuery() + "\n");
    	sb.append("\tForward: " + getForwardSensorQuery() + "\n");
    	sb.append("\tBackward: " + getBackwardSensorQuery() + "\n");
    	return sb.toString();
    	
    }
}
