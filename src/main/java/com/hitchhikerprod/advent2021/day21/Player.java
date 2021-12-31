package com.hitchhikerprod.advent2021.day21;

import java.util.Objects;

public class Player {
    /** Stored as 0-9 to make the math easier, even though the board is ostensibly 1-10 */
    private int position;

    private int score;

    private final int winningScore;

    public Player(int position, int winningScore) {
        this.position = position - 1;
        this.score = 0;
        this.winningScore = winningScore;
    }

    public static Player copyOf(Player that) {
        final Player copy = new Player(that.position+1, that.winningScore);
        copy.score = that.score;
        return copy;
    }

    public int position() {
        return position + 1;
    }

    public int points() {
        return score;
    }

    public boolean hasWon() {
        return (score >= winningScore);
    }

    public void advance(int spaces) {
        position = (position + spaces) % 10;
    }

    public void score(int points) {
        score += points;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, score);
    }

    @Override
    public String toString() {
        return "Player[pos="+position()+", pts="+points()+"]";
    }
}
