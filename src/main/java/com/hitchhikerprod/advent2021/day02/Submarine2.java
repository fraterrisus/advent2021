package com.hitchhikerprod.advent2021.day02;

public class Submarine2 {
    private int position = 0;
    private int depth = 0;
    private int aim = 0;

    public void adjustAim(int distance) {
        aim += distance;
    }

    public void sail(int distance) {
        position += distance;
        depth += distance * aim;
    }

    public int calculate() {
        return position * depth;
    }

    @Override
    public String toString() {
        return "[depth=" + depth + ",position=" + position + "]";
    }
}
