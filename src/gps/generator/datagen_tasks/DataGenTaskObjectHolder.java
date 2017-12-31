package gps.generator.datagen_tasks;

import gps.generator.GPSGenEnumHolder;

import java.util.Collections;
import java.util.Map;

public class DataGenTaskObjectHolder {
    private GPSGenEnumHolder.AngleUnits angleUnit;
    private Map<GPSGenEnumHolder.Patterns, GPSGenEnumHolder.Modes> dataGenTaskValues;

    DataGenTaskObjectHolder(Map<GPSGenEnumHolder.Patterns, GPSGenEnumHolder.Modes> dataGenTaskValues, GPSGenEnumHolder.AngleUnits angleUnit){
        this.dataGenTaskValues = Collections.synchronizedMap(dataGenTaskValues);
        this.angleUnit = angleUnit;
    }

    public synchronized GPSGenEnumHolder.Modes getMode(GPSGenEnumHolder.Patterns pattern){
        assert pattern != null;
        return this.dataGenTaskValues.get(pattern);
    }

    synchronized void setDataGenTaskValues(GPSGenEnumHolder.Patterns pattern, GPSGenEnumHolder.Modes mode){
        assert pattern != null && mode != null;
        this.dataGenTaskValues.replace(pattern, mode);
    }

    synchronized GPSGenEnumHolder.AngleUnits getAngleUnit() {
        return angleUnit;
    }

    synchronized void setAngleUnit(GPSGenEnumHolder.AngleUnits angleUnit) {
        this.angleUnit = angleUnit;
    }



}
