package com.hitchhikerprod.advent2021.day03;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part2 {
    private final Stream<String> data;

    public Part2() {
        data = new DataProvider().getData();
    }

    public char mostCommonBit(List<String> data, int index) {
        final int onesCount = data.stream()
                .map(word -> (word.charAt(index) == '1') ? 1 : 0)
                .reduce(Integer::sum)
                .orElse(0);
        return (onesCount * 2 >= data.size()) ? '1' : '0';
    }

    public int generatorRating(List<String> data) {
        int index = 0;
        while (data.size() > 1) {
            final var bit = mostCommonBit(data, index);
            final int copy = index;
            data = data.stream()
                    .filter(word -> word.charAt(copy) == bit)
                    .toList();
            index++;
        }
        return Integer.valueOf(data.get(0), 2);
    }

    public int scrubberRating(List<String> data) {
        int index = 0;
        while (data.size() > 1) {
            final var bit = (mostCommonBit(data, index) == '1') ? '0' : '1';
            final int copy = index;
            data = data.stream()
                    .filter(word -> word.charAt(copy) == bit)
                    .toList();
            index++;
        }
        return Integer.valueOf(data.get(0), 2);
    }

    public int solve() {
        List<String> allData = data.collect(Collectors.toList());
        int scrubber = scrubberRating(List.copyOf(allData));
        int generator = generatorRating(List.copyOf(allData));
        return scrubber * generator;
    }

    public static void main(String[] argv) {
        System.out.println(new Part2().solve());
    }
}
