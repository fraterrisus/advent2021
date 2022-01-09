package com.hitchhikerprod.advent2021.day22;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CubeTest {
    @Test
    public void testOverlaps1() {
        final Cube a = new Cube(new Range(0,3), new Range(0,3), new Range(0,3));
        final Cube b = new Cube(new Range(1,4), new Range(1,4), new Range(1,4));
        final Cube overlap = a.overlaps(b);
        assertEquals(overlap, new Cube(new Range(1,3), new Range(1,3), new Range(1,3)));
    }

    @Test
    public void testOverlaps2() {
        final Cube a = new Cube(new Range(2,4), new Range(2,4), new Range(2,4));
        final Cube b = new Cube(new Range(1,5), new Range(1,5), new Range(0,6));
        final Cube overlap = b.overlaps(a);
        assertEquals(overlap, a);
    }

    @Test
    public void testMinus1() {
        final Cube a = new Cube(new Range(0,3), new Range(0,3), new Range(0,3));
        final Cube b = new Cube(new Range(1,2), new Range(1,2), new Range(1,2));
        final Set<Cube> cubes = a.minus(b);

        final long totalPoints = cubes.stream().mapToLong(Cube::cardinality).sum();
        assertEquals(56, totalPoints);
    }

    @Test
    public void testMinus2() {
        final Cube a = new Cube(new Range(0,3), new Range(0,3), new Range(0,3));
        final Cube b = new Cube(new Range(1,2), new Range(1,2), new Range(1,2));
        final Set<Cube> cubes = b.minus(a);
        assertTrue(cubes.isEmpty());
    }
}
