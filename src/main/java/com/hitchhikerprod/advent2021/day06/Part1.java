package com.hitchhikerprod.advent2021.day06;

import java.util.ArrayList;
import java.util.List;

public class Part1 {
    private final DataProvider data;

    public Part1(DataProvider provider) {
        this.data = provider;
    }

    // Brute force simulator; too slow for part 2
    public int solve(int limit) {
        List<Integer> timers = data.getFish();

        for (int day = 0; day < limit; day++) {
            List<Integer> newTimers = new ArrayList<>();
            for (Integer clock : timers) {
                if (clock == 0) {
                    newTimers.add(6); // reset the clock on this fish
                    newTimers.add(8); // add a new fish
                } else {
                    newTimers.add(clock - 1);
                }
            }
            timers = newTimers;
        }

        return timers.size();
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day06-1.txt";
        //final String inputFile = "/inputs/day06-sample.txt";

        var provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve(80));
    }
}
