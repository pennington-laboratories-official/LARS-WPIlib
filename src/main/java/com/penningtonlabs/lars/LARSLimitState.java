package com.penningtonlabs.lars;

/**
 * Most recently received LARS limit state.
 */
public record LARSLimitState(
        boolean forward,
        boolean reverse,
        boolean measurementValid) {

    public boolean anyLimit() {
        return forward || reverse;
    }
}
