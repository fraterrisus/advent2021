package com.hitchhikerprod.advent2021.day17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Pattern;

public class DataProvider {
    private final int x1, y1, x2, y2;

    private static final Pattern AREA_REGEX = Pattern.compile(".* x=(\\d+)..(\\d+), y=([-]?\\d+)..([-]?\\d+).*");

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));

        try {
            final var line = reader.readLine();
            final var matchData = AREA_REGEX.matcher(line);
            if (matchData.matches()) {
                this.x1 = Integer.parseInt(matchData.group(1));
                this.x2 = Integer.parseInt(matchData.group(2));
                this.y1 = Integer.parseInt(matchData.group(3));
                this.y2 = Integer.parseInt(matchData.group(4));
            } else {
                throw new RuntimeException("Couldn't parse input file");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day17-sample.txt";
        final DataProvider provider = new DataProvider(inputFile);
        assert(provider.getX1() == 20);
        assert(provider.getX2() == 30);
        assert(provider.getY1() == -10);
        assert(provider.getY2() == -5);
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
}
