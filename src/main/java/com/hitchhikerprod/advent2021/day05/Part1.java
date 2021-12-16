package com.hitchhikerprod.advent2021.day05;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {
    private final DataProvider data;

    private static final boolean PART1 = false;

    Part1(DataProvider provider) {
        data = provider;
    }

    public long solve() {
        Map<Integer, Map<Integer, Integer>> seenCount = new HashMap<>();

        // For each line, generate the list of points that make up the line.
        // For each point, increment a map-based x/y counter.
        // The use of HashMap.compute helps us manage generating empty maps for coordinates that haven't been counted.
        for (Line line : data.getLines()) {
            final List<Point> points = PART1 ? line.getPointsForCardinalLines() : line.getAllPoints();
            for (Point point : points) {
                seenCount.compute(point.x(), (k1, xMap) -> {
                    final Map<Integer, Integer> innerMap = (xMap == null) ? new HashMap<>() : xMap;
                    innerMap.compute(point.y(), (k2, count) -> (count == null) ? 1 : count + 1);
                    return innerMap;
                });
            }
        }

        // Count all instances of points where the count is greater than 1.
        return seenCount.values().stream()
                .map(innerMap -> innerMap.values().stream()
                        .filter(val -> val > 1)
                        .count())
                .reduce(Long::sum).orElse(0L);
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day05-1.txt";
        //final String inputFile = "/inputs/day05-sample.txt";

        var provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve());
    }
}
