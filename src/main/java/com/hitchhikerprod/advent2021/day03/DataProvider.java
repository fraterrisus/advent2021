package com.hitchhikerprod.advent2021.day03;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Stream;

public class DataProvider {
    public Stream<String> getData() {
        final InputStream inputData = this.getClass().getResourceAsStream("/inputs/day03-1.txt");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        return reader.lines();
    }
}
