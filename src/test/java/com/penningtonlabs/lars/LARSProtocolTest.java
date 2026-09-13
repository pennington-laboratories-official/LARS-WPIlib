package com.penningtonlabs.lars;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class LARSProtocolTest {
    @Test
    void modeValuesAreStable() {
        assertEquals(0, LARSMode.STRAIGHT.getValue());
        assertEquals(1, LARSMode.FOUR_BY_FOUR.getValue());
        assertEquals(2, LARSMode.EIGHT_BY_EIGHT.getValue());
        assertEquals(3, LARSMode.LIMIT.getValue());
    }

    @Test
    void limitStateWorks() {
        LARSLimitState state = new LARSLimitState(true, false, true);
        assertEquals(true, state.forward());
        assertEquals(false, state.reverse());
        assertEquals(true, state.anyLimit());
    }

    @Test
    void statusFaultDetectionWorks() {
        LARSStatus status = new LARSStatus(true, true, true, 0);
        assertEquals(false, status.hasFault());

        LARSStatus fault = new LARSStatus(true, false, true, 1);
        assertEquals(true, fault.hasFault());
    }
}
