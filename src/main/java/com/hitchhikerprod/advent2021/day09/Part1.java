package com.hitchhikerprod.advent2021.day09;

import java.util.ArrayList;
import java.util.List;

public class Part1 {
    List<List<Integer>> depths;

    public Part1(DataProvider data) {
        this.depths = data.getDepths();
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day09-1.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve());
    }

    public long solve() {
        List<Point> minima = new ArrayList<>();

        for (int y = 0; y < depths.size(); y++) {
            final var row = depths.get(y);
            for (int x = 0; x < row.size(); x++) {
                if (isMinimum(x, y, depths)) {
                    minima.add(new Point(x,y));
                }
            }
        }

        long counter = minima.size();
        for (var p : minima) {
            counter += depths.get(p.y()).get(p.x());
        }
        return counter;
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
