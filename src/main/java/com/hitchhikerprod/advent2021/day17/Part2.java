package com.hitchhikerprod.advent2021.day17;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Part2 {
    private final DataProvider provider;

    public Part2(DataProvider provider) {
        this.provider = provider;
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day17.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part2(provider).solve());
    }

    public long solve() {
        final Map<Integer, Range> xMap = new HashMap<>();
        for (int x = 0; x <= provider.getX2(); x++) {
            final Range range = simulateDX(x);
            if (range != null) xMap.put(x, range);
        }

        final Map<Integer, Range> yMap = new HashMap<>();
        for (int y = provider.getY1(); y <= provider.getY1() * -2; y++) {
            final Range range = simulateDY(y);
            if (range != null) yMap.put(y, range);
        }

        long count = 0;
        for (var ye : yMap.entrySet()) {
            for (var xe : xMap.entrySet()) {
                if (ye.getValue().overlaps(xe.getValue()) != null) {
                    System.out.println("(" + xe.getKey() + "," + ye.getKey() + ")");
                    count++;
                }
            }
        }

        return count;
    }

    private Range simulateDX(int initial) {
        Probe probe = new Probe(initial, 0);
        int steps = 0;
        int prev = Integer.MIN_VALUE;
        Integer minSteps = null;
        Integer maxSteps = null;

        while (true) {
            final var position = probe.position();

            if (provider.getX1() <= position.x() && position.x() <= provider.getX2()) {
                if (minSteps == null) {
                    minSteps = steps;
                }
                maxSteps = steps;
            }

            if (position.x() > provider.getX2()) break;

            if (position.x() == prev) {
                maxSteps = null;
                break;
            }

            prev = position.x();

            probe.simulate();
            steps++;
        }

        if (minSteps == null) {
            return null;
        }

        return new Range(minSteps, Optional.ofNullable(maxSteps));
    }

    private Range simulateDY(int initial) {
        Probe probe = new Probe(0, initial);
        int steps = 0;
        Integer minSteps = null;
        Integer maxSteps = null;

        while (true) {
            final var position = probe.position();

            if (provider.getY1() <= position.y() && position.y() <= provider.getY2()) {
                if (minSteps == null) {
                    minSteps = steps;
                }
                maxSteps = steps;
            }

            if (position.y() < provider.getY1()) break;

            probe.simulate();
            steps++;
        }

        if (minSteps == null) {
            return null;
        }

        return new Range(minSteps, Optional.ofNullable(maxSteps));
    }
}
