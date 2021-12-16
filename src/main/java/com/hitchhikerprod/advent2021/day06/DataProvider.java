package com.hitchhikerprod.advent2021.day06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProvider {
    private final List<Integer> fish;

    public DataProvider(String inputFile) {
        try {
            final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
            var strings = reader.readLine().split(",");
            this.fish = Stream.of(strings).map(Integer::parseInt).toList();
        } catch (IOException e) {
            System.err.println("IOException caught");
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            System.err.println("Unparseable number: " + e);
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getFish() {
        return this.fish;
    }

    public Map<Integer,Long> getCounts() {
        return this.fish.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
