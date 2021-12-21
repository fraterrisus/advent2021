package com.hitchhikerprod.advent2021.day14;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Part1 {
    private final DataProvider data;

    public Part1(DataProvider provider) {
        this.data = provider;
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day14.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve(40));
    }

    public long solve(final int steps) {
        List<Character> polymer = data.getPolymer();

        final Map<Character, List<Rule>> ruleMap = data.getRules().stream()
                .collect(Collectors.groupingBy(Rule::from1, Collectors.toList()));

        for (int i = 0; i < steps; i++) {
            polymer = polymerize(polymer, ruleMap);
        }

        var stats = polymer.stream()
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                .values().stream()
                .collect(Collectors.summarizingLong(x -> x));
        return stats.getMax() - stats.getMin();
    }

    private List<Character> polymerize(final List<Character> polymer, final Map<Character, List<Rule>> rules) {
        final List<Character> newPolymer = new LinkedList<>();
        newPolymer.add(polymer.get(0));
        Character holder = null;
        for (final Character thisChar : polymer) {
            if (holder == null) {
                holder = polymer.get(0);
                continue;
            }
            final Character prevChar = holder;
            var matchingRule = rules.getOrDefault(prevChar, List.of()).stream()
                    .filter(rule -> rule.from2() == thisChar)
                    .findAny();
            matchingRule.ifPresent(rule -> newPolymer.add(rule.to()));
            newPolymer.add(thisChar);
            holder = thisChar;
        }
        return newPolymer;
    }
}
