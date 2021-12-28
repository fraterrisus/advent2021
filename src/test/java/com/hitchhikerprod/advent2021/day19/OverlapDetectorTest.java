package com.hitchhikerprod.advent2021.day19;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class OverlapDetectorTest {
    @Test
    public void testOverlap() {
        final List<Point> a = new ArrayList<>();
        final List<Point> b = new ArrayList<>();

        a.add(new Point(404, -599, -901));
        a.add(new Point(528, -643, 409));

        b.add(new Point(686, 422, 578));
        b.add(new Point(605, 423, 415));

        var uut = new OverlapDetector(a, b);
        assertFalse(uut.detect());
    }
}
