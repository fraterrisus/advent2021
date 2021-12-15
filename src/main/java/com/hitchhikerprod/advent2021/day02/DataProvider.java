package com.hitchhikerprod.advent2021.day02;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Stream;

public class DataProvider {
    public Stream<Command> getData() {
        final InputStream inputData = this.getClass().getResourceAsStream("/inputs/day02-1.txt");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        return reader.lines().map(line -> {
            final var fields = line.split("\s+");
            return new Command(Direction.valueOf(fields[0].toUpperCase()), Integer.parseInt(fields[1]));
        });
    }
}
