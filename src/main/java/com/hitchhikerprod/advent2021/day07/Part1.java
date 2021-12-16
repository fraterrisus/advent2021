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
                    .map(value -> movementCostForPart2(value, j))
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

    private long movementCostForPart1(long from, long to) {
        return Math.abs(from - to);
    }

    private Long costHelper(Long distance) {
        final Long val = costs.get(distance);
        if (val != null) { return val; }

        final Long newVal = distance + costHelper(distance - 1);
        costs.put(distance, newVal);
        return newVal;
    }

    private Long movementCostForPart2(long from, long to) {
        return costHelper(Math.abs(from - to));
    }
}
