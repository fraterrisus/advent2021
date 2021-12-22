package com.hitchhikerprod.advent2021.day15;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;

public class DataProvider {
    private final int[][] levels;

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        this.levels = parseIntArray(reader);
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day15-sample.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(Arrays.deepToString(provider.getLevels()));
    }

    public int[][] getLevels() {
        return levels;
    }

    private int[][] parseIntArray(BufferedReader reader) {
        return reader.lines().map(line -> line.chars()
                        .mapToObj(ci -> (char) ci)
                        .map(String::valueOf)
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toArray(int[][]::new);
    }
}
