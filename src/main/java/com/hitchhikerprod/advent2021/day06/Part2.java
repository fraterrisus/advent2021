package com.hitchhikerprod.advent2021.day06;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Part2 {
    private final DataProvider data;

    public Part2(DataProvider provider) {
        this.data = provider;
    }

    // Index the fish by timer value and maintain a mapping of counts, rather than a raw list of timers
    public Long solve(int limit) {
        Map<Integer, Long> counts = data.getCounts();
        for (int i = 0; i < 9; i++) {
            counts.computeIfAbsent(i, key -> 0L);
        }

        for (int i = 0; i < limit; i++) {
            Map<Integer, Long> newCounts = new HashMap<>();

            for (var entry : counts.entrySet()) {
                if (entry.getKey() != 0 && entry.getValue() != null) {
                    newCounts.put(entry.getKey() - 1, entry.getValue());
                }
            }

            newCounts.computeIfAbsent(6, key -> 0L);
            newCounts.put(6, counts.get(0) + newCounts.get(6));
            newCounts.put(8, counts.get(0));

            counts = newCounts;
        }

        return counts.values().stream().filter(Objects::nonNull).reduce(Long::sum).orElse(0L);
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day06-1.txt";
        //final String inputFile = "/inputs/day06-sample.txt";

        var provider = new DataProvider(inputFile);
        System.out.println(new Part2(provider).solve(256));
    }
}
