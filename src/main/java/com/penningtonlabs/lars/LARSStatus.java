package com.penningtonlabs.lars;

/**
 * Most recently received LARS status.
 */
public record LARSStatus(
        boolean sensorReady,
        boolean measurementValid,
        boolean configurationValid,
        int faultFlags) {

    public boolean hasFault() {
        return faultFlags != 0;
    }
}
