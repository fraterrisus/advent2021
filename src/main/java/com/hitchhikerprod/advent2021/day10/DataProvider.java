package com.hitchhikerprod.advent2021.day10;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Stream;

public class DataProvider {
    private final BufferedReader reader;

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        this.reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
    }

    public Stream<String> getStream() {
        return reader.lines();
    }
}
