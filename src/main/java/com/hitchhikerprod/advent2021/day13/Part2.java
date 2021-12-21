package com.hitchhikerprod.advent2021.day13;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class Part2 {
    private Set<Point> points;
    private final List<Fold> folds;
    private final Folder folder;

    public Part2(DataProvider provider) {
        folder = new Folder();
        this.points = provider.getPoints();
        this.folds = provider.getFolds();
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day13-1.txt";
        final DataProvider provider = new DataProvider(inputFile);
        new Part2(provider).solve();
    }

    public void solve() {
        for (var fold : folds) {
            points = folder.foldPaper(points, fold);
        }

        final int maxY = points.stream().mapToInt(Point::y).max().orElse(-1);

        for (int y = 0; y <= maxY; y++) {
            final int thisY = y;
            var sortedPoints = points.stream()
                    .filter(p -> p.y() == thisY)
                    .sorted(Comparator.comparing(Point::x))
                    .toList();

            int lastX = 0;
            for (var p : sortedPoints) {
                while (lastX < p.x()) {
                    System.out.print(" ");
                    lastX++;
                }
                System.out.print("*");
                lastX++;
            }
            System.out.println();
        }
    }
}
