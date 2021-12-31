package com.hitchhikerprod.advent2021.day21;

import java.util.ArrayList;
import java.util.List;

public class Part1 {
    private final List<Player> players;
    private final Die die;

    public Part1(DataProvider provider) {
        this.players = new ArrayList<>();
        this.players.add(provider.getPlayer1());
        this.players.add(provider.getPlayer2());
        die = new DeterministicDie();
    }

    public static void main(String[] argv) {
        final DataProvider provider = DataProvider.from("/inputs/day21.txt", 1000);
        System.out.println(new Part1(provider).solve());
    }

    public long solve() {
        game : while(true) {
            for (Player current : players) {
                int myRoll = die.roll() + die.roll() + die.roll();
                current.advance(myRoll);
                current.score(current.position());
                if (current.hasWon()) { break game; }
            }
        }

        return (long) die.numRolls() * players.stream().mapToInt(Player::points).min().orElse(0);
    }
}
