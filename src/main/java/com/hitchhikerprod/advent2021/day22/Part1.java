package com.hitchhikerprod.advent2021.day22;

import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Part1 {
    private final List<RebootStep> steps;
    private final Set<Point> litPoints;

    public Part1(DataProvider provider) {
        this.steps = provider.getSteps();
        this.litPoints = new HashSet<>();
    }

    public static void main(String[] args) {
        final String filename = "/inputs/day22.txt";
        final DataProvider provider = DataProvider.from(filename);
        System.out.println(new Part1(provider).solve());
    }

    private static final int DIM = 101;

    public long solve() {
        final BitSet flags = new BitSet(DIM * DIM * DIM);

        for (var step : steps) {
            System.out.println(step);
            for (int i = step.x().from(); i <= step.x().to(); i++) {
                if (i < -50 || i > 50) { continue; }
                for (int j = step.y().from(); j <= step.y().to(); j++) {
                    if (j < -50 || j > 50) { continue; }
                    for (int k = step.z().from(); k <= step.z().to(); k++) {
                        if (k < -50 || k > 50) { continue; }
                        final int idx = index(i, j, k);
                        final boolean value = step.op() == RebootStep.Operation.ON;
                        // if (flags.get(idx) != value) {
                            // System.out.println(step.op() + " (" + i + "," + j + "," + k + ") = " + idx);
                            flags.set(idx, value);
                        // }
                    }
                }
            }
        }

        return flags.cardinality();
    }

    private int index(int x, int y, int z) {
        return (DIM * DIM * (z + 50)) +
                (DIM * (y + 50)) +
                (x + 50);
    }

    public long solveSlowly() {
        for (var step : steps) {
            System.out.println(step);
            final Set<Point> newPoints = buildPointsFromRange(step.x(), step.y(), step.z()).stream()
                    .filter(this::acceptableForPartOne)
                    .collect(Collectors.toSet());
            System.out.println("build");
            switch (step.op()) {
                case ON -> litPoints.addAll(newPoints);
                case OFF -> litPoints.removeAll(newPoints);
            }
            System.out.println("update");
        }
        return litPoints.size();
    }

    private Set<Point> buildPointsFromRange(Range x, Range y, Range z) {
        assert(x.from() <= x.to());
        assert(y.from() <= y.to());
        assert(z.from() <= z.to());

        if ((x.from() >= 50) || (x.to() <= -50) ||
                (y.from() >= 50) || (y.to() <= -50) ||
                (z.from() >= 50) || (z.to() <= -50)) {
            return new HashSet<>();
        }

        final Set<Point> points = new HashSet<>();
        for (int i = x.from(); i <= x.to(); i++) {
            for (int j = y.from(); j <= y.to(); j++) {
                for (int k = z.from(); k <= z.to(); k++) {
                    points.add(new Point(i, j, k));
                }
            }
        }
        return Set.copyOf(points);
    }

    private boolean acceptableForPartOne(Point p) {
        return (p.x() >= -50) && (p.x() <= 50) &&
                (p.y() >= -50) && (p.y() <= 50) &&
                (p.z() >= -50) && (p.z() <= 50);
    }
}
