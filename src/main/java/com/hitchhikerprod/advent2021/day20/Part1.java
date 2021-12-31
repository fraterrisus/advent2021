package com.hitchhikerprod.advent2021.day20;

public class Part1 {
    private final DataProvider provider;

    public Part1(DataProvider provider) {
        this.provider = provider;
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day20.txt";
        final DataProvider provider = DataProvider.from(inputFile);
        System.out.println(new Part1(provider).solve(50));
    }

    public long solve(int iterations) {
        final Algorithm a = provider.getAlgorithm();
        Image image = provider.getImage();
        for (int i = 0; i < iterations; i++) {
            image = a.enhance(image);
        }
        return image.countOnPixels();
    }

}
