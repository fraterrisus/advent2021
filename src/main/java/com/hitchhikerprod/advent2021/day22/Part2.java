package com.hitchhikerprod.advent2021.day22;

import java.util.List;

public class Part2 {
    private final List<RebootStep> steps;

    public Part2(DataProvider provider) {
        this.steps = provider.getSteps();
    }

    public static void main(String[] args) {
        final String filename = "/inputs/day22.txt";
        final DataProvider provider = DataProvider.from(filename);
        System.out.println(new Part2(provider).solve());
    }

    public long solve() {
        final int xMin = steps.stream().map(RebootStep::x).mapToInt(Range::from).min().orElseThrow();
        final int xMax = steps.stream().map(RebootStep::x).mapToInt(Range::to).max().orElseThrow();
        final int yMin = steps.stream().map(RebootStep::y).mapToInt(Range::from).min().orElseThrow();
        final int yMax = steps.stream().map(RebootStep::y).mapToInt(Range::to).max().orElseThrow();
        final int zMin = steps.stream().map(RebootStep::z).mapToInt(Range::from).min().orElseThrow();
        final int zMax = steps.stream().map(RebootStep::z).mapToInt(Range::to).max().orElseThrow();

        final SparseRange r = new SparseRange(xMin, xMax, yMin, yMax, zMin, zMax);

        for (var step : steps) {
            System.out.println(step);
            for (int i = step.x().from(); i <= step.x().to(); i++) {
                for (int j = step.y().from(); j <= step.y().to(); j++) {
                    for (int k = step.z().from(); k <= step.z().to(); k++) {
                        switch (step.op()) {
                            case ON -> r.setPoint(i, j, k);
                            case OFF -> r.clearPoint(i, j, k);
                        }
                    }
                }
            }
        }

        return r.cardinality();
    }
}
