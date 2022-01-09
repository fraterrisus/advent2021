package com.hitchhikerprod.advent2021.day22;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<Cube> cubes = new HashSet<>();

        for (var step : steps) {
            Set<Cube> newCubes;
            switch (step.op()) {
                case ON -> newCubes = addCubeToList(cubes, step.toCube());
                case OFF -> newCubes = subtractCubeFromList(cubes, step.toCube());
                default -> throw new RuntimeException();
            }
            cubes = consolidate(newCubes);
            System.out.println(cubes.size());
        }

        return cubes.stream().mapToLong(Cube::cardinality).sum();
    }

    private Set<Cube> addCubeToList(final Set<Cube> inCubes, final Cube thisCube) {
        Set<Cube> slivers = new HashSet<>();
        slivers.add(thisCube);

        for (Cube c : inCubes) {
            slivers = slivers.stream()
                    .flatMap(s -> s.minus(c).stream())
                    .collect(Collectors.toSet());
        }

        final Set<Cube> outCubes = new HashSet<>(inCubes);
        outCubes.addAll(slivers);
        return Set.copyOf(outCubes);
    }

    private Set<Cube> subtractCubeFromList(final Set<Cube> inCubes, final Cube thisCube) {
        return inCubes.stream()
                .flatMap(c -> c.minus(thisCube).stream())
                .collect(Collectors.toSet());
    }

    private Set<Cube> consolidate(final Set<Cube> inCubes) {
        return inCubes;
    }

    public long solveSlowly() {
        final int xMin = steps.stream().map(RebootStep::x).mapToInt(Range::min).min().orElseThrow();
        final int xMax = steps.stream().map(RebootStep::x).mapToInt(Range::max).max().orElseThrow();
        final int yMin = steps.stream().map(RebootStep::y).mapToInt(Range::min).min().orElseThrow();
        final int yMax = steps.stream().map(RebootStep::y).mapToInt(Range::max).max().orElseThrow();
        final int zMin = steps.stream().map(RebootStep::z).mapToInt(Range::min).min().orElseThrow();
        final int zMax = steps.stream().map(RebootStep::z).mapToInt(Range::max).max().orElseThrow();

        final SparseRange r = new SparseRange(xMin, xMax, yMin, yMax, zMin, zMax);

        for (var step : steps) {
            System.out.println(step);
            //for (int i = step.x().min(); i <= step.x().max(); i++) {
                for (int j = step.y().min(); j <= step.y().max(); j++) {
                    for (int k = step.z().min(); k <= step.z().max(); k++) {
                        switch (step.op()) {
                            case ON -> r.setRange(step.x().min(), step.x().max(), j, k);
                            case OFF -> r.clearRange(step.x().min(), step.x().max(), j, k);
                        }
                    }
                }
            //}
        }

        return r.cardinality();
    }
}
