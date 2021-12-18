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

    public long solve() {
        long counter = 0L;

        for (var entry : data) {
            final Map<Character, boolean[]> possibilities = new HashMap<>();
            for (char c = 'a'; c <= 'g'; c++) {
                possibilities.put(c, new boolean[]{ true, true, true, true, true, true, true });
            }

            List<String> candidates = Stream.concat(entry.patterns().stream(), entry.outputs().stream())
                    .sorted(Comparator.comparing(String::length))
                    .toList();
            for (String value : candidates) {
                reducePossibilities(possibilities, value);
                lookForSolutions(possibilities);
            }

            for (var e : possibilities.entrySet()) {
                System.out.println(e.getKey() + ": " + Arrays.toString(e.getValue()));
            }
            System.out.println();

            var y = entry.outputs().stream()
                    .map(digit -> {
                        var x = digit.chars()
                                .mapToObj(ci -> possibilities.get((char) ci))
                                .reduce(new boolean[]{false, false, false, false, false, false, false},
                                        this::joinBooleanArrays);
                        return x;
                    })
                    .map(Solvers::lookupBooleanDigit)
                    .map(String::valueOf)
                    .collect(Collectors.joining());

            counter += Integer.parseInt(y);
        }

        return counter;
    }

    public static void main(String[] argv) {
        //final String inputFile = "/inputs/day08-sample.txt";
        final String inputFile = "/inputs/day08-1.txt";
        var provider = new DataProvider(inputFile);
        System.out.println(new Part2(provider).solve());
    }

    // -------------------------------------------------------------------------------

    private void reducePossibilities(Map<Character, boolean[]> possibilities, String value) {
        final Solvers s = new Solvers();
        final var matches = s.matchPatterns(possibilities, value).stream()
                .flatMapToInt(String::chars)
                .distinct()
                .mapToObj(ci -> String.valueOf((char) ci))
                .collect(Collectors.joining());
        final var mask = s.charsToBools(matches);

        value.chars().forEach(ch -> {
            final var p = possibilities.get((char) ch);
            for (int i = 0; i < p.length; i++) {
                p[i] = p[i] && mask[i];
            }
        });
    }

    private void lookForSolutions(Map<Character, boolean[]> possibilities) {
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

    private Consumer<Character> reduceHelper(Map<Character, boolean[]> possibilities, char index) {
        return key -> possibilities.compute(key, (k, v) -> {
            final var mask = possibilities.get(index);
            final var output = new boolean[7];
            for (int i = 0; i < 7; i++) {
                output[i] = v[i] && !mask[i];
            }
            return output;
        });
    }

    private boolean[] joinBooleanArrays(boolean[] a, boolean[] b) {
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
