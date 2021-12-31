package com.hitchhikerprod.advent2021.day21;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    @Test
    public void testBoard() {
        final Player uut = new Player(1, 100);
        uut.advance(9);
        assertEquals(10, uut.position());
        uut.advance(1);
        assertEquals(1, uut.position());
    }

    @Test
    public void testScore() {
        final Player uut = new Player(5, 1000);
        uut.score(999);
        assertFalse(uut.hasWon());
        uut.score(1);
        assertTrue(uut.hasWon());
    }
}
