package com.hitchhikerprod.advent2021.day12;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Part2 {
    private final Set<Node> graph;

    public Part2(DataProvider data) {
        this.graph = data.getNodes();
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day12-1.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part2(provider).solve());
    }

    public long solve() {
        final Node root = graph.stream().filter(node -> "start".equals(node.getName())).findFirst()
                .orElseThrow(() -> new RuntimeException("No 'start' node found."));
        final LinkedHashMap<Node, Integer> seen = new LinkedHashMap<>();

        return solveHelper(root, seen);
    }

    private long solveHelper(Node current, Map<Node, Integer> seen) {
        if (current.getName().equals("end")) {
            return 1L;
        }

        Map<Node, Integer> mySeen = new LinkedHashMap<>(seen);
        if (current.getName().equals(current.getName().toLowerCase(Locale.ROOT))) {
            mySeen.compute(current, (node, count) -> (count == null) ? 1 : count + 1);
        }

        final boolean doubleVisit = mySeen.values().stream().anyMatch(i -> i == 2);

        return current.getNeighbors().stream()
                .filter(node -> !node.getName().equals("start"))
                .filter(node -> (!doubleVisit || !mySeen.containsKey(node)))
                .mapToLong(node -> solveHelper(node, mySeen))
                .sum();
    }
}
