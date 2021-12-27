package com.hitchhikerprod.advent2021.day18;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataProviderTest {
    @Test
    public void testParser1() {
        final Snode snumber = DataProvider.parseSnode("[1,2]");
        assertNull(snumber.getParent());
        assertTrue(snumber.isPair());
        assertEquals(1, snumber.getLeft().getScalar());
        assertEquals(2, snumber.getRight().getScalar());
    }

    @Test
    public void testParser2() {
        final Snode snumber = DataProvider.parseSnode("[1,[10,20]]");
        assertNull(snumber.getParent());
        assertTrue(snumber.isPair());
        assertEquals(snumber, snumber.getLeft().getParent());
        assertEquals(1, snumber.getLeft().getScalar());
        final Snode child = snumber.getRight();
        assertEquals(snumber, child.getParent());
        assertEquals(10, child.getLeft().getScalar());
        assertEquals(20, child.getRight().getScalar());
    }
}
