package com.hitchhikerprod.advent2021.day22;

import org.junit.Test;

import static org.junit.Assert.*;

public class RangeTest {
    @Test
    public void TestIncludesWithMax() {
        final Range uut = new Range(5, 10);
        assertFalse(uut.includes(4));
        assertTrue(uut.includes(5));
        assertTrue(uut.includes(7));
        assertTrue(uut.includes(10));
        assertFalse(uut.includes(11));
    }

    @Test
    public void testBelow1() {
        final Range uut = new Range(5, 10);
        final Range result = uut.below(new Range(7, 10));
        assertEquals(result, new Range(5, 6));
    }

    @Test
    public void testAbove1() {
        final Range uut = new Range(5, 10);
        final Range result = uut.above(new Range(4, 6));
        assertEquals(result, new Range(7, 10));
    }

    @Test
    public void TestOverlaps1() {
        final Range uut = new Range(5, 10);
        final Range result = uut.overlaps(new Range(1, 3));
        assertNull(result);
    }

    @Test
    public void TestOverlaps2() {
        final Range uut = new Range(5, 10);
        final Range result = uut.overlaps(new Range(1, 5));
        assertEquals(new Range(5, 5), result);
    }

    @Test
    public void TestOverlaps3() {
        final Range uut = new Range(5, 10);
        final Range result = uut.overlaps(new Range(1, 7));
        assertEquals(new Range(5, 7), result);
    }

    @Test
    public void TestOverlaps4() {
        final Range uut = new Range(5, 10);
        final Range result = uut.overlaps(new Range(1, 15));
        assertEquals(uut, result);
    }

    @Test
    public void TestOverlaps6() {
        final Range uut = new Range(5, 10);
        final Range result = uut.overlaps(new Range(6, 8));
        assertEquals(new Range(6, 8), result);
    }

    @Test
    public void TestOverlaps7() {
        final Range uut = new Range(5, 10);
        final Range result = uut.overlaps(new Range(7, 12));
        assertEquals(new Range(7, 10), result);
    }

    @Test
    public void TestOverlaps9() {
        final Range uut = new Range(5, 10);
        final Range result = uut.overlaps(new Range(12, 15));
        assertNull(result);
    }
}
