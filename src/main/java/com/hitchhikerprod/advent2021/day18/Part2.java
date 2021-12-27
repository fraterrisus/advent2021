package com.hitchhikerprod.advent2021.day18;

import java.util.List;

public class Part2 {
    private final List<Snode> sforest;

    public Part2(DataProvider provider) {
        this.sforest = provider.getSforest();
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day18.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part2(provider).solve());
    }

    public long solve() {
        int bestSum = -1;
        for (Snode a : sforest) {
            for (Snode b : sforest) {
                if (a == b) { continue; }
                final int sum = Snode.add(a,b).magnitude();
                if (sum > bestSum) { bestSum = sum; }
            }
        }
        return bestSum;
    }
}
