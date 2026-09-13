package com.penningtonlabs.lars;

/**
 * Team-facing interface to a Pennington Laboratories LARS sensor.
 *
 * <p>Request methods generate CAN traffic. Getter methods never generate CAN
 * traffic; they return the latest cached state received from the sensor.</p>
 */
public final class LARS implements AutoCloseable {
    private final LARSCAN can;

    /**
     * Creates a LARS interface for device number 0.
     *
     * @param deviceId LARS device number on the CAN bus
     */
    public LARS(int deviceId) {
        this.can = new LARSCAN(deviceId);
    }

    /**
     * Sends a single distance request.
     */
    public void requestDistance() {
        can.requestDistance();
    }

    /**
     * Returns the latest cached distance in millimeters.
     */
    public double getDistanceMillimeters() {
        return can.getDistanceMillimeters();
    }

    /**
     * Returns whether the latest distance measurement is valid.
     */
    public boolean isMeasurementValid() {
        return can.isMeasurementValid();
    }

    /**
     * Sends a single limit-state request.
     */
    public void requestLimitState() {
        can.requestLimitState();
    }

    public LARSLimitState getLimitState() {
        return can.getLimitState();
    }

    public boolean isForwardLimitHit() {
        return can.getLimitState().forward();
    }

    public boolean isReverseLimitHit() {
        return can.getLimitState().reverse();
    }

    public boolean isLimitHit() {
        return can.getLimitState().anyLimit();
    }

    /**
     * Requests a mode change.
     */
    public void setMode(LARSMode mode) {
        can.setMode(mode);
    }

    /**
     * Requests the configured limit distance in millimeters.
     */
    public void setLimitMillimeters(double distanceMillimeters) {
        can.setLimitMillimeters(distanceMillimeters);
    }

    /**
     * Sends a single status request.
     */
    public void requestStatus() {
        can.requestStatus();
    }

    public LARSStatus getStatus() {
        return can.getStatus();
    }

    /**
     * Returns the device number used by this interface.
     */
    public int getDeviceId() {
        return can.getDeviceId();
    }

    @Override
    public void close() {
        can.close();
    }
}
