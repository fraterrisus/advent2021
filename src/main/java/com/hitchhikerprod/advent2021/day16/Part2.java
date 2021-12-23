package com.hitchhikerprod.advent2021.day16;

public class Part2 {
    private final PacketParser parser;

    public Part2(DataProvider provider) {
        this.parser = new PacketParser(provider.getBits(), true);
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day16.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part2(provider).solve());
    }

    public long solve() {
        return parser.parse().value();
    }
}
