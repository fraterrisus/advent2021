package com.hitchhikerprod.advent2021.day07;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Part1 {
    private final DataProvider data;
    private final Map<Long, Long> costs;

    Part1(DataProvider provider) {
        this.data = provider;
        this.costs = new HashMap<>();
        this.costs.put(0L, 0L);
    }

    public long solve() {
        var stats = data.getCrabs().stream().collect(Collectors.summarizingInt(x -> x));

        long min = Long.MAX_VALUE;
        for (long i = stats.getMin(); i < stats.getMax(); i++) {
            final long j = i;
            final long cost = data.getCrabs().stream()
                    .map(value -> Math.abs(value - j))
                    // For part 2, this is a triangular number function which can be computed analytically
                    .map(value -> ((value + 1) * value) / 2)
                    .reduce(Long::sum)
                    .orElse(0L);
            if (cost < min) { min = cost; }
        }
        return min;
    }

    public static void main(String[] argv) {
        // final String inputData = "/inputs/day07-sample.txt";
        final String inputData = "/inputs/day07-1.txt";

        var provider = new DataProvider(inputData);
        System.out.println(new Part1(provider).solve());
    }
}
