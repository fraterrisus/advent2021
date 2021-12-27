package com.hitchhikerprod.advent2021.day18;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataProviderTest {
    @Test
    public void testParser1() {
        final Snumber snumber = DataProvider.parseSnumber("[1,2]");
        assertTrue(snumber.isPair());
        final Spair spair = (Spair)snumber;
        assertEquals(spair.left(), new Sscalar(1));
        assertEquals(spair.right(), new Sscalar(2));
    }

    @Test
    public void testParser2() {
        final Snumber snumber = DataProvider.parseSnumber("[1,[10,20]]");
        assertTrue(snumber.isPair());
        final Spair spair = (Spair)snumber;
        assertEquals(spair.left(), new Sscalar(1));
        assertEquals(spair.right(), new Spair(10, 20));
    }
}
