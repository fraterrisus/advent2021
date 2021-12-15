package com.hitchhikerprod.advent2021.day01;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class DataProvider {
    public List<Integer> getData() {
        final InputStream inputData = this.getClass().getResourceAsStream("/inputs/day01-1.txt");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        return reader.lines().map(Integer::valueOf).toList();
    }
}
