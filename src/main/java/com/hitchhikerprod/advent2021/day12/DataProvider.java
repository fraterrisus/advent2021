package com.hitchhikerprod.advent2021.day12;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class DataProvider {
    private record StringPair(String a, String b) {}

    public Set<Node> nodes;

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        nodes = parseNodes(reader);
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day12-1.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(provider.getNodes());
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    private Set<Node> parseNodes(BufferedReader reader) {
        final Set<Node> nodes = new HashSet<>();
        final List<StringPair> edges = new ArrayList<>();

        reader.lines().forEach(line -> {
            var names = line.split("-");
            edges.add(new StringPair(names[0], names[1]));
            nodes.add(new Node(names[0]));
            nodes.add(new Node(names[1]));
        });

        Map<String, Node> nodeMap = nodes.stream().collect(Collectors.toMap(Node::getName, n -> n));

        nodes.forEach(n -> {
            List<Node> adjacent = new ArrayList<>();
            edges.forEach(e -> {
                if (e.a().equals(n.getName())) {
                    adjacent.add(nodeMap.get(e.b()));
                } else if (e.b().equals(n.getName())) {
                    adjacent.add(nodeMap.get(e.a()));
                }
            });
            n.setNeighbors(adjacent);
        });

        return nodes;
    }
}
