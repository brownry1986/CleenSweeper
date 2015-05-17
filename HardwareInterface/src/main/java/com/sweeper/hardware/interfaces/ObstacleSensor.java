package com.sweeper.hardware.interfaces;

import com.sweeper.hardware.enumerations.ObstacleType;

public interface ObstacleSensor {

	//Left is always x-1
	ObstacleType getLeftSensorQuery();
	
	//right is always x+1
	ObstacleType getRightSensorQuery();
	
	//up is always y+1
	ObstacleType getForwardSensorQuery();
	
	//down is always y-1
	ObstacleType getBackwardSensorQuery();
	
}
