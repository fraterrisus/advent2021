package com.hitchhikerprod.advent2021.day18;

public record Sscalar(int value) implements Snumber {
    @Override
    public boolean isPair() {
        return false;
    }

    @Override
    public int magnitude() {
        return value;
    }
}
