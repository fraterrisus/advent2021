package com.hitchhikerprod.advent2021.day10;

import java.util.Stack;

public class Part2 {
    public static void main(String[] argv) {
        final String inputFile = "/inputs/day10-1.txt";
        //final String inputFile = "/inputs/day10-sample.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part2().parse(provider));
    }

    public long parse(DataProvider provider) {
        final var scores = provider.getStream()
                .map(this::parseLineAndScore)
                .filter(x -> x > 0)
                .sorted()
                .toList();
        System.out.println(scores);
        final var middle = scores.size() / 2; // "There will always be an odd number of scores to consider."
        return scores.get(middle);
    }

    private long parseLineAndScore(String line) {
        Stack<Character> openChunks = new Stack<>();
        for (char ch : line.toCharArray()) {
            switch (ch) {
                case '(', '[', '{', '<' -> openChunks.push(ch);
                case ')' -> {
                    final char open = openChunks.pop();
                    if (open != '(') return 0L;
                }
                case ']' -> {
                    final char open = openChunks.pop();
                    if (open != '[') return 0L;
                }
                case '}' -> {
                    final char open = openChunks.pop();
                    if (open != '{') return 0L;
                }
                case '>' -> {
                    final char open = openChunks.pop();
                    if (open != '<') return 0L;
                }
                default -> System.err.println("Ignoring unrecognized character " + ch);
            }
        }

        long score = 0L;
        while (!openChunks.empty()) {
            score *= 5;
            switch (openChunks.pop()) {
                case '(' -> score += 1;
                case '[' -> score += 2;
                case '{' -> score += 3;
                case '<' -> score += 4;
            }
        }
        return score;
    }
}
