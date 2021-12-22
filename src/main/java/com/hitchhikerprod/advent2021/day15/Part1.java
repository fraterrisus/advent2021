package com.hitchhikerprod.advent2021.day15;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Part1 {
    private final int[][] field;

    private static final boolean PART2 = true;

    private record Point(int x, int y) {}

    public Part1(DataProvider provider) {
        if (PART2) {
            this.field = tile(provider.getLevels(), 5);
        } else {
            this.field = provider.getLevels();
        }
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day15.txt";
        //final String inputFile = "/inputs/day15-sample.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve());
    }

    public long solve() {
        final Set<Point> worklist = new HashSet<>();

        final int[][] minRisk = new int[field.length][field[0].length];
        for (var row : minRisk) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        minRisk[0][0] = 0;
        worklist.add(new Point(0,0));

        while (!worklist.isEmpty()) {
            // Select the shortest existing incomplete path(s) in the worklist.
            // Extend the path to all neighbors of those nodes:
            //   If (current length) + (new node cost) < (new node's existing path length)
            //     Set new node's path length to new value
            //     Add new node to worklist
            final var shortestKnownPath = worklist.stream()
                    .mapToInt(p -> minRisk[p.y()][p.x()])
                    .min().orElse(Integer.MAX_VALUE);
            final var closestNodes = worklist.stream()
                    .filter(p -> minRisk[p.y()][p.x()] == shortestKnownPath)
                    .toList();

            for (var node : closestNodes) {
                worklist.remove(node);
                final int myRisk = minRisk[node.y()][node.x()];

                // System.out.println("Working on " + node + " best path = " + myRisk);

                var neighbors = Stream.of(
                        new Point(node.x() - 1, node.y()),
                        new Point(node.x() + 1, node.y()),
                        new Point(node.x(), node.y() - 1),
                        new Point(node.x(), node.y() + 1)
                );

                neighbors.forEach(p -> {
                    try {
                        final int newRisk = myRisk + field[p.y()][p.x()];

                        // System.out.print("  Examining " + p + ":" + field[p.y()][p.x()] + " best path = " + minRisk[p.y()][p.x()]);

                        // This gate is necessary to prevent backtracking and loops, although it could just as easily
                        // be replaced by "if a path has already been found for this node, skip it" because the
                        // min-detector ('shortestKnownPath') guarantees that we have the best possible route
                        // (which also prevents us from ever evaluating a node twice)
                        if (newRisk < minRisk[p.y()][p.x()]) {
                            // System.out.print(" > " + newRisk);
                            minRisk[p.y()][p.x()] = newRisk;
                            worklist.add(p);
                        }

                        // System.out.println();

                    } catch (ArrayIndexOutOfBoundsException ignored) {}
                });
            }
        }

        return minRisk[minRisk.length-1][minRisk[0].length-1];
    }

    private int[][] tile(int[][] base, int factor) {
        final int oldDim = base.length;
        final int newDim = oldDim * factor;
        final int[][] field = new int[newDim][newDim];

        for (int s = 0; s < factor; s++) {
            for (int y = 0; y < oldDim; y++) {
                for (int t = 0; t < factor; t++) {
                    for (int x = 0; x < oldDim; x++) {
                        final int y1 = (oldDim * s) + y;
                        final int x1 = (oldDim * t) + x;
                        field[y1][x1] = base[y][x] + s + t;
                        if (field[y1][x1] > 9) { field[y1][x1] -= 9; }
                    }
                }
            }
        }

        return field;
    }
}
