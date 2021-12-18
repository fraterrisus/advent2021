package com.hitchhikerprod.advent2021.day10;

import java.util.List;
import java.util.Stack;

public class Part1 {
    public static void main(String[] argv) {
        final String inputFile = "/inputs/day10-1.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part1().parse(provider));
    }

    public long parse(DataProvider provider) {
        return provider.getStream()
                .map(this::parseLineAndScore)
                .reduce(Long::sum)
                .orElse(-1L);
    }

    private long parseLineAndScore(String line) {
        Stack<Character> openChunks = new Stack<>();
        for (char ch : line.toCharArray()) {
            switch (ch) {
                case '(', '[', '{', '<' -> openChunks.push(ch);
                case ')' -> {
                    final char open = openChunks.pop();
                    if (open != '(') return 3L;
                }
                case ']' -> {
                    final char open = openChunks.pop();
                    if (open != '[') return 57L;
                }
                case '}' -> {
                    final char open = openChunks.pop();
                    if (open != '{') return 1197L;
                }
                case '>' -> {
                    final char open = openChunks.pop();
                    if (open != '<') return 25137L;
                }
                default -> System.err.println("Ignoring unrecognized character " + ch);
            }
        }
        return 0L;
    }
}
