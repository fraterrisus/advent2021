package com.hitchhikerprod.advent2021.day19;

import org.junit.Test;

import java.util.List;

public class PointTest {
    @Test
    public void transformTest() {
        final Point base = new Point(1,2,3);
        final List<Point> rotations = Point.TRANSFORMATIONS.stream()
                .map(base::transform)
                .distinct()
                .toList();
        rotations.forEach(System.out::println);
    }
}
