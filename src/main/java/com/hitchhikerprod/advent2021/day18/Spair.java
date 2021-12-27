package com.hitchhikerprod.advent2021.day18;

public record Spair(Snumber left, Snumber right) implements Snumber {
    public Spair(int left, int right) {
        this(new Sscalar(left), new Sscalar(right));
    }

    @Override
    public boolean isPair() {
        return true;
    }

    @Override
    public int magnitude() {
        return (3 * left.magnitude()) + (2 * right.magnitude());
    }
}
