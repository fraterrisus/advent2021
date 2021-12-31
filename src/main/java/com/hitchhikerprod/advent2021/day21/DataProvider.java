package com.hitchhikerprod.advent2021.day21;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataProvider {
    private final Player p0;
    private final Player p1;

    private static final Pattern PLAYER_REGEX = Pattern.compile("Player [0-9] starting position: ([0-9]+)");

    public DataProvider(int p0, int p1, int winningScore) {
        this.p0 = new Player(p0, winningScore);
        this.p1 = new Player(p1, winningScore);
    }

    public static DataProvider from(String filename, int winningScore) {
        final InputStream inputData = DataProvider.class.getResourceAsStream(filename);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        var positions = reader.lines()
                .map(PLAYER_REGEX::matcher)
                .filter(Matcher::matches)
                .map(m -> m.group(1))
                .mapToInt(Integer::parseInt)
                .toArray();
        return new DataProvider(positions[0], positions[1], winningScore);
    }

    public Player getPlayer1() {
        return p0;
    }

    public Player getPlayer2() {
        return p1;
    }
}
