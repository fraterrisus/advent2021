package com.hitchhikerprod.advent2021.day22;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataProvider {
    private final List<RebootStep> rebootSteps;

    private static final Pattern OPERATION_REGEX = Pattern.compile("(on|off)\\s+x=(-?\\d+)\\.\\.(-?\\d+)," +
            "y=(-?\\d+)\\.\\.(-?\\d+),z=(-?\\d+)\\.\\.(-?\\d+)");

    public DataProvider(Stream<String> lines) {
        rebootSteps = lines.map(this::parse).toList();
    }

    public static DataProvider from(String filename) {
        final InputStream inputData = com.hitchhikerprod.advent2021.day21.DataProvider.class.getResourceAsStream(filename);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        return new DataProvider(reader.lines());
    }

    public List<RebootStep> getSteps() {
        return this.rebootSteps;
    }

    private RebootStep parse(String line) {
        final var matcher = OPERATION_REGEX.matcher(line);
        if (!matcher.matches()) {
            throw new RuntimeException("Failed to parse input data");
        }

        final RebootStep.Operation op = RebootStep.Operation.valueOf(matcher.group(1).toUpperCase(Locale.ROOT));

        final int[] coordinates = IntStream.range(2, matcher.groupCount() + 1)
                .mapToObj(matcher::group)
                .mapToInt(Integer::parseInt)
                .toArray();

        return new RebootStep(
                op,
                new Range(coordinates[0], coordinates[1]),
                new Range(coordinates[2], coordinates[3]),
                new Range(coordinates[4], coordinates[5])
        );
    }
}
