package com.hitchhikerprod.advent2021.day01;

import java.util.List;

public class Part1 {
    private final List<Integer> data;

    public Part1() {
        this.data = new DataProvider().getData();
    }

    public int solve() {
        int count = 0;
        int previous = Integer.MAX_VALUE;
        for (int value : data) {
            if (value > previous) {
                count++;
            }
            previous = value;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(new Part1().solve());
    }
}
