package com.hitchhikerprod.advent2021.day08;

/*  By digit           By length
 *   0 = abc-efg (6)    2 = --c--f- (1)
 *   1 = --c--f- (2)    3 = a-c--f- (7)
 *   2 = a-cde-g (5)    4 = -bcd-f- (4)
 *   3 = a-cd-fg (5)    5 = a-cde-g (2)
 *   4 = -bcd-f- (4)        a-cd-fg (3)
 *   5 = ab-d-fg (5)        ab-d-fg (5)
 *   6 = ab-defg (6)    6 = abc-efg (0)
 *   7 = a-c--f- (3)        ab-defg (6)
 *   8 = abcdefg (7)        abcd-fg (9)
 *   9 = abcd-fg (6)    7 = abcdefg (8)
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Solvers {
    // Map of digit (0-9) to the pattern of segments that are enabled
    private static boolean[][] DIGIT_PATTERNS = {
            {  true,  true,  true, false,  true,  true,  true },
            { false, false,  true, false, false,  true, false },
            {  true, false,  true,  true,  true, false,  true },
            {  true, false,  true,  true, false,  true,  true },
            { false,  true,  true,  true, false,  true, false },
            {  true,  true, false,  true, false,  true,  true },
            {  true,  true, false,  true,  true,  true,  true },
            {  true, false,  true, false, false,  true, false },
            {  true,  true,  true,  true,  true,  true,  true },
            {  true,  true,  true,  true, false,  true,  true }
    };

    public static int lookupBooleanDigit(boolean[] wires) {
        for (int i = 0; i < DIGIT_PATTERNS.length; i++) {
            if (Arrays.equals(wires, DIGIT_PATTERNS[i])) {
                return i;
            }
        }
        return -1;
    }

    public static List<Character> boolsToChars(boolean[] set) {
        final List<Character> output = new ArrayList<>();
        for (int i = 0; i < set.length; i++) {
            if (set[i]) {
                output.add((char)('a' + i));
            }
        }
        return List.copyOf(output);
    }

    public static boolean[] charsToBools(String input) {
        final boolean[] output = new boolean[]{false, false, false, false, false, false, false};
        for (char ch : input.toCharArray()) {
            output[ch - 'a'] = true;
        }
        return output;
    }

    public static List<String> combineCharLists(List<List<Character>> input) {
        if (input.size() == 0) { return List.of(""); }
        final var thisSet = input.get(0);
        final var rest = input.subList(1, input.size());
        return thisSet.stream()
                .flatMap(prefix -> combineCharLists(rest).stream().map(suffix -> prefix + suffix))
                .toList();
    }

    private static String charListToString(List<Character> input) {
        var builder = new StringBuilder();
        input.forEach(builder::append);
        return builder.toString();
    }

    public static List<String> matchPatterns(Map<Character, boolean[]> possibilities, String needle) {
        final List<String> wireCombinations = allPossibilities(possibilities, needle);
        final List<String> matches = new ArrayList<>();
        for (boolean[] digitPattern : DIGIT_PATTERNS) {
            final String digitWires = charListToString(boolsToChars(digitPattern));
            if (wireCombinations.contains(digitWires)) {
                matches.add(digitWires);
            }
        }
        return matches;
    }

    private static List<String> allPossibilities(Map<Character, boolean[]> possibilities, String needle) {
        return allPermutations(needle).stream()
                .flatMap(perm -> combineCharLists(perm.chars()
                        .mapToObj(ch -> possibilities.get((char) ch))
                        .map(Solvers::boolsToChars)
                        .toList())
                        .stream())
                .distinct()
                .toList();
    }

    private static List<String> allPermutations(String str) {
        if (str.length() == 0) {
            ArrayList<String> baseResult = new ArrayList<>();
            baseResult.add("");
            return baseResult;
        }
        char ch = str.charAt(0);
        String rest = str.substring(1);
        List<String> recResult = allPermutations(rest);
        List<String> myResult = new ArrayList<>();
        for (String s : recResult) {
            for (int j = 0; j <= s.length(); j++) {
                String newString = s.substring(0, j) + ch + s.substring(j);
                myResult.add(newString);
            }
        }
        return myResult;
    }
}
