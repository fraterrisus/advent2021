package com.hitchhikerprod.advent2021.day08;

import java.util.List;

public class Part1 {
    private final List<DataProvider.Entry> data;

    public Part1(DataProvider provider) {
        this.data = provider.getData();
    }

    public long solve() {
        return data.stream().flatMap(entry -> entry.outputs().stream())
                .filter(str -> { final int len = str.length(); return (len == 2 || len == 3 || len == 4 || len == 7); })
                .count();
    }

    public static void main(String[] argv) {
        //final String inputFile = "/inputs/day08-sample.txt";
        final String inputFile = "/inputs/day08-1.txt";
        var provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve());
    }
}
