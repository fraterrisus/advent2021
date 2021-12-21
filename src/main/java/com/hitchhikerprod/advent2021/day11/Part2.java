package com.hitchhikerprod.advent2021.day11;

import java.util.*;
import java.util.stream.Stream;

public class Part2 {
    final int[][] levels;

    public Part2(DataProvider data) {
        this.levels = data.getLevels();
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day11-1.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part2(provider).solve());
    }

    public long solve() {
        int step = 1;
        while(true) {
            incrementAllLevels();
            var worklist = findFlashingSquid();
            if (flashSquid(worklist)) {
                return step;
            }
            resetFlashedSquid();
            step++;
        }
    }

    private void incrementAllLevels() {
        for (int y = 0; y < levels.length; y++) {
            for (int x = 0; x < levels[y].length; x++) {
                levels[y][x]++;
            }
        }
    }

    private LinkedHashSet<Point> findFlashingSquid() {
        var worklist = new LinkedHashSet<Point>();
        for (int y = 0; y < levels.length; y++) {
            for (int x = 0; x < levels[y].length; x++) {
                if (levels[y][x] > 9) {
                    worklist.add(new Point(x, y));
                }
            }
        }
        return worklist;
    }

    private boolean flashSquid(LinkedHashSet<Point> worklist) {
        final var flashed = new HashSet<>();

        while (worklist.size() > 0) {
            var it = worklist.iterator();
            final var squid = it.next();
            it.remove();

            if (flashed.add(squid)) { // skip duplicates
                var points = Stream.of(
                        new Point(squid.x() - 1, squid.y() - 1),
                        new Point(squid.x() - 1, squid.y()),
                        new Point(squid.x() - 1, squid.y() + 1),
                        new Point(squid.x(), squid.y() - 1),
                        new Point(squid.x(), squid.y() + 1),
                        new Point(squid.x() + 1, squid.y() - 1),
                        new Point(squid.x() + 1, squid.y()),
                        new Point(squid.x() + 1, squid.y() + 1)
                );

                points.forEach(p -> {
                    try {
                        levels[p.y()][p.x()]++;
                        if (levels[p.y()][p.x()] > 9) {
                            worklist.add(p);
                        }
                    } catch(ArrayIndexOutOfBoundsException ignored) {}
                });
            }
        }

        return (flashed.size() == 100);
    }

    private void resetFlashedSquid() {
        for (int y = 0; y < levels.length; y++) {
            for (int x = 0; x < levels[y].length; x++) {
                if (levels[y][x] > 9) {
                    levels[y][x] = 0;
                }
            }
        }
    }
}
