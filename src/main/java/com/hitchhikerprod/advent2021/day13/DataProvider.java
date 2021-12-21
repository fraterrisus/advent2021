package com.hitchhikerprod.advent2021.day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class DataProvider {
    private final Set<Point> points;
    private final List<Fold> folds;

    private static final Pattern FOLD_PATTERN = Pattern.compile("fold along ([xy])=(\\d+)");

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        points = new HashSet<>();
        folds = new ArrayList<>();
        parseInstructions(reader);
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day13-sample.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(provider.getPoints());
        System.out.println(provider.getFolds());
    }

    public List<Fold> getFolds() {
        return folds;
    }

    public Set<Point> getPoints() {
        return points;
    }

    private void parseInstructions(BufferedReader reader) {
        try {
            while (true) {
                final String line = reader.readLine();
                if (line == null || line.equals("")) {
                    break;
                }
                final String[] dims = line.split(",");
                points.add(new Point(Integer.parseInt(dims[0]), Integer.parseInt(dims[1])));
            }

            while (true) {
                final String line = reader.readLine();
                if (line == null) {
                    break;
                }
                var match = FOLD_PATTERN.matcher(line);
                if (!match.matches()) {
                    System.err.println("Regex failure: " + line);
                    break;
                }

                folds.add(new Fold(
                        Integer.parseInt(match.group(2)),
                        Dimension.valueOf(match.group(1).toUpperCase())
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
