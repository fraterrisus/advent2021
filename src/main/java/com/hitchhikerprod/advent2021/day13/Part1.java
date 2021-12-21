package com.hitchhikerprod.advent2021.day13;

import java.util.List;
import java.util.Set;

public class Part1 {
    private Set<Point> points;
    private final List<Fold> folds;
    private final Folder folder;

    public Part1(DataProvider provider) {
        folder = new Folder();
        this.points = provider.getPoints();
        this.folds = provider.getFolds();
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day13-1.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve());
    }

    public long solve() {
        var fold = folds.get(0);
        points = folder.foldPaper(points, fold);
        return points.size();
    }
}
