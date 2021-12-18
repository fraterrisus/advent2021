package com.hitchhikerprod.advent2021.day09;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class DataProvider {
    private final List<List<Integer>> depths;

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        this.depths = reader.lines().map(line -> line.chars()
                .mapToObj(ci -> (char) ci)
                .map(ch -> Integer.parseInt(ch.toString()))
                .toList())
                .toList();
    }

    public List<List<Integer>> getDepths() {
        return this.depths;
    }
}
