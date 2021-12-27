package com.hitchhikerprod.advent2021.day18;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpairTest {
    @Test
    public void testMagnitude() {
        assertEquals(29, new Spair(9, 1).magnitude());
        assertEquals(21, new Spair(1, 9).magnitude());
    }

    @Test
    public void testMagnitudeRecursive() {
        assertEquals(129, new Spair(new Spair(9,1), new Spair(1,9)).magnitude());
    }
}
