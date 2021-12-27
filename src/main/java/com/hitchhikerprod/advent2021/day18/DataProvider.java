package com.hitchhikerprod.advent2021.day18;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class DataProvider {
    private final List<Snode> sforest;

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        this.sforest = reader.lines().map(DataProvider::parseSnode).toList();
    }

    public List<Snode> getSforest() {
        return this.sforest;
    }

    private record Spointer(Snode node, int loc) {}

    public static Snode parseSnode(String input) {
        return parseHelper(input.toCharArray(), 0).node();
    }

    private static Spointer parseHelper(char[] input, int ptr) {
        if (input[ptr] == '[') {
            final Spointer left = parseHelper(input, ptr + 1);
            assert(input[left.loc()] == ',');
            final Spointer right = parseHelper(input, left.loc() + 1);
            final Snode newPair = new Snode(left.node(), right.node());
            assert(input[right.loc()] == ']');
            return new Spointer(newPair, right.loc() + 1);
        }
        int newPtr = ptr;
        StringBuilder digits = new StringBuilder();
        while (newPtr < input.length && input[newPtr] >= '0' && input[newPtr] <= '9') {
            digits.append(input[newPtr]);
            newPtr++;
        }
        final Snode newScalar = new Snode(Integer.parseInt(digits.toString()));
        return new Spointer(newScalar, newPtr);
    }
}
