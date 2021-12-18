package com.hitchhikerprod.advent2021.day08;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Part2 {
    private final List<DataProvider.Entry> data;

    public Part2(DataProvider provider) {
        this.data = provider.getData();
    }

    public static void main(String[] argv) {
        //final String inputFile = "/inputs/day08-sample.txt";
        final String inputFile = "/inputs/day08-1.txt";
        var provider = new DataProvider(inputFile);
        System.out.println(new Part2(provider).solve());
    }

    public long solve() {
        long counter = 0L;
        for (var entry : data) {
            final Map<Character, boolean[]> wireMap = initializeWireMap();
            solveWireMap(wireMap, entry);
            counter += convertOutputsToDigits(wireMap, entry);
        }
        return counter;
    }

    private Map<Character, boolean[]> initializeWireMap() {
        final Map<Character, boolean[]> wireMap = new HashMap<>();
        for (char c = 'a'; c <= 'g'; c++) {
            wireMap.put(c, new boolean[]{ true, true, true, true, true, true, true });
        }
        return wireMap;
    }

    private void solveWireMap(Map<Character, boolean[]> wireMap, DataProvider.Entry entry) {
        Stream.concat(entry.patterns().stream(), entry.outputs().stream())
                .sorted(Comparator.comparing(String::length))
                .forEach(value -> {
                    matchDigitPatterns(wireMap, value);
                    reduceSinglesAndPairs(wireMap);
                });
    }

    private Integer convertOutputsToDigits(Map<Character, boolean[]> wireMap, DataProvider.Entry entry) {
        var digitString = entry.outputs().stream()
                .map(digit -> {
                    var x = digit.chars()
                            .mapToObj(ci -> wireMap.get((char) ci))
                            .reduce(new boolean[]{false, false, false, false, false, false, false},
                                    this::joinBooleanArraysWithOr);
                    return x;
                })
                .map(Solvers::lookupBooleanDigit)
                .map(String::valueOf)
                .collect(Collectors.joining());
        return Integer.parseInt(digitString);
    }

    private void matchDigitPatterns(Map<Character, boolean[]> wireMap, String value) {
        final var matches = Solvers.matchPatterns(wireMap, value).stream()
                .flatMapToInt(String::chars)
                .distinct()
                .mapToObj(ci -> String.valueOf((char) ci))
                .collect(Collectors.joining());

        final var mask = Solvers.charsToBools(matches);
        value.chars().forEach(ch -> wireMap.compute((char) ch, (k, v) -> joinBooleanArraysWithAnd(v, mask)));
    }

    private void reduceSinglesAndPairs(Map<Character, boolean[]> possibilities) {
        var truthMap = possibilities.entrySet().stream()
                .collect(Collectors.groupingBy(e -> countTruths(e.getValue()),
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())));

        final var singles = truthMap.get(1L);
        if (singles != null && singles.size() > 0) {
            for (char index : singles) {
                possibilities.keySet().stream()
                        .filter(key -> key != index)
                        .forEach(reduceHelper(possibilities, index));
            }
        }

        final var pairs = truthMap.get(2L);
        if (pairs != null && pairs.size() > 0) {
            for (char key1 : pairs) {
                List<Character> matchingKeys = pairs.stream()
                        .filter(k -> k != key1)
                        .filter(k -> Arrays.equals(possibilities.get(k), possibilities.get(key1)))
                        .toList();
                if (matchingKeys.size() > 0) {
                    final char key2 = matchingKeys.get(0);
                    possibilities.keySet().stream()
                            .filter(key -> key != key1)
                            .filter(key -> key != key2)
                            .forEach(reduceHelper(possibilities, key1));
                }
            }
        }
    }

    private Consumer<Character> reduceHelper(Map<Character, boolean[]> wireMap, char index) {
        return key -> wireMap.compute(key, (k, v) -> joinBooleanArraysWithNotAnd(v, wireMap.get(index)));
    }

    private boolean[] joinBooleanArraysWithAnd(boolean[] a, boolean[] b) {
        assert(a.length == b.length);
        final boolean c[] = new boolean[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] && b[i];
        }
        return c;
    }

    private boolean[] joinBooleanArraysWithNotAnd(boolean[] a, boolean [] b) {
        assert(a.length == b.length);
        final boolean c[] = new boolean[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] && !b[i];
        }
        return c;
    }

    private boolean[] joinBooleanArraysWithOr(boolean[] a, boolean[] b) {
        assert(a.length == b.length);
        final boolean c[] = new boolean[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] || b[i];
        }
        return c;
    }

    private long countTruths(boolean[] values) {
        long count = 0;
        for (boolean b : values) {
            if (b) { count++; }
        }
        return count;
    }
}
