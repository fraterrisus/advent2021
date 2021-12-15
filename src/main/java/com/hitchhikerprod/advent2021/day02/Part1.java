package com.hitchhikerprod.advent2021.day02;

import java.util.stream.Stream;

public class Part1 {
    private final Stream<Command> data;

    public Part1() {
        data = new DataProvider().getData();
    }

    public int solve() {
        final var submarine = new Submarine1();
        data.forEach(command -> {
            submarine.sail(command.distance() * command.direction().getPositionDelta());
            submarine.dive(command.distance() * command.direction().getDepthDelta());
        });
        return submarine.calculate();
    }

    public static void main(String[] args) {
        System.out.println(new Part1().solve());
    }
}
