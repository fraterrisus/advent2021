package com.hitchhikerprod.advent2021.day16;

public class Part1 {
    private final PacketParser parser;

    public Part1(DataProvider provider) {
        this.parser = new PacketParser(provider.getBits());
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day16.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve());
    }

    public long solve() {
        return parser.parse().version();
    }
}
