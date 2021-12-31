package com.hitchhikerprod.advent2021.day21;

public class DeterministicDie implements Die {
    private int nextNumber;
    private int numRolls;

    public DeterministicDie() {
        nextNumber = 0;
        numRolls = 0;
    }

    @Override
    public int roll() {
        numRolls++;
        int rv = nextNumber + 1;
        nextNumber = rv % 100;
        return rv;
    }

    @Override
    public int numRolls() {
        return numRolls;
    }
}
