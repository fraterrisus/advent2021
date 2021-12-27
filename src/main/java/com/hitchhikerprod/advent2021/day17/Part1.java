package com.hitchhikerprod.advent2021.day17;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Part1 {
    private final DataProvider provider;

    public Part1(DataProvider provider) {
        this.provider = provider;
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day17-sample.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve());
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

        List<Integer> yValues = yMap.keySet().stream().sorted(Comparator.reverseOrder()).toList();
        int globalBest = Integer.MIN_VALUE;
        for (Integer y : yValues) {
            final Range yRange = yMap.get(y);
            final var x = xMap.entrySet().stream()
                    .filter(e -> e.getValue().overlaps(yRange) != null)
                    .findAny();
            if (x.isEmpty()) continue;

            final Probe probe = new Probe(x.get().getKey(), y);
            int bestY = Integer.MIN_VALUE;
            while (true) {
                var pos = probe.simulate();
                if (pos.y() < bestY) {
                    System.out.println("Simulation from (" + x.get().getKey() + "," + y + ") reached " + bestY);
                    if (globalBest < bestY) { globalBest = bestY; }
                    break;
                }
                bestY = pos.y();
            }
        }

        return globalBest;
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
