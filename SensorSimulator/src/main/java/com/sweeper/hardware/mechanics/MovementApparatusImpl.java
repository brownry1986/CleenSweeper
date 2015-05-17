package com.sweeper.hardware.mechanics;

import com.sweeper.hardware.enumerations.SurfaceType;
import com.sweeper.hardware.interfaces.MovementApparatus;
import com.sweeper.hardware.state.SweeperState;
import com.sweeper.hardware.util.Utility;

public class MovementApparatusImpl implements MovementApparatus {

    public void moveLeft() {
        SurfaceType oldSurface = SweeperState.getSurfaceType();
        SweeperState.setXLocation(SweeperState.getXLocation() - 1 );
        SurfaceType newSurface = SweeperState.getSurfaceType();

        // Drain battery by the number of units required to move from old surface type to new surface type
        SweeperState.drainBattery(Utility.determinePowerForMovement(oldSurface, newSurface));
    }

    public void moveRight() {
        SurfaceType oldSurface = SweeperState.getSurfaceType();
        SweeperState.setXLocation(SweeperState.getXLocation() + 1 );
        SurfaceType newSurface = SweeperState.getSurfaceType();

        // Drain battery by the number of units required to move from old surface type to new surface type
        SweeperState.drainBattery(Utility.determinePowerForMovement(oldSurface, newSurface));
    }

    public void moveBackward() {
        SurfaceType oldSurface = SweeperState.getSurfaceType();
        SweeperState.setYLocation(SweeperState.getYLocation() - 1 );
        SurfaceType newSurface = SweeperState.getSurfaceType();

        // Drain battery by the number of units required to move from old surface type to new surface type
        SweeperState.drainBattery(Utility.determinePowerForMovement(oldSurface, newSurface));
    }

    public void moveForward() {
        SurfaceType oldSurface = SweeperState.getSurfaceType();
        SweeperState.setYLocation(SweeperState.getYLocation() + 1 );
        SurfaceType newSurface = SweeperState.getSurfaceType();

        // Drain battery by the number of units required to move from old surface type to new surface type
        SweeperState.drainBattery(Utility.determinePowerForMovement(oldSurface, newSurface));
    }

}
