package com.sweeper.hardware.interfaces;

import com.sweeper.hardware.enumerations.SurfaceType;

public interface CleaningApparatus {

    void vacuum();

    SurfaceType getCleaningApparatusPosition();

    void setCleaningApparatusPosition(SurfaceType surfaceType);

    boolean isFull();

    void empty();

}
