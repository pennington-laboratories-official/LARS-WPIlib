package com.penningtonlabs.lars;

import edu.wpi.first.wpilibj.CAN;

/**
 * Internal CAN transport and protocol implementation.
 *
 * <p>This class is deliberately package-private. Teams should use {@link LARS}
 * rather than depending on raw CAN identifiers.</p>
 */
final class LARSCAN implements AutoCloseable {
    /*
     * FIRST CAN device type:
     * Distance Sensor = 6.
     *
     * IMPORTANT:
     * Replace this value with the manufacturer ID assigned to Pennington
     * Laboratories by FIRST before the first public production release.
     */
    static final int DEVICE_TYPE = 6;
    static final int MANUFACTURER_ID = 0xFF;

    // Provisional API class/index map. Freeze these with the LARS firmware
    // before the first public release.
    static final int CLASS_CONTROL = 0;
    static final int CLASS_REQUEST = 1;
    static final int CLASS_RESPONSE = 2;

    static final int API_SET_MODE = (CLASS_CONTROL << 4) | 0;
    static final int API_SET_LIMIT = (CLASS_CONTROL << 4) | 1;

    static final int API_REQUEST_DISTANCE = (CLASS_REQUEST << 4) | 0;
    static final int API_REQUEST_LIMIT_STATE = (CLASS_REQUEST << 4) | 1;
    static final int API_REQUEST_STATUS = (CLASS_REQUEST << 4) | 2;

    static final int API_RESPONSE_DISTANCE = (CLASS_RESPONSE << 4) | 0;
    static final int API_RESPONSE_LIMIT_STATE = (CLASS_RESPONSE << 4) | 1;
    static final int API_RESPONSE_STATUS = (CLASS_RESPONSE << 4) | 2;

    private final CAN can;

    private double distanceMillimeters;
    private boolean measurementValid;
    private LARSLimitState limitState = new LARSLimitState(false, false, false);
    private LARSStatus status = new LARSStatus(false, false, false, 0);

    LARSCAN(int deviceId) {
        if (deviceId < 0 || deviceId > 63) {
            throw new IllegalArgumentException("LARS device ID must be 0-63");
        }
        can = new CAN(deviceId, MANUFACTURER_ID, DEVICE_TYPE);
    }

    int getDeviceId() {
        return can.getDeviceId();
    }

    void requestDistance() {
        can.writePacket(new byte[0], API_REQUEST_DISTANCE);
    }

    void requestLimitState() {
        can.writePacket(new byte[0], API_REQUEST_LIMIT_STATE);
    }

    void requestStatus() {
        can.writePacket(new byte[0], API_REQUEST_STATUS);
    }

    void setMode(LARSMode mode) {
        if (mode == null) {
            throw new IllegalArgumentException("mode cannot be null");
        }
        can.writePacket(new byte[] {(byte) mode.getValue()}, API_SET_MODE);
    }

    void setLimitMillimeters(double distanceMillimeters) {
        if (!Double.isFinite(distanceMillimeters) ||
                distanceMillimeters < 0 ||
                distanceMillimeters > 65535) {
            throw new IllegalArgumentException("limit must be between 0 and 65535 mm");
        }

        int millimeters = (int) Math.round(distanceMillimeters);
        byte[] data = new byte[] {
            (byte) (millimeters & 0xFF),
            (byte) ((millimeters >>> 8) & 0xFF)
        };
        can.writePacket(data, API_SET_LIMIT);
    }

    double getDistanceMillimeters() {
        pollResponses();
        return distanceMillimeters;
    }

    boolean isMeasurementValid() {
        pollResponses();
        return measurementValid;
    }

    LARSLimitState getLimitState() {
        pollResponses();
        return limitState;
    }

    LARSStatus getStatus() {
        pollResponses();
        return status;
    }

    private void pollResponses() {
        CAN.CANData data = new CAN.CANData();

        if (can.readPacketLatest(API_RESPONSE_DISTANCE, data)) {
            if (data.length >= 3) {
                int mm = (data.data[0] & 0xFF) | ((data.data[1] & 0xFF) << 8);
                distanceMillimeters = mm;
                measurementValid = (data.data[2] & 0x01) != 0;
            }
        }

        if (can.readPacketLatest(API_RESPONSE_LIMIT_STATE, data)) {
            if (data.length >= 1) {
                int flags = data.data[0] & 0xFF;
                limitState = new LARSLimitState(
                    (flags & 0x01) != 0,
                    (flags & 0x02) != 0,
                    (flags & 0x04) != 0);
            }
        }

        if (can.readPacketLatest(API_RESPONSE_STATUS, data)) {
            if (data.length >= 3) {
                int flags = (data.data[2] & 0xFF);
                status = new LARSStatus(
                    (data.data[0] & 0x01) != 0,
                    (data.data[0] & 0x02) != 0,
                    (data.data[0] & 0x04) != 0,
                    (data.data[1] & 0xFF));
                // Reserved byte is intentionally ignored.
            }
        }
    }

    @Override
    public void close() {
        can.close();
    }
}
