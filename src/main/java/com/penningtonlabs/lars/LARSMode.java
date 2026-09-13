package com.penningtonlabs.lars;

/**
 * Operating mode of a LARS sensor.
 */
public enum LARSMode {
    STRAIGHT(0),
    FOUR_BY_FOUR(1),
    EIGHT_BY_EIGHT(2),
    LIMIT(3);

    private final int value;

    LARSMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    static LARSMode fromValue(int value) {
        for (LARSMode mode : values()) {
            if (mode.value == value) {
                return mode;
            }
        }
        return STRAIGHT;
    }
}
