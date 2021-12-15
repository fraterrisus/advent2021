package com.hitchhikerprod.advent2021.day03;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part1 {
    private final Stream<String> data;

    public Part1() {
        data = new DataProvider().getData();
    }

    public int solve() {
        final List<Integer> countOnes = new ArrayList<>(10);
        final AtomicInteger dataCount = new AtomicInteger(0);
        data.forEach(value -> {
            dataCount.getAndIncrement();
            int[] bits = value.chars().toArray();
            for (int index = 0; index < bits.length; index++) {
                if (countOnes.size() <= index || countOnes.get(index) == null) {
                    countOnes.add(index, 0);
                }
                if (bits[index] == '0') {
                    countOnes.set(index, countOnes.get(index) + 2);
                }
            }
        });

        final String gammaString = countOnes.stream()
                .map(count -> (count >= dataCount.get()) ? '1' : '0')
                .map(Object::toString)
                .collect(Collectors.joining());
        final int gamma = Integer.parseInt(gammaString, 2);

        final String epsilonString = countOnes.stream()
                .map(count -> (count >= dataCount.get()) ? '0' : '1')
                .map(Object::toString)
                .collect(Collectors.joining());
        final int epsilon = Integer.parseInt(epsilonString, 2);

        return gamma * epsilon;
    }

    public static void main(String[] argv) {
        System.out.println(new Part1().solve());
    }
}
