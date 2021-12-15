package com.hitchhikerprod.advent2021.day02;

import java.util.stream.Stream;

public class Part2 {
    private final Stream<Command> data;

    public Part2() {
        data = new DataProvider().getData();
    }

    public int solve() {
        final var submarine = new Submarine2();
        data.forEach(command -> {
            submarine.adjustAim(command.distance() * command.direction().getDepthDelta());
            submarine.sail(command.distance() * command.direction().getPositionDelta());
        });
        return submarine.calculate();
    }

    public static void main(String[] args) {
        System.out.println(new Part2().solve());
    }
}
