package com.hitchhikerprod.advent2021.day18;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class DataProvider {
    private final List<Snumber> snumbers;

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        this.snumbers = reader.lines().map(DataProvider::parseSnumber).toList();
    }

    public List<Snumber> getSnumbers() {
        return this.snumbers;
    }

    private record Spointer(Snumber number, int loc) {}

    public static Snumber parseSnumber(String input) {
        return parseHelper(input.toCharArray(), 0).number();
    }

    private static Spointer parseHelper(char[] input, int ptr) {
        if (input[ptr] == '[') {
            final Spointer left = parseHelper(input, ptr + 1);
            assert(input[left.loc()] == ',');
            final Spointer right = parseHelper(input, left.loc() + 1);
            final Snumber newNumber = new Spair(left.number(), right.number());
            assert(input[right.loc()] == ']');
            return new Spointer(newNumber, right.loc() + 1);
        }
        int newPtr = ptr;
        StringBuilder digits = new StringBuilder();
        while (newPtr < input.length && input[newPtr] >= '0' && input[newPtr] <= '9') {
            digits.append(input[newPtr]);
            newPtr++;
        }
        final Snumber newNumber = new Sscalar(Integer.parseInt(digits.toString()));
        return new Spointer(newNumber, newPtr);
    }
}
