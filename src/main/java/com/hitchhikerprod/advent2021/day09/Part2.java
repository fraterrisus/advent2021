package com.hitchhikerprod.advent2021.day09;

import java.util.*;
import java.util.stream.Stream;

public class Part2 {
    List<List<Integer>> depths;

    public Part2(DataProvider provider) {
        this.depths = provider.getDepths();
    }

    public static void main(String[] ignored) {
        final String inputFile = "/inputs/day09-1.txt";
        //final String inputFile = "/inputs/day09-sample.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part2(provider).solve());
    }

    public long solve() {
        return findMinima().stream()
                .map(this::findBasinSize)
                .sorted(Comparator.reverseOrder()) // descending order
                .limit(3) // top three
                .reduce((a, b) -> a * b) // take product
                .orElse(-1L);
    }

    private List<Point> findMinima() {
        final List<Point> minima = new ArrayList<>();
        for (int y = 0; y < depths.size(); y++) {
            final var row = depths.get(y);
            for (int x = 0; x < row.size(); x++) {
                if (isMinimum(x, y, depths)) {
                    minima.add(new Point(x,y));
                }
            }
        }
        return List.copyOf(minima);
    }

    private long findBasinSize(Point lowest) {
        final Set<Point> basin = new HashSet<>();
        final List<Point> worklist = new ArrayList<>();
        worklist.add(lowest);

        while (worklist.size() > 0) {
            final Point current = worklist.remove(0);
            if (basin.add(current)) { // skip duplicates
                worklist.addAll(findHigherNeighbors(current));
            }
        }

        return basin.size();
    }

    private List<Point> findHigherNeighbors(Point current) {
        final Integer myDepth = depths.get(current.y()).get(current.x());

        final Stream<Point> potentialNeighbors = Stream.of(
                new Point(current.x() - 1, current.y()),
                new Point(current.x() + 1, current.y()),
                new Point(current.x(), current.y() - 1),
                new Point(current.x(), current.y() + 1)
        );

        return potentialNeighbors.map(them -> {
                    try {
                        final Integer theirDepth = depths.get(them.y()).get(them.x());
                        if (theirDepth != 9 && theirDepth > myDepth) {
                            return them;
                        }
                    } catch (IndexOutOfBoundsException ignored) { }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private boolean isMinimum(int x, int y, List<List<Integer>> depths) {
        final Integer me = depths.get(y).get(x);

        try { if (depths.get(y).get(x-1) <= me) { return false; } }
        catch (IndexOutOfBoundsException ignored) { }

        try { if (depths.get(y).get(x+1) <= me) { return false; } }
        catch (IndexOutOfBoundsException ignored) { }

        try { if (depths.get(y-1).get(x) <= me) { return false; } }
        catch (IndexOutOfBoundsException ignored) { }

        try { if (depths.get(y+1).get(x) <= me) { return false; } }
        catch (IndexOutOfBoundsException ignored) { }

        return true;
    }
}
