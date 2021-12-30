package com.hitchhikerprod.advent2021.day19;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataProvider {
    private final List<List<Point>> readouts;

    public DataProvider(List<String> inputLines) {
        this.readouts = parseScanners(inputLines);
    }

    public static DataProvider from(String inputFile) {
        final InputStream inputData = DataProvider.class.getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        return new DataProvider(reader.lines().toList());
    }

    public List<List<Point>> parseScanners(List<String> lines) {
        List<List<Point>> readouts = new ArrayList<>();

        List<Point> points = new ArrayList<>();
        for (String line : lines) {
            if (line.equals("")) {
                continue;
            }
            if (line.startsWith("---")) {
                if (!points.isEmpty()) readouts.add(List.copyOf(points));
                points = new ArrayList<>();
                continue;
            }
            points.add(Point.from(line));
        }
        if (!points.isEmpty()) readouts.add(List.copyOf(points));

        return List.copyOf(readouts);
    }

    public List<List<Point>> getReadouts() {
        return readouts;
    }
}
