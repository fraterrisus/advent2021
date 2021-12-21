package com.hitchhikerprod.advent2021.day14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataProvider {
    private final List<Character> polymer;
    private final List<Rule> rules;

    private static final Pattern RULE_REGEX = Pattern.compile("([A-Z][A-Z])\\s*->\\s*([A-Z])");

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        this.polymer = parsePolymer(reader);
        this.rules = parseRules(reader);
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day14-sample.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(provider.getPolymer());
        System.out.println(provider.getRules());
    }

    public List<Character> getPolymer() {
        return polymer;
    }

    public List<Rule> getRules() {
        return rules;
    }

    private List<Character> parsePolymer(BufferedReader reader) {
        try {
            final String rawPolymer = reader.readLine();
            reader.readLine(); // blank
            return rawPolymer.chars().mapToObj(ci -> (char) ci).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Rule> parseRules(BufferedReader reader) {
        return reader.lines()
                .map(RULE_REGEX::matcher)
                .filter(Matcher::matches)
                .map(m -> new Rule(m.group(1).charAt(0), m.group(1).charAt(1), m.group(2).charAt(0)))
                .toList();
    }
}
