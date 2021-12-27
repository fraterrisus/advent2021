package com.hitchhikerprod.advent2021.day17;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProbeTest {
    @Test
    public void test1() {
        Probe uut = new Probe(6, 0);
        for (int i = 0; i < 6; i++) {
            uut.simulate();
        }
        assertEquals(21, uut.position().x());
    }

    @Test
    public void testSample() {
        Probe uut = new Probe(7, 2);
        for (int i = 0; i < 7; i++) uut.simulate();
        var position = uut.position();
        assertEquals(28, position.x());
        assertEquals(-7, position.y());
    }
}

