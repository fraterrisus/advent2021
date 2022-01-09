package com.hitchhikerprod.advent2021.day22;

import org.junit.Test;

import static org.junit.Assert.*;

public class SparseRangeTest {
    @Test
    public void testSet1() {
        final SparseRange uut = new SparseRange(0,10,0,10,0,10);
        uut.setPoint(5,0,0);
        uut.setPoint(6,0,0);
        uut.setPoint(4,0,0);
        uut.setPoint(1,0,0);
        uut.clearPoint(6,0,0);
        uut.setPoint(10,10,10);
        uut.setPoint(9,10,10);
        assertFalse(uut.getPoint(0,0,0));
        assertTrue(uut.getPoint(1,0,0));
        assertFalse(uut.getPoint(2,0,0));
        assertFalse(uut.getPoint(3,0,0));
        assertTrue(uut.getPoint(4,0,0));
        assertTrue(uut.getPoint(5,0,0));
        assertFalse(uut.getPoint(6,0,0));
        assertFalse(uut.getPoint(7,0,0));
        assertEquals(5, uut.cardinality());
    }

    @Test
    public void testNegativeMins() {
        final SparseRange uut = new SparseRange(-5,5,-5,5,-5,5);
        uut.setPoint(-5,-5,-5);
        uut.setPoint(0,0,0);
    }

    @Test
    public void testSetRange() {
        final SparseRange uut = new SparseRange(0,10,0,5,0,5);

        uut.setPoint(2,0,0);
        uut.setPoint(4,0,0);
        uut.setPoint(8,0,0);

        uut.setRange(1,6,0,0);
        assertFalse(uut.getPoint(0,0,0));
        assertTrue(uut.getPoint(1,0,0));
        assertTrue(uut.getPoint(2,0,0));
        assertTrue(uut.getPoint(4,0,0));
        assertTrue(uut.getPoint(6,0,0));
        assertFalse(uut.getPoint(7,0,0));
        assertTrue(uut.getPoint(8,0,0));
    }

    @Test
    public void testClearRange() {
        final SparseRange uut = new SparseRange(0,10,0,5,0,5);

        uut.setRange(2,6,0,0);
        uut.clearRange(3,6,0,0);

        assertFalse(uut.getPoint(1,0,0));
        assertTrue(uut.getPoint(2,0,0));
        assertFalse(uut.getPoint(3,0,0));
        assertFalse(uut.getPoint(4,0,0));
        assertFalse(uut.getPoint(5,0,0));
        assertFalse(uut.getPoint(6,0,0));
        assertFalse(uut.getPoint(7,0,0));
    }
}
