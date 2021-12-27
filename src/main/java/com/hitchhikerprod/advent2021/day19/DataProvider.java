package com.hitchhikerprod.advent2021.day19;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataProvider {
    private final List<List<Point>> readouts;

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        this.readouts = parseScanners(reader.lines().toList());
    }

    public static List<List<Point>> parseScanners(List<String> lines) {
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
}
