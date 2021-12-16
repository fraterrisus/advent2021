package com.hitchhikerprod.advent2021.day05;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class DataProvider {
    private static final Pattern PARSER_REGEX = Pattern.compile("(\\d+),(\\d+)\\s*->\\s*(\\d+),(\\d+)");

    private final List<Line> lines;

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        lines = parsePoints(reader);
    }

    public List<Line> getLines() {
        return lines;
    }

    private Line buildLine(String input) {
        var matcher = PARSER_REGEX.matcher(input);
        if (matcher.matches()) {
            return new Line(
                    new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
                    new Point(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)))
            );
        } else {
            return null;
        }
    }

    private List<Line> parsePoints(BufferedReader reader) {
        return reader.lines()
                .map(this::buildLine)
                .filter(Objects::nonNull)
                .toList();
    }
}
