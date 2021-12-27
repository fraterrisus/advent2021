package com.hitchhikerprod.advent2021.day17;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class RangeTest {
    @Test
    public void TestIncludesWithMax() {
        final Range uut = new Range(5, Optional.of(10));
        assertFalse(uut.includes(4));
        assertTrue(uut.includes(5));
        assertTrue(uut.includes(7));
        assertTrue(uut.includes(10));
        assertFalse(uut.includes(11));
    }

    @Test
    public void TestIncludesWithoutMax() {
        final Range uut = new Range(5, Optional.empty());
        assertFalse(uut.includes(4));
        assertTrue(uut.includes(5));
        assertTrue(uut.includes(10));
        assertTrue(uut.includes(Integer.MAX_VALUE));
    }

    @Test
    public void TestOverlaps1() {
        final Range uut = new Range(5, Optional.of(10));
        final Range result = uut.overlaps(new Range(1, Optional.of(3)));
        assertNull(result);
    }

    @Test
    public void TestOverlaps2() {
        final Range uut = new Range(5, Optional.of(10));
        final Range result = uut.overlaps(new Range(1, Optional.of(5)));
        assertEquals(new Range(5, Optional.of(5)), result);
    }

    @Test
    public void TestOverlaps3() {
        final Range uut = new Range(5, Optional.of(10));
        final Range result = uut.overlaps(new Range(1, Optional.of(7)));
        assertEquals(new Range(5, Optional.of(7)), result);
    }

    @Test
    public void TestOverlaps4() {
        final Range uut = new Range(5, Optional.of(10));
        final Range result = uut.overlaps(new Range(1, Optional.of(15)));
        assertEquals(uut, result);
    }

    @Test
    public void TestOverlaps5() {
        final Range uut = new Range(5, Optional.of(10));
        final Range result = uut.overlaps(new Range(1, Optional.empty()));
        assertEquals(uut, result);
    }

    @Test
    public void TestOverlaps6() {
        final Range uut = new Range(5, Optional.of(10));
        final Range result = uut.overlaps(new Range(6, Optional.of(8)));
        assertEquals(new Range(6, Optional.of(8)), result);
    }

    @Test
    public void TestOverlaps7() {
        final Range uut = new Range(5, Optional.of(10));
        final Range result = uut.overlaps(new Range(7, Optional.of(12)));
        assertEquals(new Range(7, Optional.of(10)), result);
    }

    @Test
    public void TestOverlaps8() {
        final Range uut = new Range(5, Optional.of(10));
        final Range result = uut.overlaps(new Range(7, Optional.empty()));
        assertEquals(new Range(7, Optional.of(10)), result);
    }

    @Test
    public void TestOverlaps9() {
        final Range uut = new Range(5, Optional.of(10));
        final Range result = uut.overlaps(new Range(12, Optional.of(15)));
        assertNull(result);
    }

    @Test
    public void TestOverlaps10() {
        final Range uut = new Range(5, Optional.of(10));
        final Range result = uut.overlaps(new Range(12, Optional.empty()));
        assertNull(result);
    }
}
