package com.hitchhikerprod.advent2021.day02;

public enum Direction {
    FORWARD(1,0),
    UP(0,-1),
    DOWN(0,1);

    private final int positionDelta;
    private final int depthDelta;

    Direction(int x, int y) {
        positionDelta = x;
        depthDelta = y;
    }

    public int getPositionDelta() {
        return positionDelta;
    }

    public int getDepthDelta() {
        return depthDelta;
    }
}
