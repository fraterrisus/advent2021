package com.hitchhikerprod.advent2021.day07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class DataProvider {
    private final List<Integer> crabs;

    public DataProvider(String inputFile) {
        try {
            final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
            var strings = reader.readLine().split(",");
            this.crabs = Stream.of(strings).map(Integer::parseInt).toList();
        } catch (IOException e) {
            System.err.println("IOException caught");
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            System.err.println("Unparseable number: " + e);
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getCrabs() {
        return this.crabs;
    }
}
