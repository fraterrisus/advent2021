package com.hitchhikerprod.advent2021.day18;

import java.util.ArrayList;
import java.util.List;

public class Part1 {
    private final List<Snode> sforest;

    public Part1(DataProvider provider) {
        this.sforest = provider.getSforest();
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day18.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve());
    }

    public long solve() {
        List<Snode> worklist = new ArrayList<>(sforest);
        Snode total = worklist.remove(0);
        while (!worklist.isEmpty()) {
            total = Snode.add(total, worklist.remove(0));
        }
        System.out.println(total);
        return total.magnitude();
    }
}
