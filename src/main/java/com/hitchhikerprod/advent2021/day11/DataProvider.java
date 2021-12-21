package com.hitchhikerprod.advent2021.day11;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class DataProvider {
    private final int[][] levels;

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        levels = parseIntArray(reader);
    }

    public int[][] getLevels() {
        return this.levels;
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
