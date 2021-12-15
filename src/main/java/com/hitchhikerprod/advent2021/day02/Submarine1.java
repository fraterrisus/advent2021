package com.hitchhikerprod.advent2021.day02;

public class Submarine1 {
    private int position = 0;
    private int depth = 0;

    public void dive(int distance) {
        depth += distance;
    }

    public void sail(int distance) {
        position += distance;
    }

    public int calculate() {
        return position * depth;
    }

    @Override
    public String toString() {
        return "[depth=" + depth + ",position=" + position + "]";
    }
}
