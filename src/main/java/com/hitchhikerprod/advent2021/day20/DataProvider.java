package com.hitchhikerprod.advent2021.day20;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class DataProvider {
    private final Algorithm algorithm;
    private final Image image;

    public DataProvider(String algorithm, List<String> image) {
        this.algorithm = new Algorithm(algorithm);
        this.image = new Image(image, false);
    }

    public static DataProvider from(String filename) {
        final InputStream inputData = DataProvider.class.getResourceAsStream(filename);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        List<String> lines = reader.lines().toList();
        return new DataProvider(lines.get(0), lines.subList(2,lines.size()));
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Image getImage() {
        return image;
    }
}
