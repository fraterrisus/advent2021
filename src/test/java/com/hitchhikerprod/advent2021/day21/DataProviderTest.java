package com.hitchhikerprod.advent2021.day21;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataProviderTest {
    @Test
    public void testFromFile() {
        var uut = DataProvider.from("/inputs/day21.txt", 1000);
        assertEquals(2, uut.getPlayer1().position());
        assertEquals(5, uut.getPlayer2().position());
    }
}
