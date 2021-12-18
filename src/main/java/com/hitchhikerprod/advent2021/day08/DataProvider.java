package com.hitchhikerprod.advent2021.day08;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DataProvider {
    record Entry(List<String> patterns, List<String> outputs) {}

    private final List<Entry> data;

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        this.data = parseEntries(reader);
    }

    public List<Entry> getData() {
        return this.data;
    }

    private List<Entry> parseEntries(BufferedReader reader) {
        return reader.lines().map(line -> {
            var tokens = line.split("\\s+");
            return new Entry(
                    Arrays.stream(Arrays.copyOfRange(tokens, 0, 10)).toList(),
                    Arrays.stream(Arrays.copyOfRange(tokens, 11, 15)).toList()
            );
        }).toList();
    }
}
