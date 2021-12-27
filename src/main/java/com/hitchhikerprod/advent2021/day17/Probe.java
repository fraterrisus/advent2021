package com.hitchhikerprod.advent2021.day17;

public class Probe {
    public record Point(int x, int y) {}

    private int x;
    private int y;
    private int dx;
    private int dy;

    public Probe(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
        this.x = 0;
        this.y = 0;
    }

    public Point position() {
        return new Point(x, y);
    }

    public Point simulate() {
        // change position based on current velocity
        x += dx;
        y += dy;

        // apply drag to X velocity
        if (dx > 1) { dx -= 1; }
        else if (dx < -1) { dx += 1; }
        else { dx = 0; }

        // apply gravity to Y velocity
        dy -= 1;

        return position();
    }

    public boolean reaches(Range x, Range y) {
        while(true) {
            if (x.includes(this.x) && y.includes(this.y)) {
                return true;
            }
            if (this.y < y.min() || (x.max().isPresent() && this.x > x.max().get())) {
                return false;
            }
            simulate();
        }
    }
}
