package com.hitchhikerprod.advent2021.day12;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class Part1 {
    private final Set<Node> graph;

    public Part1(DataProvider data) {
        this.graph = data.getNodes();
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day12-1.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve());
    }

    public long solve() {
        final Node root = graph.stream().filter(node -> "start".equals(node.getName())).findFirst()
                .orElseThrow(() -> new RuntimeException("No 'start' node found."));
        final Set<Node> seen = Set.of();

        return solveHelper(root, seen);
    }

    private long solveHelper(Node current, Set<Node> seen) {
        if (current.getName().equals("end")) {
            return 1L;
        }

        Set<Node> mySeen = new LinkedHashSet<>(seen);
        if (current.getName().equals(current.getName().toLowerCase(Locale.ROOT))) {
            mySeen.add(current);
        }

        return current.getNeighbors().stream()
                .filter(node -> ! mySeen.contains(node))
                .mapToLong(node -> solveHelper(node, mySeen))
                .sum();
    }
}
