package com.hitchhikerprod.advent2021.day18;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SnodeTest {
    private static final Map<String, String> EXPLOSION_TEST = new HashMap<>();
    static {
        EXPLOSION_TEST.put("[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]");
        EXPLOSION_TEST.put("[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]");
        EXPLOSION_TEST.put("[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]");
        EXPLOSION_TEST.put("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
        EXPLOSION_TEST.put("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]");
    }

    @Test
    public void testExplodeYes() {
        EXPLOSION_TEST.forEach((key, value) -> {
            final Snode uut = DataProvider.parseSnode(key);
            assertTrue(uut.explode());
            assertEquals(value, uut.toString());
        });
    }

    @Test
    public void testExplodeNo() {
        final Snode uut = DataProvider.parseSnode("[1,[2,[3,9]]]");
        assertFalse(uut.explode());
    }

    @Test
    public void testMagnitude1() {
        assertEquals(29, new Snode(new Snode(9),new Snode(1)).magnitude());
        assertEquals(21, new Snode(new Snode(1),new Snode(9)).magnitude());
    }

    @Test
    public void testMagnitude2() {
        final Snode uut = new Snode(
            new Snode(new Snode(9),new Snode(1)),
            new Snode(new Snode(1),new Snode(9))
        );
        assertEquals(129, uut.magnitude());
    }

    @Test
    public void testSplitNo() {
        final Snode uut = DataProvider.parseSnode("[1,9]");
        assertFalse(uut.split());
        assertEquals("[1,9]", uut.toString());
    }

    @Test
    public void testSplitEven() {
        final Snode uut = DataProvider.parseSnode("[1,10]");
        assertTrue(uut.split());
        assertEquals("[1,[5,5]]", uut.toString());
    }

    @Test
    public void testSplitOdd() {
        final Snode uut = DataProvider.parseSnode("[1,11]");
        assertTrue(uut.split());
        assertEquals("[1,[5,6]]", uut.toString());
    }
}
