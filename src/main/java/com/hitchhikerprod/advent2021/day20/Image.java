package com.hitchhikerprod.advent2021.day20;

import java.util.List;

public class Image {
    public static final char ON = '#';
    public static final char OFF = '.';

    public record Dimensions(int x, int y) {}

    private final List<String> rows;
    private final boolean infinity;

    public Image(List<String> rows, boolean infinity) {
        this.rows = rows;
        this.infinity = infinity;
    }

    public Image(List<String> rows) {
        this(rows, false);
    }

    public boolean at(int x, int y) {
        try {
            return (rows.get(y).charAt(x) == ON);
        } catch (IndexOutOfBoundsException e) {
            return infinity;
        }
    }

    public Dimensions getDimensions() {
        int xdim = rows.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
        int ydim = rows.size();
        return new Dimensions(xdim, ydim);
    }

    public long countOnPixels() {
        if (infinity) {
            throw new IllegalArgumentException();
        }
        return rows.stream()
                .flatMapToInt(String::chars)
                .filter(ch -> ch == ON)
                .count();
    }

    public String toString() {
        return String.join("\n", rows);
    }
}
