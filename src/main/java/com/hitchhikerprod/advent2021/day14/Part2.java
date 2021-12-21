package com.hitchhikerprod.advent2021.day14;

import java.util.HashMap;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Part2 {
    private final DataProvider data;

    public Part2(DataProvider provider) {
        this.data = provider;
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day14.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part2(provider).solve(40));
    }

    public long solve(final int steps) {
        // Reorganize the polymer as a map of adjacent characters and a frequency count.
        // i.e. {B}->{C}->{2} means that the string "BC" appears twice in the polymer.
        Map<Character, Map<Character, Long>> polymerMap = mapPolymerByElementPairsCount();

        // Reorganize the list of rules as a nested hash of characters.
        // i.e. {B}->{C}->{D} implies a rule "BC -> D"
        final Map<Character, Map<Character, Character>> ruleMap = data.getRules().stream()
                .collect(Collectors.groupingBy(Rule::from1, Collectors.toMap(Rule::from2, Rule::to)));

        // Iterate.
        for (int i = 0; i < steps; i++) {
            polymerMap = polymerize(polymerMap, ruleMap);
        }

        // The number of times a character appears in the final polymer is the sum of all frequency counts beneath it.
        // i.e. if {B}->{C}->{10} and {B}->{D}->{4} then 'B' must appear 14 times in the polymer.
        final Map<Character, Long> counts = polymerMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().values().stream()
                                .mapToLong(x -> x)
                                .sum()));

        // The final character of the string doesn't appear in the polymer map because nothing comes after it.
        final Character lastChar = data.getPolymer().get(data.getPolymer().size() - 1);
        counts.compute(lastChar, (k, v) -> (v == null) ? 1 : v + 1);

        // Run summaryStatistics to get the largest and smallest values.
        final LongSummaryStatistics stats = counts.values().stream().mapToLong(x -> x).summaryStatistics();
        return stats.getMax() - stats.getMin();
    }

    private Map<Character, Map<Character, Long>> mapPolymerByElementPairsCount() {
        final Map<Character, Map<Character, Long>> polymerMap = new HashMap<>();
        Character prevChar = null;
        for (Character thisChar : data.getPolymer()) {
            if (prevChar != null) {
                polymerMap.computeIfAbsent(prevChar, ch -> new HashMap<>());
                polymerMap.get(prevChar).compute(thisChar, (k, v) -> (v == null) ? 1 : v + 1);
            }
            prevChar = thisChar;
        }

        return polymerMap;
    }

    private BiFunction<Character, Long, Long> accumulator(Long base) {
        return (k, v) -> (v == null) ? base : base + v;
    }

    private Map<Character, Map<Character, Long>> polymerize(
            final Map<Character, Map<Character, Long>> polymerMap,
            final Map<Character, Map<Character, Character>> ruleMap)
    {
        final Map<Character, Map<Character, Long>> newMap = new HashMap<>();

        // The polymerize operation is "AB" + "AB->C" = "ACB", so:
        //   Iterate over all character pairs (A,B) in the existing polymer, saving the number of occurrences {n}.
        //   Lookup the rule for {A}->{B}, saving the character to insert {C}.
        //   Increment the new polymer {A}->{C} by +{n}.
        //   Increment the new polymer {C}->{B} by +{n}.
        for (final var e : polymerMap.entrySet()) {
            for (final var f : e.getValue().entrySet()) {
                final Character c1 = e.getKey();
                final Character c2 = f.getKey();
                final Long combinationCount = f.getValue();
                final Character newChar = ruleMap.get(c1).get(c2);

                if (newChar != null) {
                    newMap.computeIfAbsent(c1, ch -> new HashMap<>());
                    newMap.get(c1).compute(newChar, accumulator(combinationCount));
                    newMap.computeIfAbsent(newChar, ch -> new HashMap<>());
                    newMap.get(newChar).compute(c2, accumulator(combinationCount));
                }
            }
        }

        return newMap;
    }
}
